/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import java.sql.Date;
import java.util.ArrayList;

/**
 *
 * @author Pault
 */
public class Reservation
{
    private int prix;
    private int idResa;
    private ArrayList<Reservations> reservations;
    private Date dateReservation;

    public Reservation(int idResa, ArrayList<Reservations> reservations)
    {
        
        this.idResa = idResa;
        this.reservations = reservations;
    }
    
    public Date getDateReservation()
    {
        return dateReservation;
    }

    public void setDateReservation(Date dateReservation)
    {
        this.dateReservation = dateReservation;
    }


    public int getIdResa()
    {
        return idResa;
    }

    public void setIdResa(int idResa)
    {
        this.idResa = idResa;
    }

    public ArrayList<Reservations> getReservations()
    {
        return reservations;
    }

    public void setReservations(ArrayList<Reservations> reservations)
    {
        this.reservations = reservations;
        this.prix = 0;
        for(Reservations reservation : reservations)
        {
            this.prix += reservation.getPrix();
        }
    }
    
    public void addReservations(Reservations reservation)
    {
        this.reservations.add(reservation);
        this.prix += reservation.getPrix();
    }
    
    public void removeReservations(Reservations reservation)
    {
        this.reservations.remove(reservation);
        this.prix -= reservation.getPrix();
    }
}
