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

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;

import ie.gmit.sw.ai.*;
import ie.gmit.sw.game.Enemy;
import ie.gmit.sw.game.FuzzyBattle;
import ie.gmit.sw.game.Game;
import ie.gmit.sw.game.Maze;
import ie.gmit.sw.game.PlaySound;
import ie.gmit.sw.game.Player;

/**  
* GameFrame.java - The game frame class which contains the game view and handles UI updates
* @author John Walsh
* @version 1.0
*/
public class GameFrame implements KeyListener {
	
	public static final int MAZE_DIMENSION = 80;
	private JFrame gameFrame;
	private GameView gamePanel;
	private JPanel coverPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	
	// Left panel elements
	private JLabel lblCurScore;
	private JLabel lblCurStepstoExit;
	private JLabel lblCurSteps;
	private JLabel lblHealthPoints;
	private JLabel lblArmorPoints;
	private JLabel lblCurWeapon;
	private JLabel lblCurWeaponStr;
	private JLabel lblCurSpecial;
	private JLabel lblEnemiesAmount;
	private JLabel lblCommonAmount;
	private JLabel lblBossesAmount;
	private JLabel lblCurDifficulty;
	
	// Right panel elements
	private JLabel lblCurExits;
	private JComboBox<String> gameDifficulty1;
	private JComboBox<String> gameDifficulty2;
	
	// The game controller object instance
	private Game game;
	
	public GameFrame() throws Exception {
		setupFrame();
	}
	
	/**
	 * Setup of the window frame that surrounds the game
	 * @throws Exception
	 */
	private void setupFrame() throws Exception {
		gameFrame = new JFrame("Maze Game - AI Project - John Walsh");
		gameFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameFrame.addKeyListener(this);
		gameFrame.getContentPane().setLayout(null);
		gameFrame.getContentPane().setBackground(Color.black);
		gameFrame.setSize(1200,700);
		gameFrame.setResizable(false);
		gameFrame.setLocationRelativeTo(null);
		gameFrame.setVisible(true);
		setupCoverPanel();
		gameFrame.repaint();
	}
	
	/**
	 * Setup of the left panel in the game window frame that contains labels and information for the player
	 */
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
		
