package by.tc.task31.controller.command.impl;

import by.tc.task31.controller.command.Command;
import by.tc.task31.service.ServiceException;
import by.tc.task31.service.ServiceFactory;
import by.tc.task31.service.UserService;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Map;

import static by.tc.task31.controller.command.ControlConst.*;
import static by.tc.task31.controller.command.PageUrl.BLOCK_PAGE_URL;
import static by.tc.task31.controller.command.PageUrl.ERROR_PAGE_URL;

public class BlockPage implements Command {

    private ServiceFactory factory = ServiceFactory.getInstance();

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();
	    String lang = (String) session.getAttribute(LANG_ATTRIBUTE);
	    int userId = Integer.parseInt(request.getParameter(ID));

	    UserService service = factory.getUserService();

        RequestDispatcher requestDispatcher;
        try {
	        Map<Integer, String> reasons = service.getReasons(lang);
	        request.setAttribute(ID, userId);
	        request.setAttribute(REASONS, reasons);
	        requestDispatcher = request.getRequestDispatcher(BLOCK_PAGE_URL);
	        requestDispatcher.forward(request, response);
        } catch (ServiceException e) {
            request.setAttribute(ERROR_ATTRIBUTE, e.getMessage());
            RequestDispatcher dispatcher = request.getRequestDispatcher(ERROR_PAGE_URL);
            dispatcher.forward(request, response);
        }
    }
}