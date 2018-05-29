package ismt.application.scene;

import ismt.application.engine.Card;
import ismt.application.engine.CardDeck;
import ismt.application.engine.CardGame;
import ismt.application.engine.Player;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class BlackJackScene extends CardGame {

	private int acesPlayer = 0, acesDealer = 0;
	public SimpleIntegerProperty pointsPlayer = new SimpleIntegerProperty(0), pointsDealer = new SimpleIntegerProperty(0);
	private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
	public ObservableList<Node> dealerHand, playerHand;
	private Text message = new Text();
	private HBox dealerCards = new HBox(20);
	private HBox playerCards = new HBox(20);
	
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

	@Override
	public void deal() {
		card_deck = new CardDeck();
	}

	@Override
	public void shuffle() {
		card_deck.shuffle();
		deal();
	}

	private Parent createContent(EventHandler<ActionEvent> buttonBackhandler) {
		dealerHand = dealerCards.getChildren();
		playerHand = playerCards.getChildren();

		Pane root = new Pane();
		root.setPrefSize(800, 600);

		Region background = new Region();
		background.setPrefSize(800, 600);
		background.setStyle("-fx-background-color: rgba(0, 0, 0, 1)");

		HBox rootLayout = new HBox(5);
		rootLayout.setPadding(new Insets(5, 5, 5, 5));
		Rectangle leftBG = new Rectangle(550, 560);
		leftBG.setArcWidth(50);
		leftBG.setArcHeight(50);
		leftBG.setFill(Color.GREEN);
		Rectangle rightBG = new Rectangle(230, 560);
		rightBG.setArcWidth(50);
		rightBG.setArcHeight(50);
		rightBG.setFill(Color.ORANGE);

		// LEFT
		VBox leftVBox = new VBox(50);
		leftVBox.setAlignment(Pos.TOP_CENTER);
		Text dealerScore = new Text("Dealer: ");
		Text playerScore = new Text("Player: ");
		leftVBox.getChildren().addAll(dealerScore, dealerCards, message, playerCards, playerScore);

		// RIGHT
		VBox rightVBox = new VBox(20);
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
		rootLayout.getChildren().addAll(new StackPane(leftBG, leftVBox), new StackPane(rightBG, rightVBox));
		root.getChildren().addAll(background, rootLayout);

		// BIND PROPERTIES
		btnPlay.disableProperty().bind(playable);
		btnHit.disableProperty().bind(playable.not());
		btnStand.disableProperty().bind(playable.not());

		playerScore.textProperty().bind(new SimpleStringProperty("Player: ").concat(pointsPlayer.asString()));
        dealerScore.textProperty().bind(new SimpleStringProperty("Dealer: ").concat(pointsDealer.asString()));
        
        pointsPlayer.addListener((obs, old, newValue) -> {
			if (newValue.intValue() >= 21) {
				endGame();
			}
		});

        pointsDealer.addListener((obs, old, newValue) -> {
			if (newValue.intValue() >= 21) {
				endGame();
			}
		});

		// INIT BUTTONS
		btnPlay.setOnAction(event -> {
			startNewGame();
		});

		btnHit.setOnAction(event -> {
			takeCard(acesPlayer,card_deck.draw_card(), playerHand, pointsPlayer);
		});

		btnStand.setOnAction(event -> {
			while (pointsDealer.get() < 17) {
				takeCard(acesDealer,card_deck.draw_card(), dealerHand, pointsDealer);
			}
			endGame();
		});
		buttonBack.setOnAction(buttonBackhandler);
		
		return root;
	}

	public boolean startNewGame() {
		playable.set(true);
		message.setText("New game started");

		card_deck = new CardDeck();
		card_deck.shuffle();
		
		dealerHand.clear();
		acesDealer = 0;
		pointsDealer.set(0);
		playerHand.clear();
		acesPlayer = 0;
		pointsPlayer.set(0);

		takeCard(acesDealer,card_deck.draw_card(),dealerHand, pointsDealer);
		takeCard(acesDealer,card_deck.draw_card(),dealerHand, pointsDealer);
		takeCard(acesPlayer,card_deck.draw_card(),playerHand, pointsPlayer);
		takeCard(acesPlayer,card_deck.draw_card(),playerHand, pointsPlayer);
		
		return true;
	}

	public boolean endGame() {
		playable.set(false);

		int dealerValue = pointsDealer.get();
		int playerValue = pointsPlayer.get();
		String winner = "Exceptional case: d: " + dealerValue + " p: " + playerValue;

		// the order of checking is important
		if (dealerValue == 21 || playerValue > 21 || dealerValue == playerValue
				|| (dealerValue < 21 && dealerValue > playerValue)) {
			winner = "DEALER";
		} else if (playerValue == 21 || dealerValue > 21 || playerValue > dealerValue) {
			winner = "PLAYER";
		}
		message.setText(winner + " WON");
		
		return true;
	}

	public boolean takeCard(int aces, Card card, ObservableList<Node> cardHand, SimpleIntegerProperty value) {
		
		cardHand.add(card.generateCardImage());
		
		if (card.getRank() == 1) {
			aces++;
		}

		if (value.get() + card.getPoints() > 21 && aces > 0) {
			value.set(value.get() + card.getRank() - 10); // then count ace as
															// '1' not '11'
			aces--;
		} else {
			value.set(value.get() + card.getRank());
		}
		
		return true;
	}

}
