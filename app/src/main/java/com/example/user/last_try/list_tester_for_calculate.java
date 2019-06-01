package com.example.user.last_try;


import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class list_tester_for_calculate extends AppCompatActivity {
    private GridLayoutManager gridLayout;

    private RecyclerView recyclerView;
    private MyAdapter_for_hisab adapter;
    private List<hisab> listItems;
    double total_cost;
    EditText summary;
    FirebaseDatabase database;
    String address;
    DatabaseReference myRef, myRef2;
    HashMap<String, Integer> map;
    int number_of_contributors = 1;
    double each_person_koto_dibe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        map = new HashMap<>();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tester_for_calculate);
        database = FirebaseDatabase.getInstance();
        summary = (EditText) findViewById(R.id.summary);
        //Toast.makeText(getApplicationContext(),MainActivity.usernames+notunjourney.kon_desh+String.valueOf(eventadd.koy_number_din), Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        gridLayout = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayout);

        listItems = new ArrayList<>();

        Intent iin = getIntent();

        Bundle b = iin.getExtras();

        if (b != null) {
            address = (String) b.get("address");
            Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
            myRef = database.getReference(address + "hisab");
            myRef2 = database.getReference(address + "contributors");
            myRef2.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {
                        number_of_contributors = 1;
                    } else {
                        number_of_contributors = (int) dataSnapshot.getChildrenCount() + 1;

                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });
            myRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {
                        summary.setText("no history");
                    } else {


                    }


                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });

            adapter = new MyAdapter_for_hisab(listItems, this);
            recyclerView.setAdapter(adapter);
            listItems.add(new hisab("ki kaj", "ke dise", "amount"));
            //Toast.makeText(getApplicationContext(),ekta.ke_comment_korse, Toast.LENGTH_SHORT).show();
            adapter.notifyDataSetChanged();
            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    hisab ekta = dataSnapshot.getValue(hisab.class);
                    total_cost += Integer.parseInt(ekta.amount);
                    listItems.add(ekta);
                    adapter.notifyDataSetChanged();
                    each_person_koto_dibe = total_cost / number_of_contributors;
                    if (map.containsKey(ekta.ke_taka_dise)) {
                        int inc = map.get(ekta.ke_taka_dise);
                        map.put(ekta.ke_taka_dise, inc + Integer.parseInt(ekta.amount));

                    } else {
                        map.put(ekta.ke_taka_dise, Integer.parseInt(ekta.amount));
                    }
                    String po = "every one will give " + String.valueOf(each_person_koto_dibe) + "except\n";
                    Set<String> setCodes = map.keySet();
                    Iterator<String> iterator = setCodes.iterator();

                    while (iterator.hasNext()) {
                        String keydise = iterator.next();
                        int kototakadise = map.get(keydise);
                        double balance = (kototakadise - each_person_koto_dibe);
                        if (balance > 0) {
                            po += keydise + " " + String.valueOf(balance) + "taka pabe\n";
                        } else {
                            po += keydise + " " + String.valueOf(-balance) + " taka dibe\n";
                        }

                    }
                    summary.setText(po);
                    po = "";
                    //Toast.makeText(getApplicationContext(),ekta.ke_comment_korse, Toast.LENGTH_SHORT).show();


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
            };
            myRef.addChildEventListener(childEventListener);
        }
    }

    public void clear(View view) {
        database.getReference(address + "hisab").setValue(null);
        listItems.clear();
    }
}
