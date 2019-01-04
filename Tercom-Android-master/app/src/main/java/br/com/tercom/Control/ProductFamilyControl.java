package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.ProductFamily;
import br.com.tercom.Entity.ProductFamilyList;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class ProductFamilyControl extends GenericControl {

    private Activity activity;

    public ProductFamilyControl(Activity activity) {
        this.activity = activity;
    }


    public ApiResponse add(String name) {

        TreeMap<String,String> map = new TreeMap<>();
        map.put("name", name);

        try {
            String link = getBase(EnumREST.SITE, EnumREST.PRODUCTFAMILY, EnumREST.ADD);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<ProductFamily> providerApiResponse = new ApiResponse<>(ProductFamily.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }


    public ApiResponse update(int idProductFamily, String name) {

        TreeMap<String,String> map = new TreeMap<>();
        map.put("name", name);

        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTFAMILY, EnumREST.SET), String.valueOf(idProductFamily));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<ProductFamily> providerApiResponse = new ApiResponse<>(ProductFamily.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse remove(int idProductFamily) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTFAMILY, EnumREST.REMOVE), String.valueOf(idProductFamily));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductFamily> providerApiResponse = new ApiResponse<>(ProductFamily.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }


    public ApiResponse get(int idProductFamily) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTFAMILY, EnumREST.GET), String.valueOf(idProductFamily));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductFamily> providerApiResponse = new ApiResponse<>(ProductFamily.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse getGroups(int idProductFamily) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTFAMILY, EnumREST.GETGROUPS), String.valueOf(idProductFamily));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductFamily> providerApiResponse = new ApiResponse<>(ProductFamily.class);
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
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTFAMILY, EnumREST.SEARCH,EnumREST.NAME), value);
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductFamilyList> providerApiResponse = new ApiResponse<>(ProductFamilyList.class);
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
