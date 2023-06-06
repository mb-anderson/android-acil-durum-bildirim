package com.gazi.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Anasayfa extends AppCompatActivity {

    DatabaseReference veriyolu;
    FirebaseDatabase veritabani;

    EditText Tc ;
    EditText isim;
    EditText Soyisim;
    Button kayıtButton;
    String telefon;
    TextView text;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_anasayfa);

        veritabani = FirebaseDatabase.getInstance();

        veriyolu = veritabani.getInstance().getReference("Üyeler");

        Intent gelenintent = getIntent();
        telefon =gelenintent.getStringExtra("telefon1");
        Tc = findViewById(R.id.TcText);
        isim = findViewById(R.id.isimText);
        Soyisim = findViewById(R.id.soyisimText);
        kayıtButton = findViewById(R.id.kayıtolButton);
        text = findViewById(R.id.textView);

        kayıtButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent harita = new Intent(Anasayfa.this,Harita.class);
                kullaniciOluştur();
                startActivity(harita);
            }
        });


    }


    private void kullaniciOluştur(){
        String uyeisim =isim.getText().toString();
        String uyesoyisim =Soyisim.getText().toString();
        String uyetc =  Tc.getText().toString();
        String uyeid = veriyolu.push().getKey();

        Kullanici uye = new Kullanici(uyeid,uyeisim,uyesoyisim,uyetc,telefon);
        veriyolu.child(uyeid).setValue(uye);

    }
}