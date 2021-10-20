package com.usha.birthdaycardapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class BirthdayGreetingActivity extends AppCompatActivity {
    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_birthday_greeting);
        textView = findViewById(R.id.welcomeTxt);
        Bundle bundle = getIntent().getExtras();

        String welcomeMsg = bundle.getString("msg");

        textView.setText(welcomeMsg);
        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
                R.anim.fade);
        textView.startAnimation(animation);


    }
//    public void clockwise(View view){
//
//        Animation animation = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.clockwise);
//        textView.startAnimation(animation);
//    }

//    public void zoom(View view){
//        ImageView image = (ImageView)findViewById(R.id.imageView);
//        Animation animation1 = AnimationUtils.loadAnimation(getApplicationContext(),
//                R.anim.clockwise);
//        image.startAnimation(animation1);
//    }
//
//    public void fade(View view){
//        ImageView image = (ImageView)findViewById(R.id.imageView);
//        Animation animation1 =
//                AnimationUtils.loadAnimation(getApplicationContext(),
//                        R.anim.fade);
//        image.startAnimation(animation1);
//    }
//
//    public void blink(View view){
//        ImageView image = (ImageView)findViewById(R.id.imageView);
//        Animation animation1 =
//                AnimationUtils.loadAnimation(getApplicationContext(),
//                        R.anim.blink);
//        image.startAnimation(animation1);
//    }
//
//    public void move(View view){
//        ImageView image = (ImageView)findViewById(R.id.imageView);
//        Animation animation1 =
//                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.move);
//        image.startAnimation(animation1);
//    }
//
//    public void slide(View view){
//        ImageView image = (ImageView)findViewById(R.id.imageView);
//        Animation animation1 =
//                AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide);
//        image.startAnimation(animation1);
//    }
}