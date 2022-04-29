package org.flowsoft.flowg;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
import org.flowsoft.flowg.exceptions.InvalidTokenException;
import org.flowsoft.flowg.exceptions.TabException;
import org.flowsoft.flowg.exceptions.type.*;
import org.flowsoft.flowg.nodes.base.Node;
import org.flowsoft.flowg.visitors.CodeGeneratingVisitor;
import org.flowsoft.flowg.visitors.PrettyPrintingVisitor;
import org.flowsoft.flowg.visitors.TreePrintingVisitor;
import org.flowsoft.flowg.visitors.TypeCheckingVisitor;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class main {
    public static void main(String[] args) {
        if (args.length != 2) {
            System.err.println("Usage: <input file> <output file>");
            return;
        }

        var inputFile = args[0];
        var outputFile = args[1];

        var baseDir = Path.of(inputFile).getParent();

        Yylex scanner;
        try {
            scanner = new Yylex(new FileReader(inputFile), inputFile);
        } catch (FileNotFoundException e) {
            System.err.println("Input file not found!");
            e.printStackTrace();
            return;
        }

        parser parser = new parser(scanner, new ComplexSymbolFactory());
        try {
            try {
                var symbol = parser.debug_parse();
                var rootNode = (Node) symbol.value;
                System.out.println(rootNode.Accept(new TreePrintingVisitor()));
                System.out.println(rootNode.Accept(new PrettyPrintingVisitor()));
                var typeCheckingVisitor = new TypeCheckingVisitor(baseDir);
                rootNode.Accept(typeCheckingVisitor);
                var codeGeneratingVisitor = new CodeGeneratingVisitor(typeCheckingVisitor.GetSymbolTable(), baseDir);
                codeGeneratingVisitor.run(rootNode);
                typeCheckingVisitor.PrintSymbolTable();
                System.out.println(codeGeneratingVisitor.GetCode());
                File f = new File(outputFile);
                FileWriter writer = new FileWriter(f);
                String str = codeGeneratingVisitor.GetCode();

                writer.write(str);
                writer.close();
            } catch (ParseException e) {
                throw e.GetInnerException();
            }

        catch (WrongPointIndexingException e) {
            System.err.format("%s:%d:%d: error: Points can only be indexed by x, y and z. You used: \"%s\"\n", e.GetLeft().getUnit(), e.GetLeft().getLine(), e.GetLeft().getColumn(), e.GetIdentifier());
        }
        } catch (TabException e) {
            System.err.format("%s:%d:%d: error: tabs are cringe and so are you\n", e.GetLeft().getUnit(), e.GetLeft().getLine(), e.GetLeft().getColumn());
            System.err.println(GetLine(e.GetLeft().getUnit(), e.GetLeft(), e.GetRight()));
        } catch (InvalidTokenException e) {
            System.err.format("%s:%d:%d: error: invalid token\n", e.GetLeft().getUnit(), e.GetLeft().getLine(), e.GetLeft().getColumn());
            System.err.println(GetLine(e.GetLeft().getUnit(), e.GetLeft(), e.GetRight()));
        } catch (ExpectedTypeException e) {
            System.err.format("%s:%d:%d: error: expected %s but got %s\n", e.GetLeft().getUnit(), e.GetLeft().getLine(), e.GetLeft().getColumn(), TypeHelper.TypeToString(e.GetExpected()), TypeHelper.TypeToString(e.GetActual()));
            System.err.println(GetLine(e.GetLeft().getUnit(), e.GetLeft(), e.GetRight()));
        } catch (IncludeNotFoundException e) {
            System.err.format("%s:%d:%d: error: included file '%s' not found\n", e.GetLeft().getUnit(), e.GetLeft().getLine(), e.GetLeft().getColumn(), e.GetFilename());
            System.err.println(GetLine(e.GetLeft().getUnit(), e.GetLeft(), e.GetRight()));
        } catch (SymbolNotFoundException e) {
            System.err.format("%s:%d:%d: error: symbol '%s' not found\n", e.GetLeft().getUnit(), e.GetLeft().getLine(), e.GetLeft().getColumn(), e.GetIdentifier());
            System.err.println(GetLine(e.GetLeft().getUnit(), e.GetLeft(), e.GetRight()));
        } catch (ParameterCountException e) {
            System.err.format("%s:%d:%d: error: expected %d parameters but got %d\n", e.GetLeft().getUnit(), e.GetLeft().getLine(), e.GetLeft().getColumn(), e.GetExpectedCount(), e.GetActualCount());
            System.err.println(GetLine(e.GetLeft().getUnit(), e.GetLeft(), e.GetRight()));
        } catch (TypeMismatchException e) {
            System.err.format("%s:%d:%d: error: type mismatch between %s and %s\n", e.GetLeft().getUnit(), e.GetLeft().getLine(), e.GetLeft().getColumn(), TypeHelper.TypeToString(e.GetLeftType()), TypeHelper.TypeToString(e.GetRightType()));
            System.err.println(GetLine(e.GetLeft().getUnit(), e.GetLeft(), e.GetRight()));
        } catch (RedeclarationException e) {
            System.err.format("%s:%d:%d: error: symbol redefinition\n", e.GetLeft().getUnit(), e.GetLeft().getLine(), e.GetLeft().getColumn());
            System.err.println(GetLine(e.GetLeft().getUnit(), e.GetLeft(), e.GetRight()));
        } catch (Exception e) {
            System.out.println("Could not parse");
            e.printStackTrace();
        }
    }

    private static String GetLine(String file, Location left, Location right) {
        String contents;
        try {
            contents = Files.readString(Path.of(file));
        } catch (IOException e) {
            return null;
        }

        var lines = contents.split("\r\n|\n", -1);
        var line = lines[left.getLine() - 1];

        var underline = " ".repeat(left.getColumn() - 1) + "~".repeat(right.getColumn() - left.getColumn() + 1);

        return line + "\n" + underline;
    }
}
