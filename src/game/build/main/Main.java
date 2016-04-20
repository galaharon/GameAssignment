package game.build.main;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import game.build.ref.Reference;
import game.build.util.Trigger;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;

public class Main
{
	private static final Trigger stopFlag = new Trigger();
	private static final JFrame frame = new JFrame(Reference.TITLE + Reference.VERSION);
	private static final Listener listener =  new Listener();
	private static long lastSaveTime = System.currentTimeMillis();
	
	private static final Game GAME = new Game();
	
	//TODO Documentation + logging
	
	public static void main(String[] args) throws Exception
	{
		//initialisation stuff goes here
		initFrame();

		//mainLoop
		while(!stopFlag.isTriggered())
		{
			try
			{
				mainLoop();
				if(System.currentTimeMillis() - lastSaveTime > TimeUnit.MINUTES.toMillis(15)) //Saves automatically every 15 mins.
				{
					save();
				}
			}
			catch(Exception e)
			{
				//Handle exception
				stopFlag.setTrigger();
			}
		}
		System.out.println("TRIGGERED");
		// clean up
	}
	
	/**
	 * Sets the flag to stop the program from running.
	 */
	public static void setStopFlag()
	{
		stopFlag.setTrigger();
	}
	
	/**
	 * Initialises the JFrame. Should only be called once, from main.
	 */
	private static void initFrame()
	{
		frame.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		frame.addWindowListener(listener);
		frame.addWindowFocusListener(listener);
		frame.addWindowStateListener(listener);
		
		JLabel label = new JLabel("");
		Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
		if(d.height < 720 || d.width < 1280)
		{
			label.setPreferredSize(d);
		}
		else
		{
			label.setPreferredSize(new Dimension(1280,720));
		}
		
		frame.getContentPane().add(label, BorderLayout.CENTER);
		frame.pack();
		frame.setVisible(true);
	}
	
	private static void mainLoop() throws Exception
	{
		//menu stuff happens first, until game starts to run
		if(GAME.isRunning())
		{
			GAME.preLoop();
			GAME.tick();
			GAME.postLoop();
			return;
		}
		//display menu here
	}
	
	public static void save()
	{
		System.out.println("Saving!");
		//Save here
		lastSaveTime = System.currentTimeMillis();
	}
	
	public static void pauseGame()
	{
		if(isGamePaused())
		{
			System.out.println("Game already paused!");
			return;
		}
		GAME.pause();
		//Window stuff
	}
	
	public static void unpauseGame()
	{
		if(!isGamePaused())
		{
			System.out.println("Game already running!");
			return;
		}
		//Window Stuff
		GAME.unpause();
	}
	
	public static boolean isGamePaused()
	{
		return GAME.isGamePaused();
	}
	
	private static class Listener extends WindowAdapter //TODO add functionality.
	{
		@Override
		public void windowClosing(WindowEvent e)
		{
			//XXX IDK?
			System.out.println("Closing!");
		}

		@Override
		public void windowClosed(WindowEvent e)
		{
			System.out.println("Closed!");
			setStopFlag();
		}

		@Override
		public void windowIconified(WindowEvent e)
		{
			System.out.println("Iconed!");
			//PAUSE?
		}

		@Override
		public void windowDeiconified(WindowEvent e)
		{
			System.out.println("Deiconed!");
			//UNPAUSE?
		}

		@Override
		public void windowGainedFocus(WindowEvent e)
		{
			System.out.println("Gained focus!");
		}

		@Override
		public void windowLostFocus(WindowEvent e)
		{
			System.out.println("Lost Focus!");
		}
	}
	
}
