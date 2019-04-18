package br.com.tercom.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.Address;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;

public class OrderAddressListAdapter extends RecyclerView.Adapter<OrderAddressListAdapter.ViewHolder>{

    private LayoutInflater layoutInflater;
    private Context context;
    private ArrayList<Address> addresses;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public OrderAddressListAdapter (Context c, ArrayList<Address> addresses){
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.context = c;
        this.addresses = addresses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_order_address_list, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtAddressListStreet.setText(addresses.get(position).getStreet());
        holder.txtAddressListNumber.setText(String.valueOf(addresses.get(position).getNumber()));
        holder.txtAddressListNeighborhood.setText(addresses.get(position).getNeighborhood());
        holder.txtAddressListCity.setText(addresses.get(position).getCity());
        holder.txtAddressListState.setText(addresses.get(position).getState());
        if(addresses.get(position).getComplement().equals("")){
            holder.txtAddressListComplement.setVisibility(View.GONE);
        } else {
            holder.txtAddressListComplement.setText("Complemento: "+addresses.get(position).getComplement());
        }
    }

    @Override
    public int getItemCount() {
        return addresses != null?  addresses.size() : 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtAddressListStreet;
        private TextView txtAddressListNumber;
        private TextView txtAddressListComplement;
        private TextView txtAddressListNeighborhood;
        private TextView txtAddressListCity;
        private TextView txtAddressListState;

        public ViewHolder (View itemView){
            super(itemView);
            txtAddressListStreet = itemView.findViewById(R.id.txtAddressListStreet);
            txtAddressListNumber = itemView.findViewById(R.id.txtAddressListNumber);
            txtAddressListComplement = itemView.findViewById(R.id.txtAddressListComplement);
            txtAddressListNeighborhood = itemView.findViewById(R.id.txtAddressListNeighborhood);
            txtAddressListCity = itemView.findViewById(R.id.txtAddressListCity);
            txtAddressListState = itemView.findViewById(R.id.txtAddressListState);
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
