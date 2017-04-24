package com.hd.rxjavaoperator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.hd.studyandtest.R;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
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
        retry();
    }
    private void retry(){
        Observable<String> observable = Observable.range(10, 3).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                if(integer == 11){
                    throw new IllegalArgumentException("exception");
                }
                return integer.toString() + "retry";
            }
        });

        observable.retry(new Func2<Integer, Throwable, Boolean>() {
            @Override
            public Boolean call(Integer integer, Throwable throwable) {
                Log.i(TAG, "call: integer="+integer);
                return integer<3;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, s);
            }
        }, new Action1<Throwable>() {
            @Override
            public void call(Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
            }
        });


    }
    private void onExceptionResumeNext(){
        Observable<String> observable = Observable.just(1, 2, 3, 4, 5).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                if (integer == 4) {
                    throw new Error("hahaha");
                }
                return integer + "str";
            }
        });
        observable.onExceptionResumeNext(Observable.just("A", "B")).subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError"+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext"+s);
            }
        });
    }
    private void onErrorResumeNext(){
        Observable<String> observable = Observable.just(1, 2, 3, 4, 5).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                if (integer == 4) {
                    throw new IllegalArgumentException("hahaha");
                }
                return integer + "str";
            }
        });
        observable.onErrorResumeNext(new Func1<Throwable, Observable<? extends String>>() {
            @Override
            public Observable<? extends String> call(Throwable throwable) {
                Log.i(TAG, "call: throwable="+throwable.getMessage());
                return Observable.just("Bingo","Hello");
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "onErrorResumeNext == " + s);
            }
        });
    }
    private void onErrorReturn(){
        Observable.just(1, 2, 3, 4, 5).map(new Func1<Integer, String>() {
            @Override
            public String call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
                if (integer == 4) {
                    throw new IllegalArgumentException("IllegalArgumentException");
                }
                return integer + "str";
            }
        }).onErrorReturn(new Func1<Throwable, String>() {
            @Override
            public String call(Throwable throwable) {
                Log.i(TAG, throwable.getMessage());
                return "Bingo";
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: e="+e.getMessage());
            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: s="+s);
            }
        });


    }
    private void repeat(){
        Observable.just(5).repeat(4).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
            }
        });

    }
    private void combinelatest(){
        Observable.combineLatest(Observable.range(5,2), Observable.range(10, 4), new Func2<Integer, Integer, String>() {
            @Override
            public String call(Integer integer, Integer integer2) {
                return integer+"=="+integer2;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, s + "=combineLatest");
            }
        });
    }
    private void simple(){
        Observable.interval(1,TimeUnit.SECONDS).sample(2, TimeUnit.SECONDS).subscribe(new Action1<Long>() {
            @Override
            public void call(Long aLong) {
                Log.i(TAG, "call: along="+aLong.toString());
            }
        });

    }
    private void ignoreElements(){
        Observable.range(10,10).ignoreElements().subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {
                Log.i(TAG, "onError: ");
            }

            @Override
            public void onNext(Integer integer) {

            }
        });

    }
    private void takeLast(){
        Observable.range(10,10).takeLast(3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
            }
        });
    }
    private void take(){
        Observable.range(10,10).take(3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
            }
        });
    }
    private void skipLast(){
        Observable.range(10,10).skipLast(3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
            }
        });
    }
    private void skip(){
        Observable.range(10,10).skip(3).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
            }
        });
    }
    private void elementAt(){
        Observable.range(10,10).elementAt(9).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
            }
        });

    }
    private void distinct(){
        Observable.just(1,3,4,1,5,3).distinct(new Func1<Integer, Integer>() {
            @Override
            public Integer call(Integer integer) {
                return 1;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
            }
        });
    }
    private void debounce(){
        Observable.range(1,100).debounce(new Func1<Integer, Observable< Integer>>() {
            @Override
            public Observable<Integer> call(final Integer integer) {
                return Observable.create(new Observable.OnSubscribe<Integer>() {
                    @Override
                    public void call(Subscriber<? super Integer> subscriber) {
                        if (integer % 2 == 0 && !subscriber.isUnsubscribed()) {
                            subscriber.onNext(integer);
                            subscriber.onCompleted();
                        }
                    }
                });
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
            }
        });


    }
    private void first(){
        Observable.range(1,100).first(new Func1<Integer, Boolean>() {
            @Override
            public Boolean call(Integer integer) {
                return integer%5==0;
            }
        }).subscribe(new Action1<Integer>() {
            @Override
            public void call(Integer integer) {
                Log.i(TAG, "call: integer="+integer);
            }
        });


    }
    private void rangeAndScan(){
        Observable.range(1,100).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                Log.i(TAG, "call: integer="+integer+",integer2="+integer2);
                return integer+integer2*2;
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
