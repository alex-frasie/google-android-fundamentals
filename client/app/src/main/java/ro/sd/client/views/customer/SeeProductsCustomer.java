package ro.sd.client.views.customer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import ro.sd.client.R;
import ro.sd.client.dto.CarPartDTO;
import ro.sd.client.dto.CarPartsDTO;
import ro.sd.client.utils.WebResponse;
import ro.sd.client.utils.recyclers.CarPartsRecyclerAdapter;

public class SeeProductsCustomer extends AppCompatActivity {

    private RequestQueue requestQueue;
    private CarPartsDTO carPartsDTO;
    private RecyclerView recyclerView;

    private CarPartDTO carPartDTO = new CarPartDTO();

    public static View.OnClickListener myOnClickListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_products_customer);

        myOnClickListener = new MyOnClickListener(this);

        //get items
        carPartsDTO = new CarPartsDTO();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        parseResult();

        recyclerView = findViewById(R.id.product_list_customer);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private class MyOnClickListener implements View.OnClickListener {

        private final Context context;

        private MyOnClickListener(SeeProductsCustomer context) {
            this.context = context;
        }

        @Override
        public void onClick(final View v) {
            TextView carPartName = v.findViewById(R.id.car_part_name);

            requestQueue = Volley.newRequestQueue(getApplicationContext());
            String API = "http://10.0.2.2:8080/warehouse/customer/product/" + carPartName.getText().toString();


            WebResponse threadResponse = new WebResponse(API);
            Thread thread = new Thread(threadResponse);
            thread.start();
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            carPartDTO = threadResponse.getResponseDto();

            final Intent i = new Intent(context, ProductDetailsCustomer.class);
            i.putExtra("carPartName", carPartName.getText().toString());

            i.putExtra("carPartDescription", carPartDTO.getDescription());
            i.putExtra("carPartYear", String.valueOf(carPartDTO.getYear()));
            i.putExtra("carPartPrice", String.valueOf(carPartDTO.getPrice()));
            i.putExtra("carPartProducer", carPartDTO.getProducerName());
            i.putExtra("carPartQuantity", String.valueOf(carPartDTO.getQuantityAvailable()));
            if(carPartDTO.isNew()) {
                i.putExtra("carPartNew", "yes");
            } else i.putExtra("carPartNew", "no");

            context.startActivity(i);

        }

    }

    private void parseResult(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://10.0.2.2:8080/warehouse/customer/products", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        carPartsDTO = gson.fromJson(String.valueOf(response), CarPartsDTO.class);

                        CarPartsRecyclerAdapter carPartsRecyclerAdapter = new CarPartsRecyclerAdapter(carPartsDTO);
                        recyclerView.setAdapter(carPartsRecyclerAdapter);

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }
}
