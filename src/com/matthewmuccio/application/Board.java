package com.matthewmuccio.application;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import javax.swing.JLabel;
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
	private JLabel labelScore;
	private int scoreCount;
	private final int PLAYER_X = 40;
	private final int PLAYER_Y = 60;
	private final int DELAY = 10; // ms
	
	// Objects on Board are either images or drawings 
	// created with the painting tools in the Java 2D API.
	public Board()
	{
		this.init();
	}
	
	private void init()
	{
		this.addKeyListener(new TAdapter());
		this.setFocusable(true);
		this.setBackground(Color.BLACK);
		this.setDoubleBuffered(true);
		
		player = new Player(PLAYER_X, PLAYER_Y);
		timer = new Timer(DELAY, this);
		timer.start();
		labelScore = new JLabel("score");
		this.add(labelScore);
	}
	
	// All painting is done inside this method.
	@Override
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		
		this.draw(graphics);
		
		this.drawText(graphics);
		
		Toolkit.getDefaultToolkit().sync();
	}
	
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
	
	// Draws text on JFrame.
	private void drawText(Graphics graphics)
	{
		Graphics2D graphics2D = (Graphics2D)graphics;
		graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		labelScore.setFont(new Font("Courier New", Font.BOLD, 100));
		labelScore.setForeground(Color.WHITE);
		labelScore.setText("Score");
	}
	
	// We move the sprite and repaint the board. (Called every DELAY ms.)
	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{
		this.updatePlayerShots();
		this.updatePlayer();
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
	
	private void updatePlayer()
	{
		player.move();
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
	}*/
}