package com.intellect.assignment.validator;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.intellect.assignment.domain.User;
import com.intellect.assignment.domain.ValidationErrors;

@Service
public class UserValidator {

	private List<ValidationErrors> valErrors = null;
	private String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
			+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final Logger logger = Logger.getLogger(UserValidator.class);

	public List<ValidationErrors> validateUser(User user) {

		logger.info("Inside validateUser() - Validator");
		valErrors = new ArrayList<>();

		if (user.getEmail() == null || !Pattern.matches(EMAIL_PATTERN, user.getEmail())) {

			ValidationErrors error = new ValidationErrors();
			error.setCode(String.valueOf(0));
			error.setField("Email");
			error.setMessage("Invalid Mail Id");
			valErrors.add(error);
		}
		if (user.getfName() == null || user.getfName().trim().length() == 0) {

			ValidationErrors error = new ValidationErrors();
			error.setCode(String.valueOf(0));
			error.setField("fName");
			error.setMessage("Enter your fName");
			valErrors.add(error);
		}
		if (user.getlName() == null || user.getfName().trim().length() == 0) {

			ValidationErrors error = new ValidationErrors();
			error.setCode(String.valueOf(0));
			error.setField("lName");
			error.setMessage("Enter your lName");
			valErrors.add(error);
		}
		if (user.getBirthDate() == null || user.getBirthDate().toString().trim().length() == 0) {

			ValidationErrors error = new ValidationErrors();
			error.setCode(String.valueOf(0));
			error.setField("birthDate");
			error.setMessage("Enter your birthDate");
			valErrors.add(error);

		}
		if (user.getBirthDate().after(new Date())) {
			
			logger.warn(user.getBirthDate() + "  " + new Date());
			ValidationErrors error1 = new ValidationErrors();
			error1.setCode(String.valueOf(0));
			error1.setField("birthDate");
			error1.setMessage("Enter a valid birthDate");
			valErrors.add(error1);
		}
		if (user.getId() == null || user.getId().trim().length() == 0) {

			ValidationErrors error = new ValidationErrors();
			error.setCode(String.valueOf(0));
			error.setField("id");
			error.setMessage("Enter your id");
			valErrors.add(error);
		}

		return valErrors;

	}
}
