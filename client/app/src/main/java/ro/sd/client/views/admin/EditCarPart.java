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
import ro.sd.client.dto.CarPartDTO;
import ro.sd.client.dto.ResponseDTO;
import ro.sd.client.utils.WebRequest;

public class EditCarPart extends AppCompatActivity {

    private Button buttonEdit;

    private EditText editName;
    private EditText editDescription;
    private EditText editYear;
    private EditText editPrice;
    private EditText editQuantity;

    private CarPartDTO carPartDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_car_part);

        buttonEdit = (Button) findViewById(R.id.btnEditCarPart);
        editName = (EditText) findViewById(R.id.etEditCarPartName);
        editDescription = (EditText) findViewById(R.id.etEditCarPartDescription);
        editYear = (EditText) findViewById(R.id.etEditCarPartYear);
        editPrice = (EditText) findViewById(R.id.etEditCarPartPrice);
        editQuantity = (EditText) findViewById(R.id.etEditCarPartQuantity);

        carPartDTO = new CarPartDTO();

        buttonEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!editName.getText().toString().isEmpty() &&
                    !editDescription.getText().toString().isEmpty() &&
                    !editPrice.getText().toString().isEmpty() &&
                    !editQuantity.getText().toString().isEmpty() &&
                    !editYear.getText().toString().isEmpty()) {

                    carPartDTO.setName(editName.getText().toString());
                    carPartDTO.setYear(Integer.parseInt(editYear.getText().toString()));
                    carPartDTO.setQuantityAvailable(Integer.parseInt(editQuantity.getText().toString()));
                    carPartDTO.setPrice(Double.parseDouble(editPrice.getText().toString()));
                    carPartDTO.setDescription(editDescription.getText().toString());

                    String deleteAPI = "http://10.0.2.2:8080/warehouse/admin/product/update";

                    WebRequest webRequest = new WebRequest(carPartDTO, deleteAPI);

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
