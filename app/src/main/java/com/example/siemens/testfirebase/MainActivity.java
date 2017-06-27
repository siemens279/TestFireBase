package com.example.siemens.testfirebase;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    String TAG = "MyLog";
    EditText etName, etPas;
    Button bIn, bReg;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();

        etName = (EditText) findViewById(R.id.editTextName);
        etPas = (EditText) findViewById(R.id.editTextPas);
        bIn = (Button) findViewById(R.id.buttonIn);
        bReg = (Button) findViewById(R.id.buttonReg);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                    Intent intent = new Intent(MainActivity.this, NewDateBaseLoad.class);
                    startActivity(intent);
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
        mAuth.addAuthStateListener(mAuthListener);

        bIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                singin(etName.getText().toString(), etPas.getText().toString());
            }
        });
        bReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registration(etName.getText().toString(), etPas.getText().toString());
            }
        });
    }

    public void singin(String name, String pas) {
        mAuth.signInWithEmailAndPassword(name, pas).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Goog avtorization", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Bad avtorization", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, task.toString());
                }
            }
        });
    }
    public void registration(String name, String pas) {
        mAuth.createUserWithEmailAndPassword(name, pas).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "Goog registration", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Bad registration", Toast.LENGTH_SHORT).show();
                    Log.d(TAG, task.getException().getMessage());
                }
            }
        });
    }

}
