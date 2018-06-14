package ismt.application.engine;


public class PlayerStats extends Stats{
	
	private String name;
	private int money;
	private int points;

//	Stats stats = new Stats();

	public PlayerStats(String given_name, 
			int given_victories, int given_draws, 
			int given_losses, int given_money, int given_points) {

		name = given_name;
		money = given_money;
		points = given_points;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public int getMoney() {
		return money;
	}
	
	public void setMoney(int money) {
		this.money = money;
	}
	
	public int getPoints() {
		return points;
	}

	public void setPoints(int points) {
		this.points = points;
	}
	
}
