package br.com.tercom.Control;

import android.app.Activity;
import android.util.Pair;

import java.util.TreeMap;

import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProductList;
import br.com.tercom.Entity.ProductSend;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;

public class ProductControl extends GenericControl {

    private Activity activity;

    public ProductControl(Activity activity) {
        this.activity = activity;
    }

    public ApiResponse add(ProductSend product) {

        TreeMap<String,String> map = new TreeMap<>();
        map.put("name", product.getName());
        map.put("description", product.getDescription());
        map.put("utility", product.getUtility());
        map.putAll(product.getTreeMap());
       

        try {
            String link = getBase(EnumREST.SITE, EnumREST.PRODUCT, EnumREST.ADD);
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<Product> providerApiResponse = new ApiResponse<>(Product.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }


    public ApiResponse update(int idProduct,String name,String description, String utility,TreeMap<String, String> mapValues) {

        TreeMap<String,String> map = new TreeMap<>();
        map.put("name", name);
        map.put("description",description);
        map.put("utility",utility);
        map.putAll(mapValues);

        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCT, EnumREST.SET), String.valueOf(idProduct));
            Pair<String, String> completePost = new Pair<>(link, getPostValues(map));
            CustomPair<String> jsonResult =  callJson(EnumMethod.POST,activity,completePost);
            ApiResponse<Product> providerApiResponse = new ApiResponse<>(Product.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse get(int idProduct) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCT, EnumREST.GET), String.valueOf(idProduct));
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<Product> providerApiResponse = new ApiResponse<>(Product.class);
            if(jsonResult.first){
                providerApiResponse = populateApiResponse(providerApiResponse,jsonResult.second);
            }
            return providerApiResponse;
        } catch (Exception e) {
            e.printStackTrace();
            return getErrorResponse();
        }
    }

    public ApiResponse search(String value, EnumREST filter) {
        try {
            String link = getLink(getBase(EnumREST.SITE, EnumREST.PRODUCT, EnumREST.SEARCH,filter), value);
            CustomPair<String> jsonResult =  callJson(EnumMethod.GET,activity,link);
            ApiResponse<ProductList> providerApiResponse = new ApiResponse<>(ProductList.class);
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
