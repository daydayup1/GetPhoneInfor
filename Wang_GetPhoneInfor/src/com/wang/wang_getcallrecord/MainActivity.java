package com.wang.wang_getcallrecord;

import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.wang.wang_getcallrecord.R;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.BDNotifyListener;//假如用到位置提醒功能，需要import该类
import com.baidu.location.Poi;
import com.baidu.location.LocationClientOption.LocationMode;
//已成功获取通话记录和地理位置
public class MainActivity extends Activity {
	//以下四个参数用在获取地理位置
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
	TextView textView1;
	/**
	 * 用来显示通话记录
	 */
	String[][] information;
	//储存本机号码
	String localPhoneNumble;
	//储存通讯录联系人
	String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //此处为调用及显示通话记录模块
        GetCallRecord getCallRecord=new GetCallRecord(); 
        information=getCallRecord.GetCallRecord(this);
        EditText editText=(EditText)findViewById(R.id.editText1);
        for(int j=0;j<information[0].length;j++){
        	String callRecordString="呼叫时间"+information[0][j]+";"+information[1][j]+";手机号:"+information[2][j]+information[3][j]+";通话时间"+
                    information[4][j]+"s"+"\n";
        	Log.i("information is",callRecordString);
        	editText.append(callRecordString);
        }
       
        //此处为获取地理位置模块，使用百度SDK实现
        textView1=(TextView)findViewById(R.id.textView1);
        mLocationClient = new LocationClient(getApplicationContext());     //声明LocationClient类
        mLocationClient.registerLocationListener( myListener );    //注册监听函数
        mLocationClient.setLocOption(new DefaultLocationClientOption().getDefaultLocationClientOption());
        mLocationClient.start();
        
        //此处获取本机号码
        TelephonyManager telephonyManager=(TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
        localPhoneNumble=new Phonenumber().GetlocPhonenumber(telephonyManager);
        Log.i("本机号码：", localPhoneNumble);
        
        //此处获取手机联系人及其手机号
        contact=new Contacts().getContacts(this);
        Log.e("联系人", contact);
    }   

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
                textView1.setText(sb.toString());                            
            }       
    }
    
    //onStop函数中停止定位服务。
    @Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mLocationClient.unRegisterLocationListener(myListener); //注销掉监听
		mLocationClient.stop(); //停止定位服务
		super.onStop();
	}
}
