package DBWorks;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
 

public class DBConnection {
    static String mysJDBCDriver = "com.mysql.jdbc.Driver";
    static String url = "jdbc:mysql://mysql2.cs.stonybrook.edu:3306/ssattar";
    static String username = "ssattar";
    static String password = "108862829";
    static java.sql.Connection myConnection = null;
                   
    static PreparedStatement myPreparedStatement = null;
   
    public DBConnection()
    {}
    
    public static ResultSet ExecQuery(String query){
        ResultSet myResultSet = null;
        try{
            if(myConnection == null || (myConnection !=null && !myConnection.isValid(0)))
            {
                Class.forName(mysJDBCDriver).newInstance();
                myConnection = DriverManager.getConnection(url,username,password);
            }
            myPreparedStatement = myConnection.prepareStatement(query);
            myResultSet = myPreparedStatement.executeQuery();
            
        } catch(ClassNotFoundException e)
        {
        }
        catch (SQLException e){
        } catch (InstantiationException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  myResultSet;
    }
    
    public static int ExecUpdateQuery(String query){
       int retValue = 0;
        try{
            if(myConnection == null || (myConnection !=null && !myConnection.isValid(0)))
            {
                Class.forName(mysJDBCDriver).newInstance();
                myConnection = DriverManager.getConnection(url,username,password);
            }
            myPreparedStatement = myConnection.prepareStatement(query);
            retValue = myPreparedStatement.executeUpdate();
            
        } catch(ClassNotFoundException e)
        {
        }
        catch (SQLException e){
        } catch (InstantiationException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(DBConnection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return  retValue;
    }
}
