package io;

import audio.SoundPlayer;

public class StandardMenu {

	public static boolean promptYesOrNo(String prompt) {
		char response = ' ';
		while( !(response == 'y' || response == 'n')) {

			StandardIO.println(prompt + " (y/n)");
			StandardIO.printLineBreak();

			response = StandardIO.promptChar();
			StandardIO.printLineBreak();

			StandardIO.printDivider();
			if(response == 'y') {
				return true;
			}else if(response == 'n') {
				return false;
			}else {
				StandardIO.printInputNotRecognized();
				StandardIO.printDivider();
			}
		}
		
		// code shouldn't get here
		return false;
	}
	
	public static int promptSelection(String prompt, String[] options) {
		return promptSelection(prompt, options, false);
	}

	public static int promptSelectionEscapable(String prompt, String[] options) {
		return promptSelection(prompt, options, true);
	}

	private static int promptSelection(String prompt, String[] options, boolean isEscapable) {
		assert options.length > 0;

		boolean choiceIsValid = false;
		while(!choiceIsValid) {
			StandardIO.println(prompt);
			StandardIO.printLineBreak();

			// print options w/ labels
			int label = 1;
			for(String opt : options) 
				StandardIO.println(String.format("%d - %s", label++, opt));
			StandardIO.printLineBreak();

			if(isEscapable) {
				StandardIO.printEscCharReminder();
			} 


			// get user choice
			int choice = StandardIO.promptInt();
			StandardIO.printLineBreak();
			StandardIO.printDivider();

			// allow escape?
			if(isEscapable && choice == StandardIO.ESCAPE_INT) {
				return choice;
			}

			// check choice is within bounds
			choiceIsValid = (0 <= choice-1) && (choice-1 < options.length);
			if(choiceIsValid) {
				return choice;
			} 

			StandardIO.println("Input not recognized. Please choose one of the given options.\n");
			StandardIO.printDivider();
		} 

		// shouldn't get here
		return -1;
	}
	
	public static int promptIndex(String prompt, String[] options) {
		int selection = promptSelection(prompt, options, false);
		return selection - 1;
	}
	
	public static int promptIndexEscapable(String prompt, String[] options) {
		int selection = promptSelection(prompt, options, true);
		if(selection == StandardIO.ESCAPE_INT)
			return selection;
		return selection - 1;
	}
}
