package lab.geom3d;

public class Block implements BodyWithBase {
    double width;
    double depth;
    double height;

    public Block(double width, double depth, double height) {
        this.width = width;
        this.height = height;
        this.depth = depth;
    }


    @Override
    public double getHeight() {
        return height;
    }

    @Override
    public double getSurfaceOfBase() {
        return width * depth;
    }
}
