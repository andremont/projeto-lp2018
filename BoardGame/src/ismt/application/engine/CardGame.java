package ismt.application.engine;

import java.io.File;

import javafx.scene.Scene;
import javafx.stage.Stage;

public abstract class CardGame {
	protected static final int APP_WIDTH = 910;
	protected static final int APP_HEIGHT = 600;
	static final int BUTTON_SIZE = 100;
	static final int GAP_SIZE = 10;
	String name;
	int numberOfCards;
	int numberOfPlayers;
	protected final String resourceFolder = new File("resource").toURI().toString();
	public CardDeck card_deck;

	public abstract Scene buildPlayScene(Stage primaryStage, Scene sceneMain);
	public abstract void deal();
	public abstract void shuffle();
	public abstract boolean startNewGame();
	public abstract boolean endGame();
	public abstract void simulateGame();
}
