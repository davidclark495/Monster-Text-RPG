package io;

import java.util.Scanner;

public class StandardIO {

	private Scanner scanner;

	public StandardIO() {
		scanner = new Scanner(System.in);
	}

	// get input //
 	public String promptInput() {
		System.out.print("Your Input: ");
		try{
			return scanner.next();
		}catch(Exception e) {
			return "";
		}
	}
	public int promptInt() {
		System.out.print("Your Input: ");
		try {
			return scanner.nextInt();
		}catch(Exception e) {
			return -1;
		}
	}
	public char promptChar() {
		System.out.print("Your Input: ");
		try {
			return scanner.next().charAt(0);
		}catch(Exception e) {
			return 0;
		}
	}

	// standard output //
	public void printDivider() {
		System.out.println("--------------------\n");
	}
	public void printLineBreak() {
		System.out.println();
	}
	public void delay() {	
		//System.out.println("Press [enter] to continue.");
		//Scanner reader = new Scanner(System.in);
		//reader.nextLine();

		try {
			Thread.sleep(1000);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public void printInputNotRecognized() {
		System.out.println("Input not recognized.\n");
	}
	public void printEscCharReminder() {
		System.out.println("(Press -1 to go back.)\n");
	}

}
