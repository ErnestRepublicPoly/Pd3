package sg.edu.rp.c346.pd3;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.AlarmClock;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;

public class Alarm extends AppCompatActivity {
    Button btnResetAlarm, btnaddAlarm;
    TimePicker tpAlarmTime;
    TextView tvUserMeds, tvUserTimes;

    DatabaseReference portfolio;
    ArrayList<String> user = new ArrayList<>();
    ArrayList<String> meds = new ArrayList<>();
    ArrayList<String> times = new ArrayList<>();

    @Override
    protected void onStart() {
        super.onStart();
        user.clear();
        meds.clear();
        times.clear();

        portfolio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                String loggedInUser = prefs.getString("user", " ");

                if(dataSnapshot.hasChild(loggedInUser)) {
                        tvUserMeds.setText(dataSnapshot.child(loggedInUser).child("medication").getValue().toString());
                        tvUserTimes.setText(dataSnapshot.child(loggedInUser).child("time").getValue().toString());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
            }
        });

    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        portfolio = FirebaseDatabase.getInstance().getReference("Accounts");
        setContentView(R.layout.activity_alarm);
        btnResetAlarm = findViewById(R.id.buttonReset);
        btnaddAlarm = findViewById(R.id.buttonAddAlarm);
        tpAlarmTime = findViewById(R.id.timePicker);
        tvUserMeds = findViewById(R.id.tvMeds);
        tvUserTimes = findViewById(R.id.tvTimes);

        checkPermission();



        btnaddAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentAlarm = new Intent(AlarmClock.ACTION_SET_ALARM);
                intentAlarm.putExtra(AlarmClock.EXTRA_HOUR, tpAlarmTime.getHour());
                intentAlarm.putExtra(AlarmClock.EXTRA_MINUTES, tpAlarmTime.getMinute());
                startActivity(intentAlarm);
            }
        });

        btnResetAlarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar cal1 = Calendar.getInstance();
                tpAlarmTime.setHour(cal1.get(Calendar.HOUR_OF_DAY));
                tpAlarmTime.setMinute(cal1.get(Calendar.MINUTE));
            }
        });
    }
    private void checkPermission() {
        int permissionSetAlarm = ContextCompat.checkSelfPermission(this, Manifest.permission.SET_ALARM);
        if (permissionSetAlarm != PackageManager.PERMISSION_GRANTED) {
            String[] permissionNeeded = new String[]{Manifest.permission.SET_ALARM};
            ActivityCompat.requestPermissions(this, permissionNeeded, 2);
        }
    }
}
