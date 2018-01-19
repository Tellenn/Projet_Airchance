/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andréas
 */
public class TableImpl {
    
    
    public static void printResultSet(ResultSet rs){
        ResultSetMetaData rsmd = null;
        int columnsNumber = 0;

        try {
            rsmd = rs.getMetaData();
            columnsNumber = rsmd.getColumnCount();
            System.out.println("Results :\n");
            while(rs.next()){
                for(int i = 1; i <= columnsNumber; i++){
                    if (i > 1) System.out.println(",  ");
                    String columnValue = rs.getString(i);
                    System.out.println(columnValue + " "+rsmd.getColumnName(i));
                }
                System.out.println("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
