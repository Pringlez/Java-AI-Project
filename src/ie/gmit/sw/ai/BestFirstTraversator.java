package ie.gmit.sw.ai;

import java.util.*;

/**  
* BestFirstTraversator.java - The bfs algorithm class, used for traversing the maze to locate the goal node
* @author John Walsh
* @version 1.0 
* @see Traversator
*/
public class BestFirstTraversator extends Utility implements Traversator {
	
	private Node goal;
	
	public BestFirstTraversator(Node goal){
		this.goal = goal;
	}
	
	public void traverse(Node[][] maze, Node node){
		System.out.println("Using Best First Traversator - Looking for Maze Exit Goal!\n");
		unvisitA(maze);
		LinkedList<Node> queue = new LinkedList<Node>();
		queue.addFirst(node);
		
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
		while(!queue.isEmpty()){
			node = queue.poll();
			node.setVisited(true);	
			visitCount++;
			
			if (node.isGoalNode() && node.nodeType != 'P'){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(node, time, visitCount, false);
				break;
			}
			
			// Sleep for x amount of seconds
			//sleep(1);
			
			Node[] children = node.children(maze);
			for (int i = 0; i < children.length; i++) {
				if (children[i] != null && !children[i].isVisited()){
					children[i].setParent(node);
					queue.addFirst(children[i]);
				}
			}
			
			//Sort the whole queue. Effectively a priority queue, first in, best out
			Collections.sort(queue,(Node current, Node next) -> current.getHeuristic(goal) - next.getHeuristic(goal));		
		}
	}
}