package by.tc.task31.dao.impl;

import by.tc.task31.dao.DAOException;
import by.tc.task31.dao.UserDAO;
import by.tc.task31.dao.connector.ConnectorToDB;
import by.tc.task31.dao.util.DaoUtil;
import by.tc.task31.entity.User;

import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDAOImpl implements UserDAO {
    private static final String USER_STATUS = "user";

    private static final String SQL_EXCEPTION_MESSAGE = "SQL error";
    private static final String CONVERT_PASSWORD_ERROR_MESSAGE = "Can't convert password";

    private Connection connection;
    private PreparedStatement statement;

    @Override
    public User getUserInformation(String lang, String username, String password) throws DAOException{
        connection = null;
        try {
            password = DaoUtil.createPassword(password);
            return searchUserInDB(username, password, lang);
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION_MESSAGE);
        } catch (NoSuchAlgorithmException e) {
            throw new DAOException(CONVERT_PASSWORD_ERROR_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                ConnectorToDB.closeConnection(connection);
            }
        }
    }

    private User searchUserInDB(String username, String password, String lang) throws DAOException, SQLException {
        connection = ConnectorToDB.getConnection();

        String validatePassword = "SELECT u.id_user, u.username, u.email, u.password, u.surname, u.name, " +
                "u.lastname, u.discount, u.balance, u.status, start_of_ban, end_of_ban, reason_type " +
                "FROM user AS u " +
                "LEFT JOIN (" +
                "SELECT b.start_of_ban, b.end_of_ban, br.reason_type, b.user_id FROM banned_user AS b " +
                "INNER JOIN ban_reason AS br ON (b.id_reason = br.banned_reason_id)" +
                "WHERE br.lang_name = ?) AS a " +
                "ON u.id_user = a.user_id " +
                "WHERE (username = ? OR email = ?) AND password = ?";
        statement = connection.prepareStatement(validatePassword);
        statement.setString(1, lang);
        statement.setString(2, username);
        statement.setString(3, username);
        statement.setString(4, password);

        ResultSet resultSet = statement.executeQuery();
        if (!resultSet.isBeforeFirst()) {
            return null;
        }

        return DaoUtil.createUserFromDB(resultSet);
    }

    @Override
    public User addUser(String username, String password, String name, String lastname, String surname, String email) throws DAOException {
        connection = null;

        try {
            password = DaoUtil.createPassword(password);
            User user = new User();
            user.setUsername(username);
            user.setPassword(password);
            user.setEmail(email);
            user.setName(name);
            user.setDiscount(0);
            user.setStatus(USER_STATUS);
            user.setBalance(0);
            user.setSurname(surname);
            user.setLastname(lastname);
            connection = ConnectorToDB.getConnection();
            addUserToDB(user);
            return user;
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION_MESSAGE);
        } catch (NoSuchAlgorithmException e) {
            throw new DAOException(CONVERT_PASSWORD_ERROR_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                ConnectorToDB.closeConnection(connection);
            }
        }
    }

    private void addUserToDB(User user) throws SQLException {
        String changeLogInData = "INSERT INTO user (username, email, password, name, " +
                "surname, lastname, discount, status, balance) VALUES (?,?,?,?,?,?,0,'user',0)";
        statement = connection.prepareStatement(changeLogInData);
        statement.setString(1, user.getUsername());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassword());
        statement.setString(4, user.getName());
        statement.setString(5, user.getSurname());
        statement.setString(6, user.getLastname());
        statement.executeUpdate();
    }

    @Override
    public boolean userInDB(String username) throws DAOException{
        connection = null;
        try {
            connection = ConnectorToDB.getConnection();
            String searchUser = "SELECT * FROM user WHERE user.username = ? OR user.email = ?";
            statement = connection.prepareStatement(searchUser);
            statement.setString(1, username);
            statement.setString(2, username);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                ConnectorToDB.closeConnection(connection);
            }
        }
    }

    @Override
    public List<User> getUsers(String lang) throws DAOException {
        try {

            connection = ConnectorToDB.getConnection();

            String query = "SELECT u.id_user, u.username, u.email, u.surname, u.name, " +
                    "u.lastname, u.discount, u.balance, u.status, start_of_ban, end_of_ban, reason_type " +
                    "FROM user AS u " +
                    "LEFT JOIN (" +
                    "SELECT b.start_of_ban, b.end_of_ban, br.reason_type, b.user_id FROM banned_user AS b " +
                    "INNER JOIN ban_reason AS br ON (b.id_reason = br.banned_reason_id)" +
                    "WHERE br.lang_name = ?) AS a " +
                    "ON u.id_user = a.user_id ";
            statement = connection.prepareStatement(query);
            statement.setString(1, lang);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return null;
            }

            return DaoUtil.createUsers(resultSet);
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                ConnectorToDB.closeConnection(connection);
            }
        }
    }

    @Override
    public Map<Integer, String> getReasons(String lang) throws DAOException {
        try {
            connection = ConnectorToDB.getConnection();

            String query = "SELECT b.banned_reason_id, b.reason_type " +
                    "FROM ban_reason AS b WHERE b.lang_name = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, lang);
            ResultSet resultSet = statement.executeQuery();

            if (!resultSet.isBeforeFirst()) {
                return null;
            }

            Map<Integer, String> reasons = new HashMap<>();
            DaoUtil.createMapFromDB(resultSet, reasons);
            return reasons;
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                ConnectorToDB.closeConnection(connection);
            }
        }
    }

    @Override
    public void blockUser(int id, Date date, int reason) throws DAOException {
        PreparedStatement userStat;
        PreparedStatement blockStat;
        try {
            connection = ConnectorToDB.getConnection();

            String userQuery = "UPDATE user SET status='banned' WHERE id_user=?";
            userStat = connection.prepareStatement(userQuery);
            userStat.setInt(1, id);
            userStat.executeUpdate();

            String blockQuery = "INSERT INTO banned_user (id_reason, user_id, start_of_ban, end_of_ban) " +
                    "VALUES (?,?,CURRENT_TIMESTAMP(),?)";
            blockStat = connection.prepareStatement(blockQuery);
            blockStat.setInt(1, reason);
            blockStat.setInt(2, id);
            blockStat.setDate(3, date);
            blockStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                ConnectorToDB.closeConnection(connection);
            }
        }
    }

    @Override
    public void unlockUser(int id) throws DAOException {
        PreparedStatement userStat;
        PreparedStatement blockStat;
        try {
            connection = ConnectorToDB.getConnection();
            String userQuery = "UPDATE user SET status='user' WHERE id_user=?";
            userStat = connection.prepareStatement(userQuery);
            userStat.setInt(1, id);
            userStat.executeUpdate();

            String blockQuery = "DELETE FROM banned_user WHERE user_id=?";
            blockStat = connection.prepareStatement(blockQuery);
            blockStat.setInt(1, id);
            blockStat.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                ConnectorToDB.closeConnection(connection);
            }
        }
    }

    @Override
    public void changeUserData(int id, String username, String password, String name, String lastname, String surname, String email) throws DAOException {
        try {
            password = DaoUtil.createPassword(password);
            connection = ConnectorToDB.getConnection();
            String query = "UPDATE user SET username=?, email=?, password=?, name=?, surname=?, lastname=? WHERE id_user=?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, email);
            statement.setString(3, password);
            statement.setString(4, name);
            statement.setString(5, surname);
            statement.setString(6, lastname);
            statement.setInt(7, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION_MESSAGE);
        } catch (NoSuchAlgorithmException e) {
            throw new DAOException(CONVERT_PASSWORD_ERROR_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                ConnectorToDB.closeConnection(connection);
            }
        }
    }

    @Override
    public void deleteUser(int id) throws DAOException {
        try {
            connection = ConnectorToDB.getConnection();
            String query = "DELETE FROM user WHERE id_user=?";
            statement = connection.prepareStatement(query);
            statement.setInt(1, id);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION_MESSAGE);
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException e) {
                ConnectorToDB.closeConnection(connection);
            }
        }
    }
}