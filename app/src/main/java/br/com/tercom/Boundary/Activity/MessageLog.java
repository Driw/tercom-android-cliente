package br.com.tercom.Boundary.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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

    @BindView(R.id.txtNewMessage)
    EditText txtNewMessage;
    @BindView(R.id.rvMessageLog)
    RecyclerView rvMessageLog;

    @OnClick(R.id.btnRespond) void addNewMessage(){
        newMessageItem.setMessage(txtNewMessage.getText().toString());
        newMessageItem.setIdUser(USER_STATIC.getCustomerEmployeeId());
        messageList.add(newMessageItem);
        message.setMensagens(messageList);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_log);
        createToolbar();
        ButterKnife.bind(this);
        /*MessageAdapterLog messageAdapterLog = new MessageAdapterLog(this, message.getMensagens());
        rvMessageLog.setAdapter(messageAdapterLog);*/
    }

}
