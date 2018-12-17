package todday.funny.seoulcatcher.viewmodel;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageManager;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.Cap;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.android.gms.maps.model.RoundCap;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import todday.funny.seoulcatcher.R;
import todday.funny.seoulcatcher.model.Call;
import todday.funny.seoulcatcher.model.routeModel.Route;
import todday.funny.seoulcatcher.model.routeModel.RouteFeature;
import todday.funny.seoulcatcher.model.routeModel.RouteGeometry;
import todday.funny.seoulcatcher.model.routeModel.RouteType;
import todday.funny.seoulcatcher.util.HeartSpeech;

public class MapViewModel extends BaseViewModel implements OnMapReadyCallback {
    private float MAP_ZOOM_LEVEL_WORLD = 1.0f;
    private float MAP_ZOOM_LEVEL_LANDMASS_CONTINENT = 5.0f;
    private float MAP_ZOOM_LEVEL_CITY = 10.0f;
    private float MAP_ZOOM_LEVEL_STREETS = 15.0f;
    private float MAP_ZOOM_LEVEL_STREETS_BULDINGS = 17.5f;
    private float MAP_ZOOM_LEVEL_BUILDINGS = 20.0f;


    public ObservableField<Call> mCall = new ObservableField<>();
    public ObservableField<Route> mRoute = new ObservableField<>();
    public CompositeDisposable mCompositeDisposable;
    private GoogleMap map;
    private FusedLocationProviderClient fusedLocationProviderClient;
    private HeartSpeech heartSpeech;
    public ObservableBoolean isKindFire = new ObservableBoolean(true);

    public MapViewModel(Context context, Call call) {
        super(context);
        heartSpeech = new HeartSpeech(context);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context);
        mCall.set(call);
        isKindFire.set(call.getKind().equals(context.getString(R.string.fire)));
    }

    public void setCompositeDisposable(CompositeDisposable mCompositeDisposable) {
        this.mCompositeDisposable = mCompositeDisposable;
    }

    public void setMapView(MapView mapView) {
        if (mapView != null) {
            mapView.getMapAsync(this);
        }
    }

    public void speech(View view) {
        if (view instanceof AppCompatImageView && heartSpeech != null) {
            AppCompatImageView imageView = (AppCompatImageView) view;
            if (heartSpeech.isSpeaking()) {
                imageView.setImageResource(R.drawable.ic_volume_up_black_24dp);
                heartSpeech.stopTTS();
            } else {
                imageView.setImageResource(R.drawable.ic_volume_off_black_24dp);
                heartSpeech.speakTTS();
            }
        }
    }

    @SuppressLint("MissingPermission")
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;
        map.setMyLocationEnabled(true);
        getCurrentLocation();
    }

    @SuppressLint("MissingPermission")
    public void getCurrentLocation() {
        map.clear();
        fusedLocationProviderClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                if (map != null) {
                    double lat = location.getLatitude();
                    double lon = location.getLongitude();

                    map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(lat, lon), MAP_ZOOM_LEVEL_STREETS_BULDINGS));
                    getRoute(location);
                }
            }
        });
    }

    private void getRoute(final Location location) {
        if (location != null && mCall.get() != null) {
            final Call call = mCall.get();

            final String startLatitude = String.valueOf(location.getLatitude());
            final String startLongitude = String.valueOf(location.getLongitude());
            final LatLng startLatLng = new LatLng(Double.parseDouble(startLatitude), Double.parseDouble(startLongitude));

            map.addMarker(new MarkerOptions().position(new LatLng(location.getLatitude(), location.getLongitude())).title(mContext.getString(R.string.origin))).showInfoWindow();

            final String endLatitude = call.getLatitude();
            final String endLongitude = call.getLongitude();
            final LatLng endLatLng = new LatLng(Double.parseDouble(endLatitude), Double.parseDouble(endLongitude));

            String endName = call.getAddress();

            map.addMarker(new MarkerOptions().position(new LatLng(Double.parseDouble(call.getLatitude()), Double.parseDouble(call.getLongitude()))).title(endName)).showInfoWindow();

            mCompositeDisposable.add(mServerDataController.getRoute(startLongitude, startLatitude, endLongitude, endLatitude, mContext.getString(R.string.origin), endName).subscribe(new Consumer<Route>() {
                @Override
                public void accept(Route route) throws Exception {
                    if (route != null) {
                        mRoute.set(route);
                        drawRoute(startLatLng, endLatLng);
                    }

                }
            }, new Consumer<Throwable>() {
                @Override
                public void accept(Throwable throwable) throws Exception {
                    throwable.printStackTrace();
                    PolylineOptions polylineOptions = new PolylineOptions();
                    polylineOptions.startCap(new RoundCap());
                    polylineOptions.endCap(new RoundCap());
                    polylineOptions.width(25.f);
                    polylineOptions.color(ContextCompat.getColor(mContext, R.color.colorPrimary));
                    polylineOptions.add(startLatLng);
                    polylineOptions.add(endLatLng);
                    map.addPolyline(polylineOptions);
                }
            }));
        }
    }

    private void drawRoute(LatLng startLatLng, LatLng endLatLng) {
        Route route = mRoute.get();
        try {
            if (route != null) {
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions.startCap(new RoundCap());
                polylineOptions.endCap(new RoundCap());
                polylineOptions.width(25.f);
                polylineOptions.color(ContextCompat.getColor(mContext, R.color.colorPrimary));
                polylineOptions.add(startLatLng);
                ArrayList<RouteFeature> routeFeatures = route.getFeatures();
                for (RouteFeature routeFeature : routeFeatures) {
                    RouteGeometry routeGeometry = routeFeature.getGeometry();
                    String type = routeGeometry.getType();
                    List<Object> coordinateList = routeGeometry.getCoordinates();
                    if (type.equals(RouteType.POINT)) {
                        LatLng latLng = new LatLng((Double) coordinateList.get(1), (Double) coordinateList.get(0));
                        polylineOptions.add(latLng);
                    } else if (type.equals(RouteType.LINE_STRING)) {
                        for (Object item : coordinateList) {
                            ArrayList<Double> itemList = (ArrayList<Double>) item;
                            LatLng latLng = new LatLng(itemList.get(1), itemList.get(0));
                            polylineOptions.add(latLng);
                        }
                    }
                }
                polylineOptions.add(endLatLng);
                map.addPolyline(polylineOptions);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void close() {
        if (heartSpeech != null) {
            if (heartSpeech.isSpeaking()) {
                heartSpeech.stopTTS();
            }
        }
        super.close();
    }
}
