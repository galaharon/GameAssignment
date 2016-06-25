package game.build.main;

import static game.build.util.Reference.GAME_DIMENSION;
import static game.build.util.Reference.TITLE;
import static game.build.util.Reference.VERSION;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;
import game.build.graphic.MenuPane;
import game.build.graphic.Screens;
import game.build.util.Logger;
import game.build.util.Resources;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.image.WritableRaster;

import javax.swing.JFrame;

public class Main
{
	private static final JFrame frame = new JFrame(TITLE + " " + VERSION);
	private static final BufferedImage cursorImage = Resources.getIcon("cursor", ".png");
	private static final Cursor defaultCustom = Toolkit.getDefaultToolkit().createCustomCursor(cursorImage, new Point(25, 25), "customCursor");
	private static MenuPane currentPane = null;
	
	public static void main(String[] args) throws Exception
	{
		Logger.enableConsoleMode();
		Logger.info("System loading!");
		Logger.info("Initialising frame...");
		initFrame();
		Logger.info("Frame initialised successfully.");
	}
	
	/**
	 * Initialises the JFrame. Should only be called once, from main.
	 */
	private static void initFrame()
	{
		frame.setCursor(defaultCustom);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.setPreferredSize(GAME_DIMENSION);
		frame.setResizable(false);
		frame.setLayout(new BorderLayout());

		Logger.info("Initialising Main Menu...");
		MenuPane menuPane = Screens.mainMenu();
		
		frame.add(menuPane);
		currentPane = menuPane;
		
		frame.pack();
		frame.setLocationRelativeTo(null); //places in centre of screen
		frame.setVisible(true); 
	}
	
	public static void changeCursorColour(int r, int g, int b)
	{
		BufferedImage copy = Resources.deepCopy(cursorImage);
		WritableRaster raster = copy.getRaster();
		for(int y = 0; y < copy.getHeight(); y++)
		{
			for(int x = 0; x < copy.getWidth(); x++)
			{
				int[] pixel = raster.getPixel(x, y, (int[])null);
				pixel[0] = r;
				pixel[1] = g;
				pixel[2] = b;
				raster.setPixel(x, y, pixel);
			}
		}
		frame.setCursor(Toolkit.getDefaultToolkit().createCustomCursor(copy, new Point(25,25), "customCursor" + Integer.toHexString((r << 16)|(g<<8)|b)));
	}
	
	public static void setCurrentScreen(MenuPane pane)
	{
		frame.remove(currentPane);
		currentPane = pane;
		frame.add(pane);
		frame.validate();
		frame.repaint();
		defaultCustomCursor();
	}
	
	public static void defaultCustomCursor()
	{
		frame.setCursor(defaultCustom);
	}
	
	public static void exit()
	{
		frame.dispose();
	}
}
