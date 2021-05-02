package com.example.paindiary.fragments;

import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;


import com.example.paindiary.R;
import com.example.paindiary.databinding.MapsFragmentBinding;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.geometry.LatLng;

import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;

import java.util.List;
import java.util.Locale;

public class MapsFragment  extends Fragment {

    private MapView mapView;

    private MapsFragmentBinding binding;

    //Default location set to melbourne
    double lat = -37.876823;
    double lon = 145.045837;

    //String address = "Melbourne, Victoria";

    public MapsFragment(){}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        String token = getString(R.string.mapbox_access_token);
        Mapbox.getInstance(getActivity(), token);
         binding = MapsFragmentBinding.inflate(inflater, container, false);
         View view = binding.getRoot();
        mapView = (MapView) view.findViewById(R.id.mapView);
        mapView.onCreate(savedInstanceState);
        loadMap(lat,lon);
        //View view = inflater.inflate(R.layout.maps_fragment,container,false);

        binding.getMap.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                    String address = binding.address.getText().toString().trim();

                if(!address.isEmpty()){
                    try {
                        Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
                        List<Address> addresses = geocoder.getFromLocationName(address, 1);
                        if(addresses.size()>0){
                            lat = addresses.get(0).getLatitude();
                            lon = addresses.get(0).getLongitude();
                        }else{
                            binding.address.setError("Please enter a valid address in detail");
                        }
                    }catch(Exception e){

                    }
                    loadMap(lat,lon);
                }else{
                    binding.address.setError("Please enter a valid address");
                }


            }
        });

        return view;

    }

    //Method to load map using the latitude and longitude given
    public void loadMap(double lat, double lon){
        final LatLng latLng= new LatLng(lat, lon);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        CameraPosition position = new CameraPosition.Builder().target(latLng).zoom(13).build();
                        mapboxMap.setCameraPosition(position);
                        mapboxMap.addMarker(new MarkerOptions().position(latLng)).setTitle("");
                        //SymbolManager symbolManager = new SymbolManager(mapView, mapboxMap, style);

                        //SymbolOptions symbolOptions = new SymbolOptions().withLatLng(latLng).withIconImage("Eiffel Tower").withIconSize(1.3f);
                    }
                });
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }
    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }
    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }
    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
    }







}
