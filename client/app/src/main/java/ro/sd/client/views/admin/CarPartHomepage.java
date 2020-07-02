package ro.sd.client.views.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ro.sd.client.R;

public class CarPartHomepage extends AppCompatActivity {

    private Button seeCarParts;
    private Button addCarPart;
    private Button editCarPart;
    private Button deleteCarPart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_part_homepage);

        seeCarParts = (Button) findViewById(R.id.buttonSeeCarParts);
        addCarPart = (Button) findViewById(R.id.buttonAddCarPart);
        editCarPart = (Button) findViewById(R.id.buttonEditCarPart);
        deleteCarPart = (Button) findViewById(R.id.buttonDeleteCarPart);

        seeCarParts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeeProducts.class);
                startActivity(intent);
            }
        });

        addCarPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AddCarPart.class);
                startActivity(intent);
            }
        });

        editCarPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), EditCarPart.class);
                startActivity(intent);
            }
        });

        deleteCarPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), DeleteCarPart.class);
                startActivity(intent);
            }
        });
    }
}
