package gr.hua.dit.it21530.assignment3;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    public ArrayList<LatLng> list;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        //get userID from MainActivity and send query to ContentProvider of Assignment1
        Intent intent = getIntent();
        userID = MainActivity.userid;
        list = new ArrayList<>();
        Cursor cursor = getContentResolver().query(
                Uri.parse("content://gr.hua.dit.it21530.assignment1/locations"),
                null,
                null,
                new String[]{userID},
                null);
        if (cursor.moveToFirst()) {
            do {
                //add locations to list
                LatLng latLng = new LatLng(cursor.getDouble(2), cursor.getDouble(3));
                list.add(latLng);
            } while (cursor.moveToNext());
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        Toast.makeText(this, "list has items "+list.size(), Toast.LENGTH_LONG).show();
        mMap = googleMap;
        //add all locations returned from query as Markers on the Map
        for (LatLng ll: list) {
            mMap.addMarker(new MarkerOptions().position(ll).title("Marker"));
        }
        //if no locations returned, then move camera to random location at (100,100)
        //else move it to the first location of the list
        if (list.isEmpty()) {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(new LatLng(100,100)));
        } else {
            mMap.moveCamera(CameraUpdateFactory.newLatLng(list.get(0)));
        }
    }
}
