package game.build.graphic;

import game.build.util.Resources;

@SuppressWarnings("serial")
public class Projectile extends ImagePanel
{
	private boolean isDead;
	private long lastTick = System.currentTimeMillis();
	private int msRemaining;
	private double velocity; //pixels/ms
	private final double direction;
	public final int worth;
	
	public Projectile(int x, int y, int time, double vel, double dir, int points)
	{
		super(Resources.getIcon("projectile"), x, y);
		this.msRemaining = time;
		this.velocity = vel;
		this.direction = dir;
		this.worth = points;
	}
	
	public void update()
	{
		if(!this.isDead)
		{
			long time = System.currentTimeMillis();
			long diff = time - this.lastTick;
			this.lastTick = time;
			this.msRemaining = (int) (this.msRemaining - diff);
			if(this.msRemaining <= 0)
			{
				this.isDead = true;
				return;
			}
			else
			{
				this.xOffset = (int)Math.floor(this.xOffset + this.velocity * Math.cos(this.direction) * diff);
				this.yOffset = (int)Math.floor(this.yOffset + this.velocity * Math.sin(this.direction) * diff);
				this.repaint();
			}
		}
	}
	
	public boolean isDead()
	{
		return this.isDead;
	}

}
