package ismt.application.tests;

import ismt.application.engine.CardDeck;
import ismt.application.engine.CardGame;
import ismt.application.scene.BlackJackScene;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class BlackJackSceneTest extends TestCase{
	
	private BlackJackScene myBlackJack;
	private CardDeck expected;
	
	@Override protected void setUp() {
        myBlackJack = new BlackJackScene();
        expected = myBlackJack.card_deck;
    }
    
	public BlackJackSceneTest(String name) {
		super(name);
	}
	
	public void testDeal() {
        CardDeck result = new CardDeck();
        assertTrue(!expected.equals(result));     
    }
	
	public void testShuffle() {
        myBlackJack.shuffle();
        CardDeck result = myBlackJack.card_deck;
        assertNotSame(expected, result);     
    }

	public void testStartGame() {
		boolean expected = true;
        boolean result = myBlackJack.startNewGame();
        assertEquals(expected, result);     
    }
	
	public void testEndGame() {
		boolean expected = true;
        boolean result = myBlackJack.endGame();
        assertEquals(expected, result);     
    }
	
	public void testTakeCard() {
		boolean expected = true;
        boolean result = myBlackJack.takeCard(1, myBlackJack.card_deck.draw_card(), myBlackJack.dealerHand, myBlackJack.pointsDealer);
        assertEquals(expected, result);     
    }
	
	public static junit.framework.Test suite() {
		TestSuite suite= new TestSuite();
		suite.addTest(new BlackJackSceneTest("testDeal"));
		/*suite.addTest(new BlackJackSceneTest("testShuffle"));
		suite.addTest(new BlackJackSceneTest("testStartGame"));
		suite.addTest(new BlackJackSceneTest("testEndGame"));
		suite.addTest(new BlackJackSceneTest("testTakeCard"));*/
		return suite;
		}
}
