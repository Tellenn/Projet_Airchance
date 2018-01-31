/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Tables.Avion;
import Tables.InstanceVol;
import Tables.PNC;
import Tables.PNT;
import Tables.ReservationPassager;
import Tables.Reservation_Correspondances;
import Tables.Reservations;

import Tables.Vol;

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
        if (list==null)
        {
            System.out.println("Il n'y a pas de PNC disponible");
        }else
        {
            for (PNC pnc : list)
            {

                    System.out.print(pnc.getIdEmploye());
                    System.out.print(" / "+pnc.getNomEmploye()+" / ");
                    System.out.print(" / "+pnc.getPrenomEmploye()+" / ");
                    System.out.println();
            }
        }
    }
    public static void affichePNT(ArrayList<PNT> list)
    {
        System.out.print("numPNT /");
        System.out.print("/ Nom /");
        System.out.print("/ Prenom /");
        System.out.println();
        if (list==null)
        {
            System.out.println("Il n'y a pas de PNC disponible");
        }else
        {
            for (PNT pnt : list)
            {
                System.out.print(pnt.getIdEmploye());
                System.out.print(" / "+pnt.getNomEmploye()+" / ");
                System.out.print(" / "+pnt.getPrenomEmploye()+" / ");
                System.out.println();
            }
        }
    }
    public static void afficheAvion(ArrayList<Avion> list)
    {
        System.out.print("idAvion /");
        System.out.print("/ NomModele /");
         System.out.println();
        for (Avion avion : list)
        {
            System.out.print(avion.getIdAvion());
            System.out.print(" / "+avion.getModele().getNomModele()+" / ");
            System.out.println();
        }
    }

    public static void afficheVol(ArrayList<Vol> list)
    {
                
        System.out.print("numVol /");
        System.out.print("/ VilleDep /");
        System.out.print("/ VilleArr /");
        System.out.print("/ Type /");
        System.out.println();
        for (Vol vol : list)
        {
            System.out.print(vol.getNumVol());
            System.out.print(" / "+vol.getIdVilleOrigine().getNomVille()+" / ");
            System.out.print(" / "+vol.getIdVilleDestination().getNomVille()+" / ");
            if (vol.getType()==1)
            {
                System.out.print(" / "+"passager"+" / ");
            }else
            {
                System.out.print(" / "+"fret"+" / ");
            }
            System.out.println();
        }
    }

    static void afficheReservations(Reservation_Correspondances reservations) {
        System.out.print("idClient /");
        System.out.print("/ prixTotal /");
        System.out.print("/ dateReservation /");
        System.out.println();
        System.out.print(reservations.getIdClient().getIdClient());
        System.out.print(" / "+reservations.getPrix()+" / ");
        System.out.println(" / "+reservations.getDateReservation());
        
        System.out.println("RESERVATIONS");

        for(Reservations res : reservations.getReservations()){
            if(res instanceof ReservationPassager){
                System.out.print("numReservation /");
                System.out.print(" / prix /");
                System.out.print(" / dateReservation /");
                System.out.print(" / numInstance /");
                System.out.print(" / place / ");
                System.out.println(" / Avion / ");
                
                System.out.print(res.getNumReservation());
                System.out.print(" / "+res.getPrix()+" / ");
                System.out.print(" / "+res.getDateReservation()+" / ");
                System.out.println(" / "+res.getNumInstance().getNumInstance()+" / ");
                System.out.println(" / "+((ReservationPassager)res).getNumPlace().getNumPlace()+" / ");
                System.out.println(" / "+((ReservationPassager)res).getIdAvion().getIdAvion()+" / ");
            }
            
            
        }

    
    }

}
