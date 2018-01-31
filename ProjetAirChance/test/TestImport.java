/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BD.DBManager;
import DAL.ExportDAL;
import DAL.ImportDAL;
import Tables.Avion;
import Tables.AvionFret;
import Tables.AvionPassager;
import Tables.Modele;
import Tables.Ville;
import Tables.Vol;
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
 * @author Pault
 */
public class TestImport
{
    private DBManager manager;
    
    public TestImport()
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
    public void tearDown()
    {
        try
        {
            DBManager.dbDisconnect();
        } catch (SQLException ex)
        {
            Logger.getLogger(TestImport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    
    @Test
    public void importAvionFret()
    {
        try
        {
            manager.changeAutocommit(false);
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);
            
            
            
            ImportDAL dal = new ImportDAL();
            Modele m = new Modele();
            m.importFromId("A300");
            AvionFret a = dal.importTableAvionFret(0,m, 0, 0, 0).get(0);
            assertTrue("Error while trying to import an AvionFret",a.getModele().getNomModele().equals("A300"));
            manager.rollBack();
            
        } catch (SQLException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to import an AvionFret", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to import an AvionFret", true);
        } catch (Exception ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to import an AvionFret", true);
        }
    }
    
    @Test
    public void importAvionPassager()
    {
        try
        {
            manager.changeAutocommit(false);
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);
            
            ImportDAL dal = new ImportDAL();
            Modele m = new Modele();
            m.importFromId("Falcon900");
            Ville v = new Ville();
            v.importFromId("1");
            AvionPassager a = dal.importTableAvionPassager(0,m,0, 0, 0, v).get(0);
            assertTrue("Error while trying to import an AvionPassager",a.getModele().getNomModele().equals("Falcon900")
                    &&a.getIdDerniereVille().getNomVille().equals("Paris"));
            
            manager.rollBack();
            
        } catch (SQLException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to import an AvionPassager", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to import an AvionPassager", true);
        } catch (Exception ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to import an AvionPassager", true);
        }
    }
    
    @Test
    public void testAvionDispo() throws Exception
    {//   public AvionPassager(int idAvion, String nomModele, int placesEco, int placesAffaire, int placesPrem, int idVille) {

        Ville ville = new Ville();
        ville.importFromId("3");
        
        AvionPassager vionvion = new AvionPassager(0,"Falcon8X",5000,5000,5000,ville.getIdVille());
        
        ExportDAL exp = new ExportDAL();
        exp.exportAvionPassager(vionvion);
        
        ImportDAL imp = new ImportDAL();
        ArrayList<Avion> avions = imp.importAvionDispo("passager", "2018/02/25 10:00:00", "2018/02/25 10:00:00", ville);
        
        if(avions.size() <= 0){
            assertFalse("error",true);
        }
    }
}
