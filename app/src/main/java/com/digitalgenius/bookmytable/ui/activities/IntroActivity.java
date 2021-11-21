package com.digitalgenius.bookmytable.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.digitalgenius.bookmytable.R;
import com.digitalgenius.bookmytable.databinding.ActivityIntroBinding;

public class IntroActivity extends AppCompatActivity {
    ActivityIntroBinding binding;
    private final int[] layouts={R.layout.slide_1,R.layout.slide_2,R.layout.slide_3};
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityIntroBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        addBottomDotsAndText(0);
        setListener();

        MyViewPagerAdapter myViewPagerAdapter=new MyViewPagerAdapter(layouts);
        binding.pager.setAdapter(myViewPagerAdapter);

        binding.pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                addBottomDotsAndText(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void setListener() {
        binding.getstarted.setOnClickListener(v -> {
            startActivity(new Intent(this,LoginActivity.class));
            overridePendingTransition(R.anim.slide_from_bottom,R.anim.slide_from_bottom);
        });
    }

    private void addBottomDotsAndText(int i) {
        switch (i) {
            case 0 : {
                binding.headline.setText(getResources().getString(R.string.slide_1_title));
                binding.dot0.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.dot1.setTextColor(getResources().getColor(R.color.dot_inactive));
                binding.dot2.setTextColor(getResources().getColor(R.color.dot_inactive));
                break;
            }
            case 1 : {
                binding.headline.setText(getResources().getString(R.string.slide_2_title));
                binding.dot0.setTextColor(getResources().getColor(R.color.dot_inactive));
                binding.dot1.setTextColor(getResources().getColor(R.color.colorPrimary));
                binding.dot2.setTextColor(getResources().getColor(R.color.dot_inactive));
                break;
            }
            case 2 : {
                binding.headline.setText(getResources().getString(R.string.slide_3_title));
                binding.dot0.setTextColor(getResources().getColor(R.color.dot_inactive));
                binding.dot1.setTextColor(getResources().getColor(R.color.dot_inactive));
                binding.dot2.setTextColor(getResources().getColor(R.color.colorPrimary));
                break;
            }
        }
    }

    class MyViewPagerAdapter extends PagerAdapter{

        int[] layouts;

        public MyViewPagerAdapter(int[] layouts) {
            this.layouts = layouts;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View view= layoutInflater.inflate(this.layouts[position],container,false);
            container.addView(view);
            return view;
        }

        @Override
        public int getCount() {
            return this.layouts.length;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
            return view.equals(object);
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, @NonNull Object view) {
            collection.removeView((View) view);
        }
    }
}
