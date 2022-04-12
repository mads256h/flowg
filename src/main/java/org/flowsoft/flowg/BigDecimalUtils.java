package org.flowsoft.flowg;

import java.math.BigDecimal;
import java.math.RoundingMode;

public final class BigDecimalUtils {
    public static BigDecimal Divide(BigDecimal left, BigDecimal right) {
        try {
            return left.divide(right);
        }
        catch (ArithmeticException e) {
            return left.divide(right, 100, RoundingMode.HALF_UP);
        }
    }
}
