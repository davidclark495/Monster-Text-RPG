package location;

import io.StandardIO;
import pokemon.Dex;
import pokemon.Pokemon;

public class FirstArea extends StoryArea{

	public FirstArea() {
		this("First Area");
	}
	// base constructor
	public FirstArea(String nm) {
		super(nm);
		this.setMapDescription("The start of a journey.");
		this.setLocalDescription("...a strange sound.");
		this.defineActivity();
	}

	@Override
	protected void defineActivity() {
		this.setActivity( () -> {
			// do different activities at different iteration times
			if(this.getRunActivityIterations() == 0) {
				return firstActivity();				
			}
			else {
				return false;
			}
		});
	}

	/**
	 * prints things, like a cutscene
	 * permanent changes: 
	 * 		adds the starter pokemon to the party
	 * 		updates the local description
	 * 
	 * @return a boolean (True) indicating success
	 */
	private boolean firstActivity() {
		// set up
		Pokemon starterPoke = chooseStarterRandom();
		int initialIODelay = StandardIO.getCrawlDelay();
		StandardIO.setCrawlDelay(initialIODelay * 10);

		// print things, like a cutscene
		StandardIO.println("...you hear a Pokemon cry out in the distance.\n");
		StandardIO.println("You follow the sound.\n");
		StandardIO.printDivider();
		StandardIO.delay(500);

		StandardIO.setCrawlDelayPermissive(StandardIO.getCrawlDelay() * 2);
		StandardIO.println(starterPoke.getCry() + "\n");
		StandardIO.setCrawlDelayPermissive(StandardIO.getCrawlDelay() / 2);
		StandardIO.printDivider(); 
		StandardIO.delay(500);

		StandardIO.println("The Pokemon is frantically searching for... something.\n");

		boolean loopAgain = true;
		char input = 0;
		while(loopAgain) {// get valid input			
			StandardIO.println("Pick up the Pokemon? (y/n)");
			input = StandardIO.promptChar();
			StandardIO.println("");
			if(input == 'y' || input == 'n') {
				loopAgain = false;					
			} else {
				StandardIO.printInputNotRecognized();	
			}
			StandardIO.printDivider();
		}
		// respond to input
		if(input == 'y') {
			StandardIO.println("...the wild Pokemon seems to calm down.\n");
			StandardIO.println("They leap to the ground. They want you to follow.\n");
		}
		else if(input == 'n') {
			StandardIO.println("The wild Pokemon looks at you cautiously. It approaches.\n");
			StandardIO.println("The Pokemon gives you a quick sniff, then resumes its search.\n");
			StandardIO.println("They've found a trail.\n");
			StandardIO.println("...\n");
			StandardIO.println("It looks like they want you to follow.\n");
		}

		StandardIO.printDivider();
		StandardIO.delay(500);
		StandardIO.println(starterPoke.getName() + " joined your team.\n");

		this.getPlayer().getTrainer().addPokemon(starterPoke);
		StandardIO.delay(1000);

		// restore needed settings
		this.setLocalDescription("Nothing else to see.");
		StandardIO.setCrawlDelay(initialIODelay);

		return true;
	}

	private Pokemon chooseStarterRandom() {
		Pokemon poke = Dex.generateEncounter(new Pokemon[] {Dex.vulpix, Dex.wooper, Dex.pikachu});
		poke.setLevel(5);
		return poke;
	}

}
