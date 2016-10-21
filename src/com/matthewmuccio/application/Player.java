package com.matthewmuccio.application;

import java.awt.Image;
import java.awt.event.KeyEvent;
import javax.swing.ImageIcon;

// Player represents a sprite that the user controls.
// Contains image and coordinate properties of the sprite.
// key() methods control the sprite's movement.
public class Player
{
	private int x;
	private int y;
	private int dx;
	private int dy;
	private Image image;
	
	// Default Player constructor.
	public Player()
	{
		this.init();
	}
	
	// Initializes Player, all real image creation is done here.
	private void init()
	{
		ImageIcon imageIcon = new ImageIcon("player.png");
		image = imageIcon.getImage();
		x = 40;
		y = 60;
	}
	
	// Changes the coordinates of the sprite.
	// x and y values are used in paintComponent()
	// to draw image of sprite.
	public void move()
	{
		this.x += dx;
		this.y += dy;
	}
	
	// Getters
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public Image getImage()
	{
		return image;
	}
	
	// When given key is pressed.
	public void keyPressed(KeyEvent keyEvent)
	{
		int key = keyEvent.getKeyCode();
		
		if (key == KeyEvent.VK_A)
		{
			dx = -5;
		}
		if (key == KeyEvent.VK_D)
		{
			dx = 5;
		}
		if (key == KeyEvent.VK_W)
		{
			dy = -5;
		}
		if (key == KeyEvent.VK_S)
		{
			dy = 5;
		}
	}
	
	// When given key is released.
	public void keyReleased(KeyEvent keyEvent)
	{
		int key = keyEvent.getKeyCode();
		
		// Player stops moving
		if (key == KeyEvent.VK_A ||
			key == KeyEvent.VK_D)
		{
			dx = 0;
		}
		if (key == KeyEvent.VK_W ||
			key == KeyEvent.VK_S)
		{
			dy = 0;
		}
	}
}