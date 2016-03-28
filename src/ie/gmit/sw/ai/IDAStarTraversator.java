package ie.gmit.sw.ai;

/**  
* IDAStarTraversator.java - The incremental deepening A* algorithm class, used for traversing the maze to locate the goal node
* @author John Walsh
* @version 1.0 
* @see Traversator
*/
public class IDAStarTraversator extends Utility implements Traversator {
	
	private Node[][] maze;
	private Node goal;
	private int visitCount = 0;
	private long time;
	
	public IDAStarTraversator(Node goal){
		this.goal = goal;
	}
	
	public void traverse(Node[][] maze, Node start){
		System.out.println("\nUsing IDA Star Traversator!");
		unvisit(maze);
		this.maze = maze;
		time = System.currentTimeMillis();
		
		//Start the algorithm and the clock
		int bound = start.getHeuristic(goal);
		boolean complete = false;		
		do{
			int result = contour(start, 0, bound);
			if (result == Integer.MIN_VALUE) complete = true; //Found
			if (result == Integer.MAX_VALUE) System.exit(1); //Not found				
			bound = result;
			
			try { //Pause before next iteration
				if (!complete) {
					// Sleep for x amount of seconds
					//sleep(1);
					unvisit(maze);					
				}
			} catch (Exception e) {
				e.printStackTrace();
			}			
		}while(!complete);		
		
		//Output the stats
		if (complete){
	        time = System.currentTimeMillis() - time; //Stop the clock
	        TraversatorStats.printStats(goal, time, visitCount);
	        
		}else{
			System.out.println("Unable to find goal node.");
		}		
	}
	
	private int contour(Node node, int g, int bound){
		node.setVisited(true);
		node.setPathCost(g);
		visitCount++;
		
		// Sleep for x amount of seconds
		//sleep(1);
		
		int f = g + node.getHeuristic(goal);
		if (f > bound) return f;
		if (node.isGoalNode()) return Integer.MIN_VALUE; //Denotes found
		int min = Integer.MAX_VALUE; //Denotes not found
		
		Node[] children = node.children(maze);
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].isVisited()){		
				children[i].setParent(node);
				int result = contour(children[i], g + 1, bound);
				if (result == Integer.MIN_VALUE) return Integer.MIN_VALUE;				
				if (result < min) min = result;
			}
		}
		return min;		
	}
}