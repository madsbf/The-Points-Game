package dk.partyroulette.thepointsgame.context;

import dk.partyroulette.thepointsgame.R;
import dk.partyroulette.thepointsgame.R.layout;
import dk.partyroulette.thepointsgame.dummy.DummyProfile;
import dk.partyroulette.thepointsgame.model.Profile;
import android.support.v4.app.Fragment;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class ProfileFragment extends Fragment 
{
	/**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public ProfileFragment() {
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_profile,
				container, false);
		
		Profile profile = DummyProfile.getProfile(getActivity());
		
		TextView textDescription = (TextView) rootView.findViewById(R.id.textDescription);
		textDescription.setText(profile.getDescription(getActivity()));

		TextView textPoints = (TextView) rootView.findViewById(R.id.textPoints);
		textPoints.setText(String.valueOf(profile.getPoints()));
		
		return rootView;
	}
}