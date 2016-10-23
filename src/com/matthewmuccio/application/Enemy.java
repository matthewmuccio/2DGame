package com.matthewmuccio.application;

public class Enemy extends Entity
{
	private final int INITIAL_X = 2000;
	private final int ENEMY_SPEED = 5;
	
	public Enemy(int x, int y)
	{
		super(x, y);
		
		this.init(x, y);
	}
	
	private void init(int x, int y)
	{
		this.loadImage("enemy.png");
		this.getImageDimensions();
	}
	
	// Moves Enemy.
	// Enemies will loop back to right side of JPanel Board
	// after they have disappeared on the left side.
	public void move()
	{
		if (x < -100)
		{
			x = INITIAL_X;
		}
		
		x -= ENEMY_SPEED;
	}
}
