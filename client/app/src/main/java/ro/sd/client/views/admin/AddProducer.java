package ro.sd.client.views.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.sd.client.R;
import ro.sd.client.dto.ProducerDTO;
import ro.sd.client.dto.ResponseDTO;
import ro.sd.client.utils.WebRequest;

public class AddProducer extends AppCompatActivity {

    private Button btnAdd;

    private EditText etProducerName;
    private EditText etProducerYear;
    private EditText etProducerCountry;

    private ProducerDTO producerDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_producer);

        btnAdd = (Button) findViewById(R.id.btnProducerAdd);

        etProducerName = (EditText) findViewById(R.id.etProducerName);
        etProducerCountry = (EditText) findViewById(R.id.etProducerCountry);
        etProducerYear = (EditText) findViewById(R.id.etProducerYear);

        producerDTO = new ProducerDTO();

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!etProducerName.getText().toString().isEmpty() && !etProducerCountry.getText().toString().isEmpty() && !etProducerYear.getText().toString().isEmpty()) {

                    producerDTO.setName(etProducerName.getText().toString());
                    producerDTO.setCountry(etProducerCountry.getText().toString());
                    producerDTO.setStartingYear(Integer.parseInt(etProducerYear.getText().toString()));

                    WebRequest webRequest = new WebRequest(producerDTO, "http://10.0.2.2:8080/warehouse/admin/producer/add");

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
}
