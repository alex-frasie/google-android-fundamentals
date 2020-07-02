package ro.sd.client.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.widget.Toast;

import ro.sd.client.dto.ResponseDTO;

public class Validator {

    @SuppressLint("SetTextI18n")
    public boolean validate(ResponseDTO responseDTO, Context context){

        if(responseDTO.getSeverity().equals("SUCCESS_MESSAGE")){
            Toast toast = Toast.makeText(context, responseDTO.getMessage(), Toast.LENGTH_SHORT);
            toast.getView().setBackgroundColor(Color.parseColor("#66DF6B"));
            toast.show();

            return true;
        }
        if(responseDTO.getSeverity().equals("ERROR_MESSAGE")){
            Toast toast = Toast.makeText(context, responseDTO.getMessage(), Toast.LENGTH_SHORT);
            toast.getView().setBackgroundColor(Color.parseColor("#ECA09A"));
            toast.show();

            return false;
        }
        if(responseDTO.getSeverity().equals("WARNING_MESSAGE")){
            Toast toast = Toast.makeText(context, responseDTO.getMessage(), Toast.LENGTH_SHORT);
            toast.getView().setBackgroundColor(Color.parseColor("#E4C975"));
            toast.show();

            return false;
        }
        if(responseDTO.getSeverity().equals("INFORMATION_MESSAGE")){
            Toast toast = Toast.makeText(context, responseDTO.getMessage(), Toast.LENGTH_SHORT);
            toast.getView().setBackgroundColor(Color.parseColor("#96C9F3"));
            toast.show();

            return false;
        }

        return false;
    }

    public void incompleteMessage(Context context){
        Toast toast = Toast.makeText(context, "Incomplete fields!", Toast.LENGTH_SHORT);
        toast.getView().setBackgroundColor(Color.parseColor("#E4C975"));
        toast.show();
    }

    public void errorMessage(Context context){
        Toast toast = Toast.makeText(context, "Something went wrong :(", Toast.LENGTH_SHORT);
        toast.getView().setBackgroundColor(Color.parseColor("#ECA09A"));
        toast.show();
    }
}
