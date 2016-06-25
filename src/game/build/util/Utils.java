package game.build.util;

import static game.build.util.Reference.GAME_DIMENSION;

import java.awt.Point;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class Utils
{
	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("hh:mm:ss");
	/**
	 * Used to rethrow checked exceptions without wrapping them in an unchecked exception.
	 * @param e - the exception to throw
	 */
	@SuppressWarnings("unchecked")
	public static <T extends Throwable> RuntimeException throwUnchecked(Throwable e) throws T
	{
		throw (T) e;
	}
	
	/**
	 * @return the time in HH:MM:SS format.
	 */
	public static final String getTime()
	{
		return LocalDateTime.now().format(dateFormat);
	}

	public static Point randomPointOnBoundary(Random r, int fuzziness) //Screen Boundary Random Point
	{
		int totalLength = 2*(GAME_DIMENSION.width + GAME_DIMENSION.height);
		int pos = r.nextInt(totalLength);
		int x, y;
		if(pos < GAME_DIMENSION.height)
		{
			x = 0;
			y = pos;
		}
		else if(GAME_DIMENSION.height <= pos && pos < GAME_DIMENSION.height+GAME_DIMENSION.width)
		{
			y = 0;
			x = pos - GAME_DIMENSION.height;
		}
		else if(GAME_DIMENSION.height+GAME_DIMENSION.width <= pos && pos < 2*GAME_DIMENSION.height+GAME_DIMENSION.width)
		{
			y = pos - GAME_DIMENSION.height-GAME_DIMENSION.width;
			x = GAME_DIMENSION.width;
		}
		else
		{
			y = GAME_DIMENSION.height;
			x = pos - 2*GAME_DIMENSION.height-GAME_DIMENSION.width;
		}
		int xSgn = (int) Math.signum(GAME_DIMENSION.width/2 - x);
		int ySgn = (int) Math.signum(GAME_DIMENSION.height/2 - y);
		
		x += xSgn*(10 + r.nextInt(fuzziness));		
		y += ySgn*(10 + r.nextInt(fuzziness));
		return new Point(x,y);
	}

	public static boolean percentChance(double d)
	{
		return new Random().nextDouble() < d/100D;
	}
}
