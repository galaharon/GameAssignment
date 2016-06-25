package game.build.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Utils
{
	private static final DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("hh:mm:ss");
	/**
	 * Used to rethrow checked exceptions without wrapping them in an unchecked exception.
	 * @param e - the exception to throw
	 */
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
}
