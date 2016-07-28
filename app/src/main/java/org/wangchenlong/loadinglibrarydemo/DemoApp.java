package org.wangchenlong.loadinglibrarydemo;

import android.app.Application;
import android.content.Context;
import android.os.StrictMode;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.squareup.leakcanary.LeakCanary;

import org.wangchenlong.loadinglibrarydemo.main.DaggerMainComponent;
import org.wangchenlong.loadinglibrarydemo.main.MainComponent;
import org.wangchenlong.loadinglibrarydemo.main.MainModule;
import org.wangchenlong.loadinglibrarydemo.splash.DaggerSplashComponent;
import org.wangchenlong.loadinglibrarydemo.splash.SplashComponent;
import org.wangchenlong.loadinglibrarydemo.splash.SplashModule;

/**
 * Demo的Application
 * <p>
 * Created by wangchenlong on 16/7/27.
 */

public class DemoApp extends Application {

    private DemoComponent mDemoComponent; // 应用组件
    private MainComponent mMainComponent; // 主页组件
    private SplashComponent mSplashComponent; // 闪屏组件

    @Override public void onCreate() {
        super.onCreate();
        LeakCanary.install(this); // 内存泄露

        StrictMode.enableDefaults();

        // 应用组件初始化
        mDemoComponent = DaggerDemoComponent.builder()
                .demoModule(new DemoModule(this))
                .build();
    }

    // 获得应用组件
    @Nullable public DemoComponent demoComponent() {
        return mDemoComponent;
    }

    // 主页组件
    @Nullable public MainComponent mainComponent() {
        if (mMainComponent == null) {
            mMainComponent = DaggerMainComponent.builder()
                    .demoComponent(demoComponent())
                    .mainModule(new MainModule())
                    .build();
        }
        return mMainComponent;
    }

    // 闪屏组件
    @NonNull public SplashComponent splashComponent() {
        if (mSplashComponent == null) {
            mSplashComponent = DaggerSplashComponent.builder()
                    .demoComponent(demoComponent())
                    .splashModule(new SplashModule())
                    .build();
        }

        return mSplashComponent;
    }

    // 释放闪屏组件
    public void releaseSplashComponent() {
        mSplashComponent = null;
    }

    // 释放主页组件
    public void releaseMainComponent() {
        mMainComponent = null;
    }

    /**
     * 返回应用的Application
     *
     * @param context 上下文
     * @return 当前Application
     */
    @NonNull public static DemoApp app(@NonNull Context context) {
        return (DemoApp) context.getApplicationContext();
    }
}
