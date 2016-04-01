package ie.gmit.sw.ai;

/**  
* BasicHillClimbingTraversator.java - The hill climbing algorithm class, used for traversing the maze to locate the goal node
* @author John Walsh
* @version 1.0 
* @see Traversator
*/
public class BasicHillClimbingTraversator extends Utility implements Traversator {
	
	private Node goal;
	
	public BasicHillClimbingTraversator(Node goal){
		this.goal = goal;
	}
	
	public void traverse(Node[][] maze, Node node){
		System.out.println("\nUsing Basic Hill Climbing Traversator to find goal!");
		unvisitA(maze);
        long time = System.currentTimeMillis();
    	int visitCount = 0;
    	
    	Node next = null;
		while(node != null){
			node.setVisited(true);	
			visitCount++;
			
			if (node.isGoalNode()){
		        time = System.currentTimeMillis() - time; //Stop the clock
		        TraversatorStats.printStats(node, time, visitCount, false);
				break;
			}
			
			// Sleep for x amount of seconds
			//sleep(1);
			
			Node[] children = node.children(maze);			
			int fnext = Integer.MAX_VALUE;			
			for (int i = 0; i < children.length; i++) {					
				if (children[i].getHeuristic(goal) < fnext){
					next = children[i];
					fnext = next.getHeuristic(goal);	
				}
			}
						
			if (fnext >= node.getHeuristic(goal)){
				System.out.println("Cannot improve on current node " + node.toString() + " \nh(n)=" + node.getHeuristic(goal) + " = Local Optimum...");
				break;
			}
			node = next;	
			next = null;
		}
	}
}