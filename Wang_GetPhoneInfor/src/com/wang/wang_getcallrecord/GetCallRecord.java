package com.wang.wang_getcallrecord;
//成功获取通话记录并返回。
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.CallLog;
import android.provider.CallLog.Calls;
import android.provider.ContactsContract.CommonDataKinds.Phone;

public class GetCallRecord {
	public  String[][] GetCallRecord(Context context){
		Cursor cursor = context.getContentResolver().query(CallLog.Calls.CONTENT_URI,                            
		        null, null, null, null); 
		
		
		//ContentResolver resolver = context.getContentResolver();
		//Cursor phoneCursor = resolver.query(Phone.CONTENT_URI,PHONES_PROJECTION, null, null, null);
		String[][] information = new String[5][cursor.getCount()];
		
		
		if(cursor.moveToFirst()){                                                                                
		    do{                                                                                                  
		        CallLog calls =new CallLog();                                                                  
		        //号码                                                                                             
		        String number = cursor.getString(cursor.getColumnIndex(Calls.NUMBER));                           
		        //呼叫类型                                                                                           
		        String type;                                                                                     
		        switch (Integer.parseInt(cursor.getString(cursor.getColumnIndex(Calls.TYPE)))) {                 
		        case Calls.INCOMING_TYPE:         
		       
		            type = "呼入";                                                                                 
		            break;                                                                                       
		        case Calls.OUTGOING_TYPE:                                                                        
		            type = "呼出";                                                                                 
		            break;                                                                                       
		        case Calls.MISSED_TYPE:                                                                          
		            type = "未接";                                                                                 
		            break;                                                                                       
		        default:                                                                                         
		            type = "挂断";//应该是挂断.根据我手机类型判断出的                                                              
		            break;                                                                                       
		        }                                                                                                
		        SimpleDateFormat sfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");                              
		        Date date = new Date(Long.parseLong(cursor.getString(cursor.getColumnIndexOrThrow(Calls.DATE))));
		        //呼叫时间                                                                                           
		        String time = sfd.format(date);                                                                  
		        //联系人                                                                                            
		        String name = cursor.getString(cursor.getColumnIndexOrThrow(Calls.CACHED_NAME));                 
		        //通话时间,单位:s                                                                                      
		        String duration = cursor.getString(cursor.getColumnIndexOrThrow(Calls.DURATION));                
		        information[0][cursor.getPosition()]=time;
		        information[1][cursor.getPosition()]=type;
		        information[2][cursor.getPosition()]=number;
		        information[3][cursor.getPosition()]=name;
		        information[4][cursor.getPosition()]=duration;
		    }while(cursor.moveToNext());                                                                         
		                                                                                                          
		}
		cursor.close();
		return information;
	}
	
}

