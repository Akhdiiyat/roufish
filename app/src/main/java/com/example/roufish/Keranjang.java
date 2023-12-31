package com.example.roufish;

import android.content.Intent;
import android.os.Bundle;
import android.service.controls.actions.FloatAction;
import android.util.SparseArray;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.roufish.activities.MainPageBuyer;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class Keranjang extends AppCompatActivity {
    private TextView nama, harga,alamat,nohp;
    private FloatingActionButton backTomain;
    private ImageView gambar;
    TextView BelumAdaPilihan;
    TextView jamKeranjang,totHarga;
    TextView jam;
    private SparseArray<String> radioButtonTextMap = new SparseArray<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keranjang);

        nama = findViewById(R.id.produkNama);
        harga = findViewById(R.id.produkHarga);
        gambar = findViewById(R.id.fotoProduk_keranjang);
        alamat = findViewById(R.id.jln_keranjang);
        nohp = findViewById(R.id.nohp_keranjang);
        totHarga = findViewById(R.id.totHarga_keranjang);

        backTomain = findViewById(R.id.backToMain_keranjang);

        BelumAdaPilihan = findViewById(R.id.BelumAdaPilihan);
        Button pesanproduk = findViewById(R.id.pesan_produk);
        FloatingActionButton backToMain = findViewById(R.id.backToMain_keranjang);
        jamKeranjang = findViewById(R.id.jam_keranjang);
        jam = findViewById(R.id.jam);

        Intent intent = getIntent();
        pesanproduk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent pesanprodukIntent = new Intent(Keranjang.this,pembayaran.class);
                if (intent != null) {
                    if (intent.hasExtra("productPrice")) {
                        String productPrice = intent.getStringExtra("productPrice");
                        pesanprodukIntent.putExtra("productPrice", productPrice);
                    }
                    if (intent.hasExtra("productName")) {
                        String productName = intent.getStringExtra("productName");
                        pesanprodukIntent.putExtra("productName", productName);
                    }
                    if (intent.hasExtra("productPrice")) {
                        String productPrice = intent.getStringExtra("productPrice");
                        pesanprodukIntent.putExtra("productPrice", productPrice);
                    }
                    if (intent.hasExtra("image_url")) {
                        String imageUrl = intent.getStringExtra("image_url");
                        pesanprodukIntent.putExtra("image_url", imageUrl);
                        Toast.makeText(Keranjang.this, "berhasil" + imageUrl, Toast.LENGTH_SHORT).show();
                    }
                    if (intent.hasExtra("documentId")) {
                        String documentId = intent.getStringExtra("documentId");
                        pesanprodukIntent.putExtra("documentId", documentId);
                        //Toast.makeText(Keranjang.this, "id ada", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(Keranjang.this, "id tidak ada", Toast.LENGTH_SHORT).show();
                    }
                }
                startActivity(pesanprodukIntent);
            }
        });
        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainIntent = new Intent(Keranjang.this, MainPageBuyer.class);
                startActivity(backToMainIntent);
            }
        });

        TextView ubah = findViewById(R.id.Text_UbahKeranjang);
        ubah.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String productName = extras.getString("productName");
            String productPrice = extras.getString("productPrice");
            String productImageUrl = extras.getString("productImageUrl");

            nama.setText(productName);
            harga.setText("Rp." + String.valueOf(productPrice));
            totHarga.setText("Rp." + String.valueOf(productPrice));
            Picasso.get().load(productImageUrl).into(gambar);
        }
        backTomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backIntent = new Intent(Keranjang.this, DescriptionProduct.class);
                startActivity(backIntent);
            }
        });
        showUserData();
    }
    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);

        bottomSheetDialog.setContentView(R.layout.activity_page_keranjang);

        RadioGroup radioGroup = bottomSheetDialog.findViewById(R.id.radiogroup);
        Button btnLanjutkan = bottomSheetDialog.findViewById(R.id.btn_lanjutkan);

        radioButtonTextMap.put(R.id.Ambilditempat, "Ambil di tempat");
        radioButtonTextMap.put(R.id.Diantarkerumah, "Diantar ke rumah");
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                String selectedOption = radioButtonTextMap.get(checkedId, "");
                if ("Ambil di tempat".equals(selectedOption)) {
                    jam.setVisibility(View.VISIBLE);
                    jamKeranjang.setVisibility(View.VISIBLE);
                } else if ("Diantar ke rumah".equals(selectedOption)) {
                    jam.setVisibility(View.GONE);
                    jamKeranjang.setVisibility(View.GONE);
                } else {

                }
                BelumAdaPilihan.setText(selectedOption);
            }
        });

        btnLanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bottomSheetDialog.dismiss();
            }
        });
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR_OF_DAY, 2);
        String currentTime = sdf.format(calendar.getTime());
        jam.setText(currentTime);

        bottomSheetDialog.show();
    }

    public void showUserData() {
        FirebaseFirestore firestore = FirebaseFirestore.getInstance();
        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        FirebaseAuth mAuth = FirebaseAuth.getInstance();
        String userId = mAuth.getCurrentUser().getUid();

        DocumentReference documentReference = firestore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if (value != null && value.exists()) {
                    alamat.setText(value.getString("alamat"));
                    nohp.setText(value.getString("noHP"));
                }

            }
        });
    }
}
