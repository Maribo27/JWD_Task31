package by.tc.hostel_system.controller.command.user_impl;

import by.tc.hostel_system.controller.command.Command;
import by.tc.hostel_system.entity.PaginationHelper;
import by.tc.hostel_system.entity.Request;
import by.tc.hostel_system.service.ServiceException;
import by.tc.hostel_system.service.ServiceFactory;
import by.tc.hostel_system.service.request.RequestNotFoundException;
import by.tc.hostel_system.service.request.RequestService;
import by.tc.hostel_system.util.ControllerUtil;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

import static by.tc.hostel_system.controller.constant.ControlConst.*;
import static by.tc.hostel_system.controller.constant.PageUrl.NOTHING_FOUND_PAGE;
import static by.tc.hostel_system.controller.constant.PageUrl.REQUESTS_PAGE;

public class UserRequests implements Command {
    private static final Logger logger = Logger.getLogger(UserRequests.class);

    /**
     * The method searches for users requests.
     * It redirects to a page with requests on a successful search,
     * otherwise it redirects to the page with an error.
     * @see Command#execute(HttpServletRequest, HttpServletResponse)
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Object user = session.getAttribute(USER);

        String lang = (String) session.getAttribute(LANG);
        String page = request.getParameter(NUMBER);

        RequestService service = ServiceFactory.getInstance().getRequestService();
        try {
            List<Request> requests = service.getRequests(lang, user, page);
            int currentPage = Integer.parseInt(page);
            PaginationHelper paginationHelper = ControllerUtil.createPagination(request, currentPage, requests.size(), "SHOW_USER_REQUESTS");
            request.setAttribute(PAGE, paginationHelper);

            List<Request> requestsOnPage = requests.subList(paginationHelper.getBegin(), paginationHelper.getEnd());
            request.setAttribute(REQUESTS, requestsOnPage);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher(REQUESTS_PAGE);
            requestDispatcher.forward(request, response);
        } catch (RequestNotFoundException e) {
            logger.error(e.getMessage(), e);
            ControllerUtil.updateWithMessage(request, response, e.getMessage(), NOTHING_FOUND_PAGE);
        } catch (ServiceException e) {
            logger.error(e.getMessage(), e);
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}