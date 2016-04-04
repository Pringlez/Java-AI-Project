package ie.gmit.sw.game;

import java.util.Collections;
import java.util.LinkedList;
import java.util.Random;

import ie.gmit.sw.ai.AStarTraversator;
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
	private boolean run;
	private int playerRowTemp;
	private int playerColTemp;
	private int minUpdateTime;
	private int maxUpdateTime;
	private Node pathGoal;
	private AStarTraversator traverse;
	private LinkedList<Node> nodeListPath;
	
	// Idea: Make enemy walk only when the player walks
	
	public Enemy() {
		super();
		setStrength(0);
		setDifficulty(0);
		setBoss(false);
		setAlgorithm(0);
	}
	
	public Enemy(int id, int health, int armor, int strength, int difficulty, boolean boss) {
		super(health, armor);
		setId(id);
		setStrength(strength);
		setDifficulty(difficulty);
		setBoss(boss);
		setAlgorithm(0);
	}
	
	@Override
	public void run() {
		while(isRun()){
			try {
	            Thread.sleep(new Random().nextInt((getMaxUpdateTime() - getMinUpdateTime()) + 1) + getMinUpdateTime());
	            switch(getAlgorithm()){
	            	case 0:
	            		checkMove(new Random().nextInt((3 - 0) + 1) + 0);
	            	break;
	            	case 1:
	            		aStarFindPlayer();
	            	break;
	            	default:
	            		checkMove(new Random().nextInt((3 - 0) + 1) + 0);
	            	break;
	            }
	            
	        } catch (InterruptedException error) {
	            System.out.println("Error - " + error);
	        }
		}
	}

	/**
	 * Checks if the player is making a valid move, return false if the move is invalid
	 * @param r
	 * @param c
	 * @return Returns true if its a valid move
	 */
	private boolean isValidMove(int r, int c){
		if(getPlayer().isGameOver()) return false;
		if((r < 0) || (c < 0) || !(r <= getMaze().length - 1 && c <= getMaze()[r].length - 1)) return false;
		
		switch(getMaze()[r][c].getNodeType()){
			case ' ':
				getMaze()[getRowPos()][getColPos()].setNodeType(' ');
				getMaze()[r][c].setNodeType('E');
				if(this.isBoss())
					getMaze()[r][c].setNodeType('F');
				getMaze()[r][c].setEnemyID(getMaze()[getRowPos()][getColPos()].getEnemyID());
				getMaze()[getRowPos()][getColPos()].setEnemyID(0);
			return true;
			case 'P':
				// Starting a battle with the player using the fuzzy logic library
				FuzzyBattle fuzzyBattle = new FuzzyBattle();
				boolean enemyWon = fuzzyBattle.startBattle(getPlayer(), this, "fcl/battle.fcl");
				if(enemyWon == true){
					// The player has lost the game!
					getMaze()[getRowPos()][getColPos()].setNodeType(' ');
					getMaze()[getRowPos()][getColPos()].setEnemyID(0);
					getPlayer().setGameOver(true);
					getMaze()[r][c].setNodeType('L');
					new PlaySound("res/lose_game.wav");
				}else{
					getMaze()[getRowPos()][getColPos()].setNodeType('D');
					getMaze()[getRowPos()][getColPos()].setEnemyID(0);
					this.setHealth(0);
					new PlaySound("res/win_fight.wav");
				}
			return enemyWon;
			case 'T':
				getMaze()[getRowPos()][getColPos()].setNodeType(' ');
				getMaze()[r][c].setNodeType('E');
				if(this.isBoss())
					getMaze()[r][c].setNodeType('F');
				getMaze()[r][c].setEnemyID(getMaze()[getRowPos()][getColPos()].getEnemyID());
				getMaze()[getRowPos()][getColPos()].setEnemyID(0);
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
			default:
				if (isValidMove(getRowPos() + 1, getColPos())){
					setRowPos(getRowPos() + 1);
				}
			break;
		}   	  	
	}
	
	/**
	 * The A* algorithm search will be used here to find the player
	 */
	private void aStarFindPlayer() {
		// If the player is 60 steps or lower from the maze exit goal then search and destroy the player!
		// Keep the enemy moving while the player is 60 steps and above away from the maze's exit
		if(getPlayer().getStepsToExit() > 60 || getPlayer().getStepsToExit() <= 0){
			setMinUpdateTime(600);
			setMaxUpdateTime(750);
			checkMove(new Random().nextInt((3 - 0) + 1) + 0);
			return;
		}
		
		if(getPlayerRowT() != getPlayer().getRowPos() || getPlayerColT() != getPlayer().getColPos()){
			setMinUpdateTime(400);
			setMaxUpdateTime(500);
			setNodeListPath(new LinkedList<Node>());
			setPlayerRowT(getPlayer().getRowPos());
			setPlayerColT(getPlayer().getColPos());
			setTraverse(new AStarTraversator(getMaze()[getPlayer().getRowPos()][getPlayer().getColPos()], false, true));
			getTraverse().traverse(getMaze(), getMaze()[getRowPos()][getColPos()]);
			if(getTraverse().isFoundGoal()){
				setPathGoal(getTraverse().getPathGoal());
				while (getPathGoal() != null){
					getNodeListPath().add(getPathGoal());
					setPathGoal(getPathGoal().getParent());
				}
				Collections.reverse(getNodeListPath());
				getNodeListPath().removeFirst();
			}
		}
		
		if(getTraverse().isFoundGoal()){
			
			Node nextPath = getNodeListPath().poll();
			boolean foundNextPath = false;
			
			while (nextPath != null && !foundNextPath){
				
				if(nextPath.getRow() == (getRowPos() - 1)){
					if (isValidMove(getRowPos() - 1, getColPos())){
						setRowPos(getRowPos() - 1);
						foundNextPath = true;
						break;
					}
				}
				if(nextPath.getRow() == (getRowPos() + 1)){
					if (isValidMove(getRowPos() + 1, getColPos())){
						setRowPos(getRowPos() + 1);
						foundNextPath = true;
						break;
					}
				}
				if(nextPath.getCol() == (getColPos() - 1)){
					if (isValidMove(getRowPos(), getColPos() - 1)){
						setColPos(getColPos() - 1);
						foundNextPath = true;
						break;
					}
				}
				if(nextPath.getCol() == (getColPos() + 1)){
					if (isValidMove(getRowPos(), getColPos() + 1)){
						setColPos(getColPos() + 1);
						foundNextPath = true;
						break;
					}
				}
			}
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

	public boolean isRun() {
		return run;
	}

	public void setRun(boolean run) {
		this.run = run;
	}

	public int getPlayerRowT() {
		return playerRowTemp;
	}

	public void setPlayerRowT(int playerRowT) {
		this.playerRowTemp = playerRowT;
	}

	public int getPlayerColT() {
		return playerColTemp;
	}

	public void setPlayerColT(int playerColT) {
		this.playerColTemp = playerColT;
	}

	public int getPlayerRowTemp() {
		return playerRowTemp;
	}

	public void setPlayerRowTemp(int playerRowTemp) {
		this.playerRowTemp = playerRowTemp;
	}

	public int getPlayerColTemp() {
		return playerColTemp;
	}

	public void setPlayerColTemp(int playerColTemp) {
		this.playerColTemp = playerColTemp;
	}

	public int getMinUpdateTime() {
		return minUpdateTime;
	}

	public void setMinUpdateTime(int minUpdateTime) {
		this.minUpdateTime = minUpdateTime;
	}

	public int getMaxUpdateTime() {
		return maxUpdateTime;
	}

	public void setMaxUpdateTime(int maxUpdateTime) {
		this.maxUpdateTime = maxUpdateTime;
	}

	public Node getPathGoal() {
		return pathGoal;
	}

	public void setPathGoal(Node pathGoal) {
		this.pathGoal = pathGoal;
	}

	public AStarTraversator getTraverse() {
		return traverse;
	}

	public void setTraverse(AStarTraversator traverse) {
		this.traverse = traverse;
	}
	
	public LinkedList<Node> getNodeListPath() {
		return nodeListPath;
	}

	public void setNodeListPath(LinkedList<Node> nodeListPath) {
		this.nodeListPath = nodeListPath;
	}
}