package com.matthewmuccio.application;

import java.awt.Image;
import javax.swing.ImageIcon;

// Player launches Shot objects using space key.
public class Shot
{
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean visible;
	private Image image;
	
	// Initiates x and y coordinates and the visible flag when Player launches a Shot..
	public Shot(int x, int y)
	{
		this.x = x;
		this.y = y;
		visible = true;
	}
	
	protected void loadImage(String imageName)
	{
		ImageIcon imageIcon = new ImageIcon(imageName);
		this.image = imageIcon.getImage();
	}
	
	protected void getImageDimensions()
	{
		this.width = image.getWidth(null);
		this.height = image.getHeight(null);
	}

	public void setVisible(Boolean visible)
	{
		this.visible = visible;
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