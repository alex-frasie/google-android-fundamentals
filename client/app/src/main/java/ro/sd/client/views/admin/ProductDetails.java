package ro.sd.client.views.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import ro.sd.client.R;
import ro.sd.client.dto.CarPartDTO;

public class ProductDetails extends AppCompatActivity {


    private TextView productName;
    private TextView productDescription;
    private TextView productPrice;
    private TextView productQuantity;
    private TextView productYear;
    private TextView productProducerName;
    private TextView productNew;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);

        productName = (TextView) findViewById(R.id.tvNameProd);
        productDescription = (TextView) findViewById(R.id.tvDescription);
        productPrice = (TextView) findViewById(R.id.tvPrice);
        productQuantity = (TextView) findViewById(R.id.tvQuantity);
        productYear = (TextView) findViewById(R.id.tvYear);
        productProducerName = (TextView) findViewById(R.id.tvProducerName);
        productNew = (TextView) findViewById(R.id.tvNew);

        Bundle bundle = getIntent().getExtras();

        productName.setText("Name: " + bundle.getString("carPartName"));
        productPrice.setText("Price: " + bundle.getString("carPartPrice"));
        productYear.setText("Fabrication year: " + bundle.getString("carPartYear"));
        productQuantity.setText("Quantity available: " + bundle.getString("carPartQuantity"));
        productProducerName.setText("Producer: " + bundle.getString("carPartProducer"));
        productNew.setText("New: " + bundle.getString("carPartNew"));
        productDescription.setText("Details: " + bundle.getString("carPartDescription"));

    }
}
