package game.build.main;

import static java.io.File.separator;
import game.build.util.Logger;
import game.build.util.Utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class BeatFile
{
	private String songName;
	private long[] timeDiffs;
	
	public BeatFile(String name, long[] array)
	{
		this.songName = name;
		this.timeDiffs = array;
	}
	
	public long get(int i)
	{
		return timeDiffs[i];
	}
	
	public int arrSize()
	{
		return timeDiffs.length;
	}
	
	public String songName()
	{
		return this.songName;
	}
	
	public void toFile(String name)
	{
		File file = new File("resources" + separator + "maps" + separator + name + ".map");
		try
		{
			file.createNewFile();
		}
		catch(IOException e)
		{
			Logger.fatal("Failed to create " + name + " to map file!");
			Logger.trace(e);
			Utils.throwUnchecked(e);
		}
		try(FileOutputStream out = new FileOutputStream(file))
		{
			FileChannel channel = out.getChannel();
			ByteBuffer buf = ByteBuffer.allocate(this.songName.getBytes().length + 4 + 8*this.timeDiffs.length + 4);
			buf.putInt(this.songName.getBytes().length);
			buf.put(this.songName.getBytes("UTF-8"));
			buf.putInt(this.timeDiffs.length);
			for(long l : this.timeDiffs)
			{
				buf.putLong(l);
			}
			buf.rewind();
			channel.write(buf);
			channel.close();
		}
		catch (IOException e)
		{
			Logger.fatal("Failed to write " + name + " to map file!");
			Logger.trace(e);
			Utils.throwUnchecked(e);
		}
		
	}
	
	public static BeatFile fromFile(File file)
	{
		Logger.trace("Attempting to read " + file.getName());
		try(FileInputStream in= new FileInputStream(file))
		{
			FileChannel channel = in.getChannel();
			ByteBuffer buf = channel.map(FileChannel.MapMode.READ_ONLY, 0, channel.size());
			byte[] bytes = new byte[buf.getInt()];
			buf.get(bytes);
			String songName = new String(bytes,0,bytes.length,"UTF-8");
			long[] array = new long[buf.getInt()];
			for(int i = 0; i < array.length; i++)
			{
				array[i] = buf.getLong();
			}
			return new BeatFile(songName, array);
		}
		catch (IOException e)
		{
			Logger.fatal("Failed to read " + file.getName());
			Logger.trace(e);
			throw Utils.throwUnchecked(e);
		}
	}
}
