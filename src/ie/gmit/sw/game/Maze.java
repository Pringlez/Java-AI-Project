package ie.gmit.sw.game;

import java.util.Random;

import ie.gmit.sw.ai.Node;

/**  
* Maze.java - The maze class containing attributes for the maze game generation and setup
* @author John Walsh
* @version 1.0
*/
public class Maze {
	
	private Node[][] maze;
	private Node goal;
	private int goalPos;
	
	public Maze(int rows, int cols){
		maze = new Node[rows][cols];
		init();
		buildMaze();
		buildPaths();
		setGoalNode();
		unvisit();
		addFeature('M', 'X', 15);
		addFeature('A', 'X', 15);
		addFeature('W', 'X', 50);
		addFeature('?', 'X', 25);
		addFeature('B', 'X', 30);
		addFeature('H', 'X', 20);
		addFeature('N', 'X', 10);
	}
	
	/**
	 * Initialize the node maze array
	 */
	private void init(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				maze[row][col] = new Node(row, col);
				maze[row][col].setNodeType('X');
				maze[row][col].setWalkable(false);
			}
		}
	}
	
	/**
	 * Add the maze features to the game, like weapons, armor and health
	 * by replacing the default character
	 * @param feature
	 * @param replace
	 * @param number
	 */
	private void addFeature(char feature, char replace, int number){
		int counter = 0;
		while (counter < number){
			int row = (int) (maze.length * Math.random());
			int col = (int) (maze[0].length * Math.random());
			
			if (maze[row][col].getNodeType() == replace){
				maze[row][col].nodeType = feature;
				counter++;
			}
		}
	}
	
	/**
	 * Builds the maze by randomly adding walkways to the maze
	 */
	private void buildMaze(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length - 1; col++){
				int num = (int) (Math.random() * 10);
				if (num >= 5 && col + 1 < maze[row].length - 1){
					// When char is set to ' ' this means its a free path or area to walk
					maze[row][col + 1].setNodeType(' ');
					maze[row][col + 1].setWalkable(true);
					continue;
				}
				if (row + 1 < maze.length){ //Check south
					maze[row + 1][col].setNodeType(' ');
					maze[row + 1][col].setWalkable(true);
				}
			}
		}	
	}
	
	/**
	 * Generates the paths found next to each node in the maze
	 */
	private void buildPaths(){
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length - 1; col++){
				if(col < maze[row].length - 1)
					if(maze[row][col + 1].isWalkable()){
						maze[row][col].addPath(Node.Direction.West);
					}
				if(col > 0)
					if(maze[row][col - 1].isWalkable()){
						maze[row][col].addPath(Node.Direction.East);
					}
				if(row < maze.length - 1)
					if(maze[row + 1][col].isWalkable()){
						maze[row][col].addPath(Node.Direction.North);
					}
				if(row > 0)
					if(maze[row - 1][col].isWalkable()){
						maze[row][col].addPath(Node.Direction.South);
					}
			}
		}
	}
	
	/**
	 * Randomly create the goal node in the maze
	 */
	public void setGoalNode() {
		
		Random random = new Random();
		int randRow = 0;
		int randCol = 0;
		boolean goalSet = false;
		
		while(goalSet != true){
			
			this.setGoalPos(random.nextInt((2 - 0) + 1) + 0);
			
			switch(this.getGoalPos()){
				case 0:
					// Creates the nodes on the bottom side of the maze
					randRow = random.nextInt(((maze.length - 15) - (maze.length - 15)) + 1) + (maze.length - 15);
					randCol = random.nextInt((maze[0].length - 5) + 1) + 5;
				break;
				case 1:
					// Creates the nodes on the right side of the maze
					randRow = random.nextInt(((maze.length - 15) - 1) + 1) + 1;
					randCol = random.nextInt(((maze[0].length - 1) - (maze[0].length - 3)) + 1) + (maze[0].length - 3);
				break;
				case 2:
					// Creates the nodes on the top side of the maze
					randRow = random.nextInt((2 - 0) + 1) + 0;
					randCol = random.nextInt((maze[0].length - 5) + 1) + 5;
				break;
				default:
					randRow = random.nextInt(((maze.length - 15) - 1) + 1) + 1;
					randCol = random.nextInt(((maze[0].length - 1) - (maze[0].length - 3)) + 1) + (maze[0].length - 3);
				break;
			}
			
			try {
				if(maze[randRow][randCol].isWalkable()){
					maze[randRow][randCol].setGoalNode(true);
					maze[randRow][randCol].setNodeType('G');
					maze[randRow][randCol].setWalkable(true);
					goal = maze[randRow][randCol];
					goalSet = true;
				}
			} catch (Exception e) {
			}
		}
	}
	
	/**
	 * Un-visit all nodes in the maze
	 */
	protected void unvisit(){
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
			}
		}
	}
	
	public Node getGoalNode(){
		return this.goal;
	}
	
	public Node[][] getMaze(){
		return this.maze;
	}
	
	public int getGoalPos() {
		return goalPos;
	}

	public void setGoalPos(int goalPos) {
		this.goalPos = goalPos;
	}

	public String toString(){
		StringBuffer sb = new StringBuffer();
		for (int row = 0; row < maze.length; row++){
			for (int col = 0; col < maze[row].length; col++){
				sb.append(maze[row][col]);
				if (col < maze[row].length - 1) sb.append(",");
			}
			sb.append("\n");
		}
		return sb.toString();
	}
}