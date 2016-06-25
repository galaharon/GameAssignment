package game.build.main;

import static java.io.File.separator;
import game.build.util.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import org.mp3transform.Decoder;

public class SongPlayer
{
	private static Thread currentThread = null;
	private static String currentSong = null;
	
	@SuppressWarnings("deprecation")
	public static void playSong(String name)
	{
		if(name.equals(currentSong))
		{
			return;
		}
		if(currentThread != null && currentThread.isAlive())
		{
			currentThread.stop();
		}
		Logger.info("Now playing: " + name);
		currentThread = new Thread(()->{
			try
			{
				Decoder decoder = new Decoder();
				File file = new File("resources" + separator + "songs" + separator + name +".mp3");
				FileInputStream in = new FileInputStream(file);
				BufferedInputStream bin = new BufferedInputStream(in, 128 * 1024);
				decoder.play(file.getName(), bin);
				in.close();
                decoder.stop();
                currentSong = null;
            }
			catch(Exception e)
			{
				Logger.fatal("Failed to play song: " + name);
				Logger.trace(e);
            }
		});
		currentSong = name;
		currentThread.start();
	}
	
	public static void playSoundEffect(String name)
	{
		new Thread(()->{
			try
			{
				Decoder decoder = new Decoder();
				File file = new File("resources" + separator + "fx" + separator + name +".mp3");
				FileInputStream in = new FileInputStream(file);
				BufferedInputStream bin = new BufferedInputStream(in, 128 * 1024);
				decoder.play(file.getName(), bin);
				in.close();
                decoder.stop();
                currentSong = null;
            }
			catch(Exception e)
			{
				Logger.error("Failed to play effect: " + name);
				Logger.trace(e);
            }
		}).start();
	}
	
	@SuppressWarnings("deprecation")
	protected static void cleanUp()
	{
		if(currentThread != null && currentThread.isAlive())
		{
			currentThread.stop();
		}
	}
}
