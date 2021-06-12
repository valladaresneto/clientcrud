package br.com.company.client.clientservice.helper;

import br.com.company.client.clientservice.model.Client;
import br.com.company.client.clientservice.model.User;

import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class TestHelper {

    @LocalServerPort
    private int port;

    protected RestTemplate restTemplate = new RestTemplate();

    protected HttpEntity<Client> getClientHttpEntity(Client client) {
        User user = new User(null, "admin", "password");
        HttpEntity<String> response = restTemplate.exchange(getLoginPath(), HttpMethod.POST, new HttpEntity<>(user), String.class);
        HttpHeaders headers = response.getHeaders();
        HttpHeaders httpHeaders = new HttpHeaders();
        String key = "Authorization";
        httpHeaders.add(key, String.valueOf(headers.get(key).get(0)));
        return new HttpEntity<>(client, httpHeaders);
    }

    protected String getBasePath() {
        return "http://localhost:" + port + "/api/v1/";
    }

    protected String getLoginPath() {
        return getBasePath() + "login";
    }

    protected String getClientsPath() {
        return getBasePath() + "clients";
    }

}
