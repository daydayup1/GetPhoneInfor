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
import com.baidu.location.BDNotifyListener;//�����õ�λ�����ѹ��ܣ���Ҫimport����
import com.baidu.location.Poi;
import com.baidu.location.LocationClientOption.LocationMode;
//�ѳɹ���ȡͨ����¼�͵���λ��
public class MainActivity extends Activity {
	//�����ĸ��������ڻ�ȡ����λ��
	public LocationClient mLocationClient = null;
	public BDLocationListener myListener = new MyLocationListener();
	
	TextView textView1;
	/**
	 * ������ʾͨ����¼
	 */
	String[][] information;
	//���汾������
	String localPhoneNumble;
	//����ͨѶ¼��ϵ��
	String contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //�˴�Ϊ���ü���ʾͨ����¼ģ��
        GetCallRecord getCallRecord=new GetCallRecord(); 
        information=getCallRecord.GetCallRecord(this);
        EditText editText=(EditText)findViewById(R.id.editText1);
        for(int j=0;j<information[0].length;j++){
        	String callRecordString="����ʱ��"+information[0][j]+";"+information[1][j]+";�ֻ���:"+information[2][j]+information[3][j]+";ͨ��ʱ��"+
                    information[4][j]+"s"+"\n";
        	Log.i("information is",callRecordString);
        	editText.append(callRecordString);
        }
       
        //�˴�Ϊ��ȡ����λ��ģ�飬ʹ�ðٶ�SDKʵ��
        textView1=(TextView)findViewById(R.id.textView1);
        mLocationClient = new LocationClient(getApplicationContext());     //����LocationClient��
        mLocationClient.registerLocationListener( myListener );    //ע���������
        mLocationClient.setLocOption(new DefaultLocationClientOption().getDefaultLocationClientOption());
        mLocationClient.start();
        
        //�˴���ȡ��������
        TelephonyManager telephonyManager=(TelephonyManager)this.getSystemService(TELEPHONY_SERVICE);
        localPhoneNumble=new Phonenumber().GetlocPhonenumber(telephonyManager);
        Log.i("�������룺", localPhoneNumble);
        
        //�˴���ȡ�ֻ���ϵ�˼����ֻ���
        contact=new Contacts().getContacts(this);
        Log.e("��ϵ��", contact);
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
                textView1.setText(sb.toString());                            
            }       
    }
    
    //onStop������ֹͣ��λ����
    @Override
	protected void onStop() {
		// TODO Auto-generated method stub
		mLocationClient.unRegisterLocationListener(myListener); //ע��������
		mLocationClient.stop(); //ֹͣ��λ����
		super.onStop();
	}
}
