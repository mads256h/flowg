package org.flowsoft.flowg;

import java_cup.runtime.ComplexSymbolFactory;
import java_cup.runtime.ComplexSymbolFactory.*;
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
%yylexthrow InvalidTokenException
%eofval{
  return symbol("EOF", sym.EOF);
%eofval}
%eofclose

%debug


%states GCodePreState, GcodeFunctionState

%init{
    _file = file;
%init}

%{
    private String _file = "unknown";

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
GCodeCode = [^}]*
Anything = .

%%

<YYINITIAL> {Type} { return symbol("type", sym.TYPE, new TypeNode(TypeHelper.StringToType(yytext()), leftLocation(), rightLocation())); }

// Boolean literals
<YYINITIAL> "true" { return symbol("true", sym.BOOLEAN_LITERAL, new BooleanLiteralNode(true, leftLocation(), rightLocation())); }
<YYINITIAL> "false" { return symbol("false", sym.BOOLEAN_LITERAL, new BooleanLiteralNode(false, leftLocation(), rightLocation())); }

// Builtin functions
<YYINITIAL> "move" { return symbol("move", sym.MOVE); }
<YYINITIAL> "line" { return symbol("line", sym.LINE); }
<YYINITIAL> "cw_arc" { return symbol("cw_arc", sym.CW_ARC); }
<YYINITIAL> "ccw_arc" { return symbol("ccw_arc", sym.CCW_ARC); }

// Math builtins
<YYINITIAL> "sqrt" { return symbol("sqrt", sym.SQRT); }
<YYINITIAL> "sin" { return symbol("sin", sym.SIN); }
<YYINITIAL> "cos" { return symbol("cos", sym.COS); }
<YYINITIAL> "tan" { return symbol("tan", sym.TAN); }
<YYINITIAL> "arcsin" { return symbol("arcsin", sym.ARCSIN); }
<YYINITIAL> "arccos" { return symbol("arccos", sym.ARCCOS); }
<YYINITIAL> "arctan" { return symbol("arctan", sym.ARCTAN); }

// Control flow
<YYINITIAL> "for" { return symbol("for", sym.FOR); }
<YYINITIAL> "to" { return symbol("to", sym.TO); }
<YYINITIAL> "return" { return symbol("return", sym.RETURN); }
<YYINITIAL> "if" { return symbol("if", sym.IF); }
<YYINITIAL> "else" { return symbol("else", sym.ELSE); }

// GCode
<YYINITIAL> "gcode" { yybegin(GCodePreState); return symbol("GCODE", sym.GCODE); }


<GcodeFunctionState> {GCodeCode} { return symbol("GCodeCodeNode", sym.GCODECODE, new GCodeCodeNode(yytext(), leftLocation(), rightLocation())); }

<YYINITIAL> {Identifier} { return symbol("identifier", sym.IDENTIFIER, new IdentifierNode(yytext(), leftLocation(), rightLocation())); }

<YYINITIAL> {Number} { return symbol("number literal", sym.NUMBER_LITERAL, new NumberLiteralNode(new BigDecimal(yytext()), leftLocation(), rightLocation())); }

<YYINITIAL> {Whitespace} { /* Ignore */ }

<YYINITIAL> {Comment} { /* Ignore */ }

<YYINITIAL> "(" { return symbol("(", sym.L_PAREN); }
<YYINITIAL> ")" { return symbol(")", sym.R_PAREN); }

<GCodePreState> "{" { yybegin(GcodeFunctionState); return symbol("{", sym.L_BRACKET); }
<GcodeFunctionState> "}" { yybegin(YYINITIAL); return symbol("}", sym.R_BRACKET); }
<YYINITIAL> "{" { return symbol("{", sym.L_BRACKET); }
<YYINITIAL> "}" { return symbol("}", sym.R_BRACKET); }

<YYINITIAL> "[" { return symbol("[", sym.L_SQUARE_BRACKET); }
<YYINITIAL> "]" { return symbol("]", sym.R_SQUARE_BRACKET); }

<YYINITIAL> "=" { return symbol("=", sym.ASSIGNMENT); }
<YYINITIAL> ";" { return symbol(";", sym.SEMICOLON); }
<YYINITIAL> "," { return symbol(",", sym.COMMA); }
<YYINITIAL> "." { return symbol(".", sym.DOT); }

// Arithmic operators
<YYINITIAL> "+" { return symbol("+", sym.PLUS); }
<YYINITIAL> "-" { return symbol("-", sym.MINUS); }
<YYINITIAL> "*" { return symbol("*", sym.TIMES); }
<YYINITIAL> "/" { return symbol("/", sym.DIVIDE); }
<YYINITIAL> "^" { return symbol("^", sym.POWER); }

// Boolean operators
<YYINITIAL> ">" { return symbol(">", sym.GREATER_THAN); }
<YYINITIAL> "<" { return symbol("<", sym.LESS_THAN); }
<YYINITIAL> "==" { return symbol("==", sym.EQUALS); }
<YYINITIAL> ">=" { return symbol(">=", sym.GREATER_THAN_EQUALS); }
<YYINITIAL> "<=" { return symbol("<=", sym.LESS_THAN_EQUALS); }
<YYINITIAL> "&&" { return symbol("&&", sym.AND); }
<YYINITIAL> "||" { return symbol("||", sym.OR); }
<YYINITIAL> "!" { return symbol("!", sym.NOT); }

// This catches any error.
{Anything} { throw new InvalidTokenException(leftLocation(), rightLocation()); }

