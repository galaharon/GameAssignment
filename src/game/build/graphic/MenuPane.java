package game.build.graphic;

import game.build.util.Resources;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

@SuppressWarnings("serial")
public class MenuPane extends JLayeredPane implements MouseMotionListener, MouseListener
{
	private final BufferedImage bg;
	
	public MenuPane(String name)
	{
		this.bg = Resources.getImage(name);
		this.setLayout(new GridBagLayout());
		this.addMouseMotionListener(this);
		this.addMouseListener(this);
	}
	
	public void addLayer(JComponent layer)
	{
		GridBagConstraints g = new GridBagConstraints();
		g.gridx = 0;
		g.gridy = 0;
		g.weightx = 1;
		g.weighty = 1;
		g.fill = GridBagConstraints.BOTH;
		this.add(layer, g);
	}
	
	@Override
	public Dimension getPreferredSize()
	{
		return new Dimension(bg.getWidth(), bg.getHeight());
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		int x = (this.getWidth() - bg.getWidth())/2;
		int y = (this.getHeight() - bg.getHeight())/2;
		g2d.drawImage(bg, x, y, this);
		g2d.dispose();
	}

	@Override
	public void mouseDragged(MouseEvent e)
	{
		synchronized(this.getTreeLock())
		{
			for(Component c : this.getComponents())
			{
				if(c instanceof ButtonPanel)
				{
					((ButtonPanel) c).mouseDragged(e);
				}
			}
		}
	}

	@Override
	public void mouseMoved(MouseEvent e)
	{
		synchronized(this.getTreeLock())
		{
			for(Component c : this.getComponents())
			{
				if(c instanceof ButtonPanel)
				{
					((ButtonPanel) c).mouseMoved(e);
				}
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		synchronized(this.getTreeLock())
		{
			for(Component c : this.getComponents())
			{
				if(c instanceof ButtonPanel)
				{
					((ButtonPanel) c).mouseClicked(e);
				}
			}
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		synchronized(this.getTreeLock())
		{
			for(Component c : this.getComponents())
			{
				if(c instanceof ButtonPanel)
				{
					((ButtonPanel) c).mousePressed(e);
				}
			}
		}
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		synchronized(this.getTreeLock())
		{
			for(Component c : this.getComponents())
			{
				if(c instanceof ButtonPanel)
				{
					((ButtonPanel) c).mouseReleased(e);
				}
			}
		}
	}

	@Override
	public void mouseEntered(MouseEvent e){}

	@Override
	public void mouseExited(MouseEvent e){}
}
