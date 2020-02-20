package main;

import java.io.IOException;

import javax.swing.SwingUtilities;

public class Main
{
	public static MainFrame mainFrame;
	public static ScreenFrame screenFrame;
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable()
		{
			public void run()
			{
				try {
					mainFrame = new MainFrame();
					screenFrame = new ScreenFrame();
				} catch (IOException e) {
					System.err.print("Couldn't start the program.");
					e.printStackTrace();
				}
				
			}
		});
	}

}