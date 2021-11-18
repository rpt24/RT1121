import org.junit.Test;

/**
 * class for unit testing. tests the 6 cases provided for me to test.
 * @author ryanthornton
 *
 */
public class JunitTest 
{
	@Test
	public void testOrder1() throws CustomException
	{
		Order myOrder = new Order("JAKR", 101, 5, "2015-09-03");
		myOrder.checkout();
	}
	
	@Test
	public void testOrder2() throws CustomException
	{
		Order myOrder = new Order("LADW", 10, 3, "2020-07-02");
		myOrder.checkout();
	}
	
	@Test
	public void testOrder3() throws CustomException
	{
		Order myOrder = new Order("CHNS", 25, 5, "2015-07-02");
		myOrder.checkout();
	}
	
	@Test
	public void testOrder4() throws CustomException
	{
		Order myOrder = new Order("JAKD", 0, 6, "2015-09-03");
		myOrder.checkout();
	}
	
	@Test
	public void testOrder5() throws CustomException
	{
		Order myOrder = new Order("JAKR", 0, 9, "2015-07-02");
		myOrder.checkout();
	}
	
	@Test
	public void testOrder6() throws CustomException
	{
		Order myOrder = new Order("LADW", 50, 4, "2020-07-02");
		myOrder.checkout();
	}
}
