package pong;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class PauseScreen extends GameScene{

    public PauseScreen(Stage stage, Orchestrator orchestrator){
        super(stage, orchestrator);

        // select the layout
        GridPane gridPane = new GridPane();
        gridPane.setVgap(50);
        gridPane.setAlignment(Pos.CENTER);

        // create the content
        Text txtTitle = new Text("PAUSED");
        Text txtInstructions = new Text("Use the MOUSE, W & S, or Up & Down to move\n " +
                "First one to get " + Constants._Goal_Area + " points wins \n" +
                "Select Play to start \n" +
                "Press ESCAPE to pause \n" +
                "Select Exit to return to main menu");
        txtTitle.getStyleClass().add("title");
        txtTitle.setTextAlignment(TextAlignment.CENTER);
        txtInstructions.setTextAlignment(TextAlignment.CENTER);
        txtTitle.setFont(new Font(50));

        // create buttons
        Button btnPlay = new Button("Resume");
        Button btnQuit = new Button("Exit");
        HBox hBox = new HBox(btnPlay, btnQuit);
        hBox.setSpacing(50);
        hBox.setAlignment(Pos.BASELINE_CENTER);

        // quit the game
        btnQuit.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                orchestrator.pong.reset();
                orchestrator.setState(GameState.START);
            }
        });

        // play the game
        btnPlay.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                orchestrator.setState(GameState.PLAYING);
            }
        });

        // set the layout
        gridPane.addRow(0, txtTitle);
        gridPane.addRow(1, txtInstructions);
        gridPane.addRow(2, hBox);

        // Create the scene
        scene = new Scene(gridPane, 300, 300);

        // Add the scene to the stage
        this.stage.show();
    }

    // Show the the game and start playing it
    public void start(){
        this.stage.setScene(scene);
        this.stage.show();
        this.animationTimer.start();
    }

    public void pause(){
        this.stage.hide();
        this.animationTimer.stop();
    }


}
