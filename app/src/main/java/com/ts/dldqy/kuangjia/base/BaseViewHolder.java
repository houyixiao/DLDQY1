package com.ts.dldqy.kuangjia.base;

import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.View;

/**
 * 万能ViewHolder
 * RecyclerView适配器不需要再写ViewHolder
 * @author wh
 */

public class BaseViewHolder extends RecyclerView.ViewHolder {
    
    private SparseArray<View> views;
    
    public BaseViewHolder(View itemView) {
        super(itemView);
        this.views=new SparseArray<>();
    }

    /**
     * 
     * @param viewId
     * @param <T>
     * @return
     */
    public <T extends View> T getView(int viewId) {
        View view = views.get(viewId);
        if (view == null) {
            view = itemView.findViewById(viewId);
            views.put(viewId, view);
        }
        return (T) view;
    }

    public View getRootView() {
        return itemView;
    }
}
