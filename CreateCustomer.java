import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.swing.JPanel;

public class CreateCustomer {

    // Database connection variables
    private Connection conn = null;
    private final String dbConnect = "jdbc:mysql://localhost:3306/cardata";
    private final String dbUserName = "root";
    private final String dbPass = "root";

    public JFrame frame;

    // Declare text areas for username and password
    private JTextArea txtUsername;
    private JPasswordField txtPassword; // Changed from JTextArea to JPasswordField

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    CreateCustomer window = new CreateCustomer();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public CreateCustomer() {
        try {
            conn = DriverManager.getConnection(dbConnect, dbUserName, dbPass);
            System.out.println("Connection successful");
        } catch (SQLException e) {
            System.out.println("An error has occurred during connection");
            e.printStackTrace();
        }
        initialize();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 500, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel("Create New Customer Page");
        lblNewLabel.setBounds(110, 31, 186, 13);
        frame.getContentPane().add(lblNewLabel);

        JLabel lblFirstName = new JLabel("First Name ");
        lblFirstName.setBounds(57, 90, 79, 13);
        frame.getContentPane().add(lblFirstName);

        JLabel lblLastName = new JLabel("Last Name ");
        lblLastName.setBounds(57, 140, 79, 13);
        frame.getContentPane().add(lblLastName);

        JLabel lblBirthDate = new JLabel("Birth Date ");
        lblBirthDate.setBounds(57, 187, 79, 13);
        frame.getContentPane().add(lblBirthDate);

        JLabel lblDLN = new JLabel("Drivers License");
        lblDLN.setBounds(56, 238, 104, 13);
        frame.getContentPane().add(lblDLN);

        JLabel lblEmailAddress = new JLabel("Email Address");
        lblEmailAddress.setBounds(56, 370, 104, 13);
        frame.getContentPane().add(lblEmailAddress);

        JLabel lblPhoneNumber = new JLabel("Phone Number");
        lblPhoneNumber.setBounds(56, 405, 104, 27);
        frame.getContentPane().add(lblPhoneNumber);

        JTextArea txtFirstName = new JTextArea();
        txtFirstName.setBounds(176, 84, 187, 27);
        frame.getContentPane().add(txtFirstName);

        JTextArea txtLastName = new JTextArea();
        txtLastName.setBounds(176, 134, 187, 25);
        frame.getContentPane().add(txtLastName);

        JTextArea txtBirthDate = new JTextArea();
        txtBirthDate.setText("0000-00-00");
        txtBirthDate.setBounds(176, 181, 187, 27);
        frame.getContentPane().add(txtBirthDate);

        JTextArea txtDLN = new JTextArea();
        txtDLN.setText("000000000000000000");
        txtDLN.setBounds(176, 232, 187, 27);
        frame.getContentPane().add(txtDLN);

        JTextArea txtEmailAddress = new JTextArea();
        txtEmailAddress.setBounds(173, 364, 190, 26);
        frame.getContentPane().add(txtEmailAddress);

        JTextArea txtPhoneNumber = new JTextArea();
        txtPhoneNumber.setText("0000000000");
        txtPhoneNumber.setBounds(170, 406, 196, 27);
        frame.getContentPane().add(txtPhoneNumber);

        // Username field
        txtUsername = new JTextArea();
        txtUsername.setBounds(176, 271, 187, 27);
        frame.getContentPane().add(txtUsername);

        // Password field - Changed to JPasswordField
        txtPassword = new JPasswordField();
        txtPassword.setBounds(176, 319, 187, 27);
        frame.getContentPane().add(txtPassword);

        JButton btnCreateCustomer = new JButton("Create");
        btnCreateCustomer.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Get the values from text fields
                String firstName = txtFirstName.getText();
                String lastName = txtLastName.getText();
                String birthDate = txtBirthDate.getText();
                String dln = txtDLN.getText();
                String username = txtUsername.getText();

                // Get password from JPasswordField (converting char[] to String)
                String password = new String(txtPassword.getPassword());  // Convert char[] to String

                String emailAddress = txtEmailAddress.getText();
                String phoneNumber = txtPhoneNumber.getText();

                // Validate empty fields
                if (firstName.isEmpty() || lastName.isEmpty() || birthDate.isEmpty() || dln.isEmpty() ||
                        username.isEmpty() || password.isEmpty() || emailAddress.isEmpty() || phoneNumber.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "All fields must be filled out.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;  // Stop further processing if any field is empty
                }

                // Validate first and last names (only alphabetic characters and spaces allowed)
                if (!isValidName(firstName)) {
                    JOptionPane.showMessageDialog(frame, "First name can only contain letters and spaces.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;  // Stop further processing if name is invalid
                }

                if (!isValidName(lastName)) {
                    JOptionPane.showMessageDialog(frame, "Last name can only contain letters and spaces.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;  // Stop further processing if name is invalid
                }

                // Validate email address
                if (!isValidEmail(emailAddress)) {
                    JOptionPane.showMessageDialog(frame, "Invalid email address. Please ensure it contains an '@'.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;  // Stop further processing if email is invalid
                }

                // Validate DLN (should be exactly 18 digits long and contain only digits)
                if (!isValidDLN(dln)) {
                    JOptionPane.showMessageDialog(frame, "Driver's License Number must be exactly 18 digits.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;  // Stop further processing if DLN is invalid
                }

                // Call the method to insert data into the database
                insertCustomerData(firstName, lastName, birthDate, dln, username, password, emailAddress, phoneNumber);
            }
        });

        btnCreateCustomer.setBounds(85, 475, 85, 21);
        frame.getContentPane().add(btnCreateCustomer);

        JButton btnCancel = new JButton("Cancel");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new instance of CreateCustomer
                Dashboard createDashboard = new Dashboard();
                // Make the CreateCustomer window visible
                createDashboard.frame.setVisible(true);
                // Optionally, hide the current Dashboard window
                frame.setVisible(false);
            }
        });
        btnCancel.setBounds(225, 475, 85, 21);
        frame.getContentPane().add(btnCancel);

        JLabel lblPassword = new JLabel("Password");
        lblPassword.setBounds(56, 325, 104, 13);
        frame.getContentPane().add(lblPassword);

        JLabel lblUsername = new JLabel("Username");
        lblUsername.setBounds(56, 277, 104, 13);
        frame.getContentPane().add(lblUsername);
        
        JPanel panel = new JPanel();
        panel.setBounds(32, 54, 366, 411);
        frame.getContentPane().add(panel);
    }

    /**
     * Insert customer data into the database.
     */
    private void insertCustomerData(String firstName, String lastName, String birthDate, String dln, String username, String password, String emailAddress, String phoneNumber) {
        // Query to fetch the maximum CUST_ID
        String selectMaxIdQuery = "SELECT MAX(CUST_ID) FROM CUSTOMERS";
        int nextCustId = 0;

        // Fetch the next CUST_ID (max + 1)
        try (PreparedStatement pst = conn.prepareStatement(selectMaxIdQuery);
             ResultSet rs = pst.executeQuery()) {
            if (rs.next()) {
                nextCustId = rs.getInt(1) + 1;  // Increment the maximum CUST_ID by 1
            }
        } catch (SQLException ex) {
            System.out.println("An error occurred while fetching the max CUST_ID.");
            ex.printStackTrace();
            return;  // Stop execution if fetching max CUST_ID failed
        }

        String query = "INSERT INTO CUSTOMERS (CUST_ID, CUST_FST_NM, CUST_LAST_NM, CUST_EMAIL_AD, CUST_PHONE_NO, CUST_BTH_DT, CUST_USR_NM, CUST_DLN, CUST_PWD) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, nextCustId);  // Set the manually incremented CUST_ID
            pst.setString(2, firstName);
            pst.setString(3, lastName);
            pst.setString(4, emailAddress);

            // Check if the phone number is empty. If it is, set it to NULL.
            if (phoneNumber.isEmpty()) {
                pst.setNull(5, java.sql.Types.INTEGER);  // Set to NULL if empty
            } else {
                pst.setString(5, phoneNumber);  // Otherwise, set the phone number
            }

            // Validate birth date (ensure it's not empty and in correct format)
            if (birthDate.isEmpty() || !isValidDate(birthDate)) {
                JOptionPane.showMessageDialog(frame, "Invalid birth date format. Please use YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
                return; // Stop execution if birth date is invalid
            } else {
                pst.setString(6, birthDate);  // Set the birth date
            }

            pst.setString(7, username);  // Set the Username
            pst.setString(8, dln);  // Driver's license number
            pst.setString(9, password);  // Set the Password

            int rowsAffected = pst.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("Customer data inserted successfully.");
                JOptionPane.showMessageDialog(frame, "Customer created successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            } else {
                System.out.println("Failed to insert customer data.");
                JOptionPane.showMessageDialog(frame, "Failed to create customer. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException ex) {
            if (ex instanceof com.mysql.cj.jdbc.exceptions.MysqlDataTruncation) {
                JOptionPane.showMessageDialog(frame, "Invalid date value for birth date. Please enter a valid date in the format YYYY-MM-DD.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                System.out.println("An error occurred while inserting data.");
                ex.printStackTrace();
                JOptionPane.showMessageDialog(frame, "An error occurred while inserting the data. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Validate date format (YYYY-MM-DD).
     */
    private boolean isValidDate(String date) {
        Pattern pattern = Pattern.compile("^\\d{4}-\\d{2}-\\d{2}$");
        Matcher matcher = pattern.matcher(date);
        return matcher.matches();
    }

    /**
     * Validate name (only letters and spaces).
     */
    private boolean isValidName(String name) {
        return name.matches("[a-zA-Z\\s]+");
    }

    /**
     * Validate email address (basic validation to check for '@').
     */
    private boolean isValidEmail(String email) {
        return email.contains("@");
    }

    /**
     * Validate DLN (must be 18 digits).
     */
    private boolean isValidDLN(String dln) {
        return dln.matches("\\d{18}");
    }
}
