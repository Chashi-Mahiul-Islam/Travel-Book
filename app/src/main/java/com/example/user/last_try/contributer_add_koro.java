package com.example.user.last_try;


import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Collections;

public class contributer_add_koro extends AppCompatActivity {
    EditText contribute_to;FirebaseDatabase database;DatabaseReference myRef,myRef2,myRef3,myRef_for_privacy;
    DatabaseReference journey_continue,journey_continue2;
    boolean is_first=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contributer_add_koro);
        contribute_to=(EditText)findViewById(R.id.contribute_to);
        database = FirebaseDatabase.getInstance();
    }
    public void contributer_add_kor(View view){
        final String jar_journey_te_contribute_kortasi=contribute_to.getText().toString();
        journey_continue = database.getReference(jar_journey_te_contribute_kortasi+"currentjourney");
        myRef2 = database.getReference(MainActivity.usernames+"nijerjourney");
        journey_continue.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                    Toast.makeText(getApplicationContext(), jar_journey_te_contribute_kortasi + "has no recent journey", Toast.LENGTH_SHORT).show();
                    return;
                }

                final continue_journey ekta = dataSnapshot.getValue(continue_journey.class);
                myRef_for_privacy = database.getReference(ekta.database_path + "contributorsprivacy");
                myRef_for_privacy.child(MainActivity.usernames).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                            Toast.makeText(getApplicationContext(), "sorry you are not allowed to contribute", Toast.LENGTH_SHORT).show();


                        } else if(is_first==true){
                            is_first=false;
                            myRef2.push().setValue(new nijer_journey_details(notunjourney.kon_desh, notunjourney.koydiner_journey, ekta.database_path, ekta.title));
                            journey_continue2 = database.getReference(MainActivity.usernames + "currentjourney");
                            continue_journey Continue_Journey2 = new continue_journey(1, 1, ekta.database_path, false, notunjourney.koydiner_journey, notunjourney.kon_desh, ekta.title);
                            journey_continue2.setValue(Continue_Journey2);
                            Intent intent = new Intent(getApplicationContext(), eventadd.class);
                            intent.putExtra("title", ekta.title);
                            intent.putExtra("event", 1);
                            intent.putExtra("day", 1);
                            intent.putExtra("koy_dine_sesh", notunjourney.koydiner_journey);
                            intent.putExtra("database_path", ekta.database_path);
                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });



            }

            public void onCancelled(DatabaseError databaseError) {
            }
        });


    }
}
