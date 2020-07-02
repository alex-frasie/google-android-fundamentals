package ro.sd.client.views.admin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import ro.sd.client.R;
import ro.sd.client.dto.ProducersDTO;
import ro.sd.client.utils.recyclers.ProducersRecyclerAdapter;

public class SeeProducers extends AppCompatActivity {

    private RequestQueue requestQueue;
    private ProducersDTO producersDTO;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_see_producers);

        producersDTO = new ProducersDTO();
        requestQueue = Volley.newRequestQueue(getApplicationContext());
        parseResult();

        recyclerView = findViewById(R.id.producer_list);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);

        recyclerView.setLayoutManager(linearLayoutManager);
    }

    private void parseResult(){
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, "http://10.0.2.2:8080/warehouse/admin/producers", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        producersDTO = gson.fromJson(String.valueOf(response), ProducersDTO.class);

                        ProducersRecyclerAdapter producersRecyclerAdapter = new ProducersRecyclerAdapter(producersDTO);
                        recyclerView.setAdapter(producersRecyclerAdapter);

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
