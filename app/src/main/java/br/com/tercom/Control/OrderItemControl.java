package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.OrderItemProduct;
import br.com.tercom.Entity.OrderItemProductList;
import br.com.tercom.Entity.OrderItemService;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class OrderItemControl extends GenericControl {
    private Activity activity;

    public OrderItemControl(Activity activity) { this.activity = activity; }

    public ApiResponse addProduct(int idOrderRequest, int idProduct, int idProvider, int idManufacturer, String observation, boolean betterPrice){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("idOrderRequest", String.valueOf(idOrderRequest));
        map.put("idProduct", String.valueOf(idProduct));
        map.put("idProvider", String.valueOf(idProvider));
        map.put("idManufacturer", String.valueOf(idManufacturer));
        map.put("observation", observation);
        map.put("betterPrice", String.valueOf(betterPrice));
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERITEMPRODUCT, EnumREST.ADD), String.valueOf(idOrderRequest));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<OrderItemProduct> orderApiResponse = new ApiResponse<>(OrderItemProduct.class);
            if(jsonResult.first){
                orderApiResponse = populateApiResponse(orderApiResponse,jsonResult.second);
            }
            return orderApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }

    }

    public ApiResponse addService(int idOrderRequest, int idService, int idProvider, String observations, boolean betterPrice){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("idOrderRequest", String.valueOf(idOrderRequest));
        map.put("idService", String.valueOf(idService));
        map.put("idProvider", String.valueOf(idProvider));
        map.put("observations", observations);
        map.put("betterPrice", String.valueOf(betterPrice));
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERITEMSERVICE, EnumREST.ADD), String.valueOf(idOrderRequest));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<OrderItemService> orderApiResponse = new ApiResponse<>(OrderItemService.class);
            if(jsonResult.first){
                orderApiResponse = populateApiResponse(orderApiResponse,jsonResult.second);
            }
            return orderApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }

    }

    public ApiResponse getAllProducts(int idOrderRequest){
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERITEMSERVICE, EnumREST.ADD), String.valueOf(idOrderRequest));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,link);
            ApiResponse<OrderItemProductList> orderApiResponse = new ApiResponse<>(OrderItemProductList.class);
            if(jsonResult.first){
                orderApiResponse = populateApiResponse(orderApiResponse,jsonResult.second);
            }
            return orderApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse getAllServices(int idOrderRequest){
        try{
            String link = getLink(getBase(EnumREST.SITE, EnumREST.ORDERITEMSERVICE, EnumREST.ADD), String.valueOf(idOrderRequest));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,link);
            ApiResponse<OrderItemProductList> orderApiResponse = new ApiResponse<>(OrderItemProductList.class);
            if(jsonResult.first){
                orderApiResponse = populateApiResponse(orderApiResponse,jsonResult.second);
            }
            return orderApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

}
