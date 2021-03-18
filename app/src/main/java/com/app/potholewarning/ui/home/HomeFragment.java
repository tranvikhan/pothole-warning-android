package com.app.potholewarning.ui.home;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.app.potholewarning.MainActivity;
import com.app.potholewarning.Models.CheckPoint;
import com.app.potholewarning.Models.MyItem;
import com.app.potholewarning.R;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.maps.android.clustering.ClusterManager;



public class HomeFragment extends Fragment implements
        OnMapReadyCallback {
    // MapController Valiable
    private GoogleMap map;
    private String mapID = null;
    private TextView mapName;
    // private List<Marker> listMarker;
    // Declare a variable for the cluster manager.

    // Manager Checkpoints
    private ClusterManager<MyItem> clusterManager;

    // Manager Line
    private Polyline polyline;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        SupportMapFragment mapFragment = SupportMapFragment.newInstance();
        getChildFragmentManager()
                .beginTransaction()
                .add(R.id.mapview, mapFragment).commit();
        mapFragment.getMapAsync(this);
        mapName = root.findViewById(R.id.text_map_name);
        return root;
    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        try{
            String id = getArguments().getString("mapID");
            Log.e("Hien Map ID",id);
            if(!id.equals("khongco")){
                mapID = id;
            }
        }catch (Exception e){

            Log.e("Erro",e.toString());
        }
        try {
            // Customise the styling of the base map using a JSON object defined
            // in a raw resource file.
            boolean success = googleMap.setMapStyle(
                    MapStyleOptions.loadRawResourceStyle(
                            getActivity(), R.raw.style_map));

            if (!success) {
                Log.e(getTag(), "Style parsing failed.");
            }
        } catch (Resources.NotFoundException e) {
            Log.e(getTag(),"Can't find style. Error: ", e);
        }
        //Add map controler
        map = googleMap;
        //listMarker = new ArrayList<>();
        clusterManager = new ClusterManager<MyItem>(getActivity(), map);
        // Add clusterManager to map
        map.setOnCameraIdleListener(clusterManager);
        map.setOnMarkerClickListener(clusterManager);

        if(mapID !=null) {
            mapName.setText(getArguments().getString("mapName"));
            // Connect Firebase
            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference myRef = database.getReference("routes");
            if(myRef.child(mapID) !=null) {
                // Read from the database
                myRef.child(mapID).child("points").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(dataSnapshot.getValue() ==null){
                            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(9.683908,105.575268), 15));
                            mapName.setText("");
                        }
                        // This method is called once with the initial value and again
                        // whenever data at this location is updated.
                        clusterManager.clearItems();
                        if (polyline != null) polyline.remove();


                        PolylineOptions polylineOptions = new PolylineOptions();
                        polylineOptions.width(5).color(Color.rgb(52, 152, 219)).geodesic(true);

                        LatLng newPoint = new LatLng(0, 0);
                        Log.e("Count ", "" + dataSnapshot.getChildrenCount());
                        int index = 0;
                        for (DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                            index++;
                            CheckPoint post = postSnapshot.getValue(CheckPoint.class);
                            newPoint = new LatLng(post.getLat(), post.getLng());
                            polylineOptions.add(newPoint);

                            clusterManager.addItem(new MyItem(post.getLat(), post.getLng(), "Điểm số " + index, "none"));

                        }
                        polyline = map.addPolyline(polylineOptions);


                        map.moveCamera(CameraUpdateFactory.newLatLngZoom(newPoint, 15));

                        Log.e("Value is: ", dataSnapshot.toString());
                    }

                    @Override
                    public void onCancelled(DatabaseError error) {
                        // Failed to read value
                        Log.w(getTag(), "Failed to read value.", error.toException());
                    }

                });
            }
        }else{
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(9.683908,105.575268), 15));
            mapName.setText("");
        }

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        map.setMyLocationEnabled(true);
    }
}