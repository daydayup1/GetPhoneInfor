package com.wang.location_baidu1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpException;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;

import com.example.location_baidu1.R;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
import com.baidu.location.Poi;
import com.baidu.location.LocationClientOption.LocationMode;


public class MainActivity extends Activity {
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	public TextView textView;
	public LocationClientOption mOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        mLocationClient.setLocOption(getDefaultLocationClientOption());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView1);
        mLocationClient.start();
    }
    
    public LocationClientOption getDefaultLocationClientOption(){
		if(mOption == null){
			mOption = new LocationClientOption();
			mOption.setLocationMode(LocationMode.Hight_Accuracy);//可选，默认高精度，设置定位模式，高精度，低功耗，仅设备
			mOption.setCoorType("bd09ll");//可选，默认gcj02，设置返回的定位结果坐标系，如果配合百度地图使用，建议设置为bd09ll;
			mOption.setScanSpan(3000);//可选，默认0，即仅定位一次，设置发起定位请求的间隔需要大于等于1000ms才是有效的
		    mOption.setIsNeedAddress(true);//可选，设置是否需要地址信息，默认不需要
		    mOption.setIsNeedLocationDescribe(true);//可选，设置是否需要地址描述
		    mOption.setNeedDeviceDirect(false);//可选，设置是否需要设备方向结果
		    mOption.setLocationNotify(false);//可选，默认false，设置是否当gps有效时按照1S1次频率输出GPS结果
		    mOption.setIgnoreKillProcess(true);//可选，默认true，定位SDK内部是一个SERVICE，并放到了独立进程，设置是否在stop的时候杀死这个进程，默认不杀死   
		    mOption.setIsNeedLocationDescribe(true);//可选，默认false，设置是否需要位置语义化结果，可以在BDLocation.getLocationDescribe里得到，结果类似于“在北京天安门附近”
		    mOption.setIsNeedLocationPoiList(true);//可选，默认false，设置是否需要POI结果，可以在BDLocation.getPoiList里得到
		    mOption.SetIgnoreCacheException(false);//可选，默认false，设置是否收集CRASH信息，默认收集
		}
		return mOption;
	}
    
    
    @Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
	}




	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mLocationClient.unRegisterLocationListener(myListener); //注销掉监听
		mLocationClient.stop(); //停止定位服务
		super.onStop();
	}
	
	
	/*public static String http_get(String url)  {  
	    HttpClient httpClient = null;
	    GetMethod httpGet = null;    
	    String responseBody = "";
	    int time = 0;
	    do{
	        try
	        {
	            httpClient = getHttpClient();
	            httpGet = getHttpGet(url);
	            int statusCode = httpClient.executeMethod(httpGet);
	            Log.i("http","url="+url);
	            if (statusCode != HttpStatus.SC_OK) {
	               
	            }
	            else if(statusCode == HttpStatus.SC_OK){
	                //
	            }            
	            BufferedReader reader = new BufferedReader(new InputStreamReader(httpGet.getResponseBodyAsStream()));
	            StringBuffer stringBuffer = new StringBuffer();
	            String str = "";
	            while((str = reader.readLine())!=null){
	                stringBuffer.append(str);
	            }
	            responseBody = stringBuffer.toString();
	            //System.out.println("XMLDATA=====>"+responseBody);
	            break;           
	        } catch (HttpException e) {
	            time++;
	            if(time < RETRY_TIME) {
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e1) {}
	                continue;
	            }
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        
	            e.printStackTrace();
	           //throw AppException.http(e);
	        } catch (IOException e) {
	            time++;
	            if(time < RETRY_TIME) {
	                try {
	                    Thread.sleep(1000);
	                } catch (InterruptedException e1) {}
	                continue;
	            }
	            // 发生网络异常
	            e.printStackTrace();
	            //throw AppException.http(e);
	        } finally {
	            // 释放连接
	            httpGet.releaseConnection();
	            httpClient = null;
	        }
	    }while(time < RETRY_TIME);
	                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                
	    return responseBody;
	}*/




	public class MyLocationListener implements BDLocationListener {
    	 
        @Override
        public void onReceiveLocation(BDLocation location) {
            //Receive Location
            StringBuffer sb = new StringBuffer(256);
            sb.append("time : ");
            sb.append(location.getTime());
            sb.append("\nerror code : ");
            sb.append(location.getLocType());
            sb.append("\nlatitude : ");
            sb.append(location.getLatitude());
            sb.append("\nlontitude : ");
            sb.append(location.getLongitude());
            sb.append("\nradius : ");
            sb.append(location.getRadius());
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS定位结果
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// 单位：公里每小时
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// 单位：米
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// 单位度
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps定位成功");
 
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// 网络定位结果
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //运营商信息
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("网络定位成功");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
                sb.append("\ndescribe : ");
                sb.append("离线定位成功，离线定位结果也是有效的");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("网络不同导致定位失败，请检查网络是否通畅");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机！");
            }
                sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// 位置语义化信息
                List<Poi> list = location.getPoiList();// POI数据
                if (list != null) {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list) {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
            Log.i("BaiduLocationApiDem：", sb.toString());
            textView.setText(sb.toString());
        }

    }
  
}
