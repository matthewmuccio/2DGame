package com.matthewmuccio.application;

import java.awt.Image;
import java.awt.Rectangle;

import javax.swing.ImageIcon;

// Player launches Entity objects using space key.
public class Entity
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean visible;
	private Image image;
	
	// Initiates x and y coordinates and the visible flag when Player launches a Shot..
	// All sprites on screen (Player, PlayerShot, Enemy) are in the Entity class.
	public Entity(int x, int y)
	{
		this.x = x;
		this.y = y;
		visible = true;
	}
	
	// Loads image of Entity from an ImageIcon and 
	// sets the image property to it.
	protected void loadImage(String imageName)
	{
		ImageIcon imageIcon = new ImageIcon(imageName);
		this.image = imageIcon.getImage();
	}

	// Sets visibility of given Entity.
	public void setVisible(Boolean visible)
	{
		this.visible = visible;
	}
	
	// Returns dimensions of the image.
	protected void getImageDimensions()
	{
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
	}
	
	// Returns the bounding rectangle of the Entity.
	// Important for collision detection.
	public Rectangle getBounds()
	{
		Rectangle rectangle = new Rectangle(x, y, width, height);
		return rectangle;
	}
	
	// Getters
	public Image getImage()
	{
		return image;
	}
	
	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}
	
	public boolean isVisible()
	{
		return visible;
	}
}