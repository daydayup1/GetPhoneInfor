package com.wang.listview;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.ListActivity;
import android.content.ContentResolver;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.Contacts.Phones;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.support.v4.widget.CursorAdapter;
import android.support.v4.widget.SimpleCursorAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

//本程序继承ListActivity，不用new ListView，源码自带，但是这需要将layout文件中的ListView组件id设置为android:id/list
public class MainActivity extends ListActivity {
	
	private static final String[] PHONES_PROJECTION = new String[] {
		Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID };
	
	
	
	
	ArrayAdapter<String> adapter;
	Cursor cursor;
	protected EditText editText1;
	
	List<String>itemsList=new ArrayList<String>();
	//List<Cursor>cursorsList=new Cu
	
	@Override
	protected void onListItemClick(ListView l, View v, int position, long id) {
		// TODO Auto-generated method stub
		super.onListItemClick(l, v, position, id);
		itemsList.remove(position);
		adapter.notifyDataSetChanged();
		
	}

	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       // showListView();
        showcursor();
        
    }
    
    protected void  showcursor() {
 	
		 cursor=getContentResolver().query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
		/*while (cursor.moveToNext()){
			String phoneNumber=cursor.getString(1);
			if (TextUtils.isEmpty(phoneNumber))
				continue;
			Log.e("wang12", phoneNumber);
		}*/
		SimpleCursorAdapter adapter_cursor=new SimpleCursorAdapter(
				MainActivity.this, 
				R.layout.show_cursor, 
				cursor, 
				new String[]{Phone.DISPLAY_NAME,Phone.NUMBER},
				new int[]{R.id.cursor_text1,R.id.cursor_text2});
		
		setListAdapter(adapter_cursor);
		
	}
    
    protected void  showListView() {
    	//mListView=getListView();
		
    	String[] strings=getResources().getStringArray(R.array.List);
		 adapter=new ArrayAdapter<String>(this, android.R.layout.simple_expandable_list_item_1,itemsList);
		setListAdapter(adapter);
		editText1=(EditText)findViewById(R.id.editText1);
	
		editText1.setOnKeyListener(new OnKeyListener() {
			
			@Override
			public boolean onKey(View v, int keyCode, KeyEvent event) {
				// TODO Auto-generated method stub
				if(keyCode==KeyEvent.KEYCODE_ENTER){
					String string=editText1.getText().toString();
					Toast.makeText(MainActivity.this, string, 3000).show();
					
					itemsList.add(string);
					editText1.setText("");
				}
				return false;
			}
		});
		
	}

 
}
