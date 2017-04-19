package com.lee.nature;

import java.util.LinkedList;
import java.util.List;
import java.lang.Math;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.util.AttributeSet;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.lee.nature.Cubic;
/**
 * 画图用View·
 * @author 113108A006FEU
 *
 */

public class MyView extends View {
    //public int inX [];
    //public int inY [];
    public int i;
    public double []gain = new double[10];
	public List<Point> points = new LinkedList<Point>();
	public List<Integer> points_x = new LinkedList<Integer>();
	public List<Integer> points_y = new LinkedList<Integer>();
	public double []a = new double[10];
	public double []b = new double[10];
	public double []c = new double[10];
	public double []d = new double[10];
	public double []h = new double[9];





	public MyView(Context context) {
		super(context);
		
	}
	public MyView(Context context,double gh[]) {
		super(context);
		
		for (int i=0;i<10;i++)
		{
			gain[i] = gh[i];
		}
	}

	protected void iMedia_SWS_IIRLfShelving(double wd, double G, double Q, double b[], double a[])
	{
	    double wdwd;
	    double wdqd;
	    double gdwdqd;
	    double nrm;

	    wdwd = wd * wd;
	    wdqd = wd * (1.0F / Q);
	    gdwdqd = Math.pow((double)10.0F, (double)(0.05F * Math.abs(G))) * wdqd;


	    if (G > 0.0F)
	    {
	        nrm = 1.0F / ((1.0F + wdqd) + wdwd);
	        b[0] = nrm * ((1.0F + gdwdqd) + wdwd);
	        b[1] = nrm * (2.0F * (wdwd - 1.0F));
	        b[2] = nrm * ((1.0F - gdwdqd) + wdwd);
	        a[1] = nrm * (2.0F * (wdwd - 1.0F));
	        a[2] = nrm * ((1.0F - wdqd) + wdwd);
	    }
	    else if (G < 0.0F)
	    {
	        nrm = 1.0F / ((1.0F + gdwdqd) + wdwd);
	        b[0] = nrm * ((1.0F + wdqd) + wdwd);
	        b[1] = nrm * (2.0F * (wdwd - 1.0F));
	        b[2] = nrm * ((1.0F - wdqd) + wdwd);
	        a[1] = nrm * (2.0F * (wdwd - 1.0F));
	        a[2] = nrm * ((1.0F - gdwdqd) + wdwd);
	    }
	    else
	    {
	        b[0] = 1.0F;
	        b[1] = 0.0F;
	        b[2] = 0.0F;
	        a[1] = 0.0F;
	        a[2] = 0.0F;
	    }

	    a[0] = 1.0F;
	}
	
