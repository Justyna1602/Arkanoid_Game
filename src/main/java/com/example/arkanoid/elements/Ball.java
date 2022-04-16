package com.example.arkanoid.elements;

import javafx.geometry.Point2D;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Shape;

/**
 * The class that implements the ball extend the Elements class. The class instance will be created using the constructor.
 */

public class Ball extends Elements {
    private final int r = 15;
    private static final Image image = new Image("ball.png");

    public int getR() {
        return r;
    }

    /**
     * A constructor uses the Shape class to create appropriate shapes.
     * Using the setters of the inherited Elements class, we give the object the following characteristics: size, shape, starting point in the game, speed and angle of fall.
     * By using the ImagePattern class, we give the element a specific graphical appearance.
     */

    public Ball() {
        Shape ballShape = new Circle(r);
        ballShape.setFill(new ImagePattern(image));

        setShape(ballShape);

        setPosition(new Point2D(300, 200));

        setDefaultSpeed(5);

        double defaultDirection = Math.toRadians(60);
        setVelocity(
                new Point2D(
                        Math.cos(defaultDirection),
                        Math.sin(defaultDirection)
                ).multiply(getDefaultSpeed())

        );
    }

}
