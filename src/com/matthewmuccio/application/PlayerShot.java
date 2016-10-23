package com.matthewmuccio.application;

public class PlayerShot extends Entity
{
	private final int BOARD_WIDTH = 2000;
	private final int SHOT_SPEED = 8;

	public PlayerShot(int x, int y)
	{
		super(x, y);

		this.init();
	}

	private void init()
	{
		this.loadImage("playerShot.png");
		this.getImageDimensions();
	}
	
	// PlayerShot moves at a constant speed in one direction.
	// When it hits the right border of the Board, it becomes
	// invisible and is then removed from the list of PlayerShots.
	public void move()
	{
		x += SHOT_SPEED;
		
		if (x > BOARD_WIDTH)
		{
			visible = false;
		}
	}
}