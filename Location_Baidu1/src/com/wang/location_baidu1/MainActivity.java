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
import com.baidu.location.BDNotifyListener;//�����õ�λ�����ѹ��ܣ���Ҫimport����
import com.baidu.location.Poi;
import com.baidu.location.LocationClientOption.LocationMode;


public class MainActivity extends Activity {
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	public TextView textView;
	public LocationClientOption mOption;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	
    	mLocationClient = new LocationClient(getApplicationContext());     //����LocationClient��
        mLocationClient.registerLocationListener( myListener );    //ע���������
        mLocationClient.setLocOption(getDefaultLocationClientOption());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView=(TextView)findViewById(R.id.textView1);
        mLocationClient.start();
    }
    
    public LocationClientOption getDefaultLocationClientOption(){
		if(mOption == null){
			mOption = new LocationClientOption();
			mOption.setLocationMode(LocationMode.Hight_Accuracy);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
			mOption.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ�������ϰٶȵ�ͼʹ�ã���������Ϊbd09ll;
			mOption.setScanSpan(3000);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		    mOption.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		    mOption.setIsNeedLocationDescribe(true);//��ѡ�������Ƿ���Ҫ��ַ����
		    mOption.setNeedDeviceDirect(false);//��ѡ�������Ƿ���Ҫ�豸������
		    mOption.setLocationNotify(false);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		    mOption.setIgnoreKillProcess(true);//��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��   
		    mOption.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		    mOption.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		    mOption.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
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
		mLocationClient.unRegisterLocationListener(myListener); //ע��������
		mLocationClient.stop(); //ֹͣ��λ����
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
	            // ���������쳣
	            e.printStackTrace();
	            //throw AppException.http(e);
	        } finally {
	            // �ͷ�����
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
            if (location.getLocType() == BDLocation.TypeGpsLocation){// GPS��λ���
                sb.append("\nspeed : ");
                sb.append(location.getSpeed());// ��λ������ÿСʱ
                sb.append("\nsatellite : ");
                sb.append(location.getSatelliteNumber());
                sb.append("\nheight : ");
                sb.append(location.getAltitude());// ��λ����
                sb.append("\ndirection : ");
                sb.append(location.getDirection());// ��λ��
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                sb.append("\ndescribe : ");
                sb.append("gps��λ�ɹ�");
 
            } else if (location.getLocType() == BDLocation.TypeNetWorkLocation){// ���綨λ���
                sb.append("\naddr : ");
                sb.append(location.getAddrStr());
                //��Ӫ����Ϣ
                sb.append("\noperationers : ");
                sb.append(location.getOperators());
                sb.append("\ndescribe : ");
                sb.append("���綨λ�ɹ�");
            } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// ���߶�λ���
                sb.append("\ndescribe : ");
                sb.append("���߶�λ�ɹ������߶�λ���Ҳ����Ч��");
            } else if (location.getLocType() == BDLocation.TypeServerError) {
                sb.append("\ndescribe : ");
                sb.append("��������綨λʧ�ܣ����Է���IMEI�źʹ��嶨λʱ�䵽loc-bugs@baidu.com��������׷��ԭ��");
            } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
                sb.append("\ndescribe : ");
                sb.append("���粻ͬ���¶�λʧ�ܣ����������Ƿ�ͨ��");
            } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
                sb.append("\ndescribe : ");
                sb.append("�޷���ȡ��Ч��λ���ݵ��¶�λʧ�ܣ�һ���������ֻ���ԭ�򣬴��ڷ���ģʽ��һ���������ֽ�����������������ֻ���");
            }
                sb.append("\nlocationdescribe : ");
                sb.append(location.getLocationDescribe());// λ�����廯��Ϣ
                List<Poi> list = location.getPoiList();// POI����
                if (list != null) {
                    sb.append("\npoilist size = : ");
                    sb.append(list.size());
                    for (Poi p : list) {
                        sb.append("\npoi= : ");
                        sb.append(p.getId() + " " + p.getName() + " " + p.getRank());
                    }
                }
            Log.i("BaiduLocationApiDem��", sb.toString());
            textView.setText(sb.toString());
        }

    }
  
}
