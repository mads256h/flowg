package org.flowsoft.flowg;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.Location;
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
                var typeCheckingVisitor = new TypeCheckingVisitor();
                rootNode.Accept(typeCheckingVisitor);
                var codeGeneratingVisitor = new CodeGeneratingVisitor(typeCheckingVisitor.GetSymbolTable());
                codeGeneratingVisitor.run(rootNode);
                typeCheckingVisitor.PrintSymbolTable();
                System.out.println(codeGeneratingVisitor.GetCode());
                File f = new File(outputFile);
                FileWriter writer = new FileWriter(f);
                var str = """
                        M140 S60
                        M105
                        M190 S60
                        M104 S200
                        M105
                        M109 S200
                        M82 ;absolute extrusion mode
                        G21 ;metric values
                        G90 ;absolute positioning
                        M82 ;set extruder to absolute mode
                        M107 ;start with the fan off
                        G28 Z0 ;move Z to bottom endstops
                        G28 X0 Y0 ;move X/Y to endstops
                        G1 X15 Y0 F4000 ;move X/Y to front of printer
                        G1 Z15.0 F9000 ;move the platform to 15mm
                        G92 E0 ;zero the extruded length
                        G1 F200 E10 ;extrude 10 mm of feed stock
                        G92 E0 ;zero the extruded length again
                        G1 Y50 F9000
                        ;Put printing message on LCD screen
                        M117 Printing...
                        G92 E0
                        G92 E0
                        G1 F1500 E-6.5
                        ;LAYER_COUNT:36
                        ;LAYER:0
                        M107
                        G1 F1000 X0 Y0
                        M106 S255
                        ;
                        """ + codeGeneratingVisitor.GetCode() + """
                        M140 S0
                        M107
                        M104 S0 ;extruder heater off
                        M140 S0 ;heated bed heater off (if you have it)
                        G91 ;relative positioning
                        G1 E-1 F300  ;retract the filament a bit before lifting the nozzle, to release some of the pressure
                        G1 Z+0.5 E-5 X-20 Y-20 F9000 ;move Z up a bit and retract filament even more
                        G28 X0 Y0 ;move X/Y to min endstops, so the head is out of the way
                        M84 ;steppers off
                        G90 ;absolute positioning
                        ;Version _2.6 of the firmware can abort the print too early if the file ends
                        ;too soon. However if the file hasn't ended yet because there are comments at
                        ;the end of the file, it won't abort yet. Therefore we have to put at least 512
                        ;bytes at the end of the g-code so that the file is not yet finished by the
                        ;time that the motion planner gets flushed. With firmware version _3.3 this
                        ;should be fixed, so this comment wouldn't be necessary any more. Now we have
                        ;to pad this text to make precisely 512 bytes.
                        M82 ;absolute extrusion mode
                        M104 S0
                        """;

                writer.write(str);
                writer.close();
            } catch (ParseException e) {
                throw e.GetInnerException();
            }


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
