import java.awt.EventQueue;
import javax.swing.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.ImageIcon;

public class Dashboard {

    // Database connection variables
    private Connection conn = null;
    private final String dbConnect = "jdbc:mysql://localhost:3307/cardata";
    private final String dbUserName = "root";
    private final String dbPass = "user";

    public JFrame frame;
    private JTable tblVehicle;
    private JTextArea txtSearchVehicle;  // Moved here for scope visibility

    /**
     * Launch the application.
     */
    
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Dashboard window = new Dashboard();
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
    public Dashboard() {
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
        frame.setBounds(100, 100, 800, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JLabel lblNewLabel = new JLabel(new ImageIcon("image/carLogo.jpg"));
        lblNewLabel.setBounds(10, 20, 93, 51);
        frame.getContentPane().add(lblNewLabel);
        
        // Add buttons
        JButton btnGenReport = new JButton("Generate Report");
        btnGenReport.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		RentalReport generateReportWindow = new RentalReport();
        		generateReportWindow.frame.setVisible(true);
        		frame.setVisible(false);
        	}
        });
        btnGenReport.setFont(new Font("Tahoma", Font.PLAIN, 9));
        btnGenReport.setBounds(130, 20, 127, 28);
        frame.getContentPane().add(btnGenReport);

        JButton btnCreateCust = new JButton("Create Customer");
        btnCreateCust.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Create a new instance of CreateCustomer
                CreateCustomer createCustomerWindow = new CreateCustomer();
                // Make the CreateCustomer window visible
                createCustomerWindow.frame.setVisible(true);
                // Optionally, hide the current Dashboard window
                frame.setVisible(false);
            }
        });
        
        btnCreateCust.setFont(new Font("Tahoma", Font.PLAIN, 9));
        btnCreateCust.setBounds(267, 20, 127, 28);
        frame.getContentPane().add(btnCreateCust);

        JButton btnCreateRental = new JButton("Create Rental Agreement");
        btnCreateRental.addActionListener(new ActionListener() {
        	public void actionPerformed(ActionEvent e) {
        		RentalAgreement createRentalAgreementWindow = new RentalAgreement();
        		createRentalAgreementWindow.frame.setVisible(true);
        		frame.setVisible(false);
        	}
        });
        
        btnCreateRental.setFont(new Font("Tahoma", Font.PLAIN, 9));
        btnCreateRental.setBounds(199, 51, 157, 28);
        frame.getContentPane().add(btnCreateRental);

        // Add labels
        JLabel lblSearchVehicle = new JLabel("Search Vehicle (By Car Name)");
        lblSearchVehicle.setBounds(34, 112, 189, 13);
        frame.getContentPane().add(lblSearchVehicle);

        // Search text area
        txtSearchVehicle = new JTextArea();
        txtSearchVehicle.setText("Corolla");
        txtSearchVehicle.setBounds(34, 127, 189, 21);
        frame.getContentPane().add(txtSearchVehicle);

        // Search button
        JButton btnSearch = new JButton("Search");
        btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 9));
        btnSearch.setBounds(240, 127, 85, 21);
        btnSearch.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                searchVehicle(txtSearchVehicle.getText());
            }
        });
        frame.getContentPane().add(btnSearch);

        // Table to display search results with scroll pane
        tblVehicle = new JTable();
        tblVehicle.setModel(new DefaultTableModel(
            new Object[][]{},
            new String[]{"ID", "Manufacturer", "Year", "Model Type", "Doors", "Cylinders", "Auto", "AC", "Purchase Amt", "Buyback Amt", "Model Name", "Car Name"}
        ));

        JScrollPane scrollPaneCust = new JScrollPane(tblVehicle);
        scrollPaneCust.setBounds(34, 172, 729, 163);
        frame.getContentPane().add(scrollPaneCust);
        
        JPanel panel = new JPanel();
        panel.setBounds(10, 97, 766, 256);
        frame.getContentPane().add(panel);
        

    }

 // Method to search for vehicles by car name
    private void searchVehicle(String carName) {
    // check if search input is blank
    if (carName == null || carName.trim().isEmpty()) {
    JOptionPane.showMessageDialog(frame, "Search bar cannot be empty.", "Validation Error", JOptionPane.WARNING_MESSAGE);
            return;
    }
    
        String query = "SELECT * FROM VEHICLE WHERE VCL_CAR_NM = ?";
        try (PreparedStatement pst = conn.prepareStatement(query))  { 
            pst.setString(1, carName);

            ResultSet rs = pst.executeQuery();
            DefaultTableModel model = new DefaultTableModel(
                new String[]{"ID", "Manufacturer", "Year", "ModelName", "Doors", "Cylinders", "Auto", "AC", "PurchaseAmt", "BuybackAmt", "ModelType", "CarName"}, 0);

            boolean found = false; //to check if any records are found
while (rs.next()) {
            found = true; // a record is found
                model.addRow(new Object[]{
                    rs.getInt("VCL_NO_ID"),
                    rs.getString("VCL_MFR_NM"),
                    rs.getInt("VCL_YEAR_NO"),
                    rs.getString("VCL_MODEL_TP_NM"),
                    rs.getInt("VCL_DOORS_NO"),
                    rs.getInt("VCL_CYCLINDERS_NO"),
                    rs.getString("VCL_AUTO_IND"),
                    rs.getString("VCL_AC_IND"),
                    rs.getBigDecimal("VCL_PURCHASE_AMT"),
                    rs.getBigDecimal("VCL_BUYBACK_AMT"),
                    rs.getString("VCL_MODEL_NM"),
                    rs.getString("VCL_CAR_NM")
                });
            }
            
            if (!found) { // If no records are found
                JOptionPane.showMessageDialog(frame, "No vehicles found with the name '" + carName + "'.", "Search Result", JOptionPane.INFORMATION_MESSAGE);
                return;
            }

            tblVehicle.setModel(model);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(frame, "Error fetching data: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}