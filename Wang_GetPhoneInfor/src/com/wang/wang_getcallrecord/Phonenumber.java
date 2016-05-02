package com.wang.wang_getcallrecord;

import android.R.string;
import android.telephony.TelephonyManager;

public class Phonenumber {

	public String GetlocPhonenumber( TelephonyManager telephonyManager) {
		String localPhoneNumble=telephonyManager.getLine1Number();
		if (localPhoneNumble==null) {
			localPhoneNumble="未成功获取手机号";
		}
		return localPhoneNumble;
	}
}
