package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.OrderAcceptance;
import br.com.tercom.Entity.OrderAcceptanceProduct;
import br.com.tercom.Entity.OrderAcceptanceProductPrice;
import br.com.tercom.Entity.OrderAcceptanceProductPriceList;
import br.com.tercom.Entity.OrderAcceptanceService;
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


    public ApiResponse serviceAdd(int idAcceptance, int idService,int amount,int subprice, String observations){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("idAcceptance", String.valueOf(idAcceptance));
        map.put("idQuotedServicePrice", String.valueOf(idService));
        map.put("amountRequest", String.valueOf(amount));
        map.put("observations", observations);
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERACCEPTANCESERVICE, EnumREST.ADD),String.valueOf(idAcceptance)+"/"+String.valueOf(idService));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<OrderAcceptanceService> providerApiResponse = new ApiResponse<>(OrderAcceptanceService.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }

    }


    public ApiResponse productAdd(int idAcceptance, int idProduct, int qtd,int subprice, String observations){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("idAcceptance", String.valueOf(idAcceptance));
        map.put("idQuotedProductPrice", String.valueOf(idProduct));
        map.put("amountRequest", String.valueOf(qtd));
        map.put("observations", observations);
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERACCEPTANCEPRODUCT, EnumREST.ADD), String.valueOf(idAcceptance)+"/"+String.valueOf(idProduct));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<OrderAcceptanceProduct> providerApiResponse = new ApiResponse<>(OrderAcceptanceProduct.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }

            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }

    }

    public ApiResponse getAllProducts(int idOrderRequest, int idOrderItemProduct){
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.QUOTEDPRODUCTPRICE, EnumREST.GETALL), String.valueOf(idOrderRequest)+"/"+String.valueOf(idOrderItemProduct));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<OrderAcceptanceProductPriceList> orderApiResponse = new ApiResponse<>(OrderAcceptanceProductPriceList.class);
            if(jsonResult.first){
                orderApiResponse = populateApiResponse(orderApiResponse,jsonResult.second);
            }
            return orderApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse getAllServices(int idOrderRequest, int idOrderItemProduct){
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.QUOTEDSERVICEPRICE, EnumREST.GETALL), String.valueOf(idOrderRequest)+"/"+String.valueOf(idOrderItemProduct));
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

    public ApiResponse set(int idOrderAcceptance, String observations) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("idOrderAcceptance", String.valueOf(idOrderAcceptance));
        map.put("observations", observations);
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERACCEPTANCE, EnumREST.SET), String.valueOf(idOrderAcceptance));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult = callJson(EnumMethod.POST, activity, completePost);
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

    public ApiResponse setAddress(int idOrderAcceptance, int idAddress, String state, String city, String cep, String neighborhood, String street, int number, String complement) {
        TreeMap<String, String> map = new TreeMap<>();
        map.put("idOrderAcceptance", String.valueOf(idOrderAcceptance));
        map.put("idAddress", String.valueOf(idAddress));
        map.put("state", state);
        map.put("city", city);
        map.put("cep", cep);
        map.put("neighborhood", neighborhood);
        map.put("street", street);
        map.put("number", String.valueOf(number));
        map.put("complement", complement);
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERACCEPTANCE, EnumREST.SETADDRESS), String.valueOf(idOrderAcceptance));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult = callJson(EnumMethod.POST, activity, completePost);
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
