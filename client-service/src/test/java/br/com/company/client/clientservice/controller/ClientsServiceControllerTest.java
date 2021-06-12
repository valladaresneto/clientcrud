package br.com.company.client.clientservice.controller;

import br.com.company.client.clientservice.helper.RestResponsePage;
import br.com.company.client.clientservice.helper.TestHelper;
import br.com.company.client.clientservice.model.Client;
import br.com.company.client.clientservice.repository.ClientRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs(outputDir = "target/snippets")
class ClientsServiceControllerTest extends TestHelper {

	@Autowired
	private ClientRepository repository;

	@Test
	public void testCreateClient() {
		Client client = new Client(null, "Ana", "Costa", 55L);
		HttpEntity<Client> clientHttpEntity = getClientHttpEntity(client);
		Client createdClient = restTemplate.postForObject(getClientsPath(), clientHttpEntity, Client.class);
		Assertions.assertNotNull(createdClient.getId());
		Assertions.assertEquals(client.getName(), createdClient.getName());
		Assertions.assertEquals(client.getSurname(), createdClient.getSurname());
		Assertions.assertEquals(client.getAge(), createdClient.getAge());
	}

	@Test
	public void testCreateClientWithoutName() {
		Client client = new Client(null, null, "Silva", 19L);
		testCreateWithoutField(client, "\"name\":\"Name is mandatory\"");
	}

	@Test
	public void testCreateClientWithoutSurname() {
		Client client = new Client(null, "Manoel", null, 19L);
		testCreateWithoutField(client, "\"surname\":\"Surname is mandatory\"");
	}

	@Test
	public void testCreateClientWrongAge() {
		Client client = new Client(null, "Manoel", "Silva", 17L);
		testCreateWithoutField(client, "\"age\":\"The age should be greater than 18\"");
	}

	private void testCreateWithoutField(Client client, String msgExpected) {
		HttpEntity<Client> clientHttpEntity = getClientHttpEntity(client);
		try {
			restTemplate.postForObject(getClientsPath(), clientHttpEntity, Client.class);
		} catch (HttpClientErrorException e) {
			Assertions.assertTrue(e.getLocalizedMessage().contains("\"status\":\"BAD_REQUEST\""));
			Assertions.assertTrue(e.getLocalizedMessage().contains("\"message\":\"Validation errors\""));
			Assertions.assertTrue(e.getLocalizedMessage().contains(msgExpected));
			return;
		}
		Assertions.fail("Required field not validated");
	}

	@Test
	public void testUpdateClient() {
		Client initialClient = setUpTestUpdateClient();
		initialClient.setName(initialClient.getName() + " Test");
		initialClient.setSurname(initialClient.getSurname() + " Test");
		initialClient.setAge(initialClient.getAge() + 10);

		HttpEntity<Client> clientHttpEntity = getClientHttpEntity(initialClient);
		HttpEntity<Client> updatedClientEntity = restTemplate.exchange(getClientsPath(), HttpMethod.PUT, clientHttpEntity, Client.class);
		Client updatedClient = updatedClientEntity.getBody();
		Assertions.assertEquals(initialClient.getId(), updatedClient.getId());
		Assertions.assertEquals(initialClient.getName(), updatedClient.getName());
		Assertions.assertEquals(initialClient.getSurname(), updatedClient.getSurname());
		Assertions.assertEquals(initialClient.getAge(), updatedClient.getAge());
	}

	private Client setUpTestUpdateClient() {
		repository.deleteAll();
		return repository.save(new Client(null, "Diego", "Souza", 35L));
	}

	@Test
	public void testListClientsPaginated() {
		setUpListClients();
		String requestParams = "?page=0&size=2";
		RestResponsePage<Client> page = listClients(requestParams);
		Assertions.assertEquals(3, page.getTotalElements());
		Assertions.assertEquals(2, page.getContent().size());
	}

	@Test
	public void testListClientsNotPaginated() {
		setUpListClients();
		RestResponsePage<Client> page = listClients("");
		Assertions.assertEquals(3, page.getTotalElements());
		Assertions.assertEquals(3, page.getContent().size());
	}

	@Test
	public void testListClientsFilteredByName() {
		setUpListClients();
		String requestParams = "?name=Diego";
		RestResponsePage<Client> page = listClients(requestParams);
		Assertions.assertEquals(1, page.getTotalElements());
		Assertions.assertEquals(1, page.getContent().size());
	}

	@Test
	public void testListClientsFilteredBySurname() {
		setUpListClients();
		String requestParams = "?surname=Souza";
		RestResponsePage<Client> page = listClients(requestParams);
		Assertions.assertEquals(1, page.getTotalElements());
		Assertions.assertEquals(1, page.getContent().size());
	}

	@Test
	public void testListClientsFilteredByAge() {
		setUpListClients();
		String requestParams = "?age=25";
		RestResponsePage<Client> page = listClients(requestParams);
		Assertions.assertEquals(2, page.getTotalElements());
		Assertions.assertEquals(2, page.getContent().size());
	}

	private RestResponsePage<Client> listClients(String requestParams) {
		HttpEntity<Client> clientHttpEntity = getClientHttpEntity(null);
		ParameterizedTypeReference<RestResponsePage<Client>> type = new ParameterizedTypeReference<RestResponsePage<Client>>() {};
		ResponseEntity<RestResponsePage<Client>> response = restTemplate.exchange(getClientsPath() + requestParams, HttpMethod.GET, clientHttpEntity, type);
		return response.getBody();
	}

	private void setUpListClients() {
		repository.deleteAll();
		repository.save(new Client(null, "Diego", "Souza", 35L));
		repository.save(new Client(null, "Maria", "Silva", 25L));
		repository.save(new Client(null, "João", "Rodrigues", 25L));
	}
}
