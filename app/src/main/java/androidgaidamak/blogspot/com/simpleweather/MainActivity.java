package androidgaidamak.blogspot.com.simpleweather;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;

import java.io.UnsupportedEncodingException;


public class MainActivity extends AppCompatActivity
        implements MainActivityFragment.MainActivityFragmentListener {

    private static final String TAG = "MainActivity";

    private SupportMapFragment mMapFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        final MenuItem menuItem = menu.findItem(R.id.search);
        final SearchView search = (SearchView) menuItem.getActionView();
        Log.v(TAG, "SearchView=" + search);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                Log.v(TAG, "onQueryTextSubmit(" + "query=" + query + ")");
                ((MainActivityFragment) getSupportFragmentManager()
                        .findFragmentById(R.id.informationFragment)).performQuery(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCoordinatesReceived(final double latitude, final double longitude) {
        Log.v(TAG, "lat:" + latitude);
        if (mMapFragment == null) {
            mMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        }
        mMapFragment.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap googleMap) {
                LatLng cityCoordinates = new LatLng(latitude, longitude);
                CameraPosition position = new CameraPosition.Builder()
                        .target(cityCoordinates)
                        .zoom(10)
                        .build();
                googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(position));
            }
        });
    }
}
