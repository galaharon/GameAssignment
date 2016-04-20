package game.build.main;

import game.build.util.Trigger;

public class Main
{
	private static final Trigger stopFlag = new Trigger();
	
	public static void main(String[] args) throws Exception
	{
		//initialisation stuff goes here
		//Open window
		
		//mainLoop
		while(!stopFlag.isTriggered())
		{
			try
			{
				mainLoop();
			}
			catch(Exception e)
			{
				//Handle exception
				stopFlag.setTrigger();
			}
		}
		// clean up
	}
	
	/**
	 * Sets the flag to stop the program from running.
	 */
	public static void setStopFlag()
	{
		stopFlag.setTrigger();
	}
	
	
	private static void mainLoop() throws Exception
	{

	}
	
}
