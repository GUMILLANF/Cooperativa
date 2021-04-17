package br.com.gumillanf.cooperativa.commons.uitls;

import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.Objects;

import static java.util.Objects.nonNull;

public abstract class AppValidationUtils {

    public static boolean isHttpQueryValid(Object s) {
        return nonNull(s) && !s.equals("null") && !s.equals("") && !s.equals("undefined") && !s.equals("unknown");
    }

    public static boolean validCpf(String cpf) {
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<Object> response = restTemplate.getForEntity("https://user-info.herokuapp.com/users/" + cpf, Object.class);
        String status = ((LinkedHashMap) Objects.requireNonNull(response.getBody())).get("status").toString();
        return Objects.equals(status, "ABLE_TO_VOTE");
    }

}
