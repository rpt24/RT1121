import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * Main class that drives the unit tests
 * @author ryanthornton
 *
 */
public class Main 
{
	public static void main(String args[]) throws CustomException
	{
		// run junit and track results
		Result result = JUnitCore.runClasses(JunitTest.class);
		
		// print failed tests
		for (Failure failure : result.getFailures())
		{
			System.out.println(failure.toString());
		}
		
		System.out.println(result.wasSuccessful());
	}
}