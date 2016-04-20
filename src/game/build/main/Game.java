package game.build.main;

public final class Game
{
	private static boolean isInit = false;
	private long tick = 0;
	private boolean isPaused = false;
	
	protected Game()
	{
		if(isInit)
		{
			throw new IllegalStateException("An instance of the game is created!");
		}
		isInit = true;
	}
	
	protected void pause()
	{
		//STUFF
		isPaused = true;
	}
	
	protected void unpause()
	{
		//STUFF
		isPaused = false;
	}
	
	protected boolean isGamePaused()
	{
		return this.isPaused;
	}

	protected boolean isRunning()
	{
		return false; //TODO
	}
	
	protected void preLoop()
	{
		
	}
	
	protected void tick()
	{
		tick++;
	}
	
	protected void postLoop()
	{
		
	}
}
