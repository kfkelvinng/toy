import java.util.HashSet;
import java.util.Scanner;

/** 
Print the decimal place that before the digit beginning to repeat itself
11/7 = 1.5714285 7143
*/
public class CyclingDecimalDivision {
  public static void main(String[] args) {
    Scanner sc = new Scanner(System.in);
    int a = sc.nextInt(), b = sc.nextInt(), i=0;
    for (HashSet<Integer> already = new HashSet<Integer>(b); already.add(a*10/b); a = a * 10 - (a*10/b)*b, i++) {
      System.out.print(a*10/b);
    }
    System.out.print("\n" + i + "\n");
  }
  
}

