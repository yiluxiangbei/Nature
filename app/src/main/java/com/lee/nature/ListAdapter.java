package com.lee.nature;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import android.content.Context;
import android.graphics.Color;

import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	private List<Map<String, Object>> data;
	private LayoutInflater layoutInflater;
	private Context context;
	public ListAdapter(Context context,List<Map<String, Object>> data){
		this.context=context;
		this.data=data;
		this.layoutInflater=LayoutInflater.from(context);
	}
	/**
	 * 组件集合，对应list.xml中的控件
	 * @author Administrator
	 */
	public final class Zujian{
		public ImageView image;
		public TextView title;
		public TextView headphone;
		public CheckBox btnswitch;
		public int visi;
		public boolean checked;
		public String Headset;
	}
	@Override
	public int getCount() {
		return data.size();
	}
	/**
	 * 获得某一位置的数据
	 */
	@Override
	public Object getItem(int position) {
		return data.get(position);
	}
	/**
	 * 获得唯一标识
	 */
	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Zujian zujian=null;
		if(convertView==null){
			zujian=new Zujian();
			//获得组件，实例化组件
			convertView=layoutInflater.inflate(R.layout.list, null);
			convertView.setBackgroundResource(R.drawable.listitem);
			zujian.image=(ImageView)convertView.findViewById(R.id.image);
			zujian.title=(TextView)convertView.findViewById(R.id.title);
			zujian.headphone=(TextView)convertView.findViewById(R.id.headphone);
			zujian.btnswitch=(CheckBox)convertView.findViewById(R.id.btnswitch);
			convertView.setTag(zujian);
		}else{
			zujian=(Zujian)convertView.getTag();
		}
		//绑定数据
		zujian.image.setBackgroundResource((Integer)data.get(position).get("image"));
		zujian.title.setText((String)data.get(position).get("title"));
		zujian.headphone.setText((String)data.get(position).get("headphone"));
		zujian.btnswitch.setVisibility((Integer)data.get(position).get("visi"));
		zujian.btnswitch.setChecked(new Boolean((String)data.get(position).get("checked")));
		zujian.Headset = (String)data.get(position).get("Headset");
		if (zujian.Headset == "on" && (String)data.get(position).get("checked") == "true")
		{
			convertView.setBackgroundColor(Color.BLACK);
		}
		else
		{
			convertView.setBackgroundColor(Color.GRAY);
		}
		return convertView;
	}

}
