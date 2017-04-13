package com.hd.rxjavaandretrofit;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hd.studyandtest.R;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.functions.Func0;
import rx.functions.Func1;
import rx.functions.Func2;
import rx.schedulers.Schedulers;
import rx.subjects.AsyncSubject;
import rx.subjects.BehaviorSubject;

public class ActivityRxjavaTest extends AppCompatActivity {

    private static final String TAG = "ActivityRxjavaTest";
    @Bind(R.id.mybtn)
    Button mybutton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_rxjava_test);
        ButterKnife.bind(this);
        mybutton.setText("ActivityRxjavaTest");
        findViewById(R.id.mybtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                retrofit();
            }
        });
    }

    private void retrofit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .connectTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                .addInterceptor(interceptor).build();
        Retrofit singleton = new Retrofit.Builder()
                .baseUrl("http://60.205.213.50/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(okHttpClient)
                .build();

        singleton.create(InetworkInterface.class)
                .getData()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscribe);
    }

    Subscriber subscribe = new Subscriber<BeanReceive>() {
        @Override
        public void onCompleted() {
            unsubscribe();
        }

        @Override
        public void onError(Throwable e) {

        }

        @Override
        public void onNext(BeanReceive beanReceive) {
            Log.i("123", "onNext: beanReceive=" + beanReceive);

            Log.i("123", "onNext: name=" + beanReceive.getName());
            //Toast.makeText(getApplicationContext(),"onNext: name=" + beanReceive.getName(),Toast.LENGTH_SHORT).show();
            //_tv_msg
            //((TextView)findViewById(R.id._tv_msg)).setText("name=" + beanReceive.getName());
            ((TextView)findViewById(R.id._tv_msg)).setText(beanReceive.toString());
        }
    };

    private void scanChange() {
        Integer[] ii = new Integer[10];
        Observable.from(ii).flatMap(new Func1<Integer, Observable<Integer>>() {
            @Override
            public Observable<Integer> call(Integer integer) {
                integer += 1;
                return Observable.just(integer);
            }
        }).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer + integer2;
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "onNext: integer=" + integer);
            }
        });



        /*Observable.just(1,2,3,4,5).scan(new Func2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer integer, Integer integer2) {
                return integer+integer2;
            }
        }).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "onNext: Integer="+integer);
            }
        });*/
    }

    private void behaviorSubject() {
        BehaviorSubject<String> behaviorSubject = BehaviorSubject.create();
        //behaviorSubject.onNext("hello ");
        //behaviorSubject.onNext("lilei ");
        behaviorSubject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: s=" + s);
            }
        });
        behaviorSubject.onNext("good ");
        behaviorSubject.onNext("morning ");
    }

    private void asyncSubject() {
        AsyncSubject<String> asyncSubject = AsyncSubject.create();
        asyncSubject.onNext("good ");
        asyncSubject.onNext("morning ");
        //asyncSubject.onNext("lilei");
        asyncSubject.onCompleted();
        asyncSubject.subscribe(new Observer<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: s=" + s);
            }
        });
    }

    private void schedulePeriodically() {
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(final Subscriber<? super String> observer) {
                Schedulers.newThread().createWorker().schedulePeriodically(new Action0() {
                    @Override
                    public void call() {
                        observer.onNext("cc");
                    }
                }, 1, 1, TimeUnit.SECONDS);
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "call: s=" + s);
            }
        });
    }

    private void timerCreate() {
        Observable.timer(2, TimeUnit.SECONDS).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.i(TAG, "onNext: along=" + aLong);
            }
        });

    }

    private void rangeCreate() {
        Observable.range(10, 5).subscribe(new Subscriber<Integer>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Integer integer) {
                Log.i(TAG, "onNext: integer=" + integer);
            }
        });

    }

    private void intervalCreate() {
        Observable.interval(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribeOn(AndroidSchedulers.mainThread()).subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {
            }

            @Override
            public void onError(Throwable e) {
            }

            @Override
            public void onNext(Long aLong) {
                Log.i(TAG, "onNext: along=" + aLong);
                setMsg(aLong % 60 + "");
            }
        });
    }

    private void setMsg(String aLong) {
        getMsgView().setText(aLong + "");
    }

    private TextView getMsgView() {
        return (TextView) findViewById(R.id._tv_msg);
    }

    private void deferCreate() {
        Observable.defer(new Func0<Observable<String>>() {
            @Override
            public Observable<String> call() {
                return Observable.just("hello");
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: s=" + s);
            }
        });


    }

    private void fromCreate() {
        List<String> list = new ArrayList<>();
        list.add("from1");
        list.add("from2");
        list.add("from3");
        Observable.from(list).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: s=" + s);
            }
        });


    }

    private void justCreate() {

        List<String> list = new ArrayList<>();
        list.add("from1");
        list.add("from2");
        list.add("from3");

        List<String> list2 = new ArrayList<>();
        list.add("to1");
        list.add("to2");
        list.add("to3");
        Observable.just(list, list2).flatMap(new Func1<List<String>, Observable<String>>() {
            @Override
            public Observable<String> call(List<String> strings) {
                return Observable.from(strings);
            }
        }).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                s += " 1";
                return s;
            }
        }).subscribe(new Subscriber<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: s=" + s);
            }
        });
        /*Observable.just("good ","morning ","lilei").map(new Func1<String, String>() {
            @Override
            public String call(String s) {


                return s+"1";
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: s="+s);
            }
        });*/


    }

    private void baseCreate() {
        Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onCompleted();
            }
        }).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Log.i(TAG, "onCompleted: ");
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {

            }
        });

    }


    private void testFrom(String s) {
        /*Observable.just(s).map(new Func1<String , String >() {
            @Override
            public String call(String s) {
                s+="morning";

                return s;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "call: s="+s);
            }
        });*/
        Observable.just(s).flatMap(new Func1<String, Observable<String>>() {
            @Override
            public Observable<String> call(String s) {
                s += " morning";
                return Observable.just(s);
            }
        }).map(new Func1<String, String>() {
            @Override
            public String call(String s) {
                s += " lilei";
                return s;
            }
        }).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "call: s=" + s);
            }
        });

    }

    private void testPrintArray() {
        String[] name = {"zhangsan", "lisi", "wangwu", "zhaoliu", "maqi"};
        Observable.from(name).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<String>() {
            @Override
            public void call(String s) {
                Log.i(TAG, "call: name=" + s);
            }
        });
        Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                Log.i(TAG, "call: sleep begin");
                try {
                    Thread.sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.i(TAG, "call: sleep end");
                subscriber.onNext("cc");
            }
        }).map(new Func1<String, File>() {
            @Override
            public File call(String s) {
                return new File(s);
            }
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Action1<File>() {
            @Override
            public void call(File s) {
                Log.i(TAG, "Action1 call: s=" + s.getPath());
            }
        });

    }

    private void testfirst() {
        Observer<String> mObserver = new Observer<String>() {

            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(String s) {
                Log.i(TAG, "onNext: s=" + s);
            }
        };
        Observable observable = Observable.create(new Observable.OnSubscribe<String>() {

            @Override
            public void call(Subscriber<? super String> subscriber) {
                subscriber.onNext("this is next");
            }
        });
        observable.subscribe(mObserver);
    }

    private void testRubge() {
        Log.i(TAG, "onCreate: root directory=" + Environment.getExternalStorageDirectory().getAbsolutePath());
        File[] folders = new File("/").listFiles();//Environment.getExternalStorageDirectory().listFiles();
        Observable.from(folders)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(final File file) {
                        Log.i(TAG, "flatMap call: file.isDirectory=" + file.isDirectory() + ",name=" + file.getName());
                        if (file.isDirectory()) {
                            return Observable.from(file.listFiles());
                        } else {
                            return Observable.create(new Observable.OnSubscribe<File>() {
                                @Override
                                public void call(Subscriber<? super File> subscriber) {
                                    if (file.getName().contains("微笑")) {
                                        file.delete();
                                        subscriber.onNext(null);
                                    }
                                    subscriber.onNext(file);
                                }
                            });
                        }
                    }
                }).subscribe(new Observer<File>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(File file) {
                if (file != null)
                    Log.i(TAG, "Observer call: file.isDirectory=" + file.isDirectory() + ",name=" + file.getName() + ",file.path=" + file.getAbsolutePath());
            }
        });
                        /*.filter(new Func1<File, Boolean>() {
                            @Override
                            public Boolean call(File file) {
                                return true;
                            }
                        })
                        .map(new Func1<File, Bitmap>() {
                            @Override
                            public Bitmap call(File file) {
                                Log.i(TAG, "Map call: file.isDirectory="+file.isDirectory()+",name="+file.getName());

                                return getBitmapFromFile(file);
                            }
                        })*/
        ;

    }

    private void rxjavaMethod() {
        File[] folders = new File("/").listFiles();
        Observable.from(folders)
                .flatMap(new Func1<File, Observable<File>>() {
                    @Override
                    public Observable<File> call(File file) {
                        return Observable.from(file.listFiles());
                    }
                })
                .filter(new Func1<File, Boolean>() {
                    @Override
                    public Boolean call(File file) {
                        return file.getName().toLowerCase().endsWith(".png");
                    }
                })
                .map(new Func1<File, Bitmap>() {
                    @Override
                    public Bitmap call(File file) {
                        return getBitmapFromFile(file);
                    }
                });
    }

    private void showPngFile(final File[] folders) {
        new Thread() {
            @Override
            public void run() {
                super.run();
                for (File folder : folders) {
                    File[] files = folder.listFiles();
                    for (File file : files) {
                        if (file.getName().toLowerCase().endsWith(".png")) {
                            final Bitmap bitmap = getBitmapFromFile(file);
                            addImage(bitmap);
                        }
                    }
                }
            }
        }.start();
    }

    private void addImage(final Bitmap bitmap) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //imageCollectorView.addImage(bitmap);
                Log.i("123", "addImage");
            }
        });
    }

    private Bitmap getBitmapFromFile(File file) {

        Log.i(TAG, "getBitmapFromFile");
        return null;
    }
}
