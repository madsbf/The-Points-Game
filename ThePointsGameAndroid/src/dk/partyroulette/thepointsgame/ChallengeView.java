package dk.partyroulette.thepointsgame;

import android.content.Context;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class ChallengeView // implements OnClickListener
{
	private Challenge challenge;
	private Context context;

	private Button startButton;

	private int primaryColor;
	private int secondaryColor;

	private static final String PRIMARY_COLOR_GREY = "#666666";
	private static final String SECONDARY_COLOR_GREY = "#a1a1a1";
	private int imageResourceId;

	public ChallengeView(Challenge challenge, Context context)
	{
		this.challenge = challenge;
		this.context = context;

		if(challenge instanceof LocationChallenge)
		{
			primaryColor = Color.parseColor("#669900");
			secondaryColor = Color.parseColor("#99CC00");
			imageResourceId = R.drawable.challenge_running;
		}
		else if(challenge instanceof GoodDeedChallenge)
		{
			primaryColor = Color.parseColor("#CC0000");
			secondaryColor = Color.parseColor("#FF4444");
			imageResourceId = R.drawable.challenge_health;
		}
		else if(challenge instanceof RandomChallenge)
		{
			primaryColor = Color.parseColor("#9933CC");
			secondaryColor = Color.parseColor("#AA66CC");
			imageResourceId = R.drawable.challenge_learn;
		}
		else if(challenge instanceof MathChallenge)
		{
			primaryColor = Color.parseColor("#0099CC");
			secondaryColor = Color.parseColor("#33B5E5");
			imageResourceId = R.drawable.challenge_misc;
		}
	}
	
	public void createListView(View view)
	{		
		TextView textName = (TextView) view.findViewById(R.id.textName);
		TextView textDescription = (TextView) view.findViewById(R.id.textDescription);
		ImageView imageChallenge = (ImageView) view.findViewById(R.id.imageChallengeType);

		imageChallenge.setImageResource(imageResourceId);

		if(!challenge.isActive())
		{
			ViewUtilities.updateBackgroundResourceWithRetainedPadding(view, R.drawable.selector_grey);
			ColorMatrix matrix = new ColorMatrix();
			matrix.setSaturation(0); //0 means grayscale
			ColorMatrixColorFilter cf = new ColorMatrixColorFilter(matrix);
			imageChallenge.setColorFilter(cf);
		}

		// check to see if each individual textview is null.
		// if not, assign some text!
		if (textDescription != null)
		{
			textDescription.setText(challenge.getDescription());
		}
		
		if(textName != null)
		{
			textName.setText(challenge.getTypeName());
		}
		
	}
	
	/*
	public void createDetailView(ViewGroup view)
	{

		Activity activity = (Activity) context;
		activity.setTitle(challenge.getName());
		activity.getActionBar().setIcon(imageResourceId);

		LinearLayout layoutProgress = (LinearLayout) view.findViewById(R.id.layoutProgress);

		LinearLayout[] participantViews = new LinearLayout[challenge.getParticipants().length];
		for(int i = 0; i < challenge.getParticipants().length; i++)
		{
			LayoutInflater inflater = activity.getLayoutInflater();
			LinearLayout participantView = (LinearLayout) inflater.inflate(R.layout.participant,
					view, false);

			TextView textUpperBound = (TextView) participantView.findViewById(R.id.textUpperBound);
			TextView textLowerBound = (TextView) participantView.findViewById(R.id.textLowerBound);

			textUpperBound.setText(String.valueOf(challenge.getRepetition().getAmount()));
			textLowerBound.setText("0");

			layoutProgress.addView(participantView);
			participantViews[i] = participantView;
		}

		new ImageLoader2(participantViews).execute(challenge.getParticipants());

		startButton = (Button) view.findViewById(R.id.buttonStart);
		startButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View view) 
	{
		if(view.getId() == R.id.buttonStart)
		{
			Intent intent = new Intent(context, RunStatisticsActivity.class);
			context.startActivity(intent);
		}	
	}
	
	*/
}
