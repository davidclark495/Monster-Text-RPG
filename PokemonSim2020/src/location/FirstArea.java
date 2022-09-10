package location;

import java.util.Random;

import io.StandardIO;
import pokemon.Dex;
import pokemon.Pokemon;
import pokemon.Species;
import pokemon.SpeciesList;

public class FirstArea extends StoryArea{

	// base constructor
	public FirstArea(String nm, String mapDesc, String localDesc) {
		super(nm, mapDesc, localDesc);
		this.defineActivity();
	}

	@Override
	protected void defineActivity() {
		this.setActivity( () -> {
			// do different activities at different iteration times
			if(this.runActivityIterations == 0) {
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
//		StandardIO.setCrawlDelay(initialIODelay * 10);

		// print things, like a cutscene
		StandardIO.println("...you hear a Pokemon cry out in the distance.\n");
		StandardIO.println("You follow the sound.\n");
		StandardIO.printDivider();
		StandardIO.delayModerate();

		StandardIO.setCrawlDelayPermissive(StandardIO.getCrawlDelay() * 2);
		StandardIO.println("womp-\n");

//		StandardIO.println(starterPoke.getCry() + "\n");
		StandardIO.setCrawlDelayPermissive(StandardIO.getCrawlDelay() / 2);
		StandardIO.printDivider(); 
		StandardIO.delayLong();

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
			StandardIO.delayModerate();
			StandardIO.println("It looks like they want you to follow.\n");
		}

		StandardIO.printDivider();
		StandardIO.delayModerate();
		StandardIO.println(starterPoke.getName() + " joined your team.\n");

		this.getPlayer().getTrainer().addPokemon(starterPoke);
		StandardIO.delayLong();

		// restore needed settings
		this.setLocalDescription("Nothing else to see.");
		StandardIO.setCrawlDelay(initialIODelay);

		return true;
	}

	private Pokemon chooseStarterRandom() {
		Species spec;
		switch(new Random().nextInt(3)) {
		case 0:
			spec = SpeciesList.getSpecies("Togepi");
			break;
		case 1:
			spec = SpeciesList.getSpecies("Vulpix");
			break;
		default:
			spec = SpeciesList.getSpecies("Lotad");
		}
		return new Pokemon(spec, 5);
	}

}
