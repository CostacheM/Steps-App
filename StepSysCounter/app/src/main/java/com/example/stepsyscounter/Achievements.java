package com.example.stepsyscounter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;

import androidx.appcompat.app.AppCompatActivity;


public class Achievements extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievements);
        Intent intent = getIntent();
        String data = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
        int steps = Integer.parseInt(data);

        CheckBox button1 = findViewById(R.id.checkBox1);
        if(steps >= 100) {
            button1.setChecked(true);
        }
        CheckBox button2 = findViewById(R.id.checkBox2);
        if(steps >= 200) {
            button2.setChecked(true);
        }
        CheckBox button3 = findViewById(R.id.checkBox3);
        if(steps >= 300) {
            button3.setChecked(true);
        }
        CheckBox button4 = findViewById(R.id.checkBox4);
        if(steps >= 500) {
            button4.setChecked(true);
        }
        CheckBox button5 = findViewById(R.id.checkBox5);
        if(steps >= 1000) {
            button5.setChecked(true);
        }
        CheckBox button6 = findViewById(R.id.checkBox6);
        if(steps >= 2000) {
            button6.setChecked(true);
        }
        CheckBox button7 = findViewById(R.id.checkBox7);
        if(steps >= 5000) {
            button7.setChecked(true);
        }
        CheckBox button8 = findViewById(R.id.checkBox8);
        if(steps >= 10000) {
            button8.setChecked(true);
        }
        CheckBox button9 = findViewById(R.id.checkBox9);
        if(steps >= 20000) {
            button9.setChecked(true);
        }
        CheckBox button10 = findViewById(R.id.checkBox10);
        if(steps >= 50000) {
            button10.setChecked(true);
        }
        CheckBox button11 = findViewById(R.id.checkBox11);
        if(steps >= 100000) {
            button11.setChecked(true);
        }
    }
}