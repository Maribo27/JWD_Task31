package by.tc.hostel_system.service.validation;

public class NotDateException extends InvalidParametersException {
	private static final long serialVersionUID = -5714880030166930850L;

	NotDateException(String exceptionMessage) {
		super(exceptionMessage);
	}
}