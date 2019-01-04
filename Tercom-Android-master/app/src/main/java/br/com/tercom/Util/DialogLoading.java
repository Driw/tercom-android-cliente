package br.com.tercom.Util;

import android.app.Activity;
import android.app.Dialog;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import br.com.tercom.Enum.EnumDialogOptions;
import br.com.tercom.R;

public class DialogLoading {


    private static Activity activity;
    private static Dialog loadingDialog;


    public DialogLoading(Activity activity){
        this.activity = activity;
    }


    public void init(){
        try {

            loadingDialog = new Dialog(activity);
            loadingDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            loadingDialog.setCancelable(false);
            loadingDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            loadingDialog.setContentView(R.layout.dialog_loading);
            loadingDialog.show();

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dismissD(){
        loadingDialog.dismiss();
    }

    public Dialog getConfirmDialog() {
        return loadingDialog;
    }

}
