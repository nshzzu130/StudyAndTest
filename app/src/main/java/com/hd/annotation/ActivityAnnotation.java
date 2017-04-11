package com.hd.annotation;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.hd.annotation.model.Address;
import com.hd.annotation.model.Person;
import com.hd.studyandtest.R;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class ActivityAnnotation extends AppCompatActivity {
    private static final String TAG = ActivityAnnotation.class.getSimpleName();
    private EditText mAgeEditText;
    private EditText mNameEditText;
    private EditText mBdayEditText;
    private EditText mStreetEditText;
    private EditText mPostcodeEditText;
    private EditText mCityEditText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activity_annotation);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mNameEditText = (EditText) findViewById(R.id.fullName);
        mBdayEditText = (EditText) findViewById(R.id.dateOfBirth);
        mAgeEditText = (EditText) findViewById(R.id.age);
        mStreetEditText = (EditText) findViewById(R.id.street);
        mPostcodeEditText = (EditText) findViewById(R.id.postCode);
        mCityEditText = (EditText) findViewById(R.id.city);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SimpleDateFormat df = new SimpleDateFormat("dd-MM-yyyy");
                // Create the parcelable object using the creator
                try {
                    int age = TextUtils.isEmpty(mAgeEditText.getText()) ? 0 : Integer.parseInt(mAgeEditText.getText().toString());
                    Date date = TextUtils.isEmpty(mBdayEditText.getText()) ? new Date(System.currentTimeMillis()) : df.parse(mBdayEditText.getText().toString());
                    Address address = Address.create(
                            mStreetEditText.getText().toString(),
                            mPostcodeEditText.getText().toString(),
                            mCityEditText.getText().toString(),
                            /* Country */ null);
                    Person person = Person.create(
                            mNameEditText.getText().toString(),
                            date, age, address);

                    Intent activityIntent = ActivityPerson.createIntent(ActivityAnnotation.this, person);

                    if (activityIntent != null) {
                        ActivityAnnotation.this.startActivity(activityIntent);
                    }
                } catch (ParseException e) {
                    Log.e(TAG, "onClick: Error parsing date", e);
                }
            }
        });

    }

    private void simpleAnnotation(){
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
