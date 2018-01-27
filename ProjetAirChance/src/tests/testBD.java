/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tests;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import BD.DBManager;
import Tables.Place;
import Tables.TableImpl;

/**
 *
 * @author paul
 */
public class testBD
{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        DBManager manager = new DBManager();
        manager.dbConnect();
        System.out.println("HELLO");
        System.out.println(Place.importTableWithParameter(0, 7, "", "", "").size());
        try
        {
            manager.dbDisconnect();
        } catch (SQLException ex)
        {
            Logger.getLogger(testBD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
