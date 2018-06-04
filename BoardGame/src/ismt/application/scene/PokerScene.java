package ismt.application.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;

import java.util.HashMap;
import java.util.Random;

import ismt.application.engine.Card;
import ismt.application.engine.CardDeck;
import ismt.application.engine.CardGame;
import ismt.application.engine.ImageStore;
import ismt.application.engine.Suit;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PokerScene extends CardGame {
	
	final String imageFolder = resourceFolder + "/playing_cards_images/";
	private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
	private Text message = new Text();

	private ObservableList<Node> playerHand, player2Hand, player3Hand, player4Hand, floopCards, turnCards, riverCards, burnCard;

	private HBox player = new HBox(5);
	
	private VBox player2 = new VBox(5);
	private HBox player3 = new HBox(5);
	private VBox player4 = new VBox(5);
	private HBox flop = new HBox(5);
	private HBox turn = new HBox(5);
	private HBox river = new HBox(5);

	private int dealer = 0;

	public Scene buildPlayScene(Stage primaryStage, Scene sceneMain) {
		
		ImageStore.card_back_image = new Image(imageFolder + "card_back.png");
		ImageStore.card_face_images = new HashMap<String, Image>();
		
		for (int suit_index = 0; suit_index < Suit.values().length; suit_index++) {
			for (int card_rank = 1; card_rank < 14; card_rank++) {
				String image_file_name = imageFolder + Suit.values()[suit_index] + card_rank + ".png";
				Image card_faceup_image = new Image(image_file_name);
				String key_for_image = Suit.values()[suit_index].toString() + card_rank;
				ImageStore.card_face_images.put(key_for_image, card_faceup_image);
			}
		}
		card_deck = new CardDeck();

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

		playerHand = player.getChildren();
		player2Hand = player2.getChildren();
		player3Hand = player3.getChildren();
		player4Hand = player4.getChildren();
		floopCards = flop.getChildren();
		turnCards = turn.getChildren();
		riverCards = river.getChildren();

		Pane root = new Pane();
		root.setPrefSize(1415, 770);

		Region background = new Region();
		background.setPrefSize(1415, 770);
		background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

		HBox rootLayout = new HBox(5);
		rootLayout.setPadding(new Insets(5, 5, 5, 5));
		Rectangle leftBG = new Rectangle(1170, 760);
		leftBG.setArcWidth(50);
		leftBG.setArcHeight(50);
		leftBG.setFill(Color.GREEN);
		Rectangle rightBG = new Rectangle(230, 760);
		rightBG.setArcWidth(50);
		rightBG.setArcHeight(50);
		rightBG.setFill(Color.ORANGE);

		// LEFT
		HBox mainBox = new HBox(25);
		VBox leftVBox = new VBox(50);
		HBox table = new HBox (20);
		mainBox.setAlignment(Pos.CENTER);
		player.setAlignment(Pos.CENTER);
		player2.setAlignment(Pos.CENTER);
		player3.setAlignment(Pos.CENTER);
		player4.setAlignment(Pos.CENTER);
		leftVBox.setAlignment(Pos.CENTER);
		//Text dealerScore = new Text("Dealer: ");
		//Text playerScore = new Text("Player: ");
		table.getChildren().addAll( flop, turn, river);
		leftVBox.getChildren().addAll( player3, table, player);
		mainBox.getChildren().addAll(player2, leftVBox, player4);

		// RIGHT
		VBox rightVBox = new VBox();
		rightVBox.setAlignment(Pos.CENTER);

		final TextField bet = new TextField("Bet");
		bet.setDisable(true);
		bet.setMaxWidth(50);
		Text money = new Text("Money");

		Button btnPlay = new Button("Play");
		Button btnHit = new Button("Hit");
		Button btnStand = new Button("Stand");
		Button buttonBack = new Button("Back");
		
		HBox buttonsHBox = new HBox(15, btnHit, btnStand, buttonBack);
		buttonsHBox.setAlignment(Pos.CENTER);
		rightVBox.getChildren().addAll(bet, btnPlay, money, buttonsHBox);

		// ADD BOTH STACKS TO ROOT LAYOUT
		
		
		rootLayout.getChildren().addAll(new StackPane(leftBG, mainBox), new StackPane(rightBG, rightVBox));
		root.getChildren().addAll(background, rootLayout);

		// BIND PROPERTIES
		btnPlay.disableProperty().bind(playable);
		btnHit.disableProperty().bind(playable.not());
		btnStand.disableProperty().bind(playable.not());

		
        
       

       

		// INIT BUTTONS
		btnPlay.setOnAction(event -> {
			startNewGame();
		});

		btnHit.setOnAction(event -> {
			
		});

		btnStand.setOnAction(event -> {
			
			
			endGame();
		});
		buttonBack.setOnAction(buttonBackhandler);
		
		return root;
	
	}

	private void startNewGame() {

		playable.set(true);
		message.setText("New game started");

		
		card_deck.shuffle();

		playerHand.clear();
		player2Hand.clear();
		player3Hand.clear();
		player4Hand.clear();
		floopCards.clear();
		//burnCard.clear();

		setDealer();

		for (int i = 0; i < 2; i++) {
			takeCard(card_deck.draw_card(), playerHand, true);
			takeCard(card_deck.draw_card(), player2Hand, false);
			takeCard(card_deck.draw_card(), player3Hand, false);
			takeCard(card_deck.draw_card(), player4Hand, false);
		}

		//takeCard(card_deck.draw_card(), burnCard);

		for (int i = 0; i < 3; i++) {
			takeCard(card_deck.draw_card(), floopCards, false);
		}
		
		takeCard(card_deck.draw_card(), turnCards, false);
		takeCard(card_deck.draw_card(), riverCards, false);

	}

	private void setDealer() {

		Random random = new Random();
		dealer = random.nextInt(4) + 1;
	}

	private void endGame() {

	}

	public void takeCard(Card card, ObservableList<Node> cardHand, boolean up) {
		if (up == true) {
			card.turn_card();
		}
		cardHand.add(card);
	}

	@Override
	public void deal() {
		// TODO Auto-generated method stub

	}

	@Override
	public void shuffle() {
		// TODO Auto-generated method stub

	}

}
