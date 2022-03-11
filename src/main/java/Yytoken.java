public class Yytoken {
    private TokenType _tokenType;
    private Object _value;
    public Yytoken(TokenType tokenType){
        this(tokenType, null);
    }

    public Yytoken(TokenType tokenType, Object value) {
        _tokenType = tokenType;
        _value = value;
    }

    @Override
    public String toString() {
        return "Yytoken{" +
                "_tokenType=" + _tokenType +
                ", _value=" + _value +
                '}';
    }
}
enum TokenType {
    IDENTIFIER,
    NUMBER
}
