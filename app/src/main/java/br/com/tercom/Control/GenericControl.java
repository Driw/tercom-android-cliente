package br.com.tercom.Control;

import android.app.Activity;
import android.support.annotation.Nullable;
import android.util.Pair;

import com.google.gson.Gson;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;

import br.com.tercom.Application.AppTercom;
import br.com.tercom.Entity.ApiResponse;
import br.com.tercom.Entity.GenericEntity;
import br.com.tercom.Entity.Provider;
import br.com.tercom.Entity.ProviderContact;
import br.com.tercom.Enum.BaseUrl;
import br.com.tercom.Enum.EnumMethod;
import br.com.tercom.Enum.EnumREST;
import br.com.tercom.Util.CustomPair;
import br.com.tercom.Util.HttpUtil;

import static br.com.tercom.Util.HMAC.encrypt;
import static br.com.tercom.Util.Util.getTimeStampFormated;

/**
 * <b>GenericControl</b> é uma classe abstrata feita para suprir todas necessidades em uma chamada de webservice, trazendo métodos para criar a url e adicionar os parâmetros;
 * Pode ser usado em GET ou POST, os métodos se adaptam aos diversos tipos de chamadas.
 * Pode ser trabalhando em arquiteturas SOAP ou REST.
 * @author Felipe
 * @version 2.0
 */

public abstract class GenericControl {

    private final static  int API_FAILURE = 00;
    private final static  int API_SUCCESS = 01;
    private final static  int API_PHP_FATAL_ERROR = 97;
    private final static  int API_ERROR_EXCEPTION = 98;
    private final static  int API_ERROR_API_EXCEPTION = 99;

    private final static  boolean RETURN_SUCESS = true;
    private final static  boolean RETURN_FAIL = false;

    /**
     * A URL_BASE é um atributo que contém a base do header da chamada.
     * @see BaseUrl é uma enum que tem a base da url, sendo ela de dev ou a real(mais tipos podem ser adicionados se necessário)
     */
    private final  String URL_BASE = BaseUrl.URL_DEV.path;


    /**
     * Usado para pegar a base das informações do usuário(Login e senha), que será necessário para fazer chamadas que terão como objetivo obter informações do usuário em questão.
     * @return Retorna um array de String com as informações pertinentes ao usuário em questão.
     */

    protected  String[] getValuesBase() {
        return new String[] {AppTercom.USER_STATIC.getTercomEmployee().getEmail(), AppTercom.USER_STATIC.getTercomEmployee().getPassword()};
    }

    /**
     * Webservice feito para formar o header usando padrão RESTFUL.
     * @param types 1...* valores da enum EnumWebServices, ajudando a formar o header da chamada.
     * @return Retorna uma String com a base do header. Sendo formado por url base + caminho.
     */

    protected  String getBase(EnumREST... types)
    {
        StringBuilder type = new StringBuilder();

        for(EnumREST ws : types) {
            type.append("/");
            type.append(ws.path);
        }

        return String.format(Locale.US,"%s%s",URL_BASE, type);
    }


    /**
     * Forma final da chamada, adicionando parâmetros necessários ainda na url, caso haja necessidade;
     * @param base A base é formada pelo método getBase que trará a url base que estará sendo usada no momento somado de webservices formando o caminho completo;
     * @param params O parâmetro trará qualquer informação extra variável, que pode ser necessário para completar a chamada.
     * @return Retorna a url completa que será enviada na chamada do Webservice.
     */
    protected  String getLink(String base, String params){
        StringBuilder paramBuilder = new StringBuilder();
        if(params != null && !params.trim().isEmpty()) {
            paramBuilder.append("/");
            paramBuilder.append(params);
        }

        return String.format(Locale.US,"%s%s",base,paramBuilder.toString());
    }



    /**
     * retorna o token com todos parâmetros
     * @param treeMap um map que terá cada chave e valor que será enviado no post;
     * @see br.com.tercom.Util.HMAC classe onde o encrypt é feito.
     * @return Retorna uma string com os parâmetros do post feitos, incluindo o token criptografado.
     */

    protected String getPostValues(@Nullable TreeMap<String,String> treeMap) throws UnsupportedEncodingException {

        StringBuilder valuesBuilder = new StringBuilder();

        int position  = 0;
        int last = treeMap.size()-1;
        for (Map.Entry<String,String> entry : treeMap.entrySet()) {
            valuesBuilder.append(createField(entry.getKey(),URLEncoder.encode(entry.getValue(),"UTF-8")));
            if(position != last) {
                valuesBuilder.append("&");
                position++;
            }

        }

        return valuesBuilder.toString();
    }


