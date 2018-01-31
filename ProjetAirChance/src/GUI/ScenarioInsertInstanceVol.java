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
import Tables.Vol;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pault
 */
public class ScenarioInsertInstanceVol
{

    private static Scanner scan;
    private static ImportDAL importDAL;
    private static ExportDAL exportDAL;
    private static DBManager manager;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException, ClassNotFoundException
    {
        scan = new Scanner(System.in);
        importDAL = new ImportDAL();
        exportDAL = new ExportDAL();
        manager = new DBManager();
        manager.dbConnect();
        manager.changeAutocommit(false);

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
        ArrayList<Avion> avionDispo;
        try
        {
            avionDispo = importDAL.importAvionDispo(typ, dateDep, dateArr, numvol.getIdVilleOrigine());
            AffichageArrayList.afficheAvion(avionDispo);
        } catch (ParseException ex)
        {
            Logger.getLogger(PartieManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Quel est l'ID de l'avion a utiliser ?");
        int avionID = scan.nextInt();
        scan.nextLine();

        InstanceVol volInstance = new InstanceVol(0, volID, avionID, 0, 0, 0, 0, dateDep, dateArr, "Cree");

        ArrayList<PersonnelNavigant> pnChoix = new ArrayList();
        System.out.println("Voici les PNC dispo.");
        ArrayList<PNC> pncDispo;
        try
        {
            pncDispo = importDAL.importPNCDispo(dateDep, dateArr, numvol.getIdVilleOrigine());
            AffichageArrayList.affichePNC(pncDispo);
        } catch (ParseException ex)
        {
            Logger.getLogger(PartieManager.class.getName()).log(Level.SEVERE, null, ex);
        }

        System.out.println("Lesquels voulez vous prendre? mettre des virgules entre chaque id");
        String pnc = scan.nextLine();
        String[] s = pnc.split(",");
        for (int i = 0; i < s.length; i++)
        {
            PNC temp = new PNC();
            temp.importFromId(s[i]);
            pnChoix.add(temp);
        }

        System.out.println("Voici les PNT dispo.");
        ArrayList<PNT> pntDispo = importDAL.importPNTDispo(dateDep, dateArr, numvol.getIdVilleOrigine());
        AffichageArrayList.affichePNT(pntDispo);
        System.out.println("Lesquels voulez vous prendre? mettre des virgules entre chaque id");
        String pnt = scan.nextLine();
        s = pnt.split(",");
        for (int i = 0; i < s.length; i++)
        {
            PNT temp = new PNT();
            temp.importFromId(s[i]);
            pnChoix.add(temp);
        }

        volInstance.setPersonnel(pnChoix);
        exportDAL.exportInstanceVol(volInstance);
        boolean continu = true;
        while (continu)
        {
            System.out.println("Voulez vous valider ? (y ou n)");
            String rep = scan.nextLine();
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
    }

}
