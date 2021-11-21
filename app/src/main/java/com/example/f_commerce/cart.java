package com.example.f_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class cart extends AppCompatActivity {
    TextView t1;
    ImageView img1;
    EditText e1, e2;
    DatabaseReference database;

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
        t1 = findViewById(R.id.textView3);
        img1 = (ImageView) findViewById(R.id.imageView);
        Intent intent = getIntent();
        t1.setText(intent.getStringExtra("name"));
        img1.setImageResource(intent.getIntExtra("img", 0));
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

    public void chat(View view) {
//        String url = "https://api.whatsapp.com/send?phone=+917305510075";
//        Intent intent = new Intent(Intent.ACTION_VIEW);
//        intent.setData(Uri.parse(url));
//        startActivity(intent);
        Intent intent = new Intent(getApplicationContext(),camera.class);
        startActivity(intent);
    }
}