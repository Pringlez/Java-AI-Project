package ie.gmit.sw.ai;

import java.awt.Color;

/**  
* Utility.java - This abstract class contains shared features for the search algorithms to use
* @author John Walsh
* @version 1.0
*/
public abstract class Utility {
	
	/**
	 * Sets the nodes back to their default values
	 * @param maze The node maze array object
	 */
	public void unvisit(Node[][] maze){
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				maze[i][j].setVisited(false);
				if(maze[i][j].getNodeType() == 'T')
					maze[i][j].setNodeType(' ');
				maze[i][j].setParent(null);
				maze[i][j].setColor(Color.LIGHT_GRAY);
			}
		}
	}
	
	/**
	 * Puts the thread to sleep for x amount of seconds
	 * @param seconds Amount of seconds to suspend the thread
	 */
	public void sleep(int seconds){
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}