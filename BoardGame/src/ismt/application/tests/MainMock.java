package ismt.application.tests;

import java.io.File;

import ismt.application.engine.CardGame;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainMock extends Application {

	final int APP_WIDTH = 910;
	final int APP_HEIGHT = 600;
	final int BUTTON_SIZE = 100;
	final int GAP_SIZE = 10;
	final String resourceFolder = new File("resource").toURI().toString();
	Scene sceneMain;
	CardGame game;

	public MainMock(CardGame myGame){
		this.game = myGame;
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("LP Card Games");
		// Set initial scene for login
		sceneMain = game.buildPlayScene(primaryStage, sceneMain);
		primaryStage.setScene(sceneMain);
		
		// Show application!
		primaryStage.show();
	}
}
