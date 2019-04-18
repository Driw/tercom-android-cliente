package br.com.tercom.Control;

import android.app.Activity;

import br.com.tercom.Entity.AddressList;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Customer;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class AddressControl extends GenericControl {

    private Activity activity;

    public AddressControl(Activity activity) {
        this.activity = activity;
    }


    public ApiResponse getAll(int idCostumer) {
        try {
            String link = getLink(getBase(EnumREST.SITE,EnumREST.ADDRESS, EnumREST.GETALL, EnumREST.CUSTOMER), String.valueOf(idCostumer));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<AddressList> providerApiResponse = new ApiResponse<>(AddressList.class);
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





