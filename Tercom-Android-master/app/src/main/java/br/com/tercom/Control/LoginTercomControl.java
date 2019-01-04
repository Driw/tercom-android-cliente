package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Application.AppTercom;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.LoginTercom;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class LoginTercomControl extends GenericControl {
    private Activity activity;

    public LoginTercomControl(Activity activity){
        this.activity = activity;
    }

    public ApiResponse login(String email, String password){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("email", email);
        map.put("password", password);
        //UserAgent é um valor padrão utilizado nos browsers. Alterar esse valor dependendo da resposta do Driw.
        map.put("userAgent", "android-" + AppTercom.appVersion);

        try{
            String link = getBase(EnumREST.SITE, EnumREST.LOGINTERCOM, EnumREST.LOGIN);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<LoginTercom> providerApiResponse = new ApiResponse<>(LoginTercom.class);
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

    public ApiResponse verify(int idLogin, int idTercomEmployee, String token){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("idLogin", String.valueOf(idLogin));
        map.put("idTercomEmployee", String.valueOf(idTercomEmployee));
        map.put("token", token);
        try{
            String link = getBase(EnumREST.SITE, EnumREST.LOGINTERCOM, EnumREST.VERIFY);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<LoginTercom> providerApiResponse = new ApiResponse<>(LoginTercom.class);
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

    public ApiResponse logout(int idLogin, int idTercomEmployee, String token){
        TreeMap<String, String> map = new TreeMap<>();
        map.put("idLogin", String.valueOf(idLogin));
        map.put("idTercomEmployee", String.valueOf(idTercomEmployee));
        map.put("token", token);
        try{
            String link = getBase(EnumREST.SITE, EnumREST.LOGINTERCOM, EnumREST.LOGOUT);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<LoginTercom> providerApiResponse = new ApiResponse<>(LoginTercom.class);
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
