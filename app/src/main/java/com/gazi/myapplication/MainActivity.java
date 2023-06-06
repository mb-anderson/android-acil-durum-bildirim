package com.gazi.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

public class MainActivity extends AppCompatActivity {


    DatabaseReference databaseReference;

    String phone,kod;
    String mesaj = "Doğrulama Kodunuz.";
    EditText telefon;
    EditText dogrulamaKod;
    CheckBox aydınlatma;
    Button kodButton;
    Button girisButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




        telefon = findViewById(R.id.TelefonText);
        dogrulamaKod = findViewById(R.id.TelefonDoğrulamaText);
        kodButton = findViewById(R.id.KodGonderButton);
        aydınlatma = (CheckBox) findViewById(R.id.AcıklamaMetiniText);
        girisButton =findViewById(R.id.GirisButton);


        kodButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if(telefon.length() == 11 || telefon.length() == 10){
                        if(aydınlatma.isChecked()) {
                            dogrulamaKod.setVisibility(View.VISIBLE);
                            girisButton.setVisibility(View.VISIBLE);
                            kodButton.setVisibility(View.INVISIBLE);
                            Toast.makeText(MainActivity.this, "DOĞRULAMA KODU GÖNDERİLDİ", Toast.LENGTH_SHORT).show();
                            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
                                OTPgönder();
                            } else {
                                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.SEND_SMS}, 100);
                            }
                        }else{
                            Toast.makeText(MainActivity.this, "Aydınlatma Metnini Onaylayın", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "Telefon Numaranızı Doğru Girin: 05xxxxxxxxx", Toast.LENGTH_SHORT).show();
                    }

            }

        });
        girisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (kod.equals(dogrulamaKod.getText().toString())){
                    Toast.makeText(MainActivity.this, "DOGRULAMA KODU DOĞRU", Toast.LENGTH_SHORT).show();
                    Intent anasayfa = new Intent(MainActivity.this,Anasayfa.class);
                    String uyeTelefon = telefon.getText().toString();
                    anasayfa.putExtra("telefon1",uyeTelefon);
                    startActivity(anasayfa);

                }
                else{
                    Toast.makeText(MainActivity.this, " " +kod, Toast.LENGTH_SHORT).show();

                }
            }
        });

    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if(requestCode == 100){
            if(grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                OTPgönder();
            }
            else{
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public void OTPgönder(){
        int sayı = ThreadLocalRandom.current().nextInt(10000, 99999);

        int dogrulamakodu = dogrulamakoduoluştur();
        kod = String.valueOf(sayı);
        phone = telefon.getText().toString();

        SmsManager smsManager = SmsManager.getDefault();
        ArrayList<String> parts = smsManager.divideMessage(kod + " " + mesaj);
        String phoneNumber = phone;
        smsManager.sendMultipartTextMessage(phoneNumber,null,parts,null,null);

    }
    private int dogrulamakoduoluştur(){
        int minimum = 1000;
        int maksimum = 9999;
        Random randomSayi = new Random();
        int randomNum = randomSayi.nextInt((maksimum-minimum) + 1) + minimum;
        return randomNum;
    }

}
