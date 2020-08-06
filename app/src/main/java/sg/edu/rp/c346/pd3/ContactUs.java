package sg.edu.rp.c346.pd3;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

public class ContactUs extends AppCompatActivity {
    WebView wvShow;
    TextView tvContact, tvEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        tvContact = findViewById(R.id.textViewNumber);
        tvEmail = findViewById(R.id.textViewEmail);
        wvShow = findViewById(R.id.webview1);
        final int a = 98761208;
        tvContact.setText(a + "");
        tvEmail.setText("18016517@rp.edu.sg");
        wvShow.loadUrl("https://www.sgh.com.sg/");
        wvShow.getSettings().setJavaScriptEnabled(true);

        tvContact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intentNum = new Intent(Intent.ACTION_DIAL);
                intentNum.setData(Uri.parse("tel:" + a));
                startActivity(intentNum);
            }
        });
    }
}
