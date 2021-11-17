
public class Main 
{
	public static void main(String args[]) throws CustomException
	{
		int rentday = 5;
		int discount = 10;
		Order myOrder = new Order("CHNS", discount, rentday, "2015-07-02");
		myOrder.checkout();
		
		// todo - add discount and junit
	}
}
