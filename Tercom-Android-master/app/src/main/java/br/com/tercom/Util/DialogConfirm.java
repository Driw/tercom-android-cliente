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

public class DialogConfirm {


    private static Activity activity;
    private static Dialog confirmDialog;
    private  ImageView image_confim;
    private  TextView msg_dialog;
    private  Button btn_confirm;

    public DialogConfirm(Activity activity){
        this.activity = activity;
    }

    public void init(EnumDialogOptions option, String msg){
        init(option,msg,"Fechar");
    }


    public void init(EnumDialogOptions option, String msg, String textButton){
        try {
            confirmDialog = new Dialog(activity);
            confirmDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            confirmDialog.setCancelable(false);
            confirmDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            confirmDialog.setContentView(R.layout.dialog_generic);
            image_confim = confirmDialog.findViewById(R.id.image_confim);
            msg_dialog = confirmDialog.findViewById(R.id.msg_dialog);
            btn_confirm = confirmDialog.findViewById(R.id.btn_confirm);
            image_confim.setImageBitmap(BitmapFactory.decodeResource(activity.getResources(),option.image));
            msg_dialog.setText(msg);
            btn_confirm.setText(textButton);
            btn_confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    confirmDialog.dismiss();
                }
            });
            confirmDialog.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void dismissD(){
        confirmDialog.dismiss();
    }

    public Dialog getConfirmDialog() {
        return confirmDialog;
    }

    public void onClickChanges(View.OnClickListener onClick){
        btn_confirm.setOnClickListener(onClick);
    }

}
