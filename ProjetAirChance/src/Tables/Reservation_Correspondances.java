/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import BD.DBManager;
import java.sql.Date;
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
public class Reservation_Correspondances {

// <editor-fold defaultstate="collapsed" desc=" GETTERS/SETTERS ">
    public void setReservations(ArrayList<Reservations> reservations) {
        this.reservations = reservations;
        this.setPrix(0);
        for (Reservations reservation : reservations) {
            this.setPrix(this.getPrix() + reservation.getPrix());
        }
    }

    public void addReservations(Reservations reservation) {

        this.reservations.add(reservation);
        this.setPrix(this.getPrix() + reservation.getPrix());
    }

    public void removeReservations(Reservations reservation) {
        this.reservations.remove(reservation);
        this.setPrix(this.getPrix() - reservation.getPrix());
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
     * @return the reservations
     */
    public ArrayList<Reservations> getReservations() {
        return reservations;
    }

    /**
     * @return the dateReservation
     */
    public String getDateReservation() {
        return dateReservation;
    }

    /**
     * @param dateReservation the dateReservation to set
     */
    public void setDateReservation(String dateReservation) {
        this.dateReservation = dateReservation;
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


    

// </editor-fold>
    private float prix;
    private ArrayList<Reservations> reservations;
    private String dateReservation;
    private Client idClient;

    public Reservation_Correspondances(int idResa, int idClient, ArrayList<Reservations> reservations, float prix, String dateReservation) {

        this.reservations = reservations;
        this.dateReservation = dateReservation;
        this.prix = prix;
        this.idClient = new Client();
        this.idClient.importFromId(""+idClient);

    }

    public Reservation_Correspondances() {
        this.reservations = new ArrayList<>();
        this.prix = 0;
        this.dateReservation = "";
        this.idClient = new Client();
    }

    public void importFromIdClient(String id) {

        this.idClient.importFromId(id);
        importFromIdClientPassager(id);
        importFromIdClientFret(id);
    }

    public void importFromIdClientPassager(String id) {
        String query;
        ResultSet result;
        SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd' 'hh:mm:ss");

        query = "Select * from ReservationPassager where idClient=" + id;

        try {
            result = DBManager.dbExecuteQuery(query);
            while (result.next()) {
                ReservationPassager r = new ReservationPassager();

                java.util.Date dateReservRes = result.getDate("dateReservation");
                r.setDateReservation(simple.format(dateReservRes));

                r.importFromId("" + result.getInt("numReservationP"));
                this.addReservations(r);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Reservation_Correspondances.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void importFromIdClientFret(String id) {
        String query;
        ResultSet result;
        SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd' 'hh:mm:ss");
        query = "Select * from ReservationFret where idClient=" + id;

        try {
            result = DBManager.dbExecuteQuery(query);

            while (result.next()) {
                ReservationFret r = new ReservationFret();
                java.util.Date dateReservRes = result.getDate("dateReservation");
                if (dateReservRes != null){
                    r.setDateReservation(simple.format(dateReservRes));
                }
                

                r.importFromId(result.getString("numReservationF"));
                this.addReservations(r);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(Reservation_Correspondances.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
