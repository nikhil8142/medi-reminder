package com.example.mediremainder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mediremainder.databinding.ActivityMainBinding;
import com.example.mediremainder.databinding.ActivityMedicineBinding;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;

import java.util.ArrayList;
import java.util.Calendar;

public class MedicineActivity extends AppCompatActivity {

    //private ActivityMedicineBinding binding;
    private MaterialTimePicker timePicker;
    private Calendar calendar;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private TextView selectTime;
    private Button setAlarm,cancelAlarm;
    String c[]={"1","2","3","4","5","6"};
    int pos,dosage;
    private EditText Mname;
    private ArrayList<Medicine> medList=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //binding = ActivityMedicineBinding.inflate(getLayoutInflater());
        //setContentView(binding.getRoot());
        setContentView(R.layout.activity_medicine);
        selectTime=findViewById(R.id.selectTime);
        Mname=(EditText) findViewById(R.id.mediName);
        setAlarm=findViewById(R.id.setAlarm);
        cancelAlarm=findViewById(R.id.cancelAlarm);

        createNotificationChannel();
        final Spinner dosageSpinner=(Spinner) findViewById(R.id.dosageSpinner);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, c);
        adapter2.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dosageSpinner.setAdapter(adapter2);
        dosageSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                dosage = Integer.parseInt(c[position]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                dosage=1;
            }
        });

        selectTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePicker = new MaterialTimePicker.Builder()
                        .setTimeFormat(TimeFormat.CLOCK_12H)
                        .setHour(12)
                        .setMinute(0)
                        .setTitleText("Select Alarm Time")
                        .build();

                timePicker.show(getSupportFragmentManager(), "androidknowledge");
                timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Medicine med = new Medicine();
                        if (timePicker.getHour() > 12){
                            selectTime.setText(
                                    String.format("%02d",(timePicker.getHour()-12)) +":"+ String.format("%02d", timePicker.getMinute())+"PM"
                            );

                        } else {
                            selectTime.setText(timePicker.getHour()+":" + timePicker.getMinute()+ "AM");
                        }
                        calendar = Calendar.getInstance();
                        calendar.set(Calendar.HOUR_OF_DAY, timePicker.getHour());
                        calendar.set(Calendar.MINUTE, timePicker.getMinute());
                        calendar.set(Calendar.SECOND, 0);
                        calendar.set(Calendar.MILLISECOND, 0);

                        med.dosage=dosage;
                        med.name=Mname.getText().toString();
                        med.time= selectTime.getText().toString();
                        boolean createSuccessful = new TableController(getApplicationContext()).create(med);
                        if (createSuccessful) {
                            Toast.makeText(getApplicationContext(), "Alarm Set", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        setAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                intent.putExtra("title","sai");
                intent.putExtra("content",""+String.valueOf(dosage));
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

                alarmManager.setInexactRepeating(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(),
                        AlarmManager.INTERVAL_DAY, pendingIntent);

                Intent returnBtn = new Intent(getApplicationContext(),
                        MainActivity.class);

                startActivity(returnBtn);
            }
        });

        cancelAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), AlarmReceiver.class);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 0, intent, PendingIntent.FLAG_IMMUTABLE);

                if (alarmManager == null){
                    alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

                }
                alarmManager.cancel(pendingIntent);
                Toast.makeText(getApplicationContext(), "Alarm Canceled", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void createNotificationChannel(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            CharSequence name = "akchannel";
            String desc = "Channel for Alarm Manager";
            int imp = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("androidknowledge", name, imp);
            channel.setDescription(desc);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}