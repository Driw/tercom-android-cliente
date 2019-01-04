package br.com.tercom.Util;

import java.util.Locale;

public class TextUtil {

    public static boolean emptyValidator(String value){
        return value != null && !value.trim().isEmpty();
    }

    public static boolean cvvValidator(String cvv){
        return !cvv.isEmpty() && (cvv.length() == 3 || cvv.length() == 4);
    }

    public static boolean phoneValidator(String phone){
        return  phone != null && !phone.isEmpty() && (phone.length() == 8 || phone.length() == 9);
    }
    public static boolean dddValidator(String ddd){
        return  ddd != null && !ddd.isEmpty() && ddd.length() == 2 ;
    }

    public static boolean emailValidator(String email){
        return   email.contains("@") && email.length()>=7 ;
    }

    public static boolean nameValidator(String name){
        return !name.isEmpty() && name.length() >= 2;
    }

    public static boolean passwordValidator(String pass){
        return !pass.isEmpty() && pass.length() >= 4;
    }
    public static boolean confirmPasswordValidator(String pass,String confirmPass){
        return pass.equals(confirmPass);
    }

    public static String concatenaNumero(String[] telefones){
        if(telefones.length==1){
            return telefones[1];
        }else {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < telefones.length; i++) {
                stringBuilder.append(telefones[i]);
                if (i != (telefones.length - 1))
                    stringBuilder.append("/ ");
            }
            return stringBuilder.toString();
        }
    }

    public static boolean isInteger(String value)
    {
        try
        {
            Integer num = Integer.parseInt(value);
        }
        catch(NumberFormatException e)
        {
            return false;
        }
        return true;
    }

    public static String setCnpjMask(String cnpj){
        return String.format(Locale.US,"%s.%s.%s/%s-%s",cnpj.substring(0,2),cnpj.substring(2,5),
                cnpj.substring(5,8),cnpj.substring(8,12),cnpj.substring(12));
    }

}
