package com.example.arkanoid.game;

import com.example.arkanoid.elements.Ball;
import com.example.arkanoid.elements.Brick;
import com.example.arkanoid.elements.Elements;
import com.example.arkanoid.elements.Paddle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.geometry.Point2D;

import javafx.scene.shape.Shape;

import java.util.ArrayList;
import java.util.List;

/**
 * A class that implements the game area.
 */

public class GameArea {
    private ObservableList<Brick> bricks;
    private Ball ball;
    private Paddle paddle;
    private boolean gameOver;

    public GameArea() {
        init();
    }

    /**
     * The method initializes all elements of the game.
     * In the case of Bricks initialization, the FXCollections class was used. This allows to track changes that appear on that elements.
     * Using the implemented variables, we set the arrangement of bricks from the side and the end of the Scene.
     */

    public void init() {
        ball = new Ball();
        paddle = new Paddle();
        bricks = FXCollections.observableArrayList();
        int numColumns = 5;
        int numRows = 4;
        double space = 5.0;
        double yOffset = 6.0;

        for (int x = 0; x < numColumns; x++) {
            for (int y = 0; y < numRows; y++) {
                Brick brick = new Brick();
                Shape brickShape = brick.getShape();

                double xPos =
                        x * (brickShape.getLayoutBounds().getWidth() + space) + 5;
                double yPos =
                        y * (brickShape.getLayoutBounds().getHeight() + space) + yOffset + 10;
                Point2D pos = new Point2D(xPos, yPos);

                brick.setPosition(pos);

                bricks.add(brick);
            }
        }
    }

    public ObservableList<Brick> getBricks() {
        return bricks;
    }

    public Ball getBall() {
        return ball;
    }

    public Paddle getPaddle() {
        return paddle;
    }


    public List<Elements> getAllElements() {
        List<Elements> allElements = new ArrayList<>(bricks);
        allElements.add(ball);
        allElements.add(paddle);

        return allElements;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean gameOver) {
        this.gameOver = gameOver;
    }
}
