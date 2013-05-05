package dk.partyroulette.thepointsgame;


public class Challenge {

	private long id;
	private boolean active;
	
	public Challenge(long id, boolean active)
	{
		this.id = id;
		this.active = active;
	}
	
	public boolean isActive()
	{
		return active;
	}
	
	public void setActive(boolean active)
	{
		this.active = active;
	}
	
	public String getTypeName()
	{
		return "Challenge";
	}
	
	public String getDescription()
	{
		return "No Challenge";
	}
	
	public Long getId()
	{
		return id;
	}
	
}
