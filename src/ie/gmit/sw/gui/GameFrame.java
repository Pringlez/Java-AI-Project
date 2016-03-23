package ie.gmit.sw.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

import ie.gmit.sw.ai.*;
import ie.gmit.sw.game.Player;

public class GameFrame implements KeyListener {
	
	private static final int MAZE_DIMENSION = 80;
	private JFrame gameFrame;
	private GameView gamePanel;
	private Maze model;
	private JPanel coverPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private Node[][] maze;
	private int currentRow;
	private int currentCol;
	
	// Create new player & enemy instances here
	private Player player;
	
	public GameFrame() throws Exception {
		player = new Player();
		setupFrame();
	}
	
	private void setupFrame() throws Exception {
		gameFrame = new JFrame("Maze Game - AI Project - John Walsh");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.addKeyListener(this);
		gameFrame.getContentPane().setLayout(null);
		gameFrame.getContentPane().setBackground(Color.black);
		setupCoverPanel();
		gameFrame.setSize(1200,700);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
		gameFrame.repaint();
	}
	
	private void setupLeftPanel(){
		
		leftPanel = new JPanel();
		leftPanel.setBackground(Color.black);
		leftPanel.setBounds(0, 0, 205, 751);
		gameFrame.getContentPane().add(leftPanel);
		leftPanel.setLayout(null);
		
		JLabel lblPlayer = new JLabel("Player Stats");
		lblPlayer.setBounds(10, 10, 170, 24);
		lblPlayer.setForeground(Color.yellow);
		lblPlayer.setFont(new Font("Serif", Font.BOLD, 24));
		leftPanel.add(lblPlayer);
		
		JLabel lblScore = new JLabel("Score:");
		lblScore.setBounds(10, 50, 60, 24);
		lblScore.setForeground(Color.white);
		lblScore.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblScore);
		
