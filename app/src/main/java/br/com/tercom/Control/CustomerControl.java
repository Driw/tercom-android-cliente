package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Customer;
import br.com.tercom.Entity.Manufacture;
import br.com.tercom.Entity.ManufactureList;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class CustomerControl extends GenericControl {

    private Activity activity;

    public CustomerControl(Activity activity) {
        this.activity = activity;
    }


    public ApiResponse get(int idCostumer) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.CUSTOMER, EnumREST.GET), String.valueOf(idCostumer));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<Customer> providerApiResponse = new ApiResponse<>(Customer.class);
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





