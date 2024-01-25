package io.project.TossGameTgBot.config;
import java.util.Random;
public enum CoinSide {
	
	HEADS,
	TAILS;
	
	
	
	private static final Random PRNG = new Random();

    public static CoinSide randomSide()  {
        CoinSide[] sides = values();
        return sides[PRNG.nextInt(sides.length)];
    }

}
