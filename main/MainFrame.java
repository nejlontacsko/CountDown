package main;

import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

public class MainFrame extends JFrame
{
	private static final long serialVersionUID = -2951176757088592031L;
	
	private final GridLayout gridLayout;
		
	private final JLabel
		screenLabel,
		hoursLabel,
		minutesLabel,
		secondsLabel;
	private final JComboBox<String> screenCombo;
	private final JButton
		openButton,
		setButton,
		resetButton,
		startButton;
	private final JSpinner
		hours,
		minutes,
		seconds;
	
	public MainFrame() throws IOException
	{
		super("Számláló");
		
		gridLayout = new GridLayout(4, 2, 10, 10);
		
		setLayout(gridLayout);
		
		setSize(500, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		
		Process proc = Runtime.getRuntime().exec("xrandr --listmonitors");

		BufferedReader stdInput = new BufferedReader(new 
		     InputStreamReader(proc.getInputStream()));

		List<String> lines = new ArrayList<String>();
		String s = null;
		while ((s = stdInput.readLine()) != null)
		    lines.add(s.substring(5));
		lines.remove(0);
		
		GraphicsDevice[] screens = GraphicsEnvironment.getLocalGraphicsEnvironment().getScreenDevices();	
		screenCombo = new JComboBox<String>(lines.toArray(new String[lines.size()]));
		
		openButton = new JButton("Megnyitás");
		openButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				screenCombo.setEnabled(false);
				if (Main.screenFrame.isOpened())
				{
					Main.screenFrame.Close();
					openButton.setText("Megnyitás");
				}
				else
				{
					Rectangle bounds = screens[screenCombo.getSelectedIndex()].getDefaultConfiguration().getBounds();
					Main.screenFrame.setLocation(bounds.getLocation());
					Main.screenFrame.Open(bounds.height);
					openButton.setText("Bezárás");
				}
			}
		});
		setButton = new JButton("Időt beállít");
		setButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.screenFrame.setTime(
						(int)hours.getValue() * 3600 +
						(int)minutes.getValue() * 60 +
						(int)seconds.getValue());
				
				Main.screenFrame.Push(Clock.Buttons.SET);
			}
		});
		resetButton = new JButton("Reset");
		resetButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.screenFrame.setTime(
						(int)hours.getValue() * 3600 +
						(int)minutes.getValue() * 60 +
						(int)seconds.getValue());
				Main.screenFrame.Push(Clock.Buttons.RESET);
			}
		});
		startButton = new JButton("Indít/Megállít");
		startButton.addActionListener(new ActionListener()
		{
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Main.screenFrame.Push(Clock.Buttons.START);
			}
		});
		
		hours   = new JSpinner(new SpinnerNumberModel(0, 0, 24, 1));
		minutes = new JSpinner(new SpinnerNumberModel(0, 0, 59, 1));
		seconds = new JSpinner(new SpinnerNumberModel(1, 0, 59, 1));
		
		screenLabel = new JLabel("Kijelző: ", SwingConstants.RIGHT);
		hoursLabel = new JLabel("Óra: ", SwingConstants.RIGHT);
		minutesLabel = new JLabel("Perc: ", SwingConstants.RIGHT);
		secondsLabel = new JLabel("Másodperc:", SwingConstants.RIGHT);
		
		add(screenLabel);
		add(screenCombo);
		add(openButton);
		add(hoursLabel);
		add(hours);
		add(setButton);
		add(minutesLabel);
		add(minutes);
		add(startButton);
		add(secondsLabel);
		add(seconds);
		add(resetButton);
	}
}
