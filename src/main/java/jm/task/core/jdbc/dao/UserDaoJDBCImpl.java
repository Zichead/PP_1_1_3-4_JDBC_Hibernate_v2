package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    private final Connection connection = (Connection) getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String newSqlTable = "CREATE TABLE IF NOT EXISTS Users (id INT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(20), lastName VARCHAR(20), age TINYINT (3))";
        try(Statement statement = connection.createStatement()){
            statement.executeUpdate(newSqlTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String deleteTable = "DROP TABLE IF EXISTS Users";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(deleteTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String newUser = "INSERT Users (name, lastName, age) VALUES (" + "\'" + name + "\'" + "," + "\'"+ lastName +"\'" + "," + age + ")";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(newUser);
            System.out.println("User с именем - " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void removeUserById(long id) {
        String deleteUser = "DELETE FROM Users WHERE id = " + id ;
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(deleteUser);
        } catch (SQLException e) {
            throw new RuntimeException(e);
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
            throw new RuntimeException(e);
        }
        System.out.println(userlist);
        return userlist;

    }

    public void cleanUsersTable() {
        String cleanTable = "TRUNCATE TABLE mydb.Users";
        try (Statement statement = connection.createStatement()){
            statement.executeUpdate(cleanTable);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }


    }
}
