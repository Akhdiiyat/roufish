package com.example.roufish.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.roufish.items.ListProduct;
import com.example.roufish.R;
import com.example.roufish.DescriptionProduct;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductsAdapter extends RecyclerView.Adapter<ProductsAdapter.ViewHolder> {
    private ArrayList<ListProduct> products;
    private OnItemClickListener listener;

    public ProductsAdapter(ArrayList<ListProduct> productList) {
        this.products = productList;
        this.listener = listener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ListProduct product = products.get(position);
        if (product.getImageResId() != null && !product.getImageResId().isEmpty()) {
            Picasso.get()
                    .load(product.getImageResId())
                    .into(holder.productImageView);
        } else {
            holder.productImageView.setImageResource(R.drawable.logo);
        }
        holder.productNameTextView.setText(product.getName());
        holder.productPriceTextView.setText("Rp." +String.valueOf(product.getPrice()) + "/Kg");
        holder.productDescriptionTextView.setText(product.getDeskripsi());
        holder.productImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DescriptionProduct.class);
                intent.putExtra("documentId",product.getDocumentId());
                intent.putExtra("image_url", product.getImageResId());
                intent.putExtra("productName", product.getName());
                intent.putExtra("productPrice", String.valueOf(product.getPrice()));
                intent.putExtra("productDescription", product.getDeskripsi());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView productImageView;
        TextView productNameTextView;
        TextView productPriceTextView;
        TextView productDescriptionTextView;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            productNameTextView = itemView.findViewById(R.id.productNameTextView);
            productPriceTextView = itemView.findViewById(R.id.productPriceTextView);
            productImageView = itemView.findViewById(R.id.productImageView); // Corrected initialization
            productDescriptionTextView = itemView.findViewById(R.id.productDescriptionTextView);

            itemView.setOnClickListener(new View.OnClickListener() {
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }
        public void bind(String name,int price, String imageUrl,String deskripsi){
            productNameTextView.setText(name);
            productPriceTextView.setText(String.valueOf(price));
            productDescriptionTextView.setText(deskripsi);
            Picasso.get().load(imageUrl).into(productImageView);
        }
    }
}
