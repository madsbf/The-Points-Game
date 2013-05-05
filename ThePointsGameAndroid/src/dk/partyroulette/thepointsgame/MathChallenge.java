package dk.partyroulette.thepointsgame;

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
