package com.example.user.last_try;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class open_contributor_add extends AppCompatActivity {
    EditText contributer;DatabaseReference myRef;FirebaseDatabase database;
    String database_path;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_contributor_add);
        contributer=(EditText)findViewById(R.id.contributer);
        Intent iin= getIntent();

        Bundle b = iin.getExtras();

        if(b!=null) {
            database_path=(String)b.get("database_path");
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference(database_path+"contributorsprivacy");//myRefg aman bangladesh
        }
    }

    public void contributer_add_kor(View view){
        if(contributer.getText().toString().equals("")){
            Toast.makeText(this, "please enter any contributor user name", Toast.LENGTH_LONG).show();
        }
        myRef.child(contributer.getText().toString()).setValue(1);
        contributer.setText("");
    }
    public void main_page(View view){
        Intent intent=new Intent(this,Mainpage.class);
        startActivity(intent);
    }
}
