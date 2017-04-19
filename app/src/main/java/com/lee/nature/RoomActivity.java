package com.lee.nature;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;
import com.lee.nature.ProjectActivity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
public class RoomActivity extends Activity {
	RadioGroup roomRG;
	RadioButton room1RB;
	RadioButton room2RB;
	RadioButton room3RB;
	RadioButton room4RB;
	RadioButton room5RB;
	RadioButton room6RB;
	RadioButton room7RB;
	String str;
	String strsurr;
	String strhead;
	String strswitch;
	String strheadphone;
	SharedPreferences.Editor editor;
	SharedPreferences read;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
		editor = getSharedPreferences("SWS_PARAM1", MODE_PRIVATE).edit();
		read = getSharedPreferences("SWS_PARAM1", MODE_PRIVATE);
        roomRG = (RadioGroup)findViewById(R.id.radioGrouproom);
        room1RB = (RadioButton)findViewById(R.id.btnroom1);
        room2RB = (RadioButton)findViewById(R.id.btnroom2);
        room3RB = (RadioButton)findViewById(R.id.btnroom3);
        room4RB = (RadioButton)findViewById(R.id.btnroom4);
        room5RB = (RadioButton)findViewById(R.id.btnroom5);
        room6RB = (RadioButton)findViewById(R.id.btnroom6);
        room7RB = (RadioButton)findViewById(R.id.btnroom7);
        roomRG.setOnCheckedChangeListener(listen);
		Intent intent2=getIntent();
		str = intent2.getStringExtra("projectroom");
		strsurr = intent2.getStringExtra("projectsurr");
		strhead = intent2.getStringExtra("projecthead");
		strswitch = intent2.getStringExtra("projectswitch");
		strheadphone = intent2.getStringExtra("projectheadphone");
		if (str != null)
		{
			if (str.equals("小房间"))
			{
				room1RB.setChecked(true);
			}
			else if(str.equals("中房间"))
			{
				room2RB.setChecked(true);
			}
			else if(str.equals("大房间"))
			{
				room3RB.setChecked(true);
			}
			else if(str.equals("音乐厅"))
			{
				room4RB.setChecked(true);
			}
			else if(str.equals("小型演奏厅"))
			{
				room5RB.setChecked(true);
			}
			else if(str.equals("中型演奏厅"))
			{
				room6RB.setChecked(true);
			}
			else if(str.equals("大型演奏厅"))
			{
				room7RB.setChecked(true);
			}
			else
			{
				
			}
		}
		else
		{
			room1RB.setChecked(true);
		}
		
    }
	@Override
    public void onStart() {
        super.onStart();
            getActionBar().setDisplayHomeAsUpEnabled(true);
            getActionBar().show();
        
    }
    
	public boolean onOptionsItemSelected(MenuItem item) {  
	    switch (item.getItemId()) {  
	        case android.R.id.home:  
	    		Intent intent=new Intent();
	    		intent.putExtra("room",str);
	    		intent.putExtra("surr",strsurr);
	    		intent.putExtra("head",strhead);
	    		intent.putExtra("switch",strswitch);
	    		intent.putExtra("headphone",strheadphone);
	    		intent.setClass(RoomActivity.this, ProjectActivity.class);
	    		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
	    		startActivity(intent);
	    		overridePendingTransition(0,0); 
	            return true;  
	        default:  
	            return super.onOptionsItemSelected(item);  
	    }  
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    if(keyCode == KeyEvent.KEYCODE_BACK){
		Intent intent=new Intent();
		intent.putExtra("room",str);
		intent.putExtra("surr",strsurr);
		intent.putExtra("head",strhead);
		intent.putExtra("switch",strswitch);
		intent.putExtra("headphone",strheadphone);
		intent.setClass(RoomActivity.this, ProjectActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  
		startActivity(intent);
		overridePendingTransition(0,0);
    }
    return true;
    }
        OnCheckedChangeListener  listen=new OnCheckedChangeListener() { 
        @Override 
        public void onCheckedChanged(RadioGroup group, int checkedId) 
        { 
        	
        	switch (group.getCheckedRadioButtonId()) {
        	case R.id.btnroom1:
        		str = room1RB.getText()+"";
        		break;
        	case R.id.btnroom2:
        		str = room2RB.getText()+"";
        		break;
        	case R.id.btnroom3:
        		str = room3RB.getText()+"";
        		break;
        	case R.id.btnroom4:
        		str = room4RB.getText()+"";
        		break;
        	case R.id.btnroom5:
        		str = room5RB.getText()+"";
        		break;
        	case R.id.btnroom6:
        		str = room6RB.getText()+"";
        		break;
        	case R.id.btnroom7:
        		str = room7RB.getText()+"";
        		break;
        		default:
        			break;
        	}
        	editor.putString("room", str);
        	editor.commit();
        }
};


}
