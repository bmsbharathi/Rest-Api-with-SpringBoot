package com.intellect.assignment.controller;

import java.io.IOException;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellect.assignment.domain.Response;
import com.intellect.assignment.domain.User;
import com.intellect.assignment.service.UserService;

@RestController
@RequestMapping("user")
public class UserController {

	private static final Logger logger = Logger.getLogger(UserController.class);

	@Autowired
	private UserService userService;

	@GetMapping(value = "/{id}")
	public ResponseEntity<Response> getUser(@PathVariable String id) throws NullPointerException {

		logger.info("Get User " + id);
		Response resp;
		resp = userService.getUserById(id);
		if (resp == null) {

			resp = new Response();
			resp.setResMsg("User not found.");
			resp.setUserId(String.valueOf(id));

			return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<Response>(resp, HttpStatus.OK);
	}

	@PostMapping(value = "/create", consumes = "application/json")
	public ResponseEntity<Response> createUser(@RequestBody User user) {

		logger.info("Inside createUser() - Controller");
		Response resp = userService.createUser(user);
		logger.info("Response from Service" + resp);

		if (resp.getValErrors().isEmpty()) {

			logger.info("No errors");
			return new ResponseEntity<Response>(resp, HttpStatus.OK);
		}

		logger.info("has errors");
		return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);

	}

	@GetMapping(value = "/")
	public String getUsers() throws JsonProcessingException {

		return userService.getUsers();
	}

	@DeleteMapping(value = "/delete/{id}")
	public ResponseEntity<Response> deleteUser(@PathVariable String id) {

		logger.info("To be Deleted userid: " + id);
		ResponseEntity<Response> responseEntity = userService.deleteUser(id);

		return responseEntity;
	}

	@PutMapping("/update/{id}")
	public Response updateUser(@PathVariable String id, @RequestBody String user)
			throws JsonParseException, JsonMappingException, IOException, NullPointerException {

		logger.info("update user: " + id);
		logger.info("New user: " + user);
		User newUser = new User();
		ObjectMapper mapper = new ObjectMapper();
		newUser = mapper.readValue(user, User.class);
		Response response = userService.updateUser(newUser);

		return response;
	}
}
