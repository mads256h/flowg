package org.flowsoft.flowg;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.*;
import org.flowsoft.flowg.exceptions.InvalidTokenException;
import org.flowsoft.flowg.exceptions.TabException;
import org.flowsoft.flowg.nodes.*;
import org.flowsoft.flowg.nodes.base.INode;
import org.flowsoft.flowg.nodes.functions.GCodeCodeNode;
import java.math.BigDecimal;
%%


%public
%ctorarg String file

%line
%column
%char
%unicode

// Instead of cup
%implements java_cup.runtime.Scanner
%function next_token
%type java_cup.runtime.Symbol
%yylexthrow InvalidTokenException, TabException
%eofval{
  return symbol("EOF", sym.EOF);
%eofval}
%eofclose

%debug

%init{
    _file = file;
%init}

%{
    private String _file = "unknown";
    private StringBuffer _stringBuffer = new StringBuffer();

    private Location leftLocation() {
        return new Location(_file, yyline + 1, yycolumn + 1);
    }

    private Location rightLocation() {
        return new Location(_file, yyline + 1, yycolumn + yylength());
    }

    private ComplexSymbol symbol(String name, int type) {
        return new ComplexSymbolFactory.ComplexSymbol(
            name,
            type,
            leftLocation(),
            rightLocation());
    }

    private ComplexSymbol symbol(String name, int type, INode value) {
        return new ComplexSymbolFactory.ComplexSymbol(
            name,
            type,
            leftLocation(),
            rightLocation(),
            value);
    }
%}



Type = "number"|"bool"|"point"|"void"
Identifier = [a-zA-Z_][a-zA-Z0-9_]*
Number = [0-9]+(\.[0-9]+)?
Whitespace = [\ \r\n]
NewLine = \n
Comment = \/\/[^\n]*
GCodeCode = [^{}\[\]]*
Anything = .

%state include
%state sysstring
%state userstring
%state gcode_prestate
%state gcode_functionstate
%state gcode_expression

%%

<YYINITIAL> {

    // Include statement
    "#include" { yybegin(include); return symbol("#include", sym.INCLUDE); }

    "=" { return symbol("=", sym.ASSIGNMENT); }
    ";" { return symbol(";", sym.SEMICOLON); }

    "[" { return symbol("[", sym.L_SQUARE_BRACKET); }
    "]" { return symbol("]", sym.R_SQUARE_BRACKET); }

    // Builtin functions
    "move" { return symbol("move", sym.MOVE); }
    "line" { return symbol("line", sym.LINE); }

    // Control flow
    "for" { return symbol("for", sym.FOR); }
    "to" { return symbol("to", sym.TO); }
    "return" { return symbol("return", sym.RETURN); }
    "if" { return symbol("if", sym.IF); }
    "else" { return symbol("else", sym.ELSE); }

    // GCODE
    "gcode" { yybegin(gcode_prestate); return symbol("GCODE", sym.GCODE); }

    {Whitespace} { /* Ignore */ }

    {Comment} { /* Ignore */ }

}

<YYINITIAL, gcode_expression, gcode_prestate> {
    {Type} { return symbol("type", sym.TYPE, new TypeNode(TypeHelper.StringToType(yytext()), leftLocation(), rightLocation())); }

    "(" { return symbol("(", sym.L_PAREN); }
    ")" { return symbol(")", sym.R_PAREN); }
    "," { return symbol(",", sym.COMMA); }
}

// Used in normal code and gcode_expressions
<YYINITIAL, gcode_expression> {
    // Boolean literals
    "true" { return symbol("true", sym.BOOLEAN_LITERAL, new BooleanLiteralNode(true, leftLocation(), rightLocation())); }
    "false" { return symbol("false", sym.BOOLEAN_LITERAL, new BooleanLiteralNode(false, leftLocation(), rightLocation())); }

    // Math builtins
    "sqrt" { return symbol("sqrt", sym.SQRT); }
    "sin" { return symbol("sin", sym.SIN); }
    "cos" { return symbol("cos", sym.COS); }
    "tan" { return symbol("tan", sym.TAN); }
    "arcsin" { return symbol("arcsin", sym.ARCSIN); }
    "arccos" { return symbol("arccos", sym.ARCCOS); }
    "arctan" { return symbol("arctan", sym.ARCTAN); }


    "{" { return symbol("{", sym.L_BRACKET); }
    "}" { return symbol("}", sym.R_BRACKET); }

    "." { return symbol(".", sym.DOT); }

    // Arithmic operators
    "+" { return symbol("+", sym.PLUS); }
    "-" { return symbol("-", sym.MINUS); }
    "*" { return symbol("*", sym.TIMES); }
    "/" { return symbol("/", sym.DIVIDE); }
    "^" { return symbol("^", sym.POWER); }

    // Boolean operators
    ">" { return symbol(">", sym.GREATER_THAN); }
    "<" { return symbol("<", sym.LESS_THAN); }
    "==" { return symbol("==", sym.EQUALS); }
    ">=" { return symbol(">=", sym.GREATER_THAN_EQUALS); }
    "<=" { return symbol("<=", sym.LESS_THAN_EQUALS); }
    "&&" { return symbol("&&", sym.AND); }
    "||" { return symbol("||", sym.OR); }
    "!" { return symbol("!", sym.NOT); }


    {Number} { return symbol("number literal", sym.NUMBER_LITERAL, new NumberLiteralNode(new BigDecimal(yytext()), leftLocation(), rightLocation())); }
}

<YYINITIAL, gcode_expression, gcode_prestate> {
    {Identifier} { return symbol("identifier", sym.IDENTIFIER, new IdentifierNode(yytext(), leftLocation(), rightLocation())); }
}


<include> {
    "<" {_stringBuffer.setLength(0); yybegin(sysstring);}
    "\"" {_stringBuffer.setLength(0); yybegin(userstring);}
    {Whitespace} {/* Ignore */}
}

<sysstring> {
    [^>] {_stringBuffer.append(yytext());}
    ">" {yybegin(YYINITIAL); return symbol("sysstring", sym.SYSSTRING, new SysStringNode(_stringBuffer.toString(), leftLocation(), rightLocation())); }
}

<userstring> {
    [^\"] {_stringBuffer.append((yytext()));}
    "\"" {yybegin(YYINITIAL); return symbol("userstring", sym.USERSTRING, new UserStringNode(_stringBuffer.toString(), leftLocation(), rightLocation())); }
}

<gcode_functionstate> {
    {GCodeCode} { return symbol("gcode literal", sym.GCODECODE, new GCodeCodeNode(yytext(), leftLocation(), rightLocation())); }
    "[" { yybegin(gcode_expression); return symbol("[", sym.L_SQUARE_BRACKET); }
    "}" { yybegin(YYINITIAL); return symbol("}", sym.R_BRACKET); }
}

<gcode_expression> {
    "]" { yybegin(gcode_functionstate); return symbol("]", sym.R_SQUARE_BRACKET); }

    {Whitespace} { /* Ignore */ }
}

<gcode_prestate> {
    "{" { yybegin(gcode_functionstate); return symbol("{", sym.L_BRACKET); }
    {Whitespace} { /* Ignore */ }
}

"\t" { throw new TabException(leftLocation(), rightLocation()); }

// This catches any error.
{Anything} { throw new InvalidTokenException(leftLocation(), rightLocation()); }

