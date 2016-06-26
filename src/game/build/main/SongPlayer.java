package game.build.main;

import static java.io.File.separator;
import game.build.util.Logger;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicReference;

import org.mp3transform.Decoder;

public class SongPlayer
{
	private static AtomicReference<Thread> currentThread = new AtomicReference<>();
	private static AtomicReference<String> currentSong = new AtomicReference<>();
	public static AtomicBoolean playing = new AtomicBoolean();
	public static AtomicBoolean loop = new AtomicBoolean();
	
	public static void playSong(String name)
	{
		if(name.equals(currentSong.get()))
		{
			return;
		}
		if(currentThread.get() != null && currentThread.get().isAlive())
		{
			killThread();
		}
		Logger.info("Now playing: " + name);
		currentThread.set(new Thread(()->{
			try
			{
				do
				{
				Decoder decoder = new Decoder();
				File file = new File("resources" + separator + "songs" + separator + name +".mp3");
				FileInputStream in = new FileInputStream(file);
				BufferedInputStream bin = new BufferedInputStream(in, 128 * 1024);
				playing.set(true);
				decoder.play(file.getName(), bin);
				in.close();
				decoder.stop();
				}
				while(loop.get());
				
				playing.set(false);
				currentSong.set(null);
			}
			catch(Exception e)
			{
				Logger.fatal("Failed to play song: " + name);
				Logger.trace(e);
			}
		}));
		currentSong.set(name);
		currentThread.get().start();
	}
	
	@SuppressWarnings("deprecation")
	private static void killThread()
	{
		currentThread.get().stop();
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
		if(currentThread.get() != null && currentThread.get().isAlive())
		{
			killThread();
		}
	}
}
