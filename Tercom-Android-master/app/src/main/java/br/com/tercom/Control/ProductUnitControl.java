package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.ProductUnit;
import br.com.tercom.Entity.ProductUnitList;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class ProductUnitControl extends GenericControl {

    private Activity activity;

    public ProductUnitControl(Activity activity) {
        this.activity = activity;
    }


    public ApiResponse add(String name, String shortName) {

        TreeMap<String,String> map = new TreeMap<>();
        map.put("name", name);
        map.put("shortName", shortName);

        try {
            String link = getBase(EnumREST.SITE, EnumREST.PRODUCTUNIT, EnumREST.ADD);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<ProductUnit> providerApiResponse = new ApiResponse<>(ProductUnit.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }


    public ApiResponse update(int idProductUnit, String name, String shortName) {

        TreeMap<String,String> map = new TreeMap<>();
        map.put("name", name);
        map.put("shortName", shortName);

        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTUNIT, EnumREST.SET), String.valueOf(idProductUnit));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<ProductUnit> providerApiResponse = new ApiResponse<>(ProductUnit.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse remove(int idProductUnit) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTUNIT, EnumREST.REMOVE), String.valueOf(idProductUnit));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductUnit> providerApiResponse = new ApiResponse<>(ProductUnit.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }


    public ApiResponse get(int idProductUnit) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTUNIT, EnumREST.GET), String.valueOf(idProductUnit));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductUnit> providerApiResponse = new ApiResponse<>(ProductUnit.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse getAll() {
        try {
            String link = getBase(EnumREST.SITE, EnumREST.PRODUCTUNIT, EnumREST.GETALL);
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductUnitList> providerApiResponse = new ApiResponse<>(ProductUnitList.class);
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
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTUNIT, EnumREST.SEARCH,EnumREST.NAME), value);
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductUnitList> providerApiResponse = new ApiResponse<>(ProductUnitList.class);
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
