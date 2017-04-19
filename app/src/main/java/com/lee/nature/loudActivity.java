package com.lee.nature;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;

import com.lee.nature.GlobalVal;
import com.lee.nature.MyView;
import com.lee.nature.CircleSeekBar;
import com.lee.nature.R;
import com.lee.nature.VerticalSeekbarActivity;
import com.lee.nature.CircleSeekBar.OnCircleSeekBarChangeListener;
import com.lee.nature.VerticalSeekbarActivity.HeadsetPlugReceiver;
public class loudActivity extends Activity implements  OnCircleSeekBarChangeListener
{
	public GlobalVal gv;
    CircleSeekBar csbBass;
    CircleSeekBar csbDrc;
    CircleSeekBar csbDen;
    CircleSeekBar csbX2b;
    SeekBar sbBgn;
    LinearLayout rLayout1;
    RelativeLayout rLayout2;
    Button btnBass;
    Button btnDrc;
    Button btnDen;
    Button btnX2b;
    Button btnBgn;
    Button btnLimit;
    Button btnResetall;
    public int flagBgn;
    public int flagDen;
    public int flagBass;
    public int flagX2b;
    public int flagDrc;
    public int flagLimit;
    double gain[] = new double[10];
    public static String keyLimiterGain = "LimiterGain";
    public static String keyDRCGain = "DRCGain";
    public static String keyBackGain1 = "BGNGain1";
    public static String keyBackGain2 = "BGNGain2";
    MyView view;
    AudioManager AM;
    private HeadsetPlugReceiver headsetPlugReceiver; 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerHeadsetPlugReceiver(); 
        gv = (GlobalVal)getApplication();
        AM = (AudioManager)getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        setContentView(R.layout.activity_loud);
        
        rLayout1 = (LinearLayout)findViewById(R.id.rLayout1);
        rLayout2 = (RelativeLayout)findViewById(R.id.rLayout2);
        btnBass = (Button)findViewById(R.id.btnBass);
        btnDrc = (Button)findViewById(R.id.btnDrc);
        btnDen = (Button)findViewById(R.id.btnDen);
        btnX2b = (Button)findViewById(R.id.btnX2b);
        btnBgn = (Button)findViewById(R.id.btnBgn);
        btnLimit = (Button)findViewById(R.id.btnLimit);
        btnResetall = (Button)findViewById(R.id.btnResetall);
        
        btnBass.setOnClickListener(mbt_on_off);
        btnDrc.setOnClickListener(mbt_on_off);
        btnDen.setOnClickListener(mbt_on_off);
        btnX2b.setOnClickListener(mbt_on_off);
        btnBgn.setOnClickListener(mbt_on_off);
        btnLimit.setOnClickListener(mbt_on_off);
        btnResetall.setOnClickListener(mbt_on_off);
        

        rLayout2.setBackgroundColor(Color.BLACK);
        rLayout1.setBackgroundColor(Color.BLACK);
        csbBass = (CircleSeekBar) findViewById(R.id.csbBass);
        csbBass.setProgress((int)gv.getBass());
        csbDrc = (CircleSeekBar) findViewById(R.id.csbDrc);
        csbDrc.setProgress((int)gv.getDrc());
        csbDen = (CircleSeekBar) findViewById(R.id.csbDen);
        csbDen.setProgress((int)gv.getDen());
        csbX2b = (CircleSeekBar) findViewById(R.id.csbX2b);
        csbX2b.setProgress((int)gv.getX2b());
        sbBgn = (SeekBar) findViewById(R.id.sbBgn);
        sbBgn.setProgress((int)gv.getBack());
        csbBass.setOnSeekBarChangeListener(this);  
        csbDen.setOnSeekBarChangeListener(this);  
        csbDrc.setOnSeekBarChangeListener(this);  
        csbX2b.setOnSeekBarChangeListener(this);  
        sbBgn.setOnSeekBarChangeListener(mSeekBasrListener);
        flagDen = 0;
        flagX2b = 0;
        flagDrc = 0;
        flagBass = 0;
        flagLimit = 0;
        flagBgn = 0;
        if (gv.getIsBackOn())
        {
        	flagBgn = 1;
        	btnBgn.setTextColor(Color.YELLOW);
        }
        if (gv.getIsBassOn())
        {
        	flagBass = 1;
        	btnBass.setTextColor(Color.YELLOW);
        }
        if (gv.getIsDenOn())
        {
        	flagDen = 1;
        	btnDen.setTextColor(Color.YELLOW);
        }
        if (gv.getIsX2bOn())
        {
        	flagX2b = 1;
        	btnX2b.setTextColor(Color.YELLOW);
        }
        if (gv.getIsDrcOn())
        {
        	flagDrc = 1;
        	btnDrc.setTextColor(Color.YELLOW);
        }
        if (gv.getIsLimitOn())
        {
        	flagLimit = 1;
        	btnLimit.setTextColor(Color.YELLOW);
        }
        

