package br.com.tercom.Boundary.BoundaryUtil.Component;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;


import br.com.tercom.Enum.EnumComponent;
import br.com.tercom.Enum.EnumFont;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.tercom.Util.CustomTypeFace.overrideFonts;


public class ViewComponent {

    private Activity activity;

    @BindView(R.id.frameSessao)
    TextView textComponent;
    @BindView(R.id.imageComponent)
    ImageView imageComponent;

    public ViewComponent(Activity activity){
        this.activity = activity;
    }

    public View addComponent(EnumComponent type, int image, String text){
        Resources res = activity.getResources();
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(type.component, null, true);
        ButterKnife.bind(this,view);
        overrideFonts(activity,view, EnumFont.FONT_ROBOTO_REGULAR);
        imageComponent.setImageBitmap(BitmapFactory.decodeResource(res,image));
        textComponent.setText(text);
        textComponent.setTextColor(Color.BLACK);
        return view;
    }

    public View addComponent(EnumComponent type, int image, String text, int bgColor){
        Resources res = activity.getResources();
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(type.component, null, true);
        ButterKnife.bind(this,view);
        view.setBackgroundColor(bgColor==0? activity.getResources().getColor(R.color.colorPrimary):bgColor);
        overrideFonts(activity,view,EnumFont.FONT_ROBOTO_REGULAR);
        imageComponent.setImageBitmap(BitmapFactory.decodeResource(res,image));
        textComponent.setText(text);
        textComponent.setTextColor(Color.BLACK);
        return view;
    }

    public View addComponent(EnumComponent type, int image, String text, int background, int textColor) {
       return addComponent(type, image, text, background, textColor, false);
    }

    public View addComponent(EnumComponent type, int image, String text, int background, int textColor, boolean drawable){
        Resources res = activity.getResources();
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(type.component, null, true);
        ButterKnife.bind(this,view);
        textComponent.setText(text);
        textComponent.setTextColor(activity.getResources().getColor(textColor));
        if(drawable){
            view.setBackground(activity.getResources().getDrawable(background));
        }else {
            view.setBackgroundColor(activity.getResources().getColor(background));
        }
        overrideFonts(activity,view,EnumFont.FONT_ROBOTO_REGULAR);

        if(image == 0)
            imageComponent.setVisibility(View.GONE);
        else
            imageComponent.setImageBitmap(BitmapFactory.decodeResource(res,image));


        return view;
    }

    public View addComponentTitle(EnumComponent type, int image, String title, String text, int textColor, int titleTextColor, int background){

        Resources res = activity.getResources();
        LayoutInflater inflater = activity.getLayoutInflater();
        View view = inflater.inflate(type.component, null, true);
        TextView titleComponent = view.findViewById(R.id.titleComponent);
        ButterKnife.bind(this,view);
        overrideFonts(activity,view,EnumFont.FONT_ROBOTO_REGULAR);

        titleComponent.setText(title);
        textComponent.setText(text);

        view.setBackgroundColor(activity.getResources().getColor(background == 0 ? R.color.colorPrimary : background));
        titleComponent.setTextColor(activity.getResources().getColor(titleTextColor == 0 ? R.color.colorBlack : titleTextColor));
        textComponent.setTextColor(activity.getResources().getColor(textColor == 0 ? R.color.colorBlack : textColor));

        if(image == 0) {
            imageComponent.setVisibility(View.GONE);
            titleComponent.setGravity(Gravity.CENTER);
        }
        else {
            imageComponent.setImageBitmap(BitmapFactory.decodeResource(res, image));
        }
        return view;
    }

    public View changeComponentScale(View selectedView, float scale){
        View view = selectedView;
        view.setScaleX(scale);
        view.setScaleY(scale);
        return view;

    }


}
