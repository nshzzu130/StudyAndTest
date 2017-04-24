package com.hd.rxjavaandretrofit;

import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by hudong on 2017/3/28.
 */
public interface InetworkInterface {
    @GET("study/json.php")
    Observable<BeanReceive>getData();
    @GET("study/plugin.php")
    Observable<BeanPlugin>getPlugins();
}
