package com.example.user.last_try;

import android.content.Intent;
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
import android.widget.Spinner;
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
//ekta specific address er sob user er kaj
public class list_tester4 extends AppCompatActivity {
    FirebaseStorage storage;
    String category;
    private GridLayoutManager gridLayout;
    private RecyclerView recyclerView;
    private Myadapter4 adapter;
    private List<RecyclerItem4> listItems;
    FirebaseDatabase database; StorageReference mStorageRef;int polling=1;
    DatabaseReference myRef; Bitmap bitmap=null;
    ArrayAdapter<CharSequence> adapterspinner;Spinner spinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tester4);
        storage = FirebaseStorage.getInstance();
        mStorageRef = storage.getReference();
        database = FirebaseDatabase.getInstance();
        spinner = (Spinner) findViewById(R.id.spinner);
        adapterspinner = ArrayAdapter.createFromResource(this, R.array.category_names555, android.R.layout.simple_spinner_item);
        adapterspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterspinner);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        gridLayout = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayout);
        listItems = new ArrayList<>();
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            String address = (String) b.get("address");
            myRef = database.getReference(address);
            adapter = new Myadapter4(listItems, this, address);
            recyclerView.setAdapter(adapter);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {
                    category = parent.getItemAtPosition(pos).toString();

                    listItems.clear();
                    adapter.notifyDataSetChanged();
                    ChildEventListener childEventListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                            final ekta_jaygar_asepase_ke_gese_ki_likhse Ekta = dataSnapshot.getValue(ekta_jaygar_asepase_ke_gese_ki_likhse.class);
                            if(category.contains("select a category")||category.contains(Ekta.category)) {
                                int votes=0;
                                database.getReference(Ekta.image_path+"votes").addValueEventListener(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                        if(dataSnapshot.exists() == false || dataSnapshot == null ||dataSnapshot.getChildren() == null) {
                                            listItems.add(new RecyclerItem4(Ekta.text, Ekta.image_path, Ekta.latitude, Ekta.longitude, Ekta.place_name, Ekta.category, Ekta.ke_gese,0));
                                            Collections.sort(listItems);
                                            adapter.notifyDataSetChanged();
                                        }else{
                                            int  votes=(int)dataSnapshot.getChildrenCount();
                                            listItems.add(new RecyclerItem4(Ekta.text, Ekta.image_path, Ekta.latitude, Ekta.longitude, Ekta.place_name, Ekta.category, Ekta.ke_gese,votes));
                                            Collections.sort(listItems);
                                            adapter.notifyDataSetChanged();
                                        }
                                        database.getReference(Ekta.image_path+"votes").removeEventListener(this);



                                    }

                                    @Override
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
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                }
            });
        }
    }
    public void go_to_mainpage(View view){
        Intent intent=new Intent(this,Mainpage.class);
        startActivity(intent);
    }
    public void go_to_searchpage(View view){
        Intent intent=new Intent(this,searchpage.class);
        startActivity(intent);
    }
}

