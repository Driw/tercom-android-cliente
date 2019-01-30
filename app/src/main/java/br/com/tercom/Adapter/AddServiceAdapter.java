package br.com.tercom.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.Services;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;

public class AddServiceAdapter extends  RecyclerView.Adapter<AddServiceAdapter.ViewHolder>   {

    private LayoutInflater layoutInflater;
    private ArrayList<Services> services;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public AddServiceAdapter(Context c, ArrayList<Services> categories) {
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.services = categories;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_category,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AddServiceAdapter.ViewHolder holder, int position) {
        holder.txtServices.setText(services.get(position).getName());

    }

    @Override
    public int getItemCount() {
        return services.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtServices;
        public ConstraintLayout ctServices;


        public ViewHolder(final View itemView) {
            super(itemView);
            txtServices = itemView.findViewById(R.id.txtCategory);
            ctServices = itemView.findViewById(R.id.ct_category);
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
