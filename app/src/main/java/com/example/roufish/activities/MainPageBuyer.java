package com.example.roufish.activities;

import static com.example.roufish.R.*;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.SearchView;

import com.example.roufish.DescriptionProduct;
import com.example.roufish.PesananSaya;
import com.example.roufish.R;
import com.example.roufish.adapters.HomepageAdapter;
import com.example.roufish.items.ListProduct;
import com.example.roufish.profileBuyer;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class MainPageBuyer extends AppCompatActivity {

    FloatingActionButton beli,lelang,pesananSaya, profile;
    RecyclerView recyclerView;
    private FirebaseFirestore firestore;
    private ArrayList<ListProduct> products = new ArrayList<>();
    private HomepageAdapter homepageAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main_page_buyer);
        /*for (int i = 0; i < 100; i++) {
            products.add(new ListProduct("Mujair", 5000, "https://placehold.co/600x400"));
        }*/
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        //recyclerView.setAdapter(new HomepageAdapter(this,products));
        homepageAdapter = new HomepageAdapter(this,products);
        recyclerView.setAdapter(homepageAdapter);
        beli = findViewById(id.beli);
        firestore = FirebaseFirestore.getInstance();
        searchView = findViewById(id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // Perform search when submit button is pressed (optional)
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                // Perform search as the user types
                filterProducts(newText);
                return true;
            }
        });
        homepageAdapter.setOnItemClickListener(new HomepageAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                ListProduct clickedProduct = products.get(position);
                Intent intent = new Intent(MainPageBuyer.this, DescriptionProduct.class);
                intent.putExtra("product", clickedProduct);
                startActivity(intent);
            }
        });
        //getDataFromFirestore();
        beli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent beliIntent = new Intent(MainPageBuyer.this, ProductActivity.class);
                startActivity(beliIntent);
            }
        });
        lelang = findViewById(id.lelang);
        lelang.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent lelangIntent = new Intent(MainPageBuyer.this, AuctionActivity.class);
                startActivity(lelangIntent);
            }
        });
        pesananSaya = findViewById(id.pesanan);
        pesananSaya.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(MainPageBuyer.this, RiwayatPenjualanActivity.class);
                startActivity(cartIntent);
            }
        });

        profile = findViewById(id.info_profile);
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent profileIntent =  new Intent(MainPageBuyer.this, profileBuyer.class);
                startActivity(profileIntent);
            }
        });
        BottomNavigationView bottomNavigationView = findViewById(R.id.botttom_nav_bar);
        bottomNavigationView.setOnNavigationItemSelectedListener(
                item -> {
                    if (item.getItemId() == R.id.home) {
                        // Navigate to HomeActivity when Home is clicked
                        startActivity(new Intent(MainPageBuyer.this, MainPageBuyer.class));
                        return true;
                    } else if (item.getItemId() == R.id.forum) {
                        // Navigate to ForumActivity when Forum is clicked
                        startActivity(new Intent(MainPageBuyer.this, ForumActivity.class));
                        return true;
                    }
                    // Add more conditions for other items if needed
                    return false;
                }
        );
        FirebaseFirestore database = FirebaseFirestore.getInstance();
        Query query = database.collection("produkJual");

        query.addSnapshotListener(this, (value, error) -> {
            if (error != null) {
                return;
            }
            products.clear();
            for (DocumentSnapshot document : value.getDocuments()) {
                String documentId = document.getId();
                String name = document.getString("nama");
                String description = document.getString("deskripsi");
                String Price = (String) document.get("harga");
                String weight = document.getString("berat");
                int sellPrice = Integer.parseInt(Price);
                // Construct image path using the document ID
                StorageReference imageRef = FirebaseStorage.getInstance().getReference()
                        .child("produkjual/" + document.getId() + ".jpg");

                imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                    ListProduct product = new ListProduct(name, description, sellPrice, uri.toString(),documentId);
                    products.add(product);
                    runOnUiThread(() -> recyclerView.getAdapter().notifyDataSetChanged());
                }).addOnFailureListener(exception -> {
                    // Handle failure (e.g., set a default image URL)
                    ListProduct product = new ListProduct(name, description, sellPrice, "Default_image",documentId);
                    products.add(product);
                    runOnUiThread(() -> recyclerView.getAdapter().notifyDataSetChanged());
                });
            }
            //runOnUiThread(() -> HomepageAdapter.notifyDataSetChanged());
        });
    }
    private void filterProducts(String query) {
        // Filter the products based on the query
        ArrayList<ListProduct> filteredProducts = new ArrayList<>();

        for (ListProduct product : products) {
            if (product.getName().toLowerCase().contains(query.toLowerCase())) {
                filteredProducts.add(product);
            }
        }
        homepageAdapter.filterList(filteredProducts);
    }
}
