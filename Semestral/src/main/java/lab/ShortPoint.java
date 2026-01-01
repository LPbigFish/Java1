package lab;

import javafx.geometry.Point2D;

public class ShortPoint {
    short x;
    short y;

    ShortPoint(Point2D point) {
        x = (short) point.getX();
        y = (short) point.getY();
    }

    ShortPoint(int x, int y) {
        this.x = (short) x;
        this.y = (short) y;
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
