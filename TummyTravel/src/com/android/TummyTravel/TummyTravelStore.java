package com.android.TummyTravel;

import java.util.Arrays;
import java.util.List;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;

public class TummyTravelStore extends Activity{
    private String selected;
    TummyTravelHelper db = new TummyTravelHelper(this);;
    Button save = (Button)findViewById(R.id.save);
    EditText resName = (EditText)findViewById(R.id.edtName);
    EditText resDesc = (EditText)findViewById(R.id.edtDesc);
    Spinner resType = (Spinner)findViewById(R.id.spnType);
    Spinner resCountry = (Spinner)findViewById(R.id.spnCountry);
    EditText resMin = (EditText)findViewById(R.id.budMin);
    EditText resMax = (EditText)findViewById(R.id.budMax);
    EditText resLong = (EditText)findViewById(R.id.longitude);
    EditText resLat = (EditText)findViewById(R.id.latitude);
    
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_store);
        selected = getString(R.string.selectedTemp);

        List<String> lType = getType();
        ArrayAdapter<String> resTypeAdapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lType);
        resTypeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resType.setAdapter(resTypeAdapter);
        resType.setOnItemSelectedListener(new SpinnerInfo());
        
        List<String> lCountry = getCountry();
        ArrayAdapter<String> spinner2Adapter =
                new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, lCountry);
        spinner2Adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        resCountry.setAdapter(spinner2Adapter);
        resCountry.setOnItemSelectedListener(new SpinnerInfo());
     /*   
        addButtonListeners();
        */
    }
    
    private void showToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_LONG).show();
    }
    
    private List<String> getType() {
        String[] rType = { "Bar","Fast Food", "Casual","Fine Dining","Coffee Shop" };
        List<String> TypeList = Arrays.asList(rType);
        return(TypeList);
    }
    
    private List<String> getCountry() {
        String[] countryArray = { "Philippines","United Kingdom","USA","Italy" };
        List<String> CountryList = Arrays.asList(countryArray);
        return(CountryList);
    }
    
    private class SpinnerInfo implements OnItemSelectedListener {
        private boolean isFirst = true;

        @Override
        public void onItemSelected(AdapterView<?> spinner, View selectedView, 
                                   int selectedIndex, long id) {
            if (isFirst) {
                isFirst = false;
            } else {
                String selection = spinner.getItemAtPosition(selectedIndex).toString();
                String message =
                        String.format(selected, selection);
                showToast(message);
            }
        }

        @Override
        public void onNothingSelected(AdapterView<?> spinner) {
            // Won't be invoked for a Spinner unless you programmatically remove items from the view
        }
    }
/*    private void addButtonListeners()
    {
       save.setOnClickListener
    	(
    		new View.OnClickListener()
	    	{
				@Override public void onClick(View v) {addRow();}
			}
    	);
    }
    
    private void addRow()
    {
    	try
    	{
    		// ask the database manager to add a row given the two strings
    		db.addRow
    		(
    				resName.getText().toString(),
    				resDesc.getText().toString(),
    				resType.getSelectedItem().toString(),
    				resCountry.getSelectedItem().toString(),
    				Integer.parseInt(resMin.getText().toString()),
    				Integer.parseInt(resMax.getText().toString()),
    				Integer.parseInt(resLong.getText().toString()),
    				Integer.parseInt(resLat.getText().toString())
    		);
    	}
        catch (Exception e)
        	{
        		Log.e("Add Error", e.toString());
        		e.printStackTrace();
        	}
        }*/
 
}