package by.tc.hostel_system.tag;

import by.tc.hostel_system.util.TagUtil;
import org.apache.log4j.Logger;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class FullNameTag extends TagSupport {
	private final static Logger logger = Logger.getLogger(FullNameTag.class);
	private static final long serialVersionUID = 4639722300961774680L;
	/**
	 * User name.
	 */
	private String name;
	/**
	 * User surname.
	 */
	private String surname;
	/**
	 * User last name.
	 */
	private String lastName;

	public void setName(String name) {
		this.name = name;
	}

	public void setSurname(String surname) {
		this.surname = surname;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	@Override
	public int doStartTag() throws JspException {
		if (name.isEmpty() || surname.isEmpty()) {
			return SKIP_BODY;
		}
		StringBuilder tag = new StringBuilder();
		tag.append(name);
		tag.append(" ");
		if (!lastName.isEmpty()) {
			tag.append(lastName);
			tag.append(" ");
		}
		tag.append(surname);
		JspWriter out = pageContext.getOut();
		return TagUtil.writeTag(out, tag.toString(), "Cannot write user status tag to page", logger);
	}
}