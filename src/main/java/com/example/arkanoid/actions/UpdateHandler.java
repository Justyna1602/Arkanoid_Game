package com.example.arkanoid.actions;

import com.example.arkanoid.game.BoardSize;
import com.example.arkanoid.game.GameArea;
import com.example.arkanoid.elements.Ball;
import com.example.arkanoid.elements.Brick;
import com.example.arkanoid.elements.Elements;
import com.example.arkanoid.elements.Paddle;

import javafx.collections.ObservableList;

import javafx.event.EventHandler;
import javafx.geometry.BoundingBox;
import javafx.geometry.Bounds;
import javafx.geometry.Point2D;

import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;

import java.util.Iterator;
import java.util.Set;

/**
 * The class implements methods for initializing any game events.
 * For events entered from the keyboard as well as resulting from the interaction of elements.
 */

public class UpdateHandler implements BoardSize {
    EventHandler<MouseEvent> mouseEvent;
    private final GameArea gameArea;
    private final Bounds wallBounds;
    private final Point2D paddleRightVelocity;
    private final Point2D paddleLeftVelocity;
    private static final Point2D LEFT_VECTOR = new Point2D(-1, 0);
    private static final Point2D RIGHT_VECTOR = new Point2D(1, 0);
    private static final Point2D UP_VECTOR = new Point2D(0, 1);
    private static final Point2D DOWN_VECTOR = new Point2D(0, -1);

    /**
     * Method to update all items.
     *
     * @param now
     * @param activeKeys Represents a set of events.
     */

    public void update(long now, Set<KeyCode> activeKeys) {
        applyInputToPaddle(activeKeys);

        updateElementsPositions();

        restrictPaddleMovementByWalls();
        restrictBallMovementByWalls();

        handlePaddleCollision();
        handleBrickCollision();
    }

    public UpdateHandler(GameArea gameArea) {
        this.gameArea = gameArea;

        Ball ball = new Ball();
        wallBounds = new BoundingBox(0, 35, WIDTH, HEIGHT - ball.getR() * 2);

        double defaultPaddleSpeed = gameArea.getPaddle().getDefaultSpeed();
        paddleLeftVelocity = LEFT_VECTOR.multiply(defaultPaddleSpeed);
        paddleRightVelocity = RIGHT_VECTOR.multiply(defaultPaddleSpeed);
    }

    /**
     * The method describes the behavior of the racket under the influence of events entered from the keyboard.
     * The paddle moves due to the arrows and the D and A keys.
     *
     * @param activeKeys Represents a set of events.
     */

    private void applyInputToPaddle(Set<KeyCode> activeKeys) {

        Point2D paddleVelocity = Point2D.ZERO;

        if (activeKeys.contains(KeyCode.LEFT) || activeKeys.contains(KeyCode.A)) {
            paddleVelocity = paddleVelocity.add(paddleLeftVelocity);
        }

        if (activeKeys.contains(KeyCode.RIGHT) || activeKeys.contains(KeyCode.D)) {
            paddleVelocity = paddleVelocity.add(paddleRightVelocity);
        }

        gameArea.getPaddle().setVelocity(paddleVelocity);
    }


    /**
     * The method represents the reactions of the racket's interaction with the Scene's boundary.
     */

    private void restrictPaddleMovementByWalls() {
        Paddle paddle = gameArea.getPaddle();
        double paddleWidth = paddle.getShape().getLayoutBounds().getWidth();

        if (paddle.getPosition().getX() < wallBounds.getMinX()) {
            paddle.setVelocity(Point2D.ZERO);
            paddle.setPosition(new Point2D(wallBounds.getMinX(), paddle.getPosition().getY()));
        }

        if (paddle.getPosition().getX() + paddleWidth > wallBounds.getMaxX()) {
            paddle.setVelocity(Point2D.ZERO);
            paddle.setPosition(new Point2D(wallBounds.getMaxX() - paddleWidth, paddle.getPosition().getY()));
        }
    }

    /**
     * The method represents the reaction of the ball's interaction with the stage line.
     * Additionally, that method ending the game by setting the setGameOver method to true.
     */

    private void restrictBallMovementByWalls() {
        int x = 0;

        Ball ball = gameArea.getBall();
        if (ball.getPosition().getX() <= wallBounds.getMinX()) {
            ball.setVelocity(
                    reflect(ball.getVelocity(), RIGHT_VECTOR)
            );
        }

        if (ball.getPosition().getX() + ball.getShape().getLayoutBounds().getWidth() >= wallBounds.getMaxX()) {
            ball.setVelocity(
                    reflect(ball.getVelocity(), LEFT_VECTOR)
            );
        }

        if (ball.getPosition().getY() <= wallBounds.getMinY()) {
            ball.setVelocity(
                    reflect(ball.getVelocity(), DOWN_VECTOR)
            );
        }

        if (ball.getPosition().getY() + ball.getShape().getLayoutBounds().getHeight() >= wallBounds.getMaxY()) {
            gameArea.setGameOver(true);
        }
    }

    /**
     * The method describes the interaction of the Racket with the ball.
     */

    private void handlePaddleCollision() {
        Ball ball = gameArea.getBall();
        if (gameArea.getBall().intersects(gameArea.getPaddle())) {
            ball.setVelocity(
                    reflect(ball.getVelocity(), UP_VECTOR)
            );
        }
    }

    /**
     * The method describes the interaction between the Ball and the Brick.
     *   The brick is removed with one touch of the Ball with the Iterator.
     */

    private void handleBrickCollision() {
        Ball ball = gameArea.getBall();
        ObservableList<Brick> bricks = gameArea.getBricks();
        for (Iterator<Brick> brickIterator = bricks.iterator(); brickIterator.hasNext(); ) {
            Brick brick = brickIterator.next();
            if (ball.intersects(brick)) {
                brickIterator.remove();
                ball.setVelocity(
                        reflect(ball.getVelocity(), DOWN_VECTOR)
                );
            }
        }
    }

    /**
     * The method updates the position of all items.
     */

    private void updateElementsPositions() {
        gameArea.getAllElements().forEach(
                Elements::applyVelocity
        );
    }

    /**
     * A method that sets the ball's bounce vector.
     * It takes two points as parameters:
     * @param vector
     * @param normal
     * @return Returns a new point as an instance of the Point2D Class.
     */

    private Point2D reflect(Point2D vector, Point2D normal) {
        return vector.subtract(normal.multiply(vector.dotProduct(normal) * 2));
    }

}
