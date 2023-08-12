package jm.task.core.jdbc.dao;
import java.util.logging.Level;
import java.util.logging.Logger;
import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private  static  final Logger logger = Logger.getLogger(UserDaoJDBCImpl.class.getName());
    private final Connection connection = Util.getConnection();
    public UserDaoJDBCImpl() {
        //this is an empty constructor
    }

    public void createUsersTable() {
        String newSqlTable = "CREATE TABLE IF NOT EXISTS Users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(20), age TINYINT (3))";
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(newSqlTable);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void dropUsersTable() {
        String deleteTable = "DROP TABLE IF EXISTS Users";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(deleteTable);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String newUser = "INSERT Users (name, lastName, age) VALUES (" + "\'" + name + "\'" + "," + "\'"+ lastName +"\'" + "," + age + ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(newUser);
            logger.log(Level.INFO,"User с именем -  {0}  добавлен в базу данных", name);
        } catch (SQLException e) {
           logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public void removeUserById(long id) {
        String deleteUser = "DELETE FROM Users WHERE id = " + id ;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteUser);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
    }

    public List<User> getAllUsers() {
        String getAllUsers = "SELECT * FROM Users";
        List<User> userlist = new ArrayList<>();
        try (Statement statement = connection.createStatement()){
            ResultSet rs = statement.executeQuery(getAllUsers);
            while (rs.next()){
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("lastname"));
                user.setAge(rs.getByte("age"));
                userlist.add(user);
            }
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }
        logger.log(Level.INFO,"All users {0} ", userlist);
        return userlist;

    }

    public void cleanUsersTable() {
        String cleanTable = "TRUNCATE TABLE mydb.Users";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(cleanTable);
        } catch (SQLException e) {
            logger.log(Level.SEVERE, e.getMessage(), e);
        }


    }
}
