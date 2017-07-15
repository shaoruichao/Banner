package com.example.banner;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.banner.adapter.ViewPageAdapter;
import com.example.banner.transformer.AlphaPageTransformer;
import com.example.banner.transformer.NonPageTransformer;
import com.example.banner.transformer.RotateDownPageTransformer;
import com.example.banner.transformer.RotateUpPageTransformer;
import com.example.banner.transformer.RotateYTransformer;
import com.example.banner.transformer.ScaleInTransformer;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener{

    @BindView(R.id.id_viewpager)
    ViewPager idViewpager;
    //现在是死数据，所以我用int型数组
    int[] imgRes = {R.drawable.a, R.drawable.b, R.drawable.c, R.drawable.d,
            R.drawable.e, R.drawable.f, R.drawable.g, R.drawable.h, R.drawable.i};
    //一般在项目中图片数组会从后台得到，例如
    private List<String> thumb;
    private ArrayList<ImageView> imageViews;
//    private viewPageAdapter mAdapter;
    private ViewPageAdapter mAdapter;
    private Handler handler;

    LinearLayout mLayout;
    private ArrayList<View> mList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        //设置页与页之间的间距
        idViewpager.setPageMargin(40);
        //设置item的缓存数目
        idViewpager.setOffscreenPageLimit(3);
        initImageViews();

        mAdapter = new ViewPageAdapter(this,imageViews);
        idViewpager.setAdapter(mAdapter);

        idViewpager.setCurrentItem(1000*imageViews.size());
        idViewpager.setPageTransformer(true, NonPageTransformer.INSTANCE);
//        idViewpager.setPageTransformer(true, new AlphaPageTransformer());
//        idViewpager.setPageTransformer(true, new RotateDownPageTransformer());
//        idViewpager.setPageTransformer(true, new RotateUpPageTransformer());
//        idViewpager.setPageTransformer(true, new RotateYTransformer(45));
//        idViewpager.setPageTransformer(true, new ScaleInTransformer());
//        idViewpager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer()));
//        idViewpager.setPageTransformer(true, new RotateDownPageTransformer(new AlphaPageTransformer(new ScaleInTransformer())));

        mLayout= (LinearLayout) findViewById(R.id.ll_indicator);
        for (int i = 0; i < imageViews.size(); i++) {
            View view=new View(this);
            LinearLayout.LayoutParams layoutParams=new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.MATCH_PARENT,1.0f);
            view.setLayoutParams(layoutParams);
            if(i==0){
//                view.setBackgroundColor(getResources().getColor(R.color.titleblue));
//                view.setBackgroundColor(Color.parseColor("#ff00"));
                view.setBackgroundColor(Color.RED);
            }
            mList.add(view);
            mLayout.addView(view);
        }

        idViewpager.setOnPageChangeListener(this);

        //自动轮播
        handler = new Handler();
        handler.postDelayed(new TimerRunnable(),5000);

    }


    /**
     * 初始化资源图片
     */
    private void initImageViews() {
        imageViews = new ArrayList<>();
        for (int i=0;i<imgRes.length;i++){
            ImageView imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setImageResource(imgRes[i]);

            imageViews.add(imageView);
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        for (int i = 0; i < mList.size(); i++) {
            if (i == position% mList.size()) {
                mList.get(i).setBackgroundColor(Color.RED);
            } else {
                mList.get(i).setBackgroundColor(Color.GRAY);
            }
        }
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }


//    private class viewPageAdapter extends PagerAdapter{
//
//        @Override
//        public Object instantiateItem(ViewGroup container, final int position) {
//
//            final int i = position % imageViews.size();//为了防止角标越界
////            //届时 就不用再初始化图片了，直接把imageViews替换成服务器获取的图片数组thumb就行了例如下面
////            ImageView imageView = new ImageView(MainActivity.this);
////            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
////            Glide.with(mContext).load(thumb.get(i)).into(imageView);
////            imageView.setOnClickListener(new View.OnClickListener() {
////                @Override
////                public void onClick(View v) {
////                    Toast.makeText(getBaseContext(), position % thumb.size() + "", Toast.LENGTH_LONG).show();
////                }
////            });
////            container.addView(imageView);
////            return imageView;
//            container.addView(imageViews.get(i));
//            imageViews.get(i).setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getBaseContext(),i+"",Toast.LENGTH_LONG).show();
//                }
//            });
//
//            return imageViews.get(i);
//        }
//
//        @Override
//        public void destroyItem(ViewGroup container, int position, Object object) {
//            container.removeView((View) object);
//        }
//
//        @Override
//        public int getCount() {
//            //设置为展示好多条目
//            return Integer.MAX_VALUE;
//        }
//
//        @Override
//        public boolean isViewFromObject(View view, Object object) {
//            return view == object;
//        }
//    }

    private class TimerRunnable implements Runnable {
        @Override
        public void run() {
            int curItem = idViewpager.getCurrentItem();
            idViewpager.setCurrentItem(curItem+1);
            if (handler!=null){
                handler.postDelayed(this,5000);
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler = null;//Activity退出时及时 回收
    }
}
