package br.com.tercom.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.OrderItemProduct;
import br.com.tercom.Entity.Product;
import br.com.tercom.Entity.ProdutoGenerico;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.Interface.iNewOrderItem;
import br.com.tercom.R;

public class NewOrderListAdapter extends RecyclerView.Adapter<NewOrderListAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<? extends iNewOrderItem> orders;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public NewOrderListAdapter(Context c, ArrayList<? extends iNewOrderItem> orders) {
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = c;
        this.orders = orders;

    }

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_new_order_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull NewOrderListAdapter.ViewHolder holder, int position) {
        holder.txtNewOrderListItemID.setText(orders.get(position).getId());
        holder.txtNewOrderListItemName.setText(orders.get(position).getObservations()); //CHECAR ESTA INFORMAÇÂO
        holder.txtNewOrderListItemProvider.setText(orders.get(position).getProvider().getFantasyName());
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public TextView txtNewOrderListItemID;
        public TextView txtNewOrderListItemName;
        public TextView txtNewOrderListItemProvider;

        public ViewHolder(View itemView) {
            super(itemView);
            txtNewOrderListItemID = itemView.findViewById(R.id.txtNewOrderListItemID);
            txtNewOrderListItemName = itemView.findViewById(R.id.txtNewOrderListItemName);
            txtNewOrderListItemProvider = itemView.findViewById(R.id.txtNewOrderListItemProvider);
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
