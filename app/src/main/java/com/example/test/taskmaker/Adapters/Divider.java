package com.example.test.taskmaker.Adapters;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.example.test.taskmaker.R;

public class Divider extends RecyclerView.ItemDecoration {
    private Drawable divider;
    private int orientation;
    private int layoutId;

    public Divider(Context context, int orientation,int layoutId) {
        divider=context.getDrawable(R.drawable.divider);
        this.orientation=orientation;
        this.layoutId=layoutId;
    }

    @Override
    public void onDraw(@NonNull Canvas c, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        if(orientation== LinearLayoutManager.VERTICAL){
            drawHorizontalDivider(c,parent,state);
            if(layoutId==1){
                drawVerticalDivider(c,parent,state);
            }
        }
    }

    private void drawHorizontalDivider(Canvas c, RecyclerView parent, RecyclerView.State state) {
        int left,top,right,bottom;
        left=parent.getPaddingLeft();
        right=parent.getWidth()-parent.getPaddingRight();
        int count=parent.getChildCount();
        for(int i=0;i<count;i++) {
            View currentChild = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) currentChild.getLayoutParams();
            top = currentChild.getBottom() + params.bottomMargin;
            bottom = top + divider.getIntrinsicHeight();
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
    }

    private void drawVerticalDivider(Canvas c, RecyclerView parent, RecyclerView.State state){
        int left,top,right,bottom;
        left=parent.getWidth();
        right=left+divider.getIntrinsicWidth();
        int count=parent.getChildCount();
        for(int i=0;i<count;i++) {
            View currentChild = parent.getChildAt(i);
            RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) currentChild.getLayoutParams();
            top = currentChild.getTop() + params.topMargin;
            bottom = top + currentChild.getHeight()+params.bottomMargin;
            divider.setBounds(left, top, right, bottom);
            divider.draw(c);
        }
        /*top=parent.getTop();
        bottom=parent.getBottom();
        divider.setBounds(left, top, right, bottom);
        divider.draw(c);*/
    }

    @Override
    public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

    }
}
