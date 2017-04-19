package com.lee.nature;
import com.lee.nature.ListAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import com.lee.nature.ListAdapter.Zujian;
import com.lee.nature.MainActivity.ViewHolder;
import com.lee.nature.RoomActivity;
import com.lee.nature.HeadphoneActivity;
import com.lee.nature.SurroundActivity;
import com.lee.nature.GlobalVal;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
public class ProjectActivity extends Activity {

	ListView listView;	
	List<Map<String, Object>> list;
	String strroom;
	String strhead;
	String strsurr;
	public String strswitch;
	public String headphone;
	public GlobalVal gv;
	public static String SWS_OPEN = "HIFIPARA=STEREOWIDEN_Enable=true;";
	public static String SWS_CLOSE = "HIFIPARA=STEREOWIDEN_Enable=false;";
    AudioManager AM;
	SharedPreferences.Editor editor;
	SharedPreferences read;
	private HeadsetPlugReceiver headsetPlugReceiver; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_main);
		editor = getSharedPreferences("SWS_PARAM1", MODE_PRIVATE).edit();
		read = getSharedPreferences("SWS_PARAM1", MODE_PRIVATE);
		Intent intent=getIntent();
		strroom = intent.getStringExtra("room");
		strhead = intent.getStringExtra("head");
		strsurr = intent.getStringExtra("surr");
		strswitch = intent.getStringExtra("switch");
		headphone = intent.getStringExtra("headphone");
		AM = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
		AM.setParameters(SWS_CLOSE);
		gv = (GlobalVal)getApplication();
		registerHeadsetPlugReceiver(); 
		
		if (AM.isWiredHeadsetOn())
		{
			gv.setHeadphone(true);
			headphone = "on";
		}
		else
		{
			gv.setHeadphone(false);
			headphone = "off";
		}
		if(strroom == null)
		{
			strroom = read.getString("room", "小房间");
		}
		if(strhead == null)
		{
			strhead = read.getString("headphone", "耳机1");
		}
		if(strsurr == null)
		{
			strsurr = read.getString("surround", "宽广模式");
		}
		if(strswitch == null)
		{
			strswitch = "false";
		}
		if(headphone == null)
		{
			headphone = "false";
		}
		listView=(ListView)findViewById(R.id.list);
		list=getData();
		ListAdapter newList = new ListAdapter(this, list);
		listView.setAdapter(newList);

		listView.setOnItemClickListener(new OnItemClickListener(){
	            @Override
	            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,long arg3) 
	            {
	            	if (list.get(arg2).get("headphone").equals("有线耳机"))
	            	{
	            		if(gv.getHeadphone() == true && gv.getSwitch() == true)
	            		{
		        			Intent intent2 = new Intent(ProjectActivity.this,HeadphoneActivity.class);	
		        			intent2.putExtra("projecthead", strhead);
		        			intent2.putExtra("projectsurr", strsurr);
		        			intent2.putExtra("projectroom", strroom);
		        			intent2.putExtra("projectswitch", strswitch);
		        			intent2.putExtra("projectheadphone", headphone);
		        			startActivity(intent2);
	            		}
	            	}
	            	if(list.get(arg2).get("headphone").equals("环绕方式"))
	            	{
	            		if(gv.getHeadphone() == true && gv.getSwitch() == true)
	            		{
		        			Intent intent2 = new Intent(ProjectActivity.this,SurroundActivity.class);
		        			intent2.putExtra("projecthead", strhead);
		        			intent2.putExtra("projectsurr", strsurr);
		        			intent2.putExtra("projectroom", strroom);
		        			intent2.putExtra("projectswitch", strswitch);
		        			intent2.putExtra("projectheadphone", headphone);
		        			startActivity(intent2);
	            		}
	            	}
	            	if(list.get(arg2).get("headphone").equals("混响效果"))
	            	{
	            		if(gv.getHeadphone() == true && gv.getSwitch() == true)
	            		{
		        			Intent intent2 = new Intent(ProjectActivity.this,RoomActivity.class);	
		        			intent2.putExtra("projecthead", strhead);
		        			intent2.putExtra("projectsurr", strsurr);
		        			intent2.putExtra("projectswitch", strswitch);
		        			intent2.putExtra("projectroom", strroom);
		        			intent2.putExtra("projectheadphone", headphone);
		        			startActivity(intent2);
	            		}
	            	}
	            	if(list.get(arg2).get("headphone").equals(""))
	            	{
	            		if(gv.getHeadphone() == true && gv.getSwitch() == true)
	            		{
		        			Intent intent2 = new Intent(ProjectActivity.this,VerticalSeekbarActivity.class);			
		        			startActivity(intent2);
	            		}
	            	}
	            	if(list.get(arg2).get("headphone").equals("SWS Headphone"))
	            	{
	            		if(gv.getHeadphone() == true)
	            		{
		            		Zujian vHollder = (Zujian)arg1.getTag();  
		            		if(strswitch.equals("false"))
		            		{
		            			strswitch = "true";
		            			AM.setParameters(SWS_OPEN);
		            			gv.setSwitch(true);
		            		}
		            		else
		            		{
		            			strswitch = "false";
		            			AM.setParameters(SWS_CLOSE);
		            			gv.setSwitch(false);
		            		}
		            		vHollder.btnswitch.setChecked(new Boolean(strswitch));
		            		listView=(ListView)findViewById(R.id.list);
		            		list=getData();
		            		ListAdapter newList1 = new ListAdapter(ProjectActivity.this, list);
		            		listView.setAdapter(newList1);
	            		}
	            	}
	            	
	            	
	            }
	            });
	}
	/*
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {  
    	        if (keyCode == KeyEvent.KEYCODE_BACK) {  
    	            moveTaskToBack(true);  
    	            return true;  
    	        }  
    	        return super.onKeyDown(keyCode, event);  
    	    }  */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public List<Map<String, Object>> getData(){
		List<Map<String, Object>> list1=new ArrayList<Map<String,Object>>();

		Map<String, Object> map0=new HashMap<String, Object>();
		map0.put("image", R.drawable.ic_launcher);
		map0.put("title", "Family Cinema");
		map0.put("headphone", "SWS Headphone");
		map0.put("visi", 0);
		map0.put("checked",strswitch);
		map0.put("Headset",headphone);
		list1.add(map0);
		
		Map<String, Object> map=new HashMap<String, Object>();
		map.put("image", R.drawable.ic_launcher);
		map.put("title", strhead);
		map.put("headphone", "有线耳机");
		map.put("visi", 4);
		map.put("checked",strswitch);
		map.put("Headset",headphone);
		list1.add(map);
		Map<String, Object> map1=new HashMap<String, Object>();
		map1.put("image", R.drawable.ic_launcher);
		map1.put("title", strsurr);
		map1.put("headphone", "环绕方式");
		map1.put("visi", 4);
		map1.put("checked",strswitch);
		map1.put("Headset",headphone);
		list1.add(map1);
		
		Map<String, Object> map2=new HashMap<String, Object>();
		map2.put("image", R.drawable.ic_launcher);
		map2.put("title", strroom);
		map2.put("headphone", "混响效果");
		map2.put("visi", 4);
		map2.put("checked",strswitch);
		map2.put("Headset",headphone);
		list1.add(map2);
		
		Map<String, Object> map3=new HashMap<String, Object>();
		map3.put("image", R.drawable.ic_launcher);
		map3.put("title", "高级设置");
		map3.put("headphone", "");
		map3.put("visi", 4);
		map3.put("checked",strswitch);
		map3.put("Headset",headphone);
		list1.add(map3);
		return list1;
	}
	public class HeadsetPlugReceiver extends BroadcastReceiver 
	{ 
		private static final String TAG = "HeadsetPlugReceiver"; 
		@Override 
		public void onReceive(Context context, Intent intent) 
		{ 
			if (intent.hasExtra("state"))
			{ 
				if (intent.getIntExtra("state", 0) == 0)
				{ 
			       	 gv.setHeadphone(false);
			       	 headphone = "off";
			   		 listView=(ListView)findViewById(R.id.list);
			   		 list=getData();
			   		 ListAdapter newList1 = new ListAdapter(ProjectActivity.this, list);
			   		 listView.setAdapter(newList1);
				} 
				else if (intent.getIntExtra("state", 0) == 1)
				{ 
			       	 gv.setHeadphone(true); 
			       	 gv.setSwitch(true); 
			       	 headphone = "on";
			       	 strswitch = "true";
			   		 listView=(ListView)findViewById(R.id.list);
			   		 list=getData();
			   		 ListAdapter newList1 = new ListAdapter(ProjectActivity.this, list);
			   		 listView.setAdapter(newList1);
				} 
			} 
		} 
	} 
	private void registerHeadsetPlugReceiver() 
	{ 
		headsetPlugReceiver = new HeadsetPlugReceiver(); 
		IntentFilter intentFilter = new IntentFilter(); 
		intentFilter.addAction("android.intent.action.HEADSET_PLUG"); 
		registerReceiver(headsetPlugReceiver, intentFilter); 
	} 
	@Override 
	public void onDestroy() 
	{ 
		unregisterReceiver(headsetPlugReceiver); 
		super.onDestroy(); 
	} 

    	

}

