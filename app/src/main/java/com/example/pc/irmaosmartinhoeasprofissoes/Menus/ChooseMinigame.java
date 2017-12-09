package com.example.pc.irmaosmartinhoeasprofissoes.Menus;

import android.Manifest;
import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.pc.irmaosmartinhoeasprofissoes.GeneralActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.R;
import com.example.pc.irmaosmartinhoeasprofissoes.cook.CookActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.firefighter.FirefighterActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.painter.PainterActivity;
import com.example.pc.irmaosmartinhoeasprofissoes.pilot.PilotActivity;

public class ChooseMinigame extends GeneralActivity implements LocationListener{
    private ImageButton firefighter, baker, teacher, painter, pilot;
    private TextView firefighterText,bakerText,teacherText,painterText,pilotText;
    private ImageView chooseMale, chooseFemale;
    private static final int PERMISSIONS_CONSTANT = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_minigame);

        if(!MainMenu.mp.isPlaying()) {
            MainMenu.mp.seekTo(MainMenu.mp.getCurrentPosition() - 100);
            MainMenu.mp.start();
        }

        firefighter = (ImageButton)findViewById(R.id.imgbtn_firefighter);
        baker = (ImageButton)findViewById(R.id.imgbtn_baker);
        teacher = (ImageButton)findViewById(R.id.imgbtn_teacher);
        painter = (ImageButton)findViewById(R.id.imgbtn_painter);
        pilot = (ImageButton)findViewById(R.id.imgbtn_pilot);

        firefighterText = (TextView) findViewById(R.id.btnBombeiro);
        bakerText = (TextView) findViewById(R.id.btnPasteleiro);
        teacherText = (TextView) findViewById(R.id.btnProfessor);
        painterText = (TextView) findViewById(R.id.btnPintor);
        pilotText = (TextView) findViewById(R.id.btnAviador);

        chooseMale = (ImageView)findViewById(R.id.img_chooseMale);
        chooseFemale = (ImageView)findViewById(R.id.img_chooseFemale);
        chooseFemale.setImageAlpha(50);

        SharedPreferences sharedPref = ChooseMinigame.this.
                getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);

        LayoutInflater inflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View v = inflater.inflate(R.layout.activity_choose_minigame,null);

        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?


            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.ACCESS_COARSE_LOCATION)) {

                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else  {
                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION},
                        PERMISSIONS_CONSTANT);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }

        if(sharedPref.getInt("gender",0) == 0)
            changeToMale(v);
        else
            changeToFemale(v);
    }

    @Override
    protected void onResume() {
        super.onResume();
        //if(!isServiceRunning(MusicService.class)){
        //    startService(new Intent(this, MusicService.class));
        //}
        if(!MainMenu.mp.isPlaying()) {
            MainMenu.mp.seekTo(MainMenu.mp.getCurrentPosition() - 100);
            MainMenu.mp.start();
        }
    }
    @Override
    protected  void onPause(){
        super.onPause();
        MainMenu.mp.pause();
    }

    @Override
    protected void onStop(){
        super.onStop();

    }

    @Override
    protected  void onDestroy(){
        super.onDestroy();
        if(MainMenu.mp != null) {
            MainMenu.mp.stop();
            MainMenu.mp = null;
        }
    }

    private boolean isServiceRunning(Class<?> serviceClass) {
        ActivityManager manager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

    public void backToMainMenu(View view){
        startActivity(new Intent(getApplicationContext(), MainMenu.class));
        savePosition();
    }

    public void fireFighterGame(View view)
    {
        startActivity(new Intent(getApplicationContext(), FirefighterActivity.class));
        savePosition();
    }

    public void cookGame(View view)
    {
        startActivity(new Intent(getApplicationContext(), CookActivity.class));
        savePosition();
    }

    public void painterGame(View view)
    {
        startActivity(new Intent(getApplicationContext(), PainterActivity.class));
        savePosition();
    }

    public void pilotGame(View view)
    {
        startActivity(new Intent(getApplicationContext(), PilotActivity.class));
        savePosition();
    }

    public void changeToMale(View view){
        SharedPreferences sharedPref = ChooseMinigame.this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("gender", Integer.parseInt(this.getResources().getString(R.string.choose_male)));
        editor.apply();

        chooseMale.setImageAlpha(255);
        chooseFemale.setImageAlpha(127);

        firefighter.setImageResource(R.drawable.jose_bombeiro);
        baker.setImageResource(R.drawable.jose_pasteleiro);
        teacher.setImageResource(R.drawable.jose_professor);
        painter.setImageResource(R.drawable.jose_pintor);
        pilot.setImageResource(R.drawable.jose_piloto);

        firefighterText.setText(R.string.bombeiro);
        bakerText.setText(R.string.pasteleiro);
        teacherText.setText(R.string.professor);
        painterText.setText(R.string.pintor);
        pilotText.setText(R.string.aviador);
    }

    public void changeToFemale(View view){
        SharedPreferences sharedPref = ChooseMinigame.this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putInt("gender", Integer.parseInt(this.getResources().getString(R.string.choose_female)));
        editor.apply();

        chooseFemale.setImageAlpha(255);
        chooseMale.setImageAlpha(127);

        baker.setImageResource(R.drawable.maria_pasteleira);
        painter.setImageResource(R.drawable.maria_pintora);
        pilot.setImageResource(R.drawable.maria_pilota);
        firefighter.setImageResource(R.drawable.maria_bombeira);
        teacher.setImageResource(R.drawable.maria_professora);

        firefighterText.setText(R.string.bombeira);
        bakerText.setText(R.string.pasteleira);
        teacherText.setText(R.string.professora);
        painterText.setText(R.string.pintora);
        pilotText.setText(R.string.aviadora);
    }

    public void savePosition(){
        LocationManager lm = (LocationManager) getSystemService(LOCATION_SERVICE);
        Location lastKnownLocation;
        SharedPreferences sharedPref = null;

        try {
            if (ContextCompat.checkSelfPermission(this,
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

                // Should we show an explanation?

            }
                lm.requestSingleUpdate(LocationManager.GPS_PROVIDER,this,null);
                lastKnownLocation = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);

                //SAVE LAST LOCATION
                sharedPref = this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPref.edit();
                editor.putFloat("lastLatitude", (float)lastKnownLocation.getLatitude());
                editor.putFloat("lastLongitude", (float)lastKnownLocation.getLongitude());
                editor.apply();
            }catch (Exception e){

        }

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
