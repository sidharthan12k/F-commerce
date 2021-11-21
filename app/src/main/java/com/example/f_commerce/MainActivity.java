package com.example.f_commerce;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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

import java.io.Console;
import java.util.regex.Pattern;

import static android.widget.Toast.LENGTH_LONG;

public class MainActivity extends AppCompatActivity {
    EditText e1, e2;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    DatabaseReference database;
    Switch s1;
    int n = 0;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1 = (EditText) findViewById(R.id.editText1);
        e2 = (EditText) findViewById(R.id.editText2);
        s1 = (Switch) findViewById(R.id.switch2);
        firebaseAuth = FirebaseAuth.getInstance();
        //firebaseDatabase = FirebaseDatabase.getInstance();
        //database = firebaseDatabase.getReference("hello here");
        database = FirebaseDatabase
                .getInstance("https://f-commerce-34ffe-default-rtdb.asia-southeast1.firebasedatabase.app")
                .getReference("users").child("");
        print();
    }

    private void print() {
        database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String str = snapshot.getValue().toString();
                Log.d("hlo", " " + str);
                //Toast.makeText(getApplicationContext(), str, LENGTH_LONG).show();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

    public void signin(View view) {
        Intent intent = new Intent(this, signin.class);
        startActivity(intent);
    }

    public void login(View view) {
        String usr = e1.getText().toString();
        String pswd = e2.getText().toString();
        if (usr == null || pswd == null) {
            Toast.makeText(this, "enter valid username or password", Toast.LENGTH_SHORT).show();
        } else {
            if (isValid(usr)) {
            }
            if (pswd.length() < 8) {
                e2.setError("enter valid username");
            } else {
                login(usr, pswd);
            }
        }
    }

    private void login(String usr, String pswd) {
        firebaseAuth.signInWithEmailAndPassword(usr, pswd).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(getApplicationContext(), "valid username and password", LENGTH_LONG).show();
                    if (s1.isChecked()) {
                        Intent intent = new Intent(getApplicationContext(), SellerHomePage.class);
                        intent.putExtra("type", s1.isChecked());
                        startActivity(intent);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), homePage.class);
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(getApplicationContext(), "enter a valid username and password", LENGTH_LONG).show();
                }
            }
        });
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


}