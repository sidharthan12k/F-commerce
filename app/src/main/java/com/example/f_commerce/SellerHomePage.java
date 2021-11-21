package com.example.f_commerce;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.concurrent.TimeUnit;

public class SellerHomePage extends AppCompatActivity {
    ImageView imgView;
    EditText et1, et2, et3, et4;
    TextView latTxt, longTxt;
    Button getLoc, saveItem;
    String longtlat;
    String lat, longg;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    private static final int PICK_IMAGE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_seller_home_page);
        imgView = (ImageView) findViewById(R.id.imgData);
        et1 = (EditText) findViewById(R.id.nameData);
        et2 = (EditText) findViewById(R.id.speciesData);
        et3 = (EditText) findViewById(R.id.descriptionData);
        et4 = (EditText) findViewById(R.id.MRPPrice);

        getLoc = (Button) findViewById(R.id.getLocation);
        //imgview onclick
        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectImage();
            }
        });


        getLoc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String str = "";
                try {
                    for(int i=0;i<5;i++) {
                        if(i==0)
                            str = getCurrentLocation();
                        TimeUnit.SECONDS.sleep(2);
                    }
//                    SidhuHack sd = new SidhuHack(getApplicationContext());
//                    String st =sd.doInBackground();
                    Log.d("hello", "" + longtlat + "btn");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                //setLatlog("", "");
            }
        });

    }


    private void selectImage() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            Uri imgUri = data.getData();
            imgView.setImageURI(imgUri);
        }
    }

    public void saveItems(View view) {
        FirebaseDataTransfer fdt = new FirebaseDataTransfer();
        fdt.UploadItem(et1.getText().toString(), et2.getText().toString(), et3.getText().toString(), et4.getText().toString());

    }


    public String chkPermissions() throws InterruptedException {
        if (ContextCompat.checkSelfPermission(getApplicationContext(),
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(SellerHomePage.this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
            Log.d("permission", "failed");
        } else {
            Log.d("permission", "success");
            getCurrentLocation();
        }
        return null;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults.length > 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //getCurrentLocation();
            } else {
                Toast.makeText(getApplicationContext(), "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    public String getCurrentLocation() throws InterruptedException {
        Log.d("hello", "inside getlocation");

        LocationRequest locationRequest = new LocationRequest();
        Log.d("hello", "" + locationRequest + "locaobj");
        locationRequest.setInterval(1);
        locationRequest.setFastestInterval(3);
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        Log.d("hello", "" + locationRequest + "locaobj");
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
        }
        //lat = null;
        //while(lat!=null){
//        LocationServices.getFusedLocationProviderClient(SellerHomePage.this)
//                .requestLocationUpdates(locationRequest, new LocationCallback() {
//                    @Override
//                    public void onLocationResult(LocationResult locationResult) {
//                        LocationServices.getFusedLocationProviderClient(getApplicationContext())
//                                .removeLocationUpdates(this);
//                        if (locationResult != null && locationResult.getLocations().size() > 0) {
//                            int latestlocIndex = locationResult.getLocations().size() - 1;
//                            double lati = locationResult.getLocations().get(latestlocIndex).getLatitude();
//                            double longi = locationResult.getLocations().get(latestlocIndex).getLongitude();
//                            //textLatLong.setText(String.format("Latitude : %s\n Longitude: %s", lati, longi));
//
//                            lat = String.valueOf(lati);
//                            longg = String.valueOf(longi);
//                            longtlat = lat;
//                            Toast.makeText(getApplicationContext(), longg, Toast.LENGTH_LONG).show();
//                            Toast.makeText(getApplicationContext(), lat, Toast.LENGTH_LONG).show();
//                            Location location = new Location("providerNA");
//                            location.setLongitude(longi);
//                            location.setLatitude(lati);
//                            if(lat!=null){
//                                Log.d("hello"," "+lat+"loop");
//                                setLatlog(longg,lat);
//                            }
//                            Log.d("hello"," "+lat+"hel");
//                        } else {
//                        }
//                    }
//                }, Looper.getMainLooper());

        LocationServices.getFusedLocationProviderClient(SellerHomePage.this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(@NonNull Location location) {
                double lat = location.getLatitude();

                Log.d("hello", "" + lat + "lastLoc");
                setLatlog(String.valueOf(lat), String.valueOf(location.getLongitude()));
                Toast.makeText(getApplicationContext(), "" + lat + "lastloc", Toast.LENGTH_LONG).show();
                Log.d("hello", "" + lat + "lat in success");
                longtlat = String.valueOf(lat);
                if (longtlat != null) {
                    // return longtlat;
                }
            }
        });
        Log.d("hello", "" + longtlat + "longtlat last");
        return longtlat != null ? longtlat : "";
    }

    private void setLatlog(String loongg, String lat) {
        Log.d("hello", longg + "set");
        Log.d("hello", lat + "setlatlog");
        latTxt = (TextView) findViewById(R.id.latitudeData);
        longTxt = (TextView) findViewById(R.id.loggitudeData);
        //Toast.makeText(getApplicationContext(),""+str,Toast.LENGTH_LONG).show();
        latTxt.setText(" " + longtlat);
        longg = loongg;

    }
}
//
//class SidhuHack extends AsyncTask<Void, Void, String> {
//    Context contextt;
//    String longtlat;
//    public SidhuHack(Context context){
//        contextt = context;
//    }
//
//
//    @Override
//    protected String doInBackground(Void... voids) {
//        if (ActivityCompat.checkSelfPermission(contextt, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(contextt, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//
//        }
//        LocationServices.getFusedLocationProviderClient(contextt).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
//            @Override
//            public void onSuccess(@NonNull Location location) {
//                double lat = location.getLatitude();
//
//                Log.d("hello", "" + lat + "lastLoc  class");
//                // setLatlog(String.valueOf(lat), String.valueOf(location.getLongitude()));
//                Toast.makeText(contextt, "" + lat + "lastloc", Toast.LENGTH_LONG).show();
//                Log.d("hello", "" + lat + "lat in success   class");
//                longtlat = String.valueOf(lat);
//                if (longtlat != null) {
//                    // return longtlat;
//                }
//            }
//        });
//        Log.d("hello",longtlat+"class long");
//        return longtlat;
//    }
//}