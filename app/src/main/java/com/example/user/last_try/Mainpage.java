package com.example.user.last_try;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.telephony.SmsManager;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.maps.model.LatLng;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.List;
public class Mainpage extends AppCompatActivity implements View.OnClickListener {

    String creator=null;
    //private Button logoutButton;
    //private TextView textViewUserEmail;
    static String currentAreaName=null;
    static String subcurrentAreaName=null;
    private ImageButton newAdventureButton;
    private ImageButton continueAdventureButton;
    private ImageButton mapsButton;
    private ImageButton translatorButton;
    private ImageButton uberButton;
    private ImageButton lookNearByButton;
    private ImageButton myJournalsButton;
    private ImageButton journalsButton;
    boolean is_emergency=false;
    int position_number=1;
    static String help_friend_database_address="garbage";
    LocationManager locationManager;static double latitude;static double longitude;static String current_address="location still not available";
    private SensorManager mSensorManager;//longitude current address er longitude,latitude
    SmsManager smsManager;
    LatLng latlng;
    private ShakeEventListener mSensorListener;
    boolean ager_journey_naki;//eta ki notun journey,age jodi previous kono journey thake
    FirebaseDatabase database;
    static String kar_help_lagbe;
    DatabaseReference myRef,journey_continue2,number_add_database,contributor_database,last_emergency;continue_journey Continue_Journey=null;
    static DatabaseReference kar_help_lagbe_tar_last_emergency;

