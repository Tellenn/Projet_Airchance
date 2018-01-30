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
import Tables.Modele;
import Tables.PNC;
import Tables.PNT;
import Tables.Place;
import Tables.Ville;
import Tables.Vol;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
public class TestExport
{

    private DBManager manager;

    public TestExport()
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
        ImportDAL dal = new ImportDAL();
        int nbPlaces = dal.importPlaceWithParameter(0, 7, "", "", "").size();
        System.out.println(nbPlaces);
        assertTrue("Error wrong number of places for plane 7", nbPlaces == 150);
    }

    @Test
    public void changeAutoCommit()
    {
        try
        {
            manager.changeAutocommit(false);
        } catch (SQLException ex)
        {
            assertFalse("Error while changing autocommit", true);
        }
    }

    @Test
    public void tryCommit()
    {
        try
        {
            manager.changeAutocommit(false);
            manager.commit();
        } catch (SQLException ex)
        {
            assertFalse("Error while trying commit", true);
        }
    }

    @Test
    public void tryChangeIsolationLevel()
    {
        try
        {
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (SQLException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying changing the isolation level", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Test
    public void exportFret()
    {        
        try
        {
            manager.changeAutocommit(false);
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);

            AvionFret avion = new AvionFret(0, "Falcon900", 500, 500, 1);
            
            ExportDAL dal = new ExportDAL();
            dal.exportAvionFret(avion);
            manager.rollBack();

        } catch (SQLException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export an avionFret", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export an avionFret", true);
        } catch (Exception ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export an avionFret", true);
        }
    }
    
    @Test
    public void exportPassager()
    {
        try
        {
            manager.changeAutocommit(false);
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
            manager.rollBack();
            
        } catch (SQLException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export an avionPassager", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export an avionPassager", true);
        } catch (Exception ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export an avionPassager", true);
        }
    }
    
    
    @Test
    public void exportPNC()
    {
        try
        {
            manager.changeAutocommit(false);
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);
            ArrayList<String> langues = new ArrayList<String>();
            langues.add("Anglais");
            langues.add("Francais");
            langues.add("Allemand");

            PNC pnc = new PNC(0,"Shepard","John","2","command center","12345","ATLANTIS",12000,1,langues);
            
           
            
            ExportDAL dal = new ExportDAL();
            dal.exportPNC(pnc);
            manager.rollBack();
            
        } catch (SQLException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a PNC", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a PNC", true);
        } catch (Exception ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a PNC", true);
        }
    }
    
    @Test
    public void exportPNT()
    {
        try
        {
            manager.changeAutocommit(false);
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);
            
            PNT pnt = new PNT(0,"Carter","Samantha","2","etage -22","12345","Cheyenne Mountain",12000,1);
            Modele m = new Modele();
            m.importFromId("Falcon900");
            Map<Modele, Integer> map = new HashMap<>();
            map.put(m,500);
            pnt.setPiloteModele(map);
            ExportDAL dal = new ExportDAL();
            dal.exportPNT(pnt);
            manager.rollBack();
            
        } catch (SQLException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a PNC", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a PNC", true);
        } catch (Exception ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a PNC", true);
        }
    }
    
    @Test
    public void exportModele()
    {
        try
        {
            manager.changeAutocommit(false);
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);
            
            Modele m = new Modele();
            m.setNbPilotes(1);
            m.setNomModele("Jumper");
            m.setRayonAction(999999);
            
            ExportDAL dal = new ExportDAL();
            dal.exportModele(m);
            manager.rollBack();
            
        } catch (SQLException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a Modele", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a Modele", true);
        } catch (Exception ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a Modele", true);
        }
    }
    
    @Test
    public void exportVille()
    {
        try
        {
            manager.changeAutocommit(false);
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);
            
            Ville v = new Ville(0,"ATLANTIS","Galaxie de Pegase");
            
            ExportDAL dal = new ExportDAL();
            dal.exportVille(v);
            manager.rollBack();
            
        } catch (SQLException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a Modele", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a Modele", true);
        } catch (Exception ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a Modele", true);
        }
    }
    
    
    @Test
    public void exportVol()
    {
        try
        {
            manager.changeAutocommit(false);
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);
            
            Vol v = new Vol(0,1,120,1200,2,2,0,0,1,2);
            
            ExportDAL dal = new ExportDAL();
            dal.exportVol(v);
            manager.rollBack();
            
        } catch (SQLException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a Modele", true);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a Modele", true);
        } catch (Exception ex)
        {
            Logger.getLogger(TestExport.class.getName()).log(Level.SEVERE, null, ex);
            assertFalse("Error while trying to export a Modele", true);
        }
    }

}
