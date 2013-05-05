package dk.partyroulette.thepointsgame;

import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.UUID;

import com.parse.GetCallback;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.SaveCallback;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class Profile
{
	private String id;
	private int points;
	private int startType;
	private int playerType;
	private int week;
	private Date weekStart;
	private boolean[] challengesFinished;

	private static final int START_TYPE_0 = 0;
	private static final int START_TYPE_7 = 1;

	private static final int PLAYER_TYPE_DEFAULT = 0;
	private static final int PLAYER_TYPE_COURSERA = 1;

	public static final String KEY_ID = "id";
	public static final String KEY_POINTS = "points";
	public static final String KEY_START_TYPE = "startType";
	public static final String KEY_PLAYER_TYPE = "playerType";
	public static final String KEY_WEEK = "week";
	public static final String KEY_CHALLENGES_FINISHED = "challengesFinished";
	public static final String KEY_WEEK_START = "weekStart";

	public Profile(String id, int points, int startType, int playerType, int week, boolean[] challengesFinished, Date weekStart)
	{
		this.id = id;
		this.points = points;
		this.startType = startType;
		this.playerType = playerType;
		this.week = week;
		this.weekStart = weekStart;
		this.challengesFinished = challengesFinished;
	}

	public String getDescription(Context context)
	{
		if(startType == START_TYPE_0)
		{
			return context.getString(R.string.overview_description_start_0, points, getDaysLeft());
		}
		else
		{
			return context.getString(R.string.overview_description_start_0, points, getDaysLeft());	
		}
	}

	private int getDaysLeft()
	{
		Date current = Calendar.getInstance().getTime();
		long difference = current.getTime() - weekStart.getTime();

		return (int) Math.floor(difference / 86400000) + 7;
	}

	public Profile(SharedPreferences sharedPreferences)
	{
		this.id = sharedPreferences.getString(KEY_ID, "");

		if(id.isEmpty())
		{
			createNewProfile(sharedPreferences);
		}
		else
		{
			this.points = sharedPreferences.getInt(KEY_POINTS, 0);
			this.startType = sharedPreferences.getInt(KEY_START_TYPE, 0);
			this.playerType = sharedPreferences.getInt(KEY_PLAYER_TYPE, 0);
			this.week = sharedPreferences.getInt(KEY_WEEK, 0);
			this.weekStart = new Date(sharedPreferences.getLong(KEY_WEEK_START, 0L));

			this.challengesFinished = new boolean[7];
			for(int i = 0; i < 7; i++)
			{
				challengesFinished[i] = sharedPreferences.getBoolean(KEY_CHALLENGES_FINISHED + "_" + String.valueOf(i), false);
			}
		}
	}

	public void setPlayerType(int playerType, SharedPreferences sharedPreferences)
	{
		this.playerType = playerType;

		ParseQuery query = new ParseQuery("Profile");
		query.getInBackground(id, new GetCallback() {
			public void done(ParseObject object, ParseException e) 
			{
				if (e == null) 
				{
					object.put(KEY_PLAYER_TYPE, Profile.this.playerType);
				} 
				else 
				{
					// something went wrong
				}
			}
		});
		
		Editor edit = sharedPreferences.edit();
		edit.putInt(KEY_PLAYER_TYPE, playerType);
		edit.commit();
	}

	private void createNewProfile(SharedPreferences sharedPreferences)
	{
		id = UUID.randomUUID().toString();

		Random random = new Random();
		startType = random.nextInt(2);

		if(startType == START_TYPE_0)
		{
			points = 0;
		}
		else
		{
			points = 7;
		}

		playerType = PLAYER_TYPE_DEFAULT;
		week = 0;
		weekStart = Calendar.getInstance().getTime();

		challengesFinished = new boolean[7];
		for(int i = 0; i < 7; i++)
		{
			challengesFinished[i] = false;
		}

		saveProfileWeb();
		saveProfileLocal(sharedPreferences);
	}

	private void saveProfileWeb()
	{
		ParseObject parseObject = new ParseObject("Profile");
		parseObject.put(KEY_ID, id);
		parseObject.put(KEY_POINTS, points);
		parseObject.put(KEY_START_TYPE, startType);
		parseObject.put(KEY_PLAYER_TYPE, playerType);
		parseObject.put(KEY_WEEK, 0);	
		parseObject.put(KEY_WEEK_START, weekStart);

		for(int i = 0; i < 7; i++)
		{
			parseObject.put(KEY_CHALLENGES_FINISHED + "_" + String.valueOf(i), false);
		}

		parseObject.saveInBackground();
	}

	private void saveProfileLocal(SharedPreferences sharedPreferences)
	{
		Editor edit = sharedPreferences.edit();

		edit.putString(KEY_ID, id);
		edit.putInt(KEY_POINTS, points);
		edit.putInt(KEY_START_TYPE, startType);
		edit.putInt(KEY_PLAYER_TYPE, playerType);
		edit.putInt(KEY_WEEK, week);
		edit.putLong(KEY_WEEK_START, weekStart.getTime());

		for(int i = 0; i < 7; i++)
		{
			edit.putBoolean(KEY_CHALLENGES_FINISHED + "_" + String.valueOf(i), challengesFinished[i]);
		}

		edit.commit();
	}

	public String getId()
	{
		return id;
	}

	public int getPoints()
	{
		return points;
	}

	public int getStartType()
	{
		return startType;
	}

	public int getPlayerType()
	{
		return playerType;
	}

	public int getWeek()
	{
		return week;
	}

	public boolean[] getChallengesFinished()
	{
		return challengesFinished;
	}

}
