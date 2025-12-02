package lab.geom3d;

public class Sphere implements Body3D{
    double radius;

    public Sphere(double r) {
        radius = r;
    }

    public double getVolume() {
        return (4.0 / 3.0) * radius * radius * radius * Math.PI;
    }
}
