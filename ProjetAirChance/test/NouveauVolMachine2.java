/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BD.DBManager;
import DAL.ExportDAL;
import DAL.ImportDAL;
import Tables.Vol;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author quentinblondel
 */
public class NouveauVolMachine2 {
    
    private DBManager manager;
    
    public NouveauVolMachine2() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void lectureVols() throws SQLException {
        try {
            manager.dbConnect();
            manager.changeAutocommit(false);
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);
            
            ArrayList<Vol> vols = new ArrayList<>();
            ImportDAL dal = new ImportDAL();
            vols = dal.importTableVol();
            
            manager.commit();
            manager.dbDisconnect();
        } catch (ClassNotFoundException ex) {
            Assert.assertFalse("There was an Exception"+ex,true);
            Logger.getLogger(NouveauVolMachine1.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
