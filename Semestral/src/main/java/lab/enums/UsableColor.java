package lab.enums;

import javafx.scene.paint.Color;

public enum UsableColor {
    BLANK(Color.rgb(255, 255, 255), ColorGroup.NONE),
    RED_DARK(Color.rgb(153, 15, 61), ColorGroup.RED),
    RED(Color.rgb(204, 20, 82), ColorGroup.RED),
    DARK_GREEN(Color.rgb(57, 153, 15), ColorGroup.GREEN),
    GREEN(Color.rgb(75, 204, 20), ColorGroup.GREEN),
    DARK_BLUE(Color.rgb(15, 116, 153), ColorGroup.BLUE),
    BLUE(Color.rgb(25, 194, 255), ColorGroup.BLUE),
    DARK_YELLOW(Color.rgb(153, 130, 15), ColorGroup.YELLOW),
    YELLOW(Color.rgb(204, 173, 20), ColorGroup.YELLOW);
    private final Color color;
    private final ColorGroup group;

    UsableColor( Color color, ColorGroup group) {
        this.color = color;
        this.group = group;
    }

    public Color getColor() {
        return color;
    }

    public ColorGroup getGroup() {
        return group;
    }
}
