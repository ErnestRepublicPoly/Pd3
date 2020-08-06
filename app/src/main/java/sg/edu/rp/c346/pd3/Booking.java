package sg.edu.rp.c346.pd3;

import android.Manifest;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.util.Calendar;

public class Booking extends AppCompatActivity {

    Button btnBook;
    Button btnReset;
    EditText etDate;
    TextView tvTime;
    DatePicker dp;
    private MessageReceiver br;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_booking);
        checkPermission();
        btnBook = findViewById(R.id.button);
        btnReset = findViewById(R.id.button2);
        etDate = findViewById(R.id.etDate);
        tvTime = findViewById(R.id.textViewTime);
        dp = findViewById(R.id.DatePicker);
        dp.setMinDate(Calendar.DATE);

        dp.setOnDateChangedListener(new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                etDate.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
            }
        });

        tvTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String timeOfDay = "";
                        String addMin = "0";
                        if(hourOfDay < 10){
                            timeOfDay = "AM";
                        }else{
                            timeOfDay = "PM";
                        }
                        if(minute < 10){
                            tvTime.setText(hourOfDay + ":" + addMin + minute + timeOfDay);
                        }else{
                            tvTime.setText(hourOfDay + ":" + minute + timeOfDay);
                        }
                    }
                };
                Calendar cal1 = Calendar.getInstance();
                if(tvTime.getText().toString().isEmpty() == false){
                    cal1.set(Calendar.HOUR_OF_DAY, cal1.get(Calendar.HOUR_OF_DAY));
                    cal1.set(Calendar.MINUTE, cal1.get(Calendar.MINUTE));
                }
                TimePickerDialog myTimeDialog = new TimePickerDialog(Booking.this, myTimeListener, cal1.get(Calendar.HOUR_OF_DAY), cal1.get(Calendar.MINUTE), true);
                myTimeDialog.show();
            }
        });
        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvTime.setText(null);
                etDate.setText(null);
            }
        });
        btnBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String a = "New Appointment \nDate: " + etDate.getText().toString() + "\nTime: " + tvTime.getText().toString();
                AlertDialog.Builder myBuilder = new AlertDialog.Builder(Booking.this);
                myBuilder.setMessage(a);
                myBuilder.setCancelable(false);

                myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(Booking.this, "SMS Confirmation Sent", Toast.LENGTH_LONG).show();
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage("5554", null, "Appointment Booked Successfully", null, null);
                    }
                });

                myBuilder.setNeutralButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
        br = new MessageReceiver();

        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        filter.addAction("android.provider.Telephony.SMS_RECEIVED");
        this.registerReceiver(br, filter);
    }
    private void checkPermission() {
        int permissionSendSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.SEND_SMS);
        int permissionRecvSMS = ContextCompat.checkSelfPermission(this,
                Manifest.permission.RECEIVE_SMS);
        if (permissionSendSMS != PackageManager.PERMISSION_GRANTED &&
                permissionRecvSMS != PackageManager.PERMISSION_GRANTED) {
            String[] permissionNeeded = new String[]{Manifest.permission.SEND_SMS,
                    Manifest.permission.RECEIVE_SMS};
            ActivityCompat.requestPermissions(this, permissionNeeded, 1);
        }
    }
}
