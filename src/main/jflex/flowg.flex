package org.flowsoft.flowg;

import java_cup.runtime.Symbol;
import org.flowsoft.flowg.nodes.*;
import java.math.BigDecimal;
%%

%public

%line
%column
%char
%cup
%unicode

%debug

%{
  private Symbol symbol(int type) {
    return new Symbol(type, yyline, yycolumn);
  }
  private Symbol symbol(int type, INode value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}



Type = "number"|"bool"|"point"|"void"
Identifier = [a-zA-Z][a-zA-Z0-9]*
Number = [0-9]+(\.[0-9]+)?
Whitespace = [\ \n]
NewLine = \n
Comment = \/\/[^\n]*
Anything = .

%%

{Type} { return symbol(sym.TYPE, new TypeNode(TypeHelper.StringToType(yytext()))); }

"true" { return symbol(sym.BOOLEAN_LITERAL, new BooleanLiteralNode(true)); }
"false" { return symbol(sym.BOOLEAN_LITERAL, new BooleanLiteralNode(false)); }

"return" { return symbol(sym.RETURN); }
"move" { return symbol(sym.MOVE); }
"sqrt" { return symbol(sym.SQRT); }

"for" { return symbol(sym.FOR); }
"to" { return symbol(sym.TO); }

"if" { return symbol(sym.IF); }
"else" { return symbol(sym.ELSE); }

{Identifier} { return symbol(sym.IDENTIFIER, new IdentifierNode(yytext())); }

{Number} { return symbol(sym.NUMBER_LITERAL, new NumberLiteralNode(new BigDecimal(yytext()))); }

{Whitespace} { /* Ignore */ }

{Comment} { /* Ignore */ }

"(" { return symbol(sym.L_PAREN); }
")" { return symbol(sym.R_PAREN); }

"{" { return symbol(sym.L_BRACKET); }
"}" { return symbol(sym.R_BRACKET); }

"[" { return symbol(sym.L_SQUARE_BRACKET); }
"]" { return symbol(sym.R_SQUARE_BRACKET); }

"=" { return symbol(sym.ASSIGNMENT); }
";" { return symbol(sym.SEMICOLON); }
"," { return symbol(sym.COMMA); }
"+" { return symbol(sym.PLUS); }
"-" { return symbol(sym.MINUS); }
"*" { return symbol(sym.TIMES); }
"/" { return symbol(sym.DIVIDE); }
"^" { return symbol(sym.POWER); }
"!" { return symbol(sym.NOT); }

">" { return symbol(sym.GREATER_THAN); }
"<" { return symbol(sym.LESS_THAN); }
"==" { return symbol(sym.EQUALS); }
">=" { return symbol(sym.GREATER_THAN_EQUALS); }
"<=" { return symbol(sym.LESS_THAN_EQUALS); }
"&&" { return symbol(sym.AND); }
"||" { return symbol(sym.OR); }

// This catches any error.
// Never match this symbol unless it is to report is as an error!
{Anything} { return symbol(sym.INVALID); }

