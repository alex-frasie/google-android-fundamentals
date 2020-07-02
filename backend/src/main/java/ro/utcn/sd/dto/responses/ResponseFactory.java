package ro.utcn.sd.dto.responses;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class ResponseFactory {

    public ResponseEntity generateResponse(String type, String message, HttpStatus status, HttpHeaders headers){
        if(type.equalsIgnoreCase("success")){

            return ResponseEntity.status(status)
                    .headers(headers)
                    .body(new ResponseDTO(message, "SUCCESS_MESSAGE"));
        }

        if(type.equalsIgnoreCase("error")){

            return ResponseEntity.status(status)
                    .body(new ResponseDTO(message, "ERROR_MESSAGE"));
        }

        if(type.equalsIgnoreCase("info")){
            return ResponseEntity.status(status)
                    .body(new ResponseDTO(message, "INFORMATION_MESSAGE"));
        }

        if(type.equalsIgnoreCase("warning")){
            return ResponseEntity.status(status)
                    .body(new ResponseDTO(message, "WARNING_MESSAGE"));
        }

        return null;
    }
}
