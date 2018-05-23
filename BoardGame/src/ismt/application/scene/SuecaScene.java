package ismt.application.scene;

import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class SuecaScene {

	
	
	public Scene buildPlayScene(Stage primaryStage, Scene sceneMain) {

		EventHandler<ActionEvent> buttonBackhandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				primaryStage.setScene(sceneMain);
			}
		};
		Scene tempScene = new Scene(createContent(buttonBackhandler));

		return tempScene;
	}

	
	private Parent createContent(EventHandler<ActionEvent> buttonBackhandler) {
		

		Pane root = new Pane();
		root.setPrefSize(800, 600);

		Region background = new Region();
		background.setPrefSize(800, 600);
		background.setStyle("-fx-background-color: rgba(111, 111, 111, 1)");
		
		/* TODO */
		
		Button buttonBack = new Button("Back");
		buttonBack.setOnAction(buttonBackhandler);
		
		HBox buttonsHBox = new HBox(15, buttonBack);
		buttonsHBox.setAlignment(Pos.CENTER);
		root.getChildren().addAll(background, buttonsHBox);
		
	
		return root;
	}

	
}

