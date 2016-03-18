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

import ie.gmit.sw.ai.Maze;

public class GameFrame implements KeyListener {
	
	private static final int MAZE_DIMENSION = 100;
	private JFrame gameFrame;
	private GameView gamePanel;
	private JPanel coverPanel;
	private JPanel leftPanel;
	private JPanel rightPanel;
	private char[][] model;
	private int currentRow;
	private int currentCol;
	
	// Create new player & enemy instances here
	
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
		//newGame();
		
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
		lblScore.setBounds(10, 50, 65, 24);
		lblScore.setForeground(Color.white);
		lblScore.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblScore);
		
		JLabel lblCurScore = new JLabel("0");
		lblCurScore.setBounds(75, 50, 80, 24);
		lblCurScore.setForeground(Color.yellow);
		lblCurScore.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurScore);
		
		JLabel lblSteps = new JLabel("Steps:");
		lblSteps.setBounds(10, 90, 65, 24);
		lblSteps.setForeground(Color.white);
		lblSteps.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblSteps);
		
		JLabel lblCurSteps = new JLabel("0");
		lblCurSteps.setBounds(75, 90, 80, 24);
		lblCurSteps.setForeground(Color.green);
		lblCurSteps.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurSteps);
		
		JLabel lblHealth = new JLabel("Health:");
		lblHealth.setBounds(10, 130, 65, 24);
		lblHealth.setForeground(Color.white);
		lblHealth.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblHealth);
		
		JLabel lblHealthPoints = new JLabel("100");
		lblHealthPoints.setBounds(75, 130, 80, 24);
		lblHealthPoints.setForeground(Color.red);
		lblHealthPoints.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblHealthPoints);
		
		JLabel lblArmor = new JLabel("Armor:");
		lblArmor.setBounds(10, 170, 65, 24);
		lblArmor.setForeground(Color.white);
		lblArmor.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblArmor);
		
		JLabel lblArmorPoints = new JLabel("100");
		lblArmorPoints.setBounds(75, 170, 80, 24);
		lblArmorPoints.setForeground(Color.blue);
		lblArmorPoints.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblArmorPoints);
		
		JLabel lblWeapon = new JLabel("Weapon:");
		lblWeapon.setBounds(10, 210, 80, 24);
		lblWeapon.setForeground(Color.white);
		lblWeapon.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblWeapon);
		
		JLabel lblCurWeapon = new JLabel("Unarmed");
		lblCurWeapon.setBounds(88, 210, 90, 24);
		lblCurWeapon.setForeground(Color.green);
		lblCurWeapon.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurWeapon);
		
		JLabel lblSpecial = new JLabel("Special:");
		lblSpecial.setBounds(10, 250, 70, 24);
		lblSpecial.setForeground(Color.white);
		lblSpecial.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblSpecial);
		
		JLabel lblCurSpecial = new JLabel("Empty");
		lblCurSpecial.setBounds(80, 250, 90, 24);
		lblCurSpecial.setForeground(Color.green);
		lblCurSpecial.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCurSpecial);
		
		JLabel lblEnemy = new JLabel("Enemy Stats");
		lblEnemy.setBounds(10, 290, 170, 24);
		lblEnemy.setForeground(Color.yellow);
		lblEnemy.setFont(new Font("Serif", Font.BOLD, 24));
		leftPanel.add(lblEnemy);
		
		JLabel lblEnemies = new JLabel("Enemies:");
		lblEnemies.setBounds(10, 335, 80, 24);
		lblEnemies.setForeground(Color.white);
		lblEnemies.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblEnemies);
		
		JLabel lblEnemiesAmount = new JLabel("20");
		lblEnemiesAmount.setBounds(90, 335, 80, 24);
		lblEnemiesAmount.setForeground(Color.green);
		lblEnemiesAmount.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblEnemiesAmount);
		
		JLabel lblCommon = new JLabel("Common:");
		lblCommon.setBounds(10, 375, 90, 24);
		lblCommon.setForeground(Color.white);
		lblCommon.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCommon);
		
		JLabel lblCommonAmount = new JLabel("15");
		lblCommonAmount.setBounds(100, 375, 80, 24);
		lblCommonAmount.setForeground(Color.red);
		lblCommonAmount.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblCommonAmount);
		
		JLabel lblBosses = new JLabel("Bosses:");
		lblBosses.setBounds(10, 415, 70, 24);
		lblBosses.setForeground(Color.white);
		lblBosses.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblBosses);
		
		JLabel lblBossesAmount = new JLabel("5");
		lblBossesAmount.setBounds(80, 415, 80, 24);
		lblBossesAmount.setForeground(Color.red);
		lblBossesAmount.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblBossesAmount);
		
		JLabel lblDifficulty = new JLabel("Difficulty:");
		lblDifficulty.setBounds(10, 455, 90, 24);
		lblDifficulty.setForeground(Color.white);
		lblDifficulty.setFont(new Font("Serif", Font.BOLD, 18));
		leftPanel.add(lblDifficulty);
		
		JLabel lblCurDifficulty = new JLabel("Normal");
		lblCurDifficulty.setBounds(100, 455, 90, 24);
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
		lblExits.setBounds(15, 50, 100, 24);
		lblExits.setForeground(Color.white);
		lblExits.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblExits);
		
		JLabel lblCurExits = new JLabel("0");
		lblCurExits.setBounds(110, 50, 80, 24);
		lblCurExits.setForeground(Color.yellow);
		lblCurExits.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblCurExits);
		
		JLabel lblHelp = new JLabel("Controls");
		lblHelp.setBounds(15, 220, 170, 24);
		lblHelp.setForeground(Color.yellow);
		lblHelp.setFont(new Font("Serif", Font.BOLD, 24));
		rightPanel.add(lblHelp);
		
		JLabel lblMoveRight = new JLabel("Move Right:");
		lblMoveRight.setBounds(15, 260, 100, 24);
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
		lblMoveUp.setBounds(15, 340, 100, 24);
		lblMoveUp.setForeground(Color.white);
		lblMoveUp.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblMoveUp);
		
		JLabel lblUpButton = new JLabel("W");
		lblUpButton.setBounds(100, 340, 80, 24);
		lblUpButton.setForeground(Color.yellow);
		lblUpButton.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblUpButton);
		
		JLabel lblMoveDown = new JLabel("Move Down:");
		lblMoveDown.setBounds(15, 380, 100, 24);
		lblMoveDown.setForeground(Color.white);
		lblMoveDown.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblMoveDown);
		
		JLabel lblDownButton = new JLabel("S");
		lblDownButton.setBounds(125, 380, 80, 24);
		lblDownButton.setForeground(Color.yellow);
		lblDownButton.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblDownButton);
		
		JLabel lblZoomOut = new JLabel("Zoom Out:");
		lblZoomOut.setBounds(15, 420, 100, 24);
		lblZoomOut.setForeground(Color.white);
		lblZoomOut.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblZoomOut);
		
		JLabel lblZoomOutButton = new JLabel("M");
		lblZoomOutButton.setBounds(110, 420, 80, 24);
		lblZoomOutButton.setForeground(Color.yellow);
		lblZoomOutButton.setFont(new Font("Serif", Font.BOLD, 18));
		rightPanel.add(lblZoomOutButton);
		
		JLabel lblSpecialWeapon = new JLabel("Special:");
		lblSpecialWeapon.setBounds(15, 460, 100, 24);
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
		Maze m = new Maze(MAZE_DIMENSION, MAZE_DIMENSION);
		model = m.getMaze();
		try {
			gamePanel = new GameView(model);
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
    	currentRow = (int) (MAZE_DIMENSION * Math.random());
    	currentCol = (int) (MAZE_DIMENSION * Math.random());
    	model[currentRow][currentCol] = 'E';
    	updateView(); 		
	}
	
	private void updateView(){
		gamePanel.setCurrentRow(currentRow);
		gamePanel.setCurrentCol(currentCol);
	}

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && currentCol < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow, currentCol + 1)) currentCol++;   		
        }else if (e.getKeyCode() == KeyEvent.VK_LEFT && currentCol > 0) {
        	if (isValidMove(currentRow, currentCol - 1)) currentCol--;	
        }else if (e.getKeyCode() == KeyEvent.VK_UP && currentRow > 0) {
        	if (isValidMove(currentRow - 1, currentCol)) currentRow--;
        }else if (e.getKeyCode() == KeyEvent.VK_DOWN && currentRow < MAZE_DIMENSION - 1) {
        	if (isValidMove(currentRow + 1, currentCol)) currentRow++;        	  	
        }else if (e.getKeyCode() == KeyEvent.VK_M){
        	gamePanel.toggleZoom();
        }else if (e.getKeyCode() == KeyEvent.VK_K){
        	//gamePanel.toggleZoom();
        	// Fire Special Weapon!!
        }else{
        	return;
        }
        
        updateView();       
    }
    
    public void keyReleased(KeyEvent e) {} //Ignore
	public void keyTyped(KeyEvent e) {} //Ignore

	private boolean isValidMove(int r, int c){
		if (r <= model.length - 1 && c <= model[r].length - 1 && model[r][c] == ' '){
			model[currentRow][currentCol] = ' ';
			model[r][c] = 'E';
			return true;
		}else{
			return false; //Can't move
		}
	}
	
	public static void main(String[] args) throws Exception {
		new GameFrame();
	}
}