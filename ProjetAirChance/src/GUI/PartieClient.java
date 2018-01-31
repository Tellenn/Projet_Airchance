/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BD.DBManager;
import DAL.ExportDAL;
import DAL.ImportDAL;
import Tables.Client;
import Tables.InstanceVol;
import Tables.Reservation_Correspondances;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException
    {
        Client client = new Client();
        connexionClient(client);
        menuClient(client);
        
    }
    
    private static void connexionClient(Client client) throws SQLException{
        scan = new Scanner(System.in);
        importDAL = new ImportDAL();
        exportDAL = new ExportDAL();
        manager = new DBManager();
        manager.dbConnect();
        
        boolean conn = true;
        //Choix du compte client
        do
        {
            System.out.println("Entrer votre nom :");
            String login = scan.nextLine();

            manager.dbConnect();

            //Client client = new Client();
            try
            {
                conn = true;
                client.setFromNom(login);
                System.out.println("Vous etes connecté.");
            } catch (Exception ex)
            {
                System.out.println("Nous n'avons pas pu vous authentifier veuillez vous reconnecter.");
                conn = false;
            }
        
        } while(conn == false);
    }
    
    private static void menuClient(Client client) throws SQLException{
        
        System.out.println();
        System.out.println("Que voulez vous faire ?");
        System.out.println("1- Afficher tous les instancesVols");
        System.out.println("2- Créer une réservation");
        System.out.println("3- Annuler une réservation");
        System.out.println("4- Afficher mes réservations");
        
        int choix = scan.nextInt();
        scan.nextLine();

        ArrayList<InstanceVol> allInstanceVol = importDAL.importTableInstanceVol(0, 0, 0, 0, 0, 0, 0, "", "", "Cree");
        switch (choix)
        {
            case 1:
                AffichageArrayList.afficheInstanceVol(allInstanceVol);
                break;
            case 2:
                nouvelleResa(client);
                break;
            case 3:
                
                break;
            case 4:
                
                break;
        }
    }
    
    private static void nouvelleResa(Client client) throws SQLException{
        
        System.out.println();
        System.out.println("Que voulez vous faire ?");
        System.out.println("1- Nouvelle réservation passager");
        System.out.println("2- Nouvelle réservation fret");
        System.out.println("3- Retour au menu principal");
        
        int choix = scan.nextInt();
        scan.nextLine();

        switch (choix)
        {
            case 1:

                break;
            case 2:
                break;
            case 3:
                menuClient(client);
                break;
        }
    }
    
}
