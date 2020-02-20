package main;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

import javax.swing.JLabel;
import javax.swing.SwingUtilities;

import timerprojektor.TimerService;
import timerprojektor.counter.CounterStatemachine;

public class Clock
{
	private final SimpleDateFormat sdf;
	
	@SuppressWarnings("unused")
	private JLabel label;
	
	private CounterStatemachine sm;
	
	public enum Buttons
	{
		SET, RESET, START
	}
	
	public Clock(JLabel label)
	{
		this.label = label;
		
		sdf = new SimpleDateFormat("HH:mm:ss");
		sdf.setTimeZone(TimeZone.getTimeZone("UTC+1"));
		
		sm = new CounterStatemachine();
		sm.setTimer(new TimerService());
		sm.init();
		sm.enter();
		
		Thread t = new Thread(new Runnable()
		{
        	@Override
        	public void run() {
	        	while (true) {
					SwingUtilities.invokeLater(new Runnable() {
						@Override
						public void run() {
							label.setText(getTimeString());
							sm.runCycle();
						}
					});
	
					try {
						Thread.sleep(100);
					} catch (InterruptedException ex) {
						break;
					}
				}
        	}
		});
        t.setName("Updater");
        t.setDaemon(true);
        t.start();
	}
	
	public void Incr(int i)
	{
		setTime(getTime() + i);
	}
	
	public void Decr(int i)
	{
		setTime(getTime() - i);
	}
	
	public long getTime()
	{
		return sm.getSCINums().getSecs();
	}
	
	public void setTime(long l)
	{
		sm.getSCINums().setSecs(l);
	}
	
	public boolean isZero()
	{
		return getTime() == 0;
	}
	
	public void Push(Buttons b)
	{
		switch (b)
		{
		case SET:   break;
		case RESET: sm.getSCIButtons().raiseReset(); break;
		case START: sm.getSCIButtons().raiseStart(); break;
		}
	}
	
	public String getTimeString()
	{
		return sdf.format(new Date(getTime() * 1000));
	}
}
