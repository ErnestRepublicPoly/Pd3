package sg.edu.rp.c346.pd3;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import androidx.appcompat.app.AlertDialog;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText user, password;
    Button login;

    DatabaseReference portfolio;
    ArrayList<String> username = new ArrayList<String>();
    ArrayList<String> passw = new ArrayList<String>();

    @Override
    protected void onStart() {
        super.onStart();
        username.clear();
        passw.clear();

        portfolio.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for(DataSnapshot nameSnapshot: dataSnapshot.getChildren()){
                    for(DataSnapshot usernameSnapshot: nameSnapshot.getChildren()) {
                        username.add(usernameSnapshot.child("username").getValue().toString());
                        passw.add(usernameSnapshot.child("password").getValue().toString());
                    }
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
        portfolio = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_main);
        login = findViewById(R.id.buttonLogin);
        user = findViewById(R.id.editTextName);
        password = findViewById(R.id.editTextPassword);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String logInUser = user.getText().toString();
                String pass = password.getText().toString();
                Boolean l = false;
                for(int i = 0; i < username.size(); i++){
                    if(logInUser.equals(username.get(i)) && pass.equals(passw.get(i))){
                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
                        SharedPreferences.Editor prefEdit = prefs.edit();
                        prefEdit.putString("user", username.get(i));
                        prefEdit.commit();

                        l = true;
                        Intent intent = new Intent(getBaseContext(), Home.class);
                        startActivity(intent);
                    }
                }
                if(l == false){
                    AlertBox();
                }
            }
        });
    }

    public void AlertBox() {
        AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
        myBuilder.setTitle("Login Status");
        myBuilder.setMessage("Login Failed. Check ur Username and Password");
        myBuilder.setCancelable(false);

        myBuilder.setPositiveButton("Cancel", null);

        AlertDialog myDialog = myBuilder.create();
        myDialog.show();
    }



}
