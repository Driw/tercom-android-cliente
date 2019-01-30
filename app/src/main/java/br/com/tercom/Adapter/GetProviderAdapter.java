package br.com.tercom.Adapter;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.Provider;
import br.com.tercom.Entity.ProviderList;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;

public class GetProviderAdapter extends  RecyclerView.Adapter<GetProviderAdapter.ViewHolder>   {

    private LayoutInflater layoutInflater;
    private ArrayList<Provider> providers;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public GetProviderAdapter(Context c, ArrayList<Provider> categories) {
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.providers = categories;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_category,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(GetProviderAdapter.ViewHolder holder, int position) {
        holder.txtProduct.setText(providers.get(position).getFantasyName());

    }

    @Override
    public int getItemCount() {
        return providers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtProduct;
        public ConstraintLayout ctProduct;


        public ViewHolder(final View itemView) {
            super(itemView);
            txtProduct = itemView.findViewById(R.id.txtCategory);
            ctProduct = itemView.findViewById(R.id.ct_category);
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
