package by.tc.hostel_system.service.validation;

public class NotNumberException extends InputException {
	private static final long serialVersionUID = -6775686214466297123L;

	NotNumberException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
