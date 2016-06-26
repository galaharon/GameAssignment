package game.build.graphic;

import game.build.util.Reference;
import game.build.util.Resources;

import java.awt.Point;
import java.util.Random;

@SuppressWarnings("serial")
public class Projectile extends ImagePanel
{
	private boolean isDead;
	private long lastTick = System.currentTimeMillis();
	private int msRemaining;
	private double xSpeed,ySpeed;
	private double velocity;
	public final int worth;
	private static final Random r = new Random();
	
	public Projectile(Point gen, int time, Point target, double vel, int points)
	{
		super(Resources.getIcon("projectile" + r.nextInt(8)), gen.x, gen.y);
		this.msRemaining = time;
		this.xSpeed = (target.getX() - gen.x);
		this.ySpeed = (target.getY() - gen.y);
		this.velocity = vel;
		double factor = this.velocity/Math.sqrt(xSpeed * xSpeed + ySpeed*ySpeed);
		this.xSpeed *= factor;
		this.ySpeed *= factor;
		this.worth = points;
	}
	
	public void update()
	{
		if(Math.abs(this.xOffset - Reference.GAME_DIMENSION.width/2) >= Reference.GAME_DIMENSION.width/2
			||Math.abs(this.yOffset - Reference.GAME_DIMENSION.height/2) >= Reference.GAME_DIMENSION.height/2)
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
