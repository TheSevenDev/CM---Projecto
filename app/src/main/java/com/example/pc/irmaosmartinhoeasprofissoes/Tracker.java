package com.example.pc.irmaosmartinhoeasprofissoes;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;

import com.example.pc.irmaosmartinhoeasprofissoes.Menus.Settings;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;

/**
 * Mostra um mapa no ecrâ sendo a posição marcada o sítio onde foi iniciado um jogo da aplicação pela última vez.
 */
public class Tracker extends FragmentActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;


    /**
     * Inicia o mapa e para a música de fundo do menu principal.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tracker);

        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        stopService(new Intent(this, MusicService.class));
    }


    /**
     * Consulta a última posição gravada e coloca um marcador no mapa.
     * O zoom inicial é o adequado para consultar a posição.
     */
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

    /**
     * Inicia a atividade das configurações.
     */
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
