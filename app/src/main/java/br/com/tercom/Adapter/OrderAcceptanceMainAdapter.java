package br.com.tercom.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.OrderRequest;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.Interface.iNewOrderItem;
import br.com.tercom.R;

public class OrderAcceptanceMainAdapter extends RecyclerView.Adapter<OrderAcceptanceMainAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<iNewOrderItem> orderItems;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public OrderAcceptanceMainAdapter(Context c, ArrayList<iNewOrderItem> orderItems){
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = c;
        this.orderItems = orderItems;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_order_acceptance_main_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtOrderAcceptanceMainItemName.setText(orderItems.get(position).getName());
        holder.txtOrderAcceptanceMainProvider.setText(orderItems.get(position).getProvider().getFantasyName());
        if(isProduct(position)){
            holder.txtOrderAcceptanceMainManufacturer.setText(orderItems.get(position).getManufacturer().getName());
        } else {
            holder.txtOrderAcceptanceMainManufacturer.setVisibility(View.GONE);
        }
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    private boolean isProduct(int position){
        if(orderItems.get(position).getManufacturer() != null){
            return true;
        } else {
            return false;
        }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtOrderAcceptanceMainItemName;
        private TextView txtOrderAcceptanceMainProvider;
        private TextView txtOrderAcceptanceMainManufacturer;

        public ViewHolder (View itemView){
            super(itemView);
            txtOrderAcceptanceMainItemName = itemView.findViewById(R.id.txtOrderAcceptanceMainItemName);
            txtOrderAcceptanceMainProvider = itemView.findViewById(R.id.txtOrderAcceptanceMainProvider);
            txtOrderAcceptanceMainManufacturer = itemView.findViewById(R.id.txtOrderAcceptanceMainManufacturer);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(mRecyclerViewOnClickListenerHack != null) {
                mRecyclerViewOnClickListenerHack.onClickListener(v, getPosition());
            }
        }
    }

}
