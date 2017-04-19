package com.lee.nature;
import android.app.Application;  
  
public class GlobalVal extends Application{  
    public double gain[] = new double[10]; 
    public int progress[] = new int[10];
    private short iBass;
    private short iDrc;
    private short iDen;
    private short iX2b;
    private boolean bLimit;
    private short back;
    private boolean bBass;
    private boolean bBack;
    private boolean bDrc;
    private boolean bDen;
    private boolean bX2b;
    private boolean swsswitch;
    private boolean headphone;
    private String eqstyle;
    public String getEqstyle(){  
        return this.eqstyle;  
    }  
    public void setEqstyle(String eqstyle){  
        this.eqstyle= eqstyle;  
    } 
    public boolean getHeadphone(){  
        return this.headphone;  
    }  
    public void setHeadphone(boolean headphone){  
        this.headphone= headphone;  
    } 
    public boolean getSwitch(){  
        return this.swsswitch;  
    }  
    public void setSwitch(boolean swsswitch){  
        this.swsswitch= swsswitch;  
    }  
    public double[] getGain(){  
        return this.gain;  
    }  
    public void setGain(double gaintest[]){  
        this.gain= gaintest;  
    }  
    public short getBass(){  
        return this.iBass;  
    }  
    public void setBass(short bass){  
        this.iBass= bass;  
    }  
    public short getDrc(){  
        return this.iDrc;  
    }  
    public void setDrc(short drc){  
        this.iDrc= drc;  
    }  
    public short getDen(){  
        return this.iDen;  
    }  
    public void setDen(short den){  
        this.iDen= den;  
    }  
    public short getX2b(){  
        return this.iX2b;  
    }  
    public void setX2b(short x2b){  
        this.iX2b= x2b;  
    }  
    public void setIsLimitOn(boolean limit){  
        this.bLimit= limit;  
    }  
    public boolean getIsLimitOn(){  
        return this.bLimit;  
    }  

    public short getBack(){  
        return this.back;  
    }  
    public void setBack(short back){  
        this.back= back;  
    }  
    
    
    public void setIsBassOn(boolean bass){  
        this.bBass= bass;  
    }  
    public boolean getIsBassOn(){  
        return this.bBass;  
    } 
    public void setIsDrcOn(boolean drc){  
        this.bDrc= drc;  
    }  
    public boolean getIsDrcOn(){  
        return this.bDrc;  
    } 
    public void setIsDenOn(boolean den){  
        this.bDen= den;  
    }  
    public boolean getIsDenOn(){  
        return this.bDen;  
    } 
    public void setIsX2bOn(boolean x2b){  
        this.bX2b= x2b;  
    }  
    public boolean getIsX2bOn(){  
        return this.bX2b;  
    } 
    
    public void setIsBackOn(boolean back){  
        this.bBack= back;  
    }  
    public boolean getIsBackOn(){  
        return this.bBack;  
    }
    @Override  
    public void onCreate(){   
    	iBass = 0;
    	back = 50;
    	iDrc = 0;
    	iDen = 0;
    	iX2b = 0;
    	bLimit = false;
    	bBack = false;
    	bBass = false;
    	bDen = false;
    	bX2b = false;
    	bDrc = false;
    	swsswitch = false;
    	headphone = false;
    	for(int i = 0; i< 10; i++)
    	{
    		gain[i] = 0.0f;
    		progress[i] = 50;
    	}
        super.onCreate();  
    }  
} 
