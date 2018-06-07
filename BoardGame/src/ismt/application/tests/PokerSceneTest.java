package ismt.application.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ismt.application.engine.CardDeck;
import ismt.application.scene.BlackJackScene;
import ismt.application.scene.PokerScene;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PokerSceneTest extends TestCase{
	private MainMock myAppMock;
	private PokerScene myPoker;
	private CardDeck expected;
	
	@Before
	protected void setUp() {
		
		myPoker = new PokerScene();
		myAppMock = new MainMock(myPoker);
		myPoker.card_deck = new CardDeck();
		expected = myPoker.card_deck;
		createAppMock();
	}

	@After
	public void tearDown() throws Exception {
		myPoker = null;
		myAppMock.stop();
		expected = null;
	}
	
	private void createAppMock() {
		Thread thread = new Thread(new Runnable() {

			@Override
			public void run() {
				new JFXPanel(); // Initializes the JavaFx Platform
				Platform.runLater(new Runnable() {

					@Override
					public void run() {
						myAppMock.start(new Stage()); // Create and initialize app new MainMock()
					}
				});
			}
		});
		thread.start();// Initialize the thread
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} // Time to use the app, without this the thread will be killed before
	}
	
	public PokerSceneTest(String name) {
		super(name);
	}
	
	@Test
	public void testDeal() {
		CardDeck result = new CardDeck();
		assertTrue(!expected.equals(result));
	}
	
	@Test
	public void testShuffle() {
		myPoker.shuffle();
		CardDeck result = myPoker.card_deck;
		assertNotSame(expected, result);
	}

	@Test
	public void testStartGame() {
		boolean expected = true;
		boolean result = myPoker.startNewGame();
		assertEquals(expected, result);
	}

	@Test
	public void testEndGame() {
		boolean expected = true;
		boolean result = myPoker.endGame();
		assertEquals(expected, result);
	}

	@Test
	public void testTakeCard() {
		boolean expected = true;
		boolean result = myPoker.takeCard(myPoker.card_deck.draw_card(), myPoker.playerHand, true);
		assertEquals(expected, result);
	}
	
	@Test
	public void testSetDealer() {
		boolean expected = true;
		boolean result = myPoker.setDealer();
		assertEquals(expected, result);
	}
	
	@Test
	public void testSetNewText() {
		boolean expected = true;
		boolean result = myPoker.setNewText();
		assertEquals(expected, result);
	}
	
	
	public static junit.framework.Test suite() {
		TestSuite suite = new TestSuite();
//		suite.addTest(new PokerSceneTest("testDeal")); 
//		suite.addTest(new PokerSceneTest("testShuffle"));
//		suite.addTest(new PokerSceneTest("testStartGame"));
//		suite.addTest(new PokerSceneTest("testEndGame"));
//		suite.addTest(new PokerSceneTest("testTakeCard"));
//		suite.addTest(new PokerSceneTest("testSetDealer"));
		suite.addTest(new PokerSceneTest("testSetNewText"));
		return suite;
	}
}
