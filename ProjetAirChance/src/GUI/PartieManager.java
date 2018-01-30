/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BD.DBManager;
import DAL.ExportDAL;
import DAL.ImportDAL;
import Tables.InstanceVol;
import Tables.PNC;
import Tables.Ville;
import Tables.Vol;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pault
 */
public class PartieManager
{

    private static Scanner scan;
    private static ImportDAL importDAL;
    private static ExportDAL exportDAL;
    private static DBManager manager;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
    {
        mainMenu();
    }

    public static void mainMenu()
    {
        scan = new Scanner(System.in);
        importDAL = new ImportDAL();
        exportDAL = new ExportDAL();
        manager = new DBManager();
        manager.dbConnect();

        System.out.println("Que voulez vous faire ?");
        System.out.println("1- Gérer tous les InstanceVols prévus");
        System.out.println("2- Gérer tous les Vols");
        System.out.println("3- Gérer tous les Avions");
        System.out.println("4- Gérer tous les Personnels naviguant");
        int choix = scan.nextInt();
        scan.nextLine();

        switch (choix)
        {
            case 1:
                menuInstanceVol();
                break;
            case 2:
                menuVols();
                break;
            case 3:
                menuAvions();
                break;
            case 4:
                menuPN();
                break;
        }
    }

    private static void menuInstanceVol()
    {
        System.out.println();
        System.out.println("Que voulez vous faire ?");
        System.out.println("1- Afficher tous les instancesVols");
        System.out.println("2- Ajouter un instanceVol");
        System.out.println("3- Supprimer un InstanceVol");
        System.out.println("4- Modifier un InstanceVol");
        System.out.println("5- Retour au menu principal");
        int choix = scan.nextInt();
        scan.nextLine();

        ArrayList<InstanceVol> allInstanceVol = importDAL.importTableInstanceVol();
        switch (choix)
        {
            case 1:
                AffichageArrayList.afficheInstanceVol(allInstanceVol);
                break;
            case 2:
                System.out.println("Date de départ ? format YYYY/MM/DD hh:mm:ss");
                String dateDep = scan.nextLine();
                System.out.println("Date d'arrivee ? format YYYY/MM/DD hh:mm:ss");
                String dateArr = scan.nextLine();
                System.out.println("Quel est l'ID de la ligne ?");
                int volID  = scan.nextInt();
                scan.nextLine();
                Vol numvol = new Vol();
                numvol.importFromId(""+volID);
                ArrayList<PNC> pncDispo = importDAL.importPNCDispo(dateDep,dateArr,numvol.getIdVilleOrigine());
                AffichageArrayList.affichePNC(pncDispo);
                
                break;
            case 3:
                System.out.println("Quel est l'id du vol à supprimmer ? -1 pour retour.");
                choix = scan.nextInt();
                scan.nextLine();
                if (choix != -1)
                {
                    for(InstanceVol vol:allInstanceVol)
                    {
                        if(vol.getNumInstance()==choix)
                        {
                            try
                            {
                                exportDAL.deleteInstanceVol(vol);
                            } catch (Exception ex)
                            {
                                Logger.getLogger(PartieManager.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
                break;
            case 4:
                menuPN();
                break;
            case 5:
                mainMenu();
                break;
        }
    }

    private static void menuVols()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void menuAvions()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void menuPN()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
