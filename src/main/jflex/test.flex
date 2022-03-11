import java.math.BigDecimal;
%%

%public

%line
%column
%char
%unicode

%debug

Identifier = [:jletter:][:jletterdigit:]*
Number = [0-9]+(\.[0-9]+)?
Whitespace = [\ \n]
NewLine = \n
Comment = \/\/[^\n]*

%%


{Identifier} { return new Yytoken(TokenType.IDENTIFIER, yytext()); }

{Number} { return new Yytoken(TokenType.NUMBER, new BigDecimal(yytext())); }

{Whitespace} { /* Ignore */ }

{Comment} { /* Ignore */ }



