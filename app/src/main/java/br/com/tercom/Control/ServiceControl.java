package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.ArrayList;
import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProductList;
import br.com.tercom.Entity.ProductSend;
import br.com.tercom.Entity.Services;
import br.com.tercom.Entity.ServicesList;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class ServiceControl extends GenericControl {

    private Activity activity;

    public ServiceControl(Activity activity) {
        this.activity = activity;
    }

    public ApiResponse add(String name, String description, ArrayList<String> tags) {

        TreeMap<String,String> map = new TreeMap<>();
        map.put("name", name);
        map.put("description", description);
        map.put("tags", createTags(tags));


        try {
            String link = getBase(EnumREST.SITE, EnumREST.SERVICE, EnumREST.ADD);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<Services> providerApiResponse = new ApiResponse<>(Services.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    private String createTags(ArrayList<String> values){
        StringBuilder stringBuilder = new StringBuilder();
        for(int i = 0; i < values.size(); i++ ){
            stringBuilder.append(values.get(i));
            if(i != (values.size()-1))
                stringBuilder.append(";");
        }
        return stringBuilder.toString();
    }


    public ApiResponse update(int idService, String name, String description, ArrayList<String> tags) {


        TreeMap<String,String> map = new TreeMap<>();
        map.put("name", name);
        map.put("description", description);
        map.put("tags", createTags(tags));

        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.SERVICE, EnumREST.SET), String.valueOf(idService));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<Services> providerApiResponse = new ApiResponse<>(Services.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse get(int idService) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.SERVICE, EnumREST.GET), String.valueOf(idService));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<Services> providerApiResponse = new ApiResponse<>(Services.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse getAll(int idService) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.SERVICE, EnumREST.GETALL), String.valueOf(idService));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ServicesList> providerApiResponse = new ApiResponse<>(ServicesList.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse search(String value, EnumREST filter) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.SERVICE,EnumREST.SEARCH,filter), value);
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ServicesList> providerApiResponse = new ApiResponse<>(ServicesList.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }



    public ApiResponse avaliable(String value, EnumREST filter) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.SERVICE, EnumREST.SEARCH,filter), value);
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<Services> providerApiResponse = new ApiResponse<>(Services.class);
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
