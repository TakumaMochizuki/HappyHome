package com.happy.home.api;

import java.sql.SQLException;

import org.json.JSONArray;

import android.app.Activity;

import com.google.android.gms.maps.model.LatLng;
import com.happy.home.R;
import com.happy.home.dao.DAOFactory;
import com.happy.home.model.Facility;
import com.happy.home.utils.PositionRetreiver;

public class ParseGarbageRecycle
{
	 public static void parseGarbageRecycle(Activity mActivity){

		String message = ParseApi.loadJSONFromAsset(mActivity, "restore_source.json");
		
		JSONArray jArray;
		try
		{
			jArray = new JSONArray(message);
			for (int i = 0; i < jArray.length(); i++)
			{
				int type = 2;
				
				String title = "";
				try
				{
					title = jArray.getJSONObject(i).getString("location");
				} catch (Exception e)
				{
					// TODO: handle exception
				}
				
//				String address = "";
				LatLng xy = null;
				try
				{
					String address =  jArray.getJSONObject(i).getString("address");
					xy = PositionRetreiver.getGPSLocationFromAddressString(address);
				} catch (Exception e)
				{
					// TODO: handle exception
				}
				
				Facility facility = new Facility();
				facility.setX_long(xy.latitude);
				facility.setY_long(xy.longitude);
				facility.setType(type);
				facility.setTitle(title);	
				try
				{
					DAOFactory.getInstance().getfacilityDAO().createOrUpdate(facility);
				} catch (SQLException e)
				{
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
			}
			
		}catch(Exception e){
			
		}
		
	}

}
