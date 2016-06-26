package game.build.graphic;

import static game.build.util.Utils.randomPointOnBoundary;
import game.build.main.BeatFile;
import game.build.main.Main;
import game.build.main.SongPlayer;
import game.build.util.Logger;
import game.build.util.Resources;
import game.build.util.Utils;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.KeyStroke;

import com.google.common.collect.Lists;
import com.google.common.io.Files;

@SuppressWarnings("serial")
public class LevelCreator extends MenuPane
{
	private final String song;
	private List<Long> temp = Lists.newArrayList();
	private long lastTime;
	private final List<Projectile> projectiles = Collections.synchronizedList(Lists.newLinkedList());
	private static final Random r = new Random();
	
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
				Point mouse = MouseInfo.getPointerInfo().getLocation();
				int scaledX = mouse.x - LevelCreator.this.getLocationOnScreen().x - 25;
				int scaledY = mouse.y - LevelCreator.this.getLocationOnScreen().y - 25;
				for(int i = 0; i < 7; i++)
				{
					LevelCreator.this.addLayer(new Projectile(new Point(scaledX, scaledY), 10000, randomPointOnBoundary(r, 10), 4D, 0));
				}
				LevelCreator.this.validate();
				LevelCreator.this.repaint();
			}});
		SongPlayer.playSong(this.song); //Add delay?
		lastTime = System.currentTimeMillis();
	}
	
	@Override
	public void addLayer(JComponent layer)
	{
		if(layer instanceof Projectile)
		{
			projectiles.add((Projectile)layer);
		}
		super.addLayer(layer);
	}
	
	public void songFinished()
	{
		SongPlayer.playSoundEffect("complete_creation");
		long[] array = temp.stream().mapToLong(Long::valueOf).toArray();
		new BeatFile(this.song, array).toFile(this.song);
		synchronized(this.projectiles)
		{
			for(Projectile p : this.projectiles)
			{
				p.kill();
			}
		}
		this.update();
		Main.setCurrentScreen(Screens.mainMenu());
	}
	
	public void update()
	{
		synchronized(this.projectiles)
		{
			Iterator<Projectile> it = projectiles.iterator();
			while(it.hasNext())
			{
				Projectile p = it.next();
				if(p.isDead())
				{
					it.remove();
					this.remove(p);
					continue;
				}
				p.update();
			}
		}
		this.repaint();
	}
}
