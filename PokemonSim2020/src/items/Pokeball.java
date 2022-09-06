package items;

import java.util.Random;

import io.StandardIO;
import pokemon.Player;
import pokemon.Pokemon;
import pokemon.Trainer;

public class Pokeball extends Item{
	
	public static final Pokeball 
	POKEBALL = new Pokeball("Pokeball", 1),
	GREATBALL = new Pokeball("Greatball", 1.5),
	ULTRABALL = new Pokeball("Ultraball", 2);

	private double ballBonus;

	private Pokeball(String nm, double bonus) {
		super(nm);
		this.ballBonus = bonus;
	}

	
	public double getBallBonus() {
		return this.ballBonus;
	}
	



}
