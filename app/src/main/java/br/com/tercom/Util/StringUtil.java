package br.com.tercom.Util;

public class StringUtil {

    public static String simplifyArray(String[] array){
        StringBuilder stringBuilder = new StringBuilder();
        for (String value : array) {
            stringBuilder.append(value + ";\n");
        }
        return stringBuilder.toString();
    }

    public static String[] splitComments(String comments){
        return comments.split(";");
    }


}