		JLabel lblSpecial = new JLabel("Special Pick Up:");
		lblSpecial.setBounds(10, 330, 130, 24);
		lblSpecial.setForeground(Color.white);
		lblSpecial.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblSpecial);
		
		lblCurSpecial = new JLabel("0");
		lblCurSpecial.setBounds(148, 330, 90, 24);
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

	/**
	 * Setup of the right panel in the game window frame that contains labels and information for the player
	 */
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
		
		JLabel lblSpecialWeapon = new JLabel("Special Pick Up:");
		lblSpecialWeapon.setBounds(15, 460, 130, 24);
		lblSpecialWeapon.setForeground(Color.white);
		lblSpecialWeapon.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblSpecialWeapon);
		
		JLabel lblSpecialWeaponButton = new JLabel("K");
		lblSpecialWeaponButton.setBounds(151, 460, 80, 24);
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
				initNewGame(gameDifficulty2.getSelectedItem().toString());
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
	
	/**
	 * Setup of the cover panel that greets the player when they first launch the game
	 * @throws IOException
	 */
	private void setupCoverPanel() throws IOException {
		// Setting up cover panel when game first starts
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
		gameDifficulty1.setSelectedIndex(1);
		gameDifficulty1.setFocusable(false);
		coverPanel.add(gameDifficulty1);
		
		JButton btnNewGame = new JButton("New Game");
		btnNewGame.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				gameFrame.getContentPane().remove(coverPanel);
				setupLeftPanel();
				setupRightPanel();
				initNewGame(gameDifficulty1.getSelectedItem().toString());
			}
		});
		btnNewGame.setFont(new Font("Serif", Font.BOLD, 24));
		btnNewGame.setBounds(332, 430, 211, 33);
		btnNewGame.setFocusable(false);
		coverPanel.add(btnNewGame);
	}
	
	/**
	 * Initializing the game to start
	 * @param gameDifficulty
	 */
	private void initNewGame(String gameDifficulty) {
		// Initializing the game, panel and killing old enemy threads if they exist
		if(gamePanel != null)
			gameFrame.getContentPane().remove(gamePanel);
		
		if(game != null)
			game.killEnemyThreads();
		
		game = new Game();
		
		newGame(gameDifficulty);
		lblCurDifficulty.setText(gameDifficulty);
		gameDifficulty2.setSelectedItem(gameDifficulty);
		
		gameFrame.repaint();
		// Making sure if the player block is suitable to search for the amount of steps to goal
		if(game.getPlayer().getStepsToExit() <= 0)
			initNewGame(gameDifficulty);
	}
	
	/**
	 * Starts a new game by re-generating the maze and call the enemy and player creation methods
	 * @param gameDifficulty
	 */
	private void newGame(String gameDifficulty) {
		// Create new player & enemy instances here
		Maze model = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		Node[][] maze = model.getMaze();
		Player player = new Player(100, 100);
		ArrayList<Enemy> enemies = new ArrayList<Enemy>();
		Dimension d = new Dimension(GameView.DEFAULT_VIEW_SIZE, GameView.DEFAULT_VIEW_SIZE);
		
		game.setModel(model);
		game.setMaze(maze);
		game.setPlayer(player);
		game.setEnemies(enemies);
		game.placePlayer(model.getGoalPos());
    	game.setupEnemies(gameDifficulty);
    	
		gamePanel = new GameView(game.getMaze());
		gamePanel.setBounds(204, 0, 800, 700);
    	gamePanel.setPreferredSize(d);
    	gamePanel.setMinimumSize(d);
    	gamePanel.setMaximumSize(d);
    	gameFrame.getContentPane().add(gamePanel);
    	
    	updateView();
	}
	
	/**
	 * Updates the UI information showing in the frame, both player info and enemy info
	 */
	private void updateStatsGUI(){
		
		calStepsToExit();
		lblCurScore.setText(Integer.toString(game.getPlayer().getScore()));
		lblCurSteps.setText(Integer.toString(game.getPlayer().getSteps()));
		lblHealthPoints.setText(Integer.toString(game.getPlayer().getHealth()));
		lblArmorPoints.setText(Integer.toString(game.getPlayer().getArmor()));
		lblCurWeapon.setText(game.getPlayer().getWeapon());
		lblCurWeaponStr.setText(Integer.toString(game.getPlayer().getWeaponStrength()));
		lblCurSpecial.setText(Integer.toString(game.getPlayer().getSpecial()));
		lblEnemiesAmount.setText(Integer.toString(game.getEnemies().size()));
		
		int commonCount = 0;
		int bossCount = 0;
		
		for(int i = 0; i < game.getEnemies().size(); i++){
			if(game.getEnemies().get(i).isBoss()){
				bossCount++;
			}else{
				commonCount++;
			}
		}
		
		lblCommonAmount.setText(Integer.toString(commonCount));
		lblBossesAmount.setText(Integer.toString(bossCount));
		lblCurExits.setText(Integer.toString(1));
		lblCurDifficulty.setText(gameDifficulty2.getSelectedItem().toString());
	}
	
	
	private void calStepsToExit(){
		//Calculating the step to the goal node
		AStarTraversator traverse = new AStarTraversator(game.getModel().getGoalNode(), true);
		traverse.traverse(game.getMaze(), game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()]);
		game.getPlayer().setStepsToExit(traverse.getStepsToExit());
		lblCurStepstoExit.setText(Integer.toString(game.getPlayer().getStepsToExit()));
	}
	
	/**
	 * Update the view by calling the game panel set position methods
	 */
	private void updateView(){
		gamePanel.setCurrentRow(game.getPlayer().getRowPos());
		gamePanel.setCurrentCol(game.getPlayer().getColPos());
		updateStatsGUI();
	}

	/**
	 * Check what key the user presses and executes the code / methods if a valid button is pressed
	 */
    public void keyPressed(KeyEvent e){
    	if(game.getPlayer() == null || game.getPlayer().isGameOver()) return;
    	
    	// Check here if the block is a bomb or weapon etc
        if (e.getKeyCode() == KeyEvent.VK_D && game.getPlayer().getColPos() < MAZE_DIMENSION - 1) {
        	if (isValidMove(game.getPlayer().getRowPos(), game.getPlayer().getColPos() + 1) && game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos() + 1].isWalkable()){
        		GameView.player_state = 5;
        		game.getPlayer().setColPos(game.getPlayer().getColPos() + 1);
        		game.getPlayer().setSteps(game.getPlayer().getSteps() + 1);
        	}
        }else if (e.getKeyCode() == KeyEvent.VK_A && game.getPlayer().getColPos() > 0) {
        	if (isValidMove(game.getPlayer().getRowPos(), game.getPlayer().getColPos() - 1) && game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos() - 1].isWalkable()){
        		GameView.player_state = 16;
        		game.getPlayer().setColPos(game.getPlayer().getColPos() - 1);
        		game.getPlayer().setSteps(game.getPlayer().getSteps() + 1);
        	}
        }else if (e.getKeyCode() == KeyEvent.VK_W && game.getPlayer().getRowPos() > 0) {
        	if (isValidMove(game.getPlayer().getRowPos() - 1, game.getPlayer().getColPos()) && game.getMaze()[game.getPlayer().getRowPos() - 1][game.getPlayer().getColPos()].isWalkable()){
        		GameView.player_state = 5;
        		game.getPlayer().setRowPos(game.getPlayer().getRowPos() - 1);
        		game.getPlayer().setSteps(game.getPlayer().getSteps() + 1);
        	}
        }else if (e.getKeyCode() == KeyEvent.VK_S && game.getPlayer().getRowPos() < MAZE_DIMENSION - 1) {
        	if (isValidMove(game.getPlayer().getRowPos() + 1, game.getPlayer().getColPos()) && game.getMaze()[game.getPlayer().getRowPos() + 1][game.getPlayer().getColPos()].isWalkable()){
        		GameView.player_state = 5;
        		game.getPlayer().setRowPos(game.getPlayer().getRowPos() + 1);
        		game.getPlayer().setSteps(game.getPlayer().getSteps() + 1);
        	}
        }else if (e.getKeyCode() == KeyEvent.VK_M){
        	gamePanel.toggleZoom();
        }else if (e.getKeyCode() == KeyEvent.VK_K){
        	if(game.getPlayer().getSpecial() <= 0) return;
        	// Creates a new search algorithm object to find the goal node
    		//Traversator traverse = randomSearch(new Random().nextInt((7 - 0) + 1) + 0);
    		Traversator traverse = randomSearch(0);
        	// Calls the specific traverse method contained in the class instantiated
        	//AStarTraversator traverse = new AStarTraversator(game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()], false);
        	traverse.traverse(game.getMaze(), game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()]);
        	//System.out.println("Search good?: " + traverse.isFoundGoal());
        	game.getPlayer().setSearchCount(game.getPlayer().getSearchCount() + 1);
        	game.getPlayer().setSpecial(game.getPlayer().getSpecial() - 1);
    	}
        
        updateView();       
    }
    
    public void keyReleased(KeyEvent e) {
    	GameView.player_state = 6;
    }
    
	public void keyTyped(KeyEvent e) {}
	
	/**
	 * Checks if the player is making a valid move
	 * @param r
	 * @param c
	 * @return Returns true if its a valid move
	 */
	private boolean isValidMove(int r, int c){
		// Error checking the move position
		if(!(r <= game.getMaze().length - 1 && c <= game.getMaze()[r].length - 1)) return false;

		switch(game.getMaze()[r][c].getNodeType()){
			case ' ':
				// Health pick up,
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setGoalNode(false);
				game.getMaze()[r][c].setNodeType('P');
				game.getMaze()[r][c].setGoalNode(true);
			return true;
			case 'W':
				// Sword pick up, the least powerful weapon in the game
				if(!game.getPlayer().getWeapon().equals("Sword")){
					game.getPlayer().setWeapon("Sword");
					game.getPlayer().setWeaponStrength(45);
					game.getMaze()[r][c].setNodeType('X');
					new PlaySound("res/power_up.wav");
				}
			return true;
			case '?':
				// Help me pick up, shows the player a possible route to the goal
				game.getPlayer().setSpecial(game.getPlayer().getSpecial() + 1);
				game.getMaze()[r][c].setNodeType('X');
				new PlaySound("res/power_up.wav");
			return true;
			case 'B':
				// A bomb pick up, very powerful bomb that kills enemies very well
				if(!game.getPlayer().getWeapon().equals("Shotgun")){
					game.getPlayer().setWeapon("Shotgun");
					game.getPlayer().setWeaponStrength(65);
					game.getMaze()[r][c].setNodeType('X');
					new PlaySound("res/power_up.wav");
				}
			return true;
			case 'H':
				// A hydrogen bomb pick up, extremely powerful and deadly weapon
				if(!game.getPlayer().getWeapon().equals("AK-47")){
					game.getPlayer().setWeapon("AK-47");
					game.getPlayer().setWeaponStrength(85);
					game.getMaze()[r][c].setNodeType('X');
					new PlaySound("res/power_up.wav");
				}
			return true;
			case 'M':
				// Health pick up, adds 50 health to players character
				if(game.getPlayer().getHealth() < 100){
					game.getPlayer().setHealth(game.getPlayer().getHealth() + 50);
					if(game.getPlayer().getHealth() > 100)
						game.getPlayer().setHealth(100);
					game.getMaze()[r][c].setNodeType('X');
					new PlaySound("res/power_up.wav");
				}
			return true;
			case 'A':
				// Health pick up, adds 50 health to players character
				if(game.getPlayer().getArmor() < 100){
					game.getPlayer().setArmor(game.getPlayer().getArmor() + 50);
					if(game.getPlayer().getArmor() > 100)
						game.getPlayer().setArmor(100);
					game.getMaze()[r][c].setNodeType('X');
					new PlaySound("res/power_up.wav");
				}
			return true;
			case 'T':
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setGoalNode(false);
				game.getMaze()[r][c].setNodeType('P');
				game.getMaze()[r][c].setGoalNode(true);
			return true;
			case 'G':
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
				game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setGoalNode(false);
				game.getMaze()[r][c].setNodeType('Z');
				game.getPlayer().setGameOver(true);
				new PlaySound("res/win_game.wav");
			return true;
			case 'N':
				if(!game.getPlayer().getWeapon().equals("Pipe Bomb")){
					game.getPlayer().setWeapon("Pipe Bomb");
					game.getPlayer().setWeaponStrength(100);
					game.getMaze()[r][c].setNodeType('X');
					new PlaySound("res/power_up.wav");
				}
			return true;
			case 'E':
				FuzzyBattle fuzzyBattle1 = new FuzzyBattle();
				boolean enemyWon1 = fuzzyBattle1.startBattle(game.getPlayer(), game.getEnemies().get(game.getMaze()[r][c].getEnemyID()), "fcl/battle.fcl");
				if(enemyWon1 == true){
					// The player has lost the game!
					game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
					game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setEnemyID(0);
					game.getPlayer().setGameOver(true);
					game.getMaze()[r][c].setNodeType('L');
					new PlaySound("res/lose_game.wav");
					return false;
				}else{
					game.getEnemies().get(game.getMaze()[r][c].getEnemyID()).setHealth(0);
					game.getMaze()[r][c].setNodeType('D');
					game.getMaze()[r][c].setEnemyID(0);
					new PlaySound("res/win_fight.wav");
					return false;
				}
			case 'F':
				FuzzyBattle fuzzyBattle2 = new FuzzyBattle();
				boolean enemyWon2 = fuzzyBattle2.startBattle(game.getPlayer(), game.getEnemies().get(game.getMaze()[r][c].getEnemyID()), "fcl/battle.fcl");
				if(enemyWon2 == true){
					// The player has lost the game!
					game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setNodeType(' ');
					game.getMaze()[game.getPlayer().getRowPos()][game.getPlayer().getColPos()].setEnemyID(0);
					game.getPlayer().setGameOver(true);
					game.getMaze()[r][c].setNodeType('L');
					new PlaySound("res/lose_game.wav");
					return false;
				}else{
					game.getEnemies().get(game.getMaze()[r][c].getEnemyID()).setHealth(0);
					game.getMaze()[r][c].setNodeType('L');
					game.getMaze()[r][c].setEnemyID(0);
					new PlaySound("res/win_fight.wav");
					return false;
				}
			default:
			return false;
		}
	}
	
	private Traversator randomSearch(int randNum){
		// Selecting a random algorithm to be created and returned
		switch(randNum){
			case 0:
				return new AStarTraversator(game.getModel().getGoalNode(), false);
			case 1:
				//return new BasicHillClimbingTraversator(model.getGoalNode());
			case 2:
				return new BeamTraversator(game.getModel().getGoalNode(), 50);
			case 3:
				return new BestFirstTraversator(game.getModel().getGoalNode());
			case 4:
				// Slight problem with this search, over writes blocks
				return new BruteForceTraversator(true);
			case 5:
				//return new DepthLimitedDFSTraversator(game.getMaze().length / 2);
			case 6:
				return new IDAStarTraversator(game.getModel().getGoalNode());
			case 7:
				// Not working at the moment
				//return new IDDFSTraversator();
	    	default:
	    		return new AStarTraversator(game.getModel().getGoalNode(), false);
		}
	}
	
	public static void main(String[] args) throws Exception {
		new GameFrame();
	}
}