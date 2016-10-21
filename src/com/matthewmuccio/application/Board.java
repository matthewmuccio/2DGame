package com.matthewmuccio.application;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
//import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
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
	private final int DELAY = 10;
	
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
		
		player = new Player();
		timer = new Timer(DELAY, this);
		timer.start();
	}
	
	// All painting is done inside this method.
	@Override
	public void paintComponent(Graphics graphics)
	{
		super.paintComponent(graphics);
		
		this.draw(graphics);
	}
	
	// Draws player with the drawImage() method.
	// We get the image and coordinate properties from the Player class.
	private void draw(Graphics graphics)
	{
		Graphics2D graphics2D = (Graphics2D)graphics;
		graphics2D.drawImage(player.getImage(), player.getX(), player.getY(), this);
	}
	
	// We move the sprite and repaint the board. (Called every DELAY ms.)
	@Override
	public void actionPerformed(ActionEvent actionEvent)
	{
		player.move();
		this.repaint();
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