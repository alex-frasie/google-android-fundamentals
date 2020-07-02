package ro.sd.client.views.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import ro.sd.client.R;
import ro.sd.client.dto.Authentication;
import ro.sd.client.utils.UserToken;
import ro.sd.client.views.admin.SeeProducts;

public class CustomerHomepage extends AppCompatActivity {

    private Button btnViewProducts;
    private Button btnViewCart;
    private Button btnViewOrders;

    private TextView welcomeCustomer;

    private Authentication authentication;
    private UserToken userToken = new UserToken();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_homepage);

        btnViewCart = (Button) findViewById(R.id.buttonViewCart);
        btnViewProducts = (Button) findViewById(R.id.buttonViewCarParts);
        btnViewOrders = (Button) findViewById(R.id.buttonViewOrders);
        welcomeCustomer = (TextView) findViewById(R.id.tvWelcomeCustomer);

        authentication = userToken.getCustomerByToken();

        welcomeCustomer.setText("Welcome, " + authentication.getFirstName() + " " + authentication.getLastName());

        btnViewProducts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SeeProductsCustomer.class);
                startActivity(intent);
            }
        });

        btnViewCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), CartCustomer.class);
                startActivity(intent);
            }
        });

        btnViewOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), OrdersCustomer.class);
                startActivity(intent);
            }
        });

    }
}
