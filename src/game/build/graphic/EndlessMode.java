package game.build.graphic;

import game.build.main.Main;
import game.build.main.SongPlayer;
import game.build.util.Logger;
import game.build.util.Utils;

import java.util.Iterator;
import java.util.concurrent.TimeUnit;

@SuppressWarnings("serial")
public class EndlessMode extends BaseGame
{
	private long diff = 1500; 
	public static int type = -1;
	
	public EndlessMode(int i)
	{
		super("endless");
		type = i;
		Logger.info("Endless mode activated.");
	}
	
	@Override
	public void tick()
	{
		this.update();
		this.validate();
		this.repaint();
	}
	
	public void update()
	{
		long curr = System.currentTimeMillis();
		if(curr - this.lastTime >= this.diff)
		{
			this.genRandomProjectiles(random.nextInt(5) + 1);
			if(Utils.percentChance(diff/37.5D))
			{
				diff = Math.max(diff - 10 - random.nextInt(100), 200); //spawn difference in time increase
			}
			if(Utils.percentChance(2.5D)) //velocity chance 1% per spawn to increase 0-1
			{
				vel+=random.nextDouble()/2D;
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
		this.checkForCollision();
	}
	
	@Override
	public void onHit()
	{
		//SongPlayer.playSoundEffect("hit"); Removed for now
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
		Main.setCurrentScreen(Screens.endlessOver(min, sec, ms));
	}
}
