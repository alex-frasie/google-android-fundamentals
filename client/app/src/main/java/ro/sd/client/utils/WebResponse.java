package ro.sd.client.utils;

import com.google.gson.Gson;

import ro.sd.client.dto.CarPartDTO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class WebResponse implements Runnable{

    private final static String TAG = WebResponse.class.getSimpleName();

    private String URL;
    private CarPartDTO carPartDTO;

    public WebResponse (String URL){
        this.URL = URL;
    }

    @Override
    public void run() {
        Gson gson = new Gson();
        try {
            URL url = new URL(URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.connect();

            BufferedReader bufferedReader;
            if (conn.getResponseCode() / 100 == 2)
                bufferedReader = new BufferedReader(new
                        InputStreamReader(conn.getInputStream()));
            else
                bufferedReader = new BufferedReader(new
                        InputStreamReader(conn.getErrorStream()));

            String result = bufferedReader.readLine();

            carPartDTO = gson.fromJson(result, CarPartDTO.class);
            conn.disconnect();

        } catch (IOException ex ) {
            ex.printStackTrace();
        }
    }

    public CarPartDTO getResponseDto() {
        return carPartDTO;
    }
}
