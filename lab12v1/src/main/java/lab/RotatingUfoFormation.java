package lab;

import javafx.geometry.Point2D;

public class RotatingUfoFormation extends Formation<Ufo> {
    double angle = 0;

    double rotationSpeed = 5;

    public RotatingUfoFormation(World world, Point2D pos, Point2D vel, Ufo... ufo) {
        super(world, pos, vel, ufo);
    }

    @Override
    public void simulateElements(long delta) {

    }

    @Override
    public boolean intersect(Collisionable another) {
        return false;
    }

    @Override
    public void simulate(double deltaTime) {
        angle = angle + rotationSpeed * deltaTime;
        if(entitiesInFormation.isEmpty()){
            return;
        }
        Ufo middle = entitiesInFormation.getFirst();
        middle.setPositionOfMiddle(position);
        int rotCount = entitiesInFormation.size()-1;
        double radius = 100;
        for (int i = 1; i < entitiesInFormation.size(); i++) {
            Ufo ufo = entitiesInFormation.get(i);
            double currentAngle = Math.toRadians(angle + 360.0/rotCount*(i-1));
            Point2D ufoPosition = position.add(Math.cos(currentAngle)*radius, Math.sin(currentAngle)*radius);
            ufo.setPositionOfMiddle(ufoPosition);
        }
    }
}
