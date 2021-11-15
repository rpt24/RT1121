
public class Order 
{
	enum ToolType
	{
		LADDER, CHAINSAW, JACKHAMMER;
	}
	
	String code[] = {"LADW", "CHNS", "JAKR", "JAKD"};
	String brand[] = {"Werner", "Stihl", "Ridgid", "Dewalt"};
	ToolType types[] = {ToolType.LADDER, ToolType.CHAINSAW, 
			ToolType.JACKHAMMER, ToolType.JACKHAMMER};
	
	int discount, rentalDayCount;
	
	Tool orderTool;
	
	Order(String inputCode, int inDiscount, int inRentalDayCount) throws CustomException
	{
		discount = inDiscount;
		checkDiscount();
		
		rentalDayCount = inRentalDayCount;
		checkRentalDayCount();
		
		int key = checkToolCode(inputCode);
		orderTool = new Tool(inputCode, brand[key], types[key]);
	}
	
	void checkout()
	{
		
	}
	
	void checkDiscount() throws CustomException
	{
		boolean valid = false;
		
		if (discount >= 0 && discount <= 100)
		{
			valid = true;
		}
		
		if (!valid)
		{
			throw new CustomException("Invalid discount percent");
		}
	}
	
	void checkRentalDayCount() throws CustomException
	{
		boolean valid = false;
		
		if (rentalDayCount >= 1)
		{
			valid = true;
		}
		
		if (!valid)
		{
			throw new CustomException("Invalid rental day amount");
		}
	}
	
	int checkToolCode(String inputCode) throws CustomException
	{
		boolean toolCodeValid = false;
		int key = 0; // use this to get the array positions, the positions map 1-1
		
		// loop the code array to ensure a valid code was input
		for (; key < code.length; key++)
		{
			if (code[key].equals(inputCode))
			{
				toolCodeValid = true;
				break;
			}
		}
		
		// if the code is not valid throw a custom exception
		if (!toolCodeValid)
		{
			throw new CustomException("Invalid tool code");
		}
		
		return key;
	}
	
	public class Tool
	{
		String toolBrand;
		String toolCode;
		double  toolDailyCharge;
		boolean	weekday, weekend, holiday;
		
		Tool(String code, String brand, ToolType type)
		{
			toolCode = code;
			toolBrand = brand;
		}
		
		void setDailyChargeAndChargeDays(ToolType type)
		{
			switch(type)
			{
			case LADDER:
				toolDailyCharge = 1.99;
				weekday = true;
				weekend = true;
				holiday = false;
				break;
			case CHAINSAW:
				toolDailyCharge = 1.49;
				weekday = true;
				weekend = false;
				holiday = true;
				break;
			case JACKHAMMER:
				toolDailyCharge = 2.99;
				weekday = true;
				weekend = false;
				holiday = false;
				break;
			default:
				break;
			}
		}
	}
}
