package com.dey.sayantan.thrillio.dao;

import com.dey.sayantan.thrillio.DataStore;
import com.dey.sayantan.thrillio.Util.DBUtility;
import com.dey.sayantan.thrillio.constants.Gender;
import com.dey.sayantan.thrillio.constants.UserType;
import com.dey.sayantan.thrillio.entities.User;
import com.dey.sayantan.thrillio.managers.UserManager;

import java.sql.*;
import java.util.List;
import java.util.Map;

/**
 * a dao class mainly interacts with the data store and invokes sql queries to fetch data from DB
 * in our primary application we will use getters from DataStore class to access  the data
 */
public class UserDao {
    private static final String url = "jdbc:mysql://localhost:3306/jid_thrillio?userSSL = false";
    private static final String uid = "root";
    private static final String password = "developer";

    public List<User> getUsers(){
        return DataStore.getUsers();
    }

    public long authenticateUser(String email, String psw) {
        try {
            Class.forName("com.mysql.jdbc.Driver");

            try (Connection conn = DriverManager.getConnection(url, uid, password)){
                String query = "SELECT USER_ID FROM USER WHERE USER_EMAIL = ? AND USER_PASSWORD = ?";
                PreparedStatement stmt = conn.prepareStatement(query);
                stmt.setString(1,email);
                stmt.setString(2, psw);
                ResultSet rs = stmt.executeQuery();
                System.out.printf("[UserDao: authenticateUser(L:29)] Query: "+query+",\t%s, %s\n",email,psw);

                while(rs.next())
                    return rs.getLong("USER_ID");
                stmt.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (ClassNotFoundException e1) {
            e1.printStackTrace();
        }

        return -1;
    }

    public void registerNewUser(String firstName, String lastName, String email, String password, int gender, int userType) {
        String sql = "INSERT INTO USER (USER_EMAIL, USER_PASSWORD, USER_FIRST_NAME, USER_LAST_NAME, USER_GENDER_ID, USER_TYPE_ID, USER_ENTRY_CREATION_TIME) VALUES (?, ?, ?, ?, ?, ?, NOW())";
        Connection conn  = DBUtility.getConnection();
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1, email);
            stmt.setString(2, password);
            stmt.setString(3, firstName);
            stmt.setString(4, lastName);
            stmt.setInt(5, gender);
            stmt.setInt(6, userType);

            stmt.executeUpdate();
            System.out.println("[" + Thread.currentThread().getStackTrace()[1] + "]: executing sql - " + sql);
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public User getUser(String userMail){
        User user = null;
        String sql = "SELECT * FROM USER WHERE USER_EMAIL = ? ";
        Connection conn  = DBUtility.getConnection();
        try(PreparedStatement stmt = conn.prepareStatement(sql)){
            stmt.setString(1,userMail);
            ResultSet rs = stmt.executeQuery();

            while(rs.next()){
                long id = rs.getLong("USER_ID");
                String email = rs.getString("USER_EMAIL");
                String password = rs.getString("USER_PASSWORD");
                String firstName = rs.getString("USER_FIRST_NAME");
                String lastName = rs.getString("USER_LAST_NAME");
                Gender gender = Gender.values()[rs.getInt("USER_GENDER_ID")];
                UserType userType = UserType.values()[rs.getInt("USER_TYPE_ID")];

                user = UserManager.getInstance().createUser(id, email, password, firstName, lastName, gender, userType);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }
}
