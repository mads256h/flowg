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

%%


{Whitespace} { /*Ignore */ }

{Identifier} { System.out.println("Identifier: " + yytext()); }

{Number} { System.out.println("Number: " + yytext()); }

