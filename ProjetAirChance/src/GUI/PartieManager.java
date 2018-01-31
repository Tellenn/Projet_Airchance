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
import Tables.InstanceVol;
import Tables.PNC;
import Tables.PNT;
import Tables.PersonnelNavigant;
import Tables.Ville;
import Tables.Vol;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    public static void main(String[] args) throws SQLException
    {
        mainMenu();
    }

    public static void mainMenu() throws SQLException
    {
        scan = new Scanner(System.in);
        importDAL = new ImportDAL();
        exportDAL = new ExportDAL();
        manager = new DBManager();
        manager.dbConnect();
        manager.changeAutocommit(false);

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

    private static void menuInstanceVol() throws SQLException
    {
        System.out.println();
        System.out.println("Que voulez vous faire ?");
        System.out.println("1- Afficher tous les instancesVols");
        System.out.println("2- Ajouter un instanceVol");
        System.out.println("3- Supprimer un InstanceVol");
        System.out.println("4- Modifier un InstanceVol");
        System.out.println("5- Confirmer la terminaison d'un InstanceVol");
        System.out.println("6- Retour au menu principal");
        int choix = scan.nextInt();
        scan.nextLine();
        
        try
        {
            manager.dbChangeIsolation(Connection.TRANSACTION_SERIALIZABLE);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(PartieManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        ArrayList<InstanceVol> allInstanceVol = importDAL.importTableInstanceVol();
        
        
        switch (choix)
        {
            case 1:
                AffichageArrayList.afficheInstanceVol(allInstanceVol);
                //à modifier
                manager.dbDisconnect();
                break;
            case 2:
                System.out.println("Date de départ ? format YYYY/MM/DD hh:mm:ss");
                String dateDep = scan.nextLine();
                System.out.println("Date d'arrivee ? format YYYY/MM/DD hh:mm:ss");
                String dateArr = scan.nextLine();
                System.out.println("Quel est l'ID du vol ?");
                int volID  = scan.nextInt();
                scan.nextLine();
                
                Vol numvol = new Vol();
                numvol.importFromId(""+volID);
                
                System.out.println("Voici les avions disponibles");
                int type = numvol.getType();
                String typ;
                if (type == 1)
                {
                    typ = "passager";
                }else
                {
                    typ = "fret";
                }
                ArrayList<Avion> avionDispo;
                try
                {
                    avionDispo = importDAL.importAvionDispo(typ,dateDep,dateArr,numvol.getIdVilleOrigine());
                    AffichageArrayList.afficheAvion(avionDispo);
                } catch (ParseException ex)
                {
                    Logger.getLogger(PartieManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println("Quel est l'ID de l'avion a utiliser ?");
                int avionID  = scan.nextInt();
                scan.nextLine();
                
                InstanceVol volInstance = new InstanceVol(0,volID,avionID,0,0,0,0,dateDep,dateArr,"Cree");
                
                ArrayList<PersonnelNavigant> pnChoix = new ArrayList();
                System.out.println("Voici les PNC dispo.");
                ArrayList<PNC> pncDispo;
                try
                {
                    pncDispo = importDAL.importPNCDispo(dateDep,dateArr,numvol.getIdVilleOrigine());
                    AffichageArrayList.affichePNC(pncDispo);
                } catch (ParseException ex)
                {
                    Logger.getLogger(PartieManager.class.getName()).log(Level.SEVERE, null, ex);
                }
                
                System.out.println("Lesquels voulez vous prendre? mettre des virgules entre chaque id");
                String pnc = scan.nextLine();
                String[] s = pnc.split(",");
                for(int i=0;i<s.length;i++)
                {
                    PNC temp = new PNC();
                    temp.importFromId(s[i]);
                    pnChoix.add(temp);
                }
                
                System.out.println("Voici les PNT dispo.");
                ArrayList<PNT> pntDispo = importDAL.importPNTDispo(dateDep,dateArr,numvol.getIdVilleOrigine());
                AffichageArrayList.affichePNT(pntDispo);
                System.out.println("Lesquels voulez vous prendre? mettre des virgules entre chaque id");
                String pnt = scan.nextLine();
                s = pnt.split(",");
                for(int i=0;i<s.length;i++)
                {
                    PNT temp = new PNT();
                    temp.importFromId(s[i]);
                    pnChoix.add(temp);
                }
                
                volInstance.setPersonnel(pnChoix);
                exportDAL.exportInstanceVol(volInstance);
                boolean continu = true;
                while(continu)
                {
                    System.out.println("Voulez vous valider ? (y ou n)");
                    String rep = scan.nextLine();
                    if (rep.equals("y"))
                    {   
                        manager.commit();
                        continu = false;
                    }
                    else if (rep.equals("n"))
                    {
                        manager.rollBack();
                        continu = false;
                    }
                }
                //à modifier
                manager.dbDisconnect();
                break;
            case 3:
                System.out.println("Quel est l'id de l'instanceVol à supprimmer ? -1 pour retour.");
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
                continu = true;
                while(continu)
                {
                    System.out.println("Voulez vous valider ? (y ou n)");
                    String rep = scan.nextLine();
                    if (rep.equals("y"))
                    {   
                        manager.commit();
                        continu = false;
                    }
                    else if (rep.equals("n"))
                    {
                        manager.rollBack();
                        continu = false;
                    }
                }
                //à modifier
                manager.dbDisconnect();
                break;
            case 4:
                //à modifier
                manager.dbDisconnect();
                //TODO//
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
                
            case 5:
                System.out.println("Quel est l'ID de l'instanceVol a terminer ?");
                int ivID  = scan.nextInt();
                scan.nextLine();
                InstanceVol temp = new InstanceVol();
                temp.importFromId(""+ivID);
                if (!temp.getEtat().equals("Cree") || !temp.getEtat().equals("En cours de Vol"))
                {
                    System.out.println("Erreur le vol est déja annulé ou arrivé");
                }else
                {
                    temp.setEtat("Arrive");
                    String date = new SimpleDateFormat("yyyy/MM/dd' 'hh:mm:ss").format(new Date());
                    temp.setDateArrive(date);
                    exportDAL.exportInstanceVol(temp);
                }
                continu = true;
                while(continu)
                {
                    System.out.println("Voulez vous valider ? (y ou n)");
                    String rep = scan.nextLine();
                    if (rep.equals("y"))
                    {   
                        manager.commit();
                        continu = false;
                    }
                    else if (rep.equals("n"))
                    {
                        manager.rollBack();
                        continu = false;
                    }
                }
                //à modifier
                manager.dbDisconnect();
                break;    
            case 6:
                mainMenu();
                break;
        }
    }

    private static void menuVols() throws SQLException
    {
        System.out.println();
        System.out.println("Que voulez vous faire ?");
        System.out.println("1- Afficher tous les Vols");
        System.out.println("2- Retour au menu principal");
        int choix = scan.nextInt();
        scan.nextLine();
        
        manager.changeAutocommit(false);
        try
        {
            manager.dbChangeIsolation(Connection.TRANSACTION_READ_COMMITTED);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(PartieManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        ArrayList<Vol> allVol = importDAL.importTableVol();
        switch (choix)
        {
            case 1:
                AffichageArrayList.afficheVol(allVol);
                //à modifier
                manager.dbDisconnect();
                break;
            case 2:
                mainMenu();
                break;
        }
    }

    private static void menuAvions()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private static void menuPN()
    {

        int choix = -1;
        boolean continu = true;


        while(choix != 5){
            switch(choix){
            case 1 :
// <editor-fold defaultstate="collapsed" desc=" Afficher PN ">
                    System.out.println("Reading PersonnelNaviguant...");
                    ArrayList<PNT> pnt = importDAL.importTablePNT();
                    ArrayList<PNC> pnc = importDAL.importTablePNC();
                    AffichageArrayList.affichePNT(pnt);
                    AffichageArrayList.affichePNC(pnc);
                    choix = -1;
                    break;

// </editor-fold>
                
            case 2:
// <editor-fold defaultstate="collapsed" desc=" Ajouter PN ">
                    System.out.println("Ajout d'un PN");
                    System.out.println("Nom du PN :");
                    scan.nextLine();
                    String nomPN = scan.nextLine();
                    
                    System.out.println("Prénom du PN :");
                    String prenomPN = scan.nextLine();
                    
                    System.out.println("ADRESSE");
                    System.out.println(" | numéro de rue : ");
                    String numRuePN = scan.nextLine();
                    System.out.println(" | nom de rue : ");
                    String ruePN = scan.nextLine();
                    System.out.println(" | code Postal : ");
                    String cpPN = scan.nextLine();
                    System.out.println(" | Ville : ");
                    String villePN = scan.nextLine();
                    String typePN;
                    do {
                        System.out.println("Type du PN : 'PNT' / 'PNC' ");
                        typePN = scan.nextLine();
                        
                    } while (!typePN.equals("PNT") && !typePN.equals("PNC"));
                    
                    if (typePN.equals("PNT")) {
                        
                        PNT mypnt = new PNT(0, nomPN, prenomPN, numRuePN, ruePN, cpPN, villePN, 0, 1);
                        exportDAL.exportPNT(mypnt);
                    } else {
                        PNC mypnc = new PNC(0, nomPN, prenomPN, numRuePN, ruePN, cpPN, villePN, 0, 1);
                        exportDAL.exportPNC(mypnc);
                    }
                    
                    continu = true;
                    while (continu) {
                        System.out.println("Voulez vous valider ? (y ou n)");
                        String rep = scan.nextLine();
                        if (rep.equals("y")) {                            
                            try {
                                manager.commit();
                                continu = false;
                            } catch (SQLException ex) {
                                Logger.getLogger(PartieManager.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (rep.equals("n")) {
                            try {
                                manager.rollBack();
                                continu = false;
                            } catch (SQLException ex) {
                                Logger.getLogger(PartieManager.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                    choix = -1;
                    
                    break;

// </editor-fold>

                
            case 3:
                
                    // <editor-fold defaultstate="collapsed" desc=" Modifier PN ">
                    System.out.println("Modifier un PN");
                    System.out.println("Id du PN à modifier : ");
                    int idPN = scan.nextInt();
                    scan.nextLine();
                    PersonnelNavigant pn = importDAL.importTablePN(idPN);

                    System.out.println("Modifier nom ? y/n");
                    String modif = scan.nextLine();
                    if (modif.equals("y")) {
                        System.out.println("Nom à modifier : ");
                        String nom = scan.nextLine();
                        pn.setNomEmploye(nom);
                    }

                    System.out.println("Modifier prénom ? ");
                    modif = scan.nextLine();
                    if (modif.equals("y")) {
                        System.out.println("Prénom à modifier : ");
                        String prénom = scan.nextLine();
                        pn.setPrenomEmploye(prénom);
                    }

                    System.out.println("Modifier numéro de rue ? y/n");
                    modif = scan.nextLine();
                    if (modif.equals("y")) {
                        System.out.println("Numéro de rue : ");
                        String num = scan.nextLine();
                        pn.setNumRueEmploye(num);
                    }

                    System.out.println("Modifier nom rue ? y/n");
                    modif = scan.nextLine();
                    if (modif.equals("y")) {
                        System.out.println("Nom de la rue à modifier : ");
                        String rue = scan.nextLine();
                        pn.setRueEmploye(rue);
                    }

                    System.out.println("Modifier code postal ? y/n");
                    modif = scan.nextLine();
                    if (modif.equals("y")) {
                        System.out.println("Code Postal : ");
                        String cp = scan.nextLine();
                        pn.setCpEmploye(cp);
                    }

                    System.out.println("Modifier ville de résidence ? y/n");
                    modif = scan.nextLine();
                    if (modif.equals("y")) {
                        System.out.println("Ville : ");
                        String num = scan.nextLine();
                        pn.setVilleEmploye(num);
                    }

                    System.out.println("Modifier heures Vol ? y/n");
                    modif = scan.nextLine();
                    if (modif.equals("y")) {
                        System.out.println("Heures vol : ");
                        int num = scan.nextInt();
                        pn.setHeuresVol(num);
                        scan.nextLine();
                    }

                    System.out.println("Modifier id Dernière Ville ? y/n");
                    modif = scan.nextLine();
                    if (modif.equals("y")) {
                        System.out.println("id Dernière Ville : ");
                        String num = scan.nextLine();
                        Ville tmp = new Ville();
                        tmp.importFromId(num);
                        pn.setIdDerniereVille(tmp);
                    }

                    if (pn instanceof PNT) {
                        exportDAL.exportPNT((PNT) pn);
                    } else {
                        exportDAL.exportPNC((PNC) pn);
                    }

                    continu = true;
                    while (continu) {
                        System.out.println("Voulez vous valider ? (y ou n)");
                        String rep = scan.nextLine();
                        if (rep.equals("y")) {
                            try {
                                manager.commit();
                                continu = false;
                            } catch (SQLException ex) {
                                Logger.getLogger(PartieManager.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        } else if (rep.equals("n")) {
                            try {
                                manager.rollBack();
                                continu = false;
                            } catch (SQLException ex) {
                                Logger.getLogger(PartieManager.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }

                    choix = -1;
                    break;

// </editor-fold>
                
            case 4 :
                return;
                
                
            default:
                System.out.println();
                System.out.println("Que voulez vous faire ?");
                System.out.println("1- Afficher tous les Personnels Naviguants");
                System.out.println("2- Ajouter un Personnel Naviguant");
                System.out.println("3- Modifier un Personnel Naviguant");
                System.out.println("4- Retour au menu principal");
                choix = scan.nextInt();
                scan.nextLine();
            }
        }
        
    }

}
