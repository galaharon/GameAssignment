package game.build.graphic;

import game.build.util.Logger;

import java.util.Iterator;
import java.util.List;

import javax.swing.JComponent;

import com.google.common.collect.Lists;

@SuppressWarnings("serial")
public class EndlessMode extends MenuPane
{
	private List<Projectile> projectiles = Lists.newLinkedList();
	public int score = 0;
	public int multiplier = 1;
	
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
		this.multiplier = Math.floorDiv(score, 1000);
		Iterator<Projectile> it = projectiles.iterator();
		while(it.hasNext())
		{
			Projectile p = it.next();
			if(p.isDead())
			{
				it.remove();
				this.remove(p);
				score += multiplier * p.worth;
				continue;
			}
			p.update();
		}
		this.repaint();
	}
	
}
