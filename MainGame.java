package game;

import items.Pokeball;
import items.Potion;
import pokemon.Attack;
import pokemon.PkType;
import pokemon.Pokemon;
import pokemon.Trainer;

public class MainGame {

	public static void main(String[] args) {
		// set up the player's team
		Pokemon plyrPoke1 = new Pokemon("Charmander", PkType.FIRE, 80);
		plyrPoke1.teachMove(Attack.BASIC_ATTACK);
		plyrPoke1.teachMove(Attack.EMBER);
		
		Pokemon plyrPoke2 = new Pokemon("Wooper", PkType.WATER, 50);
		plyrPoke2.teachMove(Attack.BUBBLE);
		plyrPoke2.teachMove(Attack.TACKLE);

		Trainer plyrTrnr = new Trainer();
		plyrTrnr.addPokemon(plyrPoke1);
		plyrTrnr.addPokemon(plyrPoke2);

		plyrTrnr.getBag().addItem(Pokeball.POKEBALL, 3);
		plyrTrnr.getBag().addItem(Pokeball.GREATBALL);
		plyrTrnr.getBag().addItem(Potion.POTION);

		Pokemon enemyPoke = new Pokemon("Eevee", PkType.NORMAL, 60);
		enemyPoke.teachMove(Attack.BASIC_ATTACK);
		enemyPoke.teachMove(Attack.TACKLE);



		// run the game
		PokeBattle game = new PokeBattle(plyrTrnr, enemyPoke);
		game.run();
	}

}
