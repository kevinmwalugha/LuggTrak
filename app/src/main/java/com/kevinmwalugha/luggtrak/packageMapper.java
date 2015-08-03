package com.kevinmwalugha.luggtrak;

import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class packageMapper extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, com.google.android.gms.location.LocationListener {

        private GoogleApiClient mGoogleApiClient;
        private Location mLastLocation;
        public double lat, lng;
        private LocationRequest mLocationRequest;
        public LatLng MY_LOCATION=null;
        public GoogleMap map;
        private static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;



@Override
protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_mapper);


        if (checkGooglePlayServices()) {
        buildGoogleApiClient();

        //prepare connection request
        createLocationRequest();
        }


        }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_RECOVER_PLAY_SERVICES) {

        if (resultCode == RESULT_OK) {
        // Make sure the app is not already connected or attempting to connect
        if (!mGoogleApiClient.isConnecting() &&
        !mGoogleApiClient.isConnected()) {
        mGoogleApiClient.connect();
        }
        } else if (resultCode == RESULT_CANCELED) {
        Toast.makeText(this, "Google Play Services must be installed.",
        Toast.LENGTH_SHORT).show();
        finish();
        }
        }
        }

/**
 * Create the connection
 */
protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
        .addConnectionCallbacks(this)
        .addOnConnectionFailedListener(this)
        .addApi(LocationServices.API)
        .build();

        }


/**
 * triggered when Google Play Services is connected
 *
 * @param bundle
 */
@Override
public void onConnected(Bundle bundle) {

        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
        mGoogleApiClient);
        if (mLastLocation != null) {

        // lat=mLastLocation.getLatitude();
        // lng=mLastLocation.getLongitude();

        //  Toast.makeText(this, "Latitude:" + mLastLocation.getLatitude() + ", Longitude:" + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();

        showAddress(mLastLocation.getLatitude(), mLastLocation.getLongitude());
        }
//        startLocationUpdates();

        }

@Override
public void onConnectionSuspended(int i) {

        }

@Override
public void onConnectionFailed(ConnectionResult connectionResult) {

        }


    /* Second part*/

@Override
protected void onStart() {
        super.onStart();

        if (mGoogleApiClient != null) {
        mGoogleApiClient.connect();
        }

        }

protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(
                mGoogleApiClient, mLocationRequest, this);
        }

protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);//provide update after every 20 seconds
        mLocationRequest.setFastestInterval(5000);// provide update if there is an update after 5 seconds also
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        }

@Override
public void onLocationChanged(Location location) {
        mLastLocation = location;
        Toast.makeText(this, "Update -> Latitude:" + mLastLocation.getLatitude() + ", Longitude:" + mLastLocation.getLongitude(), Toast.LENGTH_LONG).show();



        }

protected void stopLocationUpdates() {
        if (mGoogleApiClient != null) {
        LocationServices.FusedLocationApi.removeLocationUpdates(
        mGoogleApiClient, this);
        }
        }

@Override
protected void onPause() {
        super.onPause();
        stopLocationUpdates();
        }

@Override
protected void onStop() {
        super.onStop();

        if (mGoogleApiClient != null) {
        mGoogleApiClient.disconnect();
        }


        }

private boolean checkGooglePlayServices() {
        int checkGooglePlayServices = GooglePlayServicesUtil
        .isGooglePlayServicesAvailable(getApplicationContext());
        if (checkGooglePlayServices != ConnectionResult.SUCCESS) {
        /*
    * Google Play Services is missing or update is required
    *  return code could be
    * SUCCESS,
    * SERVICE_MISSING, SERVICE_VERSION_UPDATE_REQUIRED,
    * SERVICE_DISABLED, SERVICE_INVALID.
    */
        GooglePlayServicesUtil.getErrorDialog(checkGooglePlayServices,
        this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();

        return false;
        }

        return true;
        }


        public void showAddress(double latt,double lngg) {

        Geocoder geocoder = new Geocoder(getApplicationContext(), Locale.getDefault());//we pass Locale to ensure that the resulting address is localized to the user's geographic region

        List<Address> addresses = null;
        try {
        addresses = geocoder.getFromLocation(lat,lng,1);
        if(addresses != null || addresses.size() != 0){
        Address address = addresses.get(0);
        ArrayList<String> addressFragments = new ArrayList<String>();

        // Fetch the address lines using getAddressLine,
        // join them, and send them to the thread.
        for(int i = 0; i < address.getMaxAddressLineIndex(); i++) {
        addressFragments.add(address.getAddressLine(i));
        System.err.println("item" + i + ": " + addressFragments);
        }

        //line.separator works the same as \n
        // txtAddress.setText(TextUtils.join(System.getProperty("line.separator"),
        //         addressFragments));
        lat=mLastLocation.getLatitude();
        lng=mLastLocation.getLongitude();

            MY_LOCATION=new LatLng(lat,lng);

            map=((SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.mapper))).getMap();

            Marker hamburg = map.addMarker(new MarkerOptions().position(MY_LOCATION)
                    .title("HERE").snippet("Package Located"));

            // Move the camera instantly to the university with a zoom of 25.
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(MY_LOCATION, 5));

            map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

            // Zoom in, animating the camera.
            map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);

            map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
                @Override
                public void onInfoWindowClick(Marker marker) {
                    Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
                }
            });

        }

        } catch (IOException e) {
        e.printStackTrace();
        }
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_package_mapper, menu);
        return true;
        }

        @Override
        public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
        return true;
        }

        return super.onOptionsItemSelected(item);
        }

    //public void placeMeOnMap(){

//        MY_LOCATION=new LatLng(lat,lng);
//
//        map=((SupportMapFragment) (getSupportFragmentManager().findFragmentById(R.id.mapShower))).getMap();
//
//        Marker hamburg = map.addMarker(new MarkerOptions().position(MY_LOCATION)
//                .title("HERE").snippet("Package Located"));
//
//        // Move the camera instantly to the university with a zoom of 25.
//        map.moveCamera(CameraUpdateFactory.newLatLngZoom(MY_LOCATION, 5));
//
//        map.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
//
//        // Zoom in, animating the camera.
//        map.animateCamera(CameraUpdateFactory.zoomTo(10), 2000, null);
//
//        map.setOnInfoWindowClickListener(new GoogleMap.OnInfoWindowClickListener() {
//            @Override
//            public void onInfoWindowClick(Marker marker) {
//                Toast.makeText(getApplicationContext(), "hello", Toast.LENGTH_SHORT).show();
//            }
//        });
   // }


}


