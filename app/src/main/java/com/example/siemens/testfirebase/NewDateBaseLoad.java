package com.example.siemens.testfirebase;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewDateBaseLoad extends AppCompatActivity {

    private Toolbar toolbar;
    private FirebaseAuth mAuth;
    Button bLoad,bSave;
    EditText etAdd;
    //RecyclerView rv;
    DatabaseReference myRef;
    FirebaseUser user = mAuth.getInstance().getCurrentUser();

    RecyclerView mRecyclerView;
    ArrayList<Drug> drug = new ArrayList<>();
    FireBaseRecyclerAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppThemeDefault);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.database_activity);
        mAuth = FirebaseAuth.getInstance();

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Работа с базой данных");
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menuExit:
                        //Log.d("MyLog", "Press Exit button");
                        mAuth.signOut();
                        startActivity(new Intent(NewDateBaseLoad.this, MainActivity.class));
                        break;
                }
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu);

        bLoad = (Button) findViewById(R.id.buttonLoad);
        bSave = (Button) findViewById(R.id.buttonSave);
        etAdd = (EditText) findViewById(R.id.editTextAdd);
        mRecyclerView = (RecyclerView) findViewById(R.id.rv);
        //etAdd.setFocusable(false);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference();

        bSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Drug d = new Drug(etAdd.getText().toString(), "qwerty");
                //myRef.child(user.getUid()).child("Table").push().setValue(d);
                for (int i=0;i<100;i++) {
                    Drug d = new Drug(i, etAdd.getText().toString()+i, "qwerty"+i);
                    //myRef.child(user.getUid()).child("Table").push().setValue(d);
                    //Log.d("MyLog", "Add: "+i);

                    Map<String, Object> postValues = d.toMap();

                    Map<String, Object> childUpdates = new HashMap<>();
                    childUpdates.put("/posts/" + d.getId(), postValues);
                    //childUpdates.put("/user-posts/" + i + "/" + key, postValues);

                    myRef.child(user.getUid()).updateChildren(childUpdates);

                }
            }
        });

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        myRef.child(user.getUid()).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                //GenericTypeIndicator<Map<String, Drug>> t = new GenericTypeIndicator<Map<String, Drug>>() {};
                //Map<String, Drug> list = dataSnapshot.child("Table").getValue(t);
                //   GenericTypeIndicator<List<Drug>> t = new GenericTypeIndicator<List<Drug>>() {};
                //   List<Drug> list = dataSnapshot.child("Table").getValue(t);
                //String value = dataSnapshot.getValue(String.class);
                   //Log.d("MyLog", "Size: ");
                drug.clear();
                for (DataSnapshot child: dataSnapshot.child("posts").getChildren()) {
                    drug.add(child.getValue(Drug.class));
                }
//                for (int i=0;i<drug.size();i++) {
//                    Log.d("MyLog", "-" + drug.get(i).name);
//                }
                adapter = new FireBaseRecyclerAdapter(drug, new FireBaseRecyclerAdapter.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        switch (view.getId()) {
                            case R.id.textViewName:
                                Log.d("MyLog", "Name "+position);
                                break;
                        }
                    }
                });
                mRecyclerView.setAdapter(adapter);
            }
            @Override
            public void onCancelled(DatabaseError error) {
                // Failed to read value
                Log.w("MyLog", "Failed to read value.", error.toException());
            }
        });

        bLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //myRef.child(user.getUid()).child("Table");
                //Log.d("MyLog", "Count:"+myRef.hashCode());
                //myRef.child(user.getUid()).child("Table").orderByChild("text").equals("qwerty4");
                Query myTopPostsQuery = myRef.child(user.getUid()).child("posts").orderByChild("text").equalTo(etAdd.getText().toString());
                //Log.d("MyLog", "Query:"+myTopPostsQuery.toString());
                myTopPostsQuery.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        int o = 0;
                        for (DataSnapshot child: dataSnapshot.getChildren()) {
                            o=1;
                            //drug.add(child.getValue(Drug.class));
                            Drug dd = child.getValue(Drug.class);
                            //Log.d("MyLog", "Result:"+dd.getName());
                            Toast.makeText(getApplicationContext(), "Yes", Toast.LENGTH_SHORT).show();
                        }
                        if (o==0) Toast.makeText(getApplicationContext(), "No", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("MyLog", "Error");
                    }
                });
                //Log.d("MyLog", "Result:"+myRef.toString());
            }
        });


    }





}
