/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import BD.DBManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


/**
 *
 * @author Pault
 */
public class ReservationPassager implements Reservations, TableInterface
{

    // <editor-fold defaultstate="collapsed" desc=" GETTERS/SETTERS ">
    /**
     * @return the numReservationP
     */
    public int getNumReservation() {
        return numReservationP;
    }

    /**
     * @param numReservationP the numReservationP to set
     */
    public void setNumReservation(int numReservationP) {
        this.numReservationP = numReservationP;
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
     * @return the numPlace
     */
    public Place getNumPlace() {
        return numPlace;
    }

    /**
     * @param numPlace the numPlace to set
     */
    public void setNumPlace(Place numPlace) {
        this.numPlace = numPlace;
    }

    /**
     * @return the idAvion
     */
    public Avion getIdAvion() {
        return idAvion;
    }

    /**
     * @param idAvion the idAvion to set
     */
    public void setIdAvion(Avion idAvion) {
        this.idAvion = idAvion;
    }

// </editor-fold>


    private int numReservationP;
    private float prix;
    private InstanceVol numInstance;
    private Place numPlace;
    private Avion idAvion;
 
    // <editor-fold defaultstate="collapsed" desc=" CONSTRUCTOR RESERVATIONPASSAGER ">
    public ReservationPassager() {
        this.numReservationP = 0;
        this.prix = 0;
        this.numInstance = new InstanceVol();
        this.numPlace = new Place();
        this.idAvion = new AvionPassager();        
    }
    
    public ReservationPassager(int numReservation, float prix, InstanceVol numInstance, Place numPlace, AvionPassager idAvion) {
        this.numReservationP = numReservation;
        this.prix = prix;
        this.numInstance = numInstance;
        this.numPlace = numPlace;
        this.idAvion = idAvion;
    }

// </editor-fold>
    
    
    @Override
    public void showTable() {
        String query1 = "Select * from ReservationPassager";
        TableImpl.showTable(query1);
        String query2 = "Select * from ResaVolPlace";
        TableImpl.showTable(query2);
    }


    @Override
    public void importFromId(String id) {
        ResultSet result = getResultSetFromId(id);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'une ResaVolPlace");
            }
            result.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec le numReservationP "+id);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
                
            
        try {
            this.numReservationP = result.getInt("numReservationP");
            this.numInstance.importFromId(""+result.getInt("numInstance"));
            this.numPlace.importFromId(""+result.getInt("numPlace"), ""+result.getInt("idAvion"), ""+result.getInt("numInstance"));
            this.prix = result.getFloat("prix");
            this.idAvion.importFromId("idAvion");

        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ReservationPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public ResultSet getResultSetFromId(String id) {
        String query = "Select * from ResaVolPlace where numReservationP="+id;
        return TableImpl.getResultSet(query);
    }



}
