package br.com.tercom.Entity;


import com.google.gson.annotations.Expose;

import org.json.JSONObject;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;

public class ApiResponse<T extends GenericEntity> {


    private Class<T> clazzOfT;
    private int status;
    private String message;
    private String time;
    private T result;

    private T getInstance() throws Exception {

        return clazzOfT.newInstance();
    }

    public ApiResponse(Class<T> selectedClass)
    {
        clazzOfT = selectedClass;
    }

    public ApiResponse() { }


    public int getStatus() {
        return status;
    }

    public boolean getStatusBoolean() {
        return status == 1;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public T getResult() {
        return result;
    }

    public void setResult(String resultString) {

        try {

            result = (T) (resultString.startsWith("[") ? getInstance().toList(resultString, clazzOfT) : getInstance().toObject(resultString,clazzOfT));

        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
