package ie.gmit.sw.game;

import java.util.ArrayList;
import java.util.Random;

import ie.gmit.sw.ai.Node;
import ie.gmit.sw.gui.GameFrame;

/**
 * The game controller class that handles all maze, player and enemy instances
 * @author John
 *
 */
public class Game {
	
	private Maze model;
	private Node[][] maze;
	private Player player;
	private ArrayList<Enemy> enemies;
	
	public Game() {
	}
	
	public Game(Maze model, Node[][] maze, Player player, ArrayList<Enemy> enemies) {
		setModel(model);
		setMaze(maze);
		setPlayer(player);
		setEnemies(enemies);
	}
	
	/**
	 * Places the player randomly in the maze
	 * @param goalPos The goal position in the maze, depending on the number from zero to 
	 * three it'll select the opposite position in the maze
	 */
	public void placePlayer(int goalPos){
		
		getPlayer().setWeapon("Unarmed");
		
		Random random = new Random();
		boolean playerPosSet = false;
		
		// Continue to loop until a good position is found
		while(playerPosSet != true){
			
			switch(goalPos){
				case 0:
					// Creates the player on the top side of the maze
					getPlayer().setRowPos(random.nextInt((3 - 2) + 1) + 2);
					getPlayer().setColPos(random.nextInt((getMaze()[0].length - 5) + 1) + 5);
				break;
				case 1:
					// Creates the player on the left side of the maze
					getPlayer().setRowPos(random.nextInt(((getMaze().length - 15) - 1) + 1) + 1);
					getPlayer().setColPos(random.nextInt((3 - 2) + 1) + 2);
				break;
				case 2:
					// Creates the player on the bottom side of the maze
					getPlayer().setRowPos(random.nextInt(((getMaze().length - 15) - (getMaze().length - 15)) + 1) + (getMaze().length - 15));
					getPlayer().setColPos(random.nextInt((getMaze()[0].length - 5) + 1) + 5);
				break;
				default:
					getPlayer().setRowPos(random.nextInt(((getMaze().length - 15) - 1) + 1) + 1);
					getPlayer().setColPos(random.nextInt((3 - 2) + 1) + 2);
				break;
			}
			
			// Checking if the area is walkable, if true then place the player and setting the node type
	    	try {
				if(getMaze()[getPlayer().getRowPos()][getPlayer().getColPos()].isWalkable()){
					getMaze()[getPlayer().getRowPos()][getPlayer().getColPos()].setNodeType('P');
					getMaze()[getPlayer().getRowPos()][getPlayer().getColPos()].setGoalNode(true);
					playerPosSet = true;
				}
			} catch (Exception e) {
			}
		} 		
	}
	
	/**
	 * Creates and spawns the enemies in the maze, enemy strength depends on the game difficulty
	 * @param gameDifficulty The game difficulty setting
	 */
	public void setupEnemies(String gameDifficulty){
		
		int amount;
		int health;
		int armor;
		int strength;
		int difficulty;
		int bosses;
		boolean isBoss = false;
		
		Random random = new Random();
		
		// Randomly choosing the enemy's strengths, depending on difficulty setting
		switch(gameDifficulty){
			case "Easy":
				amount = 35;
				health = random.nextInt((60 - 25) + 1) + 25;
				armor = random.nextInt((35 - 5) + 1) + 5;
				strength = random.nextInt((60 - 30) + 1) + 30;
				difficulty = 0;
				bosses = 1;
			break;
			case "Normal":
				amount = 50;
				health = random.nextInt((75 - 55) + 1) + 55;
				armor = random.nextInt((45 - 25) + 1) + 25;
				strength = random.nextInt((70 - 30) + 1) + 30;
				difficulty = 1;
				bosses = 2;
			break;
			case "Hard":
				amount = 75;
				health = random.nextInt((100 - 40) + 1) + 40;
				armor = random.nextInt((75 - 50) + 1) + 50;
				strength = random.nextInt((80 - 30) + 1) + 30;
				difficulty = 2;
				bosses = 5;
			break;
			default:
				amount = 50;
				health = random.nextInt((75 - 55) + 1) + 55;
				armor = random.nextInt((45 - 25) + 1) + 25;
				strength = random.nextInt((70 - 30) + 1) + 30;
				difficulty = 1;
				bosses = 2;
			break;
		}
		
		setEnemies(new ArrayList<Enemy>());
		
		/* Creating new enemies with separate threads, game difficult defines
		 * how each enemy object is created, individual enemy has their own strengths and weaknesses  
		 */
		for(int i = 0; i < amount; i++){
			
			if(bosses > 0){
				isBoss = true;
				bosses--;
			}
			
			// Create an enemy object & create new thread
			Runnable enemy = new Enemy(i, health, armor, strength, difficulty, isBoss);
			Thread thread = new Thread(enemy);
			getEnemies().add((Enemy) enemy);
			getEnemies().get(i).setInstance(thread);
			getEnemies().get(i).setMaze(getMaze());
			getEnemies().get(i).setPlayer(getPlayer());
			getEnemies().get(i).setRun(true);
			getEnemies().get(i).setMinUpdateTime(600);
			getEnemies().get(i).setMaxUpdateTime(750);
			
			// Only boss enemies get the brains to find and destroy the player
			if(isBoss)
				getEnemies().get(i).setAlgorithm(random.nextInt((1 - 1) + 1) + 1);
			
			boolean enemyPosSet = false;
			
			// Continue to loop until a good position is found
			while(enemyPosSet != true){
				getEnemies().get(i).setRowPos((int)(GameFrame.MAZE_DIMENSION * Math.random()));
				getEnemies().get(i).setColPos((int)(GameFrame.MAZE_DIMENSION * Math.random()));
				
				// Checking if the area is walkable, if true then place enemy
		    	if(getMaze()[getEnemies().get(i).getRowPos()][getEnemies().get(i).getColPos()].isWalkable()){
			    	getMaze()[getEnemies().get(i).getRowPos()][getEnemies().get(i).getColPos()].setNodeType('E');
		    		if(getEnemies().get(i).isBoss())
		    			getMaze()[getEnemies().get(i).getRowPos()][getEnemies().get(i).getColPos()].setNodeType('F');
			    	getMaze()[getEnemies().get(i).getRowPos()][getEnemies().get(i).getColPos()].setEnemyID(i);
			    	enemyPosSet = true;
		    	}
			}
			
			isBoss = false;
			thread.start();
		}
	}
	
	/**
	 * Kills the previously running enemy threads
	 */
	public void killEnemyThreads() {
		if(getEnemies() == null || getEnemies().size() <= 0) return;
		for(int i = 0; i < getEnemies().size(); i++){
			getEnemies().get(i).setHealth(0);
			getEnemies().get(i).setRun(false);
		}
	}

	public Maze getModel() {
		return model;
	}

	public void setModel(Maze model) {
		this.model = model;
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

	public ArrayList<Enemy> getEnemies() {
		return enemies;
	}

	public void setEnemies(ArrayList<Enemy> enemies) {
		this.enemies = enemies;
	}
}