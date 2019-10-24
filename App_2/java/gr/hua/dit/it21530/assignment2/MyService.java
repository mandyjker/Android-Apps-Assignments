package gr.hua.dit.it21530.assignment2;

import android.Manifest;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

public class MyService extends Service {

    public static boolean hasStarted = false; //static field hasStarted lets BroadcastReceiver know if service has started yet or not
    private LocationManager locationManager;

    private void startListening() {
        //check if app has permission to access location
        if (ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,
                Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "I don't have location permissions!", Toast.LENGTH_SHORT);
            return;
        }
        //request location updates every 3 seconds and every 20 meters
        locationManager.requestLocationUpdates(
                LocationManager.GPS_PROVIDER,
                3000,
                20,
                new LocationListener() {
                    @Override
                    public void onLocationChanged(Location location) {
                        //create contentValues object and insert it into database using ContentProvider of Assignment1
                        ContentValues contentValues = new ContentValues();
                        contentValues.put("USERID", MainActivity.userID);
                        contentValues.put("LONGITUDE", location.getLongitude());
                        contentValues.put("LATITUDE", location.getLatitude());
                        Uri uri = getContentResolver().insert(Uri.parse("content://gr.hua.dit.it21530.assignment1/save"), contentValues);
                        Log.i("ServiceLocationChanged", "Uri returned: "+uri);
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
                }
        );
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        //register LocationManager in order to get location updates and start requesting
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        startListening();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //inform user and broadcastReceiver that service has started
        Toast.makeText(this, "service starting", Toast.LENGTH_SHORT).show();
        this.hasStarted = true;
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        //inform user and broadcastReceiver that service has stopped
        super.onDestroy();
        Toast.makeText(this, "service stopped", Toast.LENGTH_LONG).show();
        this.hasStarted = false;
    }
}
