package ro.sd.client.views.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.sd.client.R;
import ro.sd.client.dto.RegisterDTO;
import ro.sd.client.dto.ResponseDTO;
import ro.sd.client.utils.Validator;
import ro.sd.client.utils.retrofit.RetrofitClientInstance;

public class RegisterCustomer extends AppCompatActivity {

    private Button btnRegister;

    private EditText firstName;
    private EditText lastName;
    private EditText username;
    private EditText email;
    private EditText street;
    private EditText number;
    private EditText county;
    private EditText city;
    private EditText postalCode;
    private EditText password;

    private RegisterDTO registerDTO;

    private Validator validator = new Validator();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_customer);

        btnRegister = (Button) findViewById(R.id.btnRegister);
        firstName = (EditText) findViewById(R.id.etFirstName);
        lastName = (EditText) findViewById(R.id.etLastName);
        username = (EditText) findViewById(R.id.etUsernameRegister);
        email = (EditText) findViewById(R.id.etEmailRegister);
        password = (EditText) findViewById(R.id.etPasswordRegister);
        street = (EditText) findViewById(R.id.etStreetRegister);
        number = (EditText) findViewById(R.id.etNumberRegister);
        county = (EditText) findViewById(R.id.etCountyRegister);
        city = (EditText) findViewById(R.id.etCityRegister);
        postalCode = (EditText) findViewById(R.id.etPostalCodeRegister);

        registerDTO = new RegisterDTO();

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!firstName.getText().toString().isEmpty() &&
                    !lastName.getText().toString().isEmpty() &&
                    !username.getText().toString().isEmpty() &&
                    !email.getText().toString().isEmpty() &&
                    !password.getText().toString().isEmpty() &&
                    !street.getText().toString().isEmpty() &&
                    !number.getText().toString().isEmpty() &&
                    !county.getText().toString().isEmpty() &&
                    !city.getText().toString().isEmpty() &&
                    !postalCode.getText().toString().isEmpty()){

                    registerDTO.setFirstName(firstName.getText().toString());
                    registerDTO.setLastName(lastName.getText().toString());
                    registerDTO.setEmail(email.getText().toString());
                    registerDTO.setUsername(username.getText().toString());
                    registerDTO.setPassword(password.getText().toString());
                    registerDTO.setStreet(street.getText().toString());
                    registerDTO.setNumber(Integer.parseInt(number.getText().toString()));
                    registerDTO.setCounty(county.getText().toString());
                    registerDTO.setCity(city.getText().toString());
                    registerDTO.setPostalCode(postalCode.getText().toString());

                    Call<ResponseDTO> registerCustomer = RetrofitClientInstance
                            .getInstance()
                            .getApi()
                            .registerCustomer(registerDTO);

                    registerCustomer.enqueue(new Callback<ResponseDTO>() {
                        @Override
                        public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {
                            if (response.isSuccessful()) {
                                boolean ok = validator.validate(response.body(), getApplicationContext());

                                if(ok) {
                                    Intent intent = new Intent(getApplicationContext(), LoginCustomer.class);
                                    startActivity(intent);
                                }

                            } else {
                                Log.d("Register", "onResponse - Status : " + response.code());
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

                                    validator.validate(responseDTO, getApplicationContext());
                                }
                            }
                        }

                        @Override
                        public void onFailure(Call<ResponseDTO> call, Throwable t) {

                        }
                    });

                } else {
                    validator.incompleteMessage(getApplicationContext());
                }
            }
        });
    }
}
