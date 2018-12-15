package br.com.tercom.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.Message;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;

public class MessageAdapterTercom extends RecyclerView.Adapter<MessageAdapterTercom.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<Message> messages;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public MessageAdapterTercom(Context c, ArrayList<Message> messages) {
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.messages = messages;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_message_list_tercom,parent, false);
        MessageAdapterTercom.ViewHolder vh = new MessageAdapterTercom.ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtSubject.setText(messages.get(position).getSubject());
        holder.txtDate.setText(messages.get(position).getDate().toString());
    }

    @Override
    public int getItemCount() { return messages.size(); }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtSubject;
        public TextView txtDate;

        public ViewHolder(View itemView) {
            super(itemView);
            txtSubject = itemView.findViewById(R.id.txtSubject);
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
