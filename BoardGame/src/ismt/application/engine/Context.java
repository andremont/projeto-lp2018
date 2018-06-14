package ismt.application.engine;

public class Context {
	private final static Context instance = new Context();
	private String name;
	private String password;
	private Player thePlayer;

	public static Context getInstance() {
		return instance;
	}

	public void setName(String name)
	{
		this.name = name;
	}
	public String getName() {
		return name;
	}   

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public void setPlayer(String playerName) {
		this.thePlayer = Utils.GetUser(playerName);
		this.password = thePlayer.getPassword();
		this.name = thePlayer.getName();
	}

	public Player getPlayer() {
		return thePlayer;
	}
}