    /**
     *
     * @param key chave do array
     * @param values os valores do array sendo campo/valor
     * @return Um treemap para ser enviado no post
     */

    protected Map<String,String> getArrayParams(String key, ArrayList<Pair<String,String>> values){
        Map<String,String> map = new TreeMap<>();
        for(int i = 0; i< values.size(); i++)
            map.put(String.format(Locale.US,"%s[%s]",key,values.get(i).first),values.get(i).second);

        return map;
    }




    /**
     * Usado em GET e POST, ele gera os parametros baseado no formato (valor_valor) e retorna uma string completa com todos valores do array.
     * @param values É um array de strings que contém todos os valores necessários para a chamada.
     * @param separator será o caractér de escape para informar que a partir dele será um novo parâmetro
     * @return String concatenada com todos os valores contidos em values separados pelo separator.
     * @throws UnsupportedEncodingException Se faz necessário para previnir erros quando o URLEncoder estiver em ação
     * @see URLEncoder classe usda para converter o valor em UTF-8 afim de evitar erros quando recebido no webservice.
     * @method encode usado para evitar que algum parâmetro seja inválido, independete do caractér existente nele. Padrão usado: UTF-8
     */

    protected  String generateParams(String[] values,String separator) throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();

        for (int i= 0;i<values.length;i++) {

            result.append(URLEncoder.encode(values[i], "UTF-8"));


            if(i != values.length-1)
                result.append(separator);

        }