		JLabel lblCurScore = new JLabel("0");
		lblCurScore.setBounds(70, 50, 80, 24);
		lblCurScore.setForeground(Color.yellow);
		lblCurScore.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurScore);
		
		JLabel lblStepstoExit = new JLabel("Steps to Exit:");
		lblStepstoExit.setBounds(10, 90, 115, 24);
		lblStepstoExit.setForeground(Color.white);
		lblStepstoExit.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblStepstoExit);
		
		JLabel lblCurStepstoExit = new JLabel("0");
		lblCurStepstoExit.setBounds(125, 90, 60, 24);
		lblCurStepstoExit.setForeground(Color.green);
		lblCurStepstoExit.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurStepstoExit);
		
		JLabel lblSteps = new JLabel("Steps:");
		lblSteps.setBounds(10, 130, 60, 24);
		lblSteps.setForeground(Color.white);
		lblSteps.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblSteps);
		
		JLabel lblCurSteps = new JLabel("0");
		lblCurSteps.setBounds(70, 130, 80, 24);
		lblCurSteps.setForeground(Color.green);
		lblCurSteps.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurSteps);
		
		JLabel lblHealth = new JLabel("Health:");
		lblHealth.setBounds(10, 170, 65, 24);
		lblHealth.setForeground(Color.white);
		lblHealth.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblHealth);
		
		JLabel lblHealthPoints = new JLabel("100");
		lblHealthPoints.setBounds(75, 170, 80, 24);
		lblHealthPoints.setForeground(Color.red);
		lblHealthPoints.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblHealthPoints);
		
		JLabel lblArmor = new JLabel("Armor:");
		lblArmor.setBounds(10, 210, 65, 24);
		lblArmor.setForeground(Color.white);
		lblArmor.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblArmor);
		
		JLabel lblArmorPoints = new JLabel("100");
		lblArmorPoints.setBounds(75, 210, 80, 24);
		lblArmorPoints.setForeground(Color.blue);
		lblArmorPoints.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblArmorPoints);
		
		JLabel lblWeapon = new JLabel("Weapon:");
		lblWeapon.setBounds(10, 250, 80, 24);
		lblWeapon.setForeground(Color.white);
		lblWeapon.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblWeapon);
		
		JLabel lblCurWeapon = new JLabel("Unarmed");
		lblCurWeapon.setBounds(88, 250, 90, 24);
		lblCurWeapon.setForeground(Color.green);
		lblCurWeapon.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurWeapon);
		
		JLabel lblSpecial = new JLabel("Special:");
		lblSpecial.setBounds(10, 290, 70, 24);
		lblSpecial.setForeground(Color.white);
		lblSpecial.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblSpecial);
		
		JLabel lblCurSpecial = new JLabel("Empty");
		lblCurSpecial.setBounds(80, 290, 90, 24);
		lblCurSpecial.setForeground(Color.green);
		lblCurSpecial.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurSpecial);
		
		JLabel lblEnemy = new JLabel("Enemy Stats");
		lblEnemy.setBounds(10, 330, 170, 24);
		lblEnemy.setForeground(Color.yellow);
		lblEnemy.setFont(new Font("Serif", Font.BOLD, 24));
		leftPanel.add(lblEnemy);
		
		JLabel lblEnemies = new JLabel("Enemies:");
		lblEnemies.setBounds(10, 375, 80, 24);
		lblEnemies.setForeground(Color.white);
		lblEnemies.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblEnemies);
		
		JLabel lblEnemiesAmount = new JLabel("20");
		lblEnemiesAmount.setBounds(90, 375, 80, 24);
		lblEnemiesAmount.setForeground(Color.green);
		lblEnemiesAmount.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblEnemiesAmount);
		
		JLabel lblCommon = new JLabel("Common:");
		lblCommon.setBounds(10, 415, 90, 24);
		lblCommon.setForeground(Color.white);
		lblCommon.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCommon);
		
		JLabel lblCommonAmount = new JLabel("15");
		lblCommonAmount.setBounds(100, 415, 80, 24);
		lblCommonAmount.setForeground(Color.red);
		lblCommonAmount.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCommonAmount);
		
		JLabel lblBosses = new JLabel("Bosses:");
		lblBosses.setBounds(10, 455, 70, 24);
		lblBosses.setForeground(Color.white);
		lblBosses.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblBosses);
		
		JLabel lblBossesAmount = new JLabel("5");
		lblBossesAmount.setBounds(80, 455, 80, 24);
		lblBossesAmount.setForeground(Color.red);
		lblBossesAmount.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblBossesAmount);
		
		JLabel lblDifficulty = new JLabel("Difficulty:");
		lblDifficulty.setBounds(10, 495, 90, 24);
		lblDifficulty.setForeground(Color.white);
		lblDifficulty.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblDifficulty);
		
		JLabel lblCurDifficulty = new JLabel("Normal");
		lblCurDifficulty.setBounds(100, 495, 90, 24);
		lblCurDifficulty.setForeground(Color.green);
		lblCurDifficulty.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurDifficulty);
	}
	
	private void setupRightPanel(){
		
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.black);
		rightPanel.setBounds(1000, 0, 194, 751);
		gameFrame.getContentPane().add(rightPanel);
		rightPanel.setLayout(null);
		
		JLabel lblGame = new JLabel("Game Stats");
		lblGame.setBounds(15, 10, 170, 24);
		lblGame.setForeground(Color.yellow);
		lblGame.setFont(new Font("Serif", Font.BOLD, 24));
		rightPanel.add(lblGame);
		
		JLabel lblExits = new JLabel("Maze Exits:");
		lblExits.setBounds(15, 50, 110, 24);
		lblExits.setForeground(Color.white);
		lblExits.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblExits);
		
		JLabel lblCurExits = new JLabel("0");
		lblCurExits.setBounds(120, 50, 80, 24);
		lblCurExits.setForeground(Color.yellow);
		lblCurExits.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblCurExits);
		
		JLabel lblHelp = new JLabel("Controls");
		lblHelp.setBounds(15, 220, 170, 24);
		lblHelp.setForeground(Color.yellow);
		lblHelp.setFont(new Font("Serif", Font.BOLD, 24));
		rightPanel.add(lblHelp);
		
		JLabel lblMoveRight = new JLabel("Move Right:");
		lblMoveRight.setBounds(15, 260, 110, 24);
		lblMoveRight.setForeground(Color.white);
		lblMoveRight.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblMoveRight);
		
		JLabel lblRightButton = new JLabel("D");
		lblRightButton.setBounds(120, 260, 80, 24);
		lblRightButton.setForeground(Color.yellow);
		lblRightButton.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblRightButton);
		
		JLabel lblMoveLeft = new JLabel("Move Left:");
		lblMoveLeft.setBounds(15, 300, 100, 24);
		lblMoveLeft.setForeground(Color.white);
		lblMoveLeft.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblMoveLeft);
		
		JLabel lblLeftButton = new JLabel("A");
		lblLeftButton.setBounds(110, 300, 80, 24);
		lblLeftButton.setForeground(Color.yellow);
		lblLeftButton.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblLeftButton);
		
		JLabel lblMoveUp = new JLabel("Move Up:");
		lblMoveUp.setBounds(15, 340, 85, 24);
		lblMoveUp.setForeground(Color.white);
		lblMoveUp.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblMoveUp);
		
		JLabel lblUpButton = new JLabel("W");
		lblUpButton.setBounds(100, 340, 80, 24);
		lblUpButton.setForeground(Color.yellow);
		lblUpButton.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblUpButton);
		
		JLabel lblMoveDown = new JLabel("Move Down:");
		lblMoveDown.setBounds(15, 380, 110, 24);
		lblMoveDown.setForeground(Color.white);
		lblMoveDown.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblMoveDown);
		
		JLabel lblDownButton = new JLabel("S");
		lblDownButton.setBounds(125, 380, 80, 24);
		lblDownButton.setForeground(Color.yellow);
		lblDownButton.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblDownButton);
		
		JLabel lblZoomOut = new JLabel("Zoom Out:");
		lblZoomOut.setBounds(15, 420, 95, 24);
		lblZoomOut.setForeground(Color.white);
		lblZoomOut.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblZoomOut);
		
		JLabel lblZoomOutButton = new JLabel("M");
		lblZoomOutButton.setBounds(110, 420, 80, 24);
		lblZoomOutButton.setForeground(Color.yellow);
		lblZoomOutButton.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblZoomOutButton);
		
		JLabel lblSpecialWeapon = new JLabel("Special:");
		lblSpecialWeapon.setBounds(15, 460, 70, 24);
		lblSpecialWeapon.setForeground(Color.white);
		lblSpecialWeapon.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblSpecialWeapon);
		
		JLabel lblSpecialWeaponButton = new JLabel("K");
		lblSpecialWeaponButton.setBounds(85, 460, 80, 24);
		lblSpecialWeaponButton.setForeground(Color.yellow);
		lblSpecialWeaponButton.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblSpecialWeaponButton);
		
		JLabel lblMenu = new JLabel("Menu");
		lblMenu.setBounds(15, 505, 170, 24);
		lblMenu.setForeground(Color.yellow);
		lblMenu.setFont(new Font("Serif", Font.BOLD, 24));
		rightPanel.add(lblMenu);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					gameFrame.getContentPane().remove(gamePanel);
					newGame();
					gameFrame.repaint();
				} catch (Exception error) {
					System.out.println("Error - " + error);
				}
			}
		});
		btnNewGame.setBounds(15, 551, 165, 23);
		btnNewGame.setFocusable(false);
		rightPanel.add(btnNewGame);
		
		JLabel label = new JLabel("Difficulty:");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Serif", Font.BOLD, 18));
		label.setBounds(15, 592, 90, 24);
		rightPanel.add(label);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setBounds(110, 595, 70, 20);
		comboBox.addItem("Easy");
		comboBox.addItem("Normal");
		comboBox.addItem("Hard");
		comboBox.setFocusable(false);
		rightPanel.add(comboBox);
	}
	
	private void setupCoverPanel() throws IOException {
		coverPanel = new JPanel();
		coverPanel.setBounds(204, 0, 797, 670);
		coverPanel.setBackground(Color.black);
		coverPanel.setLayout(null);
		gameFrame.getContentPane().add(coverPanel);
		
		BufferedImage myPicture = ImageIO.read(new File("res/ai_maze_game.png"));
		JLabel lblNewLabel = new JLabel(new ImageIcon(myPicture));
		lblNewLabel.setBounds(40, 200, 750, 100);
		coverPanel.add(lblNewLabel);
		
		JLabel label = new JLabel("Difficulty:");
		label.setForeground(Color.WHITE);
		label.setFont(new Font("Serif", Font.BOLD, 24));
		label.setBounds(307, 350, 126, 24);
		coverPanel.add(label);
		
		JComboBox<String> comboBox = new JComboBox<String>();
		comboBox.setFont(new Font("Serif", Font.BOLD, 24));
		comboBox.setBounds(435, 350, 120, 30);
		comboBox.addItem("Easy");
		comboBox.addItem("Normal");
		comboBox.addItem("Hard");
		comboBox.setFocusable(false);
		coverPanel.add(comboBox);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameFrame.getContentPane().remove(coverPanel);
				setupLeftPanel();
				setupRightPanel();
				newGame();
				gameFrame.repaint();
			}
		});
		btnNewGame.setFont(new Font("Serif", Font.BOLD, 24));
		btnNewGame.setBounds(332, 430, 211, 33);
		btnNewGame.setFocusable(false);
		coverPanel.add(btnNewGame);
	}
	
	private void newGame() {
		model = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		maze = model.getMaze();
		try {
			gamePanel = new GameView(maze);
		} catch (Exception e) {
			e.printStackTrace();
		}
		gamePanel.setBounds(204, 0, 800, 700);
		Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	gamePanel.setPreferredSize(d);
    	gamePanel.setMinimumSize(d);
    	gamePanel.setMaximumSize(d);
    	gameFrame.getContentPane().add(gamePanel);
    	placePlayer();
	}
	
	private void placePlayer(){
		boolean playerPosSet = false;
		while(playerPosSet != true){
	    	currentRow = (int) (MAZE_DIMENSION * Math.random());
	    	currentCol = (int) (MAZE_DIMENSION * Math.random());
	    	// Need to set player to P char
	    	// Set enemy to E char
	    	// When they meet fight starts!
	    	if(maze[currentRow][currentCol].isWalkable()){
		    	player.setRowPos(currentRow);
		    	player.setColPos(currentCol);
		    	maze[player.getRowPos()][player.getColPos()].setNodeType('P');
		    	playerPosSet = true;
	    	}
		}
	    updateView(); 		
	}
	
	private void updateView(){
		player.setRowPos(currentRow);
		player.setColPos(currentCol);
		gamePanel.setCurrentRow(currentRow);
		gamePanel.setCurrentCol(currentCol);
	}

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_D && currentCol < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow, currentCol + 1)) currentCol++;   		
        }else if (e.getKeyCode() == KeyEvent.VK_A && currentCol > 0) {
        	if (isValidMove(currentRow, currentCol - 1)) currentCol--;	
        }else if (e.getKeyCode() == KeyEvent.VK_W && currentRow > 0) {
        	if (isValidMove(currentRow - 1, currentCol)) currentRow--;
        }else if (e.getKeyCode() == KeyEvent.VK_S && currentRow < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow + 1, currentCol)) currentRow++;        	  	
        }else if (e.getKeyCode() == KeyEvent.VK_M){
        	gamePanel.toggleZoom();
        }else if (e.getKeyCode() == KeyEvent.VK_K){
        	// Fire Special Weapon!!
        	boolean cleanMaze = false;
        	if(player.getSearchCount() > 0){
        		cleanMaze = true;
        	}
        	Traversator t = new AStarTraversator(model.getGoalNode());
        	t.traverse(maze, maze[player.getRowPos()][player.getColPos()], cleanMaze);
        	player.setSearchCount(player.getSearchCount() + 1);
        }else{
        	return;
        }
        updateView();       
    }
    
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore
	
	private boolean isValidMove(int r, int c){
		if (r <= maze.length - 1 && c <= maze[r].length - 1 && maze[r][c].getNodeType() == ' '){
			maze[currentRow][currentCol].setNodeType(' ');
			maze[r][c].setNodeType('P');
			return true;
		}else{
			return false; //Can't move
		}
	}
	
	public static void main(String[] args) throws Exception {
		new GameFrame();
	}
}