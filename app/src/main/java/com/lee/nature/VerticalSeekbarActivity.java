package com.lee.nature;
import com.lee.nature.MyView;
import com.lee.nature.R;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.SeekBar;
import android.R.string;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.BroadcastReceiver;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.lee.nature.loudActivity;
import com.lee.nature.ProjectActivity.HeadsetPlugReceiver;

import android.content.Context;
public class VerticalSeekbarActivity extends Activity {
	public MyView view;
	public SeekBar bar1;
	public SeekBar bar2;
	public SeekBar bar3;
	public SeekBar bar4;
	public SeekBar bar5;
	public SeekBar bar6;
	public SeekBar bar7;
	public SeekBar bar8;
	public SeekBar bar9;
	public SeekBar bar10;
	public GlobalVal gv;
	public double []gain;
	public AudioManager AM;
	public Button btnJump;
	public Button btnReset;
	public Button btnPreSet;
	public Button btnStyle;
	public LinearLayout layout;
	public RelativeLayout layout2;
	public RelativeLayout layout3;
	public HorizontalScrollView sv1;
	public String keyEQGain1 = "EQGain1";
	public String keyEQGain2 = "EQGain2";
	public String keyEQGain3 = "EQGain3";
	public String keyEQGain4 = "EQGain4";
	public String keyEQGain5 = "EQGain5";
	public String keyEQGain6 = "EQGain6";
	public String keyEQGain7 = "EQGain7";
	public String keyEQGain8 = "EQGain8";
	public String keyEQGain9 = "EQGain9";
	public String keyEQGain10 = "EQGain10";
	public HeadsetPlugReceiver headsetPlugReceiver; 
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eq);
        registerHeadsetPlugReceiver(); 
        gv = (GlobalVal)getApplication();
	    init();
		AM = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
    }
	private void init() {  
		bar1 = (SeekBar)findViewById(R.id.csr1);
		bar1.setProgress(gv.progress[0]);
		bar1.setOnSeekBarChangeListener(mSeekBasrListener);
		bar2 = (SeekBar)findViewById(R.id.csr2);
		bar2.setProgress(gv.progress[1]);
		bar2.setOnSeekBarChangeListener(mSeekBasrListener);
		bar3 = (SeekBar)findViewById(R.id.csr3);
		bar3.setProgress(gv.progress[2]);
		bar3.setOnSeekBarChangeListener(mSeekBasrListener);
		bar4 = (SeekBar)findViewById(R.id.csr4);
		bar4.setProgress(gv.progress[3]);
		bar4.setOnSeekBarChangeListener(mSeekBasrListener);
		bar5 = (SeekBar)findViewById(R.id.csr5);
		bar5.setProgress(gv.progress[4]);
		bar5.setOnSeekBarChangeListener(mSeekBasrListener);
		bar6 = (SeekBar)findViewById(R.id.csr6);
		bar6.setProgress(gv.progress[5]);
		bar6.setOnSeekBarChangeListener(mSeekBasrListener);
		bar7 = (SeekBar)findViewById(R.id.csr7);
		bar7.setProgress(gv.progress[6]);
		bar7.setOnSeekBarChangeListener(mSeekBasrListener);
		bar8 = (SeekBar)findViewById(R.id.csr8);
		bar8.setProgress(gv.progress[7]);
		bar8.setOnSeekBarChangeListener(mSeekBasrListener);
		bar9 = (SeekBar)findViewById(R.id.csr9);
		bar9.setProgress(gv.progress[8]);
		bar9.setOnSeekBarChangeListener(mSeekBasrListener);
		bar10 = (SeekBar)findViewById(R.id.csr10);
		bar10.setProgress(gv.progress[9]);
		bar10.setOnSeekBarChangeListener(mSeekBasrListener);
		btnReset = (Button)findViewById(R.id.btnReset);
		btnPreSet = (Button)findViewById(R.id.btnPreSet);
		btnStyle = (Button)findViewById(R.id.btnStyle);
		btnStyle.setTextColor(Color.YELLOW);
		btnStyle.setText(gv.getEqstyle());
		btnReset.setOnClickListener(onclick);
		btnPreSet.setOnClickListener(onclick);
		sv1 = (HorizontalScrollView)findViewById(R.id.ScrollView1);
		btnJump = (Button)findViewById(R.id.btnJump);
		btnJump.setOnClickListener(onclick);
	    layout=(LinearLayout) findViewById(R.id.layout); 
	    layout2=(RelativeLayout) findViewById(R.id.LinearLayout02); 
	    layout3=(RelativeLayout) findViewById(R.id.LinearLayout03); 
	    layout.setBackgroundColor(Color.BLACK);
	    layout2.setBackgroundColor(Color.BLACK);
	    layout3.setBackgroundColor(Color.BLACK);
		layout.removeAllViews();
		view=new MyView(VerticalSeekbarActivity.this,gv.gain);   
	    view.invalidate();  
	    layout.addView(view);    
	} 

    View.OnClickListener onclick = new OnClickListener(){
    	public void onClick(View arg0) {
    	    if (arg0.getId() == R.id.btnReset)
    	    {
                gv.progress[0] = 50;
                gv.progress[1] = 50;
                gv.progress[2] = 50;
                gv.progress[3] = 50;
                gv.progress[4] = 50;
                gv.progress[5] = 50;
                gv.progress[6] = 50;
                gv.progress[7] = 50;
                gv.progress[8] = 50;
                gv.progress[9] = 50;
    	    	gv.gain[0] = 0;
    	    	gv.gain[1] = 0;
    	    	gv.gain[2] = 0;
    	    	gv.gain[3] = 0;
    	    	gv.gain[4] = 0;
    	    	gv.gain[5] = 0;
    	    	gv.gain[6] = 0;
    	    	gv.gain[7] = 0;
    	    	gv.gain[8] = 0;
    	    	gv.gain[9] = 0;
    	    	bar1.setProgress(gv.progress[0]);
    	    	bar2.setProgress(gv.progress[1]);
    	    	bar3.setProgress(gv.progress[2]);
    	    	bar4.setProgress(gv.progress[3]);
    	    	bar5.setProgress(gv.progress[4]);
    	    	bar6.setProgress(gv.progress[5]);
    	    	bar7.setProgress(gv.progress[6]);
    	    	bar8.setProgress(gv.progress[7]);
    	    	bar9.setProgress(gv.progress[8]);
    	    	bar10.setProgress(gv.progress[9]);
    	    	btnStyle.setText("");
    	    	layout.removeView(view);
    			view=new MyView(VerticalSeekbarActivity.this,gv.gain);   
    			view.invalidate(); 
    			layout.addView(view);
    	    }
    	    if(arg0.getId() == R.id.btnJump)
    	    {
				Intent intent=new Intent();
				intent.putExtra("gain0",gv.gain[0]);
				intent.putExtra("gain1",gv.gain[1]);
				intent.putExtra("gain2",gv.gain[2]);
				intent.putExtra("gain3",gv.gain[3]);
				intent.putExtra("gain4",gv.gain[4]);
				intent.putExtra("gain5",gv.gain[5]);
				intent.putExtra("gain6",gv.gain[6]);
				intent.putExtra("gain7",gv.gain[7]);
				intent.putExtra("gain8",gv.gain[8]);
				intent.putExtra("gain9",gv.gain[9]);
				intent.setClass(VerticalSeekbarActivity.this, loudActivity.class);
				startActivity(intent);
    	    }
    	    if(arg0.getId() == R.id.btnPreSet)
    	    {
    	    	  int i;
    	    	  AlertDialog.Builder builder = new AlertDialog.Builder(VerticalSeekbarActivity.this);
                  //    指定下拉列表的显示数据
                  final String[] cities = {"低音", "超重低音", "低音&高音", "高音", "平淡","古典","舞曲","摇滚","流行","爵士"};
                  final int[] preset0 = {6,6,6,3,0,0,0,0,0,0};
                  final int[] preset1 = {9,9,9,6,1,0,0,1,0,0};
                  final int[] preset2 = {5,5,5,1,0,0,0,10,10,10};
                  final int[] preset3 = {0,0,0,0,0,0,0,10,10,10};
                  final int[] preset4 = {1,1,1,-2,-2,1,0,1,0,1};
                  final int[] preset5 = {0,0,0,0,0,0,0,-4,-4,-8};
                  final int[] preset6 = {4,2,1,0,0,-2,-1,-1,0,0};
                  final int[] preset7 = {4,4,4,4,2,0,4,4,4,4};
                  final int[] preset8 = {2,4,4,2,2,2,-2,-2,-2,-2};
                  final int[] preset9 = {1,1,1,-1,0,4,6,4,0,1};
                  
                  View view1 = LayoutInflater.from(VerticalSeekbarActivity.this).inflate(R.layout.dialog, null);
                  builder.setView(view1);
                  final Button btn1 = (Button)view1.findViewById(R.id.btn11);
                  final Button btn2 = (Button)view1.findViewById(R.id.btn21);
                  final Button btn3 = (Button)view1.findViewById(R.id.btn31);
                  final Button btn4 = (Button)view1.findViewById(R.id.btn41);
                  final Button btn5 = (Button)view1.findViewById(R.id.btn51);
                  final Button btn6 = (Button)view1.findViewById(R.id.btn61);
                  final Button btn7 = (Button)view1.findViewById(R.id.btn71);
                  final Button btn8 = (Button)view1.findViewById(R.id.btn81);
                  final Button btn9 = (Button)view1.findViewById(R.id.btn91);
                  final Button btn10 = (Button)view1.findViewById(R.id.btn101);
                  final AlertDialog dialog = builder.show();
                  final View.OnClickListener onclk = new OnClickListener()
                  {
                	  public void onClick(View arg0) {
                		  switch(arg0.getId())
                		  {
                		  case R.id.btn11:
                    	    	btnStyle.setText(btn1.getText());
                    	    	gv.setEqstyle(String.valueOf(btn1.getText()));
                          	    for (int i = 0; i<10;i++)
                          	    {
                          		    gv.progress[i] = 50 + (int)(preset0[i]/0.4f);
                          		    gv.gain[i] = preset0[i];
                          	    }
                          	    break;
                		  case R.id.btn21:
                  	    	    btnStyle.setText(btn2.getText());
                  	    	   gv.setEqstyle(String.valueOf(btn2.getText()));
                        	    for (int i = 0; i<10;i++)
                        	    {
                        		    gv.progress[i] = 50 + (int)(preset1[i]/0.4f);
                        		    gv.gain[i] = preset1[i];
                        	    }
                        	    break;
                		  case R.id.btn31:
                  	    	    btnStyle.setText(btn3.getText());
                  	    	    gv.setEqstyle(String.valueOf(btn3.getText()));
                        	    for (int i = 0; i<10;i++)
                        	    {
                        		    gv.progress[i] = 50 + (int)(preset2[i]/0.4f);
                        		    gv.gain[i] = preset2[i];
                        	    }
                        	    break;
	              		  case R.id.btn41:
	                	    	btnStyle.setText(btn4.getText());
	                	    	gv.setEqstyle(String.valueOf(btn4.getText()));
	                      	    for (int i = 0; i<10;i++)
	                      	    {
	                      		    gv.progress[i] = 50 + (int)(preset3[i]/0.4f);
	                      		    gv.gain[i] = preset3[i];
	                      	    }
	                      	    break;
	            		  case R.id.btn51:
	              	    	    btnStyle.setText(btn5.getText());
	              	    	    gv.setEqstyle(String.valueOf(btn5.getText()));
	                    	    for (int i = 0; i<10;i++)
	                    	    {
	                    		    gv.progress[i] = 50 + (int)(preset4[i]/0.4f);
	                    		    gv.gain[i] = preset4[i];
	                    	    }
	                    	    break;
		          		  case R.id.btn61:
		            	    	btnStyle.setText(btn6.getText());
		            	    	gv.setEqstyle(String.valueOf(btn6.getText()));
		                  	    for (int i = 0; i<10;i++)
		                  	    {
		                  		    gv.progress[i] = 50 + (int)(preset5[i]/0.4f);
		                  		    gv.gain[i] = preset5[i];
		                  	    }
		                  	    break;
		        		  case R.id.btn71:
		          	    	    btnStyle.setText(btn7.getText());
		          	    	    gv.setEqstyle(String.valueOf(btn7.getText()));
		                	    for (int i = 0; i<10;i++)
		                	    {
		                		    gv.progress[i] = 50 + (int)(preset6[i]/0.4f);
		                		    gv.gain[i] = preset6[i];
		                	    }
		                	    break;
			      		  case R.id.btn81:
			        	    	btnStyle.setText(btn8.getText());
			        	    	gv.setEqstyle(String.valueOf(btn8.getText()));
			              	    for (int i = 0; i<10;i++)
			              	    {
			              		    gv.progress[i] = 50 + (int)(preset7[i]/0.4f);
			              		    gv.gain[i] = preset7[i];
			              	    }
			              	    break;
	                		  
			      		  case R.id.btn91:
			        	    	btnStyle.setText(btn9.getText());
			        	    	gv.setEqstyle(String.valueOf(btn9.getText()));
			              	    for (int i = 0; i<10;i++)
			              	    {
			              		    gv.progress[i] = 50 + (int)(preset8[i]/0.4f);
			              		    gv.gain[i] = preset8[i];
			              	    }
			              	    break;
			      		  case R.id.btn101:
			        	    	btnStyle.setText(btn10.getText());
			        	    	gv.setEqstyle(String.valueOf(btn10.getText()));
			              	    for (int i = 0; i<10;i++)
			              	    {
			              		    gv.progress[i] = 50 + (int)(preset9[i]/0.4f);
			              		    gv.gain[i] = preset9[i];
			              	    }
			              	    break;
			              default:
			              	    break;
	              		  }
	                	  bar1.setProgress(gv.progress[0]);
	                	  bar2.setProgress(gv.progress[1]);
	                	  bar3.setProgress(gv.progress[2]);
	                	  bar4.setProgress(gv.progress[3]);
	                	  bar5.setProgress(gv.progress[4]);
	                	  bar6.setProgress(gv.progress[5]);
	                	  bar7.setProgress(gv.progress[6]);
	                	  bar8.setProgress(gv.progress[7]);
	                	  bar9.setProgress(gv.progress[8]);
	                	  bar10.setProgress(gv.progress[9]);
                          dialog.dismiss(); 
                	  }
                  };
                  btn1.setOnClickListener(onclk);
                  btn2.setOnClickListener(onclk);
                  btn3.setOnClickListener(onclk);
                  btn4.setOnClickListener(onclk);
                  btn5.setOnClickListener(onclk);
                  btn6.setOnClickListener(onclk);
                  btn7.setOnClickListener(onclk);
                  btn8.setOnClickListener(onclk);
                  btn9.setOnClickListener(onclk);
                  btn10.setOnClickListener(onclk);
                  btnStyle.setTextColor(Color.YELLOW);
                  builder.setInverseBackgroundForced(true);
      	    	 layout.removeView(view);
      			 view=new MyView(VerticalSeekbarActivity.this,gv.gain);   
      			 view.invalidate(); 
      			 layout.addView(view);
    	    }
    	}
};
private OnSeekBarChangeListener mSeekBasrListener = new OnSeekBarChangeListener() {
	@Override
	public void onStopTrackingTouch(SeekBar seekBar) 
	{

	}
	@Override
	public void onStartTrackingTouch(SeekBar arg0) 
	{
		
	}
	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		String str;
		layout.removeView(view);
		switch (arg0.getId()) {
		case R.id.csr1:
			gv.gain[0] = (arg1-50) * 0.4f;
			gv.progress[0] = arg1;
			str = keyEQGain1 + "="+(int)(gv.gain[0] + 20.0f);
			AM.setParameters(str);
			break;
		case R.id.csr2:
			gv.gain[1] = (arg1-50) * 0.4f;
			gv.progress[1] = arg1;
			str = keyEQGain2 + "="+(int)(gv.gain[1] + 20.0f);
			AM.setParameters(str);
			break;
		case R.id.csr3:
			gv.gain[2] = (arg1-50) * 0.4f;
			gv.progress[2] = arg1;
			str = keyEQGain3 + "="+(int)(gv.gain[2] + 20.0f);
			AM.setParameters(str);
			break;
		case R.id.csr4:
			gv.gain[3] = (arg1-50) * 0.4f;
			gv.progress[3] = arg1;
			str = keyEQGain4 + "="+(int)(gv.gain[3] + 20.0f);
			AM.setParameters(str);
			break;
		case R.id.csr5:
			gv.gain[4] = (arg1-50) * 0.4f;
			gv.progress[4] = arg1;
			str = keyEQGain5 + "="+(int)(gv.gain[4] + 20.0f);
			AM.setParameters(str);
			break;
		case R.id.csr6:
			gv.gain[5] = (arg1-50) * 0.4f;
			gv.progress[5] = arg1;
			str = keyEQGain6 + "="+(int)(gv.gain[5] + 20.0f);
			AM.setParameters(str);
			break;
		case R.id.csr7:
			gv.gain[6] = (arg1-50) * 0.4f;
			gv.progress[6] = arg1;
			str = keyEQGain7 + "="+(int)(gv.gain[6] + 20.0f);
			AM.setParameters(str);
			break;
		case R.id.csr8:
			gv.gain[7] = (arg1-50) * 0.4f;
			gv.progress[7] = arg1;
			str = keyEQGain8 + "="+(int)(gv.gain[7] + 20.0f);
			AM.setParameters(str);
			break;
		case R.id.csr9:
			gv.gain[8] = (arg1-50) * 0.4f;
			gv.progress[8] = arg1;
			str = keyEQGain9 + "="+(int)(gv.gain[8] + 20.0f);
			AM.setParameters(str);
			break;
		case R.id.csr10:
			gv.gain[9] = (arg1-50) * 0.4f;
			gv.progress[9] = arg1;
			str = keyEQGain10 + "="+(int)(gv.gain[9] + 20.0f);
			AM.setParameters(str);
			break;
		default:
			break;
		}
		
		view=new MyView(VerticalSeekbarActivity.this,gv.gain);   
		view.invalidate(); 
		layout.addView(view);
	}

};
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
					bar1.setEnabled(false);
					bar2.setEnabled(false);
					bar3.setEnabled(false);
					bar4.setEnabled(false);
					bar5.setEnabled(false);
					bar6.setEnabled(false);
					bar7.setEnabled(false);
					bar8.setEnabled(false);
					bar9.setEnabled(false);
					bar10.setEnabled(false);
					btnReset.setEnabled(false);
					btnPreSet.setEnabled(false);
					btnStyle.setEnabled(false);
					btnJump.setEnabled(false);
					sv1.setOnTouchListener(new View.OnTouchListener(){
						@Override
						public boolean onTouch(View arg0, MotionEvent arg1) {
						return true;
						}
						});
					sv1.setBackgroundColor(Color.GRAY);
					layout.setBackgroundColor(Color.GRAY);
					layout2.setBackgroundColor(Color.GRAY);
					layout3.setBackgroundColor(Color.GRAY);
				} 
				else if (intent.getIntExtra("state", 0) == 1)
				{ 
					bar1.setEnabled(true);
					bar2.setEnabled(true);
					bar3.setEnabled(true);
					bar4.setEnabled(true);
					bar5.setEnabled(true);
					bar6.setEnabled(true);
					bar7.setEnabled(true);
					bar8.setEnabled(true);
					bar9.setEnabled(true);
					bar10.setEnabled(true);
					btnReset.setEnabled(true);
					btnPreSet.setEnabled(true);
					btnStyle.setEnabled(true);
					btnJump.setEnabled(true);
					sv1.setOnTouchListener(new View.OnTouchListener(){
						@Override
						public boolean onTouch(View arg0, MotionEvent arg1) {
						return false;
						}
						});
					sv1.setBackgroundColor(Color.BLACK);
					layout.setBackgroundColor(Color.BLACK);
					layout2.setBackgroundColor(Color.BLACK);
					layout3.setBackgroundColor(Color.BLACK);
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