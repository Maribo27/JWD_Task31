package by.tc.hostel_system.service.validation;

import org.junit.Test;

import static by.tc.hostel_system.controller.constant.EntityAttributes.NAME;
import static org.junit.Assert.assertEquals;

public class DataValidatorTest {
	@Test
	public void truePassword() {
		String password = "qwerty54_";
		boolean expected = true;
		boolean actual = true;
		try {
			UserValidator.isPassword(password);
		} catch (InputException e) {
			actual = false;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void wrongPassword() {
		String password = "&5kdjweg";
		boolean expected = false;
		boolean actual = true;
		try {
			UserValidator.isPassword(password);
		} catch (InputException e) {
			actual = false;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void trueEmail() {
		String email = "qwerty_i@gmail.com";
		boolean expected = true;
		boolean actual = true;
		try {
			UserValidator.isEmail(email);
		} catch (InputException e) {
			actual = false;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void wrongEmail() {
		String email = "armaq$@lololo.npo";
		boolean expected = false;
		boolean actual = true;
		try {
			UserValidator.isEmail(email);
		} catch (InputException e) {
			actual = false;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void trueName() {
		String name = "Michael";
		boolean expected = true;
		boolean actual = true;
		try {
			UserValidator.isName(name, NAME);
		} catch (InputException e) {
			actual = false;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void wrongName() {
		String name = "_utyr";
		boolean expected = false;
		boolean actual = true;
		try {
			UserValidator.isName(name, NAME);
		} catch (InputException e) {
			actual = false;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void trueUsername() {
		String username = "qwer_ty54";
		boolean expected = true;
		boolean actual = true;
		try {
			UserValidator.isUsername(username);
		} catch (InputException e) {
			actual = false;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void wrongUsername() {
		String username = "&-+hf";
		boolean expected = false;
		boolean actual = true;
		try {
			UserValidator.isUsername(username);
		} catch (InputException e) {
			actual = false;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void trueRequestType() {
		String requestType = "booking";
		boolean expected = true;
		boolean actual = true;
		try {
			Validator.isRequestType(requestType);
		} catch (InputException e) {
			actual = false;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void wrongRequestType() {
		String requestType = "wrongType";
		boolean expected = false;
		boolean actual = true;
		try {
			Validator.isRequestType(requestType);
		} catch (InputException e) {
			actual = false;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void trueRequestStatus() {
		String requestStatus = "DELeTED";
		boolean expected = true;
		boolean actual = true;
		try {
			RequestValidator.isRequestStatus(requestStatus);
		} catch (InputException e) {
			actual = false;
		}
		assertEquals(expected, actual);
	}

	@Test
	public void wrongRequestStatus() {
		String requestStatus = "something else";
		boolean expected = false;
		boolean actual = true;
		try {
			RequestValidator.isRequestStatus(requestStatus);
		} catch (InputException e) {
			actual = false;
		}
		assertEquals(expected, actual);
	}
}
