package Invest;

import java.util.Scanner;

public class InputInvestment {
	public static void AmountOfInvest() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("How much you want to invest: ");
		int investAmount = scanner.nextInt();
		System.out.println("What company you are investing: ");
		String company = scanner.nextLine();

	}

}
