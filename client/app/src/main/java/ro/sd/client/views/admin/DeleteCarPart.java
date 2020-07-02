package ro.sd.client.views.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import ro.sd.client.R;
import ro.sd.client.dto.DeleteProdDTO;
import ro.sd.client.dto.ResponseDTO;
import ro.sd.client.utils.WebRequest;

public class DeleteCarPart extends AppCompatActivity {

    private Button deleteCarPart;
    private EditText nameCarPart;

    private DeleteProdDTO deleteProdDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_car_part);

        deleteCarPart = (Button) findViewById(R.id.btnDeleteCarPart);
        nameCarPart = (EditText) findViewById(R.id.etCarPartToBeDeleted);

        deleteProdDTO = new DeleteProdDTO();

        deleteCarPart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!nameCarPart.getText().toString().isEmpty()) {

                    deleteProdDTO.setProductName(nameCarPart.getText().toString());

                    String deleteAPI = "http://10.0.2.2:8080/warehouse/admin/product/delete";

                    WebRequest webRequest = new WebRequest(deleteProdDTO, deleteAPI);

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

    @SuppressLint("SetTextI18n")
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
