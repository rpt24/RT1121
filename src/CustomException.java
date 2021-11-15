/**
 * Custom exception class for throwing exceptions
 * where I'd like for program constraints.
 * @author ryanthornton
 *
 */
public class CustomException extends Exception
{

	/**
	 * needed this by default
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * Throws an exception with a custom message
	 * @param message
	 */
	public CustomException(String message)
	{
		super(message);
	}
}
