package br.com.tercom.Boundary.Activity;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
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

    @BindView(R.id.rvMessageList)
    RecyclerView rvMessageList;

    @OnClick(R.id.btnStartContact) void newMessage(){
        createIntentAbs(MessageLog.class);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message_list);
        ButterKnife.bind(this);
    }

    /*private void createMessageList(){
        ArrayList<Message> list = new ArrayList<Message>();
        ArrayList<MessageItem> array = new ArrayList<MessageItem>();
        for (int i = 0; i < 10; i++){
            Message m = new Message();
            MessageItem mI = new MessageItem();
            m.setSubject("subject"+i);
            m.setDate(Calendar.getInstance().getTime());
            m.setStatus("status"+i);
            mI.setMessage("message"+i);
            mI.setIdUser(i);
            array.add(mI);
            m.setMensagens(array);
        }
        MessageAdapter messageAdapter = new MessageAdapter(this, list);
        messageAdapter.setmRecyclerViewOnClickListenerHack(new RecyclerViewOnClickListenerHack() {
            @Override
            public void onClickListener(View view, int position) {
                createIntentAbs(MessageLog.class);
            }
        });
        rvMessageList.setAdapter(messageAdapter);
    }*/


}
