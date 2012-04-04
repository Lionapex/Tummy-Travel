package com.android.TummyTravel;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class TummyTravelHelper extends SQLiteOpenHelper {
	private static final String DATABASE_NAME="ttstores.db";
	  private static final int SCHEMA_VERSION=3;
	  private SQLiteDatabase myDataBase;
	  
	  public TummyTravelHelper(Context context) {
	    super(context, DATABASE_NAME, null, SCHEMA_VERSION);
	  }
	  
	  @Override
	  public void onCreate(SQLiteDatabase db) {
	    db.execSQL("CREATE TABLE restaurants " +
	    		"(_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
	    		"merchant TEXT, name TEXT, description TEXT, country TEXT, " +
	    		"type TEXT,  longitude REAL, latitude REAL, " +
	    		"budget_min TEXT, budget_max REAL, website TEXT, logo BLOB, rating FLOAT);");
	  }

	  @Override
	  public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
	  }
	  
	  public void openDataBase() throws SQLException{
			//Open the database
			String myPath = "/data/data/com.android.TummyTravel/databases/ttstores";
			myDataBase = SQLiteDatabase.openDatabase(myPath, null, SQLiteDatabase.OPEN_READONLY);
		}

	  public Cursor getAll() {
	    return(getReadableDatabase()
	            .rawQuery("SELECT _id, merchant, name, description, country, type, longitude, " +
	            		"latitude, budget_min, budget_max, website, logo, rating FROM " +
	            		"restaurants", null));
	    /*
		  SQLiteDatabase db=this.getReadableDatabase();
		  Cursor cur=db.rawQuery("SELECT _id, merchant, name, country, type, longitude, " +
	      		"latitude, budget_min, budget_max, website, logo FROM " +
	    		"restaurants", null);
		   
		   return cur;
		*/
	  }
	  
	  public Cursor getIdByName(String name) {
		  String[] args={name};
		  return(getReadableDatabase()
		            .rawQuery("SELECT _id, merchant, name, description, country, type, longitude, " +
		            		"latitude, budget_min, budget_max, website, logo, rating FROM " +
		            		"restaurants WHERE name=?",
		                      args));
		  /*
		  return(getReadableDatabase()
		          .rawQuery("SELECT _id FROM restaurants WHERE name=?", args));*/
	  }
	  

	  public Cursor getById(String id) {
	    String[] args={id};

	    return(getReadableDatabase()
	            .rawQuery("SELECT _id, merchant, name, description, country, type, longitude, " +
	            		"latitude, budget_min, budget_max, website, logo, rating FROM " +
	            		"restaurants WHERE _ID=?",
	                      args));
	  }
	  
	  public void insert(String merchant, String name, String description, String country,
            String type, Double longitude, Double latitude, Double budget_min,
            Double budget_max) {
			ContentValues cv=new ContentValues();
			   
			cv.put("merchant", merchant);
			cv.put("name", name);
			cv.put("description", description);
			cv.put("country", country);
			cv.put("type", type);
			cv.put("longitude", longitude);
			cv.put("latitude", latitude);
			cv.put("budget_min", budget_min);
			cv.put("budget_max", budget_max);
			
			getWritableDatabase().insert("restaurants", "merchant", cv);
	  }
	  
	  public void updateRating(float rate, String name)
	  {
			  ContentValues args = new ContentValues();
			  args.put("rating", rate);
			  
			  String[] whereArgs = {name.toString()};
			  getWritableDatabase().update("restaurants", args, "name=?",whereArgs);
	  }
	
	  
	  /*
	  public void insert(String name, String address,
	                     String type, String notes,
	                     String feed) {
	    ContentValues cv=new ContentValues();
	          
	    cv.put("name", name);
	    cv.put("address", address);
	    cv.put("type", type);
	    cv.put("notes", notes);
	    cv.put("feed", feed);
	    
	    getWritableDatabase().insert("restaurants", "name", cv);
	  }
	  
	  public void update(String id, String name, String address,
	                     String type, String notes, String feed) {
	    ContentValues cv=new ContentValues();
	    String[] args={id};
	    
	    cv.put("name", name);
	    cv.put("address", address);
	    cv.put("type", type);
	    cv.put("notes", notes);
	    cv.put("feed", feed);
	    
	    getWritableDatabase().update("restaurants", cv, "_ID=?",
	                                 args);
	  }
	  
	  public void updateLocation(String id, double lat, double lon) {
	    ContentValues cv=new ContentValues();
	    String[] args={id};
	    
	    cv.put("lat", lat);
	    cv.put("lon", lon);
	    
	    getWritableDatabase().update("restaurants", cv, "_ID=?",
	                                 args);
	  }
	  */
	  
	  public String getMerchant(Cursor c) {
	    return(c.getString(1));
	  }
	  
	  public String getName(Cursor c) {
	    return(c.getString(2));
	  }
	  
	  public String getCountry(Cursor c) {
	    return(c.getString(3));
	  }
	  
	  public String getType(Cursor c) {
	    return(c.getString(4));
	  }
	  
	  public String getLongitude(Cursor c) {
	    return(c.getString(5));
	  }
	  
	  public double getLatitude(Cursor c) {
	    return(c.getDouble(6));
	  }
	  
	  public double getBudgetMin(Cursor c) {
	    return(c.getDouble(7));
	  }
	  
	  public double getBudgetMax(Cursor c) {
		return(c.getDouble(8));
	  }
	  
	  public double getWebsite(Cursor c) {
		return(c.getDouble(9));
	  }
	  
	  public double getLogo(Cursor c) {
		return(c.getDouble(10));
	  }

}
