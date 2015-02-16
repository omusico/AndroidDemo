package com.example.whc.myapplication;

import android.content.Context;
import android.graphics.Color;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.Timer;

public class MapsActivity extends FragmentActivity {

    private GoogleMap mMap; // Might be null if Google Play services APK is not available.

    private LocationManager locationManager;

    private static boolean isExit = false;

    private static boolean hasTask = false;

    private Timer tExit = new Timer();

    private LocationListener locationListener;
    private String mProviderName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        SupportMapFragment mapFrag = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mMap = mapFrag.getMap();
//        mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
        //新建Criteria类
        Criteria locationcriteria = new Criteria();
        //设置精确精度
        locationcriteria.setAccuracy(Criteria.ACCURACY_FINE);
        //不提供海拔高度信息
        locationcriteria.setAltitudeRequired(false);
        //不提供方向信息
        locationcriteria.setBearingRequired(false);
        //允许运营商计费
        locationcriteria.setCostAllowed(true);
        //设置电池消耗为低耗费
        locationcriteria.setPowerRequirement(Criteria.POWER_LOW);

        //使用getSystemService()方法获得位置管理器对象
        locationManager =(LocationManager)getSystemService(Context.LOCATION_SERVICE);
        //locationManager.setTestProviderEnabled("gps", true);
        Toast.makeText(this, "getSystemService", Toast.LENGTH_SHORT).show();


        mProviderName = locationManager.getBestProvider(locationcriteria, true);

        //注册位置监听器
        locationListener = new MyLocationListener();
        locationManager.requestLocationUpdates(mProviderName, 1000, 0, locationListener);

        //检查gps功能开启
        /*
        if(checkgps()){
            Location lastKnownLocation =locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            lastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
            // 如果一下子就能定位成功，则执行以下代码，当用网络定位时，大都能一次性定位成功，当用GPS时，该代码不会起太大作用。
            if (lastKnownLocation != null) {
                // 获取当前地理位置
                // 以动画方式移动到该地理位置
                LatLng latlng = new LatLng(lastKnownLocation.getLatitude(), lastKnownLocation.getLongitude());
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latlng, 15));
                mMap.addMarker(new MarkerOptions().position(latlng).alpha(0.7f));
            }else{
                Toast.makeText(this, "NULL", Toast.LENGTH_SHORT).show();
            }
        }
        */

