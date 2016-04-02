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
	private int stepsToExit;
	private boolean countSteps;
	private boolean foundGoal;
	
	public AStarTraversator(Node goal, boolean countSteps){
		this.goal = goal;
		this.countSteps = countSteps;
	}
	
	public void traverse(Node[][] maze, Node node){
		if(!countSteps){
			System.out.println("\nUsing A Star Traversator to find goal!");
			unvisitA(maze);
		}else{
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
			
			if (node.isGoalNode()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        setStepsToExit(TraversatorStats.printStats(node, time, visitCount, countSteps));
		        setFoundGoal(true);
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
}