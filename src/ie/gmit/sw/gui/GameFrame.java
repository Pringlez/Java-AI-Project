package ie.gmit.sw.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import ie.gmit.sw.ai.*;
import ie.gmit.sw.game.Enemy;
import ie.gmit.sw.game.Maze;
import ie.gmit.sw.game.Player;

/**  
* GameFrame.java - The game frame class which contains the game view and handles UI updates
* @author John Walsh
* @version 1.0
*/
public class GameFrame implements KeyListener {
	
	private static final int MAZE_DIMENSION = 80;
	private JFrame gameFrame;
	private GameView gamePanel;
	private Maze model;
	private JPanel coverPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private Node[][] maze;
	
	// Left panel elements
	JLabel lblCurScore;
	JLabel lblCurStepstoExit;
	JLabel lblCurSteps;
	JLabel lblHealthPoints;
	JLabel lblArmorPoints;
	JLabel lblCurWeapon;
	JLabel lblCurWeaponStr;
	JLabel lblCurSpecial;
	JLabel lblEnemiesAmount;
	JLabel lblCommonAmount;
	JLabel lblBossesAmount;
	JLabel lblCurDifficulty;
	
	// Right panel elements
	JLabel lblCurExits;
	JComboBox<String> gameDifficulty1;
	JComboBox<String> gameDifficulty2;
	
	// Create new player & enemy instances here
	private Player player;
	private ArrayList<Enemy> enemies;
	
	public GameFrame() throws Exception {
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
		
		lblCurScore = new JLabel("0");
		lblCurScore.setBounds(70, 50, 80, 24);
		lblCurScore.setForeground(Color.yellow);
		lblCurScore.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurScore);
		
