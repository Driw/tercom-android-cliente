package br.com.tercom.Adapter;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Locale;
import br.com.tercom.Entity.ProductValue;
import br.com.tercom.Interface.RecyclerViewOnClickListenerHack;
import br.com.tercom.R;
public class ProductValueAdapter extends RecyclerView.Adapter<ProductValueAdapter.ViewHolder> {
    private LayoutInflater layoutInflater;
    private ArrayList<ProductValue> productValues;
    private Context context;
    private RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack;
    public void setmRecyclerViewOnClickListenerHack(RecyclerViewOnClickListenerHack mRecyclerViewOnClickListenerHack) {
        this.mRecyclerViewOnClickListenerHack = mRecyclerViewOnClickListenerHack;
    }
    public ProductValueAdapter(Context c, ArrayList<ProductValue> providers) {
        layoutInflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.productValues = providers;
        this.context = c;
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = layoutInflater.inflate(R.layout.list_item_product_value,parent,false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.productName.setText(productValues.get(position).getName());
        holder.productPackage.setText(String.format(Locale.getDefault(),"%s %s",productValues.get(position).getAmount(),productValues.get(position).getProduct().getName()));
        holder.productPrice.setText(String.format(Locale.getDefault(),"R$ %.2f",productValues.get(position).getPrice()).replace(".", ","));
        holder.productManufacturer.setText(productValues.get(position).getManufacture().getFantasyName());
        holder.productProvider.setText(productValues.get(position).getProvider().getFantasyName());
    }
    @Override
    public int getItemCount() {
        return productValues.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView productName;
        public TextView productPackage;
        public TextView productPrice;
        public TextView productManufacturer;
        public TextView productProvider;

        public ViewHolder(View itemView) {
            super(itemView);
            productName = itemView.findViewById(R.id.productName);
            productPackage = itemView.findViewById(R.id.productPackage);
            productPrice = itemView.findViewById(R.id.productPrice);
            productManufacturer = itemView.findViewById(R.id.productManufacturer);
            productProvider = itemView.findViewById(R.id.productProvider);
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