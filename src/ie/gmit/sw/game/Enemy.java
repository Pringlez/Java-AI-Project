package ie.gmit.sw.game;

public class Enemy extends Base implements Runnable {

	private int strength;
	private int difficulty;
	private boolean boss;
	private int algorithm;
	
	// Idea: Make enemy walk only when the player walks
	
	public Enemy() {
		super();
		setStrength(0);
		setDifficulty(0);
		setBoss(false);
		setAlgorithm(0);
	}
	
	public Enemy(int health, int armor, int strength, int difficulty, boolean boss, int algorithm) {
		super(health, armor);
		setStrength(strength);
		setDifficulty(difficulty);
		setBoss(boss);
		setAlgorithm(algorithm);
	}
	
	@Override
	public void run() {
		try {
            Thread.sleep(200);
        } catch (InterruptedException error) {
            System.out.println("Error - " + error);
        }
	}

	public int getStrength() {
		return strength;
	}

	public void setStrength(int strength) {
		this.strength = strength;
	}

	public int getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(int difficulty) {
		this.difficulty = difficulty;
	}

	public boolean isBoss() {
		return boss;
	}

	public void setBoss(boolean boss) {
		this.boss = boss;
	}

	public int getAlgorithm() {
		return algorithm;
	}

	public void setAlgorithm(int algorithm) {
		this.algorithm = algorithm;
	}
}