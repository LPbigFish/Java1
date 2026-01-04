package lab.components;

import javafx.geometry.Point2D;

public class ShortPoint {
    private short x;
    private short y;

    public ShortPoint(Point2D point) {
        x = (short) point.getX();
        y = (short) point.getY();
    }

    public ShortPoint(ShortPoint point) {
        x = point.x;
        y = point.y;
    }

    public ShortPoint(int x, int y) {
        this.x = (short) x;
        this.y = (short) y;
    }

    public ShortPoint add(ShortPoint point) {
        x += point.x;
        y += point.y;
        return this;
    }

    public ShortPoint sub(ShortPoint point) {
        x -= point.x;
        y -= point.y;
        return this;
    }

    public ShortPoint mul(short multiplier) {
        x *= multiplier;
        y *= multiplier;
        return this;
    }

    public Point2D getPoint() {
        return new Point2D(x, y);
    }

    public short getX() {
        return x;
    }

    public short getY() {
        return y;
    }

    public void setX(short x) {
        this.x = x;
    }
    public void setY(short y) {
        this.y = y;
    }
}
