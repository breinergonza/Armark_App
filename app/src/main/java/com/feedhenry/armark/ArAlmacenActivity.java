package com.feedhenry.armark;

import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.wikitude.architect.ArchitectStartupConfiguration;
import com.wikitude.architect.ArchitectView;
import com.wikitude.architect.services.camera.CameraLifecycleListener;
import com.wikitude.common.camera.CameraSettings;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;

import android.Manifest;

import java.io.IOException;

public class ArAlmacenActivity extends AppCompatActivity implements ArchitectViewHolderInterface, GoogleApiClient.OnConnectionFailedListener,
        GoogleApiClient.ConnectionCallbacks {

    private ArchitectView architectView;

    protected ArchitectView.SensorAccuracyChangeListener sensorAccuracyListener;

    protected ArchitectViewHolderInterface.ILocationProvider locationProvider;

    protected ArchitectView.ArchitectWorldLoadedListener worldLoadedListener;

    protected boolean hasGeo;
    protected boolean hasIR;
    protected boolean hasInstant;

    private static final String LOGTAG = "android-localizacion";

    private static final int PETICION_PERMISO_LOCALIZACION = 101;

    private GoogleApiClient apiClient;

    private double Latitud;
    private double Longitud;
    private double Altura;
    private float Exactitud;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar_almacen);

        architectView = (ArchitectView) this.findViewById(R.id.architectView);

        final ArchitectStartupConfiguration config = new ArchitectStartupConfiguration();
        config.setLicenseKey(Constantes.WIKITUDE_SDK_KEY);

        config.setFeatures(this.getFeatures());
        config.setCameraResolution(this.getCameraResolution());
        config.setCamera2Enabled(this.getCamera2Enabled());

        this.architectView.setCameraLifecycleListener(getCameraLifecycleListener());
        try {
			/* first mandatory life-cycle notification */
            this.architectView.onCreate( config );
        } catch (RuntimeException rex) {
            this.architectView = null;
            Toast.makeText(getApplicationContext(), "can't create Architect View", Toast.LENGTH_SHORT).show();
            Log.e(this.getClass().getName(), "Exception in ArchitectView.onCreate()", rex);
        }

        // register valid world loaded listener in architectView, ensure this is set before content is loaded to not miss any event
        if (this.worldLoadedListener != null && this.architectView != null) {
            this.architectView.registerWorldLoadedListener(worldLoadedListener);
        }

        apiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addConnectionCallbacks(this)
                .addApi(LocationServices.API)
                .build();

    }

    private int getFeatures() {
        int features = (hasGeo ? ArchitectStartupConfiguration.Features.Geo : 0) |
                (hasIR ? ArchitectStartupConfiguration.Features.ImageTracking : 0) |
                (hasInstant ? ArchitectStartupConfiguration.Features.InstantTracking : 0) ;
        return features;
    }

    protected CameraLifecycleListener getCameraLifecycleListener() {
        return null;
    }

    protected CameraSettings.CameraResolution getCameraResolution(){
        return CameraSettings.CameraResolution.SD_640x480;
    }
    protected boolean getCamera2Enabled() {
        return false;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        if ( this.architectView != null ) {
            this.architectView.onPostCreate();
        try {

            this.architectView.load( "file:///android_asset/Radar/index.html" );

            if (this.getInitialCullingDistanceMeters() != ArchitectViewHolderInterface.CULLING_DISTANCE_DEFAULT_METERS) {

                this.architectView.setCullingDistance( this.getInitialCullingDistanceMeters() );
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        }
    }

    @Override
    protected void onPostResume() {
        super.onPostResume();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);

            updateUI(lastLocation);
        }

    }

    @Override
    protected void onResume() {
        super.onResume();

        // call mandatory live-cycle method of architectView
        if ( this.architectView != null ) {
            this.architectView.onResume();

            // register accuracy listener in architectView, if set
            if (this.sensorAccuracyListener!=null) {
                this.architectView.registerSensorAccuracyChangeListener( this.sensorAccuracyListener );
            }
        }

        // tell locationProvider to resume, usually location is then (again) fetched, so the GPS indicator appears in status bar
        if ( this.locationProvider != null ) {
            this.locationProvider.onResume();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // call mandatory live-cycle method of architectView
        if ( this.architectView != null ) {
            this.architectView.clearCache();
            this.architectView.onDestroy();
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();

        if ( this.architectView != null ) {
            this.architectView.onLowMemory();
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        // call mandatory live-cycle method of architectView
        if ( this.architectView != null ) {
            this.architectView.onPause();

            // unregister accuracy listener in architectView, if set
            if ( this.sensorAccuracyListener != null ) {
                this.architectView.unregisterSensorAccuracyChangeListener( this.sensorAccuracyListener );
            }
        }

        // tell locationProvider to pause, usually location is then no longer fetched, so the GPS indicator disappears in status bar
        if ( this.locationProvider != null ) {
            this.locationProvider.onPause();
        }
    }

    @Override
    public String getARchitectWorldPath() {
        return null;
    }

    @Override
    public ArchitectView.ArchitectUrlListener getUrlListener() {
        return null;
    }

    @Override
    public int getContentViewId() {
        return 0;
    }

    @Override
    public String getWikitudeSDKLicenseKey() {
        return null;
    }

    @Override
    public int getArchitectViewId() {
        return 0;
    }

    @Override
    public ILocationProvider getLocationProvider(LocationListener locationListener) {
        return null;
    }

    @Override
    public ArchitectView.SensorAccuracyChangeListener getSensorAccuracyListener() {
        return null;
    }

    @Override
    public float getInitialCullingDistanceMeters() {
        return 0;
    }

    @Override
    public ArchitectView.ArchitectWorldLoadedListener getWorldLoadedListener() {
        return null;
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PETICION_PERMISO_LOCALIZACION);
        } else {

            Location lastLocation =
                    LocationServices.FusedLocationApi.getLastLocation(apiClient);

            updateUI(lastLocation);
        }
    }

    private void updateUI(Location loc) {
        if (loc != null) {
            Latitud = loc.getLatitude();
            Longitud = loc.getLongitude();
            Exactitud = loc.getAccuracy();
            Altura = loc.getAltitude();

            try{
                architectView.setLocation(Latitud, Longitud, Altura, Exactitud);
            }catch (Exception e){
                Log.e(LOGTAG, e.getMessage());
            }

        } else {
            Latitud = 0;
            Longitud = 0;
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == PETICION_PERMISO_LOCALIZACION) {
            if (grantResults.length == 1
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {

                //Permiso concedido

                @SuppressWarnings("MissingPermission")
                Location lastLocation =
                        LocationServices.FusedLocationApi.getLastLocation(apiClient);

                updateUI(lastLocation);

            } else {
                //Permiso denegado:
                Log.e(LOGTAG, "Permiso denegado");
            }
        }
    }
}
