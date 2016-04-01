package ie.gmit.sw.game;

import net.sourceforge.jFuzzyLogic.FIS;
import net.sourceforge.jFuzzyLogic.FunctionBlock;
import net.sourceforge.jFuzzyLogic.rule.Variable;

/**  
* FuzzyBattle.java - This class handles all the battle logic between the player and enemies
* @author John Walsh
* @version 1.0
*/
public class FuzzyBattle {

	public FuzzyBattle(){
	}
	
	/**
	 * Starts a battle between the player and an enemy, using the fuzzy logic 
	 * API to determine the outcome of the fight
	 * @param player The player object instance
	 * @param enemy The enemy object class instance
	 * @param fclFilePath The path to the fcl logic file
	 * @return Return true if the enemy wins the fight
	 */
	public boolean startBattle(Player player, Enemy enemy, String fclFilePath){
		
        FIS fis = FIS.load(fclFilePath, true);

        if(fis == null){ 
            System.err.println("Error loading: '" + fclFilePath + "'");
            return true;
        }

        FunctionBlock functionBlock = fis.getFunctionBlock("battle");

        fis.setVariable("health", player.getHealth());
        fis.setVariable("armor", player.getArmor());
        fis.setVariable("weapon", player.getWeaponStrength());
        fis.evaluate();

        Variable survivability = functionBlock.getVariable("survivability");
        //System.out.println("Default Value: " + survivability.getDefaultValue());
        System.out.println("Survivability Percentage: " + survivability.getValue() + "%");
        
        boolean enemyWon = false;
        
    	player.setHealth((int)(player.getHealth() - (100 - survivability.getValue())));
    	player.setArmor((int)(player.getArmor() - (100 - survivability.getValue() + 10)));
    	player.setWeaponStrength((int)(player.getWeaponStrength() * (survivability.getValue() / 100)));
    	
    	if(!player.getWeapon().equals("Sword") || player.getWeaponStrength() < 35){
    		player.setWeaponStrength(0);
    		player.setWeapon("Unarmed");
    	}
        
    	if(player.getArmor() <= 0){
        	player.setArmor(0);
        }
    	
        if(player.getHealth() <= 0){
        	player.setHealth(0);
        	player.setGameOver(true);
        	System.out.println("Game Over!");
        	System.out.println("Player Score: " + player.getScore());
        	enemyWon = true;
        }
        
        if(!enemyWon){
        	player.setScore(player.getScore() + 25);
        	System.out.println("Player Health: " + player.getHealth());
        	System.out.println("Player Armor: " + player.getArmor());
        }
        
		return enemyWon;
	}
}