package dk.partyroulette.thepointsgame.context;

import java.lang.reflect.Field;

import com.parse.Parse;
import com.parse.ParseAnalytics;

import dk.partyroulette.thepointsgame.R;
import dk.partyroulette.thepointsgame.R.id;
import dk.partyroulette.thepointsgame.R.layout;
import dk.partyroulette.thepointsgame.R.menu;
import dk.partyroulette.thepointsgame.dummy.DummyContent;
import dk.partyroulette.thepointsgame.model.Profile;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.ViewConfiguration;
import android.widget.TextView;


/**
 * An activity representing a list of Challenges. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a {@link ChallengeDetailActivity} representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link ChallengeListFragment} and the item details
 * (if present) is a {@link ChallengeDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link ChallengeListFragment.Callbacks} interface
 * to listen for item selections.
 */
public class ChallengeListActivity extends FragmentActivity
implements ChallengeListFragment.Callbacks 
{

	private Profile profile;


	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	private boolean isFirstRun()
	{
		if(getSharedPreferences("dk.partyroulette.ThePointsGame", MODE_PRIVATE).getString(Profile.KEY_ID, "").isEmpty())
		{
			return true;
		}
		return false;
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_challenge_list);

		// HACK til at vise menu-knap
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class.getDeclaredField("sHasPermanentMenuKey");
			if(menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception ex) {
			// Ignore
		}


		Parse.initialize(this, "1P0mM0abV8M3XxmfEA07qzlnRODPb8gQXtpD3O2c", "xDl2RmTKgmwdDTgfwQi3r4H11rAlLsVrc1meJya3"); 
		ParseAnalytics.trackAppOpened(getIntent());

		if(isFirstRun())
		{
			// TODO: First run pop-up
		}

		if(profile == null)
		{
			profile = new Profile(getSharedPreferences("dk.partyroulette.ThePointsGame", MODE_PRIVATE));
		}
		DummyContent.init(this, profile);

		TextView textDescription = (TextView) findViewById(R.id.textDescription);
		textDescription.setText(profile.getDescription(this));

		TextView textPoints = (TextView) findViewById(R.id.textPoints);
		textPoints.setText(String.valueOf(profile.getPoints()));

		if (findViewById(R.id.challenge_detail_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((ChallengeListFragment) getSupportFragmentManager()
					.findFragmentById(R.id.challenge_list))
					.setActivateOnItemClick(true);
		}

		// TODO: If exposing deep links into your app, handle intents here.
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		//inflate with your particular xml
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return super.onCreateOptionsMenu(menu);
	}


	/**
	 * Callback method from {@link ChallengeListFragment.Callbacks}
	 * indicating that the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(Long id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			arguments.putLong(ChallengeDetailFragment.ARG_ITEM_ID, id);
			ChallengeDetailFragment fragment = new ChallengeDetailFragment();
			fragment.setArguments(arguments);
			getSupportFragmentManager().beginTransaction()
			.replace(R.id.challenge_detail_container, fragment)
			.commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, ChallengeDetailActivity.class);
			detailIntent.putExtra(ChallengeDetailFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
}
