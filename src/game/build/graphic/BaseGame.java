package game.build.graphic;

import game.build.util.Utils;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.List;
import java.util.Random;

import javax.swing.JComponent;

import com.google.common.collect.Lists;

@SuppressWarnings("serial")
public abstract class BaseGame extends MenuPane
{
	List<Projectile> projectiles = Lists.newLinkedList();
	long lastTime = System.currentTimeMillis();
	final long startTime = lastTime;
	double vel = 1.7D;
	Random random = new Random();
	
	public BaseGame(String s)
	{
		super(s);
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
	
	public abstract void update();

	final void checkForCollision()
	{
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		int mouseX = mouse.x - this.getLocationOnScreen().x;
		int mouseY = mouse.y - this.getLocationOnScreen().y;
		
		for(Projectile p : projectiles)
		{
			if(p.isInBounds(mouseX, mouseY))
			{
				this.onHit();
				return;
			}
		}
	}

	void genRandomProjectiles(int amount)
	{
		for(int i = 0; i < amount; i++)
		{
			this.genRandomProjectile();
		}
	}
	
	void genRandomProjectile()
	{
		Point mouse = MouseInfo.getPointerInfo().getLocation();
		Point randPoint = Utils.randomPointOnBoundary(random, 32);
		//int time = 10000;
		Projectile p = new Projectile(randPoint, 10000, new Point(mouse.x - this.getLocationOnScreen().x, mouse.y - this.getLocationOnScreen().y), vel,0);
		
		this.addLayer(p);
	}
	
	abstract void onHit();
}
