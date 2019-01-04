package br.com.tercom.Enum;


import br.com.tercom.R;

public enum EnumDialogOptions {

    CONFIRM(R.drawable.ic_confirmed),
    FAIL(R.drawable.ic_refused);


    public final int image;

    private EnumDialogOptions(int image){
        this.image = image;
    }

}
