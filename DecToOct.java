package expriment;
import java.io.Console;
import java.util.*;

public class DecToOct {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner in = new Scanner(System.in);
		System.out.println("Please input a number");
		int num = in.nextInt();
		String result ="";
		String n = num.valueOf();
		if (num <= 8){
			System.out.println(num);
		}
		else {
			while(num / 8 != 0){
				result+=num % 8;
				num = num / 8;
			}
			result+=num;
			StringBuffer stringBuffer = new StringBuffer (result);
	        System.out.print(stringBuffer.reverse());
		}
	}

}
