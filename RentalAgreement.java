import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.awt.event.ActionEvent;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import java.awt.Component;

public class RentalAgreement {
	
    // Database connection variables
    private Connection conn = null;
    private final String dbConnect = "jdbc:mysql://localhost:3306/cardata";
    private final String dbUserName = "root";
    private final String dbPass = "root";

	public JFrame frame;
    // Declare customer ID, insurance ID, store ID, and vehicle ID variables for the user to input 
    private JTextArea txtCUSTID;
    private JTextArea txtInsuranceID;
    private JTextArea txtStoreID;
    private JTextArea txtVehicleID;
    private JTextField txtSignature;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					RentalAgreement window = new RentalAgreement();
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
	public RentalAgreement() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
        frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblRentalAgree = new JLabel("Rental Agreement");
		lblRentalAgree.setBounds(38, 41, 118, 18);
		frame.getContentPane().add(lblRentalAgree);
		
		JLabel lblStartDate = new JLabel("Start Date");
		lblStartDate.setBounds(48, 69, 64, 13);
		frame.getContentPane().add(lblStartDate);
		
		JTextArea txtStartDate = new JTextArea();
		txtStartDate.setText("2024-12-01");
		txtStartDate.setBounds(46, 92, 144, 22);
		frame.getContentPane().add(txtStartDate);
		
		JLabel lblEndDate = new JLabel("End Date");
		lblEndDate.setBounds(48, 133, 64, 13);
		frame.getContentPane().add(lblEndDate);
		
		JTextArea txtEndDate = new JTextArea();
		txtEndDate.setText("2025-12-01");
		txtEndDate.setBounds(46, 156, 144, 22);
		frame.getContentPane().add(txtEndDate);
		
		JLabel lblInsuranceInd = new JLabel("Insurance Indictator");
		lblInsuranceInd.setBounds(231, 69, 118, 13);
		frame.getContentPane().add(lblInsuranceInd);
		
		JTextArea txtInsuranceInd = new JTextArea();
		txtInsuranceInd.setText("Y");
		txtInsuranceInd.setBounds(231, 92, 144, 22);
		frame.getContentPane().add(txtInsuranceInd);
		
		JLabel lblDropoffLoc = new JLabel("Drop-Off Location Store Number");
		lblDropoffLoc.setBounds(231, 133, 188, 13);
		frame.getContentPane().add(lblDropoffLoc);
		
		JTextArea txtDropOffLoc = new JTextArea();
		txtDropOffLoc.setText("123");
		txtDropOffLoc.setBounds(231, 156, 144, 22);
		frame.getContentPane().add(txtDropOffLoc);
		
		JLabel lblPricePerDay = new JLabel("Price Per Day");
		lblPricePerDay.setBounds(418, 69, 159, 13);
		frame.getContentPane().add(lblPricePerDay);
		
		JTextArea txtPricePerDay = new JTextArea();
		txtPricePerDay.setText("160.00");
		txtPricePerDay.setBounds(418, 92, 144, 22);
		frame.getContentPane().add(txtPricePerDay);
		
		JLabel lblInsurancePricePerDay = new JLabel("Insurance Price Per Day");
		lblInsurancePricePerDay.setBounds(428, 133, 159, 13);
		frame.getContentPane().add(lblInsurancePricePerDay);
		
		JTextArea txtInsurancePricePerDay = new JTextArea();
		txtInsurancePricePerDay.setText("32.00");
		txtInsurancePricePerDay.setBounds(423, 156, 144, 22);
		frame.getContentPane().add(txtInsurancePricePerDay);
		
		JLabel lblVehicle = new JLabel("Vehicle");
		lblVehicle.setBounds(27, 253, 118, 18);
		frame.getContentPane().add(lblVehicle);
		
		JLabel lblPayment = new JLabel("Payment");
		lblPayment.setBounds(628, 23, 118, 18);
		frame.getContentPane().add(lblPayment);
		
		JLabel lblCardNumber = new JLabel("Card Number");
		lblCardNumber.setBounds(628, 62, 118, 13);
		frame.getContentPane().add(lblCardNumber);
		
