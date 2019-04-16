package br.com.tercom.Control;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.QuotedProductPrice;
import br.com.tercom.Entity.QuotedProductPriceList;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class QuotedProductPriceControl extends GenericControl {

    private Activity activity;

    public QuotedProductPriceControl(Activity activity) {
        this.activity = activity;
    }


    public ApiResponse getAll(int idProduct) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.QUOTEDPRODUCTPRICE, EnumREST.GETALL), String.valueOf(idProduct));
            CustomPair<String> jsonResult = callJson(EnumMethod.GET, activity, link);
            ApiResponse<QuotedProductPriceList> providerApiResponse = new ApiResponse<>(QuotedProductPriceList.class);
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
