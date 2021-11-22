package com.example.f_commerce;

import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.IBinder;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class FirebaseDataTransfer extends Service {
    DatabaseReference databaseReference;
    String itemName,itemSpecies,itemDescription,itemMRP;
    Uri fileUri;

    public void FirebaseDataTransfer(){

    }
    public void UploadItem( String name, String species, String description, String MRP){
        itemName = name;
        itemSpecies = species;
        itemDescription = description;
        itemMRP = MRP;
       // fileUri = uri;
        databaseReference = FirebaseDatabase.getInstance("https://f-commerce-34ffe-default-rtdb.asia-southeast1.firebasedatabase.app").getReference("seller");
        String ref = databaseReference.push().getKey();
        databaseReference.child(ref).child("name").setValue(name);
        databaseReference.child(ref).child("species").setValue(species);
        databaseReference.child(ref).child("description").setValue(description);
        databaseReference.child(ref).child("mrp").setValue(MRP);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Toast.makeText(getApplicationContext(),"success",Toast.LENGTH_LONG).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
