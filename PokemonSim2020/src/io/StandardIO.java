package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StandardIO {

	private static Scanner scanner = new Scanner(System.in);
	private static int textCrawlDelay = 10;
	private static final int MIN_CRAWL_DELAY = 0, MAX_CRAWL_DELAY = 50;


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
				print(character);
				delay(textCrawlDelay);
			}
			print("\n");
		}
	}
	public static void print(String message) {
		System.out.print(message);
	}
	public static void printDivider() {
		println("--------------------\n");
//		delayShort(); // might be good for a final version, impedes testing
	}
	public static void printLineBreak() {
		println("");
	}
	public static void delayShort() {	
		try {
			Thread.sleep(textCrawlDelay * 50);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void delayModerate() {	
		try {
			Thread.sleep(textCrawlDelay * 100);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void delayLong() {	
		try {
			Thread.sleep(textCrawlDelay * 200);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	public static void delayVeryLong() {	
		try {
			Thread.sleep(textCrawlDelay * 400);
		}catch(Exception e) {
			e.printStackTrace();
		}
	}
	private static void delay(int time) {	
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

	// print from files
	public static void printTXTFile(String filepath) {
		try {
			Scanner reader = new Scanner(new File(filepath));
			while(reader.hasNextLine()) {
				System.out.println(reader.nextLine());
				delay(textCrawlDelay*10);
			}
		} catch (FileNotFoundException e) { }
		
	}
	
	
	
	// getters & setters
	public static int getCrawlDelay() {
		return textCrawlDelay;
	}
	/**
	 * sets the new crawl speed
	 * new value must not be negative, must be under a certain threshold
	 * 
	 * @param crawlSpeedMillis
	 */
	public static void setCrawlDelay(int crawlSpeedMillis) {
		if(crawlSpeedMillis >= MIN_CRAWL_DELAY && crawlSpeedMillis <= MAX_CRAWL_DELAY) {
			textCrawlDelay = crawlSpeedMillis;			
		}
	}
	/**
	 * sets the new crawl speed
	 * new value must not be negative
	 * used for cutscene-type events
	 * 
	 * @param crawlSpeedMillis
	 */
	public static void setCrawlDelayPermissive(int crawlSpeedMillis) {
		if(crawlSpeedMillis >= 0) {
			textCrawlDelay = crawlSpeedMillis;			
		}
	}
	
}
