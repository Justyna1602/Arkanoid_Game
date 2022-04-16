package com.example.arkanoid.elements;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

/**
 * The class implements Brick extend the Elements class.
 * The class instance will be created using the constructor.
 */

public class Brick extends Elements {

    /**
     * A constructor uses the Shape class to create appropriate shapes.
     * We create a rectangle using the Rectangle class. We give its size as arguments.
     * By using the ImagePattern class, which takes an Image class object as an argument, we give the elements a specific graphic appearance.
     */

    public Brick() {
        Shape brickShape = new Rectangle(120, 30, new ImagePattern(new Image("160356168-.png")));

        setShape(brickShape);
    }
}
