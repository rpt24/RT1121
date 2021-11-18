import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
/**
 * Order class that will contain the information for fulfilling an order.
 * Contains the nested class for Tool.
 * @author ryanthornton
 *
 */
public class Order 
{
	// enum for the ToolType
	enum ToolType
	{
		LADDER, CHAINSAW, JACKHAMMER;
	}
	
	/* Order class variables, each var in an array should map 1-1
	 * which allows for using the tool code to make a key that will
	 * be used to get information from each spot in the arrays.
	 */
	String code[] = {"LADW", "CHNS", "JAKR", "JAKD"};
	String brand[] = {"Werner", "Stihl", "Ridgid", "Dewalt"};
	ToolType types[] = {ToolType.LADDER, ToolType.CHAINSAW, 
			ToolType.JACKHAMMER, ToolType.JACKHAMMER};
	
	int discount, rentalDayCount; // class variables for discount and rental day amount
	String checkoutDate; // the starting date of the order
	String returnDate; // string of date to return
	
	
	
	// An object of Tool will be a part of each order
	Tool orderTool;
	
	/**
	 * Order constructor. This checks to make sure the variables are valid
	 * before continuing and will throw an exception if they don't meet the 
	 * proper criteria.
	 * @param inputCode [in] the tool code
	 * @param inDiscount [in] the discount 
	 * @param inRentalDayCount [in] the amount of rental days
	 * @param startDate [in] the starting date of the rental
	 * @throws CustomException
	 */
	Order(String inputCode, int inDiscount, int inRentalDayCount, 
			String startDate) throws CustomException
	{
		// set the discount and check it, throws exception
		discount = inDiscount;
		checkDiscount();
		
		// set the rental day amount and check it, throws exception
		rentalDayCount = inRentalDayCount;
		checkRentalDayCount();
		
		// checks the tool code for validity then sets a key for use with other arrays.
		int key = checkToolCode(inputCode);
		orderTool = new Tool(inputCode, brand[key], types[key]);
		checkoutDate = startDate;
	}
	
	/**
	 * Checkout method figures out the days to charge for and then 
	 * prints the final receipt.
	 */
	void checkout()
	{
		int days = getChargeableDays(); // get days we charge
		// math for the different costs
		double initialCost = days * orderTool.toolDailyCharge;
		double discountAmount = (initialCost*discount)/100;
		double finalCost = initialCost - discountAmount;
		
		// print the information
		System.out.println("###################################");
		System.out.format("Tool Code: %s\n", orderTool.toolCode);
		System.out.format("Tool Type: %s\n", orderTool.toolTypeToString());
		System.out.format("Tool Brand: %s\n", orderTool.toolBrand);
		System.out.format("Rental Days: %d\n", rentalDayCount);
		System.out.format("Check out Date: %s\n", checkoutDate);
		System.out.format("Due Date: %s\n", returnDate);
		System.out.format("Daily Rental Charge: $%.2f\n", orderTool.toolDailyCharge);
		System.out.format("Charge Days: %d\n", days);
		System.out.format("Pre-discount Charge: $%.2f\n", initialCost);
		System.out.format("Discount Percent: %d\n", discount);
		System.out.format("Discount Amount: $%.2f\n", discountAmount);
		System.out.format("Final Charge: $%.2f\n", finalCost);
		System.out.println("###################################\n\n");
		
	}
	
	/**
	 * Get the chargeable days for the tool
	 * @return the amount of chargeable days
	 */
	int getChargeableDays()
	{
		int chargeableDays = 0;
		// want to track the amount of each day we charge for total chargeable days
		int weekdays = 0, holidays = 0, weekendDays = 0;
		// make a start and end date
		LocalDate startDate = LocalDate.parse(checkoutDate);
		LocalDate endDate = startDate.plusDays(rentalDayCount);
		
		// go ahead and save these for the printout
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/YY");
		returnDate = formatter.format(endDate);
		checkoutDate = formatter.format(startDate);
		
		LocalDate tempDate = startDate.plusDays(1); // temporary date for iterating that is the day after checkout
		while (tempDate.compareTo(endDate.plusDays(1)) < 0)
		{
			// check for a weekend day
			if (tempDate.getDayOfWeek() == DayOfWeek.SATURDAY ||
					tempDate.getDayOfWeek() == DayOfWeek.SUNDAY)
			{
				// if the tool is chargeable on the weekend increment weekend counter
				if (orderTool.weekend)
				{
					weekendDays++;
				}
				// check for the fourth if it's on a weekend
				if (checkForIndependenceDay(tempDate))
				{
					/*
					 * if the fourth is on a weekend, we take away a charged weekday
					 * and add the holiday.
					 */
					weekdays--; // the fourth takes place of a weekday so decrement
					// if the tool charges holidays increment the holiday count
					if (orderTool.holiday)
					{
						holidays++;
					}
				}
			}
			else
			{
				// if not a weekend and tool charges weekdays increment weekday count
				if (orderTool.weekday)
				{
					weekdays++;	
				}
				// get the day of week and check for labor day
				DayOfWeek day = tempDate.getDayOfWeek();
				if (checkForLaborDay(tempDate, day))
				{
					// if it's labor day decrement the weekday count since a holiday takes place
					weekdays--;
					// if the tool charges holidays increment holiday count
					if (orderTool.holiday)
					{
						holidays++;
					}
				}
			}
			tempDate = tempDate.plusDays(1); // add a day to iterate
		}
		
		// ensure we don't add negative numbers from the count
		// pardon the one-liner formatting to clean up space.
		if (weekdays < 0) { weekdays = 0; }
		if (holidays < 0) { holidays = 0; }
		if (weekendDays < 0) { weekendDays = 0; }
		
		chargeableDays = weekdays + holidays + weekendDays; // add up to get final amount of chargeable days
		return chargeableDays;
	}
	
