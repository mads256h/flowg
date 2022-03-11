package org.flowsoft.flowg;

import java_cup.runtime.Symbol;
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



Identifier = [:jletter:][:jletterdigit:]*
Number = [0-9]+(\.[0-9]+)?
Whitespace = [\ \n]
NewLine = \n
Comment = \/\/[^\n]*

%%


{Identifier} { return symbol(sym.IDENTIFIER); }

{Number} { return symbol(sym.INTEGER_LITERAL); }

{Whitespace} { /* Ignore */ }

{Comment} { /* Ignore */ }



