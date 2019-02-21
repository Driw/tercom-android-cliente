package br.com.tercom.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;

import java.util.ArrayList;

import br.com.tercom.Entity.Order;
import br.com.tercom.Entity.OrderRequest;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
import butterknife.BindView;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder> {

    private LayoutInflater layoutInflater;
    private ArrayList<OrderRequest> orders;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public OrderListAdapter(Context c, ArrayList<OrderRequest> orders){
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.orders = orders;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_order_list,parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        if (orders.get(position).getStatus() < 5){
            holder.fabRemoveOrder.setVisibility(View.INVISIBLE);
        } else {
            holder.fabProceedOrder.setVisibility(View.INVISIBLE);
        }
        holder.txtOrderNumber.setText(String.valueOf(orders.get(position).getId()));
        holder.txtOrderStatus.setText(String.valueOf(orders.get(position).getStatus()));
    }

    @Override
    public int getItemCount() {
        return orders.size();
    }

        public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

            public TextView txtOrderNumber;
            public TextView txtOrderStatus;
            public TextView txtOrderOverview;
            public FloatingActionButton fabRemoveOrder;
            public FloatingActionButton fabProceedOrder;

            public ViewHolder(View itemView) {
                super(itemView);
                txtOrderNumber = itemView.findViewById(R.id.txtOrderNumber);
                txtOrderStatus = itemView.findViewById(R.id.txtOrderStatus);
                txtOrderOverview = itemView.findViewById(R.id.txtOrderOverview);
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