	protected double calculate(double centerfc,double fc,double G)
	{
		double PI = 3.1415926535897932384626433832795f;
		
		double T,B0,B1,B2,A1,A2,wd,Q;
		double b[] = new double[3];
		double a[] = new double[3];
		
		double Y1,Y2,Y;
		Q = 1.414f;
		wd  = Math.tan(PI * centerfc / 48000.0f);
		iMedia_SWS_IIRLfShelving(wd, G, Q, b, a);
		B0 = b[0];
		B1 = b[1];
		B2 = b[2];
		A1 = a[1];
		A2 = a[2];

		T = 4 * Math.pow(Math.sin(PI*fc/48000.0f),2);
		Y1 = 10 * Math.log10(Math.pow(B0 + B1 + B2,2) + (B0*B2*T - (B1*(B0 + B2) + 4*B0*B2))*T);
		Y2 = 10 * Math.log10(Math.pow(1 + A1 + A2,2) + (1*A2*T - (A1*(1+A2) + 4 * A2))*T);
		Y = Y1 - Y2;
		return Y;
	}
	private List<Cubic> calculate(List<Integer> x) {
		int n = x.size() - 1;
		float[] gamma = new float[n + 1];
		float[] delta = new float[n + 1];
		float[] D = new float[n + 1];
		int i;
		/*
		 * We solve the equation [2 1 ] [D[0]] [3(x[1] - x[0]) ] |1 4 1 | |D[1]|
		 * |3(x[2] - x[0]) | | 1 4 1 | | . | = | . | | ..... | | . | | . | | 1 4
		 * 1| | . | |3(x[n] - x[n-2])| [ 1 2] [D[n]] [3(x[n] - x[n-1])]
		 * 
		 * by using row operations to convert the matrix to upper triangular and
		 * then back sustitution. The D[i] are the derivatives at the knots.
		 */

		gamma[0] = 1.0f / 2.0f;
		for (i = 1; i < n; i++) {
			gamma[i] = 1 / (4 - gamma[i - 1]);
		}
		gamma[n] = 1 / (2 - gamma[n - 1]);

		delta[0] = 3 * (x.get(1) - x.get(0)) * gamma[0];
		for (i = 1; i < n; i++) {
			delta[i] = (3 * (x.get(i + 1) - x.get(i - 1)) - delta[i - 1])
					* gamma[i];
		}
		delta[n] = (3 * (x.get(n) - x.get(n - 1)) - delta[n - 1]) * gamma[n];

		D[n] = delta[n];
		for (i = n - 1; i >= 0; i--) {
			D[i] = delta[i] - gamma[i] * D[i + 1];
		}

		/* now compute the coefficients of the cubics */
		List<Cubic> cubics = new LinkedList<Cubic>();
		for (i = 0; i < n; i++) {
			Cubic c = new Cubic(x.get(i), D[i], 3 * (x.get(i + 1) - x.get(i))
					- 2 * D[i] - D[i + 1], 2 * (x.get(i) - x.get(i + 1)) + D[i]
					+ D[i + 1]);
			cubics.add(c);
		}
		return cubics;
	}
	double[] TDMA(double [] ta,double [] tb,double [] tc, double [] tx)
    {
        int n=tx.length;
        tc[0]=tc[0]/tb[0];
        tx[0]=tx[0]/tb[0];

        for(int i=1;i<n;i++){
            double m=1/(tb[i]-ta[i]*tc[i-1]);
            tc[i]=tc[i]*m;
            tx[i]=(tx[i]-ta[i]*tx[i-1])*m;
        }
        for(int i=n-2;i>0;i--)
        {
            tx[i]=tx[i]-tc[i]*tx[i+1];
        }
        return tx;
    }
	void Cinterp(double[] x, double[] y)
    {
        int n=x.length;
        double[] m=new double[n];

        for(int i=0;i<n-1;i++)
        {
            h[i]=x[i+1]-x[i];
        }
        a[0]=0;
        b[0]=1;
        c[0]=0;
        d[0]=0;
        a[n-1]=0;
        b[n-1]=1;
        c[n-1]=0;
        d[n-1]=0;
        for(int i=1;i<n-1;i++)
        {
            a[i]=h[i-1];
            b[i]=2*(h[i-1]+h[i]);
            c[i]=h[i];
            d[i]=6*((y[i+1]-y[i])/h[i]-(y[i]-y[i-1])/h[i-1]);
        }
        m=TDMA(a,b,c,d);
        for(int i=0;i<n-1;i++)
        {
            a[i]=y[i];
            b[i]=(y[i+1]-y[i])/h[i]-h[i]*m[i]/2-h[i]*(m[i+1]-m[i])/6;
            c[i]=m[i]/2;
            d[i]=(m[i+1]-m[i])/(6*h[i]);
        }
    }
	void Xinterp(double[] xin,double[] hin,double[]xExt)
    {
        int i=0;
        for(;i<(xin.length-1);i++)
        {
            xExt[i*10]=xin[i];
            xExt[i*10+1]=xin[i]+hin[i]/10;
            xExt[i*10+2]=xin[i]+hin[i]/10*2;
            xExt[i*10+3]=xin[i]+hin[i]/10*3;
            xExt[i*10+4]=xin[i]+hin[i]/10*4;
            xExt[i*10+5]=xin[i]+hin[i]/10*5;
            xExt[i*10+6]=xin[i]+hin[i]/10*6;
            xExt[i*10+7]=xin[i]+hin[i]/10*7;
            xExt[i*10+8]=xin[i]+hin[i]/10*8;
            xExt[i*10+9]=xin[i]+hin[i]/10*9;
            
        }    
        xExt[i*10]=xin[i];
    }
	void Yinterp(double[] xex,double[] y,double []yout)
    {
        for(int i=0;i<xex.length;i++)
        {
            int seg=(int)i/10;
            double h=xex[i]-xex[seg*10];
            yout[i]=a[seg]+b[seg]*h+c[seg]*h*h+d[seg]*h*h*h;
        }
        yout[xex.length-1]=y[y.length-1];
    }

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		Paint paint = new Paint();
		Path linePath = new Path();
		paint.setAntiAlias(true);
		paint.setColor(Color.YELLOW);
		paint.setStyle(Paint.Style.STROKE);
		paint.setPathEffect (new DashPathEffect(new float[]{15, 15}, 0 ));
		paint.setStrokeWidth(2);

