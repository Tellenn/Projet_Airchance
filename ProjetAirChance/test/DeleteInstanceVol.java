/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BD.DBManager;
import DAL.ExportDAL;
import DAL.ImportDAL;
import Tables.InstanceVol;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Andréas
 */
public class DeleteInstanceVol {
    
    
      private static Scanner scan;
    private static ImportDAL importDAL;
    private static ExportDAL exportDAL;
    private static DBManager manager;
    
    public DeleteInstanceVol() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        
        try {
            scan = new Scanner(System.in);
            importDAL = new ImportDAL();
            exportDAL = new ExportDAL();
            manager = new DBManager();
            manager.dbConnect();
            manager.dbChangeIsolation(Connection.TRANSACTION_SERIALIZABLE);
            manager.changeAutocommit(false);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ScenarioNouvelleReservation.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse(true);
        }
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void deleteInstanceVol() throws Exception{
        int numInstance = 5;
        InstanceVol iv = new InstanceVol();
        iv.importFromId(""+5);
        exportDAL.deleteInstanceVol(iv);
    }
}
