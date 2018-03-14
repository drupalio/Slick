package com.mrezanasirloo.slick.sample.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.mrezanasirloo.slick.Presenter;
import com.mrezanasirloo.slick.sample.R;
import com.mrezanasirloo.slick.test.SlickPresenterTestable;

public class ActivitySimple extends AppCompatActivity implements ViewSimple {

    @Presenter
    PresenterSimple presenter;
    private static final String TAG = ActivitySimple.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        PresenterSimple_Slick.bind(this, R.id.textView3, "foo");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
    }

    @Override
    public SlickPresenterTestable<? extends ViewTestable> presenter() {
        return presenter;
    }
}