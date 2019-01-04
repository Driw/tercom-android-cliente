package br.com.tercom.Boundary.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import static br.com.tercom.Application.AppTercom.USER_STATIC;

import br.com.tercom.Adapter.MessageAdapterLog;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.Message;
import br.com.tercom.Entity.MessageItem;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageLog extends AbstractAppCompatActivity {

    private ArrayList<MessageItem> messageList;
    private MessageItem newMessageItem;
    private Message message;

    @BindView(R.id.txtMessageUser)
    TextView txtMessageUser;
    @BindView(R.id.txtMessageNotUser)
    TextView txtMessageNotUser;
    @BindView(R.id.txtMessageUserName)
    TextView txtMessageUserName;
    @BindView(R.id.txtNewMessage)
    EditText txtNewMessage;

    @OnClick(R.id.btnRespond) void addNewMessage(){
        newMessageItem.setMessage(txtNewMessage.getText().toString());
        newMessageItem.setIdUser(USER_STATIC.getToken());
        messageList.add(newMessageItem);
        message.setMensagens(messageList);
    }

    public MessageLog(Message message) {
        this.message = message;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_log);
        createToolbar();
        ButterKnife.bind(this);
        MessageAdapterLog adapter = new MessageAdapterLog(this, message.getMensagens());
    }

}
