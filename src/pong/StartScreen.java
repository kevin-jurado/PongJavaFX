package pong;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class StartScreen extends GameScene{

    // TODO start, quit
    public StartScreen(Stage stage, Orchestrator orchestrator){
        super(stage, orchestrator);

        // select the layout
        GridPane gridPane = new GridPane();
        gridPane.setVgap(50);

        // create a group object
        Group grpButton = new Group();

        // create the content
        Text title = new Text("P O N G");
        Text instructions = new Text("Use W & S or Up & Down to move\n " +
                "Select Play to start \n" +
                "Press Space Bar to pause"+
                "Press Q to quit");
        title.getStyleClass().add("title");
        title.setTextAlignment(TextAlignment.CENTER);
        instructions.setTextAlignment(TextAlignment.CENTER);

        // create buttons
        Button btnPlay = new Button("Play");
        Button btnQuit = new Button("Quit");

        // quit the game
        btnQuit.setOnAction(e->Platform.exit());

        // set the layout
        gridPane.addRow(0, title);
        gridPane.addRow(1, instructions);
        gridPane.addRow(2, btnPlay, btnQuit);

        // Create the scene
        Scene scene = new Scene(gridPane, 300, 300);

        // Add the scene to the stage
        this.stage.setScene(scene);
        this.stage.show();
    }

    // Show the the game and start playing it
    public void start(){
        this.stage.show();
        this.animationTimer.start();
    }

    public void pause(){
        this.stage.hide();
        this.animationTimer.stop();
    }

    private void handleUserInput(KeyEvent e){
        KeyCode key = e.getCode();
        // Keyboard controls
        if(key == KeyCode.Q){
            System.out.println("Quit here");
        }
    }
}
