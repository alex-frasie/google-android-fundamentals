package ro.sd.client.utils;

import com.google.gson.Gson;

import org.springframework.http.HttpHeaders;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

import ro.sd.client.dto.ResponseDTO;

public class WebRequest implements Runnable {

    private Object object;
    private String API_URL;

    private String token;

    private ResponseDTO responseDTO = new ResponseDTO();

    public WebRequest(Object object, String url){
        this.object = object;
        this.API_URL = url;
    }

    @Override
    public void run() {

        Gson gson = new Gson();

        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Accept", "application/json");
            conn.setDoInput(true);

            DataOutputStream os = new DataOutputStream(conn.getOutputStream());
            os.writeBytes(gson.toJson(object));

            conn.connect();
            BufferedReader bufferedReader;
            if (conn.getResponseCode() / 100 == 2) {
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

            } else
                bufferedReader = new BufferedReader(new InputStreamReader(conn.getErrorStream()));

            String result = bufferedReader.readLine();
            responseDTO = gson.fromJson(result, ResponseDTO.class);

            token = conn.getHeaderField("Authentication");

            conn.disconnect();

        } catch (ProtocolException ex) {
            ex.printStackTrace();
        } catch (MalformedURLException ex) {
            ex.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }

    }

    public ResponseDTO getResponseDTO() {
        return responseDTO;
    }

    public String getToken(){
        return token;
    }

    public void setTokenForUser(UserToken userToken){
        userToken.setToken(token);
    }


}
