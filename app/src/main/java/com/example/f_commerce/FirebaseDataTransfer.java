package com.example.f_commerce;

import android.net.Uri;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class FirebaseDataTransfer  {
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
    }



}
