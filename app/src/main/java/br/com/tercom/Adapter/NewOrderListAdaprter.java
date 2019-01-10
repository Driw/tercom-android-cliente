package br.com.tercom.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProdutoGenerico;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;

public class NewOrderListAdaprter extends RecyclerView.Adapter<NewOrderListAdaprter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<ProdutoGenerico> produtos;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public NewOrderListAdaprter(Context c, ArrayList<Product> products){
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_new_order_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtNewOrderListItemProduct.setText(produtos.get(position).getName());
        holder.txtNewOrderListItemManufacturer.setText(produtos.get(position).getManufacturer());
        holder.txtNewOrderListItemProvider.setText(produtos.get(position).getProvider());
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        public TextView txtNewOrderListItemProduct;
        public TextView txtNewOrderListItemManufacturer;
        public TextView txtNewOrderListItemProvider;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNewOrderListItemProduct = itemView.findViewById(R.id.txtNewOrderListItemProduct);
            txtNewOrderListItemManufacturer = itemView.findViewById(R.id.txtNewOrderListItemManufacturer);
            txtNewOrderListItemProvider = itemView.findViewById(R.id.txtNewOrderListItemProvider);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }

        }

    }

}
