package br.com.tercom.Util;

import android.content.Context;
import android.graphics.Typeface;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import br.com.tercom.Enum.EnumFont;

/**
 * Created by Trabalho on 2/21/2017.
 */

public class CustomTypeFace {


        public static final Typeface setFontSingleTxt( Context assets,EnumFont font)
        {
            return Typeface.createFromAsset(assets.getAssets(), font.path);
        }


        // getWindow().getDecorView().getRootView() para pegar a activity


        public static void overrideFonts(final Context context, final View v,EnumFont font) {
            try {
                if (v instanceof ViewGroup) {
                    ViewGroup vg = (ViewGroup) v;
                    for (int i = 0; i < vg.getChildCount(); i++) {
                        View child = vg.getChildAt(i);
                        overrideFonts(context, child,font);
                    }
                } else if (v instanceof TextView) {
                    ((TextView) v).setTypeface(Typeface.createFromAsset(context.getAssets(), font.path));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

