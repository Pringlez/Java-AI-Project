package ie.gmit.sw.ai;

import java.awt.Color;

/**  
* TraversatorStats.java - This class handles displaying info about the goals path
* @author John Walsh
* @version 1.0
*/
public class TraversatorStats {
	
	private static boolean debug = true;
	
	public static int printStats(Node node, long time, int visitCount, boolean countSteps){
		double depth = 0;
		int count = 0;
		while (node != null){			
			node = node.getParent();
			if (node != null){
				if(!countSteps)
					if(node.getNodeType() != 'P'){
						node.setColor(Color.YELLOW);
						node.setNodeType('T');
						node.setWalkable(true);
					}
				count++;
			}
			depth++;			
		}
		
		if(debug){
			if(!countSteps){
		        System.out.println("\nVisited " + visitCount + " nodes in " + time + "ms.");
		        System.out.println("Found goal at a depth of " + String.format("%.0f", depth));
		        System.out.println("EBF = B* = k^(1/d) = " + String.format("%.2f", Math.pow((double) visitCount, (1.00d / depth))));
			}
		}
		
		return count;
	}
}