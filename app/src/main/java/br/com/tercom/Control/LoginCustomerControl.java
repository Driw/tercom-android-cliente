package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Application.AppTercom;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.LoginCustomer;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class LoginCustomerControl extends GenericControl {
    private Activity activity;

    public LoginCustomerControl(Activity activity){
        this.activity = activity;
    }

    public ApiResponse login(String email, String password){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("email", email);
        map.put("password", password);
        map.put("userAgent", "android-" + AppTercom.appVersion);

        try{
            String link = getBase(EnumREST.SITE, EnumREST.LOGINCUSTOMER, EnumREST.LOGIN);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map, true));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<LoginCustomer> providerApiResponse = new ApiResponse<>(LoginCustomer.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        }
        catch(Exception e){
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse verify(int idLogin, int idCustomerEmployee, String token){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("login_id", String.valueOf(idLogin));
        map.put("login_customerEmployee", String.valueOf(idCustomerEmployee));
        map.put("login_token", token);
        try{
            String link = getBase(EnumREST.SITE, EnumREST.LOGINCUSTOMER, EnumREST.VERIFY);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<LoginCustomer> providerApiResponse = new ApiResponse<>(LoginCustomer.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        }
        catch(Exception e){
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse logout(int idLogin, int idCustomerEmployee, String token){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("Login_id", String.valueOf(idLogin));
        map.put("login_customerEmployee", String.valueOf(idCustomerEmployee));
        map.put("login_token", token);
        try{
            String link = getBase(EnumREST.SITE, EnumREST.LOGINCUSTOMER, EnumREST.LOGOUT);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<LoginCustomer> providerApiResponse = new ApiResponse<>(LoginCustomer.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        }
        catch(Exception e){
            e.printStackTrace();
            return getErrorResponse();
        }
    }
}
