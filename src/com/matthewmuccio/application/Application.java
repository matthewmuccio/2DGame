package com.matthewmuccio.application;

import java.awt.EventQueue;
import javax.swing.JFrame;

// Application is the entry point of the game.
public class Application extends JFrame
{
	public Application()
	{
		
	}
	
	private void initUI()
	{
		// A new Board is put in the center of the JFrame container.
		this.add(new Board());
		
		// Sets properties of JFrame window.
		this.setSize(250, 200);
		this.setTitle("2D Game");
		
		// Application will close when user clicks on the close button. (Non-default behavior)
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Centers the JFrame window on the screen.
		this.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args)
	{
		
	}
}