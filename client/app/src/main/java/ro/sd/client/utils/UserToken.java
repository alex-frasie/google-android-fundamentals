package ro.sd.client.utils;

import android.util.Base64;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import java.io.UnsupportedEncodingException;

import lombok.Data;
import ro.sd.client.dto.Authentication;

@Data
public class UserToken {

    private static String token;

    public String getAdminByToken() {
        Algorithm algorithm = Algorithm.HMAC256("SecurityToken");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        //DecodedJWT jwt = verifier.verify(token);

        String sub = decodeBase64(token);

        String[] subs = sub.split(":");

        String subject = subs[3].substring(1);
        subject = subject.split(",")[0];
        subject = subject.substring(0, subject.length()-1);

        return subject;
    }

    public Authentication getCustomerByToken() {
        Authentication authentication = new Authentication();

        Algorithm algorithm = Algorithm.HMAC256("SecurityToken");
        JWTVerifier verifier = JWT.require(algorithm)
                .withIssuer("auth0")
                .build();
        //DecodedJWT jwt = verifier.verify(token);

        String jwt = decodeBase64(token);

        jwt = jwt.substring(27);
        String[] jwt2 = jwt.split(":");

        String role = jwt2[1];
        role = role.split(",")[0];
        String[] ceva = role.split(" ");
        role = ceva[0];
        String username = ceva[1];
        authentication.setRole(role.substring(1));

        String firstName = jwt2[2];
        firstName = firstName.split(",")[0];
        authentication.setFirstName(firstName.substring(1, firstName.length()-1));

        String lastName = jwt2[3];
        lastName = lastName.split(",")[0];
        authentication.setLastName(lastName.substring(1, lastName.length()-1));

        authentication.setUsername(username.substring(0, username.length()-1));

        System.out.println(authentication);

        return authentication;
    }

    private static String decodeBase64(String coded){
        byte[] valueDecoded= new byte[0];
        try {
            Thread.sleep(1000);
            valueDecoded = Base64.decode(coded.getBytes("UTF-8"), Base64.DEFAULT);
        } catch (UnsupportedEncodingException | InterruptedException e) {
        }
        return new String(valueDecoded);
    }

    public void setToken(String setToken){
        token = setToken;
    }

}
