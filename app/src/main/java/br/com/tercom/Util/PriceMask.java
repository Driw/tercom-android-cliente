package br.com.tercom.Util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Locale;

public class PriceMask implements TextWatcher {

    private static final Locale DEFAULT_LOCALE = new Locale("pt", "BR");

    private static DecimalFormat NUMBER_FORMAT = (DecimalFormat) DecimalFormat.getCurrencyInstance(DEFAULT_LOCALE);

    private static final int FRACTION_DIGITS = 2;

    private static final String DECIMAL_SEPARATOR;

    private static final String CURRENCY_SIMBOL;

    static {
        NUMBER_FORMAT.setMaximumFractionDigits(FRACTION_DIGITS);
        NUMBER_FORMAT.setMaximumFractionDigits(FRACTION_DIGITS);
        NUMBER_FORMAT.setParseBigDecimal(true);
        DECIMAL_SEPARATOR = String.valueOf(NUMBER_FORMAT.getDecimalFormatSymbols().getDecimalSeparator());
        CURRENCY_SIMBOL = NUMBER_FORMAT.getCurrency().getSymbol(DEFAULT_LOCALE);
    }

    final EditText target;

    public PriceMask(EditText target) {
        this.target = target;
    }

    private boolean updating = false;

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int after) {
        if (updating) {
            updating = false;
            return;
        }

        updating = true;
        try {
            String valueStr = formatNumber(fixDecimal(s.toString()));
            BigDecimal parsedValue = ((BigDecimal) NUMBER_FORMAT.parse(valueStr));
            String value = NUMBER_FORMAT.format(parsedValue);
            target.setText(value);
            target.setSelection(value.length());
        } catch (ParseException | NumberFormatException ex) {
            throw new IllegalArgumentException("Erro ao aplicar a máscara", ex);
        }
    }

    private String formatNumber(String originalNumber) {
        String number = originalNumber.replaceAll("[^\\d]", "");
        switch(number.length()) {
            case 0 :
                number = "0" + DECIMAL_SEPARATOR + "00";
                break;
            case 1 :
                number = "0" + DECIMAL_SEPARATOR+ "0" + number;
                break;
            case 2 :
                number = "0" + DECIMAL_SEPARATOR + number;
                break;
            default:
                number =  number.substring(0, number.length() - 2) + DECIMAL_SEPARATOR + number.substring(number.length() - 2);
                break;
        }
        return CURRENCY_SIMBOL + number;
    }

    private String fixDecimal(String number) {
        int dotPos = number.indexOf('.') + 1;
        int length = number.length();
        return (length - dotPos < FRACTION_DIGITS) ? fixDecimal(number + "0") : number;
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
    }

    @Override
    public void afterTextChanged(Editable s) {
    }



    public static double unmaskPrice(String value) {
        float valueF =  Float.parseFloat(value.replace("R$", "").replace(".","").replace(",","."));
        return Double.parseDouble(String.format(Locale.US,"%.2f",valueF));
    }



}
