package br.com.tercom.Enum;

import br.com.tercom.R;

public enum EnumComponent {

     COMPONENT_SQUARE(R.layout.component_square_menu),
     COMPONENT_REC_DOUBLE_LINE(R.layout.component_rec_double_line),
     COMPONENT_REC_SINGLE(R.layout.component_rec_single_line),
     COMPONENT_BUTTON(R.layout.component_button),
     COMPONENT_MINI_SQUARE(R.layout.component_mini_square),
     COMPONENT_TITLE_TEXT(R.layout.component_rec_title_text),
     COMPONENT_REC_VARIABLE(R.layout.component_rec_variable),
     COMPONENT_TITLE_LINE(R.layout.component_title_line);


    public final int component;

    private EnumComponent(int component){
        this.component = component;
    }


}
