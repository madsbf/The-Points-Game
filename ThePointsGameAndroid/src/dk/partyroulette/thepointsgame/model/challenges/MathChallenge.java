package dk.partyroulette.thepointsgame.model.challenges;

public class MathChallenge extends Challenge 
{

	public MathChallenge(long id,
			boolean active) 
	{
		super(id, active);
	}
	
	@Override
	public String getTypeName()
	{
		return "Math challenge";
	}

}
