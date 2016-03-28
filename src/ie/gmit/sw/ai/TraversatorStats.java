package ie.gmit.sw.ai;

import java.awt.Color;

/**  
* TraversatorStats.java - This class handles displaying info about the goals path
* @author John Walsh
* @version 1.0
*/
public class TraversatorStats {
	
	private static boolean debug = false;
	
	public static void printStats(Node node, long time, int visitCount){
		double depth = 0;
		
		while (node != null){			
			node = node.getParent();
			if (node != null){
				node.setColor(Color.YELLOW);
				node.setNodeType('T');
				node.setWalkable(true);
			}
			depth++;			
		}
		
		if(debug){
	        System.out.println("Visited " + visitCount + " nodes in " + time + "ms.");
	        System.out.println("Found goal at a depth of " + String.format("%.0f", depth));
	        System.out.println("EBF = B* = k^(1/d) = " + String.format("%.2f", Math.pow((double) visitCount, (1.00d / depth))));           
	        //SoundEffects.ALARM.play();
		}
	}
}