package com.example.chaochen.myscrollevent;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;
import android.widget.ScrollView;

/**
 * @创建者 chenchao.
 * @创建时间 2018/7/2 9:55
 * @描述 ${TODO}
 */

public class MyLineralayout extends LinearLayout {
    private int mMove;
    private int yDown, yMove;
    private int i = 0;
    private boolean isIntercept = false;

    public MyLineralayout(Context context) {
        super(context);
    }

    public MyLineralayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLineralayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    private ScrollView scrollView;

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        scrollView = (ScrollView) getChildAt(0);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        onInterceptTouchEvent(ev);
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        int y = (int) ev.getY();
        int mScrollY = scrollView.getScrollY();

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                yDown = y;
                break;
            case MotionEvent.ACTION_MOVE:
                yMove = y;
                if (yMove - yDown > 0 && mScrollY == 0) {
                    if (!isIntercept) {
                        yDown = (int) ev.getY();
                        isIntercept = true;
                    }
                } else {
                    isIntercept = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                layout(getLeft(), getTop() - i, getRight(), getBottom() - i);
                i = 0;
                isIntercept = false;
                break;
            default:
                break;
        }
        if (isIntercept) {
            mMove = yMove - yDown;
            i += mMove;
            layout(getLeft(), getTop() + mMove, getRight(), getBottom() + mMove);
        }
        return isIntercept;
    }
}
