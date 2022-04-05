package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;
import org.flowsoft.flowg.TypeException;

import java.util.Map;
import java.util.Objects;

public final class TypePair {
    private final Type _first;
    private final Type _last;

    public TypePair(Type first, Type last) {
        _first = first;
        _last = last;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TypePair typePair = (TypePair) o;
        return _first == typePair._first && _last == typePair._last;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_first, _last);
    }


    public static <T> T TryBothWays(Type left, Type right, Map<TypePair, T> map) throws TypeException {
        var pair = new TypePair(left, right);
        if (map.containsKey(pair)) {
            return map.get(pair);
        }

        pair = new TypePair(right, left);
        if (map.containsKey(pair)) {
            return map.get(pair);
        }

        throw new TypeException();
    }
}
