package com.example.currentlocation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;

import static android.app.ProgressDialog.show;

import android.content.Context;
public class emergencyButton extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    DrawerLayout drawLayout;
    ActionBarDrawerToggle toggle;
    NavigationView navigationView;
    FusedLocationProviderClient fusedLocationProviderClient;
    Toolbar toolbar;
    TextView phone_Call;
    int count = 0;
    FirebaseDatabase rootnode;
    DatabaseReference reference;
    String latitude = "";
    String longitude = "";
    private String[] PERMISSIONS;
    public static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emergency_button);
        drawLayout = (DrawerLayout) findViewById(R.id.drawer);
        navigationView = findViewById(R.id.navV);
        //toolbar = findViewById(R.id.hamburger);
        navigationView.bringToFront();
        toggle = new ActionBarDrawerToggle(this, drawLayout, R.string.open, R.string.close);
        drawLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        PERMISSIONS = new String[] {

                Manifest.permission.SEND_SMS,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
        };

        //navigationView.setCheckedItem(R.id.navV);
        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final NavigationView navView = (NavigationView) findViewById(R.id.navV);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);*/





        Button emergencyButton = (Button) findViewById(R.id.button);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);
        emergencyButton.setOnClickListener(new View.OnClickListener() {

            @Override


            public void onClick(View view) {
                Toast.makeText(emergencyButton.this,
                        "Alert Sent", Toast.LENGTH_SHORT).show();

                if (!hasPermissions(emergencyButton.this,PERMISSIONS)) {

                    ActivityCompat.requestPermissions(emergencyButton.this,PERMISSIONS,1);
                }


                getLocation();
               /* if (ActivityCompat.checkSelfPermission(emergencyButton.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(emergencyButton.this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }

                fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
                    @Override
                    public void onComplete(@NonNull Task<Location> task) {
                        Location location = task.getResult();
                        if(location != null) {
                            try {
                                Geocoder geocoder = new Geocoder(emergencyButton.this, Locale.getDefault());
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(), location.getLongitude(), 1
                                );
                                latitude = latitude +   addresses.get(0).getLatitude();
                                longitude = longitude + addresses.get(0).getLongitude();


                            } catch (IOException e) {
                                e.printStackTrace();
                            }

                        }
                    }
                });*/


                /*MapData mapData = new MapData("23.7453736", "90.38524609999");
                reference.child("dhanmondi").setValue(mapData);*/


            }
        });
    }
    private boolean hasPermissions(Context context, String... PERMISSIONS) {

        if (context != null && PERMISSIONS != null) {

            for (String permission: PERMISSIONS){

                if (ActivityCompat.checkSelfPermission(context,permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }

        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == 1) {

            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "SMS Permission is granted", Toast.LENGTH_SHORT).show();
                getLocation();
            } else {
                Toast.makeText(this, "SMS Permission is denied", Toast.LENGTH_SHORT).show();
            }

            if (grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Fine Location Permission is granted", Toast.LENGTH_SHORT).show();
                getLocation();
            } else {
                Toast.makeText(this, "Fine Location Permission is denied", Toast.LENGTH_SHORT).show();
            }

            if (grantResults[2] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Fine Location is granted", Toast.LENGTH_SHORT).show();
                getLocation();
            } else {
                Toast.makeText(this, "Fine Location Permission is denied", Toast.LENGTH_SHORT).show();
            }


        }
    }
    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions

            return;
        }

        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if(location != null) {
                    try {
                        Geocoder geocoder = new Geocoder(emergencyButton.this, Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocation(
                                location.getLatitude(), location.getLongitude(), 1
                        );

                        String username = getIntent().getStringExtra("username");
                        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("User");
                        Query checkUser = reference.orderByChild("username").equalTo(username);
                        checkUser.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                if(snapshot.exists()) {
                                    String eContact1FromDB = snapshot.child(username).child("emergencyContct1").getValue(String.class);
                                    String phoneNumber = "+88" + eContact1FromDB;

                                    Log.d(TAG, "WE GOT THE NUMBER" + phoneNumber );
                                    String eContact2FromDB = snapshot.child(username).child("emergencyContact2").getValue(String.class);
                                    String phoneNumber2 = "+88" + eContact2FromDB;

                                    Log.d(TAG, "WE GOT THE other NUMBER" + phoneNumber );
                                    String dangerMessage = "**User is in DANGER**\n";
                                    String link = "\nLink = https://www.google.com/maps/search/?api=1&query="
                                            +addresses.get(0).getLatitude() + "," + addresses.get(0).getLongitude();
                                    String message = dangerMessage + "Address = " + addresses.get(0).getAddressLine(0) +
                                            "\nLocality = " + addresses.get(0).getLocality() +
                                            "\nCountry = " + addresses.get(0).getCountryName() +
                                            "\nLatitude = " + addresses.get(0).getLatitude() +
                                            "\nLongitude = " + addresses.get(0).getLongitude() + link;
                                    Log.d(TAG, "WE GOT THE message" + message );


                                    SmsManager sms = SmsManager.getDefault();
                                    ArrayList<String> parts = sms.divideMessage(message);
                                    for (String part : parts) {
                                        sms.sendTextMessage(phoneNumber, null, part, null, null);
                                    }


                                    SmsManager smsManager2 = SmsManager.getDefault();

                                    for (String part : parts) {
                                        smsManager2.sendTextMessage(phoneNumber2, null, part, null, null);
                                    }


                                    SmsManager smsManager3 = SmsManager.getDefault();

                                    for (String part : parts) {
                                        smsManager3.sendTextMessage("+8801611391192", null, part, null, null);
                                    }


                                    Log.d(TAG, "We passed the code for sending the sms");
                                    Context context = getApplicationContext();
                                    Toast toast = Toast.makeText(context, "Message has been sent", Toast.LENGTH_SHORT);
                                    toast.show();


                                }
                                else {
                                    Context context = getApplicationContext();
                                    Toast toast = Toast.makeText(context, "Unable to send message", Toast.LENGTH_SHORT);
                                    toast.show();

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });

                                


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        });

    }
    //@Override
   /* public boolean onOptionItemSelected(MenuItem item) {
        if (toggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }*/
    @Override
    public void onBackPressed(){
        if(drawLayout.isDrawerOpen(GravityCompat.START)){
            drawLayout.closeDrawer(GravityCompat.START);
        }
        else
        {Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        String username = getIntent().getStringExtra("username");
        switch (item.getItemId()) {
            case R.id.phoneCall:
                Intent intent = new Intent(emergencyButton.this, phoneCallActivity.class);
                intent.putExtra("username",username);
                startActivity(intent);
                break;
            case R.id.txtMessage:
                Intent intent1 = new Intent(emergencyButton.this,text_message.class);
                intent1.putExtra("username",username);
                startActivity(intent1);
                break;
            case R.id.dataView:
                Intent intent3 = new Intent(emergencyButton.this,dvChoices.class);
                startActivity(intent3);
                break;
            case R.id.profile:
                Intent intent2 = new Intent(emergencyButton.this,profile.class);
                intent2.putExtra("username",username);
                startActivity(intent2);
                break;
            case R.id.logout:
                Intent intent4 = new Intent(emergencyButton.this, MainActivity.class);
                startActivity((intent4));
                break;
        }
        //drawLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    public  void menuClick(View view)
    {
        openDrawer(drawLayout);
    }

    private static void openDrawer(DrawerLayout drawLayout) {
        drawLayout.openDrawer(GravityCompat.START);
    }



    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

}