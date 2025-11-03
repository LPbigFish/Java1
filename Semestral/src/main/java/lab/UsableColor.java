package lab;

import javafx.scene.paint.Color;

public enum UsableColor {
    RED_DARK((byte) 1, Color.rgb(153, 15, 61), 1),
    RED((byte) 2, Color.rgb(204, 20, 82), 1),
    DARK_GREEN((byte) 3, Color.rgb(57, 153, 15), 2),
    GREEN((byte) 4, Color.rgb(75, 204, 20), 2),
    DARK_BLUE((byte) 5, Color.rgb(15, 116, 153), 3),
    BLUE((byte) 6, Color.rgb(25, 194, 255), 3),
    DARK_YELLOW((byte) 7, Color.rgb(153, 130, 15), 4),
    YELLOW((byte) 8, Color.rgb(204, 173, 20), 4);

    private final byte id;
    private final Color color;
    private final int group;

    UsableColor(byte id, Color color, int group) {
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

    public int getGroup() {
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
