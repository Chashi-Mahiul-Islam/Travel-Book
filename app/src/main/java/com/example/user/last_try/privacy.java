package com.example.user.last_try;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
public class privacy extends AppCompatActivity {
    String title,database_path;
    int event,koy_number_din,koy_diner_journey;
    FirebaseDatabase database;
    DatabaseReference myRef;EditText contributor_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_privacy);

        contributor_name=(EditText)findViewById(R.id.contributor_name);
        Intent iin= getIntent();

        Bundle b = iin.getExtras();

        if(b!=null) {
            title=(String)b.get("title");
            event= (int) b.get("event");
            koy_number_din=(int)b.get("day");
            koy_diner_journey=(int)b.get("koy_dine_sesh");
            database_path=(String)b.get("database_path");


            database = FirebaseDatabase.getInstance();
            myRef = database.getReference(database_path+"contributorsprivacy");//myRefg aman bangladesh




        }
    }
    public void add(View view){
        if(contributor_name.getText().toString().equals("")){
            Toast.makeText(privacy.this, "please enter any contributor user name", Toast.LENGTH_LONG).show();
        }
        myRef.child(contributor_name.getText().toString()).setValue(1);
        database.getReference(database_path+"contributors").push().setValue(contributor_name.getText().toString());
        contributor_name.setText("");

    }
    public void done(View view){
        Intent intent=new Intent(this,eventadd.class);
        intent.putExtra("title",title);
        intent.putExtra("event",1);
        intent.putExtra("day",1);
        intent.putExtra("koy_dine_sesh",koy_diner_journey);
        intent.putExtra("database_path",database_path);
        startActivity(intent);
    }
}

