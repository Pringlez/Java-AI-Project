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
        System.out.println(survivability.getDefaultValue());
        System.out.println("Value: " + survivability.getValue());
        
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
        	System.out.println("Game Over!");
        	enemyWon = true;
        }
        
        if(!enemyWon)
        	player.setScore(player.getScore() + 25);
        
		return enemyWon;
	}
}