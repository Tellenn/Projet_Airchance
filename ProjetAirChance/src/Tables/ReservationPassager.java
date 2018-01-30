/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import java.sql.Date;

/**
 *
 * @author Pault
 */
public class ReservationPassager implements Reservations
{
    private int numReservation;
    private InstanceVol vol;
    private Place place;
    private int prix;

    public ReservationPassager(int numReservation, InstanceVol vol, Place place) throws Exception
    {
        this.numReservation = numReservation;
        this.vol = vol;
        if (place.getRes()== true)
        {
            throw new Exception("La place est déja reservée");
        }
        else
        {
            this.place = place;
            if (place.getClasse().equals("eco"))
            {
                this.prix = 100;
            }else if (place.getClasse().equals("affaire"))
            {
                this.prix = 500;
            }else if (place.getClasse().equals("prem"))
            {
                this.prix = 1000;
            }
        }
    }
    
    

    public int getNumReservation()
    {
        return numReservation;
    }

    public void setNumReservation(int numReservation)
    {
        this.numReservation = numReservation;
    }

    

    public InstanceVol getVol()
    {
        return vol;
    }

    public void setVol(InstanceVol vol)
    {
        this.vol = vol;
    }

    public Place getPlace()
    {
        return place;
    }

    public void setPlace(Place place) throws Exception
    {
        if (place.getRes()== true)
        {
            throw new Exception("La place est déja reservée");
        }
        else
        {
            this.place = place;
        }
    }    

    @Override
    public int getPrix()
    {
        return prix;
    }

    @Override
    public String getDateReservation()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setDateReservation(String date)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void importFromId(String id)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
