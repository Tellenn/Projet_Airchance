/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BD.DBManager;
import DAL.ExportDAL;
import DAL.ImportDAL;
import Tables.Avion;
import Tables.AvionPassager;
import Tables.Client;
import Tables.InstanceVol;
import Tables.Place;
import Tables.ReservationPassager;
import Tables.Reservation_Correspondances;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Pault
 */
public class PartieClient
{

    
    private static Scanner scan;
    private static ImportDAL importDAL;
    private static ExportDAL exportDAL;
    private static DBManager manager;
    private static Client client;
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        mainMenu();
        
    }

    private static void mainMenu() {
        scan = new Scanner(System.in);
        manager = new DBManager();
        importDAL = new ImportDAL();
        exportDAL = new ExportDAL();
        
        
        boolean conn = true;
        
        
        //Choix du compte client
        do
        {
            System.out.println("Entrer votre nom :");
            String login = scan.nextLine();

            manager.dbConnect();

            client = new Client();
            try
            {
                conn = true;
                client.setFromNom(login);
                client.fillReservations();
                System.out.println("Vous etes connecté.");
                menuPrincipal();
                
            } catch (Exception ex)
            {
                System.out.println("Nous n'avons pas pu vous authentifier veuillez vous reconnecter.");
                conn = false;
            }
        
        } while(conn == false);
    }

    private static void menuPrincipal() {

        int choix = -1;

        
        while(choix != 4){
            switch(choix){
                case 1:
                    AffichageArrayList.afficheReservations(client.getReservations());
                    choix = -1;
                    break;
                    
                case 2:
                    
                    Reservation_Correspondances rc = new Reservation_Correspondances();
                    rc.importFromIdClient(""+client.getIdClient());
                    
                    System.out.println("Ajouter une réservation");
                    System.out.println("Quel type de réservation voulez-vous ? 'Passager'/'Fret'");
                    String typeRes = scan.nextLine();
                    if (typeRes.equals("Passager")){
                        
                        ReservationPassager rp = new ReservationPassager();
                        System.out.println("Choississez l'instanceVol : ");
                        AffichageArrayList.afficheInstanceVol(importDAL.importTableInstanceVol());
                        System.out.println("Numéro de l'instance : ");
                        int numInstance = scan.nextInt();
                        scan.nextLine();
                        
                        System.out.println("Choississez la place :");
                        AffichageArrayList.affichePlace(importDAL.importPlaceWithParameter(0, 0, "", "", ""+numInstance));
                        System.out.println("Numéro de la place : ");
                        int numPlace = scan.nextInt();
                        scan.nextLine();
                        
                        InstanceVol iv = importDAL.importTableInstanceVol(numInstance, 0, 0, 0, 0, 0, 0, "", "", "").get(0);
                        Place p = importDAL.importPlaceWithParameter(numPlace, iv.getIdAvion().getIdAvion(), "", "", "").get(0);
                        AvionPassager a = new AvionPassager();
                        a.importFromId(""+p.getIdAvionP());
                        
                        rp.setNumInstance(iv);
                        rp.setNumPlace(p);
                        rp.setDateReservation("2018/02/01 10:30:00");
                        rp.setIdAvion(a);
                        
                        rc.addReservations(rp);
                        exportDAL.exportReservationCorrespondance(rc);
                                
                        
                    }
                    choix = -1;
                    break;
                    
                case 3:
                    break;
                    
                case 4:
                    return;
                    
                default:
                    System.out.println("Menu Principal");
                    System.out.println("1- Afficher les réservations");
                    System.out.println("2- Ajouter une réservation");
                    System.out.println("3- Supprimer une réservation");
                    System.out.println("4- Modifier une réservation");
                    System.out.println("4- Quitter");
                    choix = scan.nextInt();
                    scan.nextLine();
            }
        }
        

    }
    
}
