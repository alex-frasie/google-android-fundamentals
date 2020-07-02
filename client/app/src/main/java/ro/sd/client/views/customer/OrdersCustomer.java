package ro.sd.client.views.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.sd.client.R;
import ro.sd.client.dto.Authentication;
import ro.sd.client.dto.OrderDTO;
import ro.sd.client.dto.OrdersDTO;
import ro.sd.client.dto.ProducersDTO;
import ro.sd.client.dto.ResponseDTO;
import ro.sd.client.utils.UserToken;
import ro.sd.client.utils.recyclers.OrdersRecyclerAdapter;
import ro.sd.client.utils.recyclers.ProducersRecyclerAdapter;
import ro.sd.client.utils.retrofit.RetrofitClientInstance;

public class OrdersCustomer extends AppCompatActivity {

    private TextView tvOrders;
    private RecyclerView recyclerView;

    private Authentication authentication;
    private UserToken userToken = new UserToken();

    private OrdersDTO ordersDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders_customer);

        tvOrders = (TextView) findViewById(R.id.orders_title);
        recyclerView = (RecyclerView) findViewById(R.id.order_items);

        ordersDTO = new OrdersDTO();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);

        authentication = userToken.getCustomerByToken();

        tvOrders.setText(authentication.getFirstName() + " " + authentication.getLastName() + "'s orders");

        Call<OrdersDTO> call = RetrofitClientInstance
                .getInstance()
                .getApi()
                .getOrders();

        call.enqueue(new Callback<OrdersDTO>() {
            @Override
            public void onResponse(Call<OrdersDTO> call, Response<OrdersDTO> response) {
                ordersDTO = response.body();

                OrdersRecyclerAdapter recyclerAdapter = new OrdersRecyclerAdapter(ordersDTO);
                recyclerView.setAdapter(recyclerAdapter);
            }

            @Override
            public void onFailure(Call<OrdersDTO> call, Throwable t) {
                Log.d("Fail","Response = "+t.toString());
            }
        });
    }

}
