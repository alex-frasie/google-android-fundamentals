package ro.sd.client.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import ro.sd.client.R;
import ro.sd.client.views.admin.LoginAdmin;
import ro.sd.client.views.customer.LoginCustomer;

public class Welcome extends AppCompatActivity {

    private Button buttonCustomer;
    private Button buttonAdmin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        buttonAdmin = (Button) findViewById(R.id.buttonAdmin);
        buttonCustomer = (Button) findViewById(R.id.buttonCustomer);

        buttonCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginCustomer.class);
                startActivity(intent);
            }
        });

        buttonAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LoginAdmin.class);
                startActivity(intent);
            }
        });

    }
}
