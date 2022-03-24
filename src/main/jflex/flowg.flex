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
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline, yycolumn, value);
  }
%}



Type = "number"|"bool"|"point"|"void"
Identifier = [:jletter:][:jletterdigit:]*
Number = [0-9]+(\.[0-9]+)?
Whitespace = [\ \n]
NewLine = \n
Comment = \/\/[^\n]*

%%

{Type} { return symbol(sym.TYPE, new TypeNode(TypeHelper.StringToType(yytext()))); }

"true" { return symbol(sym.BOOLEAN_LITERAL, new BooleanLiteralNode(true)); }
"false" { return symbol(sym.BOOLEAN_LITERAL, new BooleanLiteralNode(false)); }

{Identifier} { return symbol(sym.IDENTIFIER, new IdentifierNode(yytext())); }

{Number} { return symbol(sym.NUMBER_LITERAL, new NumberLiteralNode(new BigDecimal(yytext()))); }

{Whitespace} { /* Ignore */ }

{Comment} { /* Ignore */ }

"=" { return symbol(sym.ASSIGNMENT); }
";" { return symbol(sym.SEMICOLON); }



