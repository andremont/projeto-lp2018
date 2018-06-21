package ismt.application.tests;


import ismt.application.engine.Utils;
import ismt.application.engine.CardDeck;
import ismt.application.engine.Player;
import ismt.application.engine.PlayerStats;
import ismt.application.engine.Stats;
import ismt.application.scene.StatsScene;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.stage.Stage;

import junit.framework.TestCase;
import junit.framework.TestSuite;

public class StatsSceneTest extends TestCase {

	private MainMock myAppMock;
	private StatsScene mystats;
	String playerName = "Xavier";
	Player player = new Player();
	PlayerStats playerStats = new PlayerStats(playerName, 100, 5, 5, 20000, 500);
	@Before
	protected void setUp() {
		mystats = new StatsScene();
		myAppMock = new MainMock(mystats);
		createAppMock();
		
		
		
		Stats stats = new Stats();
		stats.setDraws(111);
		stats.setLosses(102);
		stats.setVictories(100);
		
		player.setName(playerName);
		player.setPassword(playerName);
		//player.setDeck(new CardDeck());
		this.player.setStats(stats);
		
	}

	@After
	public void tearDown() throws Exception {
		mystats = null;
		myAppMock.stop();
	
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
	
	public StatsSceneTest(String name) { 
		super(name);
	}
	
    @Test
	public void testeSetUser() {

		boolean expected = true; // 
		boolean result = Utils.SaveUser(this.player); // seria necessario criaçao de ficheiro como argumento de SaveUser no Utils
	
		assertEquals(expected, result);
	}
    
    @Test
    public void testeGetUserList() {
    	
    	boolean expected = true; 
		boolean result = mystats.getUserList().add(playerStats);
	
		assertEquals(expected, result);
    	
    }

    public static junit.framework.Test testSuite() {
		TestSuite testSuite = new TestSuite();
		testSuite.addTest(new StatsSceneTest("testeSetUser")); 
		testSuite.addTest(new StatsSceneTest("testeGetUserList")); // problemas com rank do Utils ser Integer e não String 

		return testSuite;
	}

}
