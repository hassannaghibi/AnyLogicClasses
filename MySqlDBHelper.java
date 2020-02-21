/**
 * MySqlDBHelper
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.mysql.jdbc.Driver;

public class MySqlDBHelper implements Serializable {

    //  Database credentials
    private String DBName = "";
    private String DBServer = "";
    private String USER = "";
    private String PASS = "";

    private java.sql.Connection conn = null;
    private java.sql.Statement stmt = null;


    public MySqlDBHelper(String DBServer, String DBName, String USER, String PASS) {
        this.DBServer = DBServer;
        this.DBName = DBName;
        this.USER = USER;
        this.PASS = PASS;
        //STEP 2: Register JDBC driver
        try {
        	Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            Logger.getLogger(MySqlDBHelper.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public void open() {
        try {
        	String DB_URL = "jdbc:mysql://"+DBServer+"/"+ DBName;
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void close() {
        try {
            stmt.close();
            conn.close();
        } catch (SQLException ex) {
            Logger.getLogger(MySqlDBHelper.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    public List<List<String>> select_sim_output_daily() {
        try {
            List<List<String>> result = new ArrayList();

            java.sql.ResultSet rs = stmt.executeQuery("SELECT * FROM sim_output_daily"); // DML
            // stmt.executeUpdate(sql); // DDL

            //STEP 5: Extract data from result set
            int i = 0; 
            while (rs.next()) {
                //Display values
                List<String> data = new ArrayList();
                i++;
                data.add("Index = " + i);

                data.add(rs.getString("metric_name"));
                data.add(rs.getString("min_value"));
                data.add(rs.getString("metric_name"));
                data.add(rs.getString("min_value"));
                data.add(rs.getString("q1_value"));
                data.add(rs.getString("median_value"));
                data.add(rs.getString("mean_value"));
                data.add(rs.getString("max_value"));
                data.add(rs.getString("n_value"));
                data.add(rs.getString("nurse_unit"));
                data.add(rs.getString("floor"));
                data.add(rs.getString("facility"));
                data.add(rs.getString("upload_ts"));
                data.add(rs.getString("simulation_day"));
                data.add(rs.getString("shift"));
                
                data.add("****************************");
                
                result.add(data);
            }
            //STEP 6: Clean-up environment
            rs.close();

            return result;

        } catch (Exception ex) {
            Logger.getLogger(MySqlDBHelper.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }

    
    public Boolean insert_sim_output_daily(String metric_name,double min_value,double q1_value,double median_value,double mean_value,double q4_value,double max_value,double n_value,String nurse_unit,int floor,String facility,String upload_ts,int simulation_day,int shift) {
	
    	// for insert a new candidate
        java.sql.ResultSet rs = null;
        Boolean result = false;

        String sql = "INSERT INTO sim_output_daily (metric_name,min_value,q1_value,median_value,mean_value,q4_value,max_value,n_value,nurse_unit,floor,facility,upload_ts,simulation_day,shift) "
                + "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        try (java.sql.PreparedStatement pstmt = conn.prepareStatement(sql, java.sql.Statement.RETURN_GENERATED_KEYS);) {

        	// set parameters for statement
        	
            pstmt.setString(1, metric_name);
            pstmt.setDouble(2, min_value);
            pstmt.setDouble(3, q1_value);
            pstmt.setDouble(4, median_value);
            pstmt.setDouble(5, mean_value);
            pstmt.setDouble(6, q4_value);
            pstmt.setDouble(7, max_value);
            pstmt.setDouble(8, n_value);
            pstmt.setString(9, nurse_unit);
            pstmt.setInt(10, floor);
            pstmt.setString(11, facility);
            pstmt.setString(12, upload_ts);
            pstmt.setInt(13, simulation_day);
            pstmt.setInt(14, shift);

            int rowAffected = pstmt.executeUpdate();
            if (rowAffected == 1) {
            	result = true;
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                if (rs != null) rs.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return result;
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * This number is here for model snapshot storing purpose<br>
     * It needs to be changed when this class gets changed
     */
    private static final long serialVersionUID = 1L;
}
