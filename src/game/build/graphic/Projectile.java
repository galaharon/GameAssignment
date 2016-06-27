package game.build.graphic;

import static game.build.util.Reference.GAME_DIMENSION;
import game.build.util.Resources;

import java.awt.Point;
import java.util.Random;

@SuppressWarnings("serial")
public class Projectile extends ImagePanel
{
	private boolean isDead;
	private long lastTick = System.currentTimeMillis();
	private int msRemaining;
	private final double xSpeed,ySpeed;
	private double velocity;
	public final int worth;
	private static final Random r = new Random();
	
	public Projectile(Point gen, int time, Point target, double vel, int points)
	{
		super(Resources.getIcon("projectile" + r.nextInt(8)), gen.x, gen.y);
		this.msRemaining = time;
		double xS = (target.getX() - gen.x);
		double yS = (target.getY() - gen.y);
		this.velocity = vel;
		double factor = this.velocity/Math.sqrt(xS * xS + yS*yS);
		this.xSpeed = xS*factor;
		this.ySpeed = yS*factor;
		this.worth = points;
	}
	
	public void update()
	{
		if(this.xOffset <= 0 || this.xOffset >= GAME_DIMENSION.width
				|| this.yOffset <= 0 || this.yOffset >= GAME_DIMENSION.height)
		{
			this.kill();
		}
		if(!this.isDead)
		{
			long time = System.currentTimeMillis();
			long diff = time - this.lastTick;
			this.lastTick = time;
			this.msRemaining = (int) (this.msRemaining - diff);
			if(this.msRemaining <= 0)
			{
				this.kill();
				return;
			}
			else
			{
				this.xOffset += xSpeed;
				this.yOffset += ySpeed;
				this.repaint();
			}
		}
	}
	
	public boolean isDead()
	{
		return this.isDead;
	}
	
	protected void kill()
	{
		this.isDead = true;
	}

	public boolean isInBounds(int mouseX, int mouseY)
	{
		return this.xOffset <= mouseX && mouseX <= this.xOffset + this.getSize().width
				&& this.yOffset <= mouseY && mouseY <= this.yOffset + this.getSize().height;
	}

}
