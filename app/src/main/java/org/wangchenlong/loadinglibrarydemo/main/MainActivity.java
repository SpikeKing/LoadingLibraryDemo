package org.wangchenlong.loadinglibrarydemo.main;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.wangchenlong.loadinglibrarydemo.R;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_USEFUL_STRING = "extra_useful_string";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
}
