package org.flowsoft.flowg;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class BigDecimalUtils {
    public static BigDecimal Divide(BigDecimal left, BigDecimal right) {
        try {
            return left.divide(right, RoundingMode.UNNECESSARY);
        }
        catch (ArithmeticException e) {
            return left.divide(right, 100, RoundingMode.HALF_UP);
        }
    }

    public static Boolean NumberCompare(String operator, BigDecimal left, BigDecimal right) {
        switch (operator) {
            case "GE" -> { return left.compareTo(right) > 0; }
            case "LE" -> { return left.compareTo(right) < 0; }
            case "EQ" -> { return  left.compareTo(right) == 0; }
            case "EQGE" -> { return left.compareTo(right) >= 0; }
            case "EQLE" -> { return left.compareTo(right) <= 0; }
            default -> { return null; }
        }
    }
}
