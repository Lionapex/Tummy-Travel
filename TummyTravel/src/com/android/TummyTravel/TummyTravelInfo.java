package com.android.TummyTravel;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;


public class TummyTravelInfo extends Activity {
    /** Called when the activity is first created. */
	Cursor c=null;
	TextView name=null;
	TextView desc=null;
	TextView type=null;
	TextView budMin=null;
	TextView budMax=null;
	ImageView logo=null;
	ImageView flag=null;
	RatingBar rating=null;
	Spinner spinner=null;
	TummyTravelHelper helper=null;
	String store_name;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.info_store);
        
        setInputsFromExtras();
        
        helper=new TummyTravelHelper(this);
        //c=helper.getAll();
        c=helper.getIdByName(store_name);
        if (c != null ) {
        	if  (c.moveToFirst()) {
		        String res_name = c.getString(c.getColumnIndex("name"));
		        String res_desc = c.getString(c.getColumnIndex("description"));
		        String res_type = c.getString(c.getColumnIndex("type"));
		        Double res_bmin = c.getDouble(c.getColumnIndex("budget_min"));
		        Double res_bmax = c.getDouble(c.getColumnIndex("budget_max"));
		        byte[] res_logo = c.getBlob(c.getColumnIndex("logo"));
		        String country = c.getString(c.getColumnIndex("country"));
		        Float res_rating = c.getFloat(c.getColumnIndex("rating"));
		        
		        //byte[] res_flag = c.getBlob(c.getColumnIndex("Country"));

		        name=(TextView)findViewById(R.id.restName);
		        desc=(TextView)findViewById(R.id.restDesc);
		        type=(TextView)findViewById(R.id.restType);
		        budMin=(TextView)findViewById(R.id.restMinBudg);
		        budMax=(TextView)findViewById(R.id.resMaxBudg);
		        logo=(ImageView)findViewById(R.id.restBanner);
		        flag=(ImageView)findViewById(R.id.restFlag);
		        rating=(RatingBar)findViewById(R.id.resRating);
		        rating.setOnRatingBarChangeListener(new myListener());
		/*
		        spinner = (Spinner) findViewById(R.id.spinner);
		        
		        Integer rate = Integer.parseInt(res_rating.toString());
		        ArrayAdapter myAdap = (ArrayAdapter) spinner.getAdapter(); //cast to an ArrayAdapter
		        int spinnerPosition = rate - 1;

		        spinner.setSelection(spinnerPosition);
		    	spinner.setOnItemSelectedListener(new CustomOnItemSelectedListener());
		    	*/
		        name.setText(res_name.toString());
		        if (res_desc != null)
		        {
		        	String desc = res_desc.toString();
		        	this.desc.setText(desc);
		        }
		        type.setText(res_type.toString());
		        budMin.setText(String.valueOf(res_bmin));
		        budMax.setText(String.valueOf(res_bmax));
		        rating.setRating(res_rating);
		        
		        if(res_logo != null)
		        {
			        //logo.setImageBitmap(BitmapFactory.decodeByteArray(res_logo, 0, res_logo.length));
			        Bitmap logobm = BitmapFactory.decodeByteArray(res_logo, 0, res_logo.length);
			        if(logobm != null)
			        {
			        	logo.setImageBitmap(logobm);
			        }
		        }

		        
		        int image = this.getResources().getIdentifier(country.toLowerCase().replace(" ", "") + "flag","drawable", this.getPackageName());
            	if (image != 0)
            	{
            		flag.setImageResource(image);
            	}
		
		        
        	}
        }else{
        	
        }
        /*if(res_flag != null)
        {
        flag.setImageBitmap(BitmapFactory.decodeByteArray(res_flag, 0, res_flag.length));
        Bitmap flagbm = BitmapFactory.decodeByteArray(res_flag, 0, res_flag.length);
        flag.setImageBitmap(flagbm);
        }*/
    }
    
    private void setInputsFromExtras() {
        Intent intent = getIntent();
        Bundle storeInfo = intent.getExtras();
        if (storeInfo != null) {
            String store_name = 
            	storeInfo.getString("name");
            setInputs(store_name);
        }
    }
    
    private void setInputs(String store_name) {
        if (store_name != null) {
        	this.store_name = store_name;
        }
    }
    
    class myListener implements RatingBar.OnRatingBarChangeListener  
    {  
        @Override  
        public void onRatingChanged(RatingBar ratingBar, float rating1,  
                boolean fromUser) {  
            // TODO Auto-generated method stub  
            //Toast.makeText(TummyTravelInfo.this, "Rating => "+rating1, Toast.LENGTH_SHORT).show();  
    		helper.updateRating(rating1,store_name);
    		rating.setRating(rating1);
        }  
    }
    
    /*
    class CustomOnItemSelectedListener implements OnItemSelectedListener 
    {
    	  public void onItemSelected(AdapterView<?> parent, View view, int pos,long id) {
    		Toast.makeText(parent.getContext(), 
    			"OnItemSelectedListener : " + parent.getItemAtPosition(pos).toString(),
    			Toast.LENGTH_SHORT).show();
    		
    	  }
    	 
    	  @Override
    	  public void onNothingSelected(AdapterView<?> arg0) {
    		// TODO Auto-generated method stub
    	  }
    }*/
}