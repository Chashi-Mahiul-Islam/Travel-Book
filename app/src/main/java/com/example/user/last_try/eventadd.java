package com.example.user.last_try;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class eventadd extends AppCompatActivity {


    private static final int REQ_CODE_SPEECH_INPUT = 100;
    private TextView mVoiceInputTv;
    ImageButton mSpeakBtn;
    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
    Date date ;//= new Date();
    LocationManager locationManager;double latitude=0; double longitude=0;
    Spinner spinner;String str="garbage";//str hoilo location
    boolean ses_hoise=false;//jodi complete day jeta din select korsi,pura journey ses
    boolean image_ase=false;//
    String category;String address=null;
    String caption;
    private StorageReference mStorageRef;
    FirebaseDatabase database;
    String database_path;
    DatabaseReference myRef;

    static String databasepath_ki;
    DatabaseReference overall_map;
    DatabaseReference journey_continue;
    DatabaseReference last_emergency;
    DatabaseReference database_for_map_co_ordinates;
    static int koy_number_din;
    ArrayAdapter<CharSequence> adapter;
    int koy_diner_journey;
    Bitmap bitmap;
    ContentValues values;
    Button cover_pic;
    Button submit,complete_day;
    ImageButton toggle;

    ImageButton add_a_picture,gallery;
    ImageView picture;
    EditText text;

    int event;
    int checker=0;//0 hoile gallery,1 hoile direct camera
    Uri uri;String title;
    private static final int GALLERY_INTENT=2;
    private static final int CAMERA_REQUEST_CODE=1;
    ProgressDialog progress;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //mVoiceInputTv = (TextView) findViewById(R.id.voiceInput);


        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        progress = new ProgressDialog(this);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eventadd);

        mSpeakBtn = (ImageButton) findViewById(R.id.btnSpeak);
        mSpeakBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startVoiceInput();
            }
        });
        spinner = (Spinner) findViewById(R.id.spinner);
        text = (EditText) findViewById(R.id.text);
        add_a_picture = (ImageButton) findViewById(R.id.add_a_picture);
        submit = (Button) findViewById(R.id.submit);
        picture = (ImageView) findViewById(R.id.photoView);
        complete_day = (Button) findViewById(R.id.complete_day);
        gallery = (ImageButton) findViewById(R.id.gallery);
        toggle = (ImageButton) findViewById(R.id.toggle);
        Intent iin = getIntent();
        cover_pic = (Button) findViewById(R.id.set_cover_);
        Bundle b = iin.getExtras();

        if (b != null) {
            title = (String) b.get("title");
            Toast.makeText(eventadd.this, "title is"+title, Toast.LENGTH_LONG).show();
            event = (int) b.get("event");
            koy_number_din = (int) b.get("day");
            koy_diner_journey = (int) b.get("koy_dine_sesh");
            database_path = (String) b.get("database_path");
            databasepath_ki = database_path;//etar may be kaj nai
            database = FirebaseDatabase.getInstance();
            last_emergency = database.getReference(MainActivity.usernames + "last_emergency");
            myRef = database.getReference(database_path);//myRefg aman bangladesh
            complete_day.setText("complete day" + String.valueOf(koy_number_din));
            database.getReference(database_path + "specificday").child(String.valueOf(koy_number_din)).setValue(Mainpage.subcurrentAreaName);
            database_for_map_co_ordinates = database.getReference(database_path + "map");
            journey_continue = database.getReference(MainActivity.usernames + "currentjourney");//nijer current journeyr reference
            adapter = ArrayAdapter.createFromResource(this, R.array.category_names4, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);//spinner ta categoryr
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int pos, long id) {
                    category = parent.getItemAtPosition(pos).toString();
                }

                public void onNothingSelected(AdapterView<?> parent) {
                    // Another interface callback
                }
            });
            add_a_picture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    values = new ContentValues();
                    values.put(MediaStore.Images.Media.TITLE, "New Picture");
                    values.put(MediaStore.Images.Media.DESCRIPTION, "From your Camera");
                    uri = getContentResolver().insert(
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                    startActivityForResult(intent, CAMERA_REQUEST_CODE);

                }
            });
            gallery.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType("image/*");
                    startActivityForResult(intent, GALLERY_INTENT);
                }
            });
            complete_day.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    koy_number_din++;
                    picture.setImageBitmap(null);//null;
                    text.setText("");
                    event = 1;
                    if (koy_number_din > koy_diner_journey) {
                        ses_hoise = true;//location manager off korar jonno
                        journey_continue.setValue(null);
                        Intent intent = new Intent(eventadd.this, Mainpage.class);
                        startActivity(intent);
                    } else {
                        continue_journey Continue_Journey = new continue_journey(event, koy_number_din, (database_path), false, koy_diner_journey, notunjourney.kon_desh, title);
                        journey_continue.setValue(Continue_Journey);
                        complete_day.setText("complete day" + String.valueOf(koy_number_din));
                    }
                }
            });
            submit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    date = new Date();
                    if (text.getText().toString().equals("")) {
                        Toast.makeText(eventadd.this, "please enter any caption", Toast.LENGTH_LONG).show();
                    } else if (image_ase == false) {
                        Toast.makeText(eventadd.this, "please add a picture", Toast.LENGTH_LONG).show();
                    } else if (category.contains("select a category")) {
                        Toast.makeText(eventadd.this, "please select a category", Toast.LENGTH_LONG).show();
                    } else {
                        event++;
                        final String keys = MainActivity.usernames + notunjourney.kon_desh +title+ "day" + String.valueOf(koy_number_din) + "event " + String.valueOf(event);//imagepath
                        // final String keys = database_path + "day" + String.valueOf(koy_number_din) + "event " + String.valueOf(event);//imagepath
                        if (address != null) {
                            caption = address + "\n" + dateFormat.format(date) + "\n" + text.getText().toString();
                        } else {
                            caption = dateFormat.format(date) + "\n" + text.getText().toString();
                        }
                        final ekta_event eventd = new ekta_event(caption, keys, category, koy_number_din, latitude, longitude, str, MainActivity.usernames);//str hoilo current address
                        final String save_kor_text = caption;//caption
                        continue_journey Continue_Journey = new continue_journey(event, koy_number_din, (database_path), false, koy_diner_journey, notunjourney.kon_desh, title);
                        journey_continue.setValue(Continue_Journey);
                        StorageReference filepath = mStorageRef.child(keys);
                        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                Toast.makeText(eventadd.this, "upload done", Toast.LENGTH_LONG).show();
                                eventd.longitude = longitude;
                                eventd.latitude = latitude;
                                myRef.push().setValue(eventd);
                                overall_map = database.getReference(str);
                                ekta_jaygar_asepase_ke_gese_ki_likhse Ekta2 = new ekta_jaygar_asepase_ke_gese_ki_likhse(save_kor_text, keys, category, latitude, longitude, str, MainActivity.usernames);
                                overall_map.push().setValue(Ekta2);
                            }
                        });

                        text.setText("");
                        picture.setImageBitmap(null);
                        image_ase = false;

                    }

                }

            });
            cover_pic.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {
                    if (image_ase == false) {
                        Toast.makeText(eventadd.this, "please add a picture", Toast.LENGTH_LONG).show();
                    } else {
                        StorageReference filepath = mStorageRef.child(database_path + "cover pic");
                        filepath.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                                Toast.makeText(eventadd.this, "upload done", Toast.LENGTH_LONG).show();

                            }
                        });
                        picture.setImageBitmap(null);
                        image_ase = false;
                    }
                }
            });


            if (ses_hoise == false && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

            } else if (ses_hoise == false && locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (ses_hoise == false) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            LatLng latlng = new LatLng(latitude, longitude);
                            Geocoder geocoder = new Geocoder(getApplicationContext());
                            try {
                                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                address = addressList.get(0).getAddressLine(0);
                                str = addressList.get(0).getCountryName() + "," + addressList.get(0).getLocality() + "," + addressList.get(0).getSubLocality();
                                database_for_map_co_ordinates.push().setValue(new map_co_ordinate(longitude, latitude, koy_number_din, MainActivity.usernames));
                                last_emergency.push().setValue(new help_me(longitude * 1.0, latitude * 1.0, MainActivity.usernames, 0));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {

                    }

                    @Override
                    public void onProviderEnabled(String s) {

                    }

                    @Override
                    public void onProviderDisabled(String s) {

                    }
                });
            } else if (ses_hoise == false && locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        if (ses_hoise == false) {
                            latitude = location.getLatitude();
                            longitude = location.getLongitude();
                            LatLng latlng = new LatLng(latitude, longitude);
                            Geocoder geocoder = new Geocoder(getApplicationContext());
                            try {
                                List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                                address = addressList.get(0).getAddressLine(0);

                                str = addressList.get(0).getCountryName() + "," + addressList.get(0).getLocality() + "," + addressList.get(0).getSubLocality();
                                database_for_map_co_ordinates.push().setValue(new map_co_ordinate(longitude, latitude, koy_number_din, MainActivity.usernames));
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }

                    @Override
                    public void onStatusChanged(String s, int i, Bundle bundle) {
                    }

                    @Override
                    public void onProviderEnabled(String s) {
                    }

                    @Override
                    public void onProviderDisabled(String s) {
                    }
                });
            }
        }
    }
    public void startVoiceInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Hello, How can I help you?");
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {

        }
    }




    public Uri getImageUri1(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);

    }
    @Override
    protected void onStart() {
        super.onStart();

    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_INTENT && resultCode == RESULT_OK) {
            uri = data.getData();
            picture.setImageURI(uri);image_ase=true;

        } else if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                bitmap  = MediaStore.Images.Media.getBitmap(
                        getContentResolver(), uri);
                picture.setImageBitmap(bitmap);image_ase=true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else if(requestCode==REQ_CODE_SPEECH_INPUT )
        {

            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                //mVoiceInputTv.setText(result.get(0));
                text.setText(result.get(0));
            }

        }
    }
    public void show(View view){
        Intent intent=new Intent(eventadd.this,list_tester3.class);
        startActivity(intent);
    }
    public void mainpage(View view){
        continue_journey Continue_Journey=new continue_journey(event,koy_number_din,(database_path),false,koy_diner_journey,notunjourney.kon_desh,title);
        journey_continue.setValue(Continue_Journey);
        complete_day.setText("complete day" + String.valueOf(koy_number_din));
        Intent intent=new Intent(eventadd.this,Mainpage.class);
        startActivity(intent);

    }



}


