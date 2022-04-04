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

"move" { return symbol(sym.MOVE); }

{Identifier} { return symbol(sym.IDENTIFIER, new IdentifierNode(yytext())); }

{Number} { return symbol(sym.NUMBER_LITERAL, new NumberLiteralNode(new BigDecimal(yytext()))); }

{Whitespace} { /* Ignore */ }

{Comment} { /* Ignore */ }

"(" { return symbol(sym.L_PAREN); }
")" { return symbol(sym.R_PAREN); }

"=" { return symbol(sym.ASSIGNMENT); }
";" { return symbol(sym.SEMICOLON); }
"," { return symbol(sym.COMMA); }
"+" { return symbol(sym.PLUS); }
"-" { return symbol(sym.MINUS); }
"*" { return symbol(sym.TIMES); }
"/" { return symbol(sym.DIVIDE); }

// This catches any error.
// Never match this symbol unless it is to report is as an error!
{Anything} { return symbol(sym.INVALID); }

