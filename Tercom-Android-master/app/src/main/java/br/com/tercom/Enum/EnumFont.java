package br.com.tercom.Enum;


public enum EnumFont {

    FONT_ROBOTO_LIGHT("fonts/Roboto-Light.ttf"),
    FONT_ROBOTO_REGULAR("fonts/Roboto-Regular.ttf"),
    FONT_ROBOTO_BOLD("fonts/Roboto-Bold.ttf"),
    FONT_RNS("fonts/RNS-S__.TTF"),
    FONT_COMIC_SANS("fonts/comic_sans.ttf");


    public final String path;

    private EnumFont(String path){
        this.path = path;
    }


}
