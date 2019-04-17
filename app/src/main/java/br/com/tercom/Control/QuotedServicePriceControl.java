package br.com.tercom.Control;

import android.app.Activity;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.QuotedProductPriceList;
import br.com.tercom.Entity.QuotedServicePrice;
import br.com.tercom.Entity.QuotedServicePriceList;
import br.com.tercom.Entity.ServicePrice;
import br.com.tercom.Entity.ServicePriceList;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class QuotedServicePriceControl extends GenericControl {

    private Activity activity;

    public QuotedServicePriceControl(Activity activity) {
        this.activity = activity;
    }


    public ApiResponse getAll(int idProduct) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.QUOTEDSERVICEPRICE, EnumREST.GETALL), String.valueOf(idProduct));
            CustomPair<String> jsonResult = callJson(EnumMethod.GET, activity, link);
            ApiResponse<ServicePriceList> providerApiResponse = new ApiResponse<>(ServicePriceList.class);
            if (jsonResult.first) {
                providerApiResponse = populateApiResponse(providerApiResponse, jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }
}
