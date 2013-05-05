package dk.partyroulette.thepointsgame.dummy;

import android.content.Context;
import dk.partyroulette.thepointsgame.model.challenges.LocationChallenge;

public class DummyLocationChallenges 
{
	public static LocationChallenge getChallenge(int index, Context context)
	{
		switch(index)
		{
		case 0:
			return new LocationChallenge(1l, true, 1000, context);
		default:
			return null;
		}
	}
}
