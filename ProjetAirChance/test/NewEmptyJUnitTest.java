/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BD.DBManager;
import DAL.ExportDAL;
import DAL.ImportDAL;
import Tables.AvionFret;
import Tables.AvionPassager;
import Tables.Place;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
        ImportDAL dal = new ImportDAL();
        int nbPlaces = dal.importPlaceWithParameter(0, 7, "", "", "").size();
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

    @Test
    public void exportFret()
    {
        manager.dbConnect();
        
        try
        {
            manager.changeAutocommit(true);
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);

            AvionFret avion = new AvionFret(0, "Falcon900", 500, 500, 1);
            
            ExportDAL dal = new ExportDAL();
            dal.exportAvionFret(avion);
            
            manager.commit();
            manager.dbDisconnect();

        } catch (SQLException ex)
        {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export an avionFret", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export an avionFret", true);
        } catch (Exception ex)
        {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export an avionFret", true);
        }
    }
    
    @Test
    public void exportPasager()
    {
        manager.dbConnect();
        
        try
        {
            manager.changeAutocommit(true);
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);

            AvionPassager avion = new AvionPassager(0, "Falcon900", 3,0,0,1);
            
            ArrayList<Place> p = new ArrayList<>();
            for(int i=0;i<3;i++)
            {
                p.add(new Place(i,"couloir","eco",false));
            }
            avion.setPlaces(p);
            
            ExportDAL dal = new ExportDAL();
            dal.exportAvionPassager(avion);
            
            manager.commit();
            manager.dbDisconnect();

        } catch (SQLException ex)
        {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export an avionPassager", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export an avionPassager", true);
        } catch (Exception ex)
        {
            Logger.getLogger(NewEmptyJUnitTest.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export an avionPassager", true);
        }
    }

}
