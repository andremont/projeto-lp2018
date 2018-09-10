package ismt.application.scene;

import java.io.File;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class RulesScene {
	final int APP_WIDTH = 910;
	final int APP_HEIGHT = 600;
	final int BUTTON_SIZE = 100;
	final int GAP_SIZE = 10;
	final String resourceFolder = new File("resource").toURI().toString();
	Scene rulesMemorize, rulesBlackJack,
		  rulesPoker,  rulesSueca, rulesBurro, rulesPeixinho, rulesSolitaire;
	public static void main(String[] args) {

		launch(args);
	}
	public void start(Stage primaryStage) {
		rulesStage.setTitle("LP Card Games");

		// Build individual screens
		
		buildRulesScene(primaryStage);
	}
	public Scene buildPlayScene(Stage primaryStage, Scene sceneMain) {

		EventHandler<ActionEvent> buttonBackhandler = new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				primaryStage.setScene(rulesScene);
				rulesMemorize = new MemorizeScene().buildPlayScene(primaryStage, rulesScene);
				rulesBlackJack = new BlackJackScene().buildPlayScene(primaryStage, rulesScene);	
				rulesPoker = new PokerScene().buildPlayScene(primaryStage, rulesScene);
				rulesSueca = new SuecaScene().buildPlayScene(primaryStage, rulesScene); 
				rulesBurro = new BurroScene().buildPlayScene(primaryStage, rulesScene);
				rulesPeixinho = new PeixinhoScene().buildPlayScene(primaryStage, rulesScene);
				rulesSolitaire = new SolitaireScene().buildPlayScene(primaryStage, rulesScene);	
				
			
				Stage.setScene(rulesScene);
				
				// Show application!
				primaryStage.show();
			}
			public void buildRulesScene(Stage rulesStage) {
				// Scene main menu
				GridPane grid2 = new GridPane();
				grid2.setAlignment(Pos.CENTER);
				grid2.setHgap(GAP_SIZE);

				Text scenetitle2 = new Text("Main menu");
				scenetitle2.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
				grid2.add(scenetitle2, 0, 0, 2, 1);

				Button buttonMemorize = new Button("Memorize");
				Button buttonBlackJack = new Button("Blackjack");
				Button buttonPoker = new Button("Poker");
				Button buttonBurro = new Button("Burro");
				Button buttonSueca = new Button("Sueca");
				Button buttonPeixinho = new Button("Peixinho");
				Button buttonSolitaire = new Button("Solitaire");
				
				buttonMemorize.setMaxWidth(BUTTON_SIZE);
				buttonBlackJack.setMaxWidth(BUTTON_SIZE);
				buttonPoker.setMaxWidth(BUTTON_SIZE);
				buttonBurro.setMaxWidth(BUTTON_SIZE);
				buttonSueca.setMaxWidth(BUTTON_SIZE);
				buttonPeixinho.setMaxWidth(BUTTON_SIZE);
				buttonSolitaire.setMaxWidth(BUTTON_SIZE);

				
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
						if (e.getSource() == buttonMemorize)
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
			
		}
		Scene tempScene = new Scene(createContent(buttonBackhandler));

		
		
	
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
		
		JSONObject obj = JSONUtils.getJSONObjectFromFile("/obj.json");
		String[] names = JSONObject.getNames(obj);
		for(String string : names) {
			System.out.println(string + ": " + obj.get(string));
		}
		
		System.out.print("\n");
		
		JSONArray jsonArray = obj.getJSONArray("Array");
		for(int i = 0; i < jsonArray.length(); i++) {
			System.out.println(jsonArray.get(i));
		}
		
		System.out.print("\n");
		
		int number = obj.getInt("Number");
		System.out.println(number);
		
		System.out.print("\n");
		
		String string = obj.getString("String");
		System.out.println(string);
		
		System.out.print("\n");
		
		boolean bool = obj.getBoolean("Boolean");
		System.out.println(bool);
		
	}


}