/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BD.DBManager;
import Tables.Place;
import java.sql.SQLException;
import java.util.ArrayList;
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
        DBManager.dbConnect();
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
        int nbPlaces = Place.importTableWithParameter(0, 7, "", "", "").size();
        System.out.println(nbPlaces);
        assertTrue("Error wrong number of places for plane 7",nbPlaces == 150);
    }
    
    @Test
    public void tryCommit()
    {
        manager.changeAutocommit("off");
        manager.commit();
        
    }
    
}
