package by.tc.task31.dao;

public class EntityNotFoundException extends DAOException {
	private static final long serialVersionUID = -7980822817610892636L;

	public EntityNotFoundException(String exceptionMessage) {
		super(exceptionMessage);
	}
}
