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
import android.widget.Button;
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
import java.util.List;

public class list_tester_for_my_diary2 extends AppCompatActivity {
    FirebaseStorage storage;Spinner spinner;//sp1 kondin,spinner category
    int kon_din,koydin;Button voteSongkha;
    String address;
    List<String> list= new ArrayList<String>();
    ke_koydin ekjoner_class_for_vote;
    private GridLayoutManager gridLayout;
    DatabaseReference myRef_for_vote,myRef_for_vote_2,myRef_sob_diary;
    private RecyclerView recyclerView;
    private MyAdapter_my_diary2 adapter;ke_koydin for_vote_path;
    private List<recycler_item_my_diary2> listItems;
    String database_path_for_vote;
    FirebaseDatabase database; StorageReference mStorageRef;
    DatabaseReference myRef; Bitmap bitmap=null;String database_path_for_vote_consistency;
    ArrayAdapter<CharSequence> adapterspinner;Button commentSongkha;ArrayList<String>voters=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tester_for_my_diary2);
        storage=FirebaseStorage.getInstance();
        mStorageRef=storage.getReference();
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        gridLayout = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayout);
        spinner = (Spinner) findViewById(R.id.spinner);
        listItems = new ArrayList<>();
        adapter = new MyAdapter_my_diary2(listItems, this);
        recyclerView.setAdapter(adapter);
        Intent iin= getIntent();
        database = FirebaseDatabase.getInstance();
        Bundle b = iin.getExtras();
        voteSongkha=(Button)findViewById(R.id.voteSongkha);
        commentSongkha=(Button)findViewById(R.id.commentSongkha);
        if(b!=null) {
            database = FirebaseDatabase.getInstance();
            koydin = (int) b.get("koydin");
            address = (String) b.get("database_address");

            myRef_sob_diary = database.getReference("all diary");
            Toast.makeText(getApplicationContext(),address+String.valueOf(koydin), Toast.LENGTH_SHORT).show();

            myRef = database.getReference(address);
            for(int i=1;i<=koydin;i++) {
                list.add("day"+String.valueOf(i));
            }
            ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, list);
            adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adp1);
            database.getReference(address+"comments").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists() == false || dataSnapshot == null ||dataSnapshot.getChildren() == null) {
                        commentSongkha.setText("0");
                    }else{
                        int child=(int)dataSnapshot.getChildrenCount();
                        commentSongkha.setText(String.valueOf(child));
                    }



                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });
            database.getReference("all diary").child(address).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ekjoner_class_for_vote= dataSnapshot.getValue(ke_koydin.class);
                    voteSongkha.setText(String.valueOf( ekjoner_class_for_vote.votes));
                    database_path_for_vote_consistency=ekjoner_class_for_vote.database_path_for_dual;

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });
            database.getReference(address+ "individualvotes").addChildEventListener( new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    voters.add(dataSnapshot.getValue(String.class));

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

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });



        }

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                String s=parent.getItemAtPosition(pos).toString();
                s=s.substring(3);
                kon_din=Integer.parseInt(s);

                listItems.clear();
                adapter.notifyDataSetChanged();
                ChildEventListener childEventListener = new ChildEventListener() {
                    @Override
                    public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {

                        final ekta_event Ekta = dataSnapshot.getValue(ekta_event.class);

                        Toast.makeText(getApplicationContext(), String.valueOf(kon_din), Toast.LENGTH_SHORT).show();
                        if (Ekta.day == kon_din) {

                            listItems.add(new recycler_item_my_diary2(Ekta.text, Ekta.image_path, Ekta.latitude, Ekta.longitude, Ekta.place_name));
                            adapter.notifyDataSetChanged();


                        }
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
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                };
                myRef.addChildEventListener(childEventListener);



            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void comment(View view){
        Intent intent=new Intent(this,list_tester_for_comments.class);
        intent.putExtra("address",address+"comments");
        startActivity(intent);
    }
    public void vote(View view){
        if(voters.contains(MainActivity.usernames)){
            Toast.makeText(getApplicationContext(),"You have already voted for it", Toast.LENGTH_SHORT).show();
        }else {
            ekjoner_class_for_vote.votes++;
            database.getReference(ekjoner_class_for_vote.kon_desh).child(database_path_for_vote_consistency).setValue(ekjoner_class_for_vote);
            database.getReference("all diary").child(address).setValue(ekjoner_class_for_vote);
            database.getReference(address+ "individualvotes").push().setValue(MainActivity.usernames);
            Toast.makeText(getApplicationContext(), ekjoner_class_for_vote.ke + ekjoner_class_for_vote.koydin + String.valueOf(ekjoner_class_for_vote.votes), Toast.LENGTH_SHORT).show();
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
