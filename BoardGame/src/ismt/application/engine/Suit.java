package ismt.application.engine;

import java.util.Arrays;

public enum Suit {
	 hearts, diamonds, spades, clubs;
	
	public static String[] getNames(Class<? extends Enum<?>> e) {
	    return Arrays.stream(e.getEnumConstants()).map(Enum::name).toArray(String[]::new);
	}
}
