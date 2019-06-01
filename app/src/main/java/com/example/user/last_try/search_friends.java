package com.example.user.last_try;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class search_friends extends AppCompatActivity {
    Spinner spinner;
    List<String> list_for_contributors= new ArrayList<String>();
    ArrayAdapter<String> adapter;
    FirebaseDatabase database;
    DatabaseReference myRef2,myRef;
    continue_journey Continue_Journey=null;
    static String person_selected;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_friends);
        spinner=(Spinner)findViewById(R.id.spinner);
        adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list_for_contributors);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        list_for_contributors.add("select a person to see on map");
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference(MainActivity.usernames + "currentjourney");//continue journey details
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                    Continue_Journey = null;//dataSnapshot.getValue(continue_journey.class);
                } else {
                    Continue_Journey = dataSnapshot.getValue(continue_journey.class);
                    database.getReference("creatorsofmultiplejourney").child(Continue_Journey.database_path).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                            } else {
                                String creator=dataSnapshot.getValue(String.class);
                                list_for_contributors.add(creator);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    myRef2=database.getReference(Continue_Journey.database_path+"contributors");
                    myRef2.addChildEventListener(new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                            String contributor=dataSnapshot.getValue().toString();
                            list_for_contributors.add(contributor);
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

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                person_selected=parent.getItemAtPosition(pos).toString();
                if(person_selected.equals("select a person to see on map")){

                }else{

                    Intent intent=new Intent(getApplicationContext(),MapsActivity_last_emergency.class);
                    startActivity(intent);

                }


            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }
    public void main_page(View view){
        Intent intent=new Intent(this,Mainpage.class);
        startActivity(intent);
    }
}
