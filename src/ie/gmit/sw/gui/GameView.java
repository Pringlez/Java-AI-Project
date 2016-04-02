package ie.gmit.sw.gui;

import java.awt.*;
import java.awt.event.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.swing.*;

import ie.gmit.sw.ai.Node;

/**  
* GameView.java - The game view class handles all the image manipulation and drawing
* @author John Walsh
* @version 1.0
*/
public class GameView extends JPanel implements ActionListener {
	
	private static final long serialVersionUID = 1L;
	private static final int IMAGE_COUNT = 18;
	public static final int DEFAULT_VIEW_SIZE = 800;
	public static int player_state = 6;
	private int cellspan = 5;	
	private int cellpadding = 2;
	private Node[][] maze;
	private BufferedImage[] images;
	private int enemy_state = 7;
	private Timer timer;
	private int currentRow;
	private int currentCol;
	private boolean zoomOut = false;
	private int imageIndex = -1;
	
	public GameView(Node[][] maze) throws Exception {
		init();
		this.maze = maze;
		setBackground(Color.LIGHT_GRAY);
		setDoubleBuffered(true);
		timer = new Timer(100, this);
		timer.start();
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
        final int size = DEFAULT_VIEW_SIZE/cellspan;
        g2.drawRect(0, 0, GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
        
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
	        			imageIndex = enemy_state;
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
	        			imageIndex = player_state;
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
        		
       			if (maze[row][col].isGoalNode()){
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
		if (enemy_state < 0 || enemy_state == 7){
			enemy_state = 8;
		}else{
			enemy_state = 7;
		}
		
		if(e.getSource() == timer){
			 repaint();
		}
	}
	
	private void init() throws Exception{
		images = new BufferedImage[IMAGE_COUNT];
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
	}
}