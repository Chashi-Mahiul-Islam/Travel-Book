package com.example.user.last_try;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

import java.util.ArrayList;
import java.util.List;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
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

public class list_tester_for_comments extends AppCompatActivity {

    private EditText comment_kor;
    private GridLayoutManager gridLayout;

    private RecyclerView recyclerView;
    private MyAdapter_for_comments adapter;
    private List<recycler_item_for_comments> listItems;

    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_list_tester_for_comments);
        DisplayMetrics dm=new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width=dm.widthPixels;
        int height=dm.heightPixels;
        getWindow().setLayout((int)(width*.9),(int)(height*.9));
        getWindow().setBackgroundDrawable(new ColorDrawable(
                android.graphics.Color.TRANSPARENT));
        comment_kor = (EditText) findViewById(R.id.comment_kor);

        database = FirebaseDatabase.getInstance();

        //Toast.makeText(getApplicationContext(),MainActivity.usernames+notunjourney.kon_desh+String.valueOf(eventadd.koy_number_din), Toast.LENGTH_SHORT).show();

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        gridLayout = new GridLayoutManager(this, 1);
        recyclerView.setLayoutManager(gridLayout);

        listItems = new ArrayList<>();

        Intent iin = getIntent();

        Bundle b = iin.getExtras();

        if (b != null) {
            String address = (String) b.get("address");
            Toast.makeText(getApplicationContext(), address, Toast.LENGTH_SHORT).show();
            myRef = database.getReference(address);
            adapter = new MyAdapter_for_comments(listItems, this);
            recyclerView.setAdapter(adapter);

            ChildEventListener childEventListener = new ChildEventListener() {
                @Override
                public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                    recycler_item_for_comments ekta=dataSnapshot.getValue(recycler_item_for_comments.class);
                    listItems.add(ekta);
                    //Toast.makeText(getApplicationContext(),ekta.ke_comment_korse, Toast.LENGTH_SHORT).show();
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
            };
            myRef.addChildEventListener(childEventListener);

        }

    }


    public void comment(View view){
        String comment=comment_kor.getText().toString();
        comment_kor.setText("");
        //  Toast.makeText(getApplicationContext(),MainActivity.usernames+" "+comment, Toast.LENGTH_SHORT).show();
        myRef.push().setValue(new recycler_item_for_comments(MainActivity.usernames,comment));
    }

}

