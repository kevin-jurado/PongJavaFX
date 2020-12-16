package pong;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class StartScreen extends GameScene{

    // TODO start, quit
    public StartScreen(Stage stage, Orchestrator orchestrator) {
        super(stage, orchestrator);

        // select the layout
        GridPane gridPane = new GridPane();
        gridPane.setHgap(50);
        gridPane.setVgap(50);

        // create the content
        Text title = new Text("P O N G");
        Text instructions = new Text("Use W & S or Up & Down to move\n " +
                "Press N to start \n" +
                "Press Space Bar to pause"+
                "Press ESC to quit");

        gc.setTextAlign(TextAlignment.CENTER);
        gc.fillText(
                title.toString(),
                200, 50
        );
        gc.fillText(
                instructions.toString(),
                600, 50
        );

        // create buttons
        Button btnPlay = new Button("Play");
        Button btnQuit = new Button("Quit");

        // quit the game
        btnQuit.setOnAction(e->Platform.exit());

        // set the layout
        gridPane.addRow(0, title);
        gridPane.addRow(0, instructions);
        gridPane.addRow(0, btnPlay, btnQuit);

    }

    public void start(){}
    public void pause(){}
    public void update(){}
}
