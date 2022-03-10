package com.example.techstore.ui.Store;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.FragmentActivity;

import com.example.techstore.Database.DbHelper;
import com.example.techstore.R;
import com.example.techstore.databinding.ActivityMapsBinding;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    ImageButton myLocation;
    Button submit;
    private GoogleMap mMap;
    private ActivityMapsBinding binding;
    MyLocationListener myLocationListener;
    LocationManager locationManager;
    DbHelper dbHelper;
    int orderId;
    double totalPrice;
    String myAddress="";
    String email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMapsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        email=getIntent().getExtras().getString("email");
        orderId=getIntent().getExtras().getInt("orderId");
        totalPrice=getIntent().getExtras().getDouble("totalPrice");
        dbHelper=new DbHelper(getApplicationContext());

        submit=findViewById(R.id.submit2);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder
                        = new AlertDialog
                        .Builder(MapsActivity.this);
                builder.setMessage("Do you want to submit ?" + "\nTotal price: " + totalPrice + " EGP");
                builder.setTitle("Check out");
                builder.setCancelable(false);
                builder
                        .setPositiveButton(
                                "submit",
                                new DialogInterface
                                        .OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        dbHelper.submit(orderId,myAddress,totalPrice);
                                        Intent i =new Intent(MapsActivity.this,StoreActivity.class);
                                        i.putExtra("order",1);
                                        i.putExtra("email",email);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                builder
                        .setNegativeButton(
                                "Cancel",
                                new DialogInterface
                                        .OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog,
                                                        int which) {
                                        Intent i =new Intent(MapsActivity.this,StoreActivity.class);
                                        i.putExtra("order",2);
                                        i.putExtra("email",email);
                                        startActivity(i);
                                        finish();
                                    }
                                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });

        myLocationListener=new MyLocationListener(this);
        locationManager=(LocationManager) getSystemService(Context.LOCATION_SERVICE);

        myLocation=findViewById(R.id.my_location);

        try
        {
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER,6000,0,myLocationListener);
        }catch (SecurityException ex)
        {
            Toast.makeText(getApplicationContext(),"You are not allowed to access the current location",Toast.LENGTH_LONG).show();
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);
    }
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(30.04441960,31.235711600),8));

        myLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMap.clear();
                Geocoder geocoder=new Geocoder(getApplicationContext());
                List<Address> addressList;
                Location location=null;
                try{
                    location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                }catch (SecurityException x){
                    Toast.makeText(getApplicationContext(),"You are not allowed to access the current location",Toast.LENGTH_LONG).show();
                }
                if(location!=null)
                {
                    LatLng myPosition=new LatLng(location.getLatitude(),location.getLongitude());
                    try {
                        addressList=geocoder.getFromLocation(myPosition.latitude, myPosition.longitude,1);
                        if(!addressList.isEmpty())
                        {

                            try {
                                addressList = geocoder.getFromLocation(myPosition.latitude, myPosition.longitude, 1);
                                if (!addressList.isEmpty()) {
                                    String addressStr = addressList.get(0).getThoroughfare();
                                    myAddress=addressStr;
                                } else {
                                    Toast.makeText(getApplicationContext(),"No address for this location",Toast.LENGTH_LONG).show();
                                }
                            } catch (IOException e) {
                                Toast.makeText(getApplicationContext(),"Cant get the address, Check your network",Toast.LENGTH_LONG).show();
                            }

                            mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location").snippet(myAddress)).setDraggable(true);

                        }

                    }catch (IOException e)
                    {
                        mMap.addMarker(new MarkerOptions().position(myPosition).title("My Location"));
                    }
                    mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myPosition,15));
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"Please wait untill your position is determined",Toast.LENGTH_LONG).show();
                }
            }
        });
        mMap.setOnMarkerDragListener(new GoogleMap.OnMarkerDragListener() {
            @Override
            public void onMarkerDragStart(Marker marker) {

            }

            @Override
            public void onMarkerDrag(Marker marker) {

            }

            @Override
            public void onMarkerDragEnd(Marker marker) {
                myAddress="";
                Geocoder geocoder=new Geocoder(getApplicationContext());
                List<Address> addressList;
                        try {
                            addressList = geocoder.getFromLocation(marker.getPosition().latitude, marker.getPosition().longitude, 1);
                            if (!addressList.isEmpty()&&addressList.get(0).getThoroughfare()!=null) {
                                String addressStr = addressList.get(0).getThoroughfare();
                                myAddress=addressStr;
                            } else {
                                Toast.makeText(getApplicationContext(),"No address for this location",Toast.LENGTH_LONG).show();
                            }
                        } catch (IOException e) {
                            Toast.makeText(getApplicationContext(),"Cant get the address, Check your network",Toast.LENGTH_LONG).show();
                        }

                    }


        });


    }
}