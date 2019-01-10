package br.com.tercom.Boundary.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import br.com.tercom.Adapter.MessageAdapter;
import br.com.tercom.Boundary.BoundaryUtil.AbstractAppCompatActivity;
import br.com.tercom.Entity.Message;
import br.com.tercom.Entity.MessageItem;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageList extends AbstractAppCompatActivity {

    ArrayList<Message> messages;
    ArrayList<MessageItem> messageItem;

    @BindView(R.id.rvMessageList)
    RecyclerView rvMessageList;

    @OnClick(R.id.btnStartContact) void newMessage(){
        createIntentAbs(NewMessage.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);
        createToolbar();
        populate();
        MessageAdapter messageAdapter = new MessageAdapter(this, messages);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayout.VERTICAL, false);
        rvMessageList.setLayoutManager(layoutManager);
        rvMessageList.setAdapter(messageAdapter);
        messageAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                createIntentAbs(MessageLog.class);
            }
        });
    }

    private void populate(){
        messages = new ArrayList<Message>();
        messageItem = new ArrayList<MessageItem>();
        for (int i = 0; i < 5; i++){
            Message m = new Message();
            m.setSubject("Subject");
            m.setDate(Calendar.getInstance().getTime());
            m.setMensagens(messageItem);
            messages.add(m);
        }
    }

}
