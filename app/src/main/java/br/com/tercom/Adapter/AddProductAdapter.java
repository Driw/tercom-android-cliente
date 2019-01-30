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
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;

public class AddProductAdapter extends  RecyclerView.Adapter<AddProductAdapter.ViewHolder>   {

    private LayoutInflater layoutInflater;
    private ArrayList<Product> products;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public AddProductAdapter(Context c, ArrayList<Product> categories) {
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.products = categories;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_category,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(AddProductAdapter.ViewHolder holder, int position) {
        holder.txtProduct.setText(products.get(position).getName());


    }

    @Override
    public int getItemCount() {
        return products.size();
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
