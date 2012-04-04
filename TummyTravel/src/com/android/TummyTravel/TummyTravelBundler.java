package com.android.TummyTravel;

import android.os.Bundle;

public class TummyTravelBundler {
	public static Bundle getStoreName(String store_name) {
		Bundle storename = new Bundle();
		storename.putString("name", store_name);
		return(storename);
	}
}