        Intent intent=getIntent();
        gain[0] = intent.getDoubleExtra("gain0",0.0f);
        gain[1] = intent.getDoubleExtra("gain1",0.0f);
        gain[2] = intent.getDoubleExtra("gain2",0.0f);
        gain[3] = intent.getDoubleExtra("gain3",0.0f);
        gain[4] = intent.getDoubleExtra("gain4",0.0f);
        gain[5] = intent.getDoubleExtra("gain5",0.0f);
        gain[6] = intent.getDoubleExtra("gain6",0.0f);
        gain[7] = intent.getDoubleExtra("gain7",0.0f);
        gain[8] = intent.getDoubleExtra("gain8",0.0f);
        gain[9] = intent.getDoubleExtra("gain9",0.0f);
       
        rLayout1=(LinearLayout) findViewById(R.id.rLayout1);  
        rLayout1.removeAllViews();
    	view=new MyView(loudActivity.this,gain);   
        //通知view组件重绘    
        view.invalidate();  
        rLayout1.addView(view);
    }

    public void onProgressChanged(CircleSeekBar seekBar, int progress,  
            boolean fromUser) {  
    	    String str;
    	    int iBass,iDrc,iDen,iX2b;
    	    iBass= csbBass.mCurrentProgress;
    	    iDrc = csbDrc.mCurrentProgress;
    	    iDen = csbDen.mCurrentProgress;
    	    iX2b = csbX2b.mCurrentProgress;
    	    gv.setBass((short)iBass);
    	    gv.setDrc((short)iDrc);
    	    gv.setDen((short)iDen);
    	    gv.setX2b((short)iX2b);
    	    if(gv.getIsDrcOn())
    	    {
			    str = keyDRCGain +"="+gv.getDrc();
			    AM.setParameters(str);
    	    }
    }

    private OnSeekBarChangeListener mSeekBasrListener = new OnSeekBarChangeListener() {
    	@Override
    	public void onStopTrackingTouch(SeekBar seekBar) {

    		
    	}
    	@Override
    	public void onStartTrackingTouch(SeekBar arg0) {
    		
    	}
    	@Override
    	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
             String str;
             int leftgain,rightgain;
    		if (arg0.getId() == R.id.sbBgn) {
    			gv.setBack((short)arg1);
        	    if(gv.getIsBackOn())
        	    {
        	    	if(gv.getBack() > 50)
        	    	{
        	    		leftgain = 10 - (int)((gv.getBack() - 50) * 0.2f);
        	    		rightgain = 10;
        	    	}
        	    	else 
        	    	{
        	    		leftgain = 10;
        	    		rightgain = 10 - (int)((50 - gv.getBack()) * 0.2f);
        	    	}
    			    str = keyBackGain1 +"="+leftgain;
    			    AM.setParameters(str);
    			    str = keyBackGain2 +"="+rightgain;
    			    AM.setParameters(str);
        	    }
    		}
  
    	}

    };