		JTextArea txtCardNumber = new JTextArea();
		txtCardNumber.setText("2345678901234567");
		txtCardNumber.setBounds(628, 85, 144, 22);
		frame.getContentPane().add(txtCardNumber);
		
		JLabel lblCardExpirationYear = new JLabel("Card Expiration Year");
		lblCardExpirationYear.setBounds(628, 117, 118, 13);
		frame.getContentPane().add(lblCardExpirationYear);
		
		JTextArea txtCardExpirationYear = new JTextArea();
		txtCardExpirationYear.setText("2024");
		txtCardExpirationYear.setBounds(628, 133, 144, 22);
		frame.getContentPane().add(txtCardExpirationYear);
		
		JLabel lblCardExpirationMonth = new JLabel("Card Expiration Month");
		lblCardExpirationMonth.setBounds(628, 173, 118, 13);
		frame.getContentPane().add(lblCardExpirationMonth);
		
		JTextArea txtCardExpirationMonth = new JTextArea();
		txtCardExpirationMonth.setText("12");
		txtCardExpirationMonth.setBounds(628, 196, 144, 22);
		frame.getContentPane().add(txtCardExpirationMonth);
		
		JLabel lblCardSecurityCode = new JLabel("Card Security Code");
		lblCardSecurityCode.setBounds(797, 62, 118, 13);
		frame.getContentPane().add(lblCardSecurityCode);
		
		JLabel lblCardZipcode = new JLabel("Card Zipcode");
		lblCardZipcode.setBounds(797, 117, 118, 13);
		frame.getContentPane().add(lblCardZipcode);
		
		JTextArea txtCardSecurityCode = new JTextArea();
		txtCardSecurityCode.setText("132");
		txtCardSecurityCode.setBounds(797, 85, 144, 22);
		frame.getContentPane().add(txtCardSecurityCode);
		
		JTextArea txtCardZipcode = new JTextArea();
		txtCardZipcode.setText("28678");
		txtCardZipcode.setBounds(797, 133, 144, 22);
		frame.getContentPane().add(txtCardZipcode);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBounds(607, 62, 354, 187);
		frame.getContentPane().add(panel_2);
		
		JLabel lblInsuranceCompany = new JLabel("Insurance Company");
		lblInsuranceCompany.setBounds(281, 253, 138, 18);
		frame.getContentPane().add(lblInsuranceCompany);
		
		JLabel lblVehicleID = new JLabel("Vehicle ID");
		lblVehicleID.setBounds(26, 281, 118, 18);
		frame.getContentPane().add(lblVehicleID);
		
		JTextArea txtCustomerID = new JTextArea();
		txtCustomerID.setBounds(588, 775, 161, 22);
		frame.getContentPane().add(txtCustomerID);
		
		JButton btnView = new JButton("Create");
		btnView.setBounds(588, 820, 85, 21);
		frame.getContentPane().add(btnView);
		
