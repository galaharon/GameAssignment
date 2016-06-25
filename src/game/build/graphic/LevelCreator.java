package game.build.graphic;

import game.build.main.BeatFile;
import game.build.main.Main;
import game.build.main.SongPlayer;
import game.build.util.Logger;
import game.build.util.Resources;
import game.build.util.Utils;

import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.KeyStroke;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

@SuppressWarnings("serial")
public class LevelCreator extends MenuPane
{
	private final String song;
	private List<Long> temp = Lists.newArrayList();
	private long lastTime;
	
	protected LevelCreator(File f)
	{
		super("level_creator");
		this.song = f.getName().substring(0, f.getName().length() - 4);
		Logger.info("Now creating map for " + f.getName());
		if(!f.getParentFile().getAbsolutePath().equals(Resources.songDir.getAbsolutePath()))
		{
			System.out.println(f.getParentFile());
			System.out.println(f);
			File newSongFile = new File(Resources.songDir.getAbsolutePath() + File.separator + f.getName());
			try
			{
				newSongFile.createNewFile();
				Files.copy(f, newSongFile);
				System.out.println(newSongFile);
			}
			catch (IOException e)
			{
				Logger.fatal("Couldn't copy song " + this.song);
				Logger.trace(e);
				throw Utils.throwUnchecked(e);
			}
		}
		this.setFocusable(true);
		this.getInputMap().put(KeyStroke.getKeyStroke("SPACE"), "hit");
		this.getActionMap().put("hit", new AbstractAction(){
			@Override
			public void actionPerformed(ActionEvent e)
			{
				long time = System.currentTimeMillis();
				temp.add(time - lastTime);
				lastTime = time;
				//Visual effect?
			}});
		SongPlayer.playSong(this.song); //Add delay?
		lastTime = System.currentTimeMillis();
	}
	
	public void songFinished()
	{
		SongPlayer.playSoundEffect("complete_creation");
		long[] array = temp.stream().mapToLong(Long::valueOf).toArray();
		new BeatFile(this.song, array).toFile(this.song);
		Main.setCurrentScreen(Screens.mainMenu());
	}
}
