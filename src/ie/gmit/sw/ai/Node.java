package ie.gmit.sw.ai;

import java.awt.Color;

/**  
* Node.java - The node class is responsible for keeping track of individual nodes in the maze
* @author John Walsh
* @version 1.0
*/
public class Node {
	
	public enum Direction {North, South, East, West};
	private Node parent;
	private Color color = Color.LIGHT_GRAY;
	private Direction[] paths = null;
	public boolean visited =  false;
	public boolean goal;
	public boolean walkable;
	public char nodeType;
	private int row = -1;
	private int col = -1;
	private int distance;
	private int enemyID;
	
	public Node(int row, int col) {
		this.row = row;
		this.col = col;
	}

	/**
	 * Adds the path directions found next to the current node
	 * @param direction The direction to be added
	 */
	public void addPath(Direction direction) {
		int index = 0;
		if (paths == null){
			paths = new Direction[index + 1];		
		}else{
			index = paths.length;
			Direction[] temp = new Direction[index + 1];
			for (int i = 0; i < paths.length; i++) temp[i] = paths[i];
			paths = temp;
		}
		
		paths[index] = direction;
	}
	
	/**
	 * Checks if the node has a direction from its current position in the maze by checking
	 * it's children nodes and then return the result
	 * @param direction The direction to be checked
	 * @return Return true if it has direction
	 */
	public boolean hasDirection(Direction direction){
		if(paths == null) return false;
		try {
			for (int i = 0; i < paths.length; i++) {
				if (paths[i] == direction) return true;
			}
			return false;
		} catch (Exception error) {
			System.out.println("Error - " + error);
			return false;
		}
	}
	
	/**
	 * Adds the path direction to the child node if its walkable
	 * @param maze The node maze array object
	 * @return Return the maze array object
	 */
	public Node[] children(Node[][] maze){		
		java.util.List<Node> children = new java.util.ArrayList<Node>();
				
		if (row > 0 && maze[row - 1][col].hasDirection(Direction.South) && maze[row - 1][col].isWalkable()) children.add(maze[row - 1][col]); //Add North
		if (row < maze.length - 1 && maze[row + 1][col].hasDirection(Direction.North) && maze[row + 1][col].isWalkable()) children.add(maze[row + 1][col]); //Add South
		if (col > 0 && maze[row][col - 1].hasDirection(Direction.East) && maze[row][col - 1].isWalkable()) children.add(maze[row][col - 1]); //Add West
		if (col < maze[row].length - 1 && maze[row][col + 1].hasDirection(Direction.West) && maze[row][col + 1].isWalkable()) children.add(maze[row][col + 1]); //Add East
		
		return (Node[]) children.toArray(new Node[children.size()]);
	}

	/**
	 * Checks for adjacent nodes
	 * @param maze The node maze array object
	 * @return Return the maze array object
	 */
	public Node[] adjacentNodes(Node[][] maze){
		java.util.List<Node> adjacents = new java.util.ArrayList<Node>();
		
		if (row > 0) adjacents.add(maze[row - 1][col]); //Add North
		if (row < maze.length - 1) adjacents.add(maze[row + 1][col]); //Add South
		if (col > 0) adjacents.add(maze[row][col - 1]); //Add West
		if (col < maze[row].length - 1) adjacents.add(maze[row][col + 1]); //Add East
		
		return (Node[]) adjacents.toArray(new Node[adjacents.size()]);
	}
	
	/**
	 * The search heuristic used by algorithm that implement a heuristic search process
	 * @param goal The goal node in the maze
	 * @return Return the heuristic number
	 */
	public int getHeuristic(Node goal){
		double x1 = this.col;
		double y1 = this.row;
		double x2 = goal.getCol();
		double y2 = goal.getRow();
		return (int) Math.sqrt(Math.pow((x2 - x1), 2) + Math.pow((y2 - y1), 2));
	}
	
	public int getRow() {
		return row;
	}

	public int getCol() {
		return col;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}
	
	public Direction[] getPaths() {
		return paths;
	}

	public boolean isVisited() {
		return visited;
	}

	public void setVisited(boolean visited) {
		this.visited = visited;
	}

	public boolean isGoalNode() {
		return goal;
	}

	public void setGoalNode(boolean goal) {
		this.goal = goal;
	}
	
	public int getPathCost() {
		return distance;
	}

	public void setPathCost(int distance) {
		this.distance = distance;
	}

	public boolean isWalkable() {
		return walkable;
	}

	public void setWalkable(boolean walkable) {
		this.walkable = walkable;
	}

	public char getNodeType() {
		return nodeType;
	}

	public void setNodeType(char nodeType) {
		this.nodeType = nodeType;
	}

	public int getEnemyID() {
		return enemyID;
	}

	public void setEnemyID(int enemyID) {
		this.enemyID = enemyID;
	}

	public String toString() {
		return "[" + row + "/" + col + "]";
	}
}