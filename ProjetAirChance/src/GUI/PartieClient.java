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
