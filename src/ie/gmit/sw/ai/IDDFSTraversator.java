package ie.gmit.sw.ai;

/**  
* IDDFSTraversator.java - The incremental deepening dfs algorithm class, used for traversing the maze to locate the goal node
* @author John Walsh
* @version 1.0 
* @see Traversator
*/
public class IDDFSTraversator extends Utility implements Traversator {
	
	private Node[][] maze;
	private boolean keepRunning = true;
	private long time = System.currentTimeMillis();
	private int visitCount = 0;
	
	public void traverse(Node[][] maze, Node start){
		System.out.println("Using IDDFS Traversator - Looking for Maze Exit Goal!\n");
		unvisitA(maze);
		this.maze = maze;
		int limit = 1;
		
		while(keepRunning){
			dfs(start, 0, limit);
			
			if (keepRunning){
				//sleep(1);
	      		limit++;       				
			}
      	}
	}

	private void dfs(Node node, int depth, int limit){
		if (!keepRunning || depth > limit) return;		
		node.setVisited(true);	
		visitCount++;
		
		if (node.isGoalNode() && node.nodeType != 'P'){
	        time = System.currentTimeMillis() - time; //Stop the clock
	        TraversatorStats.printStats(node, time, visitCount, false);
	        keepRunning = false;
			return;
		}
		
		// Sleep for x amount of seconds
		//sleep(1);
		
		Node[] children = node.children(maze);
		for (int i = 0; i < children.length; i++) {
			if (children[i] != null && !children[i].isVisited()){
				children[i].setParent(node);
				dfs(children[i], depth + 1, limit);
			}
		}
	}
}