package com.example.roufish;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.roufish.activities.AuctionActivity;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class PageLelang extends AppCompatActivity {
    private TextView Text_Waktu;

    private void showBottomSheetDialog() {
        BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_pelelangan);
        Button lanjutkan = bottomSheetDialog.findViewById(R.id.lanjut);
        lanjutkan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText editTextHarga = bottomSheetDialog.findViewById(R.id.hargaLelang);
                String harga = editTextHarga.getText().toString();
                bottomSheetDialog.dismiss();
            }
        });
        bottomSheetDialog.show();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_page_lelang);
        Text_Waktu = findViewById(R.id.Text_Waktu);
        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra("image_url");
        String itemName = intent.getStringExtra("item_name");
        //int startingPrice = intent.getIntExtra("starting_price");
        String itemDescription = intent.getStringExtra("item_description");
        ImageView imageView = findViewById(R.id.Gambar_ikan);
        TextView name = findViewById(R.id.Text_Nama_Produk);
        name.setText(itemName);
        Picasso.get().load(imageUrl).into(imageView);
        FloatingActionButton backToMain = findViewById(R.id.backToMain);
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {
                DateFormat waktuformat = new SimpleDateFormat("HH:mm:ss");
                Text_Waktu.setText(waktuformat.format(new Date()));
                handler.postDelayed(this, 1000);
            }
        });
        Button masukkanHarga = findViewById(R.id.btn_masukkan_harga);
        masukkanHarga.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                showBottomSheetDialog();
            }
        });

        backToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent backToMainintent = new Intent(PageLelang.this, AuctionActivity.class);
                startActivity(backToMainintent);
            }
        });
    }
}