		JButton btnCancelRental = new JButton("Cancel");
		btnCancelRental.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                // Create a new instance of CreateCustomer
                Dashboard createDashboard = new Dashboard();
                // Make the CreateCustomer window visible
                createDashboard.frame.setVisible(true);
                // Optionally, hide the current Dashboard window
                frame.setVisible(false);
			}
		});
		
		btnCancelRental.setBounds(705, 820, 85, 21);
		frame.getContentPane().add(btnCancelRental);
		
		JButton btnSubmitRent = new JButton("Submit");
		btnSubmitRent.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        // Get user input from the form fields
		        String startDate = txtStartDate.getText();  
		        String endDate = txtEndDate.getText();     
		        String insuranceIndicator = txtInsuranceInd.getText(); 
		        String dropOffLocation = txtDropOffLoc.getText();  
		        String pricePerDay = txtPricePerDay.getText();  
		        String insurancePricePerDay = txtInsurancePricePerDay.getText(); 
		        String cardNumber = txtCardNumber.getText();
		        String cardExpirationYear = txtCardExpirationYear.getText(); 
		        String cardExpirationMonth = txtCardExpirationMonth.getText();  
		        String cardSecurityCode = txtCardSecurityCode.getText();  
		        String cardZipcode = txtCardZipcode.getText();  
		        String customerIdText = txtCUSTID.getText();
		        String vehicleIdText = txtVehicleID.getText();  
		        String storeIdText = txtStoreID.getText(); 
		        String insuranceIdText = txtInsuranceID.getText();  
		        String signature = txtSignature.getText();  

		        // Validate the form fields in case the input is empty and display a message stating to fill in
		        if (startDate.isEmpty() || endDate.isEmpty() || cardNumber.isEmpty() || cardSecurityCode.isEmpty() ||
		            customerIdText.isEmpty() || vehicleIdText.isEmpty() || storeIdText.isEmpty() || 
		            insuranceIdText.isEmpty() || signature.isEmpty()) {
		            
		            JOptionPane.showMessageDialog(frame, "Please fill in all the required fields.");
		            return;
		        }

		        // Will have to covert the input into a INT to be used with our database
		        int customerId = Integer.parseInt(customerIdText);  
		        int vehicleId = Integer.parseInt(vehicleIdText); 
		        int storeId = Integer.parseInt(storeIdText); 
		        int insuranceCompanyId = Integer.parseInt(insuranceIdText);  
		        
		        // Call the insertRentalAgreement method to save data to the database
		        insertRentalAgreement(startDate, endDate, customerId, vehicleId, storeId, insuranceCompanyId,
		                              insuranceIndicator, dropOffLocation, pricePerDay, insurancePricePerDay,
		                              cardNumber, cardExpirationYear, cardExpirationMonth, cardSecurityCode, cardZipcode);
		    }
		});


		
		btnSubmitRent.setBounds(215, 426, 85, 21);
		frame.getContentPane().add(btnSubmitRent);
		
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
		
		btnCancel.setBounds(347, 426, 85, 21);
		frame.getContentPane().add(btnCancel);
		
		txtVehicleID = new JTextArea();
		txtVehicleID.setText("100");
		txtVehicleID.setBounds(27, 302, 144, 22);
		frame.getContentPane().add(txtVehicleID);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(17, 271, 231, 70);
		frame.getContentPane().add(panel_1);
		
		JLabel lblCompanyName = new JLabel("Insurance ID");
		lblCompanyName.setBounds(296, 281, 104, 13);
		frame.getContentPane().add(lblCompanyName);
		
		txtInsuranceID = new JTextArea();
		txtInsuranceID.setText("1");
		txtInsuranceID.setBounds(296, 302, 144, 22);
		frame.getContentPane().add(txtInsuranceID);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBounds(277, 271, 231, 70);
		frame.getContentPane().add(panel_3);
		
		JLabel lblCustomerID = new JLabel("Customer ID");
		lblCustomerID.setBounds(196, 385, 104, 13);
		frame.getContentPane().add(lblCustomerID);
		
		txtCUSTID = new JTextArea();
		txtCUSTID.setBounds(321, 379, 187, 27);
		frame.getContentPane().add(txtCUSTID);
		
		txtStoreID = new JTextArea();
		txtStoreID.setText("1");
		txtStoreID.setBounds(575, 302, 144, 22);
		frame.getContentPane().add(txtStoreID);
		
		JLabel lblStoreID = new JLabel("Store ID");
		lblStoreID.setBounds(575, 284, 104, 13);
		frame.getContentPane().add(lblStoreID);
		
		JPanel panel_3_1 = new JPanel();
		panel_3_1.setBounds(559, 271, 231, 70);
		frame.getContentPane().add(panel_3_1);
		
		JLabel lblStore_1 = new JLabel("Store");
		lblStore_1.setBounds(559, 253, 118, 18);
		frame.getContentPane().add(lblStore_1);
		
		JLabel lblSignature = new JLabel("Customer Signature:");
		lblSignature.setBounds(550, 354, 200, 13);
		frame.getContentPane().add(lblSignature);
		
		txtSignature = new JTextField();
		txtSignature.setBounds(548, 377, 300, 30);
		frame.getContentPane().add(txtSignature);
		
		// Will use the button to submit the signature once inputed with the rental agreement
		JButton btnSubmitSignature = new JButton("Submit Signature");
		btnSubmitSignature.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
                String signature = txtSignature.getText();
                if (!signature.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "Signature submitted: " + signature);
                } else {
                    JOptionPane.showMessageDialog(frame, "Please provide a signature.");
                }
			}
		});
		btnSubmitSignature.setBounds(548, 417, 200, 30);
		frame.getContentPane().add(btnSubmitSignature);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBounds(159, 366, 368, 119);
		frame.getContentPane().add(panel_4);
		
		JPanel panel_4_1 = new JPanel();
		panel_4_1.setBounds(537, 366, 368, 119);
		frame.getContentPane().add(panel_4_1);
		
		JPanel panel = new JPanel();
		panel.setBounds(17, 62, 572, 181);
		frame.getContentPane().add(panel);
		
		
		
		
	}
	
	
	private void insertRentalAgreement(String startDate, String endDate, int customerId, int vehicleId,
	        int storeId, int insuranceCompanyId, String insuranceIndicator,
	        String dropOffLocation, String pricePerDay, String insurancePricePerDay,
	        String cardNumber, String cardExpirationYear, String cardExpirationMonth,
	        String cardSecurityCode, String cardZipcode) {

	    // Validate Vehicle ID is not already occupied by another Customer ID
	    if (isVehicleOccupied(vehicleId, startDate, endDate)) {
	        JOptionPane.showMessageDialog(frame, "This vehicle is already rented to another customer during the selected period.");
	        return;
	    }

	    // Validate Vehicle ID with the correct value
	    if (!isValidVehicleId(vehicleId)) {
	        JOptionPane.showMessageDialog(frame, "Invalid Vehicle ID. Please enter a valid vehicle ID.");
	        return;
	    }

	    // Validate Store ID with the correct value
	    if (!isValidStoreId(storeId)) {
	        JOptionPane.showMessageDialog(frame, "Invalid Store ID. Please enter a valid store ID.");
	        return;
	    }

	    // Validate Insurance Company ID
	    if (!isValidInsuranceCompanyId(insuranceCompanyId)) {
	        JOptionPane.showMessageDialog(frame, "Invalid Insurance Company ID. Please enter a valid insurance company ID.");
	        return;
	    }

	    // Get the next rental agreement ID
	    int rentalAgreeId = getNextRentalAgreeId();

	    String sql = "INSERT INTO RENTAL_AGREEMENT (" +
	            "RENTAL_AGREE_ID, RENTAL_START_DT, RENTAL_END_DT, CUST_ID, VCL_NO_ID, STORE_ID, INSURANCE_COMP_ID, " +
	            "INSURANCE_IND, DROPOFF_STORE_NO, PPD_AM, INSURANCE_PPD_AMT, CC_NO, CC_EXP_YEAR_NO, CC_EXP_MONTH_NO, " +
	            "CC_SECURITY_CD, CC_ZIP_CD) " +
	            "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

	    try (Connection conn = DriverManager.getConnection(dbConnect, dbUserName, dbPass);
	         PreparedStatement pst = conn.prepareStatement(sql)) {

	        pst.setInt(1, rentalAgreeId);  // RENTAL_AGREE_ID
	        pst.setString(2, startDate);    // RENTAL_START_DT
	        pst.setString(3, endDate);      // RENTAL_END_DT
	        pst.setInt(4, customerId);      // CUST_ID
	        pst.setInt(5, vehicleId);       // VCL_NO_ID
	        pst.setInt(6, storeId);         // STORE_ID
	        pst.setInt(7, insuranceCompanyId); // INSURANCE_COMP_ID
	        pst.setString(8, insuranceIndicator);  // INSURANCE_IND
	        pst.setString(9, dropOffLocation); // DROPOFF_STORE_NO
	        pst.setString(10, pricePerDay);  // PPD_AM
	        pst.setString(11, insurancePricePerDay); // INSURANCE_PPD_AMT
	        pst.setString(12, cardNumber);  // CC_NO
	        pst.setString(13, cardExpirationYear); // CC_EXP_YEAR_NO
	        pst.setString(14, cardExpirationMonth); // CC_EXP_MONTH_NO
	        pst.setString(15, cardSecurityCode); // CC_SECURITY_CD
	        pst.setString(16, cardZipcode); // CC_ZIP_CD

	        pst.executeUpdate();  // Execute the insert statement
	        JOptionPane.showMessageDialog(frame, "Rental Agreement Created Successfully!");

	    } catch (SQLException e) {
	        e.printStackTrace();
	        JOptionPane.showMessageDialog(frame, "Error occurred while creating rental agreement: " + e.getMessage());
	    }
	}

	private boolean isVehicleOccupied(int vehicleId, String startDate, String endDate) {
	    String query = "SELECT COUNT(*) FROM RENTAL_AGREEMENT " +
	                   "WHERE VCL_NO_ID = ? AND " +
	                   "((RENTAL_START_DT <= ? AND RENTAL_END_DT >= ?) OR " +
	                   "(RENTAL_START_DT <= ? AND RENTAL_END_DT >= ?))";  // Check overlapping rental periods

	    try (Connection conn = DriverManager.getConnection(dbConnect, dbUserName, dbPass);
	         PreparedStatement pst = conn.prepareStatement(query)) {

	        pst.setInt(1, vehicleId);
	        pst.setString(2, endDate);      // End Date (used for checking overlap)
	        pst.setString(3, startDate);    // Start Date (used for checking overlap)
	        pst.setString(4, startDate);    // Start Date (used for checking overlap)
	        pst.setString(5, endDate);      // End Date (used for checking overlap)

	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0;  // If count > 0, vehicle is already rented in the specified period
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;  // If no match, the vehicle is available
	}
 
		
	private boolean isValidVehicleId(int vehicleId) {
			String query = "SELECT COUNT(*) FROM VEHICLE WHERE VCL_NO_ID = ? AND VCL_NO_ID BETWEEN 1 AND 3189";
			try (Connection conn = DriverManager.getConnection(dbConnect, dbUserName, dbPass);
			PreparedStatement pst = conn.prepareStatement(query)) {
			
			pst.setInt(1, vehicleId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
			int count = rs.getInt(1);
			return count > 0;  // If the count is greater than 0, the vehicle ID exists in the database
			}
			
			} catch (SQLException e) {
			e.printStackTrace();
			}
			return false;
			}
		
	private boolean isValidStoreId(int storeId) {
			String query = "SELECT COUNT(*) FROM STORES WHERE STORE_ID = ?";
			try (Connection conn = DriverManager.getConnection(dbConnect, dbUserName, dbPass);
			PreparedStatement pst = conn.prepareStatement(query)) {
			
			pst.setInt(1, storeId);
			ResultSet rs = pst.executeQuery();
			if (rs.next()) {
			int count = rs.getInt(1);
			return count > 0;  // If the count is greater than 0, the store ID exists in the database
			}
			
			} catch (SQLException e) {
			e.printStackTrace();
			}
			return false;
			}
			
	private boolean isValidInsuranceCompanyId(int insuranceCompanyId) {
	    String query = "SELECT COUNT(*) FROM INSURANCE WHERE INSURANCE_COMP_ID = ?";
	    try (Connection conn = DriverManager.getConnection(dbConnect, dbUserName, dbPass);
	         PreparedStatement pst = conn.prepareStatement(query)) {
	        
	        pst.setInt(1, insuranceCompanyId);
	        ResultSet rs = pst.executeQuery();
	        if (rs.next()) {
	            int count = rs.getInt(1);
	            return count > 0;
	        }
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	    return false;
	}

	private int getNextRentalAgreeId() {
		    int maxRentalAgreeId = 0;
		    
		    // Query to get the highest RENTAL_AGREE_ID from the database
		    String query = "SELECT MAX(RENTAL_AGREE_ID) FROM RENTAL_AGREEMENT";
		    
		    try {
		        // Establish a connection to the database
		        conn = DriverManager.getConnection(dbConnect, dbUserName, dbPass);
		        
		        // Create a statement and execute the query
		        PreparedStatement pst = conn.prepareStatement(query);
		        var rs = pst.executeQuery();
		        
		        if (rs.next()) {
		            maxRentalAgreeId = rs.getInt(1);  // Get the maximum ID
		        }
		        
		        // Close the result set and statement
		        rs.close();
		        pst.close();
		        conn.close();
		        
		    } catch (SQLException e) {
		        e.printStackTrace();
		    }
		    
		    // Return the next rental agreement ID by incrementing the maximum value
		    return maxRentalAgreeId + 1;
		}
	}



