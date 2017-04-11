package com.hd.annotation;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.hd.studyandtest.R;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class ActivityAnnotation extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_annotation);
        Observable.just(TestStudentAnnotation.class).flatMap(new Func1<Class<TestStudentAnnotation>, Observable<Method>>() {
            @Override
            public Observable<Method> call(Class<TestStudentAnnotation> testStudentAnnotationClass) {
                return Observable.from(testStudentAnnotationClass.getDeclaredMethods());
            }
        }).flatMap(new Func1<Method, Observable<Annotation>>() {
            @Override
            public Observable<Annotation> call(Method method) {
                return Observable.from(method.getDeclaredAnnotations());
            }
        }).filter(new Func1<Annotation, Boolean>() {
            @Override
            public Boolean call(Annotation annotation) {
                return annotation!=null&&annotation instanceof Student;
            }
        }).subscribe(new Action1<Annotation>() {
            @Override
            public void call(Annotation annotation) {
                Student student=(Student)annotation;
                Log.i("123", "call: student id="+student.id()+",student name"+student.name()+",student sex="+student.sex());
                Toast.makeText(ActivityAnnotation.this,"call: student id="+student.id()+",student name"+student.name()+",student sex="+student.sex(),Toast.LENGTH_LONG).show();
            }
        });
    }
}
