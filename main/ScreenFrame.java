package main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class ScreenFrame extends JFrame
{
	private static final long serialVersionUID = -2657464197599438611L;
	
	private final JLabel label;
	
	public Clock clock;
	
	private boolean opened = false;
	
	public ScreenFrame()
	{
		super();
		
		label = new JLabel();
		label.setHorizontalAlignment(SwingConstants.CENTER);
		
		clock = new Clock(label);
		clock.setTime(30);
		
		setLayout(new BorderLayout());
		
		setExtendedState(JFrame.MAXIMIZED_BOTH); 
		setUndecorated(true);
		
		setBackground(Color.black);
		
		add(label, BorderLayout.CENTER);
	}
	
	public void Open(int screenWidth)
	{
		label.setFont(new Font("Courier New", Font.BOLD | Font.ITALIC, (int) ((screenWidth / 8) / 0.42)));
		setVisible(true);
		opened = true;
	}
	
	public void Close()
	{
		setVisible(false);
		opened = false;
	}
	
	public void setTime(long l)
	{
		clock.setTime(l);
	}
	
	public void Push(Clock.Buttons b)
	{
		clock.Push(b);
	}
	
	public boolean isOpened()
	{
		return opened;
	}
}
