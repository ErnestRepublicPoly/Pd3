package sg.edu.rp.c346.pd3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class Home extends AppCompatActivity {

    TextView tvUser;
    ArrayList<String> alOptions;
    ListView lvOptions;
    CustomAdapter caOptions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        tvUser = findViewById(R.id.textViewUser);
        lvOptions = findViewById(R.id.listViewOptions);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        String loggedInUser = prefs.getString("user", " ");
        tvUser.setText(" " + loggedInUser + "!");


        alOptions = new ArrayList<>();
        alOptions.add("Instructions");
        alOptions.add("Update");
        alOptions.add("Booking");
        alOptions.add("Alarms");
        alOptions.add("Contact Us");

        caOptions = new CustomAdapter(this, R.layout.row, alOptions);
        lvOptions.setAdapter(caOptions);

        lvOptions.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Object c = lvOptions.getItemAtPosition(position);
                String b = c.toString();

                if(b.equals("Instructions")){
                    Intent intent = new Intent(getBaseContext(), Instructions.class);
                    startActivity(intent);
                }else if(b.equals(("Update"))){
                    Intent intent = new Intent(getBaseContext(), Update.class);
                    startActivity(intent);
                }else if(b.equals(("Booking"))){
                    Intent intent = new Intent(getBaseContext(), Booking.class);
                    startActivity(intent);
                }else if(b.equals(("Alarms"))){
                    Intent intent = new Intent(getBaseContext(), Alarm.class);
                    startActivity(intent);
                }else if(b.equals(("Contact Us"))){
                    Intent intent = new Intent(getBaseContext(), ContactUs.class);
                    startActivity(intent);
                }
            }
        });
    }
}
