package dk.partyroulette.thepointsgame.model.challenges;

public class GoodDeedChallenge extends Challenge 
{

	public GoodDeedChallenge(long id,
			boolean active) 
	{
		super(id, active);
	}
	
	@Override
	public String getTypeName()
	{
		return "Good deed challenge";
	}

}
