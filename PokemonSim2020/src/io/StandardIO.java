package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class StandardIO {

	private static Scanner scanner = new Scanner(System.in);
	private static int textCrawlDelay = 5;


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
				delay(textCrawlDelay);
			}
			System.out.println();
		}
	}
	public static void print(String message) {
		System.out.print(message);
	}
	public static void printDivider() {
		println("--------------------\n");
		//delay(textCrawlDelay * 200); // might be good for a final version, impedes testing
	}
	public static void printLineBreak() {
		println("");
	}
	public static void delay() {	
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
		if(crawlSpeedMillis >= 0 && crawlSpeedMillis <= 50) {
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
