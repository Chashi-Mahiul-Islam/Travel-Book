package com.example.user.last_try;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class home_emergency extends AppCompatActivity {
    EditText number;
    FirebaseDatabase database;
    SmsManager smsManager;
    DatabaseReference number_add_database;
    private GridLayoutManager gridLayout;
    private RecyclerView recyclerView;
    private MyAdapter_For_Emergency_Numbers adapter;
    private List<recycler_item_for_emergency_numbers> listItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        smsManager= SmsManager.getDefault();//add
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_emergency);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        gridLayout = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayout);
        listItems = new ArrayList<>();
        database = FirebaseDatabase.getInstance();
        number_add_database=database.getReference(MainActivity.usernames+"last_emergency_numbers");
        number=(EditText)findViewById(R.id.number);
        adapter = new MyAdapter_For_Emergency_Numbers(listItems, this);
        recyclerView.setAdapter(adapter);
        number_add_database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                recycler_item_for_emergency_numbers ekta= dataSnapshot.getValue(recycler_item_for_emergency_numbers.class);
                listItems.add(ekta);
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
    public void submit(View view){
        if(number.getText().toString().equals("")){
            Toast.makeText(this, "please enter a number", Toast.LENGTH_LONG).show();
        }
        String s=number.getText().toString();
        number.setText("");
        String mGroupId = number_add_database.push().getKey();
        number_add_database.child(mGroupId).setValue(new recycler_item_for_emergency_numbers(s,mGroupId));
    }
    public void main_page(View view){
        Intent intent=new Intent(this,Mainpage.class);
        startActivity(intent);
    }
    public void emergency(View view){
        number_add_database.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                recycler_item_for_emergency_numbers S=dataSnapshot.getValue(recycler_item_for_emergency_numbers.class);
                String number = S.number;
                try {
                    smsManager.sendTextMessage(number, null, "please help me i am" + MainActivity.usernames + " location" + Mainpage.current_address + "latitude:" + Mainpage.latitude + "longitude:" + Mainpage.longitude, null, null);
                } catch (Exception e) {
                    Toast.makeText(getBaseContext(), number + " is not valid numbers\n", Toast.LENGTH_SHORT).show();
                }

            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String previousChildName) {
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String previousChildName) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}

