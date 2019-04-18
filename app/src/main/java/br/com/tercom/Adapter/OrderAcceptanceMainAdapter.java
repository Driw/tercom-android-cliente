package br.com.tercom.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.OrderQuote;
import br.com.tercom.Entity.OrderRequest;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.Interface.iNewOrderItem;
import br.com.tercom.R;

public class OrderAcceptanceMainAdapter extends RecyclerView.Adapter<OrderAcceptanceMainAdapter.ViewHolder> {

    public final int ORS_NONE = 0;
    public final int ORS_CANCEL_BY_CUSTOMER = 1;
    public final int ORS_CANCEL_BY_TERCOM = 2;
    public final int ORS_QUEUED = 3;
    public final int ORS_QUOTING = 4;
    public final int ORS_QUOTED = 5;
    public final int ORS_DONE = 6;

    private LayoutInflater layoutInflater;
    private ArrayList<iNewOrderItem> orderItems;
    private OrderQuote orderQuote;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public OrderAcceptanceMainAdapter(Context c, ArrayList<iNewOrderItem> orderItems, OrderQuote orderQuote){
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = c;
        this.orderItems = orderItems;
        this.orderQuote = orderQuote;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_order_acceptance_main_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        manageItemButtons(holder, orderQuote);
        holder.txtOrderAcceptanceMainItemName.setText(orderItems.get(position).getName());
        holder.txtOrderAcceptanceMainProvider.setText(orderItems.get(position).getProvider().getFantasyName());
        if(orderItems.get(position).isProduct() && orderItems.get(position).getManufacturer() != null){
            holder.txtOrderAcceptanceMainManufacturer.setText(orderItems.get(position).getManufacturer().getName());
        } else {
            holder.txtOrderAcceptanceMainManufacturer.setVisibility(View.GONE);
        }
    }

    private void manageItemButtons (ViewHolder holder, OrderQuote orderQuote){
        switch (orderQuote.getOrderRequest().getStatus()){
            case ORS_QUEUED:
                holder.btnOrderAcceptanceMainAdvanceOrder.setVisibility(View.GONE);
                holder.btnOrderAcceptanceMainDetails.setVisibility(View.GONE);
                break;
            case ORS_QUOTING:
                holder.btnOrderAcceptanceMainAdvanceOrder.setVisibility(View.GONE);
                holder.btnOrderAcceptanceMainAdvanceOrder.setVisibility(View.GONE);
                break;
            case ORS_QUOTED:
                holder.btnOrderAcceptanceMainAdvanceOrder.setVisibility(View.GONE);
                break;
            case ORS_DONE:
                holder.btnOrderAcceptanceMainAdvanceOrder.setVisibility(View.GONE);
                holder.btnOrderAcceptanceMainDetails.setVisibility(View.GONE);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtOrderAcceptanceMainItemName;
        private TextView txtOrderAcceptanceMainProvider;
        private TextView txtOrderAcceptanceMainManufacturer;
        private CardView btnOrderAcceptanceMainDetails;
        private CardView btnOrderAcceptanceMainAdvanceOrder;

        public ViewHolder (View itemView){
            super(itemView);
            txtOrderAcceptanceMainItemName = itemView.findViewById(R.id.txtOrderAcceptanceMainItemName);
            txtOrderAcceptanceMainProvider = itemView.findViewById(R.id.txtOrderAcceptanceMainProvider);
            txtOrderAcceptanceMainManufacturer = itemView.findViewById(R.id.txtOrderAcceptanceMainManufacturer);
            btnOrderAcceptanceMainDetails = itemView.findViewById(R.id.btnOrderAcceptanceMainDetails);
            btnOrderAcceptanceMainAdvanceOrder = itemView.findViewById(R.id.btnOrderAcceptanceMainAdvanceOrder);
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
