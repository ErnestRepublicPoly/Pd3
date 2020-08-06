package sg.edu.rp.c346.pd3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class Instructions extends AppCompatActivity {
    TextView tvMedication, tvSchedule, tvAdditional, tvRefresh;
    Button refresh;

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
                    tvMedication.setText(dataSnapshot.child(loggedInUser).child("medication").getValue().toString());
                    tvSchedule.setText(dataSnapshot.child(loggedInUser).child("time").getValue().toString());
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
        setContentView(R.layout.activity_instructions);
        portfolio = FirebaseDatabase.getInstance().getReference("Accounts");
        tvMedication = findViewById(R.id.textViewMeds);
        tvSchedule = findViewById(R.id.textViewSched);
        tvAdditional = findViewById(R.id.textViewAdd);
        refresh = findViewById(R.id.buttonRefresh);
        tvRefresh = findViewById(R.id.textViewUpdatedTime);

        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currentDate = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.getDefault()).format(new Date());
                tvAdditional.setText("Rebandage Leg on 7th of May");
                tvRefresh.setText("Updated At: " + currentDate);
            }
        });
    }
}