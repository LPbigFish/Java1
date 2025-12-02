package lab.geom3d;

public class Cylinder implements BodyWithBase {
    double radius;
    double height;

    public Cylinder(double radius, double height) {
        this.radius = radius;
        this.height = height;
    }

    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getSurfaceOfBase() {
        return Math.PI * radius * radius;
    }
}
