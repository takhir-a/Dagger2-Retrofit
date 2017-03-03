package com.github.dagger2retrofit.dependencyInjection;

import com.github.dagger2retrofit.activities.MainActivity;

import javax.inject.Singleton;

import dagger.Component;


//This is a bridge interface between modules and injection
@Singleton
@Component(modules = NetModule.class)
public interface NetComponent {
    void inject(MainActivity mainActivity);

    //You can inject extra activities like this
    //void inject(SecondActivity secondActivity)
    //void inject(ThirdActivity thirdActivity)
}