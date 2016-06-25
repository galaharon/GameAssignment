package game.build.graphic;

import game.build.main.Main;
import game.build.main.SongPlayer;
import game.build.util.Logger;
import game.build.util.Utils;

import java.util.Iterator;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("serial")
public class EndlessMode extends BaseGame
{
	private long diff = 1500;
	
	public EndlessMode()
	{
		super("endless");
		Logger.info("Endless mode activated.");
	}
	
	public void update()
	{
		long curr = System.currentTimeMillis();
		if(curr - this.lastTime >= this.diff)
		{
			Random r = new Random();
			this.genRandomProjectiles(r.nextInt(5) + 1);
			if(Utils.percentChance(diff/40D))
			{
				diff = Math.max(diff - 10 - r.nextInt(100), 200);
			}
			if(Utils.percentChance(1))
			{
				vel+=r.nextDouble();
			}
			this.lastTime = curr;
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
	public void onHit()
	{
		SongPlayer.playSoundEffect("hit");
		SongPlayer.playSoundEffect("loss");
		long survivedTime = System.currentTimeMillis() - this.startTime;
		for(Projectile p : this.projectiles)
		{
			p.kill();
		}
		this.update();
		int min = (int) TimeUnit.MILLISECONDS.toMinutes(survivedTime);
		int sec = (int) (TimeUnit.MILLISECONDS.toSeconds(survivedTime) - TimeUnit.MINUTES.toSeconds(min));
		int ms = (int) (survivedTime % 1000);
		Main.setCurrentScreen(Screens.endlessOver(min, sec, ms));
	}
}
