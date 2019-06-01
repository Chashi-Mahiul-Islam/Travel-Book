package com.example.user.last_try;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Locale;

public class notunjourney extends AppCompatActivity {
    private static final int REQ_CODE_SPEECH_INPUT = 100;

    private ImageButton mSpeakBtn;
    EditText AreaName;
    static int koydiner_journey;
    DatabaseReference journey_continue;
    static String kon_desh;
    FirebaseDatabase database;
    DatabaseReference myRef,myRef2,myRef_sob_diary,myRef3;
    EditText how_many_days,title;
    String country_name;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notunjourney);

        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak2);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });


        database = FirebaseDatabase.getInstance();
        myRef = database.getReference();//ekta country te ke ke koydin gese
        myRef3=database.getReference("creatorsofmultiplejourney");
        how_many_days=(EditText)findViewById(R.id.how_many_days);
        title=(EditText)findViewById(R.id.title);
        AreaName=(EditText)findViewById(R.id.AreaName);
        if(Mainpage.currentAreaName!=null){
            AreaName.setText(Mainpage.currentAreaName);
        }
    }

    private void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }
    public void startkoro(View view){
        kon_desh=AreaName.getText().toString();
        country_name=kon_desh;
        if(title.getText().toString().equals("")==true){
            Toast.makeText(getApplicationContext(),"Please select a title", Toast.LENGTH_SHORT).show();
            title.setText("");
            return;
        }
        else if(kon_desh.equals("")){
            Toast.makeText(getApplicationContext(), "please select a country"+MainActivity.usernames, Toast.LENGTH_SHORT).show();
            return;
        } else if(how_many_days.getText().toString().equals("")==true){
            Toast.makeText(getApplicationContext(), "please how many days you will stay"+MainActivity.usernames, Toast.LENGTH_SHORT).show();
            return;
        }
        kon_desh=AreaName.getText().toString().substring(0, 1).toUpperCase() + AreaName.getText().toString().substring(1).toLowerCase();
        country_name=kon_desh;
        myRef=database.getReference(country_name);
        String mGroupId = myRef.push().getKey();
        ke_koydin A=new ke_koydin(MainActivity.usernames,Integer.parseInt(how_many_days.getText().toString()),"single",0,country_name,mGroupId,title.getText().toString());
        myRef.child(mGroupId).setValue(A);
        myRef_sob_diary=database.getReference("all diary");
        A.database_path_for_dual=mGroupId;
        myRef_sob_diary.child(MainActivity.usernames+country_name+title.getText().toString()).setValue(A);
        koydiner_journey=Integer.parseInt(how_many_days.getText().toString());
        myRef2=database.getReference(MainActivity.usernames+"nijerjourney");//amar nijer diaryte add
        myRef2.push().setValue(new nijer_journey_details(kon_desh,koydiner_journey,MainActivity.usernames+kon_desh+title.getText().toString(),title.getText().toString()));//mGroupid
        journey_continue = database.getReference(MainActivity.usernames+"currentjourney");
        continue_journey Continue_Journey=new continue_journey(1,1,MainActivity.usernames+kon_desh+title.getText().toString(),false,Integer.parseInt(how_many_days.getText().toString()),kon_desh,title.getText().toString());
        journey_continue.setValue(Continue_Journey);
        AreaName.setText("");
        Intent intent=new Intent(notunjourney.this,eventadd.class);
        intent.putExtra("title", title.getText().toString());
        intent.putExtra("event",1);
        intent.putExtra("day",1);
        intent.putExtra("koy_dine_sesh",koydiner_journey);
        intent.putExtra("database_path",MainActivity.usernames+notunjourney.kon_desh+title.getText().toString());//amar nijer name+kondesh
        startActivity(intent);

    }
    public void contribute_kor(View view){
        kon_desh=AreaName.getText().toString();
        country_name=kon_desh;
        if(title.getText().toString().equals("")==false){
            Toast.makeText(getApplicationContext(),"let the owner of journal decide title", Toast.LENGTH_SHORT).show();
            title.setText("");
            return;
        } else if(kon_desh.equals("select a country")){
            Toast.makeText(getApplicationContext(), "please select a country"+MainActivity.usernames, Toast.LENGTH_SHORT).show();
            return;
        }else if(how_many_days.getText().toString().equals("")==true){
            Toast.makeText(getApplicationContext(), "please how many days you will stay"+MainActivity.usernames, Toast.LENGTH_SHORT).show();
            return;
        }
        kon_desh=AreaName.getText().toString().substring(0, 1).toUpperCase() + AreaName.getText().toString().substring(1).toLowerCase();
        country_name=kon_desh;
        koydiner_journey=Integer.parseInt(how_many_days.getText().toString());
        Intent intent=new Intent(this,contributer_add_koro.class);
        startActivity(intent);

    }
    public void combine_kor(View view){
        kon_desh=AreaName.getText().toString();
        country_name=kon_desh;
        if(title.getText().toString().equals("")==true){
            Toast.makeText(getApplicationContext(),"Please select a title", Toast.LENGTH_SHORT).show();
            title.setText("");
            return;
        }
        else if(kon_desh.equals("select a country")){
            Toast.makeText(getApplicationContext(), "please select a country"+MainActivity.usernames, Toast.LENGTH_SHORT).show();
            return;
        }
        else if(how_many_days.getText().toString().equals("")==true){
            Toast.makeText(getApplicationContext(), "please how many days you will stay"+MainActivity.usernames, Toast.LENGTH_SHORT).show();
            return;
        }
        kon_desh=AreaName.getText().toString().substring(0, 1).toUpperCase() + AreaName.getText().toString().substring(1).toLowerCase();
        country_name=kon_desh;
        Toast.makeText(getApplicationContext(), "successfully logged in user"+MainActivity.usernames, Toast.LENGTH_SHORT).show();
        myRef=database.getReference(country_name);
        myRef2=database.getReference(MainActivity.usernames+"nijerjourney");
        String mGroupId = myRef.push().getKey();
        myRef3.child(MainActivity.usernames+kon_desh+title.getText().toString()).setValue(MainActivity.usernames);
        ke_koydin A=new ke_koydin(MainActivity.usernames,Integer.parseInt(how_many_days.getText().toString()),"multiple",0,country_name,mGroupId,title.getText().toString());
        myRef.child(mGroupId).setValue(A);
        myRef_sob_diary=database.getReference("all diary");
        A.database_path_for_dual=mGroupId;
        myRef_sob_diary.child(MainActivity.usernames+country_name+title.getText().toString()).setValue(A);
        koydiner_journey=Integer.parseInt(how_many_days.getText().toString());
        myRef2.push().setValue(new nijer_journey_details(kon_desh,koydiner_journey,MainActivity.usernames+kon_desh+title.getText().toString(),title.getText().toString()));//mGroupid
        journey_continue = database.getReference(MainActivity.usernames+"currentjourney");
        continue_journey Continue_Journey=new continue_journey(1,1,MainActivity.usernames+kon_desh+title.getText().toString(),false,Integer.parseInt(how_many_days.getText().toString()),kon_desh,title.getText().toString());
        journey_continue.setValue(Continue_Journey);
        Intent intent=new Intent(this,privacy.class);
        intent.putExtra("title", title.getText().toString());
        intent.putExtra("event",1);
        intent.putExtra("day",1);
        intent.putExtra("koy_dine_sesh",koydiner_journey);
        intent.putExtra("database_path",MainActivity.usernames+kon_desh+title.getText().toString());
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {
                    ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    //mVoiceInputTv.setText(result.get(0));
                    title.setText(result.get(0));
                }
                break;
            }

        }
    }
}
