package ie.gmit.sw.ai;

import java.util.*;

/**  
* AStarTraversator.java - The A* algorithm class, used for traversing the maze to locate the goal node
* @author John Walsh
* @version 1.0 
* @see Traversator
*/
public class AStarTraversator extends Utility implements Traversator {
	
	private Node goal;
	private Node pathGoal;
	private int stepsToExit;
	private boolean countSteps;
	private boolean foundGoal;
	private boolean enemySearch;
	
	public AStarTraversator(Node goal, boolean countSteps){
		this.goal = goal;
		this.countSteps = countSteps;
	}
	
	public AStarTraversator(Node goal, boolean countSteps, boolean enemySearch){
		this.goal = goal;
		this.countSteps = countSteps;
		this.enemySearch = enemySearch;
	}
	
	public void traverse(Node[][] maze, Node node){
		if(!countSteps && !enemySearch){
			// When the player uses a special pickup item to find the shortest path
			System.out.println("Using A Star Traversator - Looking for Maze Exit Goal!\n");
			unvisitA(maze);
		}else if(enemySearch){
			// When the enemy is actively searching for the player
			unvisitB(maze);
		}else{
			// When the game is computing the amount of steps to the exit point
			unvisitB(maze);
		}
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		PriorityQueue<Node> open = new PriorityQueue<Node>(20, (Node current, Node next)-> (current.getPathCost() + current.getHeuristic(goal)) - (next.getPathCost() + next.getHeuristic(goal)));
		java.util.List<Node> closed = new ArrayList<Node>();
    	   	
		open.offer(node);
		node.setPathCost(0);
		while(!open.isEmpty()){
			node = open.poll();
			closed.add(node);
			node.setVisited(true);
			visitCount++;
			
			// When counting for steps to the goal or displaying path to goal node
			if (node.isGoalNode() && node.getNodeType() != 'P' && !enemySearch){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        setStepsToExit(TraversatorStats.printStats(node, time, visitCount, countSteps));
		        setFoundGoal(true);
		        setPathGoal(node);
				break;
			}else if(node.isGoalNode() && node.getNodeType() == 'P' && enemySearch){
				setFoundGoal(true);
		        setPathGoal(node);
				break;
			}
			
			// Sleep for x amount of seconds
			//sleep(1);
			
			//Process adjacent nodes
			Node[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				Node child = children[i];
				int score = node.getPathCost() + 1 + child.getHeuristic(goal);
				int existing = child.getPathCost() + child.getHeuristic(goal);
				
				if ((open.contains(child) || closed.contains(child)) && existing < score){
					continue;
				}else{
					open.remove(child);
					closed.remove(child);
					child.setParent(node);
					child.setPathCost(node.getPathCost() + 1);
					open.add(child);
				}
			}
		}
	}

	public int getStepsToExit() {
		return stepsToExit;
	}

	public void setStepsToExit(int stepsToExit) {
		this.stepsToExit = stepsToExit;
	}

	public boolean isFoundGoal() {
		return foundGoal;
	}

	public void setFoundGoal(boolean foundGoal) {
		this.foundGoal = foundGoal;
	}

	public Node getPathGoal() {
		return pathGoal;
	}

	public void setPathGoal(Node pathGoal) {
		this.pathGoal = pathGoal;
	}

	public boolean isEnemySearch() {
		return enemySearch;
	}

	public void setEnemySearch(boolean enemySearch) {
		this.enemySearch = enemySearch;
	}
}