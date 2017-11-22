package com.intellect.assignment.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intellect.assignment.domain.Response;
import com.intellect.assignment.domain.User;
import com.intellect.assignment.domain.ValidationErrors;
import com.intellect.assignment.validator.UserValidator;

@SuppressWarnings("deprecation")
@Service
public class UserService {

	@Autowired
	private UserValidator userValidator;
	private static List<User> userList = new ArrayList<>();
	private static ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = Logger.getLogger(UserService.class);

	static {

		User user = new User("12312", "BMS", "Bharathi", "bmsbharathi@gmail.com", 600096, new Date("09/11/1995"),
				false);
		User user1 = new User("123123", "Gokul", "Jackson", "gokularam3@gmail.com", 600093, new Date("4/9/1976"), true);
		User user2 = new User("123124", "Ram", "Michael", "balaagic@gmail.com", 600094, new Date("3/12/1990"), true);

		userList.add(user);
		userList.add(user1);
		userList.add(user2);

		logger.info("Initialized");
	}

	public Response createUser(User user) {

		logger.info("Inside createUser() - UserService");
		Response resp = new Response();
		List<ValidationErrors> valErrors = new ArrayList<>();
		valErrors = userValidator.validateUser(user);
		Iterator<ValidationErrors> iter = valErrors.iterator();

		logger.info("After Validation- " + valErrors);
		if (iter.hasNext()) {

			resp.setResMsg("User has Validation errors");
			resp.setUserId(user.getId());
			resp.setValErrors(valErrors);

			return resp;
		}
		for (User u : userList) {

			if (user.getEmail().equals(u.getEmail()) && !u.getIsActive()) {

				logger.info("" + u.getEmail() + "      " + user.getEmail());
				resp.setResMsg("User Successfully created");
				resp.setUserId(user.getId());
				resp.setValErrors(valErrors);
				userList.add(user);
				logger.info("Inserted here! " + u);

				return resp;
			}
			if (user.getEmail().equals(u.getEmail()) && u.getIsActive()) {

				ValidationErrors error = new ValidationErrors();
				resp.setResMsg("User with "+user.getEmail()+" already exists");
				resp.setUserId(user.getId());
				error.setCode("-1");
				error.setField(user.getEmail());
				error.setMessage("Email Id already exists");
				valErrors.add(error);
				resp.setValErrors(valErrors);

				return resp;
			}

		}

		resp.setResMsg("User Successfully created");
		resp.setUserId(user.getId());
		resp.setValErrors(valErrors);
		userList.add(user);
		logger.info("Inserted here! position2 " + user + "\n\n" + resp);

		return resp;
	}

	public String getUsers() throws JsonProcessingException {

		logger.info("List of users:\n" + mapper.writeValueAsString(userList));
		return mapper.writeValueAsString(userList);
	}

	public ResponseEntity<Response> deleteUser(String id) {

		Response resp = new Response();
		for (User u : userList) {

			if (u.getId().equals(id)) {

				u.setIsActive(false);
				resp.setResMsg("Successfully Deactivated");
				resp.setUserId(id);

				return new ResponseEntity<Response>(resp, HttpStatus.OK);
			}
		}
		resp.setResMsg("Could not delete, User with Id: " + id+" Not found!");
		resp.setUserId(id);

		return new ResponseEntity<Response>(resp, HttpStatus.BAD_REQUEST);

	}

	public Response getUserById(String id) {

		logger.info("ID: " + id);
		Response resp = new Response();
		for (User u : userList) {

			if (u.getId().equals(id) /* && u.isActive() */) {

				resp.setResMsg("User found! " + u);
				resp.setUserId(String.valueOf(id));

				return resp;
			}
		}

		resp.setResMsg("User not found! ");
		resp.setUserId(id);

		return resp;
	}

	public Response updateUser(User user) {

		logger.info("Inside UserService updateUser()");
		Response response = new Response();
		for (User u : userList) {
			logger.info(u.getId());
			if (user.getId().equals(u.getId())) {

				u.setBirthDate(user.getBirthDate());
				u.setPinCode(user.getPinCode());
				response.setResMsg("User pincode and birthDate Successfully updated!");
				response.setUserId(user.getId());

				return response;
			}
		}
		response.setResMsg("Failed to Update, User doesn't exist");
		response.setUserId("User id not found");

		return response;
	}
}
