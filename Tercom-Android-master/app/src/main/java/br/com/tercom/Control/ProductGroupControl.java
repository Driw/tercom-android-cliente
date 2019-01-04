package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.ProductGroup;
import br.com.tercom.Entity.ProductGroupList;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class ProductGroupControl extends GenericControl {

    private Activity activity;

    public ProductGroupControl(Activity activity) {
        this.activity = activity;
    }


    public ApiResponse add(int idProductFamily, String name) {

        TreeMap<String,String> map = new TreeMap<>();
        map.put("name", name);
        map.put("idProductFamily", String.valueOf(idProductFamily));

        try {
            String link = getBase(EnumREST.SITE, EnumREST.PRODUCTGROUP, EnumREST.ADD);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<ProductGroup> providerApiResponse = new ApiResponse<>(ProductGroup.class);
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
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTGROUP, EnumREST.SET), String.valueOf(idProductFamily));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<ProductGroup> providerApiResponse = new ApiResponse<>(ProductGroup.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse remove(int idProductGroup) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTGROUP, EnumREST.REMOVE), String.valueOf(idProductGroup));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductGroup> providerApiResponse = new ApiResponse<>(ProductGroup.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }


    public ApiResponse get(int idProductGroup) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTGROUP, EnumREST.GET), String.valueOf(idProductGroup));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductGroup> providerApiResponse = new ApiResponse<>(ProductGroup.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse getSubGroups(int idProductGroup) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTGROUP, EnumREST.GETPRODUCTSUBGROUP), String.valueOf(idProductGroup));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductGroup> providerApiResponse = new ApiResponse<>(ProductGroup.class);
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
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCTGROUP, EnumREST.SEARCH,EnumREST.NAME), value);
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductGroupList> providerApiResponse = new ApiResponse<>(ProductGroupList.class);
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
