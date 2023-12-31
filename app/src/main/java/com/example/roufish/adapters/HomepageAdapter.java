package com.example.roufish.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.DescriptionProduct;
import com.example.roufish.items.ListProduct;
import com.example.roufish.R;
import com.squareup.picasso.Picasso;

import org.w3c.dom.Text;

import java.util.ArrayList;
public class HomepageAdapter extends RecyclerView.Adapter<HomepageAdapter.ViewHolder> {

    private ArrayList<ListProduct> products;
    private Context context;
    private OnItemClickListener itemClickListener;
    private ArrayList<ListProduct> originalProductList;

    public HomepageAdapter(Context context, ArrayList<ListProduct> productList) {
        this.context = context;
        this.products = productList;
        this.originalProductList = new ArrayList<>(productList);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.itemClickListener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recommendation, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ListProduct product = products.get(position);
        if (product.getImageResId() != null && !product.getImageResId().isEmpty()) {
            Picasso.get().load(product.getImageResId()).into(holder.productImageView);
        } else {
            holder.productImageView.setImageResource(R.drawable.logo);
        }

        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText("Rp." +String.valueOf(product.getPrice())+ "/KG");
        holder.productDescriptionTextView.setText(product.getDeskripsi());

        // Set click listener to handle item clicks
       /* holder.productImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (itemClickListener != null) {
                    itemClickListener.onItemClick(position);
                }
            }
        });*/
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, DescriptionProduct.class);
                intent.putExtra("documentId",product.getDocumentId());
                intent.putExtra("productName", product.getName());
                intent.putExtra("productPrice", String.valueOf(product.getPrice()));
                intent.putExtra("productDescription", product.getDeskripsi());
                intent.putExtra("image_url", product.getImageResId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView productDescriptionTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productImageView = itemView.findViewById(R.id.productImageView);
            productDescriptionTextView = itemView.findViewById(R.id.productDescription);
        }
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }
    public void filterList(ArrayList<ListProduct> filteredList) {
        products = filteredList;
        notifyDataSetChanged();
    }
}