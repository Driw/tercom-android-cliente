package br.com.tercom.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.ProdutoGenerico;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private ArrayList<ProdutoGenerico> produtos;
    private LayoutInflater layoutInflater;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public OrderDetailAdapter (Context c, ArrayList<ProdutoGenerico> produtos){
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.produtos = produtos;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_order_detail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtOrderDetailProductName.setText(produtos.get(position).getName());
        holder.txtOrderDetailManufacturerName.setText(produtos.get(position).getManufacturer());
        holder.txtOrderDetailProviderName.setText(produtos.get(position).getProvider());
    }

    @Override
    public int getItemCount() {
        return produtos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtOrderDetailProductName;
        public TextView txtOrderDetailManufacturerName;
        public TextView txtOrderDetailProviderName;

        public ViewHolder(View itemView) {
            super(itemView);
            txtOrderDetailProductName = itemView.findViewById(R.id.txtOrderDetailProductName);
            txtOrderDetailManufacturerName = itemView.findViewById(R.id.txtOrderDetailManufacturerName);
            txtOrderDetailProviderName = itemView.findViewById(R.id.txtOrderDetailProviderName);
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