    ImageButton help,emergency,addNumber,calculator,addAmount,logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_mainpage);
        smsManager= SmsManager.getDefault();
        newAdventureButton = (ImageButton) findViewById(R.id.newAdventureButton);
        continueAdventureButton = (ImageButton) findViewById(R.id.continueAdventureButton);
        mapsButton = (ImageButton) findViewById(R.id.mapsButton);
        translatorButton = (ImageButton) findViewById(R.id.translatorButton);
        uberButton = (ImageButton) findViewById(R.id.uberButton);
        lookNearByButton = (ImageButton) findViewById(R.id.lookNearByButton);
        myJournalsButton = (ImageButton) findViewById(R.id.myJournalsButton);
        journalsButton = (ImageButton) findViewById(R.id.journalsButton);
        help=(ImageButton)findViewById(R.id.helpxml);
        emergency=(ImageButton)findViewById(R.id.emergencyxml);
        addNumber=(ImageButton)findViewById(R.id.addNumberxml);
        calculator=(ImageButton)findViewById(R.id.calculatorxml);
        addAmount=(ImageButton)findViewById(R.id.addamountxml);
        logout=(ImageButton)findViewById(R.id.logoutxml);
        logout.setOnClickListener(this);
        newAdventureButton.setOnClickListener(this);
        continueAdventureButton.setOnClickListener(this);
        mapsButton.setOnClickListener(this);
        translatorButton.setOnClickListener(this);
        uberButton.setOnClickListener(this);
        lookNearByButton.setOnClickListener(this);
        myJournalsButton.setOnClickListener(this);
        journalsButton.setOnClickListener(this);
        emergency.setBackgroundColor(Color.GREEN);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        mSensorListener = new ShakeEventListener();
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        database = FirebaseDatabase.getInstance();
        number_add_database=database.getReference("Numbers");
        myRef = database.getReference(MainActivity.usernames + "currentjourney");//continue journey details
        last_emergency=database.getReference(MainActivity.usernames+"last_emergency");//add
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                    Continue_Journey = null;//dataSnapshot.getValue(continue_journey.class);
                } else {
                    Continue_Journey = dataSnapshot.getValue(continue_journey.class);

                    database.getReference("creatorsofmultiplejourney").child(Continue_Journey.database_path).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                            } else {
                                creator=dataSnapshot.getValue(String.class);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                    journey_continue2 = database.getReference(Continue_Journey.database_path + "help");
                    contributor_database=database.getReference(Continue_Journey.database_path+"contributors");
                    help_friend_database_address = Continue_Journey.database_path + "help";
                    journey_continue2.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                                help.setBackgroundColor(Color.GREEN);
                                kar_help_lagbe=null;
                            } else {
                                kar_help_lagbe=dataSnapshot.getValue(String.class);
                                kar_help_lagbe_tar_last_emergency=database.getReference(kar_help_lagbe+"last_emergency");
                                help.setBackgroundColor(Color.RED);
                            }
                        }
                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
        mSensorListener.setOnShakeListener(new ShakeEventListener.OnShakeListener() {

            public void onShake() {//shake korle
                Intent intent = new Intent(getApplicationContext(), list_tester4.class);//list tester 4 ekta jaygay ki ki ase seta
                intent.putExtra("address", current_address);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), current_address, Toast.LENGTH_SHORT).show();
            }

        });
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling//location permission deoa ase naki mobile e
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            // return;
        } else if (locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {//internet on ase naki
            locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 5000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    latitude = location.getLatitude();//current location
                    longitude = location.getLongitude();
                    latlng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        current_address = addressList.get(0).getCountryName() + "," + addressList.get(0).getLocality() + "," + addressList.get(0).getSubLocality();
                        currentAreaName=addressList.get(0).getLocality();
                        subcurrentAreaName=addressList.get(0).getSubLocality();
                        if(Continue_Journey!=null){//add
                            last_emergency.push().setValue(new help_me(longitude*1.0, latitude*1.0, MainActivity.usernames,position_number));
                        }

                        // Toast.makeText(getApplicationContext(),current_address+"ye", Toast.LENGTH_SHORT).show();


                    } catch (IOException e) {
                        e.printStackTrace();
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
        } else if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {//gps ase naki
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 5000, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {

                    latitude = location.getLatitude();
                    longitude = location.getLongitude();
                    latlng = new LatLng(latitude, longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try {
                        List<Address> addressList = geocoder.getFromLocation(latitude, longitude, 1);
                        current_address = addressList.get(0).getCountryName() + "," + addressList.get(0).getLocality() + "," + addressList.get(0).getSubLocality();
                        currentAreaName=addressList.get(0).getLocality();
                        subcurrentAreaName=addressList.get(0).getLocality();
                        if(Continue_Journey!=null){//add
                            last_emergency.push().setValue(new help_me(longitude*1.0, latitude*1.0, MainActivity.usernames,position_number));
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
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

    };

    public void map(){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.maps");
        if (launchIntent != null) {
            Toast.makeText(getApplicationContext(), "gt pyse", Toast.LENGTH_SHORT).show();
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
        else
        {
            Toast.makeText(getApplicationContext(), "gt painy", Toast.LENGTH_SHORT).show();
        }
    }

    public void uber(){
        Toast.makeText(getApplicationContext(), "Uber is turning on...", Toast.LENGTH_SHORT).show();
        PackageManager pm = getPackageManager();
        try {
            pm.getPackageInfo("com.ubercab", PackageManager.GET_ACTIVITIES);
            String uri = "uber://?action=setPickup&pickup=my_location";
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(uri));
            startActivity(intent);
        } catch (PackageManager.NameNotFoundException e) {
            try {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.ubercab")));
            } catch (android.content.ActivityNotFoundException anfe) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=com.ubercab")));
            }
        }
    }
    public void translator(){
        Intent launchIntent = getPackageManager().getLaunchIntentForPackage("com.google.android.apps.translate");
        if (launchIntent != null) {
            // Toast.makeText(getApplicationContext(), "gt pyse", Toast.LENGTH_SHORT).show();
            startActivity(launchIntent);//null pointer check in case package name was not found
        }
        else
        {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.translate&hl=en")));
            Toast.makeText(getApplicationContext(), "please download translator", Toast.LENGTH_SHORT).show();
        }
    }
    @Override
    protected void onResume() {
        super.onResume();
        mSensorManager.registerListener(mSensorListener,
                mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
    }

    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
    }
    @Override
    public void onClick(View view) {


        if(view==newAdventureButton)
        {
            Intent intent=new Intent(this,notunjourney.class);
            startActivity(intent);

        }
        if(view==continueAdventureButton)
        {
            if(Continue_Journey==null){
                Toast.makeText(getApplicationContext(),"sorry no journey history", Toast.LENGTH_SHORT).show();
                return;
            }
            ager_journey_naki=Continue_Journey.ager_journey_sesh_naki;
            if(ager_journey_naki==true){
                Toast.makeText(getApplicationContext(),"sorry no journey history", Toast.LENGTH_SHORT).show();
            }else{
                notunjourney.kon_desh=Continue_Journey.current_desh;//notunk journey.kon_desh location theke direct
                Intent intent=new Intent(this,eventadd.class);
                intent.putExtra("event",Continue_Journey.event);
                intent.putExtra("day",Continue_Journey.day);
                intent.putExtra("koy_dine_sesh",Continue_Journey.koy_dine_sesh_hoyar_kotha);
                intent.putExtra("database_path",Continue_Journey.database_path);
                intent.putExtra("title",Continue_Journey.title);
                startActivity(intent);
            }
        }
        if(view==logout){
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
        }
        if(view==mapsButton)
        {
            map();
        }
        if(view==translatorButton)
        {
            translator();
        }
        if(view==uberButton)
        {
            uber();
        }
        if(view==lookNearByButton)
        {
            startActivity(new Intent(this,look_near_by.class));
        }
        if(view==myJournalsButton)
        {
            Intent intent=new Intent(this,list_tester_for_my_diary.class);//nijer diaryr sob journey
            startActivity(intent);

        }
        if(view==journalsButton)
        {
            Intent intent=new Intent(this,searchpage.class);
            startActivity(intent);
        }
    }
    public void emergency(View view){
        if(Continue_Journey==null){
            Toast.makeText(getBaseContext(), "Sorry you have no current journey\n", Toast.LENGTH_SHORT).show();
            return;
        }
        contributor_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                    Toast.makeText(getBaseContext(), "Sorry you are on a single journey\n", Toast.LENGTH_SHORT).show();
                    return;
                }else{
                    if(is_emergency==false) {
                        is_emergency = true;
                        journey_continue2.setValue(MainActivity.usernames);
                        emergency.setBackgroundColor(Color.RED);
                        contributor_database.addChildEventListener(new ChildEventListener() {
                            @Override
                            public void onChildAdded(DataSnapshot dataSnapshot, String previousChildName) {
                                String Ekta = dataSnapshot.getValue(String.class);
                                if(!MainActivity.usernames.equals(Ekta)) {
                                    number_add_database.child(Ekta).addValueEventListener(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(DataSnapshot dataSnapshot) {
                                            if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                                            } else {
                                                String number = dataSnapshot.getValue(String.class);
                                                try {
                                                    smsManager.sendTextMessage(number, null, "please help me i am" +
                                                            MainActivity.usernames + " location" + current_address + "latitude:" + latitude + "longitude:" + longitude, null, null);
                                                } catch (Exception e) {
                                                    Toast.makeText(getBaseContext(), number + " is not valid numbers\n", Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                        @Override
                                        public void onCancelled(DatabaseError databaseError) {
                                            System.out.println("The read failed: " + databaseError.getCode());
                                        }
                                    });
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
                        });
                        if(!creator.equals(MainActivity.usernames)){
                            number_add_database.child(creator).addValueEventListener(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                                    } else {
                                        String number = dataSnapshot.getValue(String.class);
                                        try {
                                            smsManager.sendTextMessage(number, null, "please help me i am" +
                                                    MainActivity.usernames + " location" + current_address + "latitude:" + latitude + "longitude:" + longitude, null, null);
                                        } catch (Exception e) {
                                            Toast.makeText(getBaseContext(), number + " is not valid numbers\n", Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                }
                                @Override
                                public void onCancelled(DatabaseError databaseError) {
                                    System.out.println("The read failed: " + databaseError.getCode());
                                }
                            });
                        }
                    }else{
                        is_emergency=false;
                        emergency.setBackgroundColor(Color.GREEN);
                        journey_continue2.setValue(null);
                    }

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
    public void help(View view){
        if(Continue_Journey==null){
            Toast.makeText(getBaseContext(), "Sorry you have no current journey\n", Toast.LENGTH_SHORT).show();
            return;
        }
        if(kar_help_lagbe==null){
            Toast.makeText(getBaseContext(), "everyone is safe\n", Toast.LENGTH_SHORT).show();
            return;
        }
        Toast.makeText(getBaseContext(), "your friend "+kar_help_lagbe+"needs help\n", Toast.LENGTH_SHORT).show();
        contributor_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                    Toast.makeText(getBaseContext(), "Sorry you are on a single journey\n", Toast.LENGTH_SHORT).show();
                    return;
                } else {
                    Intent intent=new Intent(getApplicationContext(),MapsActivity_for_emergency.class);
                    startActivity(intent);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
    public void add_amount(View view){
        if(Continue_Journey==null){
            Toast.makeText(getBaseContext(), "Sorry you have no current journey\n", Toast.LENGTH_SHORT).show();
            return;
        }
        contributor_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                    Toast.makeText(getBaseContext(), "Sorry you are on a single journey\n", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent=new Intent(getApplicationContext(),add_money.class);
                    intent.putExtra("address",Continue_Journey.database_path+"hisab");
                    startActivity(intent);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
    public void calculate(View view) {
        if(Continue_Journey==null){
            Toast.makeText(getBaseContext(), "Sorry you have no current journey\n", Toast.LENGTH_SHORT).show();
            return;
        }
        contributor_database.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                    Toast.makeText(getBaseContext(), "Sorry you are on a single journey\n", Toast.LENGTH_SHORT).show();

                } else {
                    Intent intent=new Intent(getApplicationContext(),list_tester_for_calculate.class);
                    intent.putExtra("address",Continue_Journey.database_path);
                    startActivity(intent);

                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });

    }
    public void add_number(View view){
        Intent intent=new Intent(this,number_update.class);
        startActivity(intent);

    }
    public void contributor_add_kor(View view){
        if(Continue_Journey==null){
            Toast.makeText(getBaseContext(), "Sorry you have no current journey\n", Toast.LENGTH_SHORT).show();
            return;
        }
        database.getReference("creatorsofmultiplejourney").child(Continue_Journey.database_path).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists() == false || dataSnapshot == null || dataSnapshot.getChildren() == null) {//kono search histo
                    Toast.makeText(getBaseContext(), "Sorry you can not add contributor as it is a signle journey\n", Toast.LENGTH_SHORT).show();
                    return;

                } else {
                    String who_is_the_creator=dataSnapshot.getValue(String.class);
                    if(!who_is_the_creator.equals(MainActivity.usernames)){
                        Toast.makeText(getBaseContext(), "Sorry you can not add contributor as you are not the creator of the journey\n", Toast.LENGTH_SHORT).show();
                    }else{
                        Intent intent=new Intent(getApplicationContext(),open_contributor_add.class);
                        intent.putExtra("database_path",Continue_Journey.database_path);
                        startActivity(intent);
                    }


                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                System.out.println("The read failed: " + databaseError.getCode());
            }
        });
    }
}
