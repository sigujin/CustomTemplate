package com.vito.base.ui.viewholder;

import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by afei on 2016/8/16.
 */
public class VitoSpaceItemDecoration extends RecyclerView.ItemDecoration {
    private int mColorRid;
    private int mSpacePx;

    public VitoSpaceItemDecoration(int colorRid, int spacepx) {
        mColorRid = colorRid;
        mSpacePx = spacepx;
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        //先初始化一个Paint来简单指定一下Canvas的颜色
        Paint paint = new Paint();
        paint.setColor(parent.getContext().getResources().getColor(mColorRid));
        paint.setStrokeWidth(mSpacePx);
        //获得RecyclerView中总条目数量
        int childCount = parent.getChildCount();

        //遍历一下
        for (int i = 0; i < childCount; i++) {
//            if (i == 0) {
//                //如果是第一个条目，那么我们就不画边框了
//                continue;
//            }
            //获得子View，也就是一个条目的View，准备给他画上边框
            View childView = parent.getChildAt(i);

            //先获得子View的长宽，以及在屏幕上的位置，方便我们得到边框的具体坐标
            float x = childView.getX();
            float y = childView.getY();
            int width = childView.getWidth();
            int height = childView.getHeight();

            //根据这些点画条目的四周的线
            c.drawLine(x, y, x + width, y, paint);
            c.drawLine(x, y, x, y + height, paint);
            c.drawLine(x + width, y, x + width, y + height, paint);
            c.drawLine(x, y + height, x + width, y + height, paint);


        }
        super.onDraw(c, parent, state);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        //画横线，就是往下偏移一个分割线的高度
        outRect.set(0, 0, 0, mSpacePx);

//        outRect.set(0, 0, mDivider.getIntrinsicWidth(), 0);
    }
}
