package ro.sd.client.views.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ro.sd.client.R;

public class HomepageAdmin extends AppCompatActivity {


    private Button btnProducer;
    private Button btnCarParts;

    private TextView welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage_admin);

        btnProducer = (Button) findViewById(R.id.buttonProducers);
        btnCarParts = (Button) findViewById(R.id.buttonCarParts);
        welcome = (TextView) findViewById(R.id.tvWelcomeAdmin);

        Bundle bundle = getIntent().getExtras();
        welcome.setText("Welcome, " + bundle.getString("adminUsername"));


        btnProducer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ProducersHomepage.class);
                startActivity(intent);
            }
        });

        btnCarParts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CarPartHomepage.class);
                startActivity(intent);
            }
        });
    }
}
