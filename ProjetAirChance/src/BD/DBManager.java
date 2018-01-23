package BD;


import com.sun.rowset.CachedRowSetImpl;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DBManager {

    private static final String configurationFile = "BD.properties";
    
    private static Connection conn = null;
    
    private static String jdbcDriver, dbUrl, username, password;
    
    /**
     * Establish a connection to the Oracle database
     */
    public static void dbConnect(){
        try {
            DatabaseAccessProperties dap = new DatabaseAccessProperties(configurationFile);            
            jdbcDriver = dap.getJdbcDriver();
            dbUrl = dap.getDatabaseUrl();
            username = dap.getUsername();
            System.out.println(jdbcDriver);            
            password = dap.getPassword();
            
            // Load the database driver
            Class.forName(jdbcDriver);// Get a connection to the database
            
            //Establish the Oracle Connection using Connection String
            try {
                
                conn = DriverManager.getConnection(dbUrl, username, password);
                System.out.println("Connexion OK");                
                
                // Print information about connection warnings
                SQLWarningsExceptions.printWarnings(conn);

            } catch (SQLException e) {
                System.out.println("Connection Failed! Check output console" + e);
                e.printStackTrace();
                throw e;
            } 
        } catch (Exception e) {
            System.err.println("Exception: " + e.getMessage());
            e.printStackTrace();
        }
        
    }
    
    /**
     * Disconnect from the Oracle database
     * @throws SQLException 
     */
    public static void dbDisconnect() throws SQLException {
        try {
            if (conn != null && !conn.isClosed()) {
                conn.close();
            }
        } catch (Exception e){
           throw e;
        }
    }
    
    //DB Execute Query Operation
    /**
     * Execute a query
     * @param queryStmt
     * @return a ResultSet of the query
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static ResultSet dbExecuteQuery(String queryStmt) throws SQLException, ClassNotFoundException {
        //Declare statement, resultSet and CachedResultSet as null
        Statement stmt = null;
        ResultSet resultSet = null;
        CachedRowSetImpl crs = null;
        try {
            //Connect to DBManager (Establish Oracle Connection)
            dbConnect();
            System.out.println("Select statement: " + queryStmt + "\n");

            //Create statement
            stmt = conn.createStatement();

            //Execute select (query) operation
            resultSet = stmt.executeQuery(queryStmt);

            //CachedRowSet Implementation
            //In order to prevent "java.sql.SQLRecoverableException: Closed Connection: next" error
            //We are using CachedRowSet
            crs = new CachedRowSetImpl();
            crs.populate(resultSet);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeQuery operation : " + e);
            throw e;
        } finally {
            if (resultSet != null) {                
                //Close resultSet
                resultSet.close();
            }
            if (stmt != null) {
                //Close Statement
                stmt.close();  
            }
            //Close connection
            dbDisconnect();
        }
        //Return CachedRowSet
        return crs;
    }
    
    /**
     * Execute a query update (UPDATE/INSERT/DELETE)
     * @param sqlStmt
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static void dbExecuteUpdate(String sqlStmt) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        try {
            //Connect to DBManager (Establish Oracle Connection)
            dbConnect();
            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate(sqlStmt);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }
    
    /**
     * Execute a change in the isolation level
     * @param sqlStmt
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public static void dbChangeIsolation(String level) throws SQLException, ClassNotFoundException {
        //Declare statement as null
        Statement stmt = null;
        try {
            //Connect to DBManager (Establish Oracle Connection)
            dbConnect();
            //Create Statement
            stmt = conn.createStatement();
            //Run executeUpdate operation with given sql statement
            stmt.executeUpdate("SET TRANSACTION ISOLATION LEVEL " + level);
        } catch (SQLException e) {
            System.out.println("Problem occurred at executeUpdate operation : " + e);
            throw e;
        } finally {
            if (stmt != null) {
                //Close statement
                stmt.close();
            }
            //Close connection
            dbDisconnect();
        }
    }
    
}