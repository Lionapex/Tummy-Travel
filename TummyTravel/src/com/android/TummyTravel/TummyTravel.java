package com.android.TummyTravel;

import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;

import com.google.android.maps.GeoPoint;
import com.google.android.maps.MapActivity;
import com.google.android.maps.MapController;
import com.google.android.maps.MapView;
import com.google.android.maps.MyLocationOverlay;
import com.google.android.maps.Overlay;
import com.google.android.maps.OverlayItem;

public class TummyTravel extends MapActivity {

	private MapController mapController;
	private MapView mapView;
	private LocationManager locationManager;
	private MyOverlays itemizedoverlay;
	private MyOverlays itemizedOverlay2;
	private MyLocationOverlay myLocationOverlay;
	private TummyTravelHelper dbHelper;
	List<Overlay> mapOverlays;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setContentView(R.layout.main); // bind the layout to the activity

		// Configure the Map
		mapView = (MapView) findViewById(R.id.myMap);
		mapView.setBuiltInZoomControls(true);
		mapView.setSatellite(false);
		mapController = mapView.getController();
		mapController.setZoom(18); // Zoom 1 is world view
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0,
				0, new GeoUpdateHandler());

		myLocationOverlay = new MyLocationOverlay(this, mapView);
		mapView.getOverlays().add(myLocationOverlay);

		myLocationOverlay.runOnFirstFix(new Runnable() {
			public void run() {
				mapView.getController().animateTo(
						myLocationOverlay.getMyLocation());
			}
		});
		
		mapOverlays = mapView.getOverlays();

		LoadLocations();
		createMarker(mapView.getMapCenter());
	}

	@Override
	protected boolean isRouteDisplayed() {
		return false;
	}
	
	public void LoadLocations()
	{
		try
    	{
			dbHelper=new TummyTravelHelper(this);
			Cursor c=dbHelper.getAll();
			
			if (c != null ) {
	            if  (c.moveToFirst()) {
	                do {
	                	double latitude = c.getDouble(c.getColumnIndex("latitude"));
	                	//double latitude = Double.parseDouble((c.getString(c.getColumnIndex("latitude"))));
	                	double longitude = c.getDouble(c.getColumnIndex("longitude"));
	                	String store_name = c.getString(c.getColumnIndex("name"));
	                	String country = c.getString(c.getColumnIndex("country"));
	                	String type = c.getString(c.getColumnIndex("type"));
	                	//double budget_min = Double.parseDouble((c.getString(c.getColumnIndex("budget_min"))));
	                	//double budget_max = Double.parseDouble((c.getString(c.getColumnIndex("budget_max"))));
	                	
	                	Drawable drawable;
	                	int image = this.getResources().getIdentifier(country.toLowerCase().replace(" ", ""),"drawable", this.getPackageName());
	                	if (image != 0)
	                	{
	                		drawable = this.getResources().getDrawable(image);
	                	}
	                	else
	                	{
	                		drawable = this.getResources().getDrawable(R.drawable.unknown);
	                	}
	                	
	        			itemizedoverlay = new MyOverlays(drawable, mapView);

	                	GeoPoint point = new GeoPoint(
	                            (int)(latitude *1E6 ), 
	                            (int)(longitude *1E6 ));
	                	
	                	itemizedoverlay.addOverlay(new OverlayItem(point, store_name, type));
	                	mapOverlays.add(itemizedoverlay);
	                	
	                }while (c.moveToNext());
	            } 
	        } 
    	}
    	catch(Exception ex)
    	{
    		AlertDialog.Builder b=new AlertDialog.Builder(this);
    		b.setMessage(ex.toString());
    		b.show();
    	}
	}
	
	private GeoPoint getPoint(double lat, double lon) {
	    return(new GeoPoint((int)(lat*1000000.0),
	                          (int)(lon*1000000.0)));
	}
	
	private void createMarker(GeoPoint p) {
		//GeoPoint p = mapView.getMapCenter();
		mapOverlays.remove(itemizedOverlay2);
		
		Drawable drawable2 = getResources().getDrawable(R.drawable.marker1);
		itemizedOverlay2 = new MyOverlays(drawable2, mapView);

		OverlayItem overlayitem = new OverlayItem(p, "You are here!","");
		itemizedOverlay2.addOverlay(overlayitem);

		mapOverlays.add(itemizedOverlay2);
	}

	public class GeoUpdateHandler implements LocationListener {
		@Override
		public void onLocationChanged(Location location) {
			int lat = (int) (location.getLatitude() * 1E6);
			int lng = (int) (location.getLongitude() * 1E6);
			GeoPoint point = new GeoPoint(lat, lng);
			createMarker(point);
			mapController.animateTo(point); // mapController.setCenter(point);
		}

		@Override
		public void onProviderDisabled(String provider) {
		}

		@Override
		public void onProviderEnabled(String provider) {
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
		}
	}


	@Override
	protected void onResume() {
		super.onResume();
		myLocationOverlay.enableMyLocation();
		myLocationOverlay.enableCompass();
	}

	@Override
	protected void onPause() {
		super.onResume();
		myLocationOverlay.disableMyLocation();
		myLocationOverlay.disableCompass();
	}
	/*
	//Menu Item
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
	    new MenuInflater(this).inflate(R.menu.option, menu);
	    return(super.onCreateOptionsMenu(menu));
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		 switch (item.getItemId()) {
		   case R.id.add:          
			   Intent Intent = new Intent(this, TummyTravelStore.class);
		       startActivity(Intent);
		 }
		return(super.onOptionsItemSelected(item));
		

		if (item.getItemId()==R.id.add) {
			startActivity(new Intent(this, TummyTravelStore.class));
			return(true);
		}
		else
		{
			return(super.onOptionsItemSelected(item));
		}
  	}*/
}
