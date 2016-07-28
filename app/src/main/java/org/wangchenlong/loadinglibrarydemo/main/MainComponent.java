package org.wangchenlong.loadinglibrarydemo.main;

import org.wangchenlong.loadinglibrarydemo.DemoComponent;

import dagger.Component;

/**
 * 主页的组件(Component)
 * <p>
 * Created by wangchenlong on 16/7/28.
 */
@Component(dependencies = DemoComponent.class, modules = MainModule.class)
@MainScope
public interface MainComponent {
    void inject(MainActivity mainActivity);
}
