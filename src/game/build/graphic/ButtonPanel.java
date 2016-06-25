package game.build.graphic;

import game.build.main.Main;
import game.build.util.Resources;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Random;

public class ButtonPanel extends ImagePanel implements MouseListener
{
	public ButtonPanel(int x, int y)
	{
		super(Resources.getImage("button"), x, y);
		this.addMouseListener(this);
	}
	
	@Override
	public void mouseClicked(MouseEvent e){}

	@Override
	public void mousePressed(MouseEvent e){}

	@Override
	public void mouseReleased(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		Random random = new Random();
		Main.changeCursorColour(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		this.image = Resources.getImage("buttonHighlight");
		this.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		Main.defaultCustomCursor();
		this.image = Resources.getImage("button");
		this.repaint();
	}
}
