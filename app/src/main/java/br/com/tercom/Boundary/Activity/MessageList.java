package br.com.tercom.Boundary.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Adapter.MessageAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.Message;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageList extends AbstractAppCompatActivity {

    @BindView(R.id.txtSubject)
    TextView txtSubject;
    @BindView(R.id.txtDate)
    TextView txtDate;
    @BindView(R.id.txtStatus)
    TextView txtxSatus;
    @BindView(R.id.rvMessageList)
    RecyclerView rvMessageList;

    @OnClick(R.id.btnNewMessage) void newMessage(){
        Intent newMessage = new Intent(MessageList.this, NewMessage.class);
        startActivity(newMessage);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        createToolbar();
        ButterKnife.bind(this);
    }

    private void createMessageList(ArrayList<Message> list){
        MessageAdapter messageAdapter = new MessageAdapter(this, list);
        messageAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                Intent messageLog = new Intent(MessageList.this, MessageLog.class);
                startActivity(messageLog);
            }
        });
        rvMessageList.setAdapter(messageAdapter);
    }

}
