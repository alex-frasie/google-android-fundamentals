package ro.sd.client.views.admin;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import ro.sd.client.R;
import ro.sd.client.dto.CustomerDTO;
import ro.sd.client.utils.UserToken;
import ro.sd.client.utils.WebRequest;
import ro.sd.client.dto.LoginDTO;
import ro.sd.client.dto.ResponseDTO;

public class LoginAdmin extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;

    private TextView name;

    private LoginDTO loginDTO;

    private UserToken userToken;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        username = (EditText) findViewById(R.id.etUsername);
        password = (EditText) findViewById(R.id.etPassword);
        login = (Button) findViewById(R.id.btnLogin);

        loginDTO = new LoginDTO();
        userToken = new UserToken();

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loginDTO.setCredential(username.getText().toString());
                loginDTO.setPassword(password.getText().toString());

                WebRequest webRequest = new WebRequest(loginDTO, "http://10.0.2.2:8080/warehouse/loginAdmin");

                Thread thread = new Thread(webRequest);
                thread.start();
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                ResponseDTO responseDTO = webRequest.getResponseDTO();
                webRequest.setTokenForUser(userToken);

                validate(responseDTO);

            }
        });


    }

    @SuppressLint("SetTextI18n")
    private void validate(ResponseDTO responseDTO){

        if(responseDTO.getSeverity().equals("SUCCESS_MESSAGE")){
            Toast toast = Toast.makeText(this, responseDTO.getMessage(), Toast.LENGTH_SHORT);
            toast.getView().setBackgroundColor(Color.parseColor("#66DF6B"));
            toast.show();

            Intent intent = new Intent(getApplicationContext(), HomepageAdmin.class);
            intent.putExtra("adminUsername", userToken.getAdminByToken());
            startActivity(intent);
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
}
