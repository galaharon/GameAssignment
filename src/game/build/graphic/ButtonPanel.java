package game.build.graphic;

import static game.build.util.Reference.BACK_BUTTON;
import static game.build.util.Reference.BUTTON;
import static game.build.util.Reference.CREATE_BUTTON;
import static game.build.util.Reference.ENDLESS_BUTTON;
import static game.build.util.Reference.ENDLESS_TRY_AGAIN;
import static game.build.util.Reference.EXIT_BUTTON;
import static game.build.util.Reference.INFO_BUTTON;
import static game.build.util.Reference.PLAY_BUTTON;
import static game.build.util.Reference.SONG_SELECT_BUTTON;
import game.build.main.Main;
import game.build.util.Resources;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Random;

@SuppressWarnings("serial")
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
			switch(this.name)
			{
			case EXIT_BUTTON:
				Main.exit(); return;
			case CREATE_BUTTON:
				Main.setCurrentScreen(Screens.levelCreation()); return;
			case INFO_BUTTON:
				Main.setCurrentScreen(Screens.infoScreen()); return;
			case PLAY_BUTTON:
				Main.setCurrentScreen(Screens.modeSelect()); return;
			case BACK_BUTTON:
				Main.setCurrentScreen(Screens.mainMenu()); return;
			case ENDLESS_BUTTON:
			case ENDLESS_TRY_AGAIN:
				Main.setCurrentScreen(Screens.endlessMode()); return;
			case SONG_SELECT_BUTTON:
				Main.setCurrentScreen(Screens.songSelect()); return;
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e){}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		Random random = new Random();
		Main.changeCursorColour(random.nextInt(256), random.nextInt(256), random.nextInt(256));
		this.image = Resources.getImage("buttonHighlight_" + this.name);
		this.repaint();
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		Main.defaultCustomCursor();
		this.image = Resources.getImage("button_" + this.name);
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
