package dk.partyroulette.thepointsgame;

public class RandomChallenge extends Challenge 
{

	public RandomChallenge(long id,
			boolean active) {
		super(id, active);
	}
	
	@Override
	public String getTypeName()
	{
		return "Random Challenge";
	}

}
