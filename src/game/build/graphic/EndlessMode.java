package game.build.graphic;

import game.build.main.Main;
import game.build.main.SongPlayer;
import game.build.util.Logger;
import game.build.util.Utils;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.swing.JComponent;

import com.google.common.collect.Lists;

@SuppressWarnings("serial")
public class EndlessMode extends MenuPane
{
	private List<Projectile> projectiles = Lists.newLinkedList();
	private long lastTime = System.currentTimeMillis();
	private long diff = 1500;
	private final long startTime = lastTime;
	private double vel = 1.7D;
	
	public EndlessMode()
	{
		super("endless");
		Logger.info("Endless mode activated.");
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
	
	private void checkForCollision()
	{
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		int mouseX = mouse.x - this.getLocationOnScreen().x;
		int mouseY = mouse.y - this.getLocationOnScreen().y;
		
		
		for(Projectile p : projectiles)
		{
			if(p.xOffset <= mouseX && mouseX <= p.xOffset + p.getSize().width
					&& p.yOffset <= mouseY && mouseY <= p.yOffset + p.getSize().height)
			{
				this.lostGame();
				return;
			}
		}
	}

	private void genRandomProjectiles(int amount)
	{
		for(int i = 0; i < amount; i++)
		{
			this.genRandomProjectile();
		}
	}
	
	private void genRandomProjectile()
	{
		Random r = new Random();
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point randPoint = Utils.randomPointOnBoundary(r, 32);
		int time = 10000;
		Projectile p = new Projectile(randPoint, time, new Point(mouse.x - this.getLocationOnScreen().x, mouse.y - this.getLocationOnScreen().y), vel,0);
		
		this.addLayer(p);
	}
	
	public void lostGame()
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
