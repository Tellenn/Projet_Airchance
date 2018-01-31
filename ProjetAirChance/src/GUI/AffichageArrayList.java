/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Tables.InstanceVol;
import Tables.PNC;
import Tables.PNT;
import java.util.ArrayList;

/**
 *
 * @author Pault
 */
public class AffichageArrayList
{
    public static void afficheInstanceVol(ArrayList<InstanceVol> list)
    {
        /*
        private int numInstance;
        private Vol numVol;
        private Avion idAvion;
        private int placesRestEco;
        private int placesRestPrem;
        private int placesRestAff;
        private int poidsRest;
        private String dateArrive;
        private String dateDepart;
        private String etat;
        */
        
        System.out.print("numInstance /");
        System.out.print("/ VilleDep /");
        System.out.print("/ VilleArr /");
        System.out.print("/ DateDep /");
        System.out.print("/ DateArr /");
         System.out.println();
        for (InstanceVol instance : list)
        {
            System.out.print(instance.getNumInstance());
            System.out.print(" / "+instance.getNumVol().getIdVilleOrigine().getNomVille()+" / ");
            System.out.print(" / "+instance.getNumVol().getIdVilleDestination().getNomVille()+" / ");
            System.out.print(" / "+instance.getDateDepart()+" / ");
            System.out.print(" / "+instance.getDateArrive()+" / ");
            System.out.println();
        }
    }
    
    public static void affichePNC(ArrayList<PNC> list)
    {
        /*
        private int numInstance;
        private Vol numVol;
        private Avion idAvion;
        private int placesRestEco;
        private int placesRestPrem;
        private int placesRestAff;
        private int poidsRest;
        private String dateArrive;
        private String dateDepart;
        private String etat;
        */
        
        System.out.print("numPNC /");
        System.out.print("/ Nom /");
        System.out.print("/ Prenom /");
         System.out.println();
        for (PNC pnc : list)
        {
            System.out.print(pnc.getIdEmploye());
            System.out.print(" / "+pnc.getNomEmploye()+" / ");
            System.out.print(" / "+pnc.getPrenomEmploye()+" / ");
            System.out.println();
        }
    }
    public static void affichePNT(ArrayList<PNT> list)
    {
        
        
        System.out.print("numPNC /");
        System.out.print("/ Nom /");
        System.out.print("/ Prenom /");
         System.out.println();
        for (PNT pnc : list)
        {
            System.out.print(pnc.getIdEmploye());
            System.out.print(" / "+pnc.getNomEmploye()+" / ");
            System.out.print(" / "+pnc.getPrenomEmploye()+" / ");
            System.out.println();
        }
    }
}