package br.com.tercom.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.Manufacture;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;

public class ManufacturerAdapter extends  RecyclerView.Adapter<ManufacturerAdapter.ViewHolder>   {

    private LayoutInflater layoutInflater;
    private ArrayList<Manufacture> manufacturers;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public ManufacturerAdapter(Context c, ArrayList<Manufacture> manufactures) {
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.manufacturers = manufactures;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_category,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ManufacturerAdapter.ViewHolder holder, int position) {
        holder.txtCategory.setText(manufacturers.get(position).getFantasyName());


    }

    @Override
    public int getItemCount() {
        return manufacturers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtCategory;
        public ConstraintLayout ct_category;


        public ViewHolder(View itemView) {
            super(itemView);
            txtCategory = itemView.findViewById(R.id.txtCategory);
            ct_category = itemView.findViewById(R.id.ct_category);
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
