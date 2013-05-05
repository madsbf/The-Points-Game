package dk.partyroulette.thepointsgame.dummy;

import dk.partyroulette.thepointsgame.MathChallenge;

public class DummyMathChallenges 
{
	public static MathChallenge getChallenge(int index)
	{
		switch(index)
		{
		case 0:
			return new MathChallenge(10l, true);
		default:
			return null;
		}
	}
}
