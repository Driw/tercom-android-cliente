package br.com.tercom.Enum;

public enum BaseUrl {
    URL_DEV("http://dev.tercom.com.br/api"),
    URL_REAL("");


    public final String path;


    private BaseUrl(String path){
        this.path = path;
    }
}
