package lab.enums;

import lab.components.Block;
import lab.components.Shape;
import lab.components.ShortPoint;

public enum Shapes {
    I(new Shape(new Block[]{
            new Block(new ShortPoint(0,0)),
            new Block(new ShortPoint(0,1)),
            new Block(new ShortPoint(0,2)),
            new Block(new ShortPoint(0,3)),
    }, ColorGroup.RED)),
    L(new Shape(new Block[] {
            new Block(new ShortPoint(0, 0)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(2, 0)),
            new Block(new ShortPoint(2, 1))
    }, ColorGroup.RED)),
    J(new Shape(new Block[] {
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 1)),
            new Block(new ShortPoint(2, 1)),
            new Block(new ShortPoint(2, 0))
    }, ColorGroup.RED)),
    O(new Shape(new Block[] {
            new Block(new ShortPoint(0, 0)),
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(1, 1))
    }, ColorGroup.RED)),
    S(new Shape(new Block[] {
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(0, 2)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(1, 1))
    }, ColorGroup.RED)),
    Z(new Shape(new Block[] {
            new Block(new ShortPoint(0, 0)),
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 1)),
            new Block(new ShortPoint(1, 2))
    }, ColorGroup.RED)),
    T(new Shape(new Block[] {
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(1, 1)),
            new Block(new ShortPoint(1, 2))
    }, ColorGroup.RED)),
    BLUE_I(new Shape(new Block[]{
            new Block(new ShortPoint(0,0)),
            new Block(new ShortPoint(0,1)),
            new Block(new ShortPoint(0,2)),
            new Block(new ShortPoint(0,3)),
    }, ColorGroup.BLUE)),
    BLUE_L(new Shape(new Block[] {
            new Block(new ShortPoint(0, 0)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(2, 0)),
            new Block(new ShortPoint(2, 1))
    }, ColorGroup.BLUE)),
    BLUE_J(new Shape(new Block[] {
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 1)),
            new Block(new ShortPoint(2, 1)),
            new Block(new ShortPoint(2, 0))
    }, ColorGroup.BLUE)),
    BLUE_O(new Shape(new Block[] {
            new Block(new ShortPoint(0, 0)),
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(1, 1))
    }, ColorGroup.BLUE)),
    BLUE_S(new Shape(new Block[] {
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(0, 2)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(1, 1))
    }, ColorGroup.BLUE)),
    BLUE_Z(new Shape(new Block[] {
            new Block(new ShortPoint(0, 0)),
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 1)),
            new Block(new ShortPoint(1, 2))
    }, ColorGroup.BLUE)),
    BLUE_T(new Shape(new Block[] {
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(1, 1)),
            new Block(new ShortPoint(1, 2))
    }, ColorGroup.BLUE)),
    GREEN_I(new Shape(new Block[]{
            new Block(new ShortPoint(0,0)),
            new Block(new ShortPoint(0,1)),
            new Block(new ShortPoint(0,2)),
            new Block(new ShortPoint(0,3)),
    }, ColorGroup.GREEN)),
    GREEN_L(new Shape(new Block[] {
            new Block(new ShortPoint(0, 0)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(2, 0)),
            new Block(new ShortPoint(2, 1))
    }, ColorGroup.GREEN)),
    GREEN_J(new Shape(new Block[] {
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 1)),
            new Block(new ShortPoint(2, 1)),
            new Block(new ShortPoint(2, 0))
    }, ColorGroup.GREEN)),
    GREEN_O(new Shape(new Block[] {
            new Block(new ShortPoint(0, 0)),
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(1, 1))
    }, ColorGroup.GREEN)),
    GREEN_S(new Shape(new Block[] {
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(0, 2)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(1, 1))
    }, ColorGroup.GREEN)),
    GREEN_Z(new Shape(new Block[] {
            new Block(new ShortPoint(0, 0)),
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 1)),
            new Block(new ShortPoint(1, 2))
    }, ColorGroup.GREEN)),
    GREEN_T(new Shape(new Block[] {
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(1, 1)),
            new Block(new ShortPoint(1, 2))
    }, ColorGroup.GREEN)),
    YELLOW_I(new Shape(new Block[]{
            new Block(new ShortPoint(0,0)),
            new Block(new ShortPoint(0,1)),
            new Block(new ShortPoint(0,2)),
            new Block(new ShortPoint(0,3)),
    }, ColorGroup.YELLOW)),
    YELLOW_L(new Shape(new Block[] {
            new Block(new ShortPoint(0, 0)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(2, 0)),
            new Block(new ShortPoint(2, 1))
    }, ColorGroup.YELLOW)),
    YELLOW_J(new Shape(new Block[] {
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 1)),
            new Block(new ShortPoint(2, 1)),
            new Block(new ShortPoint(2, 0))
    }, ColorGroup.YELLOW)),
    YELLOW_O(new Shape(new Block[] {
            new Block(new ShortPoint(0, 0)),
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(1, 1))
    }, ColorGroup.YELLOW)),
    YELLOW_S(new Shape(new Block[] {
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(0, 2)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(1, 1))
    }, ColorGroup.YELLOW)),
    YELLOW_Z(new Shape(new Block[] {
            new Block(new ShortPoint(0, 0)),
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 1)),
            new Block(new ShortPoint(1, 2))
    }, ColorGroup.YELLOW)),
    YELLOW_T(new Shape(new Block[] {
            new Block(new ShortPoint(0, 1)),
            new Block(new ShortPoint(1, 0)),
            new Block(new ShortPoint(1, 1)),
            new Block(new ShortPoint(1, 2))
    }, ColorGroup.YELLOW));

    private final Shape shape;
    Shapes(Shape shape) {
        this.shape = shape;
    }

    public Shape getShape() {
        return shape;
    }
}
