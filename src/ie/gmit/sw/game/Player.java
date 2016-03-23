package ie.gmit.sw.game;

public class Player extends Base {

	private int special;
	private String weapon;
	private int searchCount;
	
	public Player() {
		super();
	}
	
	public Player(int health, int armor) {
		super(health, armor);
	}

	public int getSpecial() {
		return special;
	}

	public void setSpecial(int special) {
		this.special = special;
	}

	public String getWeapon() {
		return weapon;
	}

	public void setWeapon(String weapon) {
		this.weapon = weapon;
	}

	public int getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
}