package com.hd.rxjavaoperator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.hd.studyandtest.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.observables.GroupedObservable;
import rx.schedulers.Schedulers;

public class ActivityRxjavaOperator extends AppCompatActivity {

    private static final String TAG = ActivityRxjavaOperator.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_rxjava_operator);
        rangeAndScan();
    }
    private void rangeAndScan(){
        Observable.range(1,100).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer2*2;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
            }
        });


    }
    private void groupBy(){
        final Set<Integer> set=new HashSet<>();
        Observable.range(10,10).groupBy(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return integer%3;
            }
        }).subscribe(new Action1<GroupedObservable<Integer, Integer>>() {
            @Override
            public void call(final GroupedObservable<Integer, Integer> integerIntegerGroupedObservable) {
                integerIntegerGroupedObservable.subscribe(new Action1<Integer>() {
                    @Override
                    public void call(Integer integer) {
                        Log.i(TAG, integerIntegerGroupedObservable.getKey()+"="+integer);
                        set.add(integerIntegerGroupedObservable.getKey());
                    }
                });
            }
        });
        Observable.from(set).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: "+integer);
            }
        });

    }
    private void concatMap(){
        Integer[][] array={{1,2,3},{4,5,6},{7,8,9}};
        Observable.from(array).subscribeOn(Schedulers.from(Executors.newFixedThreadPool(1))).concatMap(new Func1<Integer[], Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer[] ints) {
                Log.i(TAG, "call: ints="+ Arrays.asList(ints).toString());
                return Observable.from(ints);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
            }
        });

    }
    private void floatMap(){
        Integer[][] array={{1,2,3},{4,5,6},{7,8,9}};
        Observable.from(array).subscribeOn(Schedulers.from(Executors.newFixedThreadPool(1))).flatMap(new Func1<Integer[], Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer[] ints) {
                Log.i(TAG, "call: ints="+ Arrays.asList(ints).toString());
                return Observable.from(ints);
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
            }
        });

    }


}