	/**
	 * Check for if the date is independence day.
	 * @param theDate [in] the date to check
	 * @return true if independence day false if not
	 */
	boolean checkForIndependenceDay(LocalDate theDate)
	{
		boolean isIndependenceDay = false;
		
		if ( (theDate.getMonth() == Month.JULY) && (theDate.getDayOfMonth() == 4) )
		{
			isIndependenceDay = true;
		}
		
		return isIndependenceDay;
	}
	
	/**
	 * Check for labor day
	 * @param theDate [in] the date to check
	 * @param day [in] the day of the week
	 * @return true if labor day false if not
	 */
	boolean checkForLaborDay(LocalDate theDate, DayOfWeek day)
	{
		boolean isLaborDay = false;
		
		// if it is the first monday of the month of september it is labor day
		if (theDate.getMonth() == Month.SEPTEMBER)
		{
			// if it's monday and occurs in the first 7 days of the month it's labor day
			if (day == DayOfWeek.MONDAY && (theDate.getDayOfMonth() < 8))
			{
				isLaborDay = true;
			}
		}
		
		return isLaborDay;
	}
	
	/**
	 * Checks to ensure the discount is between 0 and 100.
	 * @throws CustomException
	 */
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
	
	/**
	 * Checks to ensure the rental day amount is greater than 0.
	 * @throws CustomException
	 */
	void checkRentalDayCount() throws CustomException
	{
		boolean valid = false;
		
		if (rentalDayCount > 0)
		{
			valid = true;
		}
		
		if (!valid)
		{
			throw new CustomException("Invalid rental day amount");
		}
	}
	
	/**
	 * Checks the tool code to ensure it's valid.
	 * @param inputCode [in] the input tool code for the order
	 * @return key - the key for extracting information pre-known in the arrays.
	 * @throws CustomException
	 */
	int checkToolCode(String inputCode) throws CustomException
	{
		boolean toolCodeValid = false;
		int key = 0; // use this to get the array positions, the positions map 1-1
		
		// loop the "code" array to ensure a valid code was input
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
	
	/**
	 * Tool class is essential to every order and contains information 
	 * about the tools, such as the daily charge and when to charge.
	 * @author ryanthornton
	 *
	 */
	public class Tool
	{
		String toolBrand;	// the brand of the tool
		String toolCode;	// the tool code
		ToolType toolType; // the too type
		double  toolDailyCharge; // how much per day it is to rent the tool
		boolean	weekday, weekend, holiday; // booleans for if we charge on these days
		
		/**
		 * Tool constructor, sets the variables for this tool.
		 * @param code [in] the tool code
		 * @param brand [in] the brand of tool
		 * @param type [in] the enum for type of tool
		 */
		Tool(String code, String brand, ToolType type)
		{
			toolCode = code;
			toolBrand = brand;
			toolType = type;
			setDailyChargeAndChargeDays(type);
		}
		
		/**
		 * Sets the class variables for daily charge and what type of 
		 * days to charge on.
		 * @param type [in] the tool type
		 */
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
		
		/**
		 * Converts the tool type enum into a string for printing
		 * @return
		 */
		String toolTypeToString()
		{
			String strType = "";
			switch(toolType)
			{
			case LADDER:
				strType = "Ladder";
				break;
			case CHAINSAW:
				strType = "Chainsaw";
				break;
			case JACKHAMMER:
				strType = "Jackhammer";
				break;
			default:
					strType = "Unkown";
					break;
			}
			return strType;
		}
	}
}
