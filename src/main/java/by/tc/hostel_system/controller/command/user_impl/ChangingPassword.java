package by.tc.hostel_system.controller.command.user_impl;

import by.tc.hostel_system.controller.command.Command;
import by.tc.hostel_system.entity.User;
import by.tc.hostel_system.service.ServiceException;
import by.tc.hostel_system.service.ServiceFactory;
import by.tc.hostel_system.service.user.UserNotFoundException;
import by.tc.hostel_system.service.user.UserService;
import by.tc.hostel_system.util.ControllerUtil;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.tc.hostel_system.controller.constant.ControlConst.LANG;
import static by.tc.hostel_system.controller.constant.ControlConst.Message.PASSWORD_CHANGED;
import static by.tc.hostel_system.controller.constant.ControlConst.Message.PASSWORD_INCORRECT;
import static by.tc.hostel_system.controller.constant.ControlConst.USER;
import static by.tc.hostel_system.controller.constant.EntityAttributes.NEW_PASSWORD;
import static by.tc.hostel_system.controller.constant.PageUrl.PREFERENCES_PAGE;

public class ChangingPassword implements Command {
	private static final Logger logger = Logger.getLogger(ChangingPassword.class);
	private static final String CHANGE = "change";
	private static final String CONFIRM = "change-confirm-password";

	/**
	 * The method changes password of the current user.
	 * It redirects to the preferences page on a successful change,
	 * otherwise it redirects to the page with an error.
	 * @see Command#execute(HttpServletRequest, HttpServletResponse)
	 */
	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    UserService service = ServiceFactory.getInstance().getUserService();

	    String lang = (String) session.getAttribute(LANG);
	    String password = request.getParameter(CONFIRM);
	    String newPassword = request.getParameter(NEW_PASSWORD);
	    Object user = session.getAttribute(USER);

	    try {
		    User newUser = service.getUserInformation(lang, user, password);
		    service.changePassword(newUser.getId(), newPassword);
		    session.setAttribute(USER, newUser);
		    ControllerUtil.updateWithMessage(request, response, PASSWORD_CHANGED.getMessage(lang), PREFERENCES_PAGE);
	    } catch (UserNotFoundException e) {
		    logger.error(e.getMessage(), e);
		    request.setAttribute(CHANGE, true);
		    ControllerUtil.updateWithMessage(request, response, PASSWORD_INCORRECT.getMessage(lang), PREFERENCES_PAGE);
	    } catch (ServiceException e) {
		    logger.error(e.getMessage(), e);
		    response.sendError(HttpServletResponse.SC_NOT_FOUND);
	    }
    }

}