package game.build.graphic;

import game.build.main.BeatFile;
import game.build.main.Main;
import game.build.main.SongPlayer;
import game.build.util.Logger;
import game.build.util.Utils;

import java.io.File;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("serial")
public class NormalMode extends BaseGame
{
	private int index = 0;
	private final BeatFile beatMap;
	
	public NormalMode(File file)
	{
		super("play_normal");
		Logger.info("Normal mode activated.");
		this.beatMap = BeatFile.fromFile(file);
		SongPlayer.playSong(this.beatMap.songName());
	}

	@Override
	public void update()
	{
		long curr = System.currentTimeMillis();  
		if(index < this.beatMap.arrSize() && curr - this.lastTime >= this.beatMap.get(index)-8) //8ms offset seems to fix input lag
		{
			index++;
			this.genRandomProjectiles(random.nextInt(3) + 1);
			if(Utils.percentChance(1))
			{
				vel+=random.nextDouble();
			}
			this.lastTime = curr;
			//Change depending on volume, etc.
		}
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
		this.repaint();
		this.checkForCollision();
	}

	@Override
	void onHit()
	{
		SongPlayer.playSoundEffect("hit");
		SongPlayer.playSong("loss");
		long survivedTime = System.currentTimeMillis() - this.startTime;
		for(Projectile p : this.projectiles)
		{
			p.kill();
		}
		this.update();
		int min = (int) TimeUnit.MILLISECONDS.toMinutes(survivedTime);
		int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(survivedTime) - TimeUnit.MINUTES.toSeconds(min));
		int ms = (int) (survivedTime % 1000);
		Main.setCurrentScreen(Screens.normalOver(beatMap.songName(),min, sec, ms));
	}
	
	public void gameWon()
	{
		SongPlayer.playSong("won");
		for(Projectile p : this.projectiles)
		{
			p.kill();
		}
		this.update();
		Main.setCurrentScreen(Screens.normalWon(beatMap.songName()));
	}
}
