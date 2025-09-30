package lab.objects;

import javafx.geometry.Point2D;

public class GameStore {
    public double posX = 0;
    public double posY = 0;

    public double rotation = 0.0;

    public double velX = 0;
    public double velY = 0;

    boolean keyObtained = false;

    double distanceFromKey = 0.0;

    Point2D keyPos;

    public GameStore() {
        keyPos = new Point2D(0, 0);
    }

    public boolean getKeyState() {
        return keyObtained;
    }

    public void setKeyState(boolean keyObtained) {
        this.keyObtained = keyObtained;
    }

    public void measureDistanceFromKey() {
        distanceFromKey = keyPos.distance(posX, posY);
    }

}
