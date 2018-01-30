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


    private int numReservation;
    private String dateReservation;
    private Client idClient;
    private ArrayList<ResaVolPlace> reservations;
    
    
    // <editor-fold defaultstate="collapsed" desc=" CONSTRUCTOR RESERVATIONPASSAGER ">
    public ReservationPassager() {
        this.numReservation = 0;
        this.idClient = new Client();
        this.dateReservation = "";
        this.reservations = new ArrayList<>();
    }
    
    public ReservationPassager(int numReservation, Client cli, String date, ArrayList<ResaVolPlace> reserv) {
        this.numReservation = numReservation;
        this.idClient = cli;
        this.dateReservation = date;
        this.reservations = reserv;
    }

// </editor-fold>
    


    
// <editor-fold defaultstate="collapsed" desc=" GETTERS/SETTERS ">
    @Override
    public int getNumReservation() {
        return numReservation;
    }
    
    @Override
    public void setNumReservation(int numReservation) {
        this.numReservation = numReservation;
    }

       /**
     * @return the idClient
     */
    public Client getIdClient() {
        return idClient;
    }

    /**
     * @param idClient the idClient to set
     */
    public void setIdClient(Client idClient) {
        this.idClient = idClient;
    }
  /**
     * @return the reservations
     */
    public ArrayList<ResaVolPlace> getReservations() {
        return reservations;
    }

    /**
     * @param reservations the reservations to set
     */
    public void setReservations(ArrayList<ResaVolPlace> reservations) {
        this.reservations = reservations;
    }

    /**
     * @return the dateReservation
     */
    @Override
    public String getDateReservation() {
        return dateReservation;
    }

    /**
     * @param dateReservation the dateReservation to set
     */
    @Override
    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
    }

// </editor-fold>
    
    
    public void fillResaVolPlace(){
        String query = "Select * from ResaVolPlace where numReservationP="+this.numReservation;
        ResultSet result;
        
        try {
            result = DBManager.dbExecuteQuery(query);
            while(result.next()){
                ResaVolPlace r = new ResaVolPlace(this.numReservation, result.getInt("numInstance"), result.getInt("numPlace"), result.getFloat("prix"));
                reservations.add(r);
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(InstanceVol.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void importFromId(String id)
    {
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
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec le numReservationP "+id);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        try {
            this.numReservation = result.getInt("numReservationP");
            this.idClient.importFromId(""+result.getInt("idClient"));
            
            SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd' 'hh:mm:ss");
            java.util.Date dateR = result.getDate("dateReservation");
            this.dateReservation = simple.format(dateR);

        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void showTable() {
        String query = "Select * from ReservationPassager";
        TableImpl.showTable(query);
    }

    @Override
    public ResultSet getResultSetFromId(String id) {
        String query = "Select * from ReservationPassager where numReservationP="+id;
        return TableImpl.getResultSet(query);
    }


}
