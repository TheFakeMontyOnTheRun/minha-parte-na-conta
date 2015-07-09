package br.odb.myshare.recyclerview.product;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.odb.myshare.R;
import br.odb.myshare.datamodel.Item;

/**
 * Created by monty on 6/27/15.
 */
public class Adapter extends RecyclerView.Adapter {

    private final View.OnClickListener itemClickListener;

    public Adapter(View.OnClickListener listener) {
        this.itemClickListener = listener;
    }

    private class ProductViewHolder extends RecyclerView.ViewHolder {

        private final View view;

        public ProductViewHolder(View itemView) {
            super(itemView);

            this.view = itemView;
        }
    }

    final private List<Item> products = new ArrayList<>();

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.product_layout, parent, false);


        v.setOnClickListener( this.itemClickListener );

        ProductViewHolder vh = new ProductViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Item product = products.get(position);

        ( (TextView) ((ProductViewHolder) holder ).view.findViewById( R.id.tvProductNameCard)).setText(product.getName());

        if ( product.getPhoto() != null ) {
            ( (ImageView) ((ProductViewHolder) holder ).view.findViewById( R.id.ivProductMainPhoto ) ).setImageBitmap(product.getPhoto());
        }
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public void setProducts(List<Item> products) {
        this.products.clear();
        this.products.addAll(products);
        this.notifyDataSetChanged();
    }
}
