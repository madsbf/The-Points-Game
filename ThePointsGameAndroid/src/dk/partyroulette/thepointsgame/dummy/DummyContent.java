package dk.partyroulette.thepointsgame.dummy;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.content.Context;

import dk.partyroulette.thepointsgame.model.Profile;
import dk.partyroulette.thepointsgame.model.challenges.Challenge;
import dk.partyroulette.thepointsgame.model.challenges.LocationChallenge;

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 * <p>
 * TODO: Replace all uses of this class before publishing your app.
 */
public class DummyContent {

    /**
     * An array of sample (dummy) items.
     */
    public static List<Challenge> ITEMS = new ArrayList<Challenge>();

    /**
     * A map of sample (dummy) items, by ID.
     */
    public static Map<Long, Challenge> ITEM_MAP = new HashMap<Long, Challenge>();

    public static void init(Context context, Profile profile) 
    {
    	if(ITEMS.isEmpty())
    	{
        	List<Challenge> challenges = DummyWeeks.getChallenges(profile.getWeek(), context);
        	for(int i = 0; i < challenges.size(); i++)
        	{
        		Challenge challenge = challenges.get(i);
        		challenge.setActive(!profile.getChallengesFinished()[i]);
            	addItem(challenge);
        	}
    	}
    }

    private static void addItem(Challenge challenge) {
        ITEMS.add(challenge);
        ITEM_MAP.put(challenge.getId(), challenge);
    }
}
