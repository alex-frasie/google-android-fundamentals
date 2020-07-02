package ro.sd.client.views.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ro.sd.client.R;

public class ProducersHomepage extends AppCompatActivity {


    private Button seeProducers;
    private Button addProducer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_producers_homepage);

        seeProducers = (Button) findViewById(R.id.buttonSeeProducers);
        addProducer = (Button) findViewById(R.id.buttonAddProducer);

        seeProducers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeeProducers.class);
                startActivity(intent);
            }
        });

        addProducer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddProducer.class);
                startActivity(intent);
            }
        });
    }
}
