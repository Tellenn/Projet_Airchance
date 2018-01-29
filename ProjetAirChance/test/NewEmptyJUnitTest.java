/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BD.DBManager;
import DAL.ImportDAL;
import java.sql.Connection;
import java.sql.SQLException;
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
 * @author paul
 */
public class NewEmptyJUnitTest
{

    private DBManager manager;

    public NewEmptyJUnitTest()
    {
    }

    @BeforeClass
    public static void setUpClass()
    {
    }

    @AfterClass
    public static void tearDownClass()
    {
    }

    @Before
    public void setUp()
    {

    }

    @After
    public void tearDown() throws SQLException
    {
        DBManager.dbDisconnect();
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    @Test
    public void getAllPlaces()
    {
        DBManager.dbConnect();
        int nbPlaces = ImportDAL.importPlaceWithParameter(0, 7, "", "", "").size();
        System.out.println(nbPlaces);
        assertTrue("Error wrong number of places for plane 7", nbPlaces == 150);
        try
        {
            DBManager.dbDisconnect();
        } catch (SQLException ex)
        {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void changeAutoCommit()
    {
        DBManager.dbConnect();
        try
        {
            manager.changeAutocommit(false);
            DBManager.dbDisconnect();
        } catch (SQLException ex)
        {
            assertFalse("Error while changing autocommit", true);
        }
    }

    @Test
    public void tryCommit()
    {
        DBManager.dbConnect();
        try
        {
            manager.changeAutocommit(false);
            manager.commit();
            DBManager.dbDisconnect();
        } catch (SQLException ex)
        {
            assertFalse("Error while trying commit", true);
        }
    }

    @Test
    public void tryChangeIsolationLevel()
    {
        DBManager.dbConnect();
        try
        {
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);
            DBManager.dbDisconnect();
        } catch (SQLException ex)
        {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying changing the isolation level", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
