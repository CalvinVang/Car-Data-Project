import java.awt.EventQueue;
import java.awt.BorderLayout;
import java.sql.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class RentalReport {

    // database connection variables
    private Connection conn = null;
    private final String dbConnect = "jdbc:mysql://localhost:3308/cardata";
    private final String dbUserName = "root";
    private final String dbPass = "root";
    
    public JFrame frame;
    private JTable tblRentalAgreement;
    private JTable tblVehicle;
    private JTable tblPayment;
    private JTable tblInsurance;
    private JTable tblStore;
    private JTable tblAddress;
    private JTable tblCustomer;
    
    private int selectedCustomerId = -1;  // variable to store selected customer ID


    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    RentalReport window = new RentalReport();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public RentalReport() {
        initialize();
        DisplayCust(); // now called once during initialization
    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 843, 881);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        // rental agreement label
        JLabel lblRentalAgreement = new JLabel("Rental Agreement Report");
        lblRentalAgreement.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblRentalAgreement.setBounds(294, 26, 455, 18);
        frame.getContentPane().add(lblRentalAgreement);

        // rental agreement table
        tblRentalAgreement = new JTable();
        tblRentalAgreement.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Start Date", "End Date", "Insurance", "Drop-Off Location", "Price/Day", "Insurance/Day"}
        ));
        JScrollPane scrollPaneRental = new JScrollPane(tblRentalAgreement);
        scrollPaneRental.setBounds(63, 54, 685, 69);
        frame.getContentPane().add(scrollPaneRental);

        // the vehicle label
        JLabel lblVehicle = new JLabel("Vehicle");
        lblVehicle.setBounds(63, 133, 100, 13);
        frame.getContentPane().add(lblVehicle);

        // the vehicle table
        tblVehicle = new JTable();
        tblVehicle.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Manufacturer Name", "Car Name", "Year", "Model Name", "Doors", "Model Type", "Cylinders", "Auto?", "AC", "Purchase Price", "Buyback Price"}
        ));
        JScrollPane scrollPaneVehicle = new JScrollPane(tblVehicle);
        scrollPaneVehicle.setBounds(63, 156, 685, 73);
        frame.getContentPane().add(scrollPaneVehicle);

        JLabel lblPayment = new JLabel("Payment");
        lblPayment.setBounds(63, 239, 100, 13);
        frame.getContentPane().add(lblPayment);

        // the payment Table
        tblPayment = new JTable();
        tblPayment.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Card Number", "Card Expiration Year", "Card Expiration Month", "Card Security Code", "Card Zipcode"}
        ));

        JScrollPane scrollPanePayment = new JScrollPane(tblPayment);
        scrollPanePayment.setBounds(63, 262, 685, 69);
        frame.getContentPane().add(scrollPanePayment);

        JLabel lblInsuranceCompany = new JLabel("Insurance Company");
        lblInsuranceCompany.setBounds(63, 341, 117, 13);
        frame.getContentPane().add(lblInsuranceCompany);

        // the insurance Table
        tblInsurance = new JTable();
        tblInsurance.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Company Name"}
        ));

        JScrollPane scrollPaneInsurance = new JScrollPane(tblInsurance);
        scrollPaneInsurance.setBounds(63, 364, 175, 56);
        frame.getContentPane().add(scrollPaneInsurance);

        JLabel lblStore = new JLabel("Store");
        lblStore.setBounds(63, 430, 117, 13);
        frame.getContentPane().add(lblStore);

        // the store Table
        tblStore = new JTable();
        tblStore.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Division Name", "Region Name", "Country Name"}
        ));

        JScrollPane scrollPaneStore = new JScrollPane(tblStore);
        scrollPaneStore.setBounds(63, 453, 372, 56);
        frame.getContentPane().add(scrollPaneStore);

        JLabel lblAddress = new JLabel("Address");
        lblAddress.setBounds(63, 519, 117, 13);
        frame.getContentPane().add(lblAddress);

        // the address Table
        tblAddress = new JTable();
        tblAddress.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Street Name", "Apart Suite Number", "Post Office Box Number", "City Name", "State Name", "Postal Code", "Region Name", "County Name"}
        ));

        JScrollPane scrollPaneAddress = new JScrollPane(tblAddress);
        scrollPaneAddress.setBounds(63, 542, 685, 73);
        frame.getContentPane().add(scrollPaneAddress);

        // the initialize the customer table
        tblCustomer = new JTable();
        tblCustomer.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"Customer ID", "Customer First Name", "Customer Last Name"}
        ));

        // JScrollPane for the customer table
        JScrollPane scrollPaneCustomer = new JScrollPane(tblCustomer);
        scrollPaneCustomer.setBounds(63, 648, 394, 69);  // Set the appropriate bounds for scrollPaneCustomer
        frame.getContentPane().add(scrollPaneCustomer);

        JLabel lblCustomerID = new JLabel("Customer ID");
        lblCustomerID.setBounds(63, 625, 94, 13);
        frame.getContentPane().add(lblCustomerID);

        JButton btnView = new JButton("Select");
        btnView.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                int row = tblCustomer.getSelectedRow();
                if (row != -1) {
                    selectedCustomerId = (int) tblCustomer.getValueAt(row, 0);  // get the selected customer's ID
                    fillTablesWithCustomerData(selectedCustomerId);  // auto-fill tables
                } else {
                    JOptionPane.showMessageDialog(frame, "Please select a customer", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        btnView.setBounds(136, 727, 129, 49);
        frame.getContentPane().add(btnView);

        JButton btnCancel = new JButton("Back");
        btnCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // create a new instance of Dashboard
                Dashboard createDashboard = new Dashboard();
                // make the Dashboard window visible
                createDashboard.frame.setVisible(true);
                // optionally, hide the current window
                frame.setVisible(false);
            }
        });
        btnCancel.setBounds(316, 727, 129, 49);
        frame.getContentPane().add(btnCancel);
        
        JButton btnExport = new JButton("Save Report");
        btnExport.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                exportReportToPNG();
            }
        });
        btnExport.setBounds(491, 727, 150, 49); // Adjust position and size as needed
        frame.getContentPane().add(btnExport);



    }
    
 // method to display all customer data in the JTable
    private void DisplayCust() {
        // SQL query to fetch all customer data
        String query = "SELECT CUST_ID, CUST_FST_NM, CUST_LAST_NM FROM customers";
        
        try {
            // establish connection
            conn = DriverManager.getConnection(dbConnect, dbUserName, dbPass);
            
            // create the Statement object to execute the query
            Statement stmt = conn.createStatement();
            
            // execute the query and get the result
            ResultSet rs = stmt.executeQuery(query);
            
            // prepare the table model for the customer data
            DefaultTableModel model = (DefaultTableModel) tblCustomer.getModel();
            
            // clear the existing table rows before adding new data
            model.setRowCount(0);
            
            // loop through the result set and add each row to the table model
            while (rs.next()) {
                Object[] row = new Object[3]; 
                row[0] = rs.getInt("CUST_ID");  // customer ID 
                row[1] = rs.getString("CUST_FST_NM");  // first Name 
                row[2] = rs.getString("CUST_LAST_NM");  // last Name
                
                model.addRow(row);  // add the row to the table model
            }
            
            // close the resources
            rs.close();
            stmt.close();
            conn.close();  // close the connection
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error fetching customer data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }
    
    private void fillTablesWithCustomerData(int customerId) {
        try {
            conn = DriverManager.getConnection(dbConnect, dbUserName, dbPass);

            // rental agreement query
            String rentalQuery = "SELECT * FROM cardata.rental_agreement WHERE CUST_ID = ?";
            try (PreparedStatement rentalStmt = conn.prepareStatement(rentalQuery)) {
                rentalStmt.setInt(1, customerId);
                ResultSet rentalRs = rentalStmt.executeQuery();
                DefaultTableModel rentalModel = (DefaultTableModel) tblRentalAgreement.getModel();
                rentalModel.setRowCount(0);
                while (rentalRs.next()) {
                    Object[] row = new Object[6];
                    row[0] = rentalRs.getDate("RENTAL_START_DT");
                    row[1] = rentalRs.getDate("RENTAL_END_DT");
                    row[2] = rentalRs.getString("INSURANCE_IND");
                    row[3] = rentalRs.getString("DROPOFF_STORE_NO");
                    row[4] = rentalRs.getDouble("PPD_AM");
                    row[5] = rentalRs.getDouble("INSURANCE_PPD_AMT");
                    rentalModel.addRow(row);
                }
            }

            // vehicle query
            String vehicleQuery = "SELECT v.* FROM cardata.vehicle v INNER JOIN cardata.rental_agreement ra ON v.VCL_NO_ID = ra.VCL_NO_ID WHERE ra.CUST_ID = ?";
            try (PreparedStatement vehicleStmt = conn.prepareStatement(vehicleQuery)) {
                vehicleStmt.setInt(1, customerId);
                ResultSet vehicleRs = vehicleStmt.executeQuery();
                DefaultTableModel vehicleModel = (DefaultTableModel) tblVehicle.getModel();
                vehicleModel.setRowCount(0);
                while (vehicleRs.next()) {
                    Object[] row = new Object[12];
                    row[0] = vehicleRs.getInt("VCL_NO_ID");
                    row[1] = vehicleRs.getString("VCL_MFR_NM");
                    row[2] = vehicleRs.getString("VCL_CAR_NM");
                    row[3] = vehicleRs.getInt("VCL_YEAR_NO");
                    row[4] = vehicleRs.getString("VCL_MODEL_NM");
                    row[5] = vehicleRs.getInt("VCL_DOORS_NO");
                    row[6] = vehicleRs.getString("VCL_MODEL_TP_NM");
                    row[7] = vehicleRs.getInt("VCL_CYCLINDERS_NO");
                    row[8] = vehicleRs.getString("VCL_AUTO_IND");
                    row[9] = vehicleRs.getString("VCL_AC_IND");
                    row[10] = vehicleRs.getDouble("VCL_PURCHASE_AMT");
                    row[11] = vehicleRs.getDouble("VCL_BUYBACK_AMT");
                    vehicleModel.addRow(row);
                }
            }

            // payment query
            String paymentQuery = "SELECT * FROM cardata.rental_agreement WHERE CUST_ID = ?";
            try (PreparedStatement paymentStmt = conn.prepareStatement(paymentQuery)) {
                paymentStmt.setInt(1, customerId);
                ResultSet paymentRs = paymentStmt.executeQuery();
                DefaultTableModel paymentModel = (DefaultTableModel) tblPayment.getModel();
                paymentModel.setRowCount(0);
                while (paymentRs.next()) {
                    Object[] row = new Object[5];
                    row[0] = paymentRs.getString("CC_NO");
                    row[1] = paymentRs.getInt("CC_EXP_YEAR_NO");
                    row[2] = paymentRs.getInt("CC_EXP_MONTH_NO");
                    row[3] = paymentRs.getInt("CC_SECURITY_CD");
                    row[4] = paymentRs.getString("CC_ZIP_CD");
                    paymentModel.addRow(row);
                }
            }

            // insurance query 
            String insuranceQuery = "SELECT i.INSURANCE_NM, i.INSURANCE_TP " +
                                     "FROM cardata.insurance i " +
                                     "INNER JOIN cardata.rental_agreement ra ON ra.INSURANCE_COMP_ID = i.INSURANCE_COMP_ID " +  // Adjusted join condition
                                     "WHERE ra.CUST_ID = ?";
            try (PreparedStatement insuranceStmt = conn.prepareStatement(insuranceQuery)) {
                insuranceStmt.setInt(1, customerId); // Set the customer ID parameter
                ResultSet insuranceRs = insuranceStmt.executeQuery();
                
                DefaultTableModel insuranceModel = (DefaultTableModel) tblInsurance.getModel();
                insuranceModel.setRowCount(0); // Clear the previous results
                
                while (insuranceRs.next()) {
                    Object[] row = new Object[1]; 
                    row[0] = insuranceRs.getString("INSURANCE_NM"); // Retrieve the insurance name
                    insuranceModel.addRow(row); // Add the result to the table
                }
            }

            // store query 
            String storeQuery = "SELECT * FROM cardata.stores s INNER JOIN cardata.rental_agreement ra ON s.STORE_ID = ra.STORE_ID WHERE ra.CUST_ID = ?";
            try (PreparedStatement storeStmt = conn.prepareStatement(storeQuery)) {
                storeStmt.setInt(1, customerId);
                ResultSet storeRs = storeStmt.executeQuery();
                DefaultTableModel storeModel = (DefaultTableModel) tblStore.getModel();
                storeModel.setRowCount(0);
                while (storeRs.next()) {
                    Object[] row = new Object[3];
                    row[0] = storeRs.getString("STORE_DIVISION_NM");
                    row[1] = storeRs.getString("STORE_REGION_NM");
                    row[2] = storeRs.getString("STORE_COUNTRY_NM");
                    storeModel.addRow(row);
                }
            }
            
            // address query 
            String addressQuery = "SELECT * FROM cardata.address WHERE CUST_ID = ?";
            try (PreparedStatement addressStmt = conn.prepareStatement(addressQuery)) {
                addressStmt.setInt(1, customerId);
                ResultSet addressRs = addressStmt.executeQuery();
                DefaultTableModel addressModel = (DefaultTableModel) tblAddress.getModel();
                addressModel.setRowCount(0);
                while (addressRs.next()) {
                    Object[] row = new Object[8];
                    row[0] = addressRs.getString("AD_ST_NM");
                    row[1] = addressRs.getString("AD_APT_SUITE_NO");
                    row[2] = addressRs.getString("AD_PO_BOX_NO");
                    row[3] = addressRs.getString("AD_CITY_NM");
                    row[4] = addressRs.getString("AD_STATE_NM");
                    row[5] = addressRs.getString("AD_POSTAL_CD");
                    row[6] = addressRs.getString("AD_REGION_NM");
                    row[7] = addressRs.getString("AD_COUNTRY_NM");
                    addressModel.addRow(row);
                }
            }
            
            // if user does not click on customer, error message
            if (tblCustomer.getSelectedRow() == -1) {
                JOptionPane.showMessageDialog(frame, "Please select a customer", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

        // catch exceptions for any errors in fetching data from database
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(frame, "Error fetching data: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            try {
                if (conn != null && !conn.isClosed()) {
                    conn.close(); // Ensure connection is closed
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
       // method to save the customer report and save it to a png file     
       private void exportReportToPNG() {
                try {
                    // Capture the content of the JFrame
                    BufferedImage image = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.TYPE_INT_RGB);
                    Graphics2D graphics = image.createGraphics();
                    
                    // Render the frame to the image
                    frame.paint(graphics);
                    
                    // Save the image to a file (PNG format)
                    File file = new File("image/customer_report.png");
                    ImageIO.write(image, "PNG", file);
                    
                    JOptionPane.showMessageDialog(frame, "Report exported successfully!", "Export Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(frame, "Error exporting report: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
       }
}
            
            
            
            
            
