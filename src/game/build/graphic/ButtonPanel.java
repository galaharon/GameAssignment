package game.build.graphic;

import static game.build.util.Reference.BUTTON;
import game.build.main.Main;
import game.build.util.Reference;
import game.build.util.Resources;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

public class ButtonPanel extends ImagePanel implements MouseListener, MouseMotionListener
{
	private final String name;
	private boolean mouseEntered = false;
	
	public ButtonPanel(int x, int y, String imageName)
	{
		super(Resources.getImage("button_" + imageName), x, y);
		this.name = imageName;
	}
	
	@Override
	public void mouseClicked(MouseEvent e){}

	@Override
	public void mousePressed(MouseEvent e)
	{
		if(this.isInBounds(e))
		{
			System.out.println(name);
			if(this.name.equals("exit"))
			{
				Main.exit();
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		System.out.println(this.name + " entered");
		Random random = new Random();
		Main.changeCursorColour(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		this.image = Resources.getImage("buttonHighlight_" + this.name);
		this.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		System.out.println(this.name + " exited");
		Main.defaultCustomCursor();
		this.image = Resources.getImage("button");
		this.repaint();
	}

	@Override
	public void mouseDragged(MouseEvent e){}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		
		if(!mouseEntered)
		{
			if(this.isInBounds(e))
			{
				mouseEntered = true;
				this.mouseEntered(e);
			}
		}
		else
		{
			if(!this.isInBounds(e))
			{
				mouseEntered = false;
				this.mouseExited(e);
			}
		}
	}
	
	private boolean isInBounds(MouseEvent e)
	{
		return this.xOffset <= e.getX() && e.getX() <= this.xOffset + BUTTON.width
				&& this.yOffset < e.getY() && e.getY() <= this.yOffset + BUTTON.height;
	}
}
