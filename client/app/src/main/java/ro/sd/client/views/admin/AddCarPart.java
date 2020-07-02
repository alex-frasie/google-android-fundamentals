package ro.sd.client.views.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import ro.sd.client.R;
import ro.sd.client.dto.CarPartDTO;
import ro.sd.client.dto.ProducerDTO;
import ro.sd.client.dto.ProducersDTO;
import ro.sd.client.dto.ResponseDTO;
import ro.sd.client.utils.WebRequest;

public class AddCarPart extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Button addCarPart;

    private EditText carPartName;
    private EditText carPartYear;
    private EditText carPartDescription;
    private EditText carPartQuantity;
    private EditText carPartPrice;
    private Spinner carPartProducers;
    private Switch carPartIsNew;

    private boolean isNew = false;
    private String producerName;

    private List<String> producers;
    private ArrayAdapter<String> adapter;

    private CarPartDTO carPartDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_car_part);

        addCarPart = (Button) findViewById(R.id.btnCarPartAdd);
        carPartName = (EditText) findViewById(R.id.etCarPartName);
        carPartDescription = (EditText) findViewById(R.id.etCarPartDescription);
        carPartPrice = (EditText) findViewById(R.id.etCarPartPrice);
        carPartQuantity = (EditText) findViewById(R.id.etCarPartQuantity);
        carPartYear = (EditText) findViewById(R.id.etCarPartYear);
        carPartIsNew = (Switch) findViewById(R.id.swCarPartNew);
        carPartProducers = (Spinner) findViewById(R.id.spListProducer);

        carPartIsNew.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Log.d("Add prod", "onChange " + isChecked);
                isNew = isChecked;
            }
        });

        producers = new ArrayList<>();

        getDataSource();

        carPartProducers.setOnItemSelectedListener(this);

        carPartDTO = new CarPartDTO();

        addCarPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!carPartDescription.getText().toString().isEmpty() &&
                    !carPartName.getText().toString().isEmpty() &&
                    !carPartPrice.getText().toString().isEmpty() &&
                    !carPartYear.getText().toString().isEmpty() &&
                    !carPartQuantity.getText().toString().isEmpty()) {

                    carPartDTO.setName(carPartName.getText().toString());
                    carPartDTO.setDescription(carPartDescription.getText().toString());
                    carPartDTO.setNew(isNew);
                    carPartDTO.setPrice(Double.parseDouble(carPartPrice.getText().toString()));
                    carPartDTO.setProducerName(producerName);
                    carPartDTO.setQuantityAvailable(Integer.parseInt(carPartQuantity.getText().toString()));
                    carPartDTO.setYear(Integer.parseInt(carPartYear.getText().toString()));

                    WebRequest webRequest = new WebRequest(carPartDTO, "http://10.0.2.2:8080/warehouse/admin/product/add");

                    Thread thread = new Thread(webRequest);
                    thread.start();
                    try {
                        thread.join();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    ResponseDTO responseDTO = webRequest.getResponseDTO();

                    validate(responseDTO);
                } else {
                    errorMessage();
                }
           }
        });
    }

    private void validate(ResponseDTO responseDTO){

        if(responseDTO.getSeverity().equals("SUCCESS_MESSAGE")){
            Toast toast = Toast.makeText(this, responseDTO.getMessage(), Toast.LENGTH_SHORT);
            toast.getView().setBackgroundColor(Color.parseColor("#66DF6B"));
            toast.show();
        }
        if(responseDTO.getSeverity().equals("ERROR_MESSAGE")){
            Toast toast = Toast.makeText(this, responseDTO.getMessage(), Toast.LENGTH_SHORT);
            toast.getView().setBackgroundColor(Color.parseColor("#ECA09A"));
            toast.show();
        }
        if(responseDTO.getSeverity().equals("WARNING_MESSAGE")){
            Toast toast = Toast.makeText(this, responseDTO.getMessage(), Toast.LENGTH_SHORT);
            toast.getView().setBackgroundColor(Color.parseColor("#E4C975"));
            toast.show();
        }
        if(responseDTO.getSeverity().equals("INFORMATION_MESSAGE")){
            Toast toast = Toast.makeText(this, responseDTO.getMessage(), Toast.LENGTH_SHORT);
            toast.getView().setBackgroundColor(Color.parseColor("#96C9F3"));
            toast.show();
        }
    }

    private void errorMessage(){
        Toast toast = Toast.makeText(this, "Incomplete fields!", Toast.LENGTH_SHORT);
        toast.getView().setBackgroundColor(Color.parseColor("#E4C975"));
        toast.show();
    }


    private RequestQueue requestQueue;
    private ProducersDTO producersDTO;

    private void getDataSource() {
        producersDTO = new ProducersDTO();

        requestQueue = Volley.newRequestQueue(getApplicationContext());

        JsonObjectRequest request = new JsonObjectRequest(com.android.volley.Request.Method.GET, "http://10.0.2.2:8080/warehouse/admin/producers", null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        producersDTO = gson.fromJson(String.valueOf(response), ProducersDTO.class);
                        for(ProducerDTO producerDTO : producersDTO.getProducerDTOs()){
                            producers.add(producerDTO.getName());
                        }

                        adapter = getAdapter();
                        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        carPartProducers.setAdapter(adapter);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(request);
    }

    private ArrayAdapter<String> getAdapter(){
        return new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, producers);
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        producerName = parent.getItemAtPosition(position).toString();
        Toast.makeText(this, producerName, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
