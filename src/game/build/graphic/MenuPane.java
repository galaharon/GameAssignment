package game.build.graphic;

import game.build.util.Resources;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

public class MenuPane extends JLayeredPane
{
	private final BufferedImage bg = Resources.getImage("main_background");
	
	public MenuPane()
	{
		this.setLayout(new GridBagLayout());
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
}
