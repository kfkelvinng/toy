import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
Result:
year:2012
month:12
  S  M  T  W  T  F  S
                    1
  2  3  4  5  6  7  8
  9 10 11 12 13 14 15
 16 17 18 19 20 21 22
 23 24 25 26 27 28 29
 30 31
*/
public class MyCal {
	
	private static int scanInt(String prompt) {
		Scanner sc = new Scanner(System.in);
		while(true) {
			System.out.print(prompt + ":");
			try {
				return sc.skip("\\D*").nextInt();
			} catch (InputMismatchException e) {
				System.out.println("Invalid "+prompt+"!");
			}
		}
	}
	
	public static void main(String[] args) {
		int year=scanInt("year"), month=scanInt("month");
		
		Calendar c = GregorianCalendar.getInstance();
		c.setFirstDayOfWeek(Calendar.SUNDAY);
		
		System.out.println("  S  M  T  W  T  F  S");
		
		c.set(year, month-1, 1);
		for (int i = 1; i < c.get(Calendar.DAY_OF_WEEK); i++) System.out.print("   ");
		while(c.get(Calendar.MONTH) == month-1) {
			if (c.get(Calendar.DAY_OF_WEEK) == 1) System.out.println();
			System.out.print(String.format("%3d", c.get(Calendar.DATE)));
			c.add(Calendar.DATE, 1);
		}
		
	}
}

