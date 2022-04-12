package org.flowsoft.flowg.visitors;

import org.flowsoft.flowg.BigDecimalUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.util.Objects;

public final class Point {
    private final BigDecimal _x;
    private final BigDecimal _y;
    private final BigDecimal _z;


    public Point(BigDecimal x, BigDecimal y, BigDecimal z) {
        _x = x;
        _y = y;
        _z = z;
    }

    public BigDecimal GetX() {
        return _x;
    }

    public BigDecimal GetY() {
        return _y;
    }

    public BigDecimal GetZ() {
        return _z;
    }

    @Override
    public String toString() {
        return String.format("[%s, %s, %s]", _x, _y, _z);
    }

    public Point Add(Point point){
        return new Point(_x.add(point._x), _y.add(point._y), _z.add(point._z));
    }

    public Point Subtract(Point point){
        return new Point(_x.subtract(point._x), _y.subtract(point._y), _z.subtract(point._z));
    }

    public Point MultiplyBy(BigDecimal number) {
        return new Point(_x.multiply(number), _y.multiply(number), _z.multiply(number));
    }

    public Point DivideBy(BigDecimal number) {
        return new Point(
                BigDecimalUtils.Divide(_x, number),
                BigDecimalUtils.Divide(_y, number),
                BigDecimalUtils.Divide(_z, number)
        );
    }

    public BigDecimal Distance(Point point) {
        var x = GetX().subtract(point.GetX());
        var y = GetY().subtract(point.GetY());
        var z = GetZ().subtract(point.GetZ());

        var x2 = x.pow(2);
        var y2 = y.pow(2);
        var z2 = z.pow(2);

        var sum = x2.add(y2).add(z2);

        return sum.sqrt(MathContext.UNLIMITED);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return _x.compareTo(point._x) == 0 &&
               _y.compareTo(point._y) == 0 &&
               _z.compareTo(point._z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(_x, _y, _z);
    }

}
