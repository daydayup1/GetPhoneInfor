package com.wang.wang_getcallrecord;

import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
//��������ðٶȻ�ȡ����λ�õĲ�����Ϣ
public class DefaultLocationClientOption {
	  public LocationClientOption getDefaultLocationClientOption(){
		  LocationClientOption mOption = null;
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
}
