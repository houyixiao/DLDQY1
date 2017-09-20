package com.ts.dldqy.kuangjia.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.widget.EditText;

import com.ts.dldqy.kuangjia.R;


/**
 * 带下划线的输入框
 * Created by llx on 2016/6/28.
 */

public class LineEditText extends EditText {
    private Paint mPaint;

    /**
     * @param context
     * @param attrs
     */
    public LineEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mPaint = new Paint();
        mPaint.setStrokeWidth((float) 2);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(this.getResources().getColor(R.color.colorAPP));
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //画底线
        canvas.drawLine(0, this.getHeight()-1, this.getWidth(), this.getHeight()-1, mPaint);
    }

    
}
