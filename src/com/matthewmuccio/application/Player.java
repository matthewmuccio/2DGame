package com.matthewmuccio.application;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

// Player represents a sprite that the user controls.
// Contains image and coordinate properties of the sprite.
// key() methods control the sprite's movement.
public class Player extends Entity
{
	private int dx;
	private int dy;
	// All playerShots fired by the Player are stored in this list.
	private ArrayList<PlayerShot> playerShots;
	
	private final int PLAYER_SPEED = 5;
	
	// Default Player constructor.
	public Player(int x, int y)
	{
		super(x, y);
		
		this.init();
	}
	
	// Initializes Player, all real image creation is done here.
	private void init()
	{
		this.playerShots = new ArrayList<PlayerShot>();
		this.loadImage("player.png");
		this.getImageDimensions();
	}
	
	// Changes the coordinates of the sprite.
	// x and y values are used in paintComponent()
	// to draw image of sprite.
	public void move()
	{
		x += dx;
		y += dy;
		
		if (x < PLAYER_SPEED)
		{
			x = PLAYER_SPEED;
		}
		if (y < PLAYER_SPEED)
		{
			y = PLAYER_SPEED;
		}
	}
	
	// Returns the ArrayList of PlayerShots, called from Board class.
	public ArrayList<PlayerShot> getPlayerShots()
	{
		return playerShots;
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
	
	// When given key is pressed.
	public void keyPressed(KeyEvent keyEvent)
	{
		int key = keyEvent.getKeyCode();
		
		if (key == KeyEvent.VK_SPACE)
		{
			this.shoot();
		}
		if (key == KeyEvent.VK_A)
		{
			dx = -PLAYER_SPEED;
		}
		if (key == KeyEvent.VK_D)
		{
			dx = PLAYER_SPEED;
		}
		if (key == KeyEvent.VK_W)
		{
			dy = -PLAYER_SPEED;
		}
		if (key == KeyEvent.VK_S)
		{
			dy = PLAYER_SPEED;
		}
	}
	
	// Fires shot when user presses Space key.
	// Creates new PlayerShot object and adds it to the playerShots ArrayList.
	// Remains in the list until it collides with an Enemy or
	// goes out of the JFrame.
	public void shoot()
	{
		PlayerShot playerShot = new PlayerShot(x + width, y + height/2);
		this.playerShots.add(playerShot);
	}
	
	// When given key is released.
	public void keyReleased(KeyEvent keyEvent)
	{
		int key = keyEvent.getKeyCode();
		
		// Player stops moving
		if (key == KeyEvent.VK_A)
		{
			dx = 0;
		}
		if (key == KeyEvent.VK_D)
		{
			dx = 0;
		}
		if (key == KeyEvent.VK_W)
		{
			dy = 0;
		}
		if (key == KeyEvent.VK_S)
		{
			dy = 0;
		}
	}
}