package ie.gmit.sw.ai;

import java.awt.Color;

/**  
* Utility.java - This abstract class contains shared features for the search algorithms to use
* @author John Walsh
* @version 1.0
*/
public abstract class Utility {
	
	public void unvisit(Node[][] maze){
		for (int i = 0; i < maze.length; i++){
			for (int j = 0; j < maze[i].length; j++){
				maze[i][j].setVisited(false);
				maze[i][j].setParent(null);
				maze[i][j].setColor(Color.LIGHT_GRAY);
			}
		}
	}
	
	public void sleep(int seconds){
		try {
			Thread.sleep(seconds * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}