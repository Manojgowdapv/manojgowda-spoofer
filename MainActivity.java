package com.yourname.spoofer;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.DataOutputStream;

public class MainActivity extends Activity {
    EditText editLat, editLon;
    TextView txtStatus;
    private static final int PERMISSION_REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (checkSelfPermission(android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION}, PERMISSION_REQUEST_CODE);
        }

        new AlertDialog.Builder(this)
            .setTitle("Welcome")
            .setMessage("Developer: manojgowda\n\nFor any information contact: +918150045830 (WhatsApp only)\n\nWebsite: gowdahub.com for updates")
            .setPositiveButton("OK", null)
            .setCancelable(false)
            .show();

        editLat = findViewById(R.id.editLat);
        editLon = findViewById(R.id.editLon);
        txtStatus = findViewById(R.id.txtStatus);
        Button btnStart = findViewById(R.id.btnStart);
        Button btnStop = findViewById(R.id.btnStop);
        WebView webView = findViewById(R.id.webView);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setDomStorageEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.addJavascriptInterface(new WebAppInterface(), "Android");

        String mapHtml = "<!DOCTYPE html><html><head><meta name='viewport' content='width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no' />" +
                "<link rel='stylesheet' href='https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/leaflet.css' />" +
                "<script src='https://cdnjs.cloudflare.com/ajax/libs/leaflet/1.9.4/leaflet.js'></script>" +
                "<style>body,html,#map{width:100%;height:100%;margin:0;padding:0;}" +
                ".locate-btn { position: absolute; bottom: 20px; right: 20px; z-index: 1000; background: white; padding: 10px 14px; border-radius: 50%; box-shadow: 0 2px 5px rgba(0,0,0,0.3); font-size: 18px; cursor: pointer; border: none; }" +
                "</style></head><body><div id='map'></div>" +
                "<button class='locate-btn' onclick='Android.getRealLocation()'>📍</button>" +
                "<script>var map = L.map('map', {zoomControl: false}).setView([20.0, 78.0], 4); " +
                "L.control.zoom({position: 'topright'}).addTo(map);" +
                "L.tileLayer('https://mt1.google.com/vt/lyrs=m&x={x}&y={y}&z={z}').addTo(map); " +
                "var marker; map.on('click', function(e){ if(marker) map.removeLayer(marker); marker = L.marker(e.latlng).addTo(map); Android.setCoords(e.latlng.lat, e.latlng.lng); });" +
                "function moveTo(lat, lon) { map.setView([lat, lon], 16); if(marker) map.removeLayer(marker); marker = L.marker([lat, lon]).addTo(map); Android.setCoords(lat, lon); }" +
                "</script></body></html>";

        webView.loadDataWithBaseURL("http://localhost", mapHtml, "text/html", "UTF-8", null);

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lat = editLat.getText().toString();
                String lon = editLon.getText().toString();

                if (lat.isEmpty() || lon.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Tap a location on the map first!", Toast.LENGTH_SHORT).show();
                    return;
                }

                runRootCommand("echo '" + lat + "," + lon + "' > /data/local/tmp/manoj_coords.txt && chmod 666 /data/local/tmp/manoj_coords.txt");
                txtStatus.setText("Status: SPOOFING ACTIVE (" + lat + ", " + lon + ")");
                txtStatus.setTextColor(0xFF008800);
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                runRootCommand("rm -f /data/local/tmp/manoj_coords.txt");
                txtStatus.setText("Status: REAL LOCATION (Spoofing Off)");
                txtStatus.setTextColor(0xFF880000);
            }
        });
    }

    private void runRootCommand(final String commands) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Process p = Runtime.getRuntime().exec("su");
                    DataOutputStream os = new DataOutputStream(p.getOutputStream());
                    os.writeBytes(commands + "\n");
                    os.writeBytes("exit\n");
                    os.flush();
                    p.waitFor();
                } catch (Exception ignore) {}
            }
        }).start();
    }

    public class WebAppInterface {
        @JavascriptInterface
        public void setCoords(double lat, double lon) {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    editLat.setText(String.format("%.5f", lat));
                    editLon.setText(String.format("%.5f", lon));
                }
            });
        }

        @JavascriptInterface
        public void getRealLocation() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        LocationManager lm = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
                        
                        Location loc = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc == null) {
                            loc = lm.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
                        }

                        if (loc != null) {
                            double lat = loc.getLatitude();
                            double lon = loc.getLongitude();
                            WebView webView = findViewById(R.id.webView);
                            webView.evaluateJavascript("moveTo(" + lat + ", " + lon + ");", null);
                            Toast.makeText(MainActivity.this, "Centered on real location", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(MainActivity.this, "Fetching fresh GPS fix...", Toast.LENGTH_SHORT).show();
                            
                            lm.requestSingleUpdate(LocationManager.GPS_PROVIDER, new LocationListener() {
                                @Override
                                public void onLocationChanged(Location location) {
                                    if (location != null) {
                                        double lat = location.getLatitude();
                                        double lon = location.getLongitude();
                                        WebView webView = findViewById(R.id.webView);
                                        webView.evaluateJavascript("moveTo(" + lat + ", " + lon + ");", null);
                                        Toast.makeText(MainActivity.this, "Centered on real location", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                @Override public void onStatusChanged(String provider, int status, Bundle extras) {}
                                @Override public void onProviderEnabled(String provider) {}
                                @Override public void onProviderDisabled(String provider) {}
                            }, Looper.getMainLooper());
                        }
                    } catch (SecurityException e) {
                        Toast.makeText(MainActivity.this, "Location permission denied", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }
}
