package io;

import audio.SoundPlayer;

public class StandardMenu {

	public static boolean getYesOrNo(String prompt) {
		char response = ' ';
		while( !(response == 'y' || response == 'n')) {

			StandardIO.println(prompt + " (y/n)");
			StandardIO.printLineBreak();

			response = StandardIO.promptChar();
			StandardIO.printLineBreak();

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

	public static int getSelection(String prompt, String[] options) {
		return getSelection(prompt, options, false);
	}

	public static int getSelectionEscapable(String prompt, String[] options) {
		return getSelection(prompt, options, true);
	}

	private static int getSelection(String prompt, String[] options, boolean isEscapable) {
		assert options.length > 0;

		boolean choiceIsValid = false;
		while(!choiceIsValid) {
			StandardIO.println(prompt);
			StandardIO.printLineBreak();

			// print options w/ labels
			int label = 1;
			for(String opt : options) {
				StandardIO.println(String.format("%d - %s", label++, opt));
			}

			if(isEscapable) {
				StandardIO.printLineBreak();
				StandardIO.printEscCharReminder();
			} else {
				StandardIO.printLineBreak();				
			}


			// get user choice
			int choice = StandardIO.promptInt();
			StandardIO.printLineBreak();

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
		} 

		// shouldn't get here
		return -1;
	}
}
