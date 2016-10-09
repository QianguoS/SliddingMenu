# SliddingMenu
继承HorizontalScrollView实现qq的侧滑菜单
一、qq侧滑菜单原理讲解
  我们在使用qq的过程中可以看到用手指左右滑动出现两个界面，其实这两个界面位于同一个布局文件中，只是拉到那一部分就显示那一部分。
  在侧滑的时候很容易遇到滑动冲突问题，滑动冲突问题就是当menu菜单中和content菜单中存在listview中，怎样判断滑动的是listview还是
  侧滑菜单，在Android中使用HorizontalScrollView可以解决滑动冲突问题。
  接下来我们剖析一下详细的代码
二、布局文件实现
  <com.example.view.SlidingMenu
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:sunshine="http://schemas.android.com/apk/res/com.example.indoordemo"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="wrap_content"
    android:layout_height="fill_parent"
    android:id="@+id/sildingMenu"
    android:scrollbars="none" 
    sunshine:rightPadding ="100dp">

    <LinearLayout
        android:layout_width="wrap_content"  
        android:layout_height="fill_parent"  
        android:orientation="horizontal">

        <include layout="@layout/layout_menu" />

        <LinearLayout  
            android:layout_width="fill_parent"  
            android:layout_height="fill_parent"  
            android:background="@drawable/qq" >  
            <Button 
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:onClick="toggleMenu"
                android:text="change"/>
        </LinearLayout>  
    </LinearLayout>

</com.example.view.SlidingMenu>
我们分析一下上面的布局文件，最外面是我们继承HorizontalScrollView书写的布局方式，下面会详细讲解。因为在HorizontalScrollView中只能包含一个控件，
所以我们使用LinearLayout。<include layout="@layout/layout_menu" />是将layout_menu包含进来，layout_menu是侧滑菜单左侧菜单区域。
			<LinearLayout  
            android:layout_width="fill_parent"  
            android:layout_height="fill_parent"  
            android:background="@drawable/qq" >  
            <Button 
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:onClick="toggleMenu"
                android:text="change"/>
        </LinearLayout>  
				 上面的代码是实现用一张图片代替content部分。
				 接下来我们看一下layout_menu内容部分
				 <RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context="com.example.indoordemo.MainActivity"
    android:background="@drawable/img_frame_background" >

    <LinearLayout 
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:orientation="vertical"
        android:layout_centerVertical="true">
        
        <RelativeLayout 
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            
            <ImageView 
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:src="@drawable/img_1"
                android:layout_centerVertical="true"
                android:id="@+id/img_1"
                android:layout_marginLeft="20dp"
                android:layout_marginTop="20dp"/>
            <TextView 
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="20dp"
                android:layout_toRightOf="@id/img_1"
                android:layout_marginLeft="20dp"
                android:text="ITEM1"
                android:textColor="#ffffff"
                android:layout_centerVertical="true"/>
            
        </RelativeLayout>
        <RelativeLayout 
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            
            <ImageView 
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:src="@drawable/img_2"
                android:layout_centerVertical="true"
                android:id="@+id/img_2"
                android:layout_marginLeft="20dp"
                android:layout_marginTop="20dp"/>
            <TextView 
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="20dp"
                android:layout_toRightOf="@id/img_2"
                android:layout_marginLeft="20dp"
                android:text="ITEM1"
                android:textColor="#ffffff"
                android:layout_centerVertical="true"/>
            
        </RelativeLayout>
        <RelativeLayout 
            android:layout_width="match_parent"
            android:layout_height="wrap_content">
            
            <ImageView 
                android:layout_width="50dp"
                android:layout_height="50dp"
                android:src="@drawable/img_3"
                android:layout_centerVertical="true"
                android:id="@+id/img_3"
                android:layout_marginLeft="20dp"
                android:layout_marginTop="20dp"/>
            <TextView 
                android:layout_width="wrap_content"
                android:layout_height="wrap_content"
                android:textSize="20dp"
                android:layout_toRightOf="@id/img_3"
                android:layout_marginLeft="20dp"
                android:text="ITEM1"
                android:textColor="#ffffff"
                android:layout_centerVertical="true"/>
            
        </RelativeLayout>
        
    </LinearLayout>

</RelativeLayout>
这部分比较简单，就是实现每一个条目，请读者自己结合项目学习，请读者先download远吗，结合APP分析远吗，这个是最快的学习方式

三、 主要代码的实现
public class SlidingMenu extends HorizontalScrollView我们先让SlidingMenu继承HorizontalScrollView
SlidingMenu需要实现三个构造方法，

public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs,0);       这里我们让两个的构造方法继承三个的构造方法
		// TODO Auto-generated constructor stub	
	}
	
	public SlidingMenu(Context context, AttributeSet attrs, int defStyle) {
	......
	......
	......
	......
	......
}
接下来我们要重写onMeasure(int widthMeasureSpec, int heightMeasureSpec)和onLayout(boolean changed, int l, int t, int r, int b)
onMeasure是计算子控件以及自身的尺寸，onLayout是确定空间的额拜访位置。我们看一下详细实现


LinearLayout wrapper = (LinearLayout) getChildAt(0); 获取根布局文件
		ViewGroup menu = (ViewGroup) wrapper.getChildAt(0);在布局文件中放置一个viewgroup作为menu容器
		
		ViewGroup content = (ViewGroup) wrapper.getChildAt(1);获取viewgroup作为content的容器
		
		mMenuRightPadding =(int) TypedValue.applyDimension(
						TypedValue.COMPLEX_UNIT_DIP, 50f,
						getResources().getDisplayMetrics());         将int型数据转化为dp，这个数据作为菜单距离右边屏幕的边距
		mMenuWidth = mScreenWidth - mMenuRightPadding;       计算菜单的宽度
		halfMenuWidth = mMenuWidth/2;
		menu.getLayoutParams().width = mMenuWidth;					  设置菜单的宽度	
		content.getLayoutParams().width = mScreenWidth;       设置内容的宽度
		}
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);

接下来我们看一下具体的摆放位置
@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		// TODO Auto-generated method stub
		super.onLayout(changed, l, t, r, b);
		if(changed){
			this.scrollTo(mMenuWidth, 0);
			once = true;
		}
	  设置刚开始的时候隐藏菜单scrollTo(mMenuWidth, 0);
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
				isOpen = true; 菜单关闭、开启的标志
			}
			 return true;
			
		}
		
		return super.onTouchEvent(ev);
		   设置手指抬起的时候，如果大于显示的菜单大于菜单的一半，将其显示出来，否则隐藏。
			 接下来设置打开关闭菜单的几个方法
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


