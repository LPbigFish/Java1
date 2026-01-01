package lab;

import javafx.scene.paint.Color;

public enum UsableColor {
    BLANK((byte) 0, Color.rgb(255, 255, 255), ColorGroup.NONE),
    RED_DARK((byte) 1, Color.rgb(153, 15, 61), ColorGroup.RED),
    RED((byte) 2, Color.rgb(204, 20, 82), ColorGroup.RED),
    DARK_GREEN((byte) 3, Color.rgb(57, 153, 15), ColorGroup.GREEN),
    GREEN((byte) 4, Color.rgb(75, 204, 20), ColorGroup.GREEN),
    DARK_BLUE((byte) 5, Color.rgb(15, 116, 153), ColorGroup.BLUE),
    BLUE((byte) 6, Color.rgb(25, 194, 255), ColorGroup.BLUE),
    DARK_YELLOW((byte) 7, Color.rgb(153, 130, 15), ColorGroup.YELLOW),
    YELLOW((byte) 8, Color.rgb(204, 173, 20), ColorGroup.YELLOW);

    private final byte id;
    private final Color color;
    private final ColorGroup group;

    UsableColor(byte id, Color color, ColorGroup group) {
        this.id = id;
        this.color = color;
        this.group = group;
    }

    public byte getId() {
        return id;
    }

    public Color getColor() {
        return color;
    }

    public ColorGroup getGroup() {
        return group;
    }

    public static UsableColor fromId(byte id) {
        for (UsableColor color : UsableColor.values()) {
            if (color.getId() == id)
                return color;
        }
        return null;
    }
}
