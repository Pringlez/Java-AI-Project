package ie.gmit.sw.ai;

/**  
* DepthLimitedDFSTraversator.java - The depth limited dfs algorithm class, used for traversing the maze to locate the goal node
* @author John Walsh
* @version 1.0 
* @see Traversator
*/
public class DepthLimitedDFSTraversator extends Utility implements Traversator {
	
	private Node[][] maze;
	private int limit;
	private boolean keepRunning = true;
	private long time = System.currentTimeMillis();
	private int visitCount = 0;
	
	public DepthLimitedDFSTraversator(int limit){
		this.limit = limit;
	}
	
	public void traverse(Node[][] maze, Node node){
		System.out.println("\nUsing Depth Limited DFS Traversator to find goal!");
		unvisitA(maze);
		this.maze = maze;
		System.out.println("Search with limit " + limit);
		dfs(node, 1);
	}
	
	private void dfs(Node node, int depth){
		if (!keepRunning || depth > limit) return;
		
		node.setVisited(true);	
		visitCount++;
		
		if (node.isGoalNode()){
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
				dfs(children[i], depth + 1);
			}
		}
	}
}