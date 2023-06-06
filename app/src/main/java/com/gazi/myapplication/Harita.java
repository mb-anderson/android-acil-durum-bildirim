package com.gazi.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import java.util.ArrayList;
import java.util.Locale;
import java.util.concurrent.ThreadLocalRandom;

public class Harita extends AppCompatActivity {

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient client;
    ImageButton imageButton;
    String yardimNumarasiMehmet = "05380920836";//Mehmet Ali Tahtaci
    String yardimNumarasiMustafa = "05076000349";//Mustafa Burak Yücel


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_harita);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_maps);

        client = LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION).withListener(new PermissionListener() {
            @Override
            public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                getCurrentLocation();
            }

            @Override
            public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

            }

            @Override
            public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

            }
        }).check();
        imageButton =findViewById(R.id.imageButton);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                    yardimSMSGonder();
                    Toast.makeText(Harita.this, "Acil Yardım Talebi Gönderildi", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = client.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {

                     supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                         @Override
                         public void onMapReady(@NonNull GoogleMap googleMap) {
                             if(location!=null){
                             LatLng latLng = new LatLng(location.getLatitude(), location.getLongitude());

                             MarkerOptions options = new MarkerOptions().position(latLng).title("Buradasın!!");
                             googleMap.addMarker(options);
                             googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 50));


                         }
                             else{
                                 Toast.makeText(Harita.this,"Konum İznininzi Kontrol Edin",Toast.LENGTH_SHORT).show();
                             }
                         }
                     });
            }
        });
    }

    public void yardimSMSGonder(){
        SmsManager smsManager = SmsManager.getDefault();
        Intent gelenintent = getIntent();
        String telefon =gelenintent.getStringExtra("telefon1");
        String kullaniciAdi =gelenintent.getStringExtra("kullaniciAdi");
        String kullaniciSoyadi =gelenintent.getStringExtra("kullaniciSoyadi");
        ArrayList<String> parts = smsManager.divideMessage("Acil Yardim Talebi Var Telefon Numarası: " + telefon + " Adı:" + kullaniciAdi + " Soyadi:"  + kullaniciSoyadi);
        String phoneNumber = yardimNumarasiMehmet;
        smsManager.sendMultipartTextMessage(phoneNumber,null,parts,null,null);
        phoneNumber = yardimNumarasiMustafa;
        smsManager.sendMultipartTextMessage(phoneNumber,null,parts,null,null);

    }

}