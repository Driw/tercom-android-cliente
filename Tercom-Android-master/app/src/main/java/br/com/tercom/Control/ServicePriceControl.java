package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.ServicePrice;
import br.com.tercom.Entity.ServicePriceList;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class ServicePriceControl extends GenericControl {
    private Activity activity;

    public ServicePriceControl(Activity activitiy) { this.activity = activitiy; }

    public ApiResponse add(int idService, int idProvider, float price, String name, String additionalDescription){
        TreeMap<String,String> map = new TreeMap<>();
        map.put("idService", String.valueOf(idService));
        map.put("idProvider", String.valueOf(idProvider));
        map.put("name", name);
        map.put("price", String.valueOf(price));
        map.put("additionalDescription", additionalDescription);

        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.SERVICEPRICE, EnumREST.ADD), String.valueOf(idService));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<ServicePrice> servicePriceApiResponse = new ApiResponse<>(ServicePrice.class);
            if(jsonResult.first){
                servicePriceApiResponse = populateApiResponse(servicePriceApiResponse,jsonResult.second);
            }
            return servicePriceApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse set(int idServicePrice, String name, Float price, String additionalDescription)
    {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("name", name);
        map.put("price", String.valueOf(price));
        map.put("additionalDescription", additionalDescription);
        try
        {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.SERVICEPRICE, EnumREST.SET), String.valueOf(idServicePrice));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult = callJson(EnumMethod.POST, activity, completePost);
            ApiResponse<ServicePrice> servicePriceApiResponse = new ApiResponse<>(ServicePrice.class);
            if(jsonResult.first)
            {
                servicePriceApiResponse = populateApiResponse(servicePriceApiResponse, jsonResult.second);
        }
            return servicePriceApiResponse;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse remove(int idServicePrice)
    {
        try
        {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.SERVICEPRICE, EnumREST.REMOVE), String.valueOf(idServicePrice));
            CustomPair<String> jsonResult = callJson(EnumMethod.GET, activity, link);
            ApiResponse<ServicePrice> servicePriceApiResponse = new ApiResponse<>(ServicePrice.class);
            if(jsonResult.first)
            {
                servicePriceApiResponse = populateApiResponse(servicePriceApiResponse, jsonResult.second);
            }
            return servicePriceApiResponse;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse get(int idServicePrice)
    {
        try
        {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.SERVICEPRICE, EnumREST.GET), String.valueOf(idServicePrice));
            CustomPair<String> jsonResult = callJson(EnumMethod.GET, activity, link);
            ApiResponse<ServicePrice> servicePriceApiResponse = new ApiResponse<>(ServicePrice.class);
            if(jsonResult.first)
            {
                servicePriceApiResponse = populateApiResponse(servicePriceApiResponse, jsonResult.second);
            }
            return servicePriceApiResponse;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse getService(int idService)
    {
        try
        {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.SERVICEPRICE, EnumREST.GETSERVICE), String.valueOf(idService));
            CustomPair<String> jsonResult = callJson(EnumMethod.GET, activity, link);
            ApiResponse<ServicePriceList> servicePriceApiResponse = new ApiResponse<>(ServicePriceList.class);
            if(jsonResult.first)
            {
                servicePriceApiResponse = populateApiResponse(servicePriceApiResponse, jsonResult.second);
            }
            return servicePriceApiResponse;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse getProvider(int idService, int idProvider)
    {
        try
        {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.SERVICEPRICE, EnumREST.GETSERVICE), getMultiplesParameters(String.valueOf(idService), String.valueOf(idProvider)));
            CustomPair<String> jsonResult = callJson(EnumMethod.GET, activity, link);
            ApiResponse<ServicePrice> servicePriceApiResponse = new ApiResponse<>(ServicePrice.class);
            if(jsonResult.first)
            {
                servicePriceApiResponse = populateApiResponse(servicePriceApiResponse, jsonResult.second);
            }
            return servicePriceApiResponse;
        }
        catch(Exception e)
        {
            e.printStackTrace();
            return getErrorResponse();
        }
    }
}
