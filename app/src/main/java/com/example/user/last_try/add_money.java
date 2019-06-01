package com.example.user.last_try;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class add_money extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference myRef;
    EditText ki_kaje,koy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money);
        koy=(EditText)findViewById(R.id.koy);
        database = FirebaseDatabase.getInstance();
        ki_kaje=(EditText)findViewById(R.id.ki_kaje);
        Intent iin = getIntent();
        Bundle b = iin.getExtras();
        if (b != null) {
            String address = (String) b.get("address");
            myRef = database.getReference(address);
        }
    }
    public void submit(View view){
        String ki_kaje_dise=ki_kaje.getText().toString();
        String taka_in_astring=koy.getText().toString();
        if(ki_kaje_dise.equals("")==true||taka_in_astring.equals("")==true){
            Toast.makeText(getApplicationContext(), "please fill up all information", Toast.LENGTH_SHORT).show();
            return;
        }

        myRef.push().setValue(new hisab(ki_kaje_dise,MainActivity.usernames,taka_in_astring));
        ki_kaje.setText("");
        koy.setText("");
    }
    public void main_page(View view){
        Intent intent=new Intent(this,Mainpage.class);
        startActivity(intent);
    }
}
