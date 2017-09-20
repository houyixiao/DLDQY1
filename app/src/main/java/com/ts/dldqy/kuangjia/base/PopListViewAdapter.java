package com.ts.dldqy.kuangjia.base;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ts.dldqy.kuangjia.R;


/**
 * 基类中popWindow适配器
 * Created by fjj on 2017/7/6.
 */

public class PopListViewAdapter extends BaseAdapter {
    private String[] names={};
    private int[] imgs={};
    private final Context context;

    public PopListViewAdapter(Context context, String[] names, int[] imgs) {
        this.context=context;
        this.names = names;
        this.imgs = imgs;
    }

    @Override
    public int getCount() {
        return names.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder vh;
        if(convertView==null){
            convertView=View.inflate(context, R.layout.item_base_popwindow,null);
            vh=new ViewHolder();
            vh.iv= (ImageView) convertView.findViewById(R.id.iv);
            vh.tv= (TextView) convertView.findViewById(R.id.tv);
            convertView.setTag(vh);
        }else {
            vh= (ViewHolder) convertView.getTag();
        }
        vh.tv.setText(names[position]);
        vh.iv.setImageResource(imgs[position]);

        return convertView;
    }
    class ViewHolder{
        TextView tv;
        ImageView iv;
    }
}
