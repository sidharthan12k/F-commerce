package com.example.f_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class signin extends AppCompatActivity {
    EditText e1, e2, e3;
    private FirebaseAuth firebaseAuth;
    Switch s1;
    private DatabaseReference databaseReference;
    private FirebaseDatabase firebaseDatabase;
    setData sdata = new setData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);
        e1 = (EditText) findViewById(R.id.editTextt1);
        e2 = (EditText) findViewById(R.id.usr1);
        e3 = (EditText) findViewById(R.id.pswd1);
        s1 = (Switch) findViewById(R.id.switch1);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase
                .getInstance("https://f-commerce-34ffe-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("users");
        boolean b1 = s1.isChecked();
        if (b1 != false) {
            Toast.makeText(getApplicationContext(), "checked", Toast.LENGTH_SHORT).show();
        }
    }

    public static boolean isValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\." +
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

    public void Signin(View view) {
        String name = e1.getText().toString();
        String usr = e2.getText().toString();
        String pswd = e3.getText().toString();
        boolean b1 = s1.isChecked();
        firebaseAuth.createUserWithEmailAndPassword(usr, pswd).addOnCompleteListener(signin.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    upload(name, usr, pswd, b1);
                } else {
                    Toast.makeText(getApplicationContext(), "fail", Toast.LENGTH_LONG).show();
                    upload(name, usr, pswd, b1);
                }
            }
        });
    }

    private void upload(String name, String usr, String pswd, boolean b1) {
        sdata.users(name, usr);


        String str = databaseReference.child("users").getRef().getKey();

        str = databaseReference.child(usr).getRef().getKey();
        databaseReference.child(str).child("usr").setValue(usr);
        databaseReference.child(str).child("name").setValue(name);
        databaseReference.child(str).child("type").setValue(b1);

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
             //   Toast.makeText(getApplicationContext(), "data added", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}