		int zeroX = 10;
		int zeroY = 460;
		int endX = 1050;
		int endY = 460;
		linePath.moveTo(zeroX,zeroY);
		linePath.lineTo(endX, endY);  
		zeroX = 10;
		zeroY = 235;
		endX = 1050;
		endY = 235;
		linePath.moveTo(zeroX,zeroY);
		linePath.lineTo(endX, endY);
		zeroX = 10;
		zeroY = 10;
		endX = 1050;
		endY = 10;
		linePath.moveTo(zeroX,zeroY);
		linePath.lineTo(endX, endY);
		zeroX = 10;
		zeroY = 10;
		endX = 10;
		endY = 460;
		linePath.moveTo(zeroX,zeroY);
		linePath.lineTo(endX, endY);
		zeroX = 1050;
		zeroY = 10;
		endX = 1050;
		endY = 460;
		linePath.moveTo(zeroX,zeroY);
		linePath.lineTo(endX, endY);
        canvas.drawPath(linePath, paint);  
        /*
        
		Paint paint1 = new Paint();
		Path linePath1 = new Path();
		paint1.setAntiAlias(true);
		paint1.setColor(Color.RED);
		paint1.setStyle(Paint.Style.STROKE);
		paint1.setStrokeWidth(5);
		zeroX = 300;
		zeroY = 10;
		endX = 300;
		endY = 460;
		linePath1.moveTo(zeroX,zeroY);
		linePath1.lineTo(endX, endY);
		zeroX = 800;
		zeroY = 10;
		endX = 800;
		endY = 460;
		linePath1.moveTo(zeroX,zeroY);
		linePath1.lineTo(endX, endY);
		zeroX = 550;
		zeroY = 10;
		endX = 550;
		endY = 460;
		linePath1.moveTo(zeroX,zeroY);
		linePath1.lineTo(endX, endY);
		canvas.drawPath(linePath1, paint1); */
		int weightX = 1000/3;
		int weightY = 450/10;
		/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		/*
		double inX[] = {10,10.2,10.404,10.61208,10.8243216,11.04080803,11.26162419,11.48685668,11.71659381,11.95092569,12.1899442,12.43374308,12.68241795,12.9360663,13.19478763,13.45868338,13.72785705,14.00241419,14.28246248,14.56811173,14.85947396,15.15666344,15.45979671,15.76899264,16.08437249,16.40605994,16.73418114,17.06886477,17.41024206,17.7584469,18.11361584,18.47588816,18.84540592,19.22231404,19.60676032,19.99889553,20.39887344,20.80685091,21.22298792,21.64744768,22.08039664,22.52200457,22.97244466,23.43189355,23.90053142,24.37854205,24.86611289,25.36343515,25.87070385,26.38811793,26.91588029,27.4541979,28.00328185,28.56334749,29.13461444,29.71730673,30.31165286,30.91788592,31.53624364,32.16696851,32.81030788,33.46651404,34.13584432,34.81856121,35.51493243,36.22523108,36.9497357,37.68873042,38.44250503,39.21135513,39.99558223,40.79549387,41.61140375,42.44363183,43.29250446,44.15835455,45.04152164,45.94235208,46.86119912,47.7984231,48.75439156,49.72947939,50.72406898,51.73855036,52.77332137,53.82878779,54.90536355,56.00347082,57.12354024,58.26601104,59.43133126,60.61995789,61.83235705,63.06900419,64.33038427,65.61699196,66.9293318,68.26791843,69.6332768,71.02594234,72.44646118,73.89539041,75.37329821,76.88076418,78.41837946,79.98674705,81.58648199,83.21821163,84.88257586,86.58022738,88.31183193,90.07806857,91.87962994,93.71722254,95.59156699,97.50339833,99.4534663,101.4425356,103.4713863,105.5408141,107.6516303,109.8046629,112.0007562,114.2407713,116.5255868,118.8560985,121.2332205,123.6578849,126.1310426,128.6536634,131.2267367,133.8512714,136.5282969,139.2588628,142.04404,144.8849208,147.7826193,150.7382716,153.7530371,156.8280978,159.9646598,163.163953,166.427232,169.7557767,173.1508922,176.6139101,180.1461883,183.749112,187.4240943,191.1725761,194.9960277,198.8959482,202.8738672,206.9313445,211.0699714,215.2913708,219.5971983,223.9891422,228.4689251,233.0383036,237.6990696,242.453051,247.3021121,252.2481543,257.2931174,262.4389797,267.6877593,273.0415145,278.5023448,284.0723917,289.7538395,295.5489163,301.4598947,307.4890925,313.6388744,319.9116519,326.3098849,332.8360826,339.4928043,346.2826604,353.2083136,360.2724798,367.4779294,374.827488,382.3240378,389.9705185,397.7699289,405.7253275,413.839834,422.1166307,430.5589633,439.1701426,447.9535455,456.9126164,466.0508687,475.3718861,484.8793238,494.5769103,504.4684485,514.5578174,524.8489738,535.3459533,546.0528723,556.9739298,568.1134084,579.4756765,591.0651901,602.8864939,614.9442237,627.2431082,639.7879704,652.5837298,665.6354044,678.9481125,692.5270747,706.3776162,720.5051685,734.9152719,749.6135774,764.6058489,779.8979659,795.4959252,811.4058437,827.6339606,844.1866398,861.0703726,878.29178,895.8576156,913.7747679,932.0502633,950.6912686,969.7050939,989.0991958,1008.88118,1029.058803,1049.639979,1070.632779,1092.045435,1113.886343,1136.16407,1158.887352,1182.065099,1205.706401,1229.820529,1254.416939,1279.505278,1305.095383,1331.197291,1357.821237,1384.977662,1412.677215,1440.930759,1469.749374,1499.144362,1529.127249,1559.709794,1590.90399,1622.72207,1655.176511,1688.280041,1722.045642,1756.486555,1791.616286,1827.448612,1863.997584,1901.277536,1939.303087,1978.089148,2017.650931,2058.00395,2099.164029,2141.147309,2183.970256,2227.649661,2272.202654,2317.646707,2363.999641,2411.279634,2459.505227,2508.695331,2558.869238,2610.046623,2662.247555,2715.492506,2769.802356,2825.198403,2881.702371,2939.336419,2998.123147,3058.08561,3119.247322,3181.632269,3245.264914,3310.170213,3376.373617,3443.901089,3512.779111,3583.034693,3654.695387,3727.789295,3802.345081,3878.391982,3955.959822,4035.079018,4115.780599,4198.096211,4282.058135,4367.699298,4455.053284,4544.154349,4635.037436,4727.738185,4822.292949,4918.738808,5017.113584,5117.455855,5219.804972,5324.201072,5430.685093,5539.298795,5650.084771,5763.086467,5878.348196,5995.91516,6115.833463,6238.150132,6362.913135,6490.171398,6619.974826,6752.374322,6887.421809,7025.170245,7165.67365,7308.987123,7455.166865,7604.270202,7756.355606,7911.482719,8069.712373,8231.10662,8395.728753,8563.643328,8734.916194,8909.614518,9087.806809,9269.562945,9454.954204,9644.053288,9836.934354,10033.67304,10234.3465,10439.03343,10647.8141,10860.77038,11077.98579,11299.54551,11525.53642,11756.04714,11991.16809,12230.99145,12475.61128,12725.1235,12979.62597,13239.21849,13504.00286,13774.08292,14049.56458,14330.55587,14617.16699,14909.51033,15207.70053,15511.85454,15822.09163,16138.53347,16461.30414,16790.53022,17126.34082,17468.86764,17818.24499,18174.60989,18538.10209,18908.86413,19287.04142,19672.78224,
};
		double inY[] = {-0.05,-0.05,-0.05,-0.06,-0.06,-0.06,-0.06,-0.07,-0.07,-0.07,-0.08,-0.08,-0.08,-0.08,-0.09,-0.09,-0.10,-0.10,-0.10,-0.11,-0.11,-0.12,-0.12,-0.13,-0.13,-0.14,-0.14,-0.15,-0.16,-0.16,-0.17,-0.18,-0.19,-0.19,-0.20,-0.21,-0.22,-0.23,-0.24,-0.25,-0.26,-0.27,-0.28,-0.29,-0.31,-0.32,-0.33,-0.35,-0.36,-0.38,-0.40,-0.42,-0.43,-0.45,-0.47,-0.50,-0.52,-0.54,-0.57,-0.59,-0.62,-0.65,-0.68,-0.71,-0.75,-0.78,-0.82,-0.86,-0.90,-0.95,-1.00,-1.05,-1.10,-1.15,-1.21,-1.28,-1.34,-1.41,-1.49,-1.57,-1.65,-1.75,-1.84,-1.95,-2.06,-2.17,-2.30,-2.44,-2.58,-2.74,-2.91,-3.09,-3.28,-3.49,-3.72,-3.96,-4.23,-4.52,-4.83,-5.17,-5.54,-5.95,-6.40,-6.89,-7.43,-8.02,-8.69,-9.42,-10.25,-11.18,-12.22,-13.41,-14.76,-16.26,-17.86,-19.33,-20.18,-19.94,-18.75,-17.21,-15.66,-14.25,-13.00,-11.91,-10.94,-10.09,-9.33,-8.66,-8.06,-7.52,-7.04,-6.60,-6.21,-5.86,-5.55,-5.26,-5.01,-4.78,-4.58,-4.41,-4.25,-4.12,-4.00,-3.91,-3.84,-3.78,-3.75,-3.73,-3.73,-3.76,-3.80,-3.87,-3.96,-4.08,-4.22,-4.40,-4.60,-4.85,-5.13,-5.47,-5.85,-6.29,-6.80,-7.38,-8.04,-8.79,-9.65,-10.61,-11.65,-12.72,-13.70,-14.36,-14.45,-13.90,-12.90,-11.70,-10.48,-9.32,-8.25,-7.28,-6.41,-5.62,-4.91,-4.26,-3.67,-3.13,-2.63,-2.17,-1.74,-1.34,-0.96,-0.60,-0.26,0.07,0.38,0.69,0.98,1.28,1.56,1.85,2.13,2.41,2.70,2.99,3.28,3.57,3.88,4.19,4.51,4.84,5.18,5.53,5.90,6.28,6.67,7.09,7.52,7.98,8.45,8.95,9.47,10.02,10.59,11.19,11.80,12.44,13.08,13.72,14.33,14.90,15.40,15.78,16.01,16.09,16.00,15.76,15.41,14.98,14.50,13.99,13.48,12.98,12.50,12.04,11.61,11.20,10.82,10.47,10.14,9.84,9.56,9.30,9.07,8.85,8.66,8.48,8.32,8.18,8.06,7.95,7.85,7.77,7.70,7.65,7.61,7.58,7.56,7.55,7.56,7.57,7.60,7.63,7.68,7.73,7.80,7.87,7.95,8.04,8.15,8.26,8.37,8.50,8.64,8.78,8.94,9.10,9.27,9.45,9.63,9.83,10.03,10.25,10.47,10.70,10.93,11.18,11.43,11.69,11.95,12.22,12.49,12.76,13.04,13.31,13.58,13.84,14.09,14.32,14.53,14.72,14.87,14.99,15.07,15.09,15.08,15.01,14.89,14.72,14.51,14.26,13.98,13.66,13.32,12.96,12.58,12.19,11.79,11.37,10.95,10.52,10.08,9.64,9.18,8.71,8.22,7.72,7.19,6.62,6.01,5.34,4.60,3.76,2.77,1.61,0.18,-1.63,-4.02,-7.36,-12.22,-15.46,-10.84,-6.89,-4.30,-2.53,-1.28,-0.39,0.25,0.71,1.03,1.26,1.40,1.49,1.53,1.54,1.53,1.49,1.44,1.37,1.30,1.22,1.14,1.05,0.96,0.87,0.79,0.70,0.62,0.54,0.46,0.39,0.32,0.25,0.20,0.15,
};
		for (int i = 0; i < 384;i++)
		{
			inY[i] = calculate(31.0f,inX[i],gain[0]) + calculate(62.0f,inX[i],gain[1]) + calculate(125.0f,inX[i],gain[2]) + calculate(250.0f,inX[i],gain[3]) + calculate(500.0f,inX[i],gain[4]) 
				   + calculate(1000.0f,inX[i],gain[5]) + calculate(2000.0f,inX[i],gain[6]) + calculate(4000.0f,inX[i],gain[7]) + calculate(8000.0f,inX[i],gain[8] + calculate(16000.0f,inX[i],gain[9]));
			
		}
		double inlogX[] = new double[384];
		float incoordX[] = new float[384];
		float incoordY[] = new float[384];
		for (int i = 0; i < 384;i++)
		{
			inlogX[i] = Math.log10(inX[i]);
		}
		for (int i = 0; i < 384;i++)
		{
			incoordX[i] = (float)((inlogX[i] - 1.0) * weightX);
			incoordY[i] = (float)(235 - (inY[i] * weightY)/5);
		}
		
		Paint paint2 = new Paint();
		Path linePath2 = new Path();
		paint2.setAntiAlias(true);
		paint2.setColor(Color.RED);
		paint2.setStyle(Paint.Style.STROKE);
		paint2.setStrokeWidth(7);
		
		linePath2.moveTo(incoordX[0],incoordY[0]);
		for(int i = 1; i < 384;i++)
		{
		    linePath2.lineTo(incoordX[i], incoordY[i]);
		}
		canvas.drawPath(linePath2, paint2); */
		
/////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		double inlogX[] = new double[10];
		double inX[] = {31.0f,62.0f,125.0f,250.0f,500.0f,1000.0f,2000.0f,4000.0f,8000.0f,16000.0f};
		int incoordX[] = new int[10];
		int incoordY[] = new int[10];
		for (int i = 0; i < 10;i++)
		{
			inlogX[i] = Math.log10(inX[i]);
		}
		points.clear();
		for (int i = 0; i < 10;i++)
		{
			incoordX[i] = (int)((inlogX[i] - 1.0) * weightX)-100;
			incoordY[i] = (int)(235 - (gain[i] * weightY)/5);
			points.add(new Point(incoordX[i],incoordY[i]));
		}
		Paint pain = new Paint();
		Path curvePath = new Path();
		pain.setColor(Color.RED);
		pain.setAntiAlias(true);

