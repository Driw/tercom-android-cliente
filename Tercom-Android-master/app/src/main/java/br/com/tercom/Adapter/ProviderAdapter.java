package br.com.tercom.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.tercom.Entity.Provider;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;

public class ProviderAdapter extends RecyclerView.Adapter<ProviderAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<Provider> providers;
    private Context context;

    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;

    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }

    public ProviderAdapter(Context c, ArrayList<Provider> providers) {
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.providers = providers;
        this.context = c;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.item_provider,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.txtFantasyName.setText(providers.get(position).getFantasyName());
        holder.txtCNPJ.setText(providers.get(position).getCnpj());
        holder.txtSpokesMan.setText(providers.get(position).getSpokesman());
    }


    @Override
    public int getItemCount() {
        return providers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView txtFantasyName;
        public TextView txtCNPJ;
        public TextView txtSpokesMan;


        public ViewHolder(View itemView) {
            super(itemView);
            txtFantasyName = itemView.findViewById(R.id.txtFantasyName);
            txtCNPJ = itemView.findViewById(R.id.txtCNPJ);
            txtSpokesMan = itemView.findViewById(R.id.txtSpokesMan);
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
