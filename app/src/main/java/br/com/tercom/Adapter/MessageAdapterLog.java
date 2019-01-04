package br.com.tercom.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;


import br.com.tercom.Entity.MessageItem;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import static br.com.tercom.Application.AppTercom.USER_STATIC;

public class MessageAdapterLog extends RecyclerView.Adapter<MessageAdapterLog.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<MessageItem> messageItem;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public MessageAdapterLog(Context c, ArrayList<MessageItem> messages) {
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messageItem = messages;
        this.context = c;
    }

    @Override
    public int getItemViewType(int position) {
        if (messageItem.get(position).getIdUser() == USER_STATIC.getId()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

       View v = layoutInflater.inflate(R.layout.item_message_user,parent,false);
       ViewHolder vh = new ViewHolder(v);
       return vh;

//        for (MessageItem m: messageItem) {
//            switch (viewType){
//                case 0:
//                    View v = layoutInflater.inflate(R.layout.item_message_user, parent, false);
//                    return new ViewHolder(v);
//                    break;
//
//                case 1:
//                    View v2 = layoutInflater.inflate(R.layout.item_message_notuser, parent, false);
//                    return new ViewHolder(v2);
//                    break;
//
//            }
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        for(MessageItem m: messageItem){
            holder.messageUser.setText(messageItem.get(position).getMessage());
        }

//        switch (holder.getItemViewType()){
//            case 0:
//                ViewHolder vh0 = (ViewHolder)holder;
//                holder.messageUser.setText(messageItem.get(position).getMessage());
//
//            case 1:
//                ViewHolder1 vh1 = (ViewHolder1)holder;
//                holder.messageNotUser.setText(messageItem.get(position).getMessage());
//                holder.responseUserName.setText(messageItem.get(position).getIdUser());
//        }

    }

    @Override
    public int getItemCount() { return messageItem.size(); }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView messageUser;
        public TextView messageNotUser;
        public TextView responseUserName;

        public ViewHolder(View itemView) {
            super(itemView);
            messageUser = itemView.findViewById(R.id.txtMessageUser);
            messageNotUser = itemView.findViewById(R.id.txtMessageNotUser);
            responseUserName = itemView.findViewById(R.id.txtMessageUserName);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null){
                mRecyclerViewOnClickListenerHack.onClickListener(v,getPosition());
            }

        }

    }

    public class ViewHolder1 extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView messageNotUser;
        public TextView responseUserName;

        public ViewHolder1(View itemView) {
            super(itemView);
            messageNotUser = itemView.findViewById(R.id.txtMessageNotUser);
            responseUserName = itemView.findViewById(R.id.txtMessageUserName);
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
