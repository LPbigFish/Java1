package lab.components;

import javafx.scene.image.PixelWriter;
import lab.enums.ColorGroup;

import java.util.Arrays;

public class Shape {
    ShortPoint pos;
    Block[] blocks;
    ShortPoint size;
    public int minOffsetX, maxOffsetX, minOffsetY, maxOffsetY;
    public Shape(Block[] blocks, ColorGroup color) {
        this.blocks = blocks;
        Arrays.stream(this.blocks).forEach(x -> x.setColor(color));
        this.pos = new ShortPoint(0,0);
        recalculateBounds();
    }
    public Block[] getBlocks() {
        return blocks;
    }
    public ShortPoint getPos() {
        return pos;
    }
    public void setPos(ShortPoint pos) {
        this.pos = pos;
    }
    public void recalculateBounds() {
        if (blocks == null || blocks.length == 0) {
            size = new ShortPoint(0, 0);
            return;
        }

        int minX = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Block b : blocks) {
            int bx = b.pos.getX();
            int by = b.pos.getY();

            if (bx < minX) minX = bx;
            if (bx > maxX) maxX = bx;
            if (by < minY) minY = by;
            if (by > maxY) maxY = by;
        }

        this.minOffsetX = minX * Block.BLOCK_SIZE;
        this.maxOffsetX = (maxX * Block.BLOCK_SIZE) + Block.BLOCK_SIZE;
        this.minOffsetY = minY * Block.BLOCK_SIZE;
        this.maxOffsetY = (maxY * Block.BLOCK_SIZE) + Block.BLOCK_SIZE;

        int width = maxOffsetX - minOffsetX;
        int height = maxOffsetY - minOffsetY;
        this.size = new ShortPoint(width, height);
    }
    public void draw(PixelWriter pw, ShortPoint gridSize) {
        int rows = gridSize.getY();
        int cols = gridSize.getX();

        for (Block block : blocks) {
            int logicRowStart = pos.getX() + (block.pos.getX() * Block.BLOCK_SIZE);
            int logicColStart = pos.getY() + (block.pos.getY() * Block.BLOCK_SIZE);

            for (int r = 0; r < Block.BLOCK_SIZE; r++) {
                for (int c = 0; c < Block.BLOCK_SIZE; c++) {

                    int logicRow = logicRowStart + r;
                    int visualX = logicColStart + c;

                    int visualY = rows - logicRow - 1;

                    if (visualX >= 0 && visualX < cols && visualY >= 0 && visualY < rows) {
                        pw.setColor(visualX, visualY, block.definition[r][c].getColor());
                    }
                }
            }
        }
    }
}
