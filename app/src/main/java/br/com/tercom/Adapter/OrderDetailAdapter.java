package br.com.tercom.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.OrderItemProduct;
import br.com.tercom.Entity.ProductValue;
import br.com.tercom.Entity.ProdutoGenerico;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.Interface.iNewOrderItem;
import br.com.tercom.R;

public class OrderDetailAdapter extends RecyclerView.Adapter<OrderDetailAdapter.ViewHolder> {

    private ArrayList<? extends iNewOrderItem> list;
    private LayoutInflater layoutInflater;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public OrderDetailAdapter (Context c, ArrayList<? extends iNewOrderItem> list){
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.list = list;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_order_detail, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtOrderDetailItemName.setText(list.get(position).getName());
        holder.txtOrderDetailItemProvider.setText(list.get(position).getProvider().getName());
        holder.txtOrderDetailAddInformation.setText(list.get(position).getObservations());
        if (list.get(position).isProduct()){
            holder.txtOrderDetailItemManufacturer.setText(list.get(position).getManufacturer().getName());
        } else {
            holder.txtOrderDetailItemManufacturer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txtOrderDetailItemName;
        public TextView txtOrderDetailItemManufacturer;
        public TextView txtOrderDetailItemProvider;
        public TextView txtOrderDetailAddInformation;


        public ViewHolder(View itemView) {
            super(itemView);
            txtOrderDetailItemName = itemView.findViewById(R.id.txtOrderDetailItemName);
            txtOrderDetailItemManufacturer = itemView.findViewById(R.id.txtOrderDetailItemManufacturer);
            txtOrderDetailItemProvider = itemView.findViewById(R.id.txtOrderDetailItemProvider);
            txtOrderDetailAddInformation = itemView.findViewById(R.id.txtOrderDetailAddInformation);
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
