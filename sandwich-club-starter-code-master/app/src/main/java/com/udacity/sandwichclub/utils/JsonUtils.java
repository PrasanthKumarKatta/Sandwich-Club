package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static android.content.ContentValues.TAG;

public class JsonUtils {
    private static final String NAME_Sandwich = "name";
    private static final String MainName_Sandwich = "mainName";
    private static final String AlsoKnownAs_Sandwich = "alsoKnownAs";
    private static final String PlaceOfOrigin_Sandwich = "placeOfOrigin";
    private static final String Description_Sandwich = "description";
    private static final String Image_Sandwich = "image";
    private static final String Ingredients_Sandwich = "ingredients";

    private static final String TAG = JsonUtils.class.getSimpleName();

    public static Sandwich parseSandwichJson(String json)
    {
        Sandwich sandwich = null;
        try {
            JSONObject mJsonObject = new JSONObject(json);

            JSONObject name = mJsonObject.getJSONObject(NAME_Sandwich);
            String mainName = name.optString(MainName_Sandwich);

            JSONArray alsoKnownAs_JsonArray = name.getJSONArray(AlsoKnownAs_Sandwich);
            List<String> alsoKnownAs = convertToListFromJson(alsoKnownAs_JsonArray);

            String placeOfOrigin = mJsonObject.optString(PlaceOfOrigin_Sandwich);

            String description = mJsonObject.optString(Description_Sandwich);

            String image = mJsonObject.optString(Image_Sandwich);

            JSONArray Ingredients_JsonArray = mJsonObject.getJSONArray(Ingredients_Sandwich);
            List<String> ingredients = convertToListFromJson(Ingredients_JsonArray);

            sandwich = new Sandwich(mainName,alsoKnownAs,placeOfOrigin,description,image,ingredients);

        } catch (JSONException e) {
            Log.e(TAG,e.getMessage());
            e.printStackTrace();
        }

        return sandwich;
    }

    private static List<String> convertToListFromJson(JSONArray alsoKnownAs_jsonArray) throws  JSONException
    {
       List<String> list = new ArrayList<>(alsoKnownAs_jsonArray.length());

       for (int i = 0; i <alsoKnownAs_jsonArray.length(); i++)
       {
           list.add(alsoKnownAs_jsonArray.getString(i));

       }

     return  list;
    }
}
