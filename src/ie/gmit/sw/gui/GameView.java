package ie.gmit.sw.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import java.io.IOException;

import javax.imageio.*;
import javax.swing.*;

import ie.gmit.sw.ai.Node;
import ie.gmit.sw.game.Game;

/**  
* GameView.java - The game view class handles all the image manipulation and drawing
* @author John Walsh
* @version 1.0
*/

public class GameView extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private int cellspan;	
	private int cellpadding;
	private int currentRow;
	private int currentCol;
	private boolean zoomOut;
	private int imageIndex;
	private int playerState;
	private int enemyState;
	private int enemyBossState;
	private Node[][] maze;
	private BufferedImage[] images;
	private Timer timer;
	
	public GameView() {
	}
	
	public GameView(Game game) {
		init();
		this.maze = game.getMaze();
		this.imageIndex = -1;
		this.playerState = 6;
		this.enemyState = 7;
		this.enemyBossState = 18;
		this.cellspan = 5;
		this.cellpadding = 2;
		this.timer = new Timer(100, this);
		this.timer.start();
		setBackground(Color.LIGHT_GRAY);
		setDoubleBuffered(true);
	}
	
	public void setCurrentRow(int row) {
		if (row < cellpadding){
			currentRow = cellpadding;
		}else if (row > (maze.length - 1) - cellpadding){
			currentRow = (maze.length - 1) - cellpadding;
		}else{
			currentRow = row;
		}
	}

	public void setCurrentCol(int col) {
		if (col < cellpadding){
			currentCol = cellpadding;
		}else if (col > (maze[currentRow].length - 1) - cellpadding){
			currentCol = (maze[currentRow].length - 1) - cellpadding;
		}else{
			currentCol = col;
		}
	}

	public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
              
        cellspan = zoomOut ? maze.length : 5;         
        final int size = 800 / cellspan;
        g2.drawRect(0, 0, 800, 800);
        
        for(int row = 0; row < cellspan; row++) {
        	for (int col = 0; col < cellspan; col++){  
        		int x1 = col * size;
        		int y1 = row * size;
        		
        		char ch = 'X';
        		
        		ch = maze[row][col].getNodeType();
        		
        		if (zoomOut){
        			if(ch == 'E'){
        				g2.setColor(Color.RED);
    					g2.fillRect(x1, y1, size, size);
        			}
        			if(ch == 'F'){
        				g2.setColor(Color.MAGENTA);
    					g2.fillRect(x1, y1, size, size);
        			}
        			if(ch == 'D'){
        				g2.setColor(Color.BLUE);
    					g2.fillRect(x1, y1, size, size);
        			}
        			if (row == currentRow && col == currentCol){
        				g2.setColor(Color.ORANGE);
        				g2.fillRect(x1, y1, size, size);
        			}
        		}else{
        			ch = maze[currentRow - cellpadding + row][currentCol - cellpadding + col].getNodeType();
        		}
        		
        		switch(ch){
	        		case 'X':
	        			imageIndex = 0;
	        		break;
	        		case 'W':
	        			imageIndex = 1;
            		break;
	        		case '?':
	        			imageIndex = 2;
            		break;
	        		case 'B':
	        			imageIndex = 3;
            		break;
	        		case 'H':
	        			imageIndex = 4;
            		break;
	        		case 'E':
	        			imageIndex = enemyState;
            		break;
	        		case 'F':
	        			imageIndex = enemyBossState;
            		break;
	        		case 'D':
	        			imageIndex = 9;
            		break;
	        		case 'T':
	        			imageIndex = 10;
            		break;
	        		case 'M':
	        			imageIndex = 11;
            		break;
	        		case 'A':
	        			imageIndex = 12;
	            	break;
	        		case 'P':
	        			imageIndex = playerState;
	            	break;
	        		case 'G':
	        			imageIndex = 13;
	            	break;
	        		case 'Z':
	        			imageIndex = 14;
	            	break;
	        		case 'L':
	        			imageIndex = 15;
	            	break;
	        		case 'O':
	        			imageIndex = 16;
	            	break;
	        		case 'N':
	        			imageIndex = 17;
	            	break;
	            	default:
	            		imageIndex = -1;
	            	break;
        		}
        		
        		if (imageIndex >= 0){
        			g2.drawImage(images[imageIndex], x1, y1, null);
        		}else{
        			if(maze[row][col].getNodeType() == ' '){
	        			g2.setColor(Color.LIGHT_GRAY);
	        			g2.fillRect(x1, y1, size, size);
        			}
        		}
        		
        		if (maze[row][col].getNodeType() == 'T'){
        			g2.setColor(maze[row][col].getColor());
        			g2.fillRect(x1, y1, size, size);
        		}
        		
       			if (maze[row][col].isGoalNode() && maze[row][col].getNodeType() != 'P'){
       				g2.setColor(Color.GREEN);
       				g2.fillRect(x1, y1, size, size);
       			}
        	}
        }
	}
	
	public void toggleZoom(){
		zoomOut = !zoomOut;		
	}

	public void actionPerformed(ActionEvent e) {
		// Updating the state of the enemy's images
		if (enemyState < 0 || enemyState == 7){
			enemyState = 8;
		}else{
			enemyState = 7;
		}
		
		if (enemyBossState < 0 || enemyBossState == 18){
			enemyBossState = 19;
		}else{
			enemyBossState = 18;
		}
		
		if(e.getSource() == timer){
			 repaint();
		}
	}
	
	private void init() {
		// Initializing the images
		images = new BufferedImage[20];
		try {
			images[0] = ImageIO.read(new java.io.File("res/hedge.png"));
			images[1] = ImageIO.read(new java.io.File("res/sword.png"));		
			images[2] = ImageIO.read(new java.io.File("res/help.png"));
			images[3] = ImageIO.read(new java.io.File("res/shotgun.png"));
			images[4] = ImageIO.read(new java.io.File("res/ak47.png"));
			images[5] = ImageIO.read(new java.io.File("res/player_right.png"));
			images[6] = ImageIO.read(new java.io.File("res/player_up.png"));
			images[7] = ImageIO.read(new java.io.File("res/spider_down.png"));
			images[8] = ImageIO.read(new java.io.File("res/spider_up.png"));
			images[9] = ImageIO.read(new java.io.File("res/spider_dead.png"));
			images[10] = ImageIO.read(new java.io.File("res/this_way.png"));
			images[11] = ImageIO.read(new java.io.File("res/health.png"));
			images[12] = ImageIO.read(new java.io.File("res/armor.png"));
			images[13] = ImageIO.read(new java.io.File("res/goal.png"));
			images[14] = ImageIO.read(new java.io.File("res/win.png"));
			images[15] = ImageIO.read(new java.io.File("res/lose.png"));
			images[16] = ImageIO.read(new java.io.File("res/player_left.png"));
			images[17] = ImageIO.read(new java.io.File("res/pipe_bomb.png"));
			images[18] = ImageIO.read(new java.io.File("res/boss_spider_down.png"));
			images[19] = ImageIO.read(new java.io.File("res/boss_spider_up.png"));
		} catch (IOException error) {
			System.out.println("Error - " + error);
		}
	}

	public int getPlayerState() {
		return playerState;
	}

	public void setPlayerState(int playerState) {
		this.playerState = playerState;
	}
}