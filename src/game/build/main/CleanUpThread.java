package game.build.main;

import game.build.util.Logger;

public class CleanUpThread extends Thread
{
	@Override
	public void run()
	{
		Logger.trace("Clean up thread initiated.");
		SongPlayer.cleanUp();
		Logger.trace("Clean up complete.");
	}
}
