package org.wangchenlong.loadinglibrarydemo.splash;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import org.wangchenlong.loadinglibrarydemo.DemoApp;
import org.wangchenlong.loadinglibrarydemo.R;
import org.wangchenlong.loadinglibrarydemo.main.MainActivity;
import org.wangchenlong.loadinglibrarydemo.main.MainLibrary;

import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Lazy;
import dagger.internal.Preconditions;
import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 启动页面, 初始化需要长时间加载的库
 * <p>
 * Created by wangchenlong on 16/7/27.
 */

public class SplashActivity extends AppCompatActivity {

    @Inject Lazy<SplashLibrary> splashLibraryLazy; // 延迟闪屏库
    @Inject @Named(SplashModule.OBSERVABLE_SPLASH_LIBRARY) Observable<SplashLibrary> splashLibraryObservable; // 闪屏库观察者
    @Inject @Named(SplashModule.SPLASH_ACTIVITY) AtomicBoolean initialized; // 闪屏模块初始化

    private Subscription mSplashSubscription; // 闪屏订阅者

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);

        DemoApp.app(this).splashComponent().inject(this);

        // 检测依赖注入释是否成功
        Preconditions.checkNotNull(splashLibraryLazy);
        Preconditions.checkNotNull(splashLibraryObservable);
        Preconditions.checkNotNull(initialized);
    }

    @Override protected void onStart() {
        super.onStart();

        // 初始化成功
        if (initialized.get()) {
            openMainAndFinish(this, splashLibraryLazy.get());
        } else {
            // 加载数据
            mSplashSubscription = splashLibraryObservable
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::onSuccess, this::onFailure);
        }
    }

    // 加载成功
    private void onSuccess(SplashLibrary library) {
        initialized.set(true);
        openMainAndFinish(SplashActivity.this, library);
    }

    // 加载失败
    private void onFailure(Throwable e) {
        Toast.makeText(SplashActivity.this, R.string.error_fatal, Toast.LENGTH_SHORT).show();
        finish();
    }

    @Override protected void onStop() {
        super.onStop();

        // 取消 注册订阅者
        if (mSplashSubscription != null && !mSplashSubscription.isUnsubscribed()) {
            mSplashSubscription.unsubscribe();
        }
    }

    @Override protected void onDestroy() {
        super.onDestroy();

        // 释放资源
        mSplashSubscription = null;
    }

    private static void openMainAndFinish(@NonNull Activity activity, @NonNull SplashLibrary splashLibrary) {
        // 提示加载库完成
        Toast.makeText(activity, splashLibrary.initializedString(), Toast.LENGTH_SHORT).show();

        // 跳转主页面
        Intent intent = new Intent(activity, MainActivity.class);
        intent.putExtra(MainActivity.EXTRA_USEFUL_STRING, splashLibrary.usefulString());
        activity.startActivity(intent);

        // 跳转页面完成关闭当前页面
        activity.finish();
    }
}
