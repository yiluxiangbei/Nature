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
public class HeadphoneActivity extends Activity {
	RadioGroup headRG;
	RadioButton head1RB;
	RadioButton head2RB;
	RadioButton head3RB;
	RadioButton head4RB;
	RadioButton head5RB;
	RadioButton head6RB;
	RadioButton head7RB;
	String str;
	String strroom;
	String strsurr;
	String strswitch;
	String strheadphone;
	SharedPreferences.Editor editor;
	SharedPreferences read;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_headphone);
		editor = getSharedPreferences("SWS_PARAM1", MODE_PRIVATE).edit();
		read = getSharedPreferences("SWS_PARAM1", MODE_PRIVATE);
        headRG = (RadioGroup)findViewById(R.id.radioGrouphead);
        head1RB = (RadioButton)findViewById(R.id.btnhead1);
        head2RB = (RadioButton)findViewById(R.id.btnhead2);
        head3RB = (RadioButton)findViewById(R.id.btnhead3);
        head4RB = (RadioButton)findViewById(R.id.btnhead4);
        head5RB = (RadioButton)findViewById(R.id.btnhead5);
        head6RB = (RadioButton)findViewById(R.id.btnhead6);
        head7RB = (RadioButton)findViewById(R.id.btnhead7);
        headRG.setOnCheckedChangeListener(listen);
		Intent intent2=getIntent();
		str = intent2.getStringExtra("projecthead");
		strroom = intent2.getStringExtra("projectroom");
		strsurr = intent2.getStringExtra("projectsurr");
		strswitch = intent2.getStringExtra("projectswitch");
		strheadphone = intent2.getStringExtra("projectheadphone");
		if (str != null)
		{
			if (str.equals("耳机1"))
			{
				head1RB.setChecked(true);
			}
			else if(str.equals("耳机2"))
			{
				head2RB.setChecked(true);
			}
			else if(str.equals("耳机3"))
			{
				head3RB.setChecked(true);
			}
			else if(str.equals("耳机4"))
			{
				head4RB.setChecked(true);
			}
			else if(str.equals("耳机5"))
			{
				head5RB.setChecked(true);
			}
			else if(str.equals("耳机6"))
			{
				head6RB.setChecked(true);
			}
			else if(str.equals("耳机7"))
			{
				head7RB.setChecked(true);
			}
			else
			{
				
			}
		}
		else
		{
			head1RB.setChecked(true);
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
	    		intent.putExtra("head",str);
	    		intent.putExtra("room",strroom);
	    		intent.putExtra("surr",strsurr);
	    		intent.putExtra("switch",strswitch);
	    		intent.putExtra("headphone",strheadphone);
	    		intent.setClass(HeadphoneActivity.this, ProjectActivity.class);
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
		intent.putExtra("head",str);
		intent.putExtra("room",strroom);
		intent.putExtra("surr",strsurr);
		intent.putExtra("switch",strswitch);
		intent.putExtra("headphone",strheadphone);
		intent.setClass(HeadphoneActivity.this, ProjectActivity.class);
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
        	case R.id.btnhead1:
        		str = head1RB.getText()+"";
        		break;
        	case R.id.btnhead2:
        		str = head2RB.getText()+"";
        		break;
        	case R.id.btnhead3:
        		str = head3RB.getText()+"";
        		break;
        	case R.id.btnhead4:
        		str = head4RB.getText()+"";
        		break;
        	case R.id.btnhead5:
        		str = head5RB.getText()+"";
        		break;
        	case R.id.btnhead6:
        		str = head6RB.getText()+"";
        		break;
        	case R.id.btnhead7:
        		str = head7RB.getText()+"";
        		break;
        		default:
        			break;
        	}
        	editor.putString("headphone", str);
        	editor.commit();
        }
};

}