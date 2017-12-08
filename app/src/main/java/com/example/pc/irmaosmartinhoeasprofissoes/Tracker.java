package com.example.pc.irmaosmartinhoeasprofissoes;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Toast;

import com.example.pc.irmaosmartinhoeasprofissoes.Menus.ChooseGender;
import com.example.pc.irmaosmartinhoeasprofissoes.Menus.MainMenu;
import com.example.pc.irmaosmartinhoeasprofissoes.Menus.Settings;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

public class Tracker extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        stopService(new Intent(this, MusicService.class));


        /*if (Build.VERSION.SDK_INT >= 23 && !isPermissionGranted()) {
            requestPermissions(PERMISSIONS, PERMISSION_ALL);
        } else {
            requestLocation();
        }*/

        //if(!isLocationEnabled())
        //    showAlert(1);

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //CONSULTAR A ULTIMA POSICAO REGISTADA
        SharedPreferences sharedPref = Tracker.this.
                getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);

        //Hide Navigation Bar
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

        double latitude = sharedPref.getFloat("lastLatitude", 0.0f);
        double longitude = sharedPref.getFloat("lastLongitude", 0.0f);

        LatLng coord = new LatLng(latitude, longitude);
        Geocoder geocoder = new Geocoder(getApplicationContext());
        try {
            List<Address> adressList = geocoder.getFromLocation(latitude, longitude, 1);
            String local = "Último jogo - ";
            if(adressList.get(0).getLocality() !=null){
                local += adressList.get(0).getLocality() + " , " + adressList.get(0).getCountryName();
            }else{
                local += adressList.get(0).getCountryName();
            }

            mMap.addMarker(new MarkerOptions().position(coord).title(local));
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(coord, 10.2f));
        }catch (IOException e){

        }
    }

    public void savePosition(){
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location lastKnownLocation;
        SharedPreferences sharedPref = null;

        try {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                    && ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                lm.requestSingleUpdate(LocationManager.GPS_PROVIDER,this,null);
                lastKnownLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                //SAVE LAST LOCATION
                sharedPref = this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putFloat("lastLatitude", (float)lastKnownLocation.getLatitude());
                editor.putFloat("lastLongitude", (float)lastKnownLocation.getLongitude());
                editor.apply();
            }

            }


        catch (Exception e){

        }
    }

    public void backToGameSettings(View view) {
        startActivity(new Intent(getApplicationContext(), Settings.class));
    }

    @Override
    public void onLocationChanged(Location location) {

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
}
