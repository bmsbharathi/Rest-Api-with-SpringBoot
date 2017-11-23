package com.intellect.assignment;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellect.assignment.domain.Response;
import com.intellect.assignment.domain.User;

public class TestController {

	private static RestTemplate restTemplate = new RestTemplate();

	private static ObjectMapper mapper = new ObjectMapper();

	private static final Logger logger = Logger.getLogger(TestController.class);

	@SuppressWarnings("deprecation")
	@Test 
	public void testCreateUser() throws JsonProcessingException {

		User user = new User();
		user.setIsActive(true);
		user.setEmail("bmsbharathgmail.com");
		user.setlName("");
		user.setfName("Ganesh");
		user.setPinCode(600091);
		user.setBirthDate(new Date("10/09/2010"));
		user.setId("" + 123127);

		Response response = restTemplate.postForObject("http://localhost:8080/user/create", user, Response.class);
		logger.info("\n\n" + mapper.writeValueAsString(response));
	}

	@Test
	@Ignore
	public void testGetUser() throws JsonProcessingException {

		Map<String, String> params = new HashMap<>();
		params.put("id", "1231211");
		Response response = restTemplate.getForObject("http://localhost:8080/user/{id}", Response.class, params);

		logger.info(mapper.writeValueAsString(response));
	}

	@Test
	@Ignore
	public void testDeleteUser() throws JsonProcessingException {

		Map<String, String> params = new HashMap<>();
		params.put("id", "123123");
		restTemplate.delete("http://localhost:8080/user/delete/{id}", params);

		Response response = new Response();
		response.setResMsg("deleted");
		response.setUserId(params.get("id"));

		logger.info("\n\n" + mapper.writeValueAsString(response));
	}

	@SuppressWarnings("deprecation")
	@Test
	@Ignore
	public void testUpdateUser() throws JsonProcessingException {

		String id = "123124";
		User user = new User();
		user.setId(id);
		user.setPinCode(625016);
		user.setBirthDate(new Date("7/12/1999"));
		String requestBody = mapper.writeValueAsString(user);
		HttpEntity<String> request = new HttpEntity<String>(requestBody);

		ResponseEntity<Response> response = restTemplate.exchange("http://localhost:8080/user/update/{id}",
				HttpMethod.PUT, request, Response.class, id);
		logger.info("\n\n" + mapper.writeValueAsString(response.getBody()));
	}
}
