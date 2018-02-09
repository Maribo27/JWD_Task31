package by.tc.hostel_system.controller.command.user_impl;

import by.tc.hostel_system.controller.command.Command;
import by.tc.hostel_system.entity.Hostel;
import by.tc.hostel_system.entity.PaginationHelper;
import by.tc.hostel_system.service.ServiceException;
import by.tc.hostel_system.service.ServiceFactory;
import by.tc.hostel_system.service.hostel.HostelNotFoundException;
import by.tc.hostel_system.service.hostel.HostelService;
import by.tc.hostel_system.util.ControllerUtil;
import org.apache.log4j.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static by.tc.hostel_system.controller.constant.ControlConst.*;
import static by.tc.hostel_system.controller.constant.EntityAttributes.*;
import static by.tc.hostel_system.controller.constant.PageUrl.AVAILABLE_HOSTELS_PAGE;
import static by.tc.hostel_system.controller.constant.PageUrl.NOTHING_FOUND_PAGE;

public class ShowAvailableHostels implements Command {
	private static final Logger logger = Logger.getLogger(ShowAvailableHostels.class);
	private static final String CITY = "city";
	private static final String CITIES = "cities";

	@Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    HttpSession session = request.getSession();

	    String lang = (String) session.getAttribute(LANG);
	    String city = request.getParameter(CITY);
	    String rooms = request.getParameter(ROOMS);
	    String days = request.getParameter(DAYS);
	    String page = request.getParameter(NUMBER);
	    String type = request.getParameter(TYPE);
	    String date = request.getParameter(DATE);

	    HostelService service = ServiceFactory.getInstance().getHostelService();
	    RequestDispatcher requestDispatcher;
	    try {
		    List<Hostel> hostels = service.getHostels(lang, city, rooms, date, days, page, type);
		    int currentPage = Integer.parseInt(page);
		    PaginationHelper paginationHelper = ControllerUtil.createPagination(request, currentPage, hostels.size(), "SHOW_HOSTELS");
		    request.setAttribute(PAGE, paginationHelper);

		    List<Hostel> hostelsOnPage = hostels.subList(paginationHelper.getBegin(), paginationHelper.getEnd());
		    request.setAttribute(HOSTELS, hostelsOnPage);

		    Map<Integer, String> cities = service.getCities(lang);
		    request.setAttribute(CITIES, cities);

		    request.setAttribute(TYPE, type);
		    request.setAttribute(CITY, city);
		    request.setAttribute(ROOMS, rooms);
		    request.setAttribute(DAYS, days);
		    request.setAttribute(DATE, date);
		    requestDispatcher = request.getRequestDispatcher(AVAILABLE_HOSTELS_PAGE);
		    requestDispatcher.forward(request, response);
	    } catch (HostelNotFoundException e) {
		    logger.error(e.getMessage(), e);
		    ControllerUtil.updateWithMessage(request, response, e.getMessage(), NOTHING_FOUND_PAGE);
	    } catch (ServiceException e) {
		    logger.error(e.getMessage(), e);
		    response.sendError(HttpServletResponse.SC_NOT_FOUND);
	    }
    }
}