/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Tables.InstanceVol;
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
    
}
