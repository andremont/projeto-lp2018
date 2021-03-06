package ismt.application.scene;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import java.util.Random;
import ismt.application.engine.Card;
import ismt.application.engine.CardDeck;
import ismt.application.engine.CardGame;
import ismt.application.engine.Context;
import ismt.application.engine.Player;
import ismt.application.engine.Utils;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class PokerScene extends CardGame {

	final String imageFolder = resourceFolder + "/playing_cards_images/";
	private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);
	private Text message = new Text();

	public ObservableList<Node> playerHand;
	private ObservableList<Node>  player2Hand, player3Hand,player4Hand, floopCards, turnCards, riverCards, burnCard;

	private Player man = new Player();

	private HBox graveyard = new HBox(5);
	private HBox player = new HBox(5);
	private VBox player2 = new VBox(5);
	private HBox player3 = new HBox(5);
	private VBox player4 = new VBox(5);
	private HBox flop = new HBox(5);
	private HBox turn = new HBox(5);
	private HBox river = new HBox(5);

	private int myMoney = 500;
	private int money2 = 500;
	private int money3 = 500;
	private int money4 = 500;
	private int pocket = 0;
	private int big = 10;
	private int small = big / 2;

	private Slider slide;
	private Label slideValue;

	private String cash, cash2, cash3, cash4, pocketCash, playerName, winner;

	private Text moneyText = new Text();
	private Text money2Text = new Text();
	private Text money3Text = new Text();
	private Text money4Text = new Text();
	private Text pocketMoney = new Text();

	private Button btnPlay, buttonBack, btnEnd, btnCheck, btnCall, btnRaise, btnFold;

	private int dealer = 0;
	private int gameState = 1;

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

		Context context = Context.getInstance();
		String name = context.getName();
		man.getName();
		playerHand = FXCollections.observableArrayList();

		playerName = "My ";

		playerHand = player.getChildren();
		player2Hand = player2.getChildren();
		player3Hand = player3.getChildren();
		player4Hand = player4.getChildren();
		floopCards = flop.getChildren();
		turnCards = turn.getChildren();
		riverCards = river.getChildren();
		burnCard = graveyard.getChildren();

		btnPlay = new Button("Play");
		buttonBack = new Button("Back");
		btnEnd = new Button("End");

		btnCheck = new Button("Check");
		btnCall = new Button("Call");
		btnRaise = new Button("Raise");
		btnFold = new Button("Fold");

		slide = new Slider();
		slideValue = new Label("Raise Value: ");

		setNewText();

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
		HBox table = new HBox(20);
		mainBox.setAlignment(Pos.CENTER);
		player.setAlignment(Pos.CENTER);
		player2.setAlignment(Pos.CENTER);
		player3.setAlignment(Pos.CENTER);
		player4.setAlignment(Pos.CENTER);
		leftVBox.setAlignment(Pos.CENTER);
		// Text dealerScore = new Text("Dealer: ");
		// Text playerScore = new Text("Player: ");
		table.getChildren().addAll(flop, turn, river);
		leftVBox.getChildren().addAll(player3, table, player);
		mainBox.getChildren().addAll(player2, leftVBox, player4);
		flop.setVisible(false);
		turn.setVisible(false);
		river.setVisible(false);

		// RIGHT
		VBox rightVBox = new VBox(10);
		rightVBox.setAlignment(Pos.CENTER);
		HBox moneyBox = new HBox(5);
		moneyBox.setAlignment(Pos.CENTER);

		pocketMoney.setFont(Font.font("Verdana", FontWeight.BOLD, 18));

		HBox btnActionsHBox = new HBox(2, btnCheck, btnCall, btnRaise, btnFold);
		HBox btnOptionsHBox = new HBox(btnPlay, btnEnd, buttonBack);
		moneyBox.getChildren().addAll(moneyText);
		btnActionsHBox.setAlignment(Pos.CENTER);
		btnOptionsHBox.setAlignment(Pos.CENTER);
		rightVBox.getChildren().addAll(btnOptionsHBox, moneyText, money2Text, money3Text, 
									money4Text, pocketMoney, btnActionsHBox, slide, slideValue);

		// ADD BOTH STACKS TO ROOT LAYOUT

		rootLayout.getChildren().addAll(new StackPane(leftBG, mainBox), new StackPane(rightBG, rightVBox));
		root.getChildren().addAll(background, rootLayout);

		// BIND PROPERTIES
		btnPlay.disableProperty().bind(playable);
		btnEnd.disableProperty().bind(playable.not());
		btnCheck.disableProperty().bind(playable.not());
		btnCall.disableProperty().bind(playable.not());
		btnRaise.disableProperty().bind(playable.not());
		btnFold.disableProperty().bind(playable.not());

		// INIT BUTTONS
		btnPlay.setOnAction(event -> {
			startNewGame();
		});
		btnEnd.setOnAction(event -> {
			playable.set(false);
			endGame();
		});

		btnCheck.setOnAction(event -> {
			check();
		});

		btnCall.setOnAction(event -> {
			call();
		});

		btnRaise.setOnAction(event -> {
			raise();
		});

		btnFold.setOnAction(event -> {
			fold();
		});
		buttonBack.setOnAction(buttonBackhandler);

		return root;

	}

	public boolean move() {

		switch (gameState) {
		case 1:
			gameState++;
			break;

		case 2:
			flop.setVisible(true);
			gameState++;
			break;
		case 3:
			turn.setVisible(true);
			gameState++;
			break;
		case 4:
			river.setVisible(true);
			gameState++;
			break;
		case 5:
			if (player2Hand.size() == 3) {
				for (int i = 1; i < 3; i++) {
					Card test = (Card) player2Hand.get(i);
					test.turn_card(); //mostra as cartas dos advers�rios
				}
			} else {
				for (int i = 0; i < 2; i++) {
					Card test = (Card) player2Hand.get(i);
					test.turn_card();
				}
			}
			if (player3Hand.size() == 3) {
				for (int i = 1; i < 3; i++) {
					Card test = (Card) player3Hand.get(i);
					test.turn_card();
				}
			} else {
				for (int i = 0; i < 2; i++) {
					Card test = (Card) player3Hand.get(i);
					test.turn_card();
				}
			}
			if (player4Hand.size() == 3) {
				for (int i = 1; i < 3; i++) {
					Card test = (Card) player4Hand.get(i);
					test.turn_card();
				}
			} else {
				for (int i = 0; i < 2; i++) {
					Card test = (Card) player4Hand.get(i);
					test.turn_card();
				}
			}
			
			btnCheck.setVisible(false);
			btnCall.setVisible(false);
			btnRaise.setVisible(false);
			btnFold.setVisible(false);
			slide.setVisible(false);

			playable.set(false);
			winner = playerName;
			endGame();
			break;
		}
		return true;

	}

	public boolean fold() {
		btnCall.setVisible(false);
		btnRaise.setVisible(false);
		player.setVisible(false);
		btnFold.setVisible(false);
		slide.setVisible(false);
		move();
		
		return true;

	}

	public boolean  raise() {
		double y = slide.getValue();
		int x = (int) y;
		myMoney = myMoney - x;
		pocket = pocket + x;
		setNewText();
		move();
		return true;

	}

	public boolean  call() {
		if (dealer != 3) {
			if (dealer == 4) {
				myMoney = myMoney - small;
				pocket = pocket + small;
			} else {
				myMoney = myMoney - big;
				pocket = pocket + big;
			}
		}
		setNewText();
		move();
		return true;

	}

	public boolean  check() {
		move();
		return true;

	}

	public boolean setDealer() {

		Image dealerImage = new Image(resourceFolder + "Dealer.png", 50, 50, true, true);
		Image smallImage = new Image(resourceFolder + "Small.png", 50, 50, true, true);
		Image bigImage = new Image(resourceFolder + "Big.png", 50, 50, true, true);
		ImageView viewDealer = new ImageView(dealerImage);
		ImageView viewSmall = new ImageView(smallImage);
		ImageView viewBig = new ImageView(bigImage);

		// atribui o dealer na primeira jogada e incrementa nas seguintes.
		if (dealer == 0) {
			Random random = new Random();
			dealer = random.nextInt(4) + 1;
		} else {
			dealer++;
			if (dealer == 5)
				dealer = 1;
		}

		switch (dealer) {
		case 1:
			playerHand.add(viewDealer);
			player2Hand.add(viewSmall);
			player3Hand.add(viewBig);

			money2 = money2 - small;
			money3 = money3 - big;
			pocket = small + big;

			setNewText();
			break;
		case 2:
			player2Hand.add(viewDealer);
			player3Hand.add(viewSmall);
			player4Hand.add(viewBig);

			money3 = money3 - small;
			money4 = money4 - big;
			pocket = small + big;
			break;
		case 3:
			player3Hand.add(viewDealer);
			player4Hand.add(viewSmall);
			playerHand.add(viewBig);

			money4 = money4 - small;
			myMoney = myMoney - big;
			pocket = small + big;
			break;
		case 4:
			player4Hand.add(viewDealer);
			playerHand.add(viewSmall);
			player2Hand.add(viewBig);

			myMoney = myMoney - small;
			money2 = money2 - big;
			pocket = small + big;
			break;
		}
		return true;
	}

	public boolean setNewText() {
		cash = Integer.toString(myMoney);
		cash2 = Integer.toString(money2);
		cash3 = Integer.toString(money3);
		cash4 = Integer.toString(money4);
		pocketCash = Integer.toString(pocket);

		moneyText.setText(playerName + " money: " + cash + "�");
		money2Text.setText("Player 2: " + cash2 + "�");
		money3Text.setText("Player 3: " + cash3 + "�");
		money4Text.setText("Player 4: " + cash4 + "�");
		pocketMoney.setText("Pocket: " + pocketCash + "�");

		if (myMoney > 0) {

			slide.setMin(1);
			slide.setMax(myMoney);
			slide.setValue(1);
			slide.setShowTickLabels(true);
			slide.setMajorTickUnit(myMoney / 2);
			slide.setMinorTickCount(myMoney / 20);

			switch (dealer) {
			case 3:
				slideValue.setText("Raise Value: " + big);
				slide.setMin(big);
				break;
			case 4:
				slideValue.setText("Raise Value: " + small);
				slide.setMin(small);
				break;
			}

			
			slide.valueProperty().addListener(new ChangeListener<Number>() { // vai buscar o valor do slide.
				public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {

					int x = new_val.intValue();

					slideValue.setText("Raise Value: " + Integer.toString(x));
					slideValue.setFont(Font.font("Verdana", FontWeight.NORMAL, 16));

					if (x == myMoney) {
						slideValue.setText("ALL IN!");
						slideValue.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
					}
				}
			});
		} else {
			slide.setDisable(true);
		}
		return true;
	}

	public boolean takeCard(Card card, ObservableList<Node> cardHand, boolean up) {
		
//		Fun��o principal, mas a turn_card() n�o est� a funcionar.		
//		if (up) {
//			card.turn_card();
//		}
//		cardHand.add(card);
		
		if (up) {
			cardHand.add(card.generateCardImage());
			
		}else {
		
		cardHand.add(card);}
		return true;
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

	@Override
	public boolean startNewGame() {

		playerName = Context.getInstance().getName() + "'s";
		this.man = Context.getInstance().getPlayer();

		setNewText();
		deal();

		playable.set(true);
		message.setText("New game started");

		card_deck.shuffle();

		clearHands();
		setDealer();

		
		//D� as cartas aos jogadores
		for (int i = 0; i < 2; i++) {
			takeCard(card_deck.draw_card(), playerHand, true);
			takeCard(card_deck.draw_card(), player2Hand, false);
			takeCard(card_deck.draw_card(), player3Hand, false);
			takeCard(card_deck.draw_card(), player4Hand, false);
		}
		
		//Queima uma carta.
		takeCard(card_deck.draw_card(), burnCard, false);

			//D� o flop
		for (int i = 0; i < 3; i++) {
			takeCard(card_deck.draw_card(), floopCards, true);
		}
		
		takeCard(card_deck.draw_card(), burnCard, false);//queima uma carta
		takeCard(card_deck.draw_card(), turnCards, true);//d� o turn
		
		takeCard(card_deck.draw_card(), burnCard, false);//queima uma carta
		takeCard(card_deck.draw_card(), riverCards, true);// d� o river

		System.out.println("myMoney: " + myMoney);
		System.out.println("money2: " + money2);
		System.out.println("money3: " + money3);
		System.out.println("money4: " + money4);
		System.out.println("pocket: " + pocket);
		System.out.println();

		setNewText();
		return true;
	}

	@Override
	public boolean endGame() {

		btnCall.setVisible(true);
		btnRaise.setVisible(true);
		flop.setVisible(false);
		river.setVisible(false);
		turn.setVisible(false);
		player.setVisible(true);
		btnFold.setVisible(true);
		slide.setVisible(true);
		btnCheck.setVisible(true);

		gameState = 1;

		clearHands();

		if (winner.equals(playerName)) {

			slideValue.setText("YOU WON!!");

			man.setPoints(man.getPoints() + 1);
			Utils.SaveUser(man);
		} else
			slideValue.setText("YOU LOSE!");

		slideValue.setFont(Font.font("Verdana", FontWeight.BOLD, 18));
		return true;
	}

	public boolean clearHands() {
		if (playerHand != null) {
			playerHand.clear();
		}
		if (player2Hand != null) {
			player2Hand.clear();
		}
		if (player3Hand != null) {
			player3Hand.clear();
		}
		if (player4Hand != null) {
			player4Hand.clear();
		}
		if (floopCards != null) {
			floopCards.clear();
		}
		if (burnCard != null) {
			burnCard.clear();
		}
		if (turnCards != null) {
			turnCards.clear();
		}
		if (riverCards != null) {
			riverCards.clear();
		}
		
		return true;
	}

	@Override
	public void simulateGame() {

		Card test = (Card) player2Hand.get(1);
		// Card card = new Card();
		// card = (Card) test;
		test.turn_card();
	}
}
