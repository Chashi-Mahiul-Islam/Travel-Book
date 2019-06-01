package com.example.user.last_try;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class number_update extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference number_add_database;
    EditText number;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_number_update);
        database = FirebaseDatabase.getInstance();
        number_add_database=database.getReference("Numbers").child(MainActivity.usernames);
        number=(EditText)findViewById(R.id.number);
    }
    public void submit(View view){
        if(number.getText().toString().equals("")){
            Toast.makeText(this, "please enter a number", Toast.LENGTH_LONG).show();
        }
        String s=number.getText().toString();
        number.setText("");
        number_add_database.setValue(s);
    }
    public void main_page(View view){
        Intent intent=new Intent(this,Mainpage.class);
        startActivity(intent);
    }
}
