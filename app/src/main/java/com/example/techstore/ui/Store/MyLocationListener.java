package com.example.techstore.ui.Store;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.NonNull;

public class MyLocationListener implements LocationListener {
    Context context;

    public MyLocationListener(Context context)
    {
        this.context=context;
    }
    @Override
    public void onLocationChanged(@NonNull Location location) {
        //Toast.makeText(context,location.getLatitude()+ ", "+location.getLongitude(),Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        Toast.makeText(context,"GPS enabled",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        Toast.makeText(context,"GPS disabled",Toast.LENGTH_LONG).show();
    }
    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }
}
