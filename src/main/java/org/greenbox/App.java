package org.greenbox;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;


/**
 * JavaFX App
 */
public class App extends Application {
    private Game game;
    @Override
    public void init() throws Exception {
        this.game = new Game();
    }

    @Override
    public void start(Stage stage) {
        BorderPane layout = new BorderPane();

        Label gameState = new Label(game.getGameState());
        gameState.setFont(Font.font("Monospaced", 40));
        layout.setTop(gameState);

        GridPane board = new GridPane();
        board.setPadding(new Insets(10));
        board.setAlignment(Pos.CENTER);

        List<Button> grids = new ArrayList<>();
        for (int y = 0; y < game.getSize(); y++) {
            for (int x = 0; x < game.getSize(); x++) {
                Button button = new Button(" ");
                button.setFont(Font.font("Monospaced", 40));
                board.add(button, x, y);
                grids.add(button);

                int finY = y;
                int finX = x;
                button.setOnMouseClicked((event) -> {
                    if (game.makeMove(finX, finY)) {
                        button.setText(game.getBoardElem(finX, finY));
                        gameState.setText(game.getGameState());
                    }
                });
            }
        }

        layout.setCenter(board);

        Button resetButton = new Button("Reset");
        resetButton.setOnMouseClicked((event) -> {
            game.initGame();
            gameState.setText(game.getGameState());
            grids.forEach((button -> button.setText(" ")));
        });
        layout.setBottom(resetButton);
        layout.setAlignment(resetButton, Pos.CENTER);

        Scene view = new Scene(layout);
        stage.setScene(view);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}