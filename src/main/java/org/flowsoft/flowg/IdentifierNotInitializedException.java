package org.flowsoft.flowg;

public class IdentifierNotInitializedException extends Exception {
    public IdentifierNotInitializedException(String identifier){
        super("The identifier:" + "\"" + identifier + "\"" + " is not initialized at this point.");
    }
}

