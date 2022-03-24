package org.flowsoft.flowg;

public class TypeHelper {
    public static Type StringToType(String type) throws IllegalArgumentException {
        switch (type) {
            case "void":
                return Type.Void;
            case "number":
                return Type.Number;
            case "bool":
                return Type.Boolean;
            case "point":
                return Type.Point;
        }
        throw new IllegalArgumentException("Unknown type");
    }

    public static String TypeToString(Type type) throws IllegalArgumentException {
        switch (type) {

            case Void:
                return "void";
            case Number:
                return "number";
            case Boolean:
                return "bool";
            case Point:
                return "point";
        }

        throw new IllegalArgumentException("Unknown type");
    }
}
