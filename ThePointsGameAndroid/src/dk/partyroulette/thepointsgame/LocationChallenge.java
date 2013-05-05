package dk.partyroulette.thepointsgame;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import android.content.Context;
import android.location.Address;
import android.location.Geocoder;
import android.location.LocationManager;

public class LocationChallenge extends Challenge 
{
	private Location location;
	private Context context;
	
	public LocationChallenge(long id, boolean active, Location location, Context context) 
	{
		super(id, active);
		this.location = location;
		this.context = context;
	}
	
	@Override
	public String getDescription()
	{
		String description = context.getString(R.string.challenge_location_description, location.getName());
		return description;
	}

	public LocationChallenge(long id, boolean active, int distance, Context context) 
	{
		super(id, active);
		
		this.context = context;
		try 
		{
			this.location =	findLocation(distance, context);
		} 
		catch (LocationNotAccessibleException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Location getLocation()
	{
		return location;
	}

	private Location findLocation(int distance, Context context) throws LocationNotAccessibleException, IOException
	{
		android.location.Location startLocation = getLocation((LocationManager) context.getSystemService(Context.LOCATION_SERVICE));

		Location newLocation = generateLocation(distance, startLocation, context);
		int i = 0;
		while(newLocation == null && i < 5)
		{
			newLocation = generateLocation(distance, startLocation, context);
			i++;
		}
		
		return newLocation;
	}

	private Location generateLocation(int distance, android.location.Location startLocation, Context context) throws IOException
	{
		android.location.Location newLocation = new android.location.Location(startLocation);

		double longitudeChange = 0.000006 * distance; // TODO
		double latitudeChange = 0.000006 * distance; // TODO

		newLocation.setLongitude(startLocation.getLongitude() + longitudeChange);
		newLocation.setLatitude(startLocation.getLatitude() + latitudeChange);

		Geocoder gc = new Geocoder(context, Locale.getDefault());
		List<Address> addresses = gc.getFromLocation(newLocation.getLatitude(), newLocation.getLongitude(), 1);
		

		if(addresses != null && !addresses.isEmpty())
		{
			Address address = addresses.get(0);
			return new Location(address.getAddressLine(0),
					address.getLongitude(),
					address.getLatitude());
		}
		else
		{
			return null;
		}
	}

	private android.location.Location getLocation(LocationManager locationManager) throws LocationNotAccessibleException
	{
		List<String> providers = locationManager.getAllProviders();

		// Find the most recent location
		if(!providers.isEmpty())
		{
			android.location.Location lastKnownLocation = locationManager.getLastKnownLocation(providers.get(0));
			for(int i = 1; i < providers.size(); i++)
			{
				android.location.Location location = locationManager.getLastKnownLocation(providers.get(i));
				if(location.getTime() > lastKnownLocation.getTime())
				{
					lastKnownLocation = location;
				}
			}

			return lastKnownLocation;
		}
		else
		{
			throw new LocationNotAccessibleException();
		}

	}
	
	@Override
	public String getTypeName()
	{
		return "Location challenge";
	}

}
