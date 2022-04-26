package org.flowsoft.flowg;

import java_cup.runtime.ComplexSymbolFactory.Location;

public class WrongPointIndexingException extends Exception {
        private final String _indexer;
        private final Location _left;
        private final Location _right;

    public WrongPointIndexingException(String identifier, Location left, Location right) {
            _indexer = identifier;
            _left = left;
            _right = right;
    }

        public String GetIdentifier() {
            return _indexer;
        }

        public Location GetLeft() {
            return _left;
        }

        public Location GetRight() {
            return _right;
        }
}

