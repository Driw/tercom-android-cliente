package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Manufacture;
import br.com.tercom.Entity.OrderRequest;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class OrderRequestControl extends GenericControl {

    private Activity activity;
    public OrderRequestControl(Activity activity) { this.activity = activity; }

    public ApiResponse add(double budget, String expiration){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("budget", String.valueOf(budget));
        map.put("expiration", expiration);
        try{
            String link = getBase(EnumREST.SITE, EnumREST.ORDERREQUEST, EnumREST.ADD);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<OrderRequest> providerApiResponse = new ApiResponse<>(OrderRequest.class);
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