        return result.toString();
    }


    /**
     * Retorna uma GenericEntity feita para caso não haja nenhum retorno do webservice.
     * @return Generic entity preenchida para um erro padrão
     */

    protected ApiResponse getErrorResponse(){
        ApiResponse apiResponse =  new ApiResponse<>();
        apiResponse.setMessage("Erro");
        apiResponse.setStatus(0);
        apiResponse.setResult(null);
        return apiResponse;
    }


    /**
     *  Usado em GET e POST, ele gera os parametros baseado no formato formato (key=valor&key=valor). Usando o "&" como separador padrão;
     * @param keys É um array de Strings que contém todas as chaves(key) dos valores que serão citados a seguir. Segue as respectivas posições do array(1-1...);
     * @param values  É um array de strings que contém todos os valores necessários para a chamada.
     * @return Retorna uma string concatenada com todos valores e chaves dos arrays combinados.
     * @throws UnsupportedEncodingException Se faz necessário para previnir erros quando o URLEncoder estiver em ação
     * @method URLEncoder.encode usado para evitar que algum parâmetro seja inválido, independete do caractér existente nele. Padrão usado: UTF-8
     */
    protected  String generateParamsWithKey(String[] keys, String[] values) throws UnsupportedEncodingException {

        StringBuilder result = new StringBuilder();

        for (int i= 0;i<values.length;i++) {
            result.append(createField(keys[i] ,URLEncoder.encode(values[i], "UTF-8")));
            if(i != values.length-1) result.append("&");
        }

        return result.toString();
    }

    /**
     *  Identifica a página que será requisitada, usada normalmente em chamadas de busca de espetáculos, sendo a página da lista que deverá ser requisitada
     * @param page número da página requisitada (int)
     * @return retorna um padrão pagina=valor
     */

    protected  String setPage(int page){
        return String.format(Locale.US,"pagina=%d",page);
    }

    /**
     * Esse método é usado para fazer as chamadas dos webservices, seja em <b>GET</b> ou <b>POST</b>/ <b>Array</b> ou <b>Object</b>.
     * Ele irá reconhecer e trazer um retorno apropriado ao que foi recebido.
     * Espera um status que dirá se o webservice foi ok ou teve algum problema durante a chamada.
     * @param method define se a chamada vai ser feita em GET ou POST, baseado no method, o método interpretará o parâmetro link de uma maneira diferente.
     * @param activity usado para a verificações de internet dentro do método de conexão;
     * @param link e formada pela url e os parâmetros da chamada. Ele considera o parâmetro method para traduzir esse objeto de formas diferentes, caso o parâmetro seja GET, ele considera
     *             uma String, caso seja POST, ele considera que o objeto é um <b>CustomPair</b>, sendo que o <i>first</i> seria o header e o <i>second</i> seria os parâmetros que iriam pelo post.
     * @return Esse método retorna um CustomPair em que o primeiro atributo é boolean e o segundo é um objet podendo ser um JSONObject, JSONArray ou uma String.
     * O primeiro atributo retorna true caso o retorno tenha sido como esperado e false caso tenha dado algum erro.
     * Caso tenha sido false, ele espera uma mensagem do webservice, caso não venha ele coloca uma mensagem padrão de erro de download dos dados.(Será colocado no second)
     * Caso o retorno tenha sido true, quer dizer que o valor esperado foi obtido e ele trará no second;
     * Os valores retornados dele serão tratados fora desse método, deverão ser tratados onde forem chamados.
     * @see EnumMethod EnumMethod: essa enum é usada para definir o tipo de chamada que será feito, pode ser selecionado GET ou POST e é o valor a ser passado em <i>method</i>
     */

    protected CustomPair<String> callJson(EnumMethod method, Activity activity, Object link){

        String jsonCalled = "";
        CustomPair customPair = new CustomPair(false,getGenericErrorObject());

        try{
            if(method == EnumMethod.GET) {
                jsonCalled = new HttpUtil().httpConnectionGET((String)link, activity);
            }
            if(method == EnumMethod.POST) {
                jsonCalled = new HttpUtil().httpConnectionPOST((Pair) link, activity);
            }

            if( !jsonCalled.trim().isEmpty()) {

                if (jsonCalled.startsWith("[")) {

                    JSONArray jsonArray = new JSONArray(jsonCalled);

                    if(jsonArray.length()!= 0) {
                        customPair = new CustomPair<>(RETURN_SUCESS,jsonArray.toString());
                    }
                }
                else {
                    JSONObject jsonObject = new JSONObject(jsonCalled);
                    customPair = new CustomPair<>(RETURN_SUCESS,jsonObject.toString());
                }
            }
            return customPair;
        }catch (Exception e){
            e.printStackTrace();
            return customPair;
        }
    }


    /**
     * Método usado para gerar um retorno genérico caso não seja possível pegar o retorno do Json
     * @return retorna um json do objeto ApiResponse com o erro;
     */

    private String getGenericErrorObject(){
        ApiResponse response = new ApiResponse<>();
        response.setStatus(100);
        response.setMessage("Não foi possível completar a ação");
        return new Gson().toJson(response);
    }

    /**
     * Popula o objeto de ApiResponse passado com os valores do Json;
     * @param response objeto de ApiResponse a ser populado;
     * @param result json que terá os dados para popular o response
     * @return um objeto de ApiResponse populado com os dados que foram passados do Json
     */

    protected ApiResponse  populateApiResponse(ApiResponse response, String result){
        try {
            JSONObject jsonObject = new JSONObject(result);
            response.setStatus(jsonObject.getInt("status"));
            response.setMessage(jsonObject.getString("message"));
            response.setTime(jsonObject.getString("time"));
            response.setResult(result);
        }catch (Exception e){
            e.printStackTrace();
        }
        return response;
    }

    /**
     * Recebe um json em formato string e a entidade referente a ele e retorna a entidade preenchida pelos seus respectivos campos no json.
     * @param json String do json recebido.
     * @param selectedClass Define a entidade que o gson retornará.
     * @param <T> Entidade que será definida em selectedClass
     * @return Retorna a entidade definida em <b>selectedClass</b> preenchida com os seus reespectivos atributos do json.
     */

    //NÃO SENDO UTILIZADO
    protected <T>  T getItem(String json, Class<T> selectedClass){
        return new Gson().fromJson(json,selectedClass);
    }


    /**
     * Recebe um jsonArray em formato string e a entidade referente a ele e retorna um array da entidade preenchida pelos seus respectivos campos no json.
     * @param json String do json recebido.
     * @param selectedClass Define a entidade que o gson retornará.
     * @param <T> Entidade que será definida em selectedClass
     * @return Retorna um array da entidade definida em <b>selectedClass</b> preenchida com os seus reespectivos atributos do json.
     */

    //NÃO SENDO UTILIZADO
    protected <T> ArrayList<T> getItems(String json, Class<T> selectedClass){
        ArrayList<T> values = new ArrayList<>();
        try {
            JSONArray jsonElements = new JSONArray(json);

            for(int i =0; i<jsonElements.length();i++)
                values.add(getItem(jsonElements.getString(i),selectedClass));

            return values;
        } catch (JSONException e) {
            e.printStackTrace();
            return values;
        }
    }



    /**
     * Esse método gera um parametro baseado na estrutura chave=valor e retorna a String
     * @param key chave que será enviado (ex:email, telefone etc)
     * @param result valor da chave em questão
     * @return uma String formada por key=result
     */

    protected   String createField(String key, String result){
        return String.format(Locale.US,"%s=%s",key,result);
    }

    protected String getMultiplesParameters(String... param){
        StringBuilder sb = new StringBuilder();
        for(int i = 0; i< param.length; i++){

            sb.append(param[i]);

            if(i == (param.length-1))
                sb.append("/");
        }

        return sb.toString();
    }




}
