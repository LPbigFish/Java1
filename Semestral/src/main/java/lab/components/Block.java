package lab.components;

import lab.enums.ColorGroup;
import lab.enums.UsableColor;

import java.util.Arrays;

public class Block {
    public static final short BLOCK_SIZE = 15;
    ShortPoint pos;
    UsableColor[][] definition;

    public Block(ShortPoint pos) {
        this.pos = pos;
        this.definition = new UsableColor[BLOCK_SIZE][BLOCK_SIZE];

    }
    public void setColor(ColorGroup color) {
        UsableColor[] colors = Arrays.stream(UsableColor.values()).filter(x -> x.getGroup().equals(color)).toArray(UsableColor[]::new);
        for (short i = 0; i < BLOCK_SIZE; i++) {
            for (short j = 0; j < BLOCK_SIZE; j++) {
                int distance = Math.min(Math.min(i, j), Math.min(BLOCK_SIZE - 1 - j, BLOCK_SIZE - 1 - i));
                if (distance % 2 == 0) {
                    definition[i][j] = colors[0];
                } else {
                    definition[i][j] = colors[1];
                }
            }
        }
    }
}
