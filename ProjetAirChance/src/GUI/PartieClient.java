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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        Scanner scan = new Scanner(System.in);
        ImportDAL importDal = new ImportDAL();
        ExportDAL exportDal = new ExportDAL();
        DBManager manager = new DBManager();
        
        
        boolean conn = true;
        
        
        //Choix du compte client
        do
        {
            System.out.println("Entrer votre nom :");
            String login = scan.nextLine();

            manager.dbConnect();

            Client client = new Client();
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
    
}
