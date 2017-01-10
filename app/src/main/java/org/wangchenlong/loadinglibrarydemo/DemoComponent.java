package org.wangchenlong.loadinglibrarydemo;

import java.util.Random;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Demo的组件(Component)
 * <p>
 * Created by wangchenlong on 16/7/27.
 */
@Singleton
@Component(modules = {DemoModule.class})
public interface DemoComponent {
}
