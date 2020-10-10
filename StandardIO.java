package io;

import java.util.Scanner;

public class StandardIO {

	private static Scanner scanner = new Scanner(System.in);
	private static int textCrawlSpeed = 5;

	
	
	
	// get input //
 	public static String promptInput() {
		print("Your Input: ");
		try{
			return scanner.next();
		}catch(Exception e) {
			return "";
		}
	}
	public static int promptInt() {
		print("Your Input: ");
		try {
			return Integer.parseInt(scanner.next());
		}catch(Exception e) {
			return -2;
		}
	}
	public static int getInt() {
		try {
			return Integer.parseInt(scanner.next());
		}catch(Exception e) {
			return -2;
		}
	}
	public static char promptChar() {
		print("Your Input: ");
		try {
			return scanner.next().charAt(0);
		}catch(Exception e) {
			return 0;
		}
	}
	
	
	

	// standard output //
	public static void println(String message) {
		//prints w/ a text crawl
		for(String line : message.split("\n", -1)){
			for(String character : line.split("")) {
				System.out.print(character);
				delay(textCrawlSpeed);
			}
			System.out.println();
		}
	}
	public static void print(String message) {
		System.out.print(message);
	}
	public static void printDivider() {
		println("--------------------\n");
	}
	public static void printLineBreak() {
		println("");
	}
	public static void delay() {	
		//println("Press [enter] to continue.");
		//Scanner reader = new Scanner(System.in);
		//reader.nextLine();

		try {
			Thread.sleep(100);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void delay(int time) {	
		try {
			Thread.sleep(time);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void printInputNotRecognized() {
		println("Input not recognized.\n");
	}
	public static void printEscCharReminder() {
		println("(Press -1 to go back.)\n");
	}

	
	
	
	
	// getters & setters
	public static int getCrawlSpeed() {
		return textCrawlSpeed;
	}
	/**
	 * sets the new crawl speed
	 * new value must not be negative, must be under a certain threshold
	 * 
	 * @param crawlSpeedMillis
	 */
	public static void setCrawlSpeed(int crawlSpeedMillis) {
		if(crawlSpeedMillis >= 0 && crawlSpeedMillis <= 50) {
			textCrawlSpeed = crawlSpeedMillis;			
		}
	}
	
}
