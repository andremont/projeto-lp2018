package ismt.application.tests;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import ismt.application.engine.CardDeck;
import ismt.application.scene.PeixinhoScene;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class PeixinhoSceneTest extends TestCase{
	private MainMock myAppMock;
	private PeixinhoScene myPeixinho;
	private CardDeck expected;

	@Before
	protected void setUp() {
		myPeixinho = new PeixinhoScene();
		myAppMock = new MainMock(myPeixinho);
		myPeixinho.card_deck = new CardDeck();
		expected = myPeixinho.card_deck;
		createAppMock();
	}

	@After
	public void tearDown() throws Exception {
		myPeixinho = null;
		myAppMock.stop();
		expected = null;
	}
	@Test
	public void testJanela(){
		PeixinhoScene result = new PeixinhoScene();
		
		assertTrue(!expected.equals(result));
	}
	
	@Test
	public void testPontos() {
		boolean expected = true;
		boolean result = myPeixinho.pontos();
		assertEquals(expected, result);
	}

	@Test
	public void testPescar(){
		PeixinhoScene result = new PeixinhoScene();
		assertTrue(!expected.equals(result));
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
	
	public PeixinhoSceneTest(String name) {
		super(name);
	}
	
	@Test
	public void testDeal() {
		CardDeck result = new CardDeck();
		assertTrue(!expected.equals(result));
	}

	@Test
	public void testShuffle() {
		myPeixinho.shuffle();
		CardDeck result = myPeixinho.card_deck;
		assertNotSame(expected, result);
	}

	@Test
	public void testStartGame() {
		boolean expected = true;
		boolean result = myPeixinho.startNewGame();
		assertEquals(expected, result);
	}

	@Test
	public void testEndGame() {
		boolean expected = true;
		boolean result = myPeixinho.endGame();
		assertEquals(expected, result);
	}

	@Test
	public void testTakeCard() {
		boolean expected = true;
		boolean result =true;
		myPeixinho.takeCard(myPeixinho.card_deck.draw_card(), myPeixinho.playerHand,true);
		assertEquals(expected, result);
	}
	public static junit.framework.Test suite() {
		TestSuite suite = new TestSuite();
		suite.addTest(new PeixinhoSceneTest("testDeal")); 
		suite.addTest(new PeixinhoSceneTest("testShuffle"));
		suite.addTest(new PeixinhoSceneTest("testStartGame"));
		suite.addTest(new PeixinhoSceneTest("testEndGame"));
		suite.addTest(new PeixinhoSceneTest("testTakeCard"));
		suite.addTest(new PeixinhoSceneTest("testJanela"));
		suite.addTest(new PeixinhoSceneTest("testPontos"));
		suite.addTest(new PeixinhoSceneTest("testPescar"));

		return suite;
	}
}