View.OnClickListener mbt_on_off = new View.OnClickListener() {
		
		@Override
		public void onClick(View arg0) {

			if ( arg0.getId() == R.id.btnBgn ) {
				String str;
				int leftgain,rightgain;
				if(flagBgn == 0)
				{
					btnBgn.setTextColor(Color.YELLOW);
					flagBgn = 1;
					gv.setIsBackOn(true);
        	    	if(gv.getBack() > 50)
        	    	{
        	    		leftgain = 10 - (int)((gv.getBack() - 50) * 0.2f);
        	    		rightgain = 10;
        	    	}
        	    	else 
        	    	{
        	    		leftgain = 10;
        	    		rightgain = 10 - (int)((50 - gv.getBack()) * 0.2f);
        	    	}
    			    str = keyBackGain1 +"="+leftgain;
    			    AM.setParameters(str);
    			    str = keyBackGain2 +"="+rightgain;
    			    AM.setParameters(str);
				}
				else
				{
					btnBgn.setTextColor(Color.WHITE);
					flagBgn = 0;
					gv.setIsBackOn(false);
    			    str = keyBackGain1 +"="+10;
    			    AM.setParameters(str);
    			    str = keyBackGain2 +"="+10;
    			    AM.setParameters(str);
				}
				
			}
			if ( arg0.getId() == R.id.btnDen ) {
				if(flagDen == 0)
				{
					btnDen.setTextColor(Color.YELLOW);
					flagDen = 1;
					gv.setIsDenOn(true);
				}
				else
				{
					btnDen.setTextColor(Color.WHITE);
					flagDen = 0;
					gv.setIsDenOn(false);
				}
				
			}
			if ( arg0.getId() == R.id.btnX2b ) {
				if(flagX2b == 0)
				{
					btnX2b.setTextColor(Color.YELLOW);
					flagX2b = 1;
					gv.setIsX2bOn(true);
				}
				else
				{
					btnX2b.setTextColor(Color.WHITE);
					flagX2b = 0;
					gv.setIsX2bOn(false);
				}
				
			}
			if ( arg0.getId() == R.id.btnDrc ) {
				String str;
				if(flagDrc == 0)
				{
					btnDrc.setTextColor(Color.YELLOW);
					flagDrc = 1;
					gv.setIsDrcOn(true);
					str = keyDRCGain +"="+gv.getDrc();
					AM.setParameters(str);
				}
				else
				{
					btnDrc.setTextColor(Color.WHITE);
					flagDrc = 0;
					gv.setIsDrcOn(false);
					str = keyDRCGain +"="+0;
					AM.setParameters(str);
				}
				
			}
			if ( arg0.getId() == R.id.btnBass ) {
				if(flagBass == 0)
				{
					btnBass.setTextColor(Color.YELLOW);
					flagBass = 1;
					gv.setIsBassOn(true);
				}
				else
				{
					btnBass.setTextColor(Color.WHITE);
					flagBass = 0;
					gv.setIsBassOn(false);
				}
				
			}
			if ( arg0.getId() == R.id.btnLimit ) {
				String str;
				if(flagLimit == 0)
				{
					str = keyLimiterGain +"="+18;
					btnLimit.setTextColor(Color.YELLOW);
					flagLimit = 1;
					gv.setIsLimitOn(true);
					AM.setParameters(str);
				}
				else
				{
					str = keyLimiterGain +"="+20;
					btnLimit.setTextColor(Color.WHITE);
					flagLimit = 0;
					gv.setIsLimitOn(false);
					AM.setParameters(str);
				}
				
			}
			if ( arg0.getId() == R.id.btnResetall ) {
				csbBass.setProgress(0);
				csbDrc.setProgress(0);
				csbDen.setProgress(0);
				csbX2b.setProgress(0);
				sbBgn.setProgress(50);
				
		        flagBgn = 0;
		        flagDen = 0;
		        flagX2b = 0;
		        flagDrc = 0;
		        flagBass = 0;
		        flagLimit = 0;
		        
				btnLimit.setTextColor(Color.WHITE);
				btnBgn.setTextColor(Color.WHITE);
				btnBass.setTextColor(Color.WHITE);
				btnDrc.setTextColor(Color.WHITE);
				btnDen.setTextColor(Color.WHITE);
				btnX2b.setTextColor(Color.WHITE);
				
	    	    gv.setBass((short)0);
	    	    gv.setDrc((short)0);
	    	    gv.setDen((short)0);
	    	    gv.setX2b((short)0);
	    	    gv.setBack((short)50);
	    	    gv.setIsBassOn(false);
	    	    gv.setIsDrcOn(false);
	    	    gv.setIsLimitOn(false);
	    	    gv.setIsX2bOn(false);
	    	    gv.setIsDenOn(false);
	    	    gv.setIsBackOn(false);
	    	    String str;
			    str = keyDRCGain +"="+gv.getDrc();
			    AM.setParameters(str);
				str = keyLimiterGain +"="+20;
				AM.setParameters(str);
			}

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
		        rLayout2.setBackgroundColor(Color.GRAY);
		        rLayout1.setBackgroundColor(Color.GRAY);
		        csbBass.setOnTouchListener(new View.OnTouchListener(){
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
					return true;
					}
					});
		        csbDrc.setOnTouchListener(new View.OnTouchListener(){
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
					return true;
					}
					});
		        csbDen.setOnTouchListener(new View.OnTouchListener(){
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
					return true;
					}
					});
		        csbX2b.setOnTouchListener(new View.OnTouchListener(){
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
					return true;
					}
					});
		        sbBgn.setEnabled(false);
		        btnBass.setEnabled(false);
		        btnDen.setEnabled(false);
		        btnDrc.setEnabled(false);
		        btnX2b.setEnabled(false);
		        btnBgn.setEnabled(false);
		        btnLimit.setEnabled(false);
		        btnResetall.setEnabled(false);
			} 
			else if (intent.getIntExtra("state", 0) == 1)
			{ 
		        rLayout2.setBackgroundColor(Color.BLACK);
		        rLayout1.setBackgroundColor(Color.BLACK);
		        
		        csbBass.setOnTouchListener(new View.OnTouchListener(){
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
					return false;
					}
					});
		        csbDrc.setOnTouchListener(new View.OnTouchListener(){
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
					return false;
					}
					});
		        csbDen.setOnTouchListener(new View.OnTouchListener(){
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
					return false;
					}
					});
		        csbX2b.setOnTouchListener(new View.OnTouchListener(){
					@Override
					public boolean onTouch(View arg0, MotionEvent arg1) {
					return false;
					}
					});
		        btnDen.setEnabled(true);
		        sbBgn.setEnabled(true);
		        btnBass.setEnabled(true);
		        btnDrc.setEnabled(true);
		        btnX2b.setEnabled(true);
		        btnBgn.setEnabled(true);
		        btnLimit.setEnabled(true);
		        btnResetall.setEnabled(true);

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