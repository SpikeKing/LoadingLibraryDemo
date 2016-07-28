package org.wangchenlong.loadinglibrarydemo.splash;

import org.wangchenlong.loadinglibrarydemo.DemoComponent;

import dagger.Component;

/**
 * 闪屏的组件(Component)
 * <p>
 * Created by wangchenlong on 16/7/27.
 */
@Component(dependencies = DemoComponent.class, modules = SplashModule.class)
@SplashScope
public interface SplashComponent {
    void inject(SplashActivity splashActivity);
}
