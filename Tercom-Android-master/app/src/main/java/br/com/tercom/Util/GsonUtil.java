package br.com.tercom.Util;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;

public class GsonUtil {


    public static <T>  T getItem(String json, Class<T> selectedClass){
        return new Gson().fromJson(json,selectedClass);
    }

    public static <T> ArrayList<T> getItems(String json, Class<T> selectedClass){
        ArrayList<T> values = new ArrayList<>();
        try {
            JSONArray jsonElements = new JSONArray(json);

            for(int i =0; i<jsonElements.length();i++)
                values.add(getItem(jsonElements.getString(i),selectedClass));

            return values;
        } catch (JSONException e) {
            e.printStackTrace();
            return values;
        }
    }





}
