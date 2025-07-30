package com.example.firepath_mobileapp;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.mapbox.geojson.Point;
import com.mapbox.maps.CameraOptions;
import com.mapbox.maps.MapView;
import com.mapbox.maps.MapboxMap;
import com.mapbox.maps.Style;
import com.mapbox.maps.plugin.Plugin;
import com.mapbox.maps.plugin.annotation.AnnotationConfig;
import com.mapbox.maps.plugin.annotation.AnnotationPlugin;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions;
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationManager;
import com.mapbox.maps.plugin.annotation.generated.PolylineAnnotationOptions;

import java.util.Arrays;

public class NavigationActivity extends AppCompatActivity {

    private MapView mapView;
    private MapboxMap mapboxMap;

    private PointAnnotationManager pointAnnotationManager;
    private PolylineAnnotationManager polylineAnnotationManager;

    private final Point userPoint = Point.fromLngLat(120.9800, 14.6000);
    private final Point firePoint = Point.fromLngLat(120.9842, 14.5995);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        mapView = findViewById(R.id.mapView);
        mapboxMap = mapView.getMapboxMap();

        mapboxMap.loadStyleUri(Style.MAPBOX_STREETS, style -> {
            // ✅ Setup annotation plugin and managers
            AnnotationPlugin annotationPlugin = mapView.getPlugin(Plugin.MAPBOX_ANNOTATION_PLUGIN);
            if (annotationPlugin != null) {
                pointAnnotationManager = annotationPlugin.createPointAnnotationManager(new AnnotationConfig());
                polylineAnnotationManager = annotationPlugin.createPolylineAnnotationManager(new AnnotationConfig());
            }

            renderMap(); // draw markers and route
        });

        Button btnRecalculate = findViewById(R.id.btnRecalculateRoute);
        btnRecalculate.setOnClickListener(v -> renderMap());
    }

    private void renderMap() {
        if (mapboxMap == null || pointAnnotationManager == null || polylineAnnotationManager == null)
            return;

        mapboxMap.setCamera(new CameraOptions.Builder()
                .center(userPoint)
                .zoom(14.0)
                .build());

        pointAnnotationManager.deleteAll();
        pointAnnotationManager.create(new PointAnnotationOptions()
                .withPoint(userPoint)
                .withTextField("You")
                .withTextSize(12.0));
        pointAnnotationManager.create(new PointAnnotationOptions()
                .withPoint(firePoint)
                .withTextField("Fire")
                .withTextSize(12.0));

        polylineAnnotationManager.deleteAll();
        polylineAnnotationManager.create(new PolylineAnnotationOptions()
                .withPoints(Arrays.asList(userPoint, firePoint))
                .withLineColor("#FF0000")
                .withLineWidth(4.0));
    }
}
