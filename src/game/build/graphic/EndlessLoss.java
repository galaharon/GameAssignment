package game.build.graphic;

import static java.awt.Color.PINK;
import static java.awt.Font.PLAIN;
import game.build.util.Logger;
import game.build.util.Resources;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

@SuppressWarnings("serial")
public class EndlessLoss extends MenuPane
{
	private static final Font TITLE_FONT = Resources.getCustomFont().deriveFont(PLAIN, 110);
	private static final Font TIME_FONT = Resources.getCustomFont().deriveFont(PLAIN, 80);
	private static final Color WEAK_RED = new Color(255,30,30);
	
	private final String survivedText;
	
	protected EndlessLoss(String text)
	{
		super("endlessLoss");
		Logger.info("Endless mode finished, survived for " + text);
		this.survivedText = text;
	}
	
	@Override
	protected void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setColor(PINK);
		g2d.setFont(TITLE_FONT);
		g2d.drawString("You Survived For", 170, 180);
		g2d.setColor(WEAK_RED);
		g2d.setFont(TIME_FONT);
		g2d.drawString(this.survivedText, centreTextOffset(this.survivedText.length()), 320);
		g2d.dispose();
	}
	
	private static int centreTextOffset(int l)
	{
		switch(l)
		{
		case 10: return 428;
		case 11: return 392;
		case 18: return 285;
		case 19: return 245;
		default:
			return (int) Math.round(604.746D - 18.4308D*l); // linear interpolation (who survives for 10+ minutes anyway?)
		}
	}
}
