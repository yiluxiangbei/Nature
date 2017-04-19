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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
public class SurroundActivity extends Activity {
	RadioGroup surrRG;
	RadioButton surr1RB;
	RadioButton surr2RB;
	RadioButton surr3RB;

	String str;
	String strroom;
	String strhead;
	String strswitch;
	String strheadphone;
	SharedPreferences.Editor editor;
	SharedPreferences read;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_surround);
		editor = getSharedPreferences("SWS_PARAM1", MODE_PRIVATE).edit();
		read = getSharedPreferences("SWS_PARAM1", MODE_PRIVATE);
        surrRG = (RadioGroup)findViewById(R.id.radioGroupsurround);
        surr1RB = (RadioButton)findViewById(R.id.btnsurround1);
        surr2RB = (RadioButton)findViewById(R.id.btnsurround2);
        surr3RB = (RadioButton)findViewById(R.id.btnsurround3);
        surrRG.setOnCheckedChangeListener(listen);
		Intent intent2=getIntent();
		str = intent2.getStringExtra("projectsurr");
		strroom = intent2.getStringExtra("projectroom");
		strhead = intent2.getStringExtra("projecthead");
		strswitch = intent2.getStringExtra("projectswitch");
		strheadphone = intent2.getStringExtra("projectheadphone");
		if (str != null)
		{
			if (str.equals("宽广模式"))
			{
				surr1RB.setChecked(true);
			}
			else if(str.equals("前置模式"))
			{
				surr2RB.setChecked(true);
			}
			else if(str.equals("近场模式"))
			{
				surr3RB.setChecked(true);
			}
			else
			{
				
			}
		}
		else
		{
			surr1RB.setChecked(true);
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
	    		intent.putExtra("surr",str);
	    		intent.putExtra("room",strroom);
	    		intent.putExtra("head",strhead);
	    		intent.putExtra("switch",strswitch);
	    		intent.putExtra("headphone",strheadphone);
	    		intent.setClass(SurroundActivity.this, ProjectActivity.class);
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
		intent.putExtra("surr",str);
		intent.putExtra("room",strroom);
		intent.putExtra("head",strhead);
		intent.putExtra("switch",strswitch);
		intent.putExtra("headphone",strheadphone);
		intent.setClass(SurroundActivity.this, ProjectActivity.class);
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
        	case R.id.btnsurround1:
        		str = surr1RB.getText()+"";
        		break;
        	case R.id.btnsurround2:
        		str = surr2RB.getText()+"";
        		break;
        	case R.id.btnsurround3:
        		str = surr3RB.getText()+"";
        		break;
        		default:
        			break;
        	}
        	editor.putString("surround", str);
        	editor.commit();
        }
};

}