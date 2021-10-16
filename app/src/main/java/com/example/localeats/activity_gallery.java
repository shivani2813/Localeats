package com.example.localeats;

import android.app.Activity;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

public class activity_gallery extends AppCompatActivity {
    // creating object of ViewPager
    ViewPager mViewPager;

    // images array
    int[] images = {R.drawable.a1, R.drawable.a2, R.drawable.a3, R.drawable.a4,
            R.drawable.a5, R.drawable.a6, R.drawable.a7, R.drawable.a8,R.drawable.a9,R.drawable.a10,R.drawable.a11,
    R.drawable.a12,R.drawable.a13,R.drawable.a14,R.drawable.a16,R.drawable.a17};

    // Creating Object of ViewPagerAdapter
    viewpagerAdapter mViewPagerAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);

        // Initializing the ViewPager Object
        mViewPager = (ViewPager)findViewById(R.id.viewPagerMain);

        // Initializing the ViewPagerAdapter
        mViewPagerAdapter = new viewpagerAdapter(activity_gallery.this, images);

        // Adding the Adapter to the ViewPager
        mViewPager.setAdapter(mViewPagerAdapter);
    }
}