		pain.setStyle(Paint.Style.STROKE);
		pain.setStrokeWidth(7);
		points_x.clear();
		points_y.clear();
		for (int i = 0; i < points.size(); i++) {
			points_x.add(points.get(i).x);
			points_y.add(points.get(i).y);
		}

		List<Cubic> calculate_x = calculate(points_x);
		List<Cubic> calculate_y = calculate(points_y);
		curvePath.moveTo(calculate_x.get(0).eval(0), calculate_y.get(0).eval(0));

		for (int i = 0; i < calculate_x.size(); i++) {
			for (int j = 1; j <= 12; j++) {
				float u = j / (float) 12;
				curvePath.lineTo(calculate_x.get(i).eval(u), calculate_y.get(i).eval(u));
			}
		}
		canvas.drawPath(curvePath, pain);
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		//三次样条插值
		/*
		double inlogX[] = new double[10];
		double inX[] = {31.0f,62.0f,125.0f,250.0f,500.0f,1000.0f,2000.0f,4000.0f,8000.0f,16000.0f};
		double incoordX[] = new double[10];
		double incoordY[] = new double[10];
		double []xExt = new double[91];
		double []yExt = new double[91];
		for (int i = 0; i < 10;i++)
		{
			inlogX[i] = Math.log10(inX[i]);
		}
		for (int i = 0; i < 10;i++)
		{
			incoordX[i] = ((inlogX[i] - 1.0) * weightX) + 50.0f;
			incoordY[i] = (235 - (gain[i] * weightY)/5);
		}
        Cinterp(incoordX,incoordY);
        Xinterp(incoordX,h,xExt);
        Yinterp(xExt,incoordY,yExt);
		Paint paint2 = new Paint();
		Path linePath2 = new Path();
		paint2.setAntiAlias(true);
		paint2.setColor(Color.BLACK);
		paint2.setStyle(Paint.Style.STROKE);
		paint2.setStrokeWidth(7);
		
		linePath2.moveTo((float)xExt[0],(float)yExt[0]);
		for(int i = 1; i < 91;i++)
		{
		    linePath2.lineTo((float)xExt[i], (float)yExt[i]);
		}
		canvas.drawPath(linePath2, paint2);*/
	}

}