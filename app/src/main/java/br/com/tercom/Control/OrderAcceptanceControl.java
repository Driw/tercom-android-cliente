package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.OrderAcceptance;
import br.com.tercom.Entity.OrderItemProductList;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class OrderAcceptanceControl extends GenericControl {
    private Activity activity;
    public OrderAcceptanceControl(Activity activity) { this.activity = activity; }

    public ApiResponse add(int idOrderQuote, int idAddress, String observations){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("idOrderQuote", String.valueOf(idOrderQuote));
        map.put("idAddress", String.valueOf(idAddress));
        map.put("observations", observations);
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERACCEPTANCE, EnumREST.ADD), String.valueOf(idOrderQuote));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<OrderAcceptance> providerApiResponse = new ApiResponse<>(OrderAcceptance.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }

    }


    public ApiResponse serviceAdd(int idAcceptance, int idOrderQuote,int subprice, String observations){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("idAcceptance", String.valueOf(idAcceptance));
        map.put("idOrderQuote", String.valueOf(idOrderQuote));
        map.put("subprice", String.valueOf(subprice));
        map.put("observations", observations);
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERACCEPTANCE, EnumREST.ADD), String.valueOf(idOrderQuote));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<OrderAcceptance> providerApiResponse = new ApiResponse<>(OrderAcceptance.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }

    }



    public ApiResponse productAdd(int idAcceptance, int idOrderQuote, int qtd,int subprice, String observations){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("idAcceptance", String.valueOf(idAcceptance));
        map.put("idOrderQuote", String.valueOf(idOrderQuote));
        map.put("qtd", String.valueOf(qtd));
        map.put("subprice", String.valueOf(subprice));
        map.put("observations", observations);
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERACCEPTANCEPRODUCT, EnumREST.ADD), String.valueOf(idOrderQuote));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<OrderAcceptance> providerApiResponse = new ApiResponse<>(OrderAcceptance.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }

    }


    public ApiResponse getAllProducts(int idAcceptance){
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERACCEPTANCEPRODUCT, EnumREST.GETALL), String.valueOf(idAcceptance));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<OrderAcceptance> orderApiResponse = new ApiResponse<>(OrderAcceptance.class);
            if(jsonResult.first){
                orderApiResponse = populateApiResponse(orderApiResponse,jsonResult.second);
            }
            return orderApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse getAllServices(int idAcceptance){
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERACCEPTANCESERVICE, EnumREST.GETALL), String.valueOf(idAcceptance));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<OrderAcceptance> orderApiResponse = new ApiResponse<>(OrderAcceptance.class);
            if(jsonResult.first){
                orderApiResponse = populateApiResponse(orderApiResponse,jsonResult.second);
            }
            return orderApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse approve(int idOrderAcceptance) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERACCEPTANCE, EnumREST.APPROVE), String.valueOf(idOrderAcceptance));
            CustomPair<String> jsonResult = callJson(EnumMethod.GET, activity, link);
            ApiResponse<OrderAcceptance> apiResponse = new ApiResponse<>(OrderAcceptance.class);
            if(jsonResult.first) {
                apiResponse = populateApiResponse(apiResponse, jsonResult.second);
            }
            return apiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

}
