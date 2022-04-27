package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.Type;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public final class TypePair {
    private final Type _first;
    private final Type _last;

    public TypePair(Type first, Type last) {
        _first = first;
        _last = last;
    }

    public static <T> Optional<T> TryBothWays(Type left, Type right, Map<TypePair, T> map) {
        var pair = new TypePair(left, right);
        if (map.containsKey(pair)) {
            return Optional.of(map.get(pair));
        }

        pair = new TypePair(right, left);
        if (map.containsKey(pair)) {
            return Optional.of(map.get(pair));
        }

        return Optional.empty();
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
}
