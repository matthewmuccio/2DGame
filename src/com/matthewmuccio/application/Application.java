package com.matthewmuccio.application;

import java.awt.EventQueue;
import javax.swing.JFrame;

// Application is the entry point of the game.
public class Application extends JFrame
{
	public Application()
	{
		this.init();
	}
	
	private void init()
	{
		// A new Board is put in the center of the JFrame container.
		this.add(new Board());
		
		// Sets properties of JFrame window.
		this.setSize(2000, 1500);
		this.setTitle("2D Game");
		
		// Application will close when user clicks on the close button. (Non-default behavior)
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// Centers the JFrame window on the screen.
		this.setLocationRelativeTo(null);
	}
	
	public static void main(String[] args)
	{
		EventQueue.invokeLater(new Runnable()
		{
			@Override
			public void run()
			{
				Application app = new Application();
				app.setVisible(true);
			}
		});
	}
}