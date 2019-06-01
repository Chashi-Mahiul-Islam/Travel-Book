package com.example.user.last_try;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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

public class list_tester_for_multiple_contributor_journey extends AppCompatActivity {
    FirebaseStorage storage;Spinner spinner;//sp1 kondin,spinner category,sp2 for karta dekhte chao
    static int kon_din;String database_path_for_vote;private Button commentSongkha,voteSongkha;
    static String kar_contribution="";
    static String category;Spinner sp1,sp2;List<String> list= new ArrayList<String>();
    List<String> list_for_contributors= new ArrayList<String>();
    private GridLayoutManager gridLayout;ArrayList<String>voters=new ArrayList<>();
    ArrayAdapter<String> adp2;
    private RecyclerView recyclerView;
    private Myadapter_for_multiple_journey adapter;
    private List<RecyclerItemForMultipleJourney> listItems;
    ke_koydin ekjoner_class_for_vote;
    FirebaseDatabase database; StorageReference mStorageRef;int polling=1;
    DatabaseReference myRef,myRef2,myRef_for_vote,myRef_for_vote_2,myRef_sob_diary; Bitmap bitmap=null;
    ArrayAdapter<CharSequence> adapterspinner;
    ArrayAdapter<CharSequence> adapterspinner2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_tester_for_multiple_contributor_journey);
        storage=FirebaseStorage.getInstance();
        mStorageRef=storage.getReference();
        commentSongkha=(Button)findViewById(R.id.commentSongkha);
        voteSongkha=(Button)findViewById(R.id.voteSongkha);
        database = FirebaseDatabase.getInstance();
        myRef =database.getReference(MyAdapter.database_key);
        myRef_sob_diary = database.getReference("all diary");
        myRef2 =database.getReference(MyAdapter.database_key+"contributors");
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        gridLayout = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayout);
        spinner = (Spinner) findViewById(R.id.spinner);
        sp2= (Spinner) findViewById(R.id.spinner3);
        adp2 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list_for_contributors);
        adp2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp2.setAdapter(adp2);
        listItems = new ArrayList<>();
//list.add("select a day");
        for(int i=1;i<=MyAdapter.database_koydin;i++) {
            list.add("day"+String.valueOf(i));
        }
        Intent iin= getIntent();

        Bundle b = iin.getExtras();

        if(b!=null) {
            final String main_person = (String) b.get("main_person");
            database_path_for_vote=(String)b.get("database_path_for_vote");
            myRef_for_vote =database.getReference(MyAdapter.kon_desh).child(database_path_for_vote);
            myRef_for_vote_2 = database.getReference(MyAdapter.database_key + "individualvotes");
            database.getReference(MyAdapter.database_key+"comments").addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    int commentcount=(int)dataSnapshot.getChildrenCount();
                    commentSongkha.setText(String.valueOf(commentcount));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });
            myRef_for_vote.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    ekjoner_class_for_vote=dataSnapshot.getValue(ke_koydin.class);
                    voteSongkha.setText(String.valueOf( ekjoner_class_for_vote.votes));
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {


                }
            });
            ChildEventListener childEventListener = new ChildEventListener() {
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
            };

            myRef_for_vote_2.addChildEventListener(childEventListener);


            list_for_contributors.add("select a contributor");
            list_for_contributors.add("all");
            list_for_contributors.add(main_person);

            adp2.notifyDataSetChanged();
            ChildEventListener childEventListener2 = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    String contributor=dataSnapshot.getValue().toString();
                    list_for_contributors.add(contributor);

                    adp2.notifyDataSetChanged();

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
            myRef2.addChildEventListener(childEventListener2);

        }

        kar_contribution="select a contributor";
        category="select a category";
        adapter = new Myadapter_for_multiple_journey(listItems, this);
        recyclerView.setAdapter(adapter);
        adapterspinner = ArrayAdapter.createFromResource(this, R.array.category_names2, android.R.layout.simple_spinner_item);
        adapterspinner.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapterspinner);
        sp1= (Spinner) findViewById(R.id.spinner2);

        ArrayAdapter<String> adp1 = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, list);
        adp1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        sp1.setAdapter(adp1);
        sp1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View selectedItemView, int pos, long id) {
                String s=parent.getItemAtPosition(pos).toString();
                String mainstring=s;
                s=s.substring(3);
                kon_din=Integer.parseInt(s);

                if(kar_contribution.contains("select a contributor")||category.contains("select a category")){}
                else if(category.contains("map")){
                    Intent intent=new Intent(getApplicationContext(),MapsActivity_for_multiple_journey.class);
                    startActivity(intent);
                }else {
                    for(int i=listItems.size()-1;i>=0;i--){
                        listItems.remove(i);
                        adapter.notifyDataSetChanged();
                    }
                    // listItems.clear(); adapter.notifyDataSetChanged();
                    ChildEventListener childEventListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                            final ekta_event Ekta = dataSnapshot.getValue(ekta_event.class);
                            final String path = dataSnapshot.getKey();
                            //  Toast.makeText(getApplicationContext(), Ekta.category + "\t" + category, Toast.LENGTH_SHORT).show();
                            if ((category.contains(Ekta.category)||category.contains("all")) && Ekta.day == kon_din && (kar_contribution.contains(Ekta.ke_dise) || kar_contribution.contains("all"))) {

                                listItems.add(new RecyclerItemForMultipleJourney(Ekta.text, Ekta.image_path, path, Ekta.latitude, Ekta.longitude, Ekta.place_name, Ekta.ke_dise));
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
                // your code here
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
        sp2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {

                kar_contribution=parent.getItemAtPosition(pos).toString();

                if(kar_contribution.contains("select a contributor")||category.contains("select a category")){}
                else if(category.contains("map")){
                    Intent intent=new Intent(getApplicationContext(),MapsActivity_for_multiple_journey.class);
                    startActivity(intent);
                }else {

                    listItems.clear();
                    adapter.notifyDataSetChanged();
                    ChildEventListener childEventListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                            //Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                            final ekta_event Ekta = dataSnapshot.getValue(ekta_event.class);
                            final String path = dataSnapshot.getKey();

                            if ((category.contains(Ekta.category)||category.contains("all")) && Ekta.day == kon_din && (kar_contribution.contains(Ekta.ke_dise) || kar_contribution.contains("all"))) {

                                listItems.add(new RecyclerItemForMultipleJourney(Ekta.text, Ekta.image_path, path, Ekta.latitude, Ekta.longitude, Ekta.place_name, Ekta.ke_dise));
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

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view,
                                       int pos, long id) {
                Toast.makeText(getApplicationContext(), parent.getItemAtPosition(pos).toString(), Toast.LENGTH_SHORT).show();
                category=parent.getItemAtPosition(pos).toString();

                if(kar_contribution.contains("select a contributor")||category.contains("select a category")){}

                else if(category.contains("map")){
                    Intent intent=new Intent(getApplicationContext(),MapsActivity_for_multiple_journey.class);
                    startActivity(intent);
                }else {

                    listItems.clear();
                    adapter.notifyDataSetChanged();
                    ChildEventListener childEventListener = new ChildEventListener() {
                        @Override
                        public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                            //Log.d(TAG, "onChildAdded:" + dataSnapshot.getKey());
                            final ekta_event Ekta = dataSnapshot.getValue(ekta_event.class);
                            final String path = dataSnapshot.getKey();

                            if ((category.contains(Ekta.category)||category.contains("all")) && Ekta.day == kon_din&&(kar_contribution.contains(Ekta.ke_dise)||kar_contribution.contains("all"))) {
                                listItems.add(new RecyclerItemForMultipleJourney(Ekta.text, Ekta.image_path, path, Ekta.latitude, Ekta.longitude,Ekta.place_name,Ekta.ke_dise));
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

            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    public void vote(View view){
        if(voters.contains(MainActivity.usernames)){
            Toast.makeText(getApplicationContext(),"You have already voted for it", Toast.LENGTH_SHORT).show();
        }else {
            ekjoner_class_for_vote.votes++;
            myRef_for_vote.setValue(ekjoner_class_for_vote);

            myRef_sob_diary.child(MyAdapter.database_key).setValue(ekjoner_class_for_vote);
            myRef_for_vote_2.push().setValue(MainActivity.usernames);
            Toast.makeText(getApplicationContext(), ekjoner_class_for_vote.ke + ekjoner_class_for_vote.koydin + String.valueOf(ekjoner_class_for_vote.votes), Toast.LENGTH_SHORT).show();
        }

    }
    public void comment(View view){
        Intent intent=new Intent(this,list_tester_for_comments.class);
        intent.putExtra("address",MyAdapter.database_key+"comments");
        startActivity(intent);
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
