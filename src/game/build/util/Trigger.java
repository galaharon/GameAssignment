package game.build.util;
/**
 * A small utility class to enable one time triggers.
 * Note: This class is not thread safe.
 */
public class Trigger
{
	/** The internal boolean - should never be set to false except by default. */
	private boolean trigger = false;
	
	
	/**
	 * A function to return the value of the trigger.
	 * @return false by default, true once the trigger has been set.
	 */
	public boolean isTriggered()
	{
		return this.trigger;
	}
	
	/**
	 * Sets the trigger to true. Cannot be undone.
	 */
	public void setTrigger()
	{
		this.trigger = true;
	}
	
}
