package ro.sd.client.views.customer;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ro.sd.client.R;
import ro.sd.client.dto.PasswordResetDTO;
import ro.sd.client.dto.ResponseDTO;
import ro.sd.client.utils.Validator;
import ro.sd.client.utils.retrofit.RetrofitClientInstance;

public class ResetPasswordCustomer extends AppCompatActivity {

    private EditText credential;
    private EditText oldPassword;
    private EditText newPasword;

    private Button btnReset;

    private Validator validator = new Validator();
    private PasswordResetDTO passwordResetDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_customer);

        credential = (EditText) findViewById(R.id.etUsernameCustomerReset);
        oldPassword = (EditText) findViewById(R.id.etPasswordCustomerReset);
        newPasword = (EditText) findViewById(R.id.etNewPasswordCustomerReset);
        btnReset = (Button) findViewById(R.id.btnReset);

        passwordResetDTO = new PasswordResetDTO();

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!credential.getText().toString().isEmpty() && !oldPassword.getText().toString().isEmpty() && !newPasword.getText().toString().isEmpty()) {

                    passwordResetDTO.setCredential(credential.getText().toString());
                    passwordResetDTO.setOldPassword(oldPassword.getText().toString());
                    passwordResetDTO.setNewPassword(newPasword.getText().toString());

                    Call<ResponseDTO> call = RetrofitClientInstance
                            .getInstance()
                            .getApi()
                            .resetPassword(passwordResetDTO);

                    call.enqueue(new Callback<ResponseDTO>() {
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

                } else {
                    validator.incompleteMessage(getApplicationContext());
                }
            }
        });
    }
}
