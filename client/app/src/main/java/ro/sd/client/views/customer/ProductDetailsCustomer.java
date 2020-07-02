package ro.sd.client.views.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.sd.client.R;
import ro.sd.client.dto.CarPartCartDTO;
import ro.sd.client.dto.ResponseDTO;
import ro.sd.client.utils.Validator;
import ro.sd.client.utils.retrofit.RetrofitClientInstance;

public class ProductDetailsCustomer extends AppCompatActivity {

    private TextView productName;
    private TextView productDescription;
    private TextView productPrice;
    private TextView productQuantity;
    private TextView productYear;
    private TextView productProducerName;
    private TextView productNew;

    private Button addToCart;
    private EditText quantity;

    private Validator validator = new Validator();

    private CarPartCartDTO carPartCartDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details_customer);

        productName = (TextView) findViewById(R.id.tvNameProd);
        productDescription = (TextView) findViewById(R.id.tvDescription);
        productPrice = (TextView) findViewById(R.id.tvPrice);
        productQuantity = (TextView) findViewById(R.id.tvQuantity);
        productYear = (TextView) findViewById(R.id.tvYear);
        productProducerName = (TextView) findViewById(R.id.tvProducerName);
        productNew = (TextView) findViewById(R.id.tvNew);
        addToCart = (Button) findViewById(R.id.btnAddToCart);
        quantity = (EditText) findViewById(R.id.etQuantityAddToCart);

        carPartCartDTO = new CarPartCartDTO();

        final Bundle bundle = getIntent().getExtras();

        productName.setText("Name: " + bundle.getString("carPartName"));
        productPrice.setText("Price: " + bundle.getString("carPartPrice"));
        productYear.setText("Fabrication year: " + bundle.getString("carPartYear"));
        productQuantity.setText("Quantity available: " + bundle.getString("carPartQuantity"));
        productProducerName.setText("Producer: " + bundle.getString("carPartProducer"));
        productNew.setText("New: " + bundle.getString("carPartNew"));
        productDescription.setText("Details: " + bundle.getString("carPartDescription"));


        addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!quantity.getText().toString().isEmpty()){

                    try{
                        carPartCartDTO.setName(bundle.getString("carPartName"));
                        carPartCartDTO.setQuantity(Integer.parseInt(quantity.getText().toString()));
                        carPartCartDTO.setPrice(Double.parseDouble(bundle.getString("carPartPrice")));

                        Call<ResponseDTO> addToCart = RetrofitClientInstance
                                .getInstance()
                                .getApi()
                                .addToCart(carPartCartDTO);

                        addToCart.enqueue(new Callback<ResponseDTO>() {
                            @Override
                            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                                if (response.isSuccessful()) {
                                    validator.validate(response.body(), getApplicationContext());
                                } else {
                                    Log.d("Add to cart", "onResponse - Status : " + response.code());
                                    if (response.code() >= 400 ) {
                                        ResponseDTO responseDTO = new ResponseDTO();

                                        Gson gson = new Gson();
                                        TypeAdapter<ResponseDTO> adapter = gson.getAdapter(ResponseDTO.class);
                                        try {
                                            if (response.errorBody() != null)
                                                responseDTO =
                                                        adapter.fromJson(
                                                                response.errorBody().string());
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }

                                        Log.d("Add to cart", "onResponse - body err : " + responseDTO);
                                        Log.d("Add to cart", "onResponse - resp : " + response);

                                        validator.validate(responseDTO, getApplicationContext());
                                    }
                                }
                            }

                            @Override
                            public void onFailure(Call<ResponseDTO> call, Throwable t) {

                            }
                        });

                    } catch (NumberFormatException | NullPointerException e) {
                        validator.errorMessage(getApplicationContext());
                    }
                } else {
                    validator.incompleteMessage(getApplicationContext());
                }
            }
        });
    }
}
