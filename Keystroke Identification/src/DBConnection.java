/*
 * DBConnection.java
 *
 * Created on 22 íæäíæ, 2008, 01:36 Õ
 *
 * To change this template, choose Tools | Template Manager
 * and open the template in the editor.
 */

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Mohamed Talaat Saad
 */
public class DBConnection {
    
    private static Connection conn;
    private static final String MYSQL_DRIVER = "org.gjt.mm.mysql.Driver";
    private static final String DATABASE_NAME = "keystroke";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASS = "";
    private static final String DATABASE_HOST_IP = "127.0.0.1";
    
    /** Creates a new instance of DBConnection */
    private DBConnection() 
    {
        initConnection();
    }
    public static Connection getDBConnection()
    {
        if(conn == null)
            new DBConnection();
        
        return conn;
        
    }
    public void initConnection()
    {
        try {
            try {
                Class.forName(MYSQL_DRIVER);
                String connectioURL = "jdbc:mysql://" + DATABASE_HOST_IP + "/" +DATABASE_NAME;
                conn = DriverManager.getConnection(connectioURL, DATABASE_USER, DATABASE_PASS);
                
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        
    }
}