		JLabel lblStepstoExit = new JLabel("Steps to Exit:");
		lblStepstoExit.setBounds(10, 90, 115, 24);
		lblStepstoExit.setForeground(Color.white);
		lblStepstoExit.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblStepstoExit);
		
		lblCurStepstoExit = new JLabel("0");
		lblCurStepstoExit.setBounds(125, 90, 60, 24);
		lblCurStepstoExit.setForeground(Color.green);
		lblCurStepstoExit.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurStepstoExit);
		
		JLabel lblSteps = new JLabel("Steps:");
		lblSteps.setBounds(10, 130, 60, 24);
		lblSteps.setForeground(Color.white);
		lblSteps.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblSteps);
		
		lblCurSteps = new JLabel("0");
		lblCurSteps.setBounds(70, 130, 80, 24);
		lblCurSteps.setForeground(Color.green);
		lblCurSteps.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurSteps);
		
		JLabel lblHealth = new JLabel("Health:");
		lblHealth.setBounds(10, 170, 65, 24);
		lblHealth.setForeground(Color.white);
		lblHealth.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblHealth);
		
		lblHealthPoints = new JLabel("100");
		lblHealthPoints.setBounds(75, 170, 80, 24);
		lblHealthPoints.setForeground(Color.red);
		lblHealthPoints.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblHealthPoints);
		
		JLabel lblArmor = new JLabel("Armor:");
		lblArmor.setBounds(10, 210, 65, 24);
		lblArmor.setForeground(Color.white);
		lblArmor.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblArmor);
		
		lblArmorPoints = new JLabel("100");
		lblArmorPoints.setBounds(75, 210, 80, 24);
		lblArmorPoints.setForeground(Color.blue);
		lblArmorPoints.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblArmorPoints);
		
		JLabel lblWeapon = new JLabel("Weapon:");
		lblWeapon.setBounds(10, 250, 80, 24);
		lblWeapon.setForeground(Color.white);
		lblWeapon.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblWeapon);
		
		lblCurWeapon = new JLabel("Unarmed");
		lblCurWeapon.setBounds(88, 250, 90, 24);
		lblCurWeapon.setForeground(Color.green);
		lblCurWeapon.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurWeapon);
		
		JLabel lblWeaponStr = new JLabel("Weapon Strength:");
		lblWeaponStr.setBounds(10, 290, 140, 24);
		lblWeaponStr.setForeground(Color.white);
		lblWeaponStr.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblWeaponStr);
		
		lblCurWeaponStr = new JLabel("0");
		lblCurWeaponStr.setBounds(160, 290, 40, 24);
		lblCurWeaponStr.setForeground(Color.green);
		lblCurWeaponStr.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurWeaponStr);
		
		JLabel lblSpecial = new JLabel("Special:");
		lblSpecial.setBounds(10, 330, 70, 24);
		lblSpecial.setForeground(Color.white);
		lblSpecial.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblSpecial);
		
		lblCurSpecial = new JLabel("Empty");
		lblCurSpecial.setBounds(80, 330, 90, 24);
		lblCurSpecial.setForeground(Color.green);
		lblCurSpecial.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurSpecial);
		
		JLabel lblEnemy = new JLabel("Enemy Stats");
		lblEnemy.setBounds(10, 370, 170, 24);
		lblEnemy.setForeground(Color.yellow);
		lblEnemy.setFont(new Font("Serif", Font.BOLD, 24));
		leftPanel.add(lblEnemy);
		
		JLabel lblEnemies = new JLabel("Enemies:");
		lblEnemies.setBounds(10, 415, 80, 24);
		lblEnemies.setForeground(Color.white);
		lblEnemies.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblEnemies);
		
		lblEnemiesAmount = new JLabel("20");
		lblEnemiesAmount.setBounds(90, 415, 80, 24);
		lblEnemiesAmount.setForeground(Color.green);
		lblEnemiesAmount.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblEnemiesAmount);
		
		JLabel lblCommon = new JLabel("Common:");
		lblCommon.setBounds(10, 455, 90, 24);
		lblCommon.setForeground(Color.white);
		lblCommon.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCommon);
		
		lblCommonAmount = new JLabel("15");
		lblCommonAmount.setBounds(100, 455, 80, 24);
		lblCommonAmount.setForeground(Color.red);
		lblCommonAmount.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCommonAmount);
		
		JLabel lblBosses = new JLabel("Bosses:");
		lblBosses.setBounds(10, 495, 70, 24);
		lblBosses.setForeground(Color.white);
		lblBosses.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblBosses);
		
		lblBossesAmount = new JLabel("5");
		lblBossesAmount.setBounds(80, 495, 80, 24);
		lblBossesAmount.setForeground(Color.red);
		lblBossesAmount.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblBossesAmount);
	}
	
	private void setupRightPanel(){
		
		rightPanel = new JPanel();
		rightPanel.setBackground(Color.BLACK);
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
		
		lblCurExits = new JLabel("0");
		lblCurExits.setBounds(120, 50, 80, 24);
		lblCurExits.setForeground(Color.yellow);
		lblCurExits.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblCurExits);
		
		JLabel lblDifficulty = new JLabel("Difficulty:");
		lblDifficulty.setBounds(15, 90, 90, 24);
		lblDifficulty.setForeground(Color.white);
		lblDifficulty.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblDifficulty);
		
		lblCurDifficulty = new JLabel("Normal");
		lblCurDifficulty.setBounds(105, 90, 90, 24);
		lblCurDifficulty.setForeground(Color.green);
		lblCurDifficulty.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblCurDifficulty);
		
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
					newGame(gameDifficulty2.getSelectedItem().toString());
					lblCurDifficulty.setText(gameDifficulty2.getSelectedItem().toString());
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
		
		gameDifficulty2 = new JComboBox<String>();
		gameDifficulty2.setBounds(110, 595, 70, 20);
		gameDifficulty2.addItem("Easy");
		gameDifficulty2.addItem("Normal");
		gameDifficulty2.addItem("Hard");
		gameDifficulty2.setFocusable(false);
		rightPanel.add(gameDifficulty2);
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
		
		gameDifficulty1 = new JComboBox<String>();
		gameDifficulty1.setFont(new Font("Serif", Font.BOLD, 24));
		gameDifficulty1.setBounds(435, 350, 120, 30);
		gameDifficulty1.addItem("Easy");
		gameDifficulty1.addItem("Normal");
		gameDifficulty1.addItem("Hard");
		gameDifficulty1.setFocusable(false);
		coverPanel.add(gameDifficulty1);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameFrame.getContentPane().remove(coverPanel);
				setupLeftPanel();
				setupRightPanel();
				newGame(gameDifficulty1.getSelectedItem().toString());
				gameDifficulty2.setSelectedIndex(gameDifficulty1.getSelectedIndex());
				lblCurDifficulty.setText(gameDifficulty1.getSelectedItem().toString());
				gameFrame.repaint();
			}
		});
		btnNewGame.setFont(new Font("Serif", Font.BOLD, 24));
		btnNewGame.setBounds(332, 430, 211, 33);
		btnNewGame.setFocusable(false);
		coverPanel.add(btnNewGame);
	}
	
	private void setupEnemies(String gameDifficulty){
		
		int amount;
		int health;
		int armor;
		int strength;
		int difficulty;
		int bosses;
		boolean isBoss = false;
		
		Random random = new Random();
		
		switch(gameDifficulty){
			case "Easy":
				amount = 8;
				health = random.nextInt((50 - 25) + 1) + 25;
				armor = random.nextInt((25 - 5) + 1) + 5;
				strength = random.nextInt((50 - 30) + 1) + 30;
				difficulty = 0;
				bosses = 1;
			break;
			case "Normal":
				amount = 12;
				health = random.nextInt((75 - 55) + 1) + 55;
				armor = random.nextInt((45 - 25) + 1) + 25;
				strength = random.nextInt((50 - 30) + 1) + 30;
				difficulty = 1;
				bosses = 2;
			break;
			case "Hard":
				amount = 16;
				health = random.nextInt((60 - 40) + 1) + 40;
				armor = random.nextInt((75 - 50) + 1) + 50;
				strength = random.nextInt((80 - 30) + 1) + 30;
				difficulty = 2;
				bosses = 3;
			break;
			default:
				amount = 8;
				health = random.nextInt((75 - 55) + 1) + 55;
				armor = random.nextInt((45 - 25) + 1) + 25;
				strength = random.nextInt((50 - 30) + 1) + 30;
				difficulty = 1;
				bosses = 2;
			break;
		}
		
		enemies = new ArrayList<Enemy>();
		
		/* Creating new enemies with separate threads, game difficult defines
		 * how each enemy object is created, individual enemy has their own strengths and weaknesses  
		 */
		for(int i = 0; i < amount; i++){
			
			if(bosses > 0){
				isBoss = true;
				bosses--;
			}
			
			// Create an enemy object & create new thread
			Runnable enemy = new Enemy(i, health, armor, strength, difficulty, isBoss);
			Thread thread = new Thread(enemy);
			enemies.add((Enemy) enemy);
			enemies.get(i).setInstance(thread);
			enemies.get(i).setMaze(maze);
			enemies.get(i).setPlayer(player);
			
			boolean enemyPosSet = false;
			
			int row;
			int col;
			
			while(enemyPosSet != true){
				row = (int) (MAZE_DIMENSION * Math.random());
				col = (int) (MAZE_DIMENSION * Math.random());
				
		    	if(maze[row][col].isWalkable()){
		    		enemies.get(i).setRowPos(row);
		    		enemies.get(i).setColPos(col);
			    	maze[enemies.get(i).getRowPos()][enemies.get(i).getColPos()].setNodeType('E');
			    	enemyPosSet = true;
		    	}
			}
			
			isBoss = false;
			thread.start();
		}
	}
	
	private void newGame(String gameDifficulty) {
		
		model = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		maze = model.getMaze();
		
		try {
			gamePanel = new GameView(maze);
		} catch (Exception error) {
			System.out.println("Error - " + error);
		}
		
		gamePanel.setBounds(204, 0, 800, 700);
		Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
    	gamePanel.setPreferredSize(d);
    	gamePanel.setMinimumSize(d);
    	gamePanel.setMaximumSize(d);
    	gameFrame.getContentPane().add(gamePanel);
    	
    	placePlayer();
    	setupEnemies(gameDifficulty);
    	updateView();
	}
	
	private void placePlayer(){
		
		player = new Player(100, 100);
		player.setWeapon("Unarmed");
		
		boolean playerPosSet = false;
		
		int row;
		int col;
		
		while(playerPosSet != true){
			row = (int) (MAZE_DIMENSION * Math.random());
			col = (int) (MAZE_DIMENSION * Math.random());
			
	    	if(maze[row][col].isWalkable()){
		    	player.setRowPos(row);
		    	player.setColPos(col);
		    	maze[player.getRowPos()][player.getColPos()].setNodeType('P');
		    	playerPosSet = true;
	    	}
		} 		
	}
	
	private void updateStatsGUI(){
		
		lblCurScore.setText(Integer.toString(player.getScore()));
		lblCurStepstoExit.setText(Integer.toString(player.getStepsToExit()));
		lblCurSteps.setText(Integer.toString(player.getSteps()));
		lblHealthPoints.setText(Integer.toString(player.getHealth()));
		lblArmorPoints.setText(Integer.toString(player.getArmor()));
		lblCurWeapon.setText(player.getWeapon());
		lblCurWeaponStr.setText(Integer.toString(player.getWeaponStrength()));
		lblCurSpecial.setText(Integer.toString(player.getSpecial()));
		lblEnemiesAmount.setText(Integer.toString(enemies.size()));
		
		int commonCount = 0;
		int bossCount = 0;
		
		for(int i = 0; i < enemies.size(); i++)
			if(enemies.get(i).isBoss()){
				bossCount++;
			}else{
				commonCount++;
			}
		
		lblCommonAmount.setText(Integer.toString(commonCount));
		lblBossesAmount.setText(Integer.toString(bossCount));
		lblCurExits.setText(Integer.toString(1));
		lblCurDifficulty.setText(gameDifficulty2.getSelectedItem().toString());
	}
	
	private void updateView(){
		gamePanel.setCurrentRow(player.getRowPos());
		gamePanel.setCurrentCol(player.getColPos());
		updateStatsGUI();
	}

    public void keyPressed(KeyEvent e){
    	// Check here if the block is a bomb or weapon etc
        if (e.getKeyCode() == KeyEvent.VK_D && player.getColPos() < MAZE_DIMENSION - 1) {
        	if (isValidMove(player.getRowPos(), player.getColPos() + 1) && maze[player.getRowPos()][player.getColPos() + 1].isWalkable()){
        		player.setColPos(player.getColPos() + 1);
        		player.setSteps(player.getSteps() + 1);
        	}
        }else if (e.getKeyCode() == KeyEvent.VK_A && player.getColPos() > 0) {
        	if (isValidMove(player.getRowPos(), player.getColPos() - 1) && maze[player.getRowPos()][player.getColPos() - 1].isWalkable()){
        		player.setColPos(player.getColPos() - 1);
        		player.setSteps(player.getSteps() + 1);
        	}
        }else if (e.getKeyCode() == KeyEvent.VK_W && player.getRowPos() > 0) {
        	if (isValidMove(player.getRowPos() - 1, player.getColPos()) && maze[player.getRowPos() - 1][player.getColPos()].isWalkable()){
        		player.setRowPos(player.getRowPos() - 1);
        		player.setSteps(player.getSteps() + 1);
        	}
        }else if (e.getKeyCode() == KeyEvent.VK_S && player.getRowPos() < MAZE_DIMENSION - 1) {
        	if (isValidMove(player.getRowPos() + 1, player.getColPos()) && maze[player.getRowPos() + 1][player.getColPos()].isWalkable()){
        		player.setRowPos(player.getRowPos() + 1);
        		player.setSteps(player.getSteps() + 1);
        	}
        }else if (e.getKeyCode() == KeyEvent.VK_M){
        	gamePanel.toggleZoom();
        }else if (e.getKeyCode() == KeyEvent.VK_K){
        	// Fire Special Weapon!!
        	if(player.getSpecial() <= 0) return;
	        	
    		Traversator traverse = null;
        	int randNum = new Random().nextInt((7 - 0) + 1) + 0;
        	
        	switch(0){
        		case 0:
        			traverse = new AStarTraversator(model.getGoalNode());
        		break;
        		case 1:
        			traverse = new BasicHillClimbingTraversator(model.getGoalNode());
	        	break;
        		case 2:
        			traverse = new BeamTraversator(model.getGoalNode(), 50);
	        	break;
        		case 3:
        			traverse = new BestFirstTraversator(model.getGoalNode());
	        	break;
        		case 4:
        			traverse = new BruteForceTraversator(true);
	        	break;
        		case 5:
        			traverse = new DepthLimitedDFSTraversator(25);
	        	break;
        		case 6:
        			traverse = new IDAStarTraversator(model.getGoalNode());
	        	break;
        		case 7:
        			traverse = new IDDFSTraversator();
	        	break;
	        	default:
	        		traverse = new AStarTraversator(model.getGoalNode());
	        	break;
        	}
        	
        	traverse.traverse(maze, maze[player.getRowPos()][player.getColPos()]);
        	player.setSearchCount(player.getSearchCount() + 1);
        	player.setSpecial(player.getSpecial() - 1);
    	}
        
        updateView();       
    }
    
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore
	
	private boolean isValidMove(int r, int c){
		// Error checking the move position
		if(!(r <= maze.length - 1 && c <= maze[r].length - 1)) return false;

		switch(maze[r][c].getNodeType()){
			case ' ':
				// Health pick up,
				maze[player.getRowPos()][player.getColPos()].setNodeType(' ');
				maze[r][c].setNodeType('P');
			return true;
			case 'W':
				// Sword pick up, the least powerful weapon in the game
				player.setWeapon("Sword");
				player.setWeaponStrength(65);
				maze[r][c].setNodeType('X');
			return true;
			case '?':
				// Help me pick up, shows the player a possible route to the goal
				player.setSpecial(player.getSpecial() + 1);
				maze[r][c].setNodeType('X');
			return true;
			case 'B':
				// A bomb pick up, very powerful bomb that kills enemies very well
				player.setWeapon("Bomb");
				player.setWeaponStrength(75);
				maze[r][c].setNodeType('X');
			return true;
			case 'H':
				// A hydrogen bomb pick up, extremely powerful and deadly weapon
				player.setWeapon("H Bomb");
				player.setWeaponStrength(100);
				maze[r][c].setNodeType('X');
			return true;
			case 'M':
				// Health pick up, adds 50 health to players character
				player.setHealth(player.getHealth() + 50);
				if(player.getHealth() > 100)
					player.setHealth(100);
				maze[r][c].setNodeType('X');
			return true;
			case 'A':
				// Health pick up, adds 50 health to players character
				player.setArmor(player.getArmor() + 50);
				if(player.getArmor() > 100)
					player.setArmor(100);
				maze[r][c].setNodeType('X');
			return true;
			case 'T':
				maze[player.getRowPos()][player.getColPos()].setNodeType(' ');
				maze[r][c].setNodeType('P');
			return true;
			default:
			return false;
		}
	}
	
	public static void main(String[] args) throws Exception {
		new GameFrame();
	}
}