package pokemon;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class MoveList {
	
	public static MoveList getMoveList() {
		return moveList;
	}
	
	public Move getMove(String name) {
		return allMoves.get(name);
	}
	
	
	private static MoveList moveList = new MoveList();
	
	private Map<String, Move> allMoves;
	
	private MoveList() {
		readMovesData();
	}
	
	private void readMovesData() {
		final String COMMA_DELIMITER = ",";
		
		// Read CSV into an ArrayList
		List<List<String>> records = new ArrayList<>();
		try (BufferedReader br = new BufferedReader(new FileReader("csv/moves.csv"))){
			String line;
			while((line = br.readLine()) != null) {
				String[] values = line.split(COMMA_DELIMITER);
				records.add(Arrays.asList(values));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		for(int i = 1; i < records.size(); i++) {
			List<String> list = records.get(i);
			String name = list.get(0);
			PkType type = PkType.valueOf(list.get(1));
			Move.Category category = Move.Category.valueOf(list.get(2));
			int pwr = Integer.parseInt(list.get(3));
			int pp = Integer.parseInt(list.get(4));
			double acc = Double.parseDouble(list.get(5));
			allMoves.put(name, new Move(name, type, category, pwr, pp, acc));
		}
	}
	
}
