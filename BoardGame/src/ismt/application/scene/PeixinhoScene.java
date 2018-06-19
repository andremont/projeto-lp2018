package ismt.application.scene;

import javafx.geometry.Insets;

import javafx.geometry.Pos;
import javax.swing.JOptionPane;
import ismt.application.engine.Card;
import ismt.application.engine.CardDeck;
import ismt.application.engine.CardGame;
import ismt.application.engine.Context;
import ismt.application.engine.Player;
import ismt.application.engine.Utils;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
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

public class PeixinhoScene extends CardGame{

	final String imageFolder = resourceFolder + "/playing_cards_images/";
	private SimpleBooleanProperty playable = new SimpleBooleanProperty(false);

	public ObservableList<Node> playerHand;
	private ObservableList<Node> playerHand2;
	private ObservableList<Node> carta_f;

	private HBox player = new HBox(5); //BAIXO
	private HBox player2 = new HBox(5);
	private VBox carta = new VBox(5);
	private Player jogador_ = new Player();

	private Button btnPlay;
	private Button buttonBack;
	private Button btnEnd;
	private Button btnPescar;;
	private Button btnPedir ;
	private Label txtpontuacao;
	private String point_text;
	private String point_text2;

	private Text text_point = new Text();
	private Text text_point2 = new Text();

	private Text point = new Text();
	private Text point2 = new Text();	
	private int pontosHand1 = 500;
	private int pontosHand2 = 500;
	private int jogo = 1;
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
		
		btnPlay = new Button("Play");
		buttonBack= new Button("Back");
		btnEnd = new Button("End");
		btnPescar = new Button("Pescar");
		btnPedir = new Button("Pedir");
		txtpontuacao = new Label();
		
		playerHand = player.getChildren();
		playerHand2 = player2.getChildren();
		carta_f = carta.getChildren();
		GridPane root = new GridPane();
		root.setVgap(5); 
	    root.setHgap(5);       
	    root.setAlignment(Pos.BOTTOM_CENTER); 
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
	
		HBox mainBox = new HBox(200);
		VBox leftVBox = new VBox(160);
		HBox table = new HBox(5);
		mainBox.setAlignment(Pos.CENTER);
		player.setAlignment(Pos.TOP_CENTER);
		player2.setAlignment(Pos.BASELINE_CENTER);
		table.getChildren().addAll(carta,player);
		leftVBox.setAlignment(Pos.CENTER);
		leftVBox.getChildren().addAll(player2,player);
		carta.setVisible(false);
		mainBox.getChildren().addAll(leftVBox,table);
		carta.setAlignment(Pos.CENTER);
		
		// RIGHT
		VBox rightVBox = new VBox(10);
		rightVBox.setAlignment(Pos.CENTER);
		HBox points = new HBox(5);
		HBox btnActionsHBox = new HBox(2, btnPescar,btnPedir);
		HBox btnOptionsHBox = new HBox(btnPlay, btnEnd, buttonBack);
		points.getChildren().addAll(point);
		btnActionsHBox.setAlignment(Pos.CENTER);
		btnOptionsHBox.setAlignment(Pos.CENTER);
		rightVBox.getChildren().addAll(btnOptionsHBox, btnActionsHBox,text_point2);

		// ADD BOTH STACKS TO ROOT LAYOUT

		rootLayout.getChildren().addAll(new StackPane(leftBG, mainBox), new StackPane(rightBG, rightVBox));
		root.getChildren().addAll(background, rootLayout);

		// BIND PROPERTIES
		btnPlay.disableProperty().bind(playable);
		btnEnd.disableProperty().bind(playable.not());
		btnPescar.disableProperty().bind(playable.not());
		btnPedir.disableProperty().bind(playable.not());
		
		// INIT BUTTONS 
		btnPlay.setOnAction(event -> {
			startNewGame();
		});
		btnEnd.setOnAction(event -> {
			playable.set(false);
			endGame();
		});
		btnPescar.setOnAction(event ->{
			mover();

		});
		btnPedir.setOnAction(event ->{
			pedir_carta();
		});
		buttonBack.setOnAction(buttonBackhandler);

		return root;

	}

	private void pedir_carta() {
		// TODO Auto-generated method stub
		janela();
	}

	public boolean takeCard(Card card, ObservableList<Node> cardHand, boolean up) {
		if (up) {
			card.turn_card();
		}
		cardHand.add(card);
		
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
	
	public void janela(){
		String a = "";
		int cartaPedida =Integer.parseInt( 
				JOptionPane.showInputDialog("Insira a nº da carta a pescar"));
		if(cartaPedida >= 1 && cartaPedida < 15){
			
			a = String.valueOf(cartaPedida);
			Card temp = getCardInHand(a, playerHand2);

			if(temp.getRank()!=0){
				JOptionPane.showMessageDialog(null,"Tenho a carta");
				
				playerHand.add(temp);
				playerHand2.remove(temp);
				
			}
		
		}else{
			JOptionPane.showMessageDialog(null, "Não tenho a carta ");
		}
		
	 }
	
	private Card getCardInHand(String cartaPedida, ObservableList<Node> playerHand3) {
		Card temp = new Card();
		
		for(int i=0; i< playerHand3.size();i++){
			temp = (Card)playerHand3.get(i);
			if(cartaPedida.equals(temp.getRank()+"")){
				return temp;
			}
		}
		return temp;
	}

	public void mover(){
		switch(jogo){
		case 1: 
			carta.setVisible(true);
			jogo++;
			break;
		case 2:
			if (playerHand2.size() == 3) {
				for (int i = 1; i < 3; i++) {
					Card test = (Card) playerHand2.get(i);
					test.turn_card();
				}
			} else {
				for (int i = 0; i < 2; i++) {
					Card test = (Card) playerHand2.get(i);
					test.turn_card();
					btnPescar.setVisible(false);
				}
			}

			pontos();
	
		}
		takeCard(card_deck.draw_card(), playerHand, true); 
	}
	
	public boolean pontos(){
		point_text = Integer.toString(pontosHand1);
		point_text2 = Integer.toString(pontosHand2);
		text_point2.setText("Pontos" + point_text2 +  " " );
		if(point_text2.equals(text_point2)){
			txtpontuacao.setText("GANHASTE");
			jogador_.setPoints(jogador_.getPoints() + 150);
			System.out.print("estou aqui");
			Utils.SaveUser(jogador_);

		}else
			txtpontuacao.setText("PERDEU, TENTE NOVAMENTE");
			return false;
	}
	
	@Override
	public boolean startNewGame() {
		point_text2 = Context.getInstance().getName() + "'s";
		this.jogador_ = Context.getInstance().getPlayer();
		deal();
		pontos();
		playable.set(true);
		card_deck.shuffle();

		playerHand.clear();
		playerHand2.clear();
		
		carta_f.clear();
		for (int i = 0; i < 4; i++) {
			takeCard(card_deck.draw_card(), playerHand, true);
			takeCard(card_deck.draw_card(), playerHand2, false);

		}
		pontos();
		return true;
	}


	@Override
	public boolean endGame() {
		btnPedir.setVisible(true);
		btnPescar.setVisible(true);
		btnPescar.setVisible(true);
		player.setVisible(true);
		carta.setVisible(false);
		
		Clear();
		return true;
	}
	
	private void Clear(){
		playerHand.clear();
		playerHand2.clear();
		carta_f.clear();
	}

	@Override
	public void simulateGame() {
		Card test = (Card) playerHand2.get(1);
		test.turn_card();
	}
	

	
}

