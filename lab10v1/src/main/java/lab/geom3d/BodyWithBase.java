package lab.geom3d;

public interface BodyWithBase extends Body3D {
    double getHeight();
    double getSurfaceOfBase();
    default double getVolume() {
        return getSurfaceOfBase() * getHeight();
    }
}
