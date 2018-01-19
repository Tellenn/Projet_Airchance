/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import sql.DBManager;

/**
 *
 * @author Andréas
 */
public class AvionPassager implements Avion, TableInterface{

    private int idAvion;
    
    private int placesEco;
    private int placesAffaire;
    private int placesPrem;
    
    
    public AvionPassager(){
        this.idAvion = 0;
        this.placesEco = 0;
        this.placesAffaire = 0;
        this.placesPrem = 0;
    }
    
    public AvionPassager(int idAvion, int placesEco, int placesAffaire, int placesPrem){
        this.idAvion = idAvion;
        this.placesAffaire = placesAffaire;
        this.placesEco = placesEco;
        this.placesPrem = placesPrem;
    }
    
    
    
    
    @Override
    public int getIdAvion(){
        return this.idAvion;
    }

    @Override
    public void setIdAvion(int idAvion) {
        this.idAvion = idAvion;
    }
    
    
    
    /**
     * @return the placesEco
     */
    public int getPlacesEco() {
        return placesEco;
    }

    /**
     * @param placesEco the placesEco to set
     */
    public void setPlacesEco(int placesEco) {
        this.placesEco = placesEco;
    }

    /**
     * @return the placesAffaire
     */
    public int getPlacesAffaire() {
        return placesAffaire;
    }

    /**
     * @param placesAffaire the placesAffaire to set
     */
    public void setPlacesAffaire(int placesAffaire) {
        this.placesAffaire = placesAffaire;
    }

    /**
     * @return the placesPrem
     */
    public int getPlacesPrem() {
        return placesPrem;
    }

    /**
     * @param placesPrem the placesPrem to set
     */
    public void setPlacesPrem(int placesPrem) {
        this.placesPrem = placesPrem;
    }

    @Override
    public void showTable() {
        
        String query = "Select * from Avion where typeAvion='Passager'";
        ResultSet result = null;
        ResultSetMetaData rsmd = null;
        int columnsNumber = 0;
        
        try {
            result = DBManager.dbExecuteQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
        try {
            rsmd = result.getMetaData();
            columnsNumber = rsmd.getColumnCount();
            System.out.println("Results :\n");
            while(result.next()){
                for(int i = 1; i <= columnsNumber; i++){
                    if (i > 1) System.out.println(",  ");
                    String columnValue = result.getString(i);
                    System.out.println(columnValue + " "+rsmd.getColumnName(i));
                }
                System.out.println("");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        
    }

    @Override
    public ResultSet getResultSetFromId(String id) {
        String query = "Select * from Avion where typeAvion='Passager'"
                + "and idAvion='"+id+"'";
        
        ResultSet result = null;
        
         try {
            result = DBManager.dbExecuteQuery(query);
        } catch (SQLException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         return result;
    }

    
    
}
