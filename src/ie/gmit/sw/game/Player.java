package ie.gmit.sw.game;

/**  
* Player.java - The player class containing attributes for player objects
* @author John Walsh
* @version 1.0
* @see Base
*/
public class Player extends Base {

	private int score;
	private int stepsToExit;
	private int steps;
	private int special;
	private String weapon;
	private int weaponStrength;
	private int searchCount;
	
	public Player() {
		super();
	}
	
	public Player(int health, int armor) {
		super(health, armor);
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public int getStepsToExit() {
		return stepsToExit;
	}

	public void setStepsToExit(int stepsToExit) {
		this.stepsToExit = stepsToExit;
	}

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
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

	public int getWeaponStrength() {
		return weaponStrength;
	}

	public void setWeaponStrength(int weaponStrength) {
		this.weaponStrength = weaponStrength;
	}

	public int getSearchCount() {
		return searchCount;
	}

	public void setSearchCount(int searchCount) {
		this.searchCount = searchCount;
	}
}