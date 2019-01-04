package br.com.tercom.Boundary.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.Message;
import br.com.tercom.Entity.MessageItem;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.tercom.Application.AppTercom.USER_STATIC;

public class NewMessage extends AbstractAppCompatActivity {

    Message newMessage = new Message();
    MessageItem messageItem = new MessageItem();
    ArrayList<MessageItem> currentMessage = new ArrayList<MessageItem>();

    @BindView(R.id.txtSubject)
    EditText txtSubject;
    @BindView(R.id.txtNewMessage)
    EditText txtNewMessage;
    @BindView(R.id.txtDate)
    TextView txtDataAtual;

    @OnClick(R.id.btnNewMessage)
     void sendNewMessage() {
        messageItem.setIdUser(USER_STATIC.getToken());
        messageItem.setMessage(txtNewMessage.toString());
        currentMessage.add(messageItem);

        newMessage.setDate(Calendar.getInstance().getTime());
        newMessage.setSubject(txtSubject.toString());
        newMessage.setMensagens(currentMessage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createToolbar();
        ButterKnife.bind(this);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        txtDataAtual.setText(String.valueOf(sdf.format(Calendar.getInstance().getTime())));

    }


}
