package com.example.whc.myapplication;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;


public class MainActivity extends Activity implements LocationListener{

    private EditText editText;
    private ImageButton btn_search;
    private ImageButton bt_injured;
    private boolean show_injured = false;
    private ImageButton btn_ambulance;
    private boolean show_ambulance = false;
    private ImageButton btn_disaster;
    private boolean show_disaster = false;




    private ImageButton btn_hospital;
    private boolean show_hospital = false;


    private WebView webview;
    private SearchDialog searchDialog;
    private Button btn;
    private LocationManager locationManager;



    private class SearchDialog extends Dialog{

        public SearchDialog(Context context) {
            super(context);
            setContentView(R.layout.search_dialog);
            this.setTitle("請輸入...例：台中火車站");

            editText = (EditText)findViewById(R.id.editText);
            editText.clearFocus();

            btn = (Button)findViewById(R.id.btn);
            btn.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                webview.loadUrl("javascript:goto('"+ editText.getText() + "')");
                editText.setText("");
                searchDialog.dismiss();
                }
            });
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        searchDialog = new SearchDialog(this);
        btn_search = (ImageButton)findViewById(R.id.btn_search);
        btn_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                searchDialog.show();
            }
        });

        bt_injured = (ImageButton)findViewById(R.id.bt_injured);
        bt_injured.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            if(!show_injured){
                webview.loadUrl("javascript:showinjured(true)");
                show_injured = true;
            }else{
                webview.loadUrl("javascript:showinjured(false)");
                show_injured = false;
            }

            }
        });

        btn_ambulance = (ImageButton)findViewById(R.id.btn_ambulance);
        btn_ambulance.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!show_ambulance){
                    webview.loadUrl("javascript:showambulance(true)");
                    show_ambulance = true;
                }else{
                    webview.loadUrl("javascript:showambulance(false)");
                    show_ambulance = false;
                }

            }
        });

        btn_disaster = (ImageButton)findViewById(R.id.btn_disaster);
        btn_disaster.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!show_disaster){
                    webview.loadUrl("javascript:showdisaster(true)");
                    show_disaster = true;
                }else{
                    webview.loadUrl("javascript:showdisaster(false)");
                    show_disaster = false;
                }

            }
        });



        webview = (WebView)findViewById(R.id.webview);
        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl("file:///android_asset/GoogleMap.html");
        webview.addJavascriptInterface(this, "js_debug");
        //locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        //locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, MainActivity.this);

    }

    @JavascriptInterface
    public void Log(String tmp1,String tmp2) {
        Log.i("debug", "tmp1 = "+tmp1+" tmp2 = "+tmp2);
    }

    @Override
    public void onLocationChanged(Location location) {
        Double longitude = location.getLongitude() ;
        Double latitude = location.getLatitude() ;
        locationManager.removeUpdates(MainActivity.this);
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

}