package com.example.f_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Locale;

public class cart extends AppCompatActivity {
    double lat ,longg;
    TextView t1,latetx,longtx,total;
    ImageView img1;
    EditText e1, e2;
    int stockavailable,stockEntered,price;
    DatabaseReference database;
    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        e1 =(EditText)findViewById(R.id.quantity);
        e2 =(EditText)findViewById(R.id.price);
        total = (TextView)findViewById(R.id.textView10);
        latetx = (TextView) findViewById(R.id.textView8);
        longtx = (TextView) findViewById(R.id.textView9);
        t1 = findViewById(R.id.textView3);
        img1 = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        t1.setText(intent.getStringExtra("name"));
        img1.setImageResource(intent.getIntExtra("img", 0));
        price = Integer.valueOf(intent.getStringExtra("price"));
        e2.setText(""+price);
        stockavailable = Integer.parseInt(intent.getStringExtra("stock"));
        e2.setInputType(InputType.TYPE_NULL);
        total.setText("hello");
        e1.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if((Integer.parseInt(String.valueOf(s)))>=stockavailable){
//                    //e1.setError("Available quantity is : "+stockavailable);
//                }else{
//                    int n = Integer.parseInt(e1.getText().toString());
//                    total.setText(n*(Integer.parseInt(e2.getText().toString())));

                updatePrice();
//                }
            }

            @Override
            public void afterTextChanged(Editable s) {
                }
        });
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        database = FirebaseDatabase
                .getInstance()
                .getReference("pricing").child(intent.getStringExtra("name"));
        //e1=(EditText)findViewById(R.id.editTextNumberDecimal);
       /* e2=(EditText)findViewById(R.id.editTextNumberDecimal2);
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String str = snapshot.getValue().toString();
                e2.setText(str);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });*/
        //boolean b2 =intent.getBooleanExtra("type",false);
        /*if(b2){
            e1.isEnabled();
        }
        else{e1.setKeyListener(null);}
*/

    }

    private void updatePrice() {
        String str =((e1.getText().toString()) != null) ? (e1.getText().toString()) :"0";

        int n = Integer.valueOf(str);
        if(n>stockavailable)
            e1.setError("Available stock : "+stockavailable);
        total.setText("Total price :"+n*price);
    }

    public void chat(View view) {
//        String url = "https://api.whatsapp.com/send?phone=+917305510075";
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(url));
//        startActivity(intent);
        try {
            getCurrentLocation();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        AlertDialog.Builder alertDia = new AlertDialog.Builder(this);
        alertDia.setMessage("Sorry Delivery is not available");
        alertDia.setTitle("Alert!!!");
        alertDia.setCancelable(false);
        alertDia.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
        alertDialog.setMessage("Please select mode of delivery");
        alertDialog.setTitle("Alert!!!");
        alertDialog.setCancelable(false);

        alertDialog.setNegativeButton("PickUp", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String lati = latetx.getText().toString();
                String longi = longtx.getText().toString();
//                String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Double.parseDouble(lati),Double.parseDouble(longi));
//                //Log.d
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
//                startActivity(intent);
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:<" + "10.657547"  + ">,<" + "77.036263" + ">?q=<" + "10.657547"  + ">,<" + "77.036263" + ">("+"seller"+")"));
                startActivity(intent);
            }
        });
        alertDialog.setPositiveButton("Door Delivery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDia.show();
            }
        });
        AlertDialog dialog = alertDialog.create();
        Log.d("hello",lat+"pickup");
        dialog.show();
//        Intent intent = new Intent(getApplicationContext(),camera.class);
//        startActivity(intent);
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

    public void getCurrentLocation() throws InterruptedException {
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

        LocationServices.getFusedLocationProviderClient(cart.this).getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(@NonNull Location location) {


                Log.d("hello", "" + lat + "lastLoc");
//                lat =String.valueOf( location.getLatitude());
//                longg = String.valueOf(location.getLongitude());
                //lat = location.getLatitude();
                latetx.setText(""+location.getLatitude());
                longtx.setText(""+location.getLongitude());
                //longg = location.getLongitude();
                Toast.makeText(getApplicationContext(), "" + lat + "lastloc", Toast.LENGTH_LONG).show();
                Log.d("hello", "" + lat + "lat in success");
//                latTxt.setText(""+location.getLatitude());
//                longTxt.setText(""+location.getLongitude());
            }
        });
//        Log.d("hello", "" + longtlat + "longtlat last");
     //   return  "";
    }

}