package io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

import items.Bag;
import pokemon.Move;
import pokemon.PkType;
import pokemon.Player;
import pokemon.Pokemon;
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
						+ poke.getType() + "\n"
						+ poke.getMaxHp() + "\n"
						+ poke.getATK() + "\n"
						+ poke.getDEF() + "\n"
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
							+ move.getPower() + "\n"
							+ move.getType() + "\n"
							+ move.getMaxPP() + "\n"
							+ move.getAudioPath() + "\n");
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
				Pokemon tempPoke = new Pokemon();
				
				if(reader.nextLine().equals("Null"))
					continue;
				
				tempPoke.setName(reader.nextLine());
				tempPoke.setNickname(reader.nextLine());
				tempPoke.setType(PkType.getTypeFromString(reader.nextLine()));
				tempPoke.setMaxHp(reader.nextInt());
				tempPoke.setATK(reader.nextInt());
				tempPoke.setDEF(reader.nextInt()); 
				tempPoke.setLevel(reader.nextInt()); reader.nextLine();
				
				
				for(int j = 0; j < 4; j++) {
					Move tempMove;
					if(reader.nextLine().equals("Null"))
						continue;
					
					String tempName = reader.nextLine();
					int tempDmg = reader.nextInt(); reader.nextLine();
					PkType tempType = PkType.getTypeFromString(reader.nextLine());
					int tempPP = reader.nextInt(); reader.nextLine();
					
					tempMove = new Move(tempName, tempDmg, tempType, tempPP);
					tempMove.setAudioPath(reader.nextLine());
					
					tempPoke.teachMove(tempMove);
				}
				
				newTrainer.addPokemon(tempPoke);
			}
			// set bag, same every time
			newTrainer.setBag(Bag.getBasicBag());
			return newTrainer;
		}catch (FileNotFoundException e) {
			return null;
		}
	}

}
