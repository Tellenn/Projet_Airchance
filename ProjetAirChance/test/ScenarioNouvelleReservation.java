/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import BD.DBManager;
import DAL.ExportDAL;
import DAL.ImportDAL;
import GUI.AffichageArrayList;
import Tables.AvionPassager;
import Tables.Client;
import Tables.InstanceVol;
import Tables.Place;
import Tables.ReservationPassager;
import Tables.Reservation_Correspondances;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
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
public class ScenarioNouvelleReservation {
    
    
    private static Scanner scan;
    private static ImportDAL importDAL;
    private static ExportDAL exportDAL;
    private static DBManager manager;
    
    public ScenarioNouvelleReservation() {
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
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);
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
    public void testAjouterNouveauScénario(){
        try{
            
        
        
        System.out.println("Scénario réservation");

        Client client = new Client();
        client.importFromId(""+15);
        System.out.println("<Client voulant réserver : n°15>");
        System.out.println("IDCLIENT "+client.getIdClient());
        System.out.println(" | NOMCLIENT "+client.getNomClient());
        System.out.println(" | PRENOMCLIENT "+client.getPrenomClient());
        System.out.println(" | NUMRUECLIENT "+client.getNumRueClient());
        System.out.println(" | RUECLIENT "+client.getRueClient());
        System.out.println(" | CPCLIENT "+client.getVilleClient());
        System.out.println(" | VILLECLIENT "+client.getVilleClient());
        System.out.println(" | HEURESCUMULEES "+client.getHeuresCumulees());
        System.out.println(" | NUMPASSEPORT "+client.getNumPasseport());
        
        //scan.nextLine();
        
        Reservation_Correspondances rc = new Reservation_Correspondances();
        rc.importFromIdClient(""+client.getIdClient());

        System.out.println("Ajouter une réservation");
        System.out.println("<Choix du type : 'Passager'>");
        String typeRes = "Passager";
        if (typeRes.equals("Passager")){

            ReservationPassager rp = new ReservationPassager();
            
            AffichageArrayList.afficheInstanceVol(importDAL.importTableInstanceVol());
            System.out.println("Choississez l'instanceVol : ");
            System.out.println("<Choix de l'instance n°4 >");
            int numInstance = 4;

            /*
            System.out.println("Choississez la place :");
            AffichageArrayList.affichePlace(importDAL.importPlaceWithParameter(0, 0, "", "", ""+numInstance));
            System.out.println("Numéro de la place : ");
            int numPlace = scan.nextInt();
            scan.nextLine();*/

            ArrayList<InstanceVol> iVolList = importDAL.importTableInstanceVol(numInstance, 0, 0, 0, 0, 0, 0, "", "", "");
            InstanceVol iv = iVolList.get(0);
            
            
            AffichageArrayList.affichePlace(importDAL.importPlaceWithParameter(0, iv.getIdAvion().getIdAvion(), "", "", ""+numInstance));
            System.out.println("Choississez la place :");
            System.out.println("<Choix de la place numéro 14>");
            int numPlace = 14;
            
            ArrayList<Place> placesList = importDAL.importPlaceWithParameter(numPlace, iv.getIdAvion().getIdAvion(), "", "", ""+numInstance);
            Place p = placesList.get(0);
            AvionPassager a = new AvionPassager();
            a.importFromId(""+p.getIdAvionP());

            rp.setNumInstance(iv);
            rp.setNumPlace(p);
            rp.setDateReservation("2018/02/01 10:30:00");
            rp.setIdAvion(a);

            
            rc.addReservations(rp);
            System.out.println("Exportation de la réservation à suivre");
            exportDAL.exportReservationCorrespondance(rc);
            System.out.println("Scénario terminé, en attente du commit");
            
            manager.commit();
            manager.dbDisconnect();
        }

        } catch (SQLException ex) {
            Logger.getLogger(ScenarioNouvelleReservation.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
    }
}
