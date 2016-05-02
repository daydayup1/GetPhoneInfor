package com.wang.wang_getcallrecord;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.util.Log;

public class Contacts {
	public String getContacts(Context context){
	final String[] PHONES_PROJECTION = new String[] {
		Phone.DISPLAY_NAME, Phone.NUMBER};
	ArrayList<String > phoneArrayList=new ArrayList<String>();
	//储存手机通信录联系人及其手机号
	String phoneInfor="";	
	 /**联系人显示名称**/  
    final int PHONES_DISPLAY_NAME_INDEX = 0;       
    /**电话号码**/  
    final int PHONES_NUMBER_INDEX = 1;  
    
        Cursor phoneCursor=context.getContentResolver().query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        if (phoneCursor!=null) {
        	while (phoneCursor.moveToNext()) {
        		String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);        	
        		String contactName = phoneCursor.getString(PHONES_DISPLAY_NAME_INDEX);
        		phoneArrayList.add("手机号："+phoneNumber+"；名字："+contactName+"\n");
        	}
        }
        phoneCursor.close();
        for (String aString	 : phoneArrayList) {
			phoneInfor=phoneInfor+aString;
		}
       // Log.e("手机", phoneInfor);	
        return phoneInfor;
	}
}
