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
import android.widget.Toast;


import com.example.pc.irmaosmartinhoeasprofissoes.Menus.ChooseGender;
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

public class Tracker extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    LocationManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        stopService(new Intent(this, MusicService.class));
        manager = (LocationManager) getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }

        if(manager.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {

            manager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng coord = new LatLng(latitude,longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try{
                        List<Address> adressList = geocoder.getFromLocation(latitude,longitude,1);
                        String local = adressList.get(0).getSubLocality() + " , " + adressList.get(0).getLocality() + " - " + adressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(coord).title(local));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(coord));
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }else if(manager.isProviderEnabled(LocationManager.GPS_PROVIDER)){
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, new LocationListener() {
                @Override
                public void onLocationChanged(Location location) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();
                    LatLng coord = new LatLng(latitude,longitude);
                    Geocoder geocoder = new Geocoder(getApplicationContext());
                    try{
                        List<Address> adressList = geocoder.getFromLocation(latitude,longitude,1);
                        String local = adressList.get(0).getSubLocality() + " , " + adressList.get(0).getLocality() + " - " + adressList.get(0).getCountryName();
                        mMap.addMarker(new MarkerOptions().position(coord).title(local));
                        mMap.moveCamera(CameraUpdateFactory.newLatLng(coord));
                    }catch(IOException e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onStatusChanged(String provider, int status, Bundle extras) {

                }

                @Override
                public void onProviderEnabled(String provider) {

                }

                @Override
                public void onProviderDisabled(String provider) {

                }
            });
        }

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



        //double latitude = sharedPref.getFloat("lastLatitude", 0.0f);
        //double longitude = sharedPref.getFloat("lastLongitude", 0.0f);

        //LatLng coord = new LatLng(latitude, longitude);
        //mMap.addMarker(new MarkerOptions().position(coord).title("Último jogo"));
        //mMap.moveCamera(CameraUpdateFactory.newLatLng(coord));

    }
}
