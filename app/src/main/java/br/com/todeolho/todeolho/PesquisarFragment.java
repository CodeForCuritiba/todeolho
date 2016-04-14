package br.com.todeolho.todeolho;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.URLEncoder;
import java.util.HashMap;

import br.com.todeolho.todeolho.model.MapMarker;

/**
 * Created by gustavomagalhaes on 4/11/16.
 */
public class PesquisarFragment extends SupportMapFragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private GoogleMap mMap;
    private HashMap<String, JSONObject> mMarkersData = new HashMap<>();
    private GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private RequestQueue mQueue;
    private static final String TAG_MARKERS = "markers";

    public PesquisarFragment() {
        super();
    }


    @Override
    public View onCreateView(LayoutInflater arg0, ViewGroup arg1, Bundle arg2) {
        View v = super.onCreateView(arg0, arg1, arg2);
        getMapAsync(this);
        return v;
    }


//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View rootView = inflater.inflate(R.layout.fragment_pesquisar, container, false);
//
//        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
//        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.map);
//        mapFragment.getMapAsync(this);
//
//        // Create an instance of GoogleAPIClient.
//        if (mGoogleApiClient == null) {
//            mGoogleApiClient = new GoogleApiClient.Builder(this.getContext())
//                    .addConnectionCallbacks(this)
//                    .addOnConnectionFailedListener(this)
//                    .addApi(LocationServices.API)
//                    .build();
//        }
//        mGoogleApiClient.connect();
//
//        // Instantiate the RequestQueue.
//        mQueue = Volley.newRequestQueue(this.getContext());
//
//        return rootView;
//    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {

        if (mQueue == null) {
            // Instantiate the RequestQueue.
            mQueue = Volley.newRequestQueue(this.getContext());
        }

        //Get API data using map boundaries
        mMap = googleMap;
        if (ContextCompat.checkSelfPermission(this.getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            final BitmapDescriptor mapMarkerIcon = BitmapDescriptorFactory.fromResource(R.drawable.ic_map_marker);

            //Disabled until we have all the country covered
            //mMap.setMyLocationEnabled(true);
            mMap.setOnCameraChangeListener(new GoogleMap.OnCameraChangeListener() {
                @Override
                public void onCameraChange(CameraPosition cameraPosition) {
                    try {
                        LatLngBounds bounds = mMap.getProjection().getVisibleRegion().latLngBounds;
                        String queryString = "NELatLon=" + URLEncoder.encode(bounds.northeast.latitude + "," + bounds.northeast.longitude, "UTF8") + "&SWLatLon=" + URLEncoder.encode(bounds.southwest.latitude + "," + bounds.southwest.longitude, "UTF8");
                        Log.i(Constants.TAG, "Sending HTTP Request: " + queryString);
                        //TODO use @JsonArrayRequest
                        // Request a string response from the provided URL.
                        StringRequest markersRequest = new StringRequest(Request.Method.GET,
                                Constants.API_URL + "/data/markers?" + queryString,
                                new Response.Listener<String> () {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    mMap.clear();
                                    mMarkersData = new HashMap<String, JSONObject>();
                                    JSONArray markers = new JSONArray(response);
                                    for (int i = 0; markers != null && i < markers.length(); i++) {
                                        JSONObject mark = markers.optJSONObject(i);
                                        MarkerOptions mo = new MarkerOptions()
                                                .position(new LatLng(mark.getDouble("lat"), mark.getDouble("lon")))
                                                .icon(mapMarkerIcon);
                                        Marker mAux = mMap.addMarker(mo);
                                        mMarkersData.put(mAux.getId(), mark);
                                    }
                                } catch (Exception ex) {
                                    Log.e(Constants.TAG, "onResponse: ", ex);
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Display error message
                            }
                        });

                        //cancel any previous request
                        mQueue.cancelAll(TAG_MARKERS);
                        markersRequest.setTag(TAG_MARKERS);

                        // Add the request to the RequestQueue.
                        mQueue.add(markersRequest);
                    } catch (Exception ex) {
                        Log.e(Constants.TAG, "Sending HTTP Request: ", ex);
                    }
                }
            });
        } else {
            // Show rationale and request permission.
        }

        //Let' keep a fixed initial location for now, so we always have data
//        if (mLastLocation != null)
//            moveMapToLastLocation();
//        else {
//
//        }

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                JSONObject data = mMarkersData.get(marker.getId());
                if (data != null) {
                    MarkerFragmentDialog dialog = new MarkerFragmentDialog();
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("marker", new MapMarker(data));
                    dialog.setArguments(bundle);
                    dialog.show(getActivity().getSupportFragmentManager(), "teste");
                    return true;
                }
                return false;
            }
        });

        mMap.getUiSettings().setZoomControlsEnabled(true);
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        moveMapTo(-25.426442510682772, -49.20446656644344);
    }

    private void moveMapToLastLocation() {
//        if (mLastLocation != null)
//            moveMapTo(mLastLocation.getLatitude(), mLastLocation.getLongitude());
    }

    private void moveMapTo(double lat, double lon) {
        if (mMap != null) {
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), 13));
        }
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {

        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
            moveMapToLastLocation();
        } catch (SecurityException ex) {
            Log.e(Constants.TAG, "onConnected: ", ex);
        }
    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
