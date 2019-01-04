package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.ProductSubGroup;
import br.com.tercom.Entity.ProductSubGroupList;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class ProductSubGroupControl extends GenericControl {

    private Activity activity;

    public ProductSubGroupControl(Activity activity) {
        this.activity = activity;
    }


    public ApiResponse add(int idProductGroup, String name) {

        TreeMap<String,String> map = new TreeMap<>();
        map.put("name", name);
        map.put("idProductGroup", String.valueOf(idProductGroup));

        try {
            String link = getBase(EnumREST.SITE, EnumREST.PRODUCTSUBGROUP, EnumREST.ADD);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<ProductSubGroup> providerApiResponse = new ApiResponse<>(ProductSubGroup.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }


    public ApiResponse update(int idProductSubGroup, String name) {

        TreeMap<String,String> map = new TreeMap<>();
        map.put("name", name);

        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTSUBGROUP, EnumREST.SET), String.valueOf(idProductSubGroup));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<ProductSubGroup> providerApiResponse = new ApiResponse<>(ProductSubGroup.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse remove(int idProductSubGroup) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTSUBGROUP, EnumREST.REMOVE), String.valueOf(idProductSubGroup));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductSubGroup> providerApiResponse = new ApiResponse<>(ProductSubGroup.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }


    public ApiResponse get(int idProductSubGroup) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTSUBGROUP, EnumREST.GET), String.valueOf(idProductSubGroup));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductSubGroup> providerApiResponse = new ApiResponse<>(ProductSubGroup.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse getSectors(int idProductSubGroup) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTSUBGROUP, EnumREST.GETPRODUCTSECTOR), String.valueOf(idProductSubGroup));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductSubGroup> providerApiResponse = new ApiResponse<>(ProductSubGroup.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }


    public ApiResponse search(String value) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTSUBGROUP, EnumREST.SEARCH,EnumREST.NAME), value);
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductSubGroupList> providerApiResponse = new ApiResponse<>(ProductSubGroupList.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }
}
