package tn.esprit.services;


import org.mindrot.jbcrypt.BCrypt;
import tn.esprit.models.SessionUser;
import tn.esprit.models.User;
import tn.esprit.models.Waitlist;
import tn.esprit.util.DBconnection;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.mail.*;
import javax.mail.internet.*;
import javax.mail.Session;
import javax.swing.text.html.HTMLDocument;
import javax.swing.text.html.HTMLEditorKit;
import javax.swing.text.BadLocationException;
import javax.swing.text.Element;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class UserService implements IUserService<User>{
    public static String verificationCodeOfUser = null;
    public static String emailOfAccountWillVerif = null;

    private Connection cnx;

    public UserService() {
        cnx = DBconnection.getInstance().getCnx();
    }

    public void addInAppNotification(int id, int carpoolingID,String message) {
        String sql = "INSERT INTO notifications (user_id, carpooling_id , message, timestamp) VALUES (?, ?, ?, ?)";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.setInt(2, carpoolingID);
            pstmt.setString(3, message);
            pstmt.setTimestamp(4, Timestamp.valueOf(LocalDateTime.now()));
            pstmt.executeUpdate();
            System.out.println("In-app notification added successfully for user: " + id);
        } catch (SQLException e) {
            System.out.println("Error adding in-app notification: " + e.getMessage());
        }
    }

    public List<String> getInAppNotifications(User user) {
        List<String> notifications = new ArrayList<>();
        String sql = "SELECT message FROM notifications WHERE user_id = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(sql);
            pstmt.setInt(1, user.getId());
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                notifications.add(rs.getString("message"));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching in-app notifications: " + e.getMessage());
        }
        return notifications;
    }


    public boolean checkPendingNotifications(User user) {
        List<String> notifications = getInAppNotifications(user);
        return !notifications.isEmpty(); // Return true if there are notifications, false otherwise
    }
    public void deleteNotification(int userId, int carpoolingId) {
        String sql = "DELETE FROM notifications WHERE user_id = ? AND carpooling_id = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(sql);
            pstmt.setInt(1, userId);
            pstmt.setInt(2, carpoolingId);
            pstmt.executeUpdate();
            System.out.println("Notification deleted for user: " + userId + ", carpooling: " + carpoolingId);
        } catch (SQLException e) {
            System.out.println("Error deleting notification: " + e.getMessage());
        }
    }

    public void deleteWaitlist(int carpoolingId, int userId) {
        String sql = "DELETE FROM waitlist WHERE carpooling_id = ? AND user_id = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(sql);
            pstmt.setInt(1, carpoolingId);
            pstmt.setInt(2, userId);
            pstmt.executeUpdate();
            System.out.println("Waitlist deleted for user: " + userId + ", carpooling: " + carpoolingId);
        } catch (SQLException e) {
            System.out.println("Error deleting waitlist: " + e.getMessage());
        }
    }

    public int getByUserId(int userId) {
        int carpoolingId = -1; // Default value indicating no carpooling found

        String sql = "SELECT carpooling_id FROM notifications WHERE user_id = ?";
        try {
            PreparedStatement pstmt = cnx.prepareStatement(sql);
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                carpoolingId = rs.getInt("carpooling_id");
            }
        } catch (SQLException e) {
            System.out.println("Error fetching carpooling ID from notifications: " + e.getMessage());
        }

        return carpoolingId;
    }

    @Override
    public void add(User user) {
        // cryptage of password
        String req = "INSERT INTO `user`(`first_name`, `last_name`, `email`, `password`, `phone_number`, `picture`, `roles`,`verified`) VALUES (?,?,?,?,?,?,?,?)";
        try {
            PreparedStatement stm  = cnx.prepareStatement(req);
            stm.setString(1,user.getFirst_name());
            stm.setString(2,user.getLast_name());
            stm.setString(3,user.getEmail());
            stm.setString(4,user.getPassword());
            stm.setString(5,user.getPhone_number());
            stm.setString(6,user.getPicture());
            stm.setString(7,"[\"ROLE_USER\"]");
            stm.setBoolean(8,false);
            accountVerif(user);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public  void sendEmail( String to, String subject, String body) {

        final String from = "malek.abdellaoui@esprit.tn";

        final String password = "Tamtouma19102001";

        String host = "smtp.office365.com";
        String port = "587"; // Port for TLS/STARTTLS

        // Email properties
        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);

        // Authenticate the sender using a javax.mail.Authenticator
        Session session = Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Create a MimeMessage object
            Message message = new MimeMessage(session);

            // Set the sender and recipient email addresses
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));

            // Set email subject and content
            message.setSubject(subject);
            message.setText(body);

            // Send the email
            Transport.send(message);

            System.out.println("Email sent successfully!");
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }


    public void  accountVerif(User user){
        String chars = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        StringBuilder code = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(chars.length());
            code.append(chars.charAt(index));
        }
        System.out.println(code);

        verificationCodeOfUser = code.toString();
        sendEmail(user.getEmail(),"verification de compte", "Dear "+user.getFirst_name()+"\n welcome to our website \n your activation code is "+code);

    }




    public void updateVerifAccount(String email ) {
        String req ="UPDATE `user` SET `verified`=true  WHERE `email`='"+email+"'";

        System.out.println(req);
        try {
            PreparedStatement stm  = cnx.prepareStatement(req);
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
    public int getOneByToken(String token) {
        int result = -1;
        try {
            String req = "SELECT `user_id` FROM `reset_password_request` WHERE `selector`=?";
            PreparedStatement ps = cnx.prepareStatement(req);
            ps.setString(1, token);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                result = rs.getInt(1);
            }
        } catch (SQLException ex) {
            System.out.println("Failed to get Token: " + ex.getMessage());
        }

        return result;
    }

    @Override
    public List<User> getAll() {
        String sql = "select * from user";

        List<User> users = new ArrayList<>();

        try {
            Statement statement = cnx.createStatement();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getInt(1));
                user.setFirst_name(resultSet.getString(2));
                user.setLast_name(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setPhone_number(resultSet.getString(6));
                user.setPicture(resultSet.getString(7));
                String roleName = resultSet.getString(8);
                if(user.getRoles().equals("[\"ROLE_USER\"]"))
                    user.setRoles("Client");
                users.add(user);

            }
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return users;
    }

    @Override
    public User getById(int id) {
        String sql = "SELECT * FROM `user` WHERE `id` = ?";
        User user = new User();
        try {
            PreparedStatement pstm = cnx.prepareStatement(sql);
            pstm.setInt(1, id );
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt("id")); // Use the column name "id"
                user.setFirst_name(resultSet.getString(2));
                user.setLast_name(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setPhone_number(resultSet.getString(6));
                user.setPicture(resultSet.getString(7));
                String roleName = resultSet.getString(8);

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
return user;
    }
    public User getByMail(String email) {
        String sql = "SELECT * FROM `user` WHERE `email` = ?";
        User user = new User();
        try {
            PreparedStatement pstm = cnx.prepareStatement(sql);
            pstm.setString(1, email );
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                user.setId(resultSet.getInt(email));
                user.setFirst_name(resultSet.getString(2));
                user.setLast_name(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setPhone_number(resultSet.getString(6));
                user.setPicture(resultSet.getString(7));
                String roleName = resultSet.getString(8);

            }


        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return user;
    }

    @Override
    public void update(User user) {

        String req ="UPDATE `user` SET  `first_name`= ?,`last_name`=? ,`email`=?,`password`=?,`phone_number`=?,`picture`=?,`roles`=? WHERE `id`=?";
        try {
            PreparedStatement stm  = cnx.prepareStatement(req);
            stm.setString(1,user.getFirst_name());
            stm.setString(2,user.getLast_name());
            stm.setString(3,user.getEmail());
            stm.setString(4,user.getPassword());
            stm.setString(5,user.getPhone_number());
            stm.setString(6,user.getPicture());
            stm.setString(7,user.getRoles());
            stm.setInt(8,user.getId());
            stm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(User user) {
        String req = "delete from user where id = ?";
        try {
            PreparedStatement pstm = cnx.prepareStatement(req);
            pstm.setInt(1, user.getId());
            pstm.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());        }

    }
    public String getUserPhoneNumber(int userId) {
        String phoneNumber = null;
        try {
            String sql = "SELECT phone_number FROM `user` WHERE id = ?";
            PreparedStatement pstm = cnx.prepareStatement(sql);
            pstm.setInt(1, userId);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                phoneNumber = resultSet.getString("phone_number");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return phoneNumber;
    }


    public User login(String email, String password) {
        // Validate email and password
        if (!isValidEmail(email) || password.isEmpty()) {
            return null;
        }

        String sql = "SELECT * FROM user WHERE email = ?";
        try (PreparedStatement pstmt = cnx.prepareStatement(sql)) {
            User user = new User();
            pstmt.setString(1, email);
            ResultSet resultSet = pstmt.executeQuery();

            if (resultSet.next()) {
                // Print user data to debug
                System.out.println("User found in database");

                user.setId(resultSet.getInt(1));
                user.setFirst_name(resultSet.getString(2));
                user.setLast_name(resultSet.getString(3));
                user.setEmail(resultSet.getString(4));
                user.setPassword(resultSet.getString(5));
                user.setPhone_number(resultSet.getString(6));
                user.setPicture(resultSet.getString(7));

                // Retrieve user's role from database
                String roleName = resultSet.getString("roles");
                // Assuming roles are stored as a JSON array string (e.g., ["ROLE_ADMIN", "ROLE_USER"])
                user.setRoles(roleName);

                // Check if password matches
                boolean passwordMatches = BCrypt.checkpw(password, user.getPassword());
                if (passwordMatches) {
                    // Check if user is verified
                    boolean verified = resultSet.getBoolean("verified");
                    if (verified) {
                        System.out.println("User is verified");
                        SessionUser.loggedUser = user;
                        System.out.println("logged user"+SessionUser.loggedUser);
                        return user; // Login successful
                    } else {
                        System.out.println("User is not verified");
                        // You may want to handle this case differently
                        return null;
                    }
                } else {
                    System.out.println("Incorrect password");
                    return null; // Incorrect password
                }
            } else {
                System.out.println("User not found");
                return null; // User not found
            }
        } catch (SQLException e) {
            System.out.println("Error during login: " + e.getMessage());
            return null; // Error during login
        }
    }


    public static boolean isValidEmail(String email) {
        // Regular expression for email validation
        String EMAIL_REGEX = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    public String getUserEmailById(int id) {
        String sql = "SELECT email FROM `user` WHERE `id` = ?";
        String userEmail = null;
        try {
            PreparedStatement pstm = cnx.prepareStatement(sql);
            pstm.setInt(1, id);
            ResultSet resultSet = pstm.executeQuery();
            if (resultSet.next()) {
                userEmail = resultSet.getString("email");
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return userEmail;
    }

}














