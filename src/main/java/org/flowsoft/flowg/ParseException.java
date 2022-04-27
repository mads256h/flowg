package org.flowsoft.flowg;

public class ParseException extends TypeException {

    private final Exception _innerException;

    public ParseException(Exception innerException) {
        _innerException = innerException;
    }

    public Exception GetInnerException() {
        return _innerException;
    }
}
