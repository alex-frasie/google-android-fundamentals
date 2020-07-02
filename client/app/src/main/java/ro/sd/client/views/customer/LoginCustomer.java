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
import ro.sd.client.dto.Authentication;
import ro.sd.client.dto.CustomerDTO;
import ro.sd.client.dto.LoginDTO;
import ro.sd.client.dto.ResponseDTO;
import ro.sd.client.utils.UserToken;
import ro.sd.client.utils.Validator;
import ro.sd.client.utils.retrofit.EndpointsAPI;
import ro.sd.client.utils.retrofit.RetrofitClientInstance;


public class LoginCustomer extends AppCompatActivity {

    private Button btnLogin;
    private Button btnRegister;
    private Button resetPassword;

    private EditText credential;
    private EditText password;

    private LoginDTO loginDTO = new LoginDTO();

    private EndpointsAPI endpointsAPI;

    private Validator validator = new Validator();
    private UserToken userToken = new UserToken();
    private Authentication authentication = new Authentication();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_customer);

        btnLogin = (Button) findViewById(R.id.btnLoginCustomer);
        btnRegister = (Button) findViewById(R.id.btnRegisterCustomer);
        credential = (EditText) findViewById(R.id.etUsernameCustomer);
        password = (EditText) findViewById(R.id.etPasswordCustomer);
        resetPassword = (Button) findViewById(R.id.btnResetPassword);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RegisterCustomer.class);
                startActivity(intent);
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!credential.getText().toString().isEmpty() && !password.getText().toString().isEmpty()) {

                    loginDTO.setCredential(credential.getText().toString());
                    loginDTO.setPassword(password.getText().toString());

                    loginCustomer(loginDTO);
                } else {
                    validator.incompleteMessage(getApplicationContext());
                }

            }
        });

        resetPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResetPasswordCustomer.class);
                startActivity(intent);
            }
        });

    }


    private void loginCustomer(LoginDTO loginDTO) {
        Call<ResponseDTO> customerLoginCall = RetrofitClientInstance
                .getInstance()
                .getApi()
                .loginCustomer(loginDTO);

        customerLoginCall.enqueue(new Callback<ResponseDTO>() {
            @Override
            public void onResponse(Call<ResponseDTO> call, Response<ResponseDTO> response) {

                if (response.isSuccessful()) {
                    validator.validate(response.body(), getApplicationContext());

                    String token = response.headers().get("Authentication");
                    Log.d("TOKEN", token);
                    userToken.setToken(token);

                    authentication = userToken.getCustomerByToken();

                    Intent intent = new Intent(getApplicationContext(), CustomerHomepage.class);
                    startActivity(intent);

                } else {
                    if (response.code() >= 400 ) {
                        ResponseDTO responseDTO = new ResponseDTO();
                        Log.d("Login", "onResponse - Status : " + response.code());
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

}
