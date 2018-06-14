package ismt.application.engine;

import ismt.application.scene.*;
import static javafx.geometry.HPos.RIGHT;
import java.io.File;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class Main extends Application {

	final int APP_WIDTH = 910;
	final int APP_HEIGHT = 600;
	final int BUTTON_SIZE = 100;
	final int GAP_SIZE = 10;
	final String resourceFolder = new File("resource").toURI().toString();
	Scene sceneLogin, sceneMain, 
		  sceneMemorize, sceneBlackJack,
		  sceneViewStats, sceneManagePlayers, sceneViewRules,
		  sceneViewPoker,  sceneViewSueca, sceneViewBurro, sceneViewPeixinho, sceneViewSolitaire;

	public static void main(String[] args) {

		launch(args);
	}

	@Override
	public void start(Stage primaryStage) {
		primaryStage.setTitle("LP Card Games");

		// Build individual screens
		buildLoginScene(primaryStage);
		buildMainScene(primaryStage);

		// Build games' screens
		sceneMemorize = new MemorizeScene().buildPlayScene(primaryStage, sceneMain);
		sceneBlackJack = new BlackJackScene().buildPlayScene(primaryStage, sceneMain);	
		sceneManagePlayers = new PlayersScene().buildPlayScene(primaryStage, sceneMain);
		sceneViewStats = new StatsScene().buildPlayScene(primaryStage, sceneMain);
		sceneViewRules = new RulesScene().buildPlayScene(primaryStage, sceneMain);
		sceneViewPoker = new PokerScene().buildPlayScene(primaryStage, sceneMain);
		sceneViewSueca = new SuecaScene().buildPlayScene(primaryStage, sceneMain); 
		sceneViewBurro = new BurroScene().buildPlayScene(primaryStage, sceneMain);
		sceneViewPeixinho = new PeixinhoScene().buildPlayScene(primaryStage, sceneMain);
		sceneViewSolitaire = new SolitaireScene().buildPlayScene(primaryStage, sceneMain);	
		
		// Set initial scene for login
		primaryStage.setScene(sceneLogin);
		
		// Show application!
		primaryStage.show();
	}

	public void buildLoginScene(Stage primaryStage) {
		// Scene login
		GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(GAP_SIZE);
		grid.setVgap(GAP_SIZE);
		grid.setPadding(new Insets(25, 25, 25, 25));

		Text scenetitle = new Text("Login page");
		scenetitle.setFill(Color.web("#0076a3"));
		scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid.add(scenetitle, 0, 0, 2, 1);

		Text userName = new Text("User Name:");
		userName.setFill(Color.web("#0076a3"));
		grid.add(userName, 0, 1);

		TextField userTextField = new TextField();
		grid.add(userTextField, 1, 1);

		Text pw = new Text("Password:");
		pw.setFill(Color.web("#0076a3"));
		grid.add(pw, 0, 2);

		PasswordField pwBox = new PasswordField();
		grid.add(pwBox, 1, 2);

		Button buttonLogin = new Button("Sign in");
		HBox hbBtn = new HBox(BUTTON_SIZE);
		hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtn.getChildren().add(buttonLogin);
		grid.add(hbBtn, 1, 4);
		
		Button buttonExit = new Button("Quit");
		HBox hbBtnBottom = new HBox(BUTTON_SIZE);
		hbBtnBottom.setAlignment(Pos.BOTTOM_RIGHT);
		hbBtnBottom.getChildren().add(buttonExit);
		grid.add(hbBtnBottom, 2, 35);

		final Text actiontarget = new Text();
		grid.add(actiontarget, 0, 6);
		GridPane.setColumnSpan(actiontarget, 2);
		GridPane.setHalignment(actiontarget, RIGHT);
		actiontarget.setId("actiontarget");

		sceneLogin = new Scene(grid, APP_WIDTH, APP_HEIGHT);
		sceneLogin.getStylesheets().add(resourceFolder + "/login.css");

		// Set button login action
		buttonLogin.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if(Utils.validateUser(userTextField.getText(), pwBox.getText())){
					// Save user credentials into Player class
					Context.getInstance().setPlayer(userTextField.getText());
					actiontarget.setText("Correct!");
					primaryStage.setScene(sceneMain);
				}
				else{
					actiontarget.setFill(Color.FIREBRICK);
					actiontarget.setText("Wrong credentials: " + userName.getText() + "|" + pw.getText());
				}
			}
		});
		
		// Set exit button action
		buttonExit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) { 
				primaryStage.close();
			}
		});
	
	}

	public void buildMainScene(Stage primaryStage) {
		// Scene main menu
		GridPane grid2 = new GridPane();
		grid2.setAlignment(Pos.CENTER);
		grid2.setHgap(GAP_SIZE);

		Text scenetitle2 = new Text("Main menu");
		scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		grid2.add(scenetitle2, 0, 0, 2, 1);

		Button buttonMemorize = new Button("Play Memorize");
		Button buttonBlackJack = new Button("Play Blackjack");
		Button buttonPoker = new Button("Play Poker");
		Button buttonBurro = new Button("Play Burro");
		Button buttonSueca = new Button("Play Sueca");
		Button buttonPeixinho = new Button("Play Peixinho");
		Button buttonSolitaire = new Button("Play Solitaire");
		Button buttonStats = new Button("View stats");
		Button buttonPlayers = new Button("Manage players");
		Button buttonRules = new Button("View rules");
		Button buttonLogout = new Button("Logout");
		
		buttonMemorize.setMaxWidth(BUTTON_SIZE);
		buttonBlackJack.setMaxWidth(BUTTON_SIZE);
		buttonPoker.setMaxWidth(BUTTON_SIZE);
		buttonBurro.setMaxWidth(BUTTON_SIZE);
		buttonSueca.setMaxWidth(BUTTON_SIZE);
		buttonPeixinho.setMaxWidth(BUTTON_SIZE);
		buttonSolitaire.setMaxWidth(BUTTON_SIZE);
		buttonStats.setMaxWidth(BUTTON_SIZE);
		buttonPlayers.setMaxWidth(BUTTON_SIZE);
		buttonRules.setMaxWidth(BUTTON_SIZE);
		buttonLogout.setMaxWidth(BUTTON_SIZE);
		buttonLogout.getStyleClass().add("logout");
		
		VBox vbBtn = new VBox(BUTTON_SIZE);
		vbBtn.setAlignment(Pos.BASELINE_CENTER);
		vbBtn.setSpacing(GAP_SIZE);
		vbBtn.setPadding(new Insets(0, 20, 10, 20)); 
		vbBtn.getChildren().addAll(buttonMemorize, buttonBlackJack, buttonPoker, buttonBurro, buttonPlayers, buttonSueca, buttonPeixinho, buttonSolitaire, buttonStats, buttonRules, buttonLogout);

		grid2.add(vbBtn, 1, 1);

		sceneMain = new Scene(grid2, APP_WIDTH, APP_HEIGHT);
		sceneMain.getStylesheets().add(resourceFolder + "/style.css");

		// Set buttons action
		EventHandler<ActionEvent> buttonClickhandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				if (e.getSource() == buttonLogout)
					primaryStage.setScene(sceneLogin);
				else if (e.getSource() == buttonMemorize)
					primaryStage.setScene(sceneMemorize);
				else if (e.getSource() == buttonBlackJack)
					primaryStage.setScene(sceneBlackJack);
				else if (e.getSource() == buttonPoker)
					primaryStage.setScene(sceneViewPoker);
				else if (e.getSource() == buttonBurro)
					primaryStage.setScene(sceneViewBurro);
				else if (e.getSource() == buttonSueca)
					primaryStage.setScene(sceneViewSueca);
				else if (e.getSource() == buttonPeixinho)
					primaryStage.setScene(sceneViewPeixinho);
				else if (e.getSource() == buttonSolitaire)
					primaryStage.setScene(sceneViewSolitaire);
				else if (e.getSource() == buttonStats)
					primaryStage.setScene(sceneViewStats);
				else if (e.getSource() == buttonPlayers)
					primaryStage.setScene(sceneManagePlayers);
				else if (e.getSource() == buttonRules)
					primaryStage.setScene(sceneViewRules);
				else
					primaryStage.setScene(sceneMain);
			}
		};

		// Associate event handler to buttons
		for(Object obj : vbBtn.getChildren().toArray())
		{
			if (obj.getClass().getTypeName() == "javafx.scene.control.Button")
				((Button)obj).setOnAction(buttonClickhandler);
		}
	}
}
