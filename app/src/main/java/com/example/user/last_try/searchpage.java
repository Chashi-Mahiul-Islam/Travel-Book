package com.example.user.last_try;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
public class searchpage extends AppCompatActivity {
    int number_of_days;
    private GridLayoutManager gridLayout; DateFormat dateFormat;
    private RecyclerView recyclerView;ArrayList<String>kon_kon_desh_search_korsos=new ArrayList<>();ArrayList<previous_search_history>stories=new ArrayList<>();
    private MyAdapter adapter;boolean kichu_serachdisos=false;
    private List<RecyclerItem> listItems;
    EditText Days;
    FirebaseStorage storage;StorageReference mStorageRef;String country;
    FirebaseDatabase database;static String age_kon_desh_search_korsi="";
    DatabaseReference myRef,myRef2,myRef_for_previous_search;boolean prothomsearch=true;
    ChildEventListener childEventListener;
    EditText AreaName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchpage);
        storage=FirebaseStorage.getInstance();
        mStorageRef=storage.getReference();
        database = FirebaseDatabase.getInstance();
        Days=(EditText)findViewById(R.id.Days);
        myRef_for_previous_search=database.getReference(MainActivity.usernames+"search history");
        myRef=database.getReference("all diary");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        AreaName=(EditText)findViewById(R.id.AreaName);
        gridLayout = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(gridLayout);
        listItems = new ArrayList<>();
        adapter = new MyAdapter(listItems, this);
        recyclerView.setAdapter(adapter);
        myRef_for_previous_search.addValueEventListener(new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {
                if ((prothomsearch==true&&dataSnapshot.exists() == false) || (prothomsearch==true&&dataSnapshot == null) || (prothomsearch==true&&dataSnapshot.getChildren() == null)) {//kono search history nai sob dekhao
                    listItems.clear();
                    adapter.notifyDataSetChanged();
                    prothomsearch = false;
                    childEventListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                            final String key = dataSnapshot.getKey();
                            final ke_koydin ek = dataSnapshot.getValue(ke_koydin.class);
                            if (ek.journey_type.contains("single")) {
                                listItems.add(new RecyclerItem(ek.ke, String.valueOf(ek.koydin), ek.ke + ek.kon_desh +ek.journey_title+ "cover pic", 0, ek.votes, ek.database_path_for_dual, ek.kon_desh, ek.journey_title));
                                Collections.sort(listItems);
                                adapter.notifyDataSetChanged();

                            } else {
                                myRef2 = database.getReference(ek.ke + ek.kon_desh +ek.journey_title+ "contributors");

                                myRef2.addValueEventListener(new ValueEventListener() {
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        final int counts = (int) dataSnapshot.getChildrenCount();
                                        listItems.add(new RecyclerItem(ek.ke, String.valueOf(ek.koydin), ek.ke + ek.kon_desh +ek.journey_title+ "cover pic", counts, ek.votes, ek.database_path_for_dual, ek.kon_desh, ek.journey_title));
                                        Collections.sort(listItems);
                                        adapter.notifyDataSetChanged();
                                       // myRef2.removeEventListener(this);
                                    }
                                    public void onCancelled(DatabaseError databaseError) {
                                    }
                                });
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
                    };
                    myRef.addChildEventListener(childEventListener);
                } else {
                    if(prothomsearch==true){
                        prothomsearch = false;

                        listItems.clear();
                        adapter.notifyDataSetChanged();
                        myRef_for_previous_search.addValueEventListener(new ValueEventListener() {
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                final previous_search_history ekta = dataSnapshot.getValue(previous_search_history.class);
                                myRef = database.getReference(ekta.kon_desh);
                                age_kon_desh_search_korsi=ekta.kon_desh;
                                childEventListener = new ChildEventListener() {
                                    @Override
                                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                                        final String key = dataSnapshot.getKey();
                                        final ke_koydin ek = dataSnapshot.getValue(ke_koydin.class);
                                        if (ek.journey_type.contains("single")) {
                                            listItems.add(new RecyclerItem(ek.ke, String.valueOf(ek.koydin), ek.ke + ek.kon_desh +ek.journey_title+ "cover pic", 0, ek.votes, key, ek.kon_desh, ek.journey_title));
                                            Collections.sort(listItems);
                                            adapter.notifyDataSetChanged();
                                        } else {
                                            myRef2 = database.getReference(ek.ke + ek.kon_desh + ek.journey_title+"contributors");

                                            myRef2.addValueEventListener(new ValueEventListener() {
                                                public void onDataChange(DataSnapshot dataSnapshot) {
                                                    final int counts = (int) dataSnapshot.getChildrenCount();

                                                    listItems.add(new RecyclerItem(ek.ke, String.valueOf(ek.koydin), ek.ke + ek.kon_desh +ek.journey_title+ "cover pic", counts, ek.votes, key, ek.kon_desh, ek.journey_title));
                                                    Collections.sort(listItems);
                                                    adapter.notifyDataSetChanged();
                                       //             myRef2.removeEventListener(this);
                                                }
                                                public void onCancelled(DatabaseError databaseError) {
                                                }
                                            });
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
                                };
                                myRef.addChildEventListener(childEventListener);
                            }
                            public void onCancelled(DatabaseError databaseError) {
                            }
                        });
                    }
                }
            }
            public void onCancelled(DatabaseError databaseError) { }
        });


    }
    public void go(View view){
        country=AreaName.getText().toString();
        if(country.equals("")){
            Toast.makeText(getApplicationContext(),"please enter a country name", Toast.LENGTH_LONG).show();
            return;
        }
        country=AreaName.getText().toString().substring(0, 1).toUpperCase() + AreaName.getText().toString().substring(1).toLowerCase();
        number_of_days=10000;
        if(Days.getText().toString().equals("")){}
        else {
            try {
                number_of_days = Integer.parseInt(Days.getText().toString());
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "please try an integer number of days", Toast.LENGTH_LONG).show();
                return;
            }
        }
        prothomsearch=false;
        myRef=database.getReference(country);
        for(int i=listItems.size()-1;i>=0;i--){
            listItems.remove(i);
            adapter.notifyDataSetChanged();
        }
        //listItems.clear();
        //adapter.notifyDataSetChanged();
        childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                final String key=dataSnapshot.getKey();
                final ke_koydin ek= dataSnapshot.getValue(ke_koydin.class);
                if(ek.journey_type.contains("single")) {
                    if(ek.koydin<=number_of_days) {
                        listItems.add(new RecyclerItem(ek.ke, String.valueOf(ek.koydin), ek.ke + ek.kon_desh + ek.journey_title + "cover pic", 0, ek.votes, key, ek.kon_desh, ek.journey_title));
                        Collections.sort(listItems);
                        adapter.notifyDataSetChanged();
                    }
                }else{
                    myRef2=database.getReference(ek.ke+ek.kon_desh+ek.journey_title+"contributors");

                    myRef2.addValueEventListener(new ValueEventListener() {
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            final int counts=(int)dataSnapshot.getChildrenCount();
                            if(ek.koydin<=number_of_days) {
                                listItems.add(new RecyclerItem(ek.ke, String.valueOf(ek.koydin), ek.ke + ek.kon_desh + ek.journey_title + "cover pic", counts, ek.votes, key, ek.kon_desh, ek.journey_title));
                                Collections.sort(listItems);
                                adapter.notifyDataSetChanged();
                            }
                        }
                        public void onCancelled(DatabaseError databaseError) { }
                    });
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
        };
        myRef.addChildEventListener(childEventListener);

    }
}
