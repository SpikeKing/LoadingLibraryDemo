package org.wangchenlong.loadinglibrarydemo.splash;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * 在模块(Module)注入组件(Component)时, 只生成一份单例, 由Dagger保存.
 * Created by wangchenlong on 16/7/27.
 */
@Scope
@Documented
@Retention(RetentionPolicy.RUNTIME)
public @interface SplashScope {
}
