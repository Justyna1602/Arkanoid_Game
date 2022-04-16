package com.example.arkanoid.elements;

import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

import javafx.scene.shape.Shape;

/**
 * The class is a general representation of the game components.
 */

public class Elements {
    private Shape shape;
    private Point2D position;
    private Point2D velocity;
    private double defaultSpeed;


    public Elements() {
        this(null);
    }

    public Elements(Shape shape) {
        this(shape, 0, 0);
    }

    public Elements(Shape shape, double x, double y) {
        this(shape, 0, 0, 0, 0);
    }

    public Elements(Shape shape, double x, double y, double dx, double dy) {
        this.shape = shape;
        position = new Point2D(x, y);
        velocity = new Point2D(dx, dy);

        updateShapePos();
    }

    public void applyVelocity() {
        position = position.add(velocity);

        updateShapePos();
    }

    public Shape getShape() {
        return shape;
    }

    public void setShape(Shape shape) {
        this.shape = shape;

        updateShapePos();
    }

    public Point2D getVelocity() {
        return velocity;
    }

    public void setVelocity(Point2D velocity) {
        this.velocity = velocity;
    }

    public Point2D getPosition() {
        return position;
    }

    public void setPosition(Point2D pos) {
        this.position = pos;

        updateShapePos();
    }

    public double getDefaultSpeed() {
        return defaultSpeed;
    }


    public void setDefaultSpeed(double defaultSpeed) {
        this.defaultSpeed = defaultSpeed;
    }

    /**
     * The method checks the interactions between individual elements and the boundaries of the displayed window.
     *
     * @param otherElements It's element representation.
     * @return  boolean. True when two elements come into contact.
     */

    public boolean intersects(Elements otherElements) {
        Shape intersection = Shape.intersect(getShape(), otherElements.getShape());
        Bounds intersectionBounds = intersection.getLayoutBounds();

        return intersectionBounds.getWidth() > 0 || intersectionBounds.getHeight() > 0;
    }

    /**
     * The method updates the location of the item.
     */

    private void updateShapePos() {
        if (shape != null) {
            shape.relocate(position.getX(), position.getY());
        }
    }
}