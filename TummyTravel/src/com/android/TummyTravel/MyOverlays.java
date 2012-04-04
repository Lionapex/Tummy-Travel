package com.android.TummyTravel;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.ItemizedOverlay;
import com.google.android.maps.MapView;
import com.google.android.maps.OverlayItem;
import com.readystatesoftware.mapviewballoons.BalloonItemizedOverlay;

public class MyOverlays extends BalloonItemizedOverlay<OverlayItem> {

	private static int maxNum = 5;
	private OverlayItem overlays[] = new OverlayItem[maxNum];
	private int index = 0;
	private boolean full = false;
	private Context context;
	private OverlayItem previousoverlay;
	
	private List<OverlayItem> items=new ArrayList<OverlayItem>();
	private ArrayList<OverlayItem> m_overlays = new ArrayList<OverlayItem>();

	/*
	private class SitesOverlay extends ItemizedOverlay<OverlayItem> {
	    private List<OverlayItem> items=new ArrayList<OverlayItem>();
	    
	    public SitesOverlay(Drawable marker) {
	      super(marker);
	      
	      boundCenterBottom(marker);
	      
	      items.add(new OverlayItem(getPoint(40.748963847316034,
	                                          -73.96807193756104),
	                                "UN", "United Nations"));
	      items.add(new OverlayItem(getPoint(40.76866299974387,
	                                          -73.98268461227417),
	                                "Lincoln Center",
	                                "Home of Jazz at Lincoln Center"));
	      items.add(new OverlayItem(getPoint(40.765136435316755,
	                                          -73.97989511489868),
	                                "Carnegie Hall",
	              "Where you go with practice, practice, practice"));
	      items.add(new OverlayItem(getPoint(40.70686417491799,
	                                          -74.01572942733765),
	                                "The Downtown Club",
	                        "Original home of the Heisman Trophy"));

	      populate();
	    }
	    
	    @Override
	    protected OverlayItem createItem(int i) {
	      return(items.get(i));
	    }
	    
	    @Override
	    protected boolean onTap(int i) {
	      Toast.makeText(TummyTravel.this,
	                      items.get(i).getSnippet(),
	                      Toast.LENGTH_SHORT).show();
	      
	      return(true);
	    }
	    
	    @Override
	    public int size() {
	      return(items.size());
	    }
	  }*/
	public MyOverlays(Drawable defaultMarker, MapView mapView) {
		super(boundCenter(defaultMarker), mapView);
		//boundCenterBottom(defaultMarker);
		this.context = mapView.getContext();

		
		//populate();
	}

	

	public void addOverlay(OverlayItem overlay) {
	    m_overlays.add(overlay);
	    populate();
	}

	@Override
	protected OverlayItem createItem(int i) {
		return m_overlays.get(i);
	}

	@Override
	public int size() {
		return m_overlays.size();
	}
    
	@Override
	protected boolean onBalloonTap(int index) {
		OverlayItem itemClicked = m_overlays.get(index);
		String store_name = itemClicked.getTitle().toString();
		String store_snippet = itemClicked.getSnippet().toString();
		
		if (store_snippet != "")
		{
			Intent intent = new Intent(context, TummyTravelInfo.class);
			Bundle tummystore = TummyTravelBundler.getStoreName(store_name);
	        intent.putExtras(tummystore);
			context.startActivity(intent);
		}
		return true;
		/*
		OverlayItem itemClicked = m_overlays.get(index);
		Toast.makeText(context, "you selected " +itemClicked.routableAddress()+" "+ itemClicked.getTitle(),
				Toast.LENGTH_LONG).show();
		return true;*/
	}
	/*
	@Override
    protected OverlayItem createItem(int i) {
      return(items.get(i));
    }
	
	@Override
    protected boolean onTap(int i) {
      Toast.makeText(context,
                      items.get(i).getSnippet(),
                      Toast.LENGTH_SHORT).show();
      
      return(true);
    }
    
    @Override
    public int size() {
      return(items.size());
    }
   
	public void addOverlay(OverlayItem overlay) {
		if (previousoverlay != null) {
			if (index < maxNum) {
				overlays[index] = previousoverlay;
			} else {
				index = 0;
				full = true;
				overlays[index] = previousoverlay;
			}
			index++;
			populate();
		}
		this.previousoverlay = overlay;
	}
/*
	protected boolean onTap(int index) {
		OverlayItem overlayItem = overlays[index];
		Builder builder = new AlertDialog.Builder(context);
		builder.setMessage("This will end the activity");
		builder.setCancelable(true);
		builder.setPositiveButton("I agree", new OkOnClickListener());
		builder.setNegativeButton("No, no", new CancelOnClickListener());
		AlertDialog dialog = builder.create();
		dialog.show();
		return true;
	};
*/
	private final class CancelOnClickListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			Toast.makeText(context, "You clicked yes", Toast.LENGTH_LONG)
					.show();
		}
	}

	private final class OkOnClickListener implements
			DialogInterface.OnClickListener {
		public void onClick(DialogInterface dialog, int which) {
			Toast.makeText(context, "You clicked no", Toast.LENGTH_LONG).show();
		}
	}
}