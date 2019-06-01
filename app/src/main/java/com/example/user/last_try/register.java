package com.example.user.last_try;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class register extends AppCompatActivity {
    EditText username,password;
    user[]S=new user[10000];
    int i=0;
    FirebaseDatabase database;
    DatabaseReference myRef;
    DatabaseReference users,koytauser;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username=(EditText)findViewById(R.id.username);
        password=(EditText)findViewById(R.id.password);
        database= FirebaseDatabase.getInstance();
        myRef= database.getReference();
        users=database.getReference("users");
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                //Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                S[i]=dataSnapshot.getValue(user.class);
                //  Toast.makeText(getApplicationContext(), S[i].username, Toast.LENGTH_SHORT).show();

                i++;

                // ...
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
                //Log.d(TAG, "onChildChanged:" + dataSnapshot.getKey());

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {
//Log.d(TAG, "onChildMoved:" + dataSnapshot.getKey());



            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        // myRef.addValueEventListener(postListener);
        users.addChildEventListener(childEventListener);
    }
    public void signin(View view){
        String name=username.getText().toString();
        String pass=password.getText().toString();
        for(int k=0;k<i;k++){
            if(S[k].username.equals(name)){

                Toast.makeText(getApplicationContext(), "try another uiser name", Toast.LENGTH_SHORT).show();
                username.setText("");

                return;

            }
        }

        Toast.makeText(getApplicationContext(), "you are registered now", Toast.LENGTH_SHORT).show();
        user d=new user(name,pass);
        users.push().setValue(d);
        MainActivity.usernames=name;
        Intent intent=new Intent(register.this,Mainpage.class);
        // Intent intent=new Intent(this,profileActivity.class);
        startActivity(intent);

    }
}
