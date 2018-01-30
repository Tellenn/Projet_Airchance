/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pault
 */
public class ReservationFret implements Reservations, TableInterface
{

  
    private int numReservationF;
    private InstanceVol numInstance;
    private float prix;
    private int poids;
    private int volume;

    public ReservationFret(int numReservation, InstanceVol vol, int poids, int volume)
    {
        this.numReservationF = numReservation;
        this.poids = poids;
        this.volume = volume;
        this.prix = this.poids*this.volume/100;
        this.numInstance = vol;
    }
    
    public ReservationFret(){
        this.numReservationF = 0;
        this.numInstance = new InstanceVol();
        this.poids = 0;
        this.prix = 0;
        this.volume = 0;
    }

   
// <editor-fold defaultstate="collapsed" desc=" GETTERS/SETTERS ">
    public int getPoids() {
        return poids;
    }
    
    public void setPoids(int poids) {
        this.poids = poids;
        this.setPrix(this.poids * this.volume / 100);
    }
    
    public int getVolume() {
        return volume;
    }
    
    public void setVolume(int volume) {
        this.volume = volume;
        this.setPrix(this.poids * this.volume / 100);
    }

    /**
     * @return the numReservationF
     */
    @Override
    public int getNumReservation() {
        return numReservationF;
    }

    /**
     * @param numReservationF the numReservationF to set
     */
    @Override
    public void setNumReservation(int numReservationF) {
        this.numReservationF = numReservationF;
    }

    /**
     * @return the numInstance
     */
    public InstanceVol getNumInstance() {
        return numInstance;
    }

    /**
     * @param numInstance the numInstance to set
     */
    public void setNumInstance(InstanceVol numInstance) {
        this.numInstance = numInstance;
    }

    /**
     * @return the prix
     */
    public float getPrix() {
        return prix;
    }

    /**
     * @param prix the prix to set
     */
    public void setPrix(float prix) {
        this.prix = prix;
    }

// </editor-fold>
 



    @Override
    public void importFromId(String id) {
        ResultSet result = getResultSetFromId(id);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'une ReservationPassager");
            }
            result.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec le numReservationF "+id);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
                
            
        try {
            this.numReservationF = result.getInt("numReservationF");
            this.numInstance.importFromId(""+result.getInt("numInstance"));
            this.setVolume(result.getInt("volume"));
            this.setPoids(result.getInt("poids"));

        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ReservationPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }


    @Override
    public void showTable() {
        String query = "Select * from ReservationFret";
        TableImpl.showTable(query);
    }

    @Override
    public ResultSet getResultSetFromId(String id) {
        String query = "Select * from ReservationFret where numReservationF="+id;
        return TableImpl.getResultSet(query);
    }
    
}
