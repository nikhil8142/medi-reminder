package com.example.mediremainder;


import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mediremainder.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.Calendar;
import java.util.List;


public class MainActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            FloatingActionButton button=findViewById(R.id.add_alarm_fab);
            button.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(getApplicationContext(), MedicineActivity.class);
                    startActivity(intent);
                }
            });
            readRecords();
        }
        public void readRecords()
        {

        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutDisplayTimes);
        linearLayoutRecords.removeAllViews();

        List<Medicine> medicineList = new TableController(this).read();

        if (medicineList.size() > 0)
        {

            for (Medicine obj : medicineList)
            {

                int id = obj.id;
                String name=obj.name;
                int dosage=obj.dosage;
                String time= String.valueOf(obj.time);

                TextView textViewStudentItem = new TextView(this);
                textViewStudentItem.setPadding(10, 30, 10, 10);
                textViewStudentItem.setText("Medicine name:"+name);
                textViewStudentItem.setTag(Integer.toString(id));
                linearLayoutRecords.addView(textViewStudentItem);
                TextView textViewTime = new TextView(this);
                textViewTime.setPadding(10, 10, 10, 10);
                textViewTime.setText("Time:"+time);
                //textViewTime.setTag(Integer.toString(id));
                //textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());
                linearLayoutRecords.addView(textViewTime);
            }

        }

        else
        {

            TextView locationItem = new TextView(this);
            locationItem.setPadding(8, 8, 8, 8);
            locationItem.setText("No records yet.");

            linearLayoutRecords.addView(locationItem);
        }

    }
    public void displayTimes() {
        LinearLayout linearLayoutRecords = (LinearLayout) findViewById(R.id.linearLayoutDisplayTimes);
        linearLayoutRecords.removeAllViews();
        int n=5;
        for(int i=0;i<n;i++)
        {
            TextView textViewStudentItem = new TextView(this);
            textViewStudentItem.setPadding(10, 30, 10, 10);
            textViewStudentItem.setText("Medicine name:");
            textViewStudentItem.setTag(Integer.toString(i));
            linearLayoutRecords.addView(textViewStudentItem);
            TextView textViewTime = new TextView(this);
            textViewTime.setPadding(10, 10, 10, 10);
            textViewTime.setText("Time:");
            textViewTime.setTag(Integer.toString(i));

            //textViewStudentItem.setOnLongClickListener(new OnLongClickListenerStudentRecord());
            linearLayoutRecords.addView(textViewTime);

        }

    }

}