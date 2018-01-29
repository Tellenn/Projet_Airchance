/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

/**
 *
 * @author Pault
 */
public class ResaFret implements Reservations
{
    private int numReservation;
    private InstanceVol vol;
    private int prix;
    private int poids;
    private int volume;

    public ResaFret(int numReservation, InstanceVol vol, int poids, int volume)
    {
        this.numReservation = numReservation;
        this.vol = vol;
        this.poids = poids;
        this.volume = volume;
        this.prix = this.poids*this.volume/100;
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

    public int getPoids()
    {
        return poids;
    }

    public void setPoids(int poids)
    {
        this.poids = poids;
        this.prix = this.poids*this.volume/100;
    }

    public int getVolume()
    {
        return volume;
    }

    public void setVolume(int volume)
    {
        this.volume = volume;
        this.prix = this.poids*this.volume/100;
    }
    
    @Override
    public int getPrix()
    {
        return this.prix;
    }
    
}
