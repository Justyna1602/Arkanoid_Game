package com.example.arkanoid;

import com.example.arkanoid.actions.InputHandler;
import com.example.arkanoid.actions.UpdateHandler;
import com.example.arkanoid.elements.Brick;
import com.example.arkanoid.elements.Elements;
import com.example.arkanoid.game.BoardSize;
import com.example.arkanoid.game.GameArea;

import javafx.animation.AnimationTimer;

import javafx.application.Application;

import javafx.collections.ListChangeListener;

import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.stream.Collectors;

/**
 * The starting class of the game.
 */

public class ArkanoidApp extends Application implements BoardSize {
    final private GameArea gameArea = new GameArea();
    final private InputHandler inputHandler = new InputHandler();
    final private UpdateHandler updateHandler = new UpdateHandler(gameArea);
    private static final BackgroundImage BACKGROUND_IMAGE = new BackgroundImage(
            new Image("background.jpg", WIDTH, HEIGHT, false, true),
            BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
            BackgroundSize.DEFAULT);


    @Override
    public void start(Stage window) {

        Scene gameScene = createGameScene();
        gameScene.setOnKeyPressed(inputHandler);
        gameScene.setOnKeyReleased(inputHandler);
        AnimationTimer gameLoop = createGameLoop();

        window.setTitle("Arkanoid Game");
        window.setScene(gameScene);
        window.setResizable(false);
        window.show();

        gameLoop.start();


    }

    public Scene createGameScene() {
        Pane canvas = new Pane();
        canvas.setPrefSize(WIDTH, HEIGHT);
        canvas.setBackground(new Background(BACKGROUND_IMAGE));


        canvas.getChildren().addAll(
                gameArea.getAllElements().stream()
                        .map(Elements::getShape)
                        .collect(Collectors.toList())
        );

        gameArea.getBricks().addListener((ListChangeListener<Brick>) change -> {
            while (change.next()) {
                if (change.wasRemoved()) {
                    canvas.getChildren().removeAll(
                            change.getRemoved().stream()
                                    .map(Brick::getShape)
                                    .collect(Collectors.toList())
                    );
                }
            }
            if (gameArea.getBricks().isEmpty()) {
                gameArea.setGameOver(true);
                Text youWin = new Text(150.0, 375.0, "You Win!");
                youWin.setFont(new Font("Serif", 72));
                youWin.setStroke(Color.BLUE);
                youWin.setFill(Color.PINK);
                canvas.getChildren().addAll(youWin);
            }
        });

        Scene scene = new Scene(canvas);
        return scene;
    }


    private AnimationTimer createGameLoop() {

        return new AnimationTimer() {
            public void handle(long now) {
                updateHandler.update(now, inputHandler.getActiveKeys());

                if (!gameArea.isGameOver()) {
                    return;
                }
                this.stop();
            }
        };
    }


    public static void main(String[] args) {
        launch(args);
    }
}