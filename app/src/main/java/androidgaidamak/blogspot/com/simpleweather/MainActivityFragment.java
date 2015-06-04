package androidgaidamak.blogspot.com.simpleweather;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;

import java.io.UnsupportedEncodingException;

import androidgaidamak.blogspot.com.simpleweather.network.GsonRequest;
import androidgaidamak.blogspot.com.simpleweather.network.VolleySingleton;
import butterknife.ButterKnife;
import butterknife.InjectView;


/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {
    public static final String REQUEST_URL = "http://api.openweathermap.org/data/2.5/find?q=%1$s" +
            "&units=metric";
    public static final String IMAGE_URL = "http://openweathermap.org/img/w/%1$s.png";
    private static final String TAG = "MainActivityFragment";

    @InjectView(R.id.weatherIconImageView)
    NetworkImageView weatherIconImageView;
    @InjectView(R.id.temperatureTextView)
    TextView temperatureTextView;
    @InjectView(R.id.humidityTextView)
    TextView humidityTextView;
    @InjectView(R.id.windSpeedTextView)
    TextView windSpeedTextView;
    @InjectView(R.id.windDegreeTextView)
    TextView windDegreeTextView;
    @InjectView(R.id.mainInfoTextView)
    TextView mainInfoTextView;
    @InjectView(R.id.descriptionTextView)
    TextView descriptionTextView;

    MainActivityFragmentListener listener;

    public MainActivityFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof MainActivityFragmentListener) {
            listener = (MainActivityFragmentListener) activity;
        } else {
            throw new IllegalStateException("Activity must implement MainActivityFragmentListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_weather, container, false);
        ButterKnife.inject(this, view);
        return view;
    }

    public void performQuery(String city) {
        String requestUrl = String.format(REQUEST_URL, city);
        GsonRequest<WeatherProtocol> request = new GsonRequest<>(Request.Method.GET, requestUrl,
                WeatherProtocol.class, null, null, mResponseListener, mErrorListener);
        VolleySingleton.getInstance(getActivity()).getRequestQueue().add(request);
    }

    private Response.Listener<WeatherProtocol> mResponseListener =
            new Response.Listener<WeatherProtocol>() {
                @Override
                public void onResponse(WeatherProtocol response) {
                    Log.v(TAG, response.toString());
                    if (response.list.length <= 0) return;
                    WeatherProtocol.CityItem city = response.list[0];
                    temperatureTextView.setText(String.valueOf(city.main.temp));
                    humidityTextView.setText(String.valueOf(city.main.humidity));
                    windSpeedTextView.setText(String.valueOf(city.wind.speed));
                    windDegreeTextView.setText(String.valueOf(city.wind.deg));
                    mainInfoTextView.setText(city.weather[0].main);
                    descriptionTextView.setText(city.weather[0].description);

                    String imageUrl = String.format(IMAGE_URL, city.weather[0].icon);
                    weatherIconImageView.setImageUrl(imageUrl, VolleySingleton
                            .getInstance(getActivity()).getImageLoader());

                    listener.onCoordinatesReceived(city.coord.lat, city.coord.lon);
                }
            };

    private Response.ErrorListener mErrorListener = new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {
            try {
                if (error.networkResponse != null) {
                    Log.v(TAG, new String(error.networkResponse.data, "UTF-8"));
                } else {
                    Log.v(TAG, error.toString());
                }
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }
        }
    };

    public interface MainActivityFragmentListener {
        public void onCoordinatesReceived(double latitude, double longitude);
    }
}
