package br.com.tercom.Util;

import android.app.Activity;
import android.util.Log;
import android.util.Pair;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import static br.com.tercom.Util.Util.isNetworkAvailable;
import static br.com.tercom.Util.Util.print;


/**
 * Created by Trabalho on 2/5/2018.
 */

public class HttpUtil extends Activity {

    public String httpConnectionGET(String url, Activity activity){

        String returnWebService = "";

        if(isNetworkAvailable(activity)) {
            HttpURLConnection urlConnection = null;

            try {
                Log.i("link", url);
                URL u = new URL(url);
                urlConnection = (HttpURLConnection) u.openConnection();
                urlConnection.setRequestMethod("GET");
                urlConnection.setUseCaches(false);
                urlConnection.setConnectTimeout(15000);
                urlConnection.connect();

                int HttpResult = urlConnection.getResponseCode();
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    returnWebService = readInput(urlConnection);
                }
                System.out.println("RETORNO DO WEB SERVICE " + returnWebService);

            }  catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            return returnWebService;
        }else{
            return null;
        }
    }


    public String httpConnectionPOST(Pair link, Activity activity) {

        String returnWebService = "";

        if(isNetworkAvailable(activity)) {
            HttpURLConnection conn  = null;

            try {
                Log.i("link", link.first.toString());
                Log.i("params", link.second.toString());
                URL u = new URL(link.first.toString());
                conn = (HttpURLConnection) u.openConnection();
                conn.setReadTimeout(15000);
                conn.setConnectTimeout(15000);
                conn.setRequestMethod("POST");
                conn.setDoInput(true);

                OutputStream os = conn.getOutputStream();
                BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
                writer.write(link.second.toString());
                writer.flush();
                writer.close();
                os.close();

                conn.connect();


                int HttpResult = conn.getResponseCode();
                print(String.valueOf(HttpResult));
                if (HttpResult == HttpURLConnection.HTTP_OK) {
                    returnWebService = readInput(conn);
                    System.out.println("RETORNO DO WEB SERVICE" + returnWebService);
                }

            }  catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (ProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (conn != null) {
                conn.disconnect();
            }
            return returnWebService;
        }else{
            return null;
        }
    }


    public String readInput(HttpURLConnection conn) throws  IOException{
        StringBuilder sb = new StringBuilder();
        BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
        String line = null;
        while ((line = br.readLine()) != null) {
            sb.append(line + "\n");
        }
        br.close();
        return sb.toString();
    }



}
