package game.build.main;

import static java.io.File.separator;
import game.build.util.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import org.mp3transform.Decoder;

public class SongPlayer
{
	private static Thread currentThread = null;
	private static String currentSong = null;
	public static AtomicBoolean playing = new AtomicBoolean(false);
	
	public static void playSong(String name)
	{
		if(name.equals(currentSong))
		{
			return;
		}
		if(currentThread != null && currentThread.isAlive())
		{
			killThread();
		}
		Logger.info("Now playing: " + name);
		currentThread = new Thread(()->{
			try
			{
				Decoder decoder = new Decoder();
				File file = new File("resources" + separator + "songs" + separator + name +".mp3");
				FileInputStream in = new FileInputStream(file);
				BufferedInputStream bin = new BufferedInputStream(in, 128 * 1024);
				playing.set(true);
				decoder.play(file.getName(), bin);
				in.close();
                decoder.stop();
                playing.set(false);
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
	
	@SuppressWarnings("deprecation")
	private static void killThread()
	{
		currentThread.stop();
		playing.set(false);
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
	
	protected static void cleanUp()
	{
		if(currentThread != null && currentThread.isAlive())
		{
			killThread();
		}
	}
}
