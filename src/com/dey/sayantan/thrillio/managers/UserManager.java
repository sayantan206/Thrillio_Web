package com.dey.sayantan.thrillio.managers;

import com.dey.sayantan.thrillio.constants.Gender;
import com.dey.sayantan.thrillio.constants.UserType;
import com.dey.sayantan.thrillio.dao.UserDao;
import com.dey.sayantan.thrillio.entities.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

public class UserManager {
    private static UserManager instance = new UserManager();
    private static UserDao dao = new UserDao();

    private UserManager() {
    }

    public static UserManager getInstance() {
        return instance;
    }

    public User createUser(long id){
        User user = new User();
        user.setId(id);
        return user;
    }
    public User createUser(long id, String email, String password, String firstName, String lastName, Gender gender, UserType userType) {
        User user = (userType == UserType.EMAIL_ADMIN) ? new EmailAdmin() :
                (userType == UserType.EDITOR) ? new Editor() :
                        (userType == UserType.CHIEF_EDITOR) ? new ChiefEditor() : new User();
        user.setId(id);
        user.setEmail(email);
        user.setPassword(password);
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setGender(gender);
//        user.setUserType(userType);

        return user;
    }

    public List<User> getUsers() {
        return dao.getUsers();
    }

    /**
     * authenticate user
     *
     * @param email    user email id
     * @param password user password plain text
     * @return userId if user is valid, -1 if user is invalid
     */
    //TODO: only store hashed passwords
    public long authenticateUser(String email, String password) {
        return dao.authenticateUser(email, password);
    }

    public void registerNewUser(String firstName, String lastName, String email, String password, int gender, int userType) {
        dao.registerNewUser(firstName, lastName, email, password, gender, userType);
    }

    public User getUser(String userEmail) {
        return dao.getUser(userEmail);
    }
}
