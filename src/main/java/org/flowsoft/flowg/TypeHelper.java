package org.flowsoft.flowg;

public class TypeHelper {
    public static Type StringToType(String type) throws IllegalArgumentException {
        return switch (type) {
            case "void" -> Type.Void;
            case "number" -> Type.Number;
            case "bool" -> Type.Boolean;
            case "point" -> Type.Point;
            default -> throw new IllegalArgumentException("Unknown type");
        };
    }

    public static String TypeToString(Type type) throws IllegalArgumentException {
        return switch (type) {
            case Void -> "void";
            case Number -> "number";
            case Boolean -> "bool";
            case Point -> "point";
        };
    }
}
