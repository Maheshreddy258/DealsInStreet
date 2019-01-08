package com.deals.sine90.dealsinstreet.Adapters;

import android.content.Context;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;


import com.deals.sine90.dealsinstreet.BannerHelper.AutoScrollViewPager;
import com.deals.sine90.dealsinstreet.R;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.CirclePageIndicator;

import java.util.ArrayList;

/**
 * Created by sine90 on 12/5/2016.
 */
public class SlidingImage_Adapter extends PagerAdapter {
    private static int currentPage = 0;
    private static int NUM_PAGES = 0;
    /*   private static final int MAX_WIDTH = 1024;
       private static final int MAX_HEIGHT = 768;
   */
    private ArrayList<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;
    AutoScrollViewPager mPager;
    View v;
    Button innerViewPagerDemo;

    public SlidingImage_Adapter(Context context, ArrayList<String> IMAGES, AutoScrollViewPager mPager, View v, Button innerViewPagerDemo) {
        this.context = context;
        this.IMAGES=IMAGES;
        this.mPager=mPager;
        this.v=v;
        this.innerViewPagerDemo=innerViewPagerDemo;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, final int position) {
        View imageLayout = inflater.inflate(R.layout.slidingimages_layout, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.image);

        imageView.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                //this will log the page number that was click

            }
        });



        Picasso.with(context).load(IMAGES.get(position)).fit().into(imageView);

        view.addView(imageLayout, 0);
        // mPager.setAdapter();
       /*
*/
        CirclePageIndicator indicator = (CirclePageIndicator)
                v.findViewById(R.id.indicator);

        indicator.setViewPager(mPager);

        final float density = context.getResources().getDisplayMetrics().density;

//Set circle indicator radius
        // indicator.setRadius(5 * density);

        NUM_PAGES =IMAGES.size();






        // Pager listener over indicator
        indicator.setOnPageChangeListener(new AutoScrollViewPager.OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                currentPage = position;
                //  Log.i("TAG", "This page was clicked: "+position);
            }

            @Override
            public void onPageScrolled(int pos, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int pos) {

            }
        });



        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
