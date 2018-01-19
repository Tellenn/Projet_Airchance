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
public class AvionFret implements Avion, TableInterface{

  

    private int idAvion;
    private int poidsDispo;
    private int volumeDispo;
    
    public AvionFret(){
        this.idAvion = 0;
        this.poidsDispo = 0;
        this.volumeDispo = 0;
    }
    
    public AvionFret(int idAvion, int poidsDispo, int volumeDispo){
        this.idAvion = idAvion;
        this.poidsDispo = poidsDispo;
        this.volumeDispo = volumeDispo;
    }
    
    
    @Override
    public int getIdAvion() {
        return this.idAvion;
    }

    @Override
    public void setIdAvion(int idAvion) {
       this.idAvion = idAvion;
    }

  /**
     * @return the poidsDispo
     */
    public int getPoidsDispo() {
        return poidsDispo;
    }

    /**
     * @param poidsDispo the poidsDispo to set
     */
    public void setPoidsDispo(int poidsDispo) {
        this.poidsDispo = poidsDispo;
    }

    /**
     * @return the volumeDispo
     */
    public int getVolumeDispo() {
        return volumeDispo;
    }

    /**
     * @param volumeDispo the volumeDispo to set
     */
    public void setVolumeDispo(int volumeDispo) {
        this.volumeDispo = volumeDispo;
    }

    @Override
    public void showTable() {
        String query = "Select * from Avion where typeAvion='Fret'";
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
        String query = "Select * from Avion where typeAvion='Fret'"
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
