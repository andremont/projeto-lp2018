package ismt.application.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ismt.application.engine.CardDeck;
import ismt.application.scene.BlackJackScene;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class StatsSceneTest extends TestCase {

	private MainMock myAppMock;
	private BlackJackScene myBlackJack;
	private CardDeck expected;

	@Before
	protected void setUp() {
		myBlackJack = new BlackJackScene();
		myAppMock = new MainMock(myBlackJack);
		myBlackJack.card_deck = new CardDeck();
		expected = myBlackJack.card_deck;
		createAppMock();
	}

	@After
	public void tearDown() throws Exception {
		myBlackJack = null;
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
	
	public BlackJackSceneTest(String name) {
		super(name);
	}
	
	@Test
	public void testDeal() {
		CardDeck result = new CardDeck();
		assertTrue(!expected.equals(result));
	}

	@Test
	public void testShuffle() {
		myBlackJack.shuffle();
		CardDeck result = myBlackJack.card_deck;
		assertNotSame(expected, result);
	}

	@Test
	public void testStartGame() {
		boolean expected = true;
		boolean result = myBlackJack.startNewGame();
		assertEquals(expected, result);
	}

	@Test
	public void testEndGame() {
		boolean expected = true;
		boolean result = myBlackJack.endGame();
		assertEquals(expected, result);
	}

	@Test
	public void testTakeCard() {
		boolean expected = true;
		boolean result = myBlackJack.takeCard(1, myBlackJack.card_deck.draw_card(), myBlackJack.dealerHand, myBlackJack.pointsDealer);
		assertEquals(expected, result);
	}

	public static junit.framework.Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new BlackJackSceneTest("testDeal")); 
		suite.addTest(new BlackJackSceneTest("testShuffle"));
		suite.addTest(new BlackJackSceneTest("testStartGame"));
		suite.addTest(new BlackJackSceneTest("testEndGame"));
		suite.addTest(new BlackJackSceneTest("testTakeCard"));
		return suite;
	}
}
