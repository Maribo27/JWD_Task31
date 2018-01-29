package by.tc.task31.service.impl;

import by.tc.task31.dao.DAOException;
import by.tc.task31.dao.DAOFactory;
import by.tc.task31.dao.HostelDAO;
import by.tc.task31.entity.Hostel;
import by.tc.task31.service.HostelService;
import by.tc.task31.service.ServiceException;

import java.sql.Date;
import java.util.List;
import java.util.Map;

public class HostelServiceImpl implements HostelService {

    @Override
    public List<Hostel> getHostels(String lang) throws ServiceException {
        HostelDAO hostelDAO = DAOFactory.getInstance().getHostelDAO();

        try {
            return hostelDAO.getHostels(lang);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public List<Hostel> getHostels(String lang, int city, int room, Date start, Date end) throws ServiceException {
        HostelDAO hostelDAO = DAOFactory.getInstance().getHostelDAO();

        try {
            return hostelDAO.getHostels(lang, city, room, start, end);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Map<Integer, String> getCities(String lang) throws ServiceException {
        HostelDAO hostelDAO = DAOFactory.getInstance().getHostelDAO();

        try {
            return hostelDAO.getCities(lang);
        } catch (DAOException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}