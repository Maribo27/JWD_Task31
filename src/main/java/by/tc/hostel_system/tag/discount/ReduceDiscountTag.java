package by.tc.hostel_system.tag.discount;

import javax.servlet.jsp.JspException;

public class ReduceDiscountTag extends DiscountTag {
	private static final long serialVersionUID = 451596039899571684L;
	private static final String CURRENT_SIGN = "minus";
	private static final String MINUS = "-";

	@Override
	public void setId(String id) {
		this.id = Integer.parseInt(id);
	}

	@Override
	public void setPage(int page) {
		this.page = page;
	}

	@Override
	public int doStartTag() throws JspException {
		return createTag(CURRENT_SIGN, MINUS);
	}
}