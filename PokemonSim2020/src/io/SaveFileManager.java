package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import items.Bag;
import pokemon.Move;
import pokemon.MoveList;
import pokemon.PkType;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.Species;
import pokemon.SpeciesList;
import pokemon.Trainer;

public class SaveFileManager {

	public static boolean writeTeamToSave(Player plyr) {
		try {
			PrintWriter writer = new PrintWriter(new File("res/game_files/team_save.txt"));
			Trainer trnr = plyr.getTrainer();
			// write a summary of each pokemon
			for(Pokemon poke : trnr.getAllPokemon()) {
				if(poke == null) {
					writer.print("Null\n");
					continue;
				}
				writer.print("Pokemon: \n");
				writer.print(""
						+ poke.getName() + "\n"
						+ poke.getNickname() + "\n"
						+ poke.getLevel() + "\n"
						//						+ poke.getHpMod() + "\n"
						//						+ poke.getAtkMod() + "\n"
						//						+ poke.getDefMod() + "\n"
						);
				// write a summary of each move
				for(Move move : poke.getAllMoves()) {
					if(move == null) {
						writer.print("Null\n");
						continue;
					}
					writer.print("Move: \n");
					writer.print(""
							+ move.getName() + "\n"
							);
//							+ move.getAudioPath() + "\n");
				}
			}

			writer.close();
			return true;
		} catch (FileNotFoundException e) {
			return false;
		}


	}

	public static Trainer loadTrainerFromSave() {
		try {
			Scanner reader = new Scanner(new File("res/game_files/team_save.txt"));
			Trainer newTrainer = new Trainer();
			for(int i = 0; i < 6; i++) {				
				if(reader.nextLine().equals("Null"))
					continue;
				
				Species spec = SpeciesList.getSpecies(reader.nextLine());
				String nickname = reader.nextLine();
				int level = reader.nextInt();
				reader.nextLine();
				Pokemon poke = new Pokemon(spec, level);
				poke.setNickname(nickname);
				
				// delete unnecessary moves, result of construction
				for(int j = 0; j < 4; j++)
					poke.forgetMove(0);
				
				// relearn saved moves
				for(int j = 0; j < 4; j++) {
					if(reader.nextLine().equals("Null"))
						continue;
					Move move = MoveList.getMove(reader.nextLine());
					poke.teachMove(move);
				}
				
				newTrainer.addPokemon(poke);
			}
			
			// set bag, same every time
			newTrainer.setBag(Bag.getBasicBag());
			return newTrainer;
		}catch (FileNotFoundException e) {
			return null;
		}
	}

}
