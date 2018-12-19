package br.com.tercom.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.Message;
import br.com.tercom.Entity.MessageItem;
import br.com.tercom.Entity.User;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import static br.com.tercom.Application.AppTercom.USER_STATIC;

public class MessageAdapterLog extends RecyclerView.Adapter {

    private LayoutInflater layoutInflater;
    private ArrayList<MessageItem> messages;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public MessageAdapterLog(Context c, ArrayList<MessageItem> messages) {
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messages = messages;
        this.context = c;
    }

   @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = null;
        ViewHolder vh;
        for (MessageItem m: messages) {
            if (Integer.toString(m.getIdUser() ) == USER_STATIC.getToken()) {
                v = layoutInflater.inflate(R.layout.item_message_user, parent, false);
            } else {
                v = layoutInflater.inflate(R.layout.item_message_notuser, parent, false);
            }
        }
        vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() { return messages.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView messageUser;
        public TextView messageNotUser;

        public ViewHolder(View itemView) {
            super(itemView);
            messageUser = itemView.findViewById(R.id.txtMessageUser);
            messageNotUser = itemView.findViewById(R.id.txtMessageNotUser);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v,getPosition());
            }

        }


    }



}
