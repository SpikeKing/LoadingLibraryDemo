package org.wangchenlong.loadinglibrarydemo.main;

import android.support.annotation.NonNull;

import java.util.Random;

import dagger.Module;
import dagger.Provides;
import rx.Observable;

/**
 * 主要的Module
 * <p>
 * Created by wangchenlong on 16/7/28.
 */
@Module
public class MainModule {
    @Provides @NonNull @MainScope
    public Observable<MainLibrary> mainLibrary(final Random random) {
        return Observable.fromCallable(() -> new MainLibrary(random));
    }
}
