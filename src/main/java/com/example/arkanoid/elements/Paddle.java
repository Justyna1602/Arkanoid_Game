package com.example.arkanoid.elements;

import com.example.arkanoid.game.BoardSize;

import javafx.geometry.Point2D;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * The class implements Paddle extend the Elements class.
 * The class instance will be created using the constructor.
 */

public class Paddle extends Elements implements BoardSize {

    /**
     * A constructor uses the Shape class to create appropriate shapes.
     * We create a rectangle using the Rectangle class. We give its size as argument.
     * By using the ImagePattern class, which takes an Image class object as an argument, we give the elements a specific graphic appearance.
     * In addition, with the help of setters the Elements class, we set the position of the racket and the speed at which it moves.
     */

    public Paddle() {
        int rectangleLength = 110;
        int rectangleHeight = 30;
        Shape paddleShape = new Rectangle(rectangleLength, rectangleHeight, new ImagePattern(new Image("pink-blue-neon.png")));

        setShape(paddleShape);

        setPosition(
                new Point2D(275, HEIGHT - rectangleHeight)
        );
        setDefaultSpeed(5);
    }
}
