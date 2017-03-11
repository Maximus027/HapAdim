package com.example.hapadim.models;

import android.content.Context;
import android.os.Parcelable;
import android.util.Log;

import com.example.hapadim.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by meltemyildirim on 3/8/17.
 */

public class JsonEndPoint {

    public static ArrayList<Place> mountains;
    public static ArrayList<Place> monuments;
    public static ArrayList<Place> longDistance;
    public static final String TAG = JsonEndPoint.class.getName();

    public String readFromJsonFile(Context context) {
        InputStream is = context.getResources().openRawResource(R.raw.places_json);
        StringBuffer sbJsonString = new StringBuffer();
        int character;

        try {
            while ((character = is.read()) != -1) {

                sbJsonString.append((char) character);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sbJsonString.toString();
    }

    public ArrayList<Place> getMountains() {
        return mountains;
    }

    public ArrayList<Place> getMonuments() {
        return monuments;
    }

    public ArrayList<Place> getLongDistance() {
        return longDistance;
    }

    public void populateLocations(String jsonObject) {
        mountains = new ArrayList<>();
        monuments = new ArrayList<>();
        longDistance = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(jsonObject);
            Log.d(TAG, "JSONArr size " + jsonArray.length());
            for (int i = 0; i < jsonArray.length(); i++) {
                Place place = new Place();
                JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                place.setCategory(jsonObject1.getString("category"));
                place.setPlaceName(jsonObject1.getString("placeName"));
                place.setUrlIMG(jsonObject1.getString("urlIMG"));
                place.setStepNumber(jsonObject1.getLong("stepNumber"));
                place.setUrlVR(jsonObject1.getString("urlVR"));
                JSONArray badges = jsonObject1.getJSONArray("badges");
                for (int j = 0; j < badges.length(); j++) {
                    JSONObject getBadge = badges.getJSONObject(i);
                    Badge badge = new Badge();
                    badge.setBadgeDesc(getBadge.getString("badgedName"));
                    badge.setBadgedName(getBadge.getString("badgeName"));
                    badge.setBadgeImg(getBadge.getString("badgeImg"));
                }
                if (place.getCategory().equals("Mountains")) {
                    mountains.add(place);

                } else if (place.getCategory().equals("Monuments")) {
                    monuments.add(place);
                } else {
                    longDistance.add(place);

                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    public void wrapParcelables(Boolean facts) {
        Parcelable wrapped = Parcels.wrap(new Place());

    }


}
