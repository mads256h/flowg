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

    public static Boolean GreaterThan(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) > 0;
    }

    public static Boolean LessThan(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) < 0;
    }

    public static Boolean Equals(BigDecimal left, BigDecimal right) {
        return  left.compareTo(right) == 0;
    }

    public static Boolean GreaterThanEquals(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) >= 0;
    }

    public static Boolean LessThanEquals(BigDecimal left, BigDecimal right) {
        return left.compareTo(right) <= 0;
    }
}
