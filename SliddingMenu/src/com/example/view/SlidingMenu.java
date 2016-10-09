package com.example.view;

import com.example.indoordemo.R;
import com.example.utils.ScreenUtils;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.method.MovementMethod;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

public class SlidingMenu extends HorizontalScrollView {
	
	private int mScreenWidth;
	
	private int mMenuRightPadding;
	
	private int mMenuWidth;
	private int halfMenuWidth;
	private boolean once;
	private boolean isOpen;
	

	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs,0);
		// TODO Auto-generated constructor stub
		
		
		
		
	}
	
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		// TODO Auto-generated constructor stub
		mScreenWidth = ScreenUtils.getScreenWidth(context);
		TypedArray a= context.getTheme().obtainStyledAttributes(attrs, R.styleable.sliddingMenu, defStyle, 0);
		int n = a.getIndexCount();
		for(int i=0;i<n;i++){
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.sliddingMenu_rightPadding:
				mMenuRightPadding = a.getDimensionPixelSize(attr, 
						(int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50f, getResources().getDisplayMetrics()));
				break;

			default:
				break;
			}
		}
		a.recycle();
	}

	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		// TODO Auto-generated method stub
		if(!once){
		LinearLayout wrapper = (LinearLayout) getChildAt(0);
		ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);
		ViewGroup content = (ViewGroup) wrapper.getChildAt(1);
		
//		mMenuRightPadding =(int) TypedValue.applyDimension(
//						TypedValue.COMPLEX_UNIT_DIP, 50f,
//						getResources().getDisplayMetrics());
		mMenuWidth = mScreenWidth - mMenuRightPadding;
		halfMenuWidth = mMenuWidth/2;
		menu.getLayoutParams().width = mMenuWidth;
		content.getLayoutParams().width = mScreenWidth;
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if(changed){
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:
			int scrollx = getScrollX();
			if(scrollx > halfMenuWidth){
				this.smoothScrollTo(mMenuWidth, 0);
				isOpen = false;
			}else{
				this.smoothScrollTo(0, 0);
				isOpen = true;
			}
			 return true;
			
		}
		
		return super.onTouchEvent(ev);
	}
	public void openMenu(){
		if(isOpen) return;
		this.smoothScrollTo(0, 0);
		isOpen = true;
	}
	public void closeMenu(){
		if(!isOpen) return;
		this.smoothScrollTo(mMenuWidth, 0);
		isOpen = false;
	}
	public void toggle(){
		if(isOpen){
			closeMenu();
		}else{
			openMenu();
		}
	}

}
