package game.build.util;

import static game.build.util.Utils.getTime;
import static game.build.util.Utils.throwUnchecked;
import static java.io.File.separator;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Logger
{
	private static final File debugFile = new File("logs" + separator + "debug-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh_mm_ss")) + ".txt");
	private static final File generalFile = new File("logs" +separator+ "log-" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-mm-dd hh_mm_ss")) + ".txt");
	private static boolean consoleMode = false; //Whether or not to log to console as well.
	
	static
	{
		try
		{
			generalFile.getParentFile().mkdirs();
			generalFile.createNewFile();// Creates log if it does not already exist.
			debugFile.createNewFile();
		}
		catch (IOException e)
		{
			System.err.println("Failed to create log files! Game exiting.");
			throwUnchecked(e);
		}
		File[] files = new File("logs").listFiles();
		if(files.length > 10)
		{
			File oldestLog = null, oldestDebug = null;
			for(File f : files)
			{
				
				if(f.getName().startsWith("log"))
				{
					oldestLog = oldestLog == null ? f : f.lastModified() < oldestLog.lastModified() ? f : oldestLog;
				}
				else if(f.getName().startsWith("debug"))
				{
					oldestDebug = oldestDebug == null ? f : f.lastModified() < oldestDebug.lastModified() ? f : oldestDebug;
				}
			}
			trace("Log count exceeds 10, deleting oldest debug and log.");
			oldestLog.delete();
			oldestDebug.delete();
		}
	}
	
	public static void enableConsoleMode()
	{
		consoleMode = true;
	}
	
	/**
	 * Logs debug messages - non important
	 * @param s - the message to log
	 */
	public static void trace(String s) // Trace level should only be written to debug file!
	{
		try(FileWriter d = new FileWriter(debugFile, true);
				BufferedWriter dw = new BufferedWriter(d))
		{
			String m = getTime()+ " [TRACE] " + s;
			dw.write(m);
			dw.newLine();
			if(consoleMode)
			{
				System.out.println(m);
			}
			
		}
		catch (IOException e)
		{
			System.err.println("Failed to write to debug file! Game exiting.");
			throwUnchecked(e);
		}
	}
	
	/**
	 * Logs unimportant exceptions
	 * @param s - the exception to log
	 */
	public static void trace(Exception e) // Trace level should only be written to debug file!
	{
		try(FileWriter f = new FileWriter(debugFile, true);
				BufferedWriter b = new BufferedWriter(f);
				PrintWriter d = new PrintWriter(b))
		{
			d.write(getTime() + "[EXCEPTION]");
			e.printStackTrace(d);
			b.newLine();
			if(consoleMode)
			{
				e.printStackTrace();
			}
			
		}
		catch (IOException f)
		{
			System.err.println("Failed to write to debug file! Game exiting.");
			throwUnchecked(f);
		}
	}
	
	/**
	 * Logs info messages
	 * @param s - the message to log
	 */
	public static void info(String s)
	{
		log("[INFO]", s);
	}
	
	/**
	 * Logs non-fatal error messages - error is recoverable from.
	 * @param s - the message to log
	 */
	public static void error(String s)
	{
		log("[ERROR]", s);
	}
	
	/**
	 * Logs fatal error messages - only use when error is non-recoverable from
	 * @param s - the message to log
	 */
	public static void fatal(String s)
	{
		log("[FATAL]", s);
	}
	
	/*
	 * Actual message logger
	 */
	private static void log(String prefix, String message)
	{
		try(FileWriter f = new FileWriter(generalFile, true);
				FileWriter d = new FileWriter(debugFile, true);
				BufferedWriter fw = new BufferedWriter(f);
				BufferedWriter dw = new BufferedWriter(d))
		{
			String m = getTime()+ " " + prefix + " " + message;
			fw.write(m);
			fw.newLine();
			dw.write(m);
			dw.newLine();
			if(consoleMode)
			{
				System.out.println(m);
			}
			
		}
		catch (IOException e)
		{
			System.err.println("Failed to write to log file! Game exiting.");
			throwUnchecked(e);
		}
	}
	
	
}
