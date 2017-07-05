package com.example.banner.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by src on 2017/7/5.
 */

public class ViewPageAdapter  extends PagerAdapter{

    //数据
    ArrayList<ImageView> mDatas;
    Context mContext;

    public ViewPageAdapter(Context context, ArrayList<ImageView> imageViews) {
        mDatas = imageViews;
        mContext = context;
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {

        final int i = position % mDatas.size();//为了防止角标越界
//            //届时 就不用再初始化图片了，直接把imageViews替换成服务器获取的图片数组thumb就行了例如下面
//            ImageView imageView = new ImageView(MainActivity.this);
//            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
//            Glide.with(mContext).load(thumb.get(i)).into(imageView);
//            imageView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    Toast.makeText(getBaseContext(), position % thumb.size() + "", Toast.LENGTH_LONG).show();
//                }
//            });
//            container.addView(imageView);
//            return imageView;
        container.addView(mDatas.get(i));
        mDatas.get(i).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext,i+"",Toast.LENGTH_LONG).show();
            }
        });

        return mDatas.get(i);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return Integer.MAX_VALUE;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }
}
