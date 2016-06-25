package game.build.util;

import static java.io.File.separator;

import java.awt.image.BufferedImage;
import java.awt.image.ColorModel;
import java.awt.image.WritableRaster;
import java.io.File;
import java.util.concurrent.ExecutionException;

import javax.imageio.ImageIO;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;

public class Resources
{
	private static final Cache<String, BufferedImage> imageCache = CacheBuilder.newBuilder().build();
	private static final BufferedImage def;
	private static final File images = new File("resources" + separator + "images");
	private static final File icons = new File("resources" + separator + "icons");
	
	static
	{
		def = null;
		images.mkdirs();
		icons.mkdirs();
	}
	
	/**
	 * Retrieves a BufferedImage of a given name from the cache.
	 * This assumes the file type is PNG.
	 * @param image - the image name
	 * @return the instance of the BufferedImage
	 */
	public static BufferedImage getImage(String image)
	{
		return getImage(image, ".png");
	}
	
	public static BufferedImage getImage(String image, String type)
	{
		File path = new File(images.getAbsolutePath() + separator + image + type);
		try
		{
			return imageCache.get(image, ()->ImageIO.read(path));
		}
		catch (ExecutionException e)
		{
			Logger.error("Failed to load image " + image);
			Logger.error("Resorting to default graphic.");
			Logger.trace(e);
			return def;
		}
	}
	
	/**
	 * Retrieves a BufferedImage of a given name from the cache.
	 * @param icon - the icon name
	 * @return the instance of the BufferedImage
	 */
	public static BufferedImage getIcon(String icon)
	{
		return getIcon(icon, ".png");
	}
	
	public static BufferedImage getIcon(String icon, String type)
	{
		File path = new File(icons.getAbsolutePath() + separator + icon + type);
		System.out.println(path.getAbsolutePath());
		try
		{
			return imageCache.get(icon, ()->ImageIO.read(path));
		}
		catch (ExecutionException e)
		{
			Logger.error("Failed to load icon " + icon);
			Logger.error("Resorting to default graphic.");
			Logger.trace(e);
			return def;
		}
	}
	
	public static BufferedImage deepCopy(BufferedImage image)
	{
		ColorModel model = image.getColorModel();
		boolean alpha = image.isAlphaPremultiplied();
		WritableRaster raster = image.copyData(null);
		return new BufferedImage(model, raster, alpha, null);
	}
	
}