        test();
    }

    public void test(){
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(new LatLng(22.620693, 120.312043), 20));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.605097, 120.3247118)).alpha(0.7f).title("凱旋－一心路口").icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6116735,120.32621380000002)).alpha(0.7f).title("凱旋－二聖路口").icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6051961,120.3270292)).alpha(0.7f).title("二聖路至崗山西街有爆炸").icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6121885,120.322566)).alpha(0.7f).title("二聖－和平路口").icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6075533,120.31889680000002)).alpha(0.7f).title("一心路派出所附近").icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6217952,120.32844539999999)).alpha(0.7f).title("三信家商附近").icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6227657,120.33097740000001)).alpha(0.7f).title("回報嚴重坍方").icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6080089,120.3183174)).alpha(0.7f).title("爆炸，嚴重坍方").icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6078604,120.3177059)).alpha(0.7f).title("正薪醫院，疏散中").icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6213198,120.3269112)).alpha(0.7f).title("三多福德路口").icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.606325200000004,120.31584980000001)).alpha(0.7f).title("管線冒火").icon(BitmapDescriptorFactory.fromResource(R.drawable.fire)));


        mMap.addMarker(new MarkerOptions().position(new LatLng(22.615853,120.32447580000002)).alpha(0.7f).title("英明國中，避難點").icon(BitmapDescriptorFactory.fromResource(R.drawable.tent)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6016006,120.32470110000001)).alpha(0.7f).title("瑞祥高中、國中，避難點").icon(BitmapDescriptorFactory.fromResource(R.drawable.tent)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6104157,120.3228021)).alpha(0.7f).title("光華國中，避難點").icon(BitmapDescriptorFactory.fromResource(R.drawable.tent)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.611109,120.3178346)).alpha(0.7f).title("中正高工，疏散點").icon(BitmapDescriptorFactory.fromResource(R.drawable.tent)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6214981,120.32415390000001)).alpha(0.7f).title("五權國小，疏散點").icon(BitmapDescriptorFactory.fromResource(R.drawable.tent)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6047801,120.30758860000002)).alpha(0.7f).title("獅甲國中，疏散點").icon(BitmapDescriptorFactory.fromResource(R.drawable.tent)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.625063300000004,120.3183603)).alpha(0.7f).title("文化中心，避難點").icon(BitmapDescriptorFactory.fromResource(R.drawable.tent)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6024029,120.31213760000001)).alpha(0.7f).title("中山光華路口").icon(BitmapDescriptorFactory.fromResource(R.drawable.tent)));

        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6210227,120.3234243)).alpha(0.7f).title("五權國小，緊急醫療中心").icon(BitmapDescriptorFactory.fromResource(R.drawable.singapore)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6118221,120.3247654)).alpha(0.7f).title("二聖醫院，緊急醫療中心").icon(BitmapDescriptorFactory.fromResource(R.drawable.singapore)));


        PolygonOptions rectOptions = new PolygonOptions()
                .add(new LatLng(22.617299,120.31458379999998),
                        new LatLng(22.6131789,120.3141546),
                        new LatLng(22.6113566,120.3333807),
                        new LatLng(22.6237956,120.33196449999998)).fillColor(0x50FF0000).strokeWidth(0);

        mMap.addPolygon(rectOptions);


        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6026803,120.31140799999999)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.601254,120.3123951)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6073553,120.3178883)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6041066,120.32741549999999)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.612188500000002,120.32835960000001)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.616308599999996,120.32827379999999)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6250236,120.333252)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.620903800000004,120.3211069)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6124856999999971,120.3220296)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6085438,120.3162146)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));
        mMap.addMarker(new MarkerOptions().position(new LatLng(22.6026803,120.31140799999999)).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));


        PolylineOptions polylineOptions = new PolylineOptions();
        polylineOptions.add(new LatLng(22.6026803,120.31140799999999)).add(new LatLng(22.601254,120.3123951)).color(Color.GREEN).width(3);
        mMap.addPolyline(polylineOptions);


        //GoogleParser parser;
        //String jsonURL = "http://maps.googleapis.com/maps/api/directions/json?origin=Adelaide,SA&destination=Adelaide,SA&waypoints=optimize:true|Barossa+Valley,SA|Clare,SA|Connawarra,SA|McLaren+Vale,SA&sensor=false";
        //parser = new GoogleParser(jsonURL);
        //Route r =  parser.parse();
        //mMap.addMarker(new MarkerOptions().position(new LatLng(r.getPoints().get(0).getLatitudeE6(),r.getPoints().get(0).getLongitudeE6())).icon(BitmapDescriptorFactory.fromResource(R.drawable.ambulance)));

        
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 当GPS定位时，在这里注册requestLocationUpdates监听就非常重要而且必要。没有这句话，定位不能成功。
        locationManager.requestLocationUpdates(mProviderName, 1000, 0,locationListener);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 取消注册监听
        if (locationManager != null) {
            locationManager.removeUpdates(locationListener);
        }
    }


    private boolean checkgps(){
        boolean providerEnabled= locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
        //若被激活，则返回真值
        if(providerEnabled ==true){
            Toast.makeText(this, "Gps模組活動正常", Toast.LENGTH_SHORT).show();
            return true;
        }
        else{
            Toast.makeText(this, "請打開GPS", Toast.LENGTH_SHORT);
            return false;
        }
    }

    private class MyLocationListener implements LocationListener{
        /**
         * 若位置发生变化，onLocationChanged方法被调用
         */
        @Override
        public void onLocationChanged(Location location) {
            if(location != null){
                //获得经度
                String latitude = Double.toString(location.getLatitude());//经度

                //获得纬度
                String longitude = Double.toString(location.getLongitude());//纬度
                //在文本框中显示
                Toast.makeText(MapsActivity.this, "經度："+longitude+"緯度"+latitude, Toast.LENGTH_SHORT).show();
                MarkerOptions markerOpt = new MarkerOptions();
                markerOpt.position(new LatLng(location.getLatitude(), location.getLongitude()));
                markerOpt.title("我在這裡");
                mMap.addMarker(markerOpt);

            }
            //locationManager.removeUpdates(this);
            locationManager.setTestProviderEnabled("gps", true);
        }
        //若屏蔽提供商，该方法被调用

        @Override
        public void onProviderDisabled(String provider) {
            // TODO Auto-generated method stub
            //Log.i("屏蔽提供商", "Invode");
        }
        //若激活提供商，该方法被调用
        @Override
        public void onProviderEnabled(String provider) {
            // TODO Auto-generated method stub
            // Log.i("激活提供商", "Invode");
        }
        //若状态发生变化，该方法被调用
        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {
            // TODO Auto-generated method stub
            //Log.i("状态发生变化", "Invode");
        }

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            finish();
            System.exit(0);
        }
        return false;
    }
}
