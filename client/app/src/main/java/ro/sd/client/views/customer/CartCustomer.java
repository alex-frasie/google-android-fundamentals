package ro.sd.client.views.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.sd.client.R;
import ro.sd.client.dto.Authentication;
import ro.sd.client.dto.CartProductsDTO;
import ro.sd.client.dto.ResponseDTO;
import ro.sd.client.utils.UserToken;
import ro.sd.client.utils.Validator;
import ro.sd.client.utils.recyclers.CartRecyclerAdapter;
import ro.sd.client.utils.retrofit.RetrofitClientInstance;

public class CartCustomer extends AppCompatActivity {

    private Authentication authentication;
    private UserToken userToken = new UserToken();

    private TextView tvCart;
    private RecyclerView recyclerView;
    private Button buttonOrder;

    private CartProductsDTO cartProductsDTO;

    private Validator validator = new Validator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart_customer);

        tvCart = (TextView) findViewById(R.id.cart_title);
        recyclerView = (RecyclerView) findViewById(R.id.cart_items);
        buttonOrder = (Button) findViewById(R.id.btnComputeOrder);

        cartProductsDTO = new CartProductsDTO();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        authentication = userToken.getCustomerByToken();

        tvCart.setText(authentication.getFirstName() + " " + authentication.getLastName() + "'s cart");

        Call<CartProductsDTO> call = RetrofitClientInstance
                .getInstance()
                .getApi()
                .getCart();

        call.enqueue(new Callback<CartProductsDTO>() {
            @Override
            public void onResponse(Call<CartProductsDTO> call, Response<CartProductsDTO> response) {
                cartProductsDTO = response.body();

                CartRecyclerAdapter cartRecyclerAdapter = new CartRecyclerAdapter(cartProductsDTO, getApplicationContext());
                recyclerView.setAdapter(cartRecyclerAdapter);
            }

            @Override
            public void onFailure(Call<CartProductsDTO> call, Throwable t) {
                Log.d("Fail","Response = "+t.toString());
            }
        });


        buttonOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<ResponseDTO> call1 = RetrofitClientInstance
                        .getInstance()
                        .getApi()
                        .computeOrder();

                Toast toast = Toast.makeText(getApplicationContext(), "Computing order...", Toast.LENGTH_SHORT);
                toast.getView().setBackgroundColor(Color.parseColor("#96C9F3"));
                toast.show();

                call1.enqueue(new Callback<ResponseDTO>() {
                    @Override
                    public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                        if(response.isSuccessful()){
                            validator.validate(response.body(), getApplicationContext());
                        } else {
                            Log.d("Register", "onResponse - Status : " + response.code());
                            if (response.code() >= 400) {
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

                                validator.validate(responseDTO, getApplicationContext());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<ResponseDTO> call, Throwable t) {

                    }
                });
            }
        });

    }
}
