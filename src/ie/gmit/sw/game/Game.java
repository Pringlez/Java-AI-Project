package ie.gmit.sw.game;

public class Game {

	private int mazeExitGoals;
	private int gameDifficulty;
	private int enemyAmount;
	
	public Game() {
		setMazeExitGoals(0);
		setGameDifficulty(0);
	}
	
	public Game(int mazeExitGoals, int gameDifficulty) {
		setMazeExitGoals(mazeExitGoals);
		setGameDifficulty(gameDifficulty);
	}

	public int getMazeExitGoals() {
		return mazeExitGoals;
	}

	public void setMazeExitGoals(int mazeExitGoals) {
		this.mazeExitGoals = mazeExitGoals;
	}

	public int getGameDifficulty() {
		return gameDifficulty;
	}

	public void setGameDifficulty(int gameDifficulty) {
		this.gameDifficulty = gameDifficulty;
	}

	public int getEnemyAmount() {
		return enemyAmount;
	}

	public void setEnemyAmount(int enemyAmount) {
		this.enemyAmount = enemyAmount;
	}
}