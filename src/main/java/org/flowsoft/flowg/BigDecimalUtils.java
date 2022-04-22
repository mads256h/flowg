package org.flowsoft.flowg;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public final class BigDecimalUtils {
    public static final MathContext DEFAULT_MATH_CONTEXT = new MathContext(100, RoundingMode.HALF_UP);

    private static final DecimalFormatSymbols DECIMAL_FORMAT_SYMBOLS = new DecimalFormatSymbols() {
        {
            setDecimalSeparator('.');
        }
    };

    private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat() {
        {
            setMaximumFractionDigits(5);
            setMinimumFractionDigits(0);
            setGroupingUsed(false);
            setDecimalFormatSymbols(DECIMAL_FORMAT_SYMBOLS);
        }
    };

    public static BigDecimal Divide(BigDecimal left, BigDecimal right) {
        try {
            //noinspection BigDecimalMethodWithoutRoundingCalled
            return left.divide(right);
        }
        catch (ArithmeticException e) {
            return left.divide(right, DEFAULT_MATH_CONTEXT);
        }
    }

    public static String ToGCode(BigDecimal v) {
        return DECIMAL_FORMAT.format(v);
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
