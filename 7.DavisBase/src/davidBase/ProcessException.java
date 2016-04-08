package davidBase;

/**
 * An exception that should be used to signal problems with the
 * execution of the commands. 
 */
@SuppressWarnings("serial")
public class ProcessException extends Exception
{
	public ProcessException(String message) {
		super(message);
	}

	public ProcessException(String message, Throwable throwable) {
		super(message, throwable);
	}
}
