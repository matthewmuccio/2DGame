package com.matthewmuccio.application;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

// Board is a JPanel where the game takes place.
// We listen for key events.
// Implements ActionListener interface.
public class Board extends JPanel implements ActionListener
{
	// Instance fields/properties
	private Timer timer;
	private Player player;
	private ArrayList<Enemy> enemyList;
	private boolean gameOver;
	private int score;
	private final int DEFAULT_ENEMY_NUM = 5; // This number^2 is the number of Enemies spawned.
	private final int PLAYER_X = 500;
	private final int PLAYER_Y = 500;
	private final int BOARD_WIDTH = 2000;
	private final int BOARD_HEIGHT = 1500;
	private final int DELAY = 10; // ms
	
	private int[][] positions = new int[DEFAULT_ENEMY_NUM][DEFAULT_ENEMY_NUM];
	
	// Objects on Board are either images or drawings 
	// created with the painting tools in the Java 2D API.
	public Board()
	{
		this.init();
	}
	
	// Initializes Board.
	private void init()
	{
		this.addKeyListener(new TAdapter());
		this.setFocusable(true);
		this.setBackground(Color.BLACK);
		Dimension dimension = new Dimension(BOARD_WIDTH, BOARD_HEIGHT);
		this.setPreferredSize(dimension);
		this.gameOver = false;
		this.score = 0;
		
		this.initPositions();
		this.initEnemies();
		player = new Player(PLAYER_X, PLAYER_Y);
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	// Initializes positions in the positions Array.
	// Randomly sets positions for the Enemies.
	public void initPositions()
	{	
		// Loops and adds Enemies to the enemyList at random positions.
		for (int x = 0; x < positions.length; x++)
		{
			for (int y = 0; y < positions[x].length; y++)
			{
				// Randomly sets the initial positions of the Enemies.
				Random random = new Random();
				
				int rand = random.nextInt(BOARD_WIDTH);
				
				this.positions[x][y] = rand;
			}
		}
	}
	
	// Initializes Enemies on JPanel Board.
	public void initEnemies()
	{
		// Instantiates the list of Enemy objects (enemyList instance field).
		this.enemyList = new ArrayList<Enemy>();
		
		// For every int[] position in int[][] positions,
		// Add an Enemy to the enemyList.
		for (int x = 0; x < positions.length; x++)
		{
			for (int y = 0; y < positions[x].length; y++)
			{
				Enemy enemy = new Enemy(positions[x][y], positions[y][x]);
				this.enemyList.add(enemy);
			}
			
		}
		
		/*for (int[] position : positions)
		{
			Enemy enemy = new Enemy(position[0], position[1]);
			this.enemyList.add(enemy);
		}*/
	}
	
	// All painting is done inside this method.
	// Either draw game Entities or write the "Game Over" message.
	@Override
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		
		if (!gameOver)
		{
			this.drawObjects(graphics);
		}
		else
		{
			this.drawGameOver(graphics);
		}
		
		Toolkit.getDefaultToolkit().sync();
	}
	
	// Draws PlayerShot and Enemies Entities as they appear on the JFrame.
	private void drawObjects(Graphics graphics)
	{
		ArrayList<PlayerShot> shots = player.getPlayerShots();
		
		// Draw Player image on the screen if he/she is visible.
		if (player.isVisible())
		{
			graphics.drawImage(player.getImage(), player.getX(), player.getY(), this);
		}
		
		// For all PlayerShots in playerShots ArrayList,
		// draw them as Player shoots them.
		for (PlayerShot playerShot : shots)
		{
			if (playerShot.isVisible())
			{
				graphics.drawImage(playerShot.getImage(), playerShot.getX(), playerShot.getY(), this);
			}
		}
		
		// For all Enemies in enemyList ArrayList,
		// draw them if they are visible (not already dead).
		for (Enemy enemy : enemyList)
		{
			if (enemy.isVisible())
			{
				graphics.drawImage(enemy.getImage(), enemy.getX(), enemy.getY(), this);
			}
		}
		
		Font font = new Font("Arial", Font.BOLD, 75);
		FontMetrics fontMetrics = this.getFontMetrics(font);
		
		String scoreStr = "Score:" + score;
		
		// Draws string with Player score.
		graphics.setColor(Color.WHITE);
		graphics.setFont(font);
		graphics.drawString(scoreStr, 5, 60);
		
		// Draws string with number of remaining Enemies in top-left of Board.
		graphics.setColor(Color.WHITE);
		graphics.drawString("Enemies left:" + enemyList.size(), 5, 120);
	}
	
	// Draws "Game Over" text in middle of the screen when game flag is false.
	// When Player kills all Enemies or a collision occurs.
	private void drawGameOver(Graphics graphics)
	{
		String gameOver = "Game Over";
		
		Font font = new Font("Courier New", Font.BOLD, 100);
		FontMetrics fontMetrics = this.getFontMetrics(font);
		
		graphics.setColor(Color.WHITE);
		graphics.setFont(font);
		graphics.drawString(gameOver, (BOARD_WIDTH - fontMetrics.stringWidth(gameOver))/2, BOARD_HEIGHT/2);
	}
	
	// We move the sprite and repaint the board. (Called every DELAY ms.)
	// Each action event is one game cycle.
	// The game logic is separated in specific methods.
	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{
		this.gameLoop();
		
		this.updatePlayer();
		this.updatePlayerShots();
		this.updateEnemies();
		
		this.checkCollisions();
		
		this.repaint();
	}
	
