package by.tc.task31.controller.command.impl;

import by.tc.task31.controller.command.Command;
import by.tc.task31.entity.User;
import by.tc.task31.service.EntityService;
import by.tc.task31.service.ServiceException;
import by.tc.task31.service.ServiceFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static by.tc.task31.controller.command.ControlConst.*;
import static by.tc.task31.controller.command.PageUrl.ERROR_PAGE_URL;
import static by.tc.task31.controller.command.PageUrl.HOSTELS_INFO_PAGE_URL;
import static by.tc.task31.controller.command.PageUrl.USERS_INFO_PAGE_URL;

public class UnlockUser implements Command {

    private ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, ServiceException {
	    HttpSession session = request.getSession();
	    String lang = (String) session.getAttribute(LANG_ATTRIBUTE);
	    int userId = Integer.parseInt(request.getParameter(ID));

	    EntityService service = factory.getEntityService();

        RequestDispatcher requestDispatcher;

        try {
	        service.unlockUser(userId);
	        List<User> users = service.getUsers(lang);

	        request.setAttribute(USERS_ATTRIBUTE, users);
	        requestDispatcher = request.getRequestDispatcher(USERS_INFO_PAGE_URL);
	        requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            request.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE_URL);
            dispatcher.forward(request, response);
        }
    }
}