package ie.gmit.sw.game;

import java.util.Random;

import ie.gmit.sw.ai.Node;

/**  
* Enemy.java - The enemy class containing attributes for enemy objects
* @author John Walsh
* @version 1.0
* @see Base
*/
public class Enemy extends Base implements Runnable {

	private int id;
	private int strength;
	private int difficulty;
	private boolean boss;
	private int algorithm;
	private Thread instance;
	private Node[][] maze;
	private Player player;
	
	// Idea: Make enemy walk only when the player walks
	
	public Enemy() {
		super();
		setStrength(0);
		setDifficulty(0);
		setBoss(false);
		setAlgorithm(new Random().nextInt((2 - 0) + 1) + 0);
	}
	
	public Enemy(int id, int health, int armor, int strength, int difficulty, boolean boss) {
		super(health, armor);
		setId(id);
		setStrength(strength);
		setDifficulty(difficulty);
		setBoss(boss);
		setAlgorithm(new Random().nextInt((2 - 0) + 1) + 0);
	}
	
	@Override
	public void run() {
		while(true){
			try {
	            Thread.sleep(500);
	            checkMove(new Random().nextInt((3 - 0) + 1) + 0);
	        } catch (InterruptedException error) {
	            System.out.println("Error - " + error);
	        }
		}
	}
	
	/**
	 * Checks if the player is making a valid move, return false if the move is invalid
	 * @param r
	 * @param c
	 * @return
	 */
	private boolean isValidMove(int r, int c){
		if(player.isGameOver()) return false;
		if((r < 0) || (c < 0) || !(r <= maze.length - 1 && c <= maze[r].length - 1)) return false;
		
		switch(maze[r][c].getNodeType()){
			case ' ':
				maze[getRowPos()][getColPos()].setNodeType(' ');
				maze[r][c].setNodeType('E');
			return true;
			case 'P':
				// Starting a battle with the player using the fuzzy logic library
				FuzzyBattle fuzzyBattle = new FuzzyBattle();
				boolean enemyWon = fuzzyBattle.startBattle(player, this, "fcl/battle.fcl");
				if(enemyWon == true){
					// The player has lost the game!
					maze[getRowPos()][getColPos()].setNodeType(' ');
					player.setGameOver(true);
					maze[r][c].setNodeType('L');
				}else{
					maze[getRowPos()][getColPos()].setNodeType('D');
					this.setHealth(0);
				}
			return enemyWon;
			case 'T':
				maze[getRowPos()][getColPos()].setNodeType(' ');
				maze[r][c].setNodeType('E');
			return true;
			default:
			return false;
		}
	}
	
	/**
	 * Checking what direction the player wants to go in
	 * @param direction
	 */
	private void checkMove(int direction){
		if(this.getHealth() <= 0) return;
		// Moving the enemy object to a new position in the maze
		switch(direction){
			// Going up in the maze
			case 0:
				if (isValidMove(getRowPos() - 1, getColPos())){
					setRowPos(getRowPos() - 1);
				}
			break;
			// Going down in the maze
			case 1:
				if (isValidMove(getRowPos() + 1, getColPos())){
					setRowPos(getRowPos() + 1);
				}
			break;
			// Going left in the maze
			case 2:
				if (isValidMove(getRowPos(), getColPos() - 1)){
					setColPos(getColPos() - 1);
				}
			break;
			// Going right in the maze
			case 3:
				if (isValidMove(getRowPos(), getColPos() + 1)){
					setColPos(getColPos() + 1);
				}
			break;
		}   	  	
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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

	public Thread getInstance() {
		return instance;
	}

	public void setInstance(Thread instance) {
		this.instance = instance;
	}

	public Node[][] getMaze() {
		return maze;
	}

	public void setMaze(Node[][] maze) {
		this.maze = maze;
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}
}