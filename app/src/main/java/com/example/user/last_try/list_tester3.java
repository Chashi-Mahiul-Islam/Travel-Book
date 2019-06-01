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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;
//upload er somoy nijer diary nijei dekhbe
public class list_tester3 extends AppCompatActivity {//recycler item 2
    FirebaseStorage storage;
    private GridLayoutManager gridLayout;
    private RecyclerView recyclerView;
    private MyAdapter3 adapter;
    private List<RecyclerItem2> listItems;//re
    FirebaseDatabase database; StorageReference mStorageRef;int polling=1;
    DatabaseReference myRef; Bitmap bitmap=null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tester3);
        storage=FirebaseStorage.getInstance();
        mStorageRef=storage.getReference();
        database = FirebaseDatabase.getInstance();
        myRef =database.getReference(eventadd.databasepath_ki);//amanbangladesh
        Toast.makeText(getApplicationContext(),MainActivity.usernames+notunjourney.kon_desh+String.valueOf(eventadd.koy_number_din), Toast.LENGTH_SHORT).show();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        gridLayout = new GridLayoutManager(this, 1);//2
        recyclerView.setLayoutManager(gridLayout);
        listItems = new ArrayList<>();
        adapter = new MyAdapter3(listItems, this);
        recyclerView.setAdapter(adapter);
        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                final ekta_event Ekta = dataSnapshot.getValue(ekta_event.class);
                final String path = dataSnapshot.getKey();
                if (Ekta.day == eventadd.koy_number_din) {

                    listItems.add(new RecyclerItem2(Ekta.text, Ekta.image_path, path, Ekta.latitude, Ekta.longitude, Ekta.place_name));
                    adapter.notifyDataSetChanged();

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

