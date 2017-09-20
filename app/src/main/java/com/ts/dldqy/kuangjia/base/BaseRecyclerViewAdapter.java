package com.ts.dldqy.kuangjia.base;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * 封装RecyclerViewAdapter，建议ViewHolder使用BaseViewHolder
 * 包含点击、长按事件
 * 如果ViewHolder使用BaseViewHolder，不需要每次写ViewHolder
 * @author wh
 */

public abstract class BaseRecyclerViewAdapter<T> extends RecyclerView.Adapter<BaseViewHolder> {
    
    private Context context;
    private LayoutInflater inflater;
    private List<T> list;
    private int layoutId;
    private View view;
    protected OnItemClickListener onItemClickListener;//单击事件
    protected OnItemLongClickListener onItemLongClickListener;//长按事件

    /**
     * 继承此适配器需要传入三个参数
     * @param context
     * @param list
     * @param layoutId RecyclerView item布局ID
     */
    public BaseRecyclerViewAdapter(Context context, List<T> list, int layoutId) {
        this.context = context;
        this.list = list;
        this.layoutId = layoutId;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view=inflater.inflate(layoutId,parent,false);
        BaseViewHolder viewHolder=new BaseViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        bindData(holder,list.get(position),position);
    }

    @Override
    public int getItemCount() {
        return list==null?0:list.size();
    }

    /**
     * 继承此适配器复写此方法即可
     * demo：TextView tv=viewHolder.getView(R.id.tv);
     * tv.setText(list.getName());
     * @param viewHolder
     * @param list 此list并非List集合，而是已经get(position)的Bean
     * @param position
     */
    protected abstract void bindData(BaseViewHolder viewHolder, T list, int position);

    /**
     * RecyclerView单击事件
     * @param onItemClickListener
     */
    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener=onItemClickListener;
    }

    /**
     * RecyclerView长按事件
     * @param onItemLongClickListener
     */
    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener){
        this.onItemLongClickListener=onItemLongClickListener;
    }
    
    
    public interface OnItemClickListener {
        void onItemClickListner(View v, int position);
    }
    
    public interface OnItemLongClickListener{
        void onItemLongClickListner(View v, int position);
    }
    

}
