package dk.partyroulette.thepointsgame.dummy;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;

import dk.partyroulette.thepointsgame.Challenge;

public class DummyWeeks 
{
	public static List<Challenge> getChallenges(int week, Context context)
	{
		ArrayList<Challenge> challenges = new ArrayList<Challenge>();
		
		switch(week)
		{
		case 0:
			challenges.add(DummyLocationChallenges.getChallenge(0, context));
			challenges.add(DummyMathChallenges.getChallenge(0));
			challenges.add(DummyMathChallenges.getChallenge(0));
			challenges.add(DummyMathChallenges.getChallenge(0));
			challenges.add(DummyMathChallenges.getChallenge(0));
			challenges.add(DummyMathChallenges.getChallenge(0));
			challenges.add(DummyMathChallenges.getChallenge(0));
			break;
		}
		
		return challenges;
	}

}
