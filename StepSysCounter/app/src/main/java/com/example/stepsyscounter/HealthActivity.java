package com.example.stepsyscounter;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

public class HealthActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_health);
        Intent intent = getIntent();
        String data = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);

        TextView kcalMessage = findViewById(R.id.KcalMessage);
        kcalMessage.setText("Kcalories burned today: ");

        TextView kcalText = findViewById(R.id.kcalText);
        int steps = Integer.parseInt(data);
        double kcal = (double) steps / 25;
        String text = kcal + " kcal";
        kcalText.setText(text);

        TextView kmMessage = findViewById(R.id.KmMessage);
        kmMessage.setText("Kilometers travelled today: ");

        TextView kmText = findViewById(R.id.KmText);
        double km = (double) steps / 3000;
        DecimalFormat df = new DecimalFormat("####0.00");
        String kmTravelled = df.format(km) + " km";
        kmText.setText(kmTravelled);
    }
}