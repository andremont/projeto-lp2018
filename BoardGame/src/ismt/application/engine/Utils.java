package ismt.application.engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;
import javax.json.JsonReader;
import javax.json.JsonValue;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;




public class Utils {

	private static final String INFO_JSON = "info.json";
	private static final String USERS_JSON = "users.json";
	private static final String STATS_JSON = "stats.json";
	
	/** Validates user credentials  */
	public static boolean validateUser(String username, String password) 
	{ 
		boolean valid = false;
		InputStream fileReader = null;

		try {
			fileReader = new FileInputStream(USERS_JSON);
			// Create Json reader to read the file in Json format
			JsonReader jsonReader = Json.createReader(fileReader);
			JsonObject usersObject = jsonReader.readObject();
			jsonReader.close();

			if(usersObject != null)
				if (usersObject.containsKey(username)){
					JsonValue user = usersObject.get(username);
					String originalPass = user.asJsonObject().getString("password");

					if (originalPass.equals(password)) 
						valid = true;
				}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			try {
				fileReader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		return valid;
	}
	
	/**
	 * 
	 * @param type
	 * @param userObject
	 * @return Card array
	 */
	private static CardDeck buildCardArrayFromFile(String type, JsonObject userObject) {
		CardDeck deck = new CardDeck();
		JsonArray deckArray = userObject.getJsonArray(type);

		// Get each card info
		for(int i=0; i < deckArray.size(); i++){
			JsonObject card = deckArray.get(i).asJsonObject();
			Card tempCard = new Card();

			if (card.containsKey("rank"))
				tempCard.setRank(Integer.parseInt(card.getString("rank")));
			if (card.containsKey("points"))
				tempCard.setRank(Integer.parseInt(card.getString("points")));
			if (card.containsKey("suit"))
				tempCard.setSuit(getEnumFromString(Suit.class, card.getString("suit")));
			
			deck.cards_in_this_deck.add(tempCard);
		}

		return deck;
	}
	
	/**
	 * A common method for all enums since they can't have another base class
	 * @param <T> Enum type
	 * @param c enum type. All enums must be all caps.
	 * @param string case insensitive
	 * @return corresponding enum, or null
	 */
	public static <T extends Enum<T>> T getEnumFromString(Class<T> c, String string) {
	    if( c != null && string != null ) {
	        try {
	            return Enum.valueOf(c, string.trim().toUpperCase());
	        } catch(IllegalArgumentException ex) {
	        }
	    }
	    return null;
	}
	
	/** Print all cards in deck */
	public static void printAllCards(ArrayList<Card> cards)
	{
		for(Card newCard : cards)
			System.out.println(newCard.getRank() + " | " + newCard.getSuit() + " | " + newCard.getPoints() + " | \n" );
	}

	/** Saves all cards in deck to a String */
	public static String getAllCards(ArrayList<Card> cards)
	{
		String tempString = "";

		for(Card newCard : cards)
			tempString += newCard.getRank() + " | " + newCard.getSuit() + " | " + newCard.getPoints() + "\n";

		return tempString;
	}
	
	/**
	 * Get user records
	 * @param player name
	 * @return Player object
	 */
	public static Stats GetUserRecords(String player)
	{
		InputStream fileReader = null;
		Stats stats = new Stats();

		try {
			fileReader = new FileInputStream(STATS_JSON);

			// Create Json reader to read the file in Json format
			JsonReader jsonReader = Json.createReader(fileReader);
			JsonObject userObject = jsonReader.readObject().get(player).asJsonObject();
			jsonReader.close();
			fileReader.close();

			if(userObject != null)
				if(userObject != JsonArray.NULL)
				{
					stats.setVictories(Integer.parseInt(userObject.getString("victories")));
					stats.setDraws(Integer.parseInt(userObject.getString("draws")));
					stats.setLosses(Integer.parseInt(userObject.getString("losses")));
				}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return stats;
	}

	/**
	 * 
	 * @param Player name
	 * @return Player details
	 */
	public static Player GetUser(String player)
	{
		InputStream fileReader = null;
		Player newPlayer = new Player();

		try {
			fileReader = new FileInputStream(USERS_JSON);

			// Create Json reader to read the file in Json format
			JsonReader jsonReader = Json.createReader(fileReader);
			JsonObject userObject = jsonReader.readObject().get(player).asJsonObject();
			jsonReader.close();
			fileReader.close();

			if(userObject != null)
				if(userObject != JsonArray.NULL)
				{
					newPlayer.setName(player);
					newPlayer.setPassword(userObject.getString("password"));
					
					if (userObject.containsKey("money"))
						newPlayer.setMoney(Integer.parseInt(userObject.getString("money")));
					if (userObject.containsKey("deck"))
						newPlayer.setDeck(buildCardArrayFromFile("deck", userObject));
					if (userObject.containsKey("points"))
						newPlayer.setPoints(Integer.parseInt(userObject.getString("points")));
				}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return newPlayer;
	}

	/**
	 * Get all users
	 * @return Player array
	 */
	public static ArrayList<Player> getAllPlayers() throws IOException
	{
		ArrayList<Player> players = new ArrayList<Player>();
		InputStream fileReader = new FileInputStream(USERS_JSON);

		try{
			// Create Json reader to read the file in Json format
			JsonReader jsonReader = Json.createReader(fileReader);
			JsonObject playerObject = jsonReader.readObject();
			fileReader.close();
			ArrayList<String> playerNames = new ArrayList<String>();
			playerNames.addAll(playerObject.keySet()); 

			// Get each player info 
			for(int i=0; i < playerNames.size(); i++){
				String playerName = playerNames.get(i);
				players.add(GetUser(playerName));
			}
		}
		finally{
			fileReader.close();
		}
		return players;
	}

	/**
	 * Saves input user to file, including cards
	 * @param player
	 * @return success variable
	 */
	public static boolean SaveUser(Player player)
	{
		boolean success = false;
		InputStream fileReader = null;

		try {
			fileReader = new FileInputStream(USERS_JSON);

			// Create Json reader to read the file in Json format
			JsonReader jsonReader = Json.createReader(fileReader);
			JsonObject usersObject = jsonReader.readObject();
			jsonReader.close();
			fileReader.close();
			JsonObjectBuilder usersBuilder = Json.createObjectBuilder();

			if(usersObject != null)
				if(usersObject != JsonArray.NULL)
					usersObject.entrySet().forEach(e -> usersBuilder.add(e.getKey(), e.getValue()));

			// Remove if exists
			usersBuilder.remove(player.getName());

			// Add all player's deck cards
			JsonObjectBuilder userBuilder = Json.createObjectBuilder();
			//userBuilder.add("deck", buildCardsArray(player.getDeck())); 
			
			// Add all player's properties and attributes
			userBuilder.add("password", player.getPassword())
			.add("money", player.getMoney() + "")
			.add("points", player.getPoints() + "");

			usersBuilder.add(player.getName(), userBuilder);

			// Write to file
			OutputStream os = new FileOutputStream(USERS_JSON);
			Map<String, Boolean> config = new HashMap<>();
			config.put(JsonGenerator.PRETTY_PRINTING, true);
			JsonWriterFactory jwf = Json.createWriterFactory(config);
			JsonWriter jsonWriter = jwf.createWriter(os);
			jsonWriter.writeObject(usersBuilder.build());
			jsonWriter.close();

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		finally{
			success = true;
		}

		return success;
	}

	/** Builds an returns an array built in Json */
	private static JsonArrayBuilder buildCardsArray(CardDeck cards) {
		JsonArrayBuilder arrayBuilder = Json.createArrayBuilder();

		for(Card card : cards.cards_in_this_deck){
			JsonObjectBuilder jpb = Json.createObjectBuilder().
					add("rank", card.getRank()).
					add("suit", card.getSuit().toString()).
					add("points", card.getPoints());
			arrayBuilder.add(jpb);
		}
		return arrayBuilder;
	}
}