	// Parses all PlayerShots from the playerShots list.
	// Depending on whether the PlayerShot is visible,
	// it either moves or is removed from the container.
	private void updatePlayerShots()
	{
		ArrayList<PlayerShot> playerShots = player.getPlayerShots();
		
		for (int i = 0; i < playerShots.size(); i++)
		{
			PlayerShot playerShot = (PlayerShot)playerShots.get(i);
			
			if (playerShot.isVisible())
			{
				playerShot.move();
			}
			else
			{
				playerShots.remove(i);
			}
		}
	}
	
	private void gameLoop()
	{
		if (gameOver)
		{
			timer.stop();
		}
	}
	
	private void updatePlayer()
	{
		if (player.isVisible())
		{
			player.move();
		}
	}
	
	// Check if there are any Enemy objects in enemyList.
	// If list is empty, then gameOver = true and the game is over.
	// If not empty, go through the list and move all its items.
	// Killed Enemies are removed from the list.
	private void updateEnemies()
	{
		if (enemyList.isEmpty())
		{
			this.gameOver = true;
			return;
		}
		
		for (int i = 0; i < enemyList.size(); i++)
		{
			Enemy enemy = enemyList.get(i);
			
			if (enemy.isVisible())
			{
				enemy.move();
			}
			else
			{
				enemyList.remove(i);
			}
		}
	}
	
	// Checks for potential collisions between Player and Enemies.
	// Check if Player collides with any Enemies.
	// Created rectangles to bound the objects.
	public void checkCollisions()
	{
		Rectangle playerRect = player.getBounds();
		
		// Checks if Player hits Enemy (or vice versa).
		// For every Enemy in the enemyList, check for collision.
		for (Enemy enemy : enemyList)
		{
			Rectangle enemyRect = enemy.getBounds();
			
			if (playerRect.intersects(enemyRect))
			{
				player.setVisible(false);
				enemy.setVisible(false);
				gameOver = true;
			}
		}
		
		
		ArrayList<PlayerShot> playerShots = player.getPlayerShots();
		
		// Checks if PlayerShot hits Enemy.
		// For every PlayerShot in playerShots list (on screen), check for collision.
		// Every playerShot --> Enemy collision is one point for Player.
		for (PlayerShot playerShot : playerShots)
		{
			Rectangle playerShotRect = playerShot.getBounds();
			
			for (Enemy enemy : enemyList)
			{
				Rectangle enemyRect = enemy.getBounds();
				
				if (playerShotRect.intersects(enemyRect))
				{
					playerShot.setVisible(false);
					enemy.setVisible(false);
					score++;
				}
			}
		}
	}
	
	// The overriden methods of the KeyAdapter delegate
	// the processing to the methods of the Player class.
	private class TAdapter extends KeyAdapter
	{
		@Override
		public void keyReleased(KeyEvent keyEvent)
		{
			player.keyReleased(keyEvent);
		}
		
		@Override
		public void keyPressed(KeyEvent keyEvent)
		{
			player.keyPressed(keyEvent);
		}
	}
	
	/*
	// Draws Player and all avaliable PlayerShots.
	// We get the image and coordinate properties from the Player class.
	private void draw(Graphics graphics)
	{
		Graphics2D graphics2D = (Graphics2D)graphics;
		graphics2D.drawImage(player.getImage(), player.getX(), player.getY(), this);
	
		ArrayList<PlayerShot> playerShots = player.getPlayerShots();
		
		for (Object playerShot : playerShots)
		{
			PlayerShot shot = (PlayerShot)playerShot;
			
			graphics2D.drawImage(shot.getImage(), shot.getX(), shot.getY(), this);
		}
	}
	*/
	
	/*
	// Draws text on JFrame.
	private void drawText(Graphics graphics)
	{
		Graphics2D graphics2D = (Graphics2D)graphics;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		labelScore.setFont(new Font("Courier New", Font.BOLD, 75));
		labelScore.setForeground(Color.WHITE);
		labelScore.setBounds(10, 0, labelScore.getWidth(), labelScore.getHeight());
		labelScore.setText("Score:" + this.player.getPlayerShots().size());
	}
	*/
	
	/*
	// Donut drawing
	// Delegates the actual painting to a specific method (good programmign practice).
	private void draw(Graphics graphics)
	{
		// Graphics2D class extends the Graphics class.
		// It provides more control over geometry,
		// coordinate transformations, color management,
		// and text layout.
		Graphics2D graphics2D = (Graphics2D)graphics;
		
		// RH used to make the drawing smooth.
		RenderingHints renderingHints = new RenderingHints(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		renderingHints.put(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		graphics2D.setRenderingHints(renderingHints);
		
		// Size (width and height of the JFrame window).
		// Used to center the drawing on the window.
		Dimension size = this.getSize();
		double width = size.getWidth();
		double height = size.getHeight();
		
		// Creates the ellipse object.
		Ellipse2D ellipse2D = new Ellipse2D.Double(0, 0, 80, 130);
		graphics2D.setStroke(new BasicStroke(1));
		graphics2D.setColor(Color.GRAY);
		
		// Ellipse object is rotated 72 tiems to create a donut shape.
		for (double d = 0; d < 360; d += 5)
		{
			AffineTransform affineTransform = AffineTransform.getTranslateInstance(width/2, height/2);
			affineTransform.rotate(Math.toRadians(d));
			graphics2D.draw(affineTransform.createTransformedShape(ellipse2D));
		}
	}
	*/
}