/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BD.DBManager;
import DAL.ExportDAL;
import DAL.ImportDAL;
import GUI.AffichageArrayList;
import GUI.PartieManager;
import Tables.Avion;
import Tables.InstanceVol;
import Tables.PNC;
import Tables.PNT;
import Tables.PersonnelNavigant;
import Tables.Vol;
import java.sql.Connection;
import java.text.ParseException;
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
 * @author Pault
 */
public class ScénarioNouvelInstanceVol
{
    
    public ScénarioNouvelInstanceVol()
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
    public void tearDown()
    {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
    // @Test
    // public void hello() {}
    
    @Test
    public void Scénario1()
    {
        try
        {
            Scanner scan;
            ImportDAL importDAL;
            ExportDAL exportDAL;
            DBManager manager;

            scan = new Scanner(System.in);
            importDAL = new ImportDAL();
            exportDAL = new ExportDAL();
            manager = new DBManager();
            manager.dbConnect();
            manager.changeAutocommit(false);
            manager.dbChangeIsolation(Connection.TRANSACTION_SERIALIZABLE);

            System.out.println("Date de départ 2018/01/10 00:00:00");
            String dateDep = "2018/02/10 12:00:00";
            System.out.println("Date d'arrivee 2018/02/10 21:00:00");
            String dateArr = "2018/02/10 21:00:00";
            System.out.println("Le volID est 1 (vol paris-new york,passager)");
            int volID = 1;

            Vol numvol = new Vol();
            numvol.importFromId("" + volID);

            System.out.println("Voici les avions disponibles");
            int type = numvol.getType();
            String typ;
            if (type == 1)
            {
                typ = "passager";
            } else
            {
                typ = "fret";
            }
            ArrayList<Avion> avionDispo = null;
            
            avionDispo = importDAL.importAvionDispo(typ, dateDep, dateArr, numvol.getIdVilleOrigine());
            AffichageArrayList.afficheAvion(avionDispo);
           

            System.out.println("Quel est l'ID de l'avion a utiliser ?");
            int avionID = avionDispo.get(0).getIdAvion();

            InstanceVol volInstance = new InstanceVol(0, volID, avionID, 0, 0, 0, 0, dateDep, dateArr, "Cree");

            ArrayList<PersonnelNavigant> pnChoix = new ArrayList();
            System.out.println("Voici les PNC dispo.");
            ArrayList<PNC> pncDispo;
            
            pncDispo = importDAL.importPNCDispo(dateDep, dateArr, numvol.getIdVilleOrigine());
            System.out.println(pncDispo);
            AffichageArrayList.affichePNC(pncDispo);
            
            pnChoix.add(pncDispo.get(0));
            pnChoix.add(pncDispo.get(1));

            System.out.println("Voici les PNT dispo.");
            ArrayList<PNT> pntDispo = importDAL.importPNTDispo(dateDep, dateArr, numvol.getIdVilleOrigine());
            AffichageArrayList.affichePNT(pntDispo);
            
            pnChoix.add(pntDispo.get(0));
            
            volInstance.setPersonnel(pnChoix);
            exportDAL.exportInstanceVol(volInstance);
            boolean continu = true;
            while (continu)
            {
                System.out.println("Voulez vous valider ? (y ou n)");
                String rep = "n";
                if (rep.equals("y"))
                {
                    manager.commit();
                    continu = false;
                } else if (rep.equals("n"))
                {
                    manager.rollBack();
                    continu = false;
                }
            }
            //à modifier
            manager.dbDisconnect();
            
        }catch(Exception e)
        {
            Logger.getLogger(PartieManager.class.getName()).log(Level.SEVERE, null, e);
            Assert.assertFalse("There was an Exception"+e,true);
        }
    }
}
