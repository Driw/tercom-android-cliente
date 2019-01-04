package br.com.tercom.Control;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Permission;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class PermissionControl extends GenericControl {
    private Activity activity;

    public PermissionControl(Activity activity){
        this.activity = activity;
    }

    public ApiResponse add(String packet, String action, int assignmentLevel){
        TreeMap<String,String> map = new TreeMap<>();
        map.put("packet", packet);
        map.put("aciton", action);
        map.put("assignmentLevel", String.valueOf(assignmentLevel));

        try{
            String link = getBase(EnumREST.SITE, EnumREST.PERMISSION, EnumREST.ADD);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<Permission> providerApiResponse = new ApiResponse<>(Permission.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        }
        catch(Exception e){
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse set(int id, String packet, String action, int assignmentLevel){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("packet", packet);
        map.put("action", action);
        map.put("assignmentLevel", String.valueOf(assignmentLevel));

        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PERMISSION, EnumREST.SET), String.valueOf(id));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<Permission> providerApiResponse = new ApiResponse<>(Permission.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        }
        catch(Exception e){
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse remove(int id){
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PERMISSION, EnumREST.REMOVE), String.valueOf(id));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<Permission> providerApiResponse = new ApiResponse<>(Permission.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        }
        catch(Exception e){
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse get(int id){
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PERMISSION, EnumREST.GET), String.valueOf(id));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<Permission> providerApiResponse = new ApiResponse<>(Permission.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        }
        catch(Exception e){
            e.printStackTrace();
            return getErrorResponse();
        }
    }
}
