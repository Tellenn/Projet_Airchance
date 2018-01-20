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
public class Modele implements TableInterface{
    
    private String nomModele;
    private int nbPilotes;
    private int rayonAction;
    
    public Modele(){
        this.nomModele = "";
        this.nbPilotes = 0;
        this.rayonAction = 0;
    }
    
    public Modele(String nomModele, int nbPilotes, int rayonAction){
        this.nomModele = nomModele;
        this.nbPilotes = nbPilotes;
        this.rayonAction = rayonAction;
    }
    
    
     /**
     * @return the nomModele
     */
    public String getNomModele() {
        return nomModele;
    }

    /**
     * @param nomModele the nomModele to set
     */
    public void setNomModele(String nomModele) {
        this.nomModele = nomModele;
    }

    /**
     * @return the nbPilotes
     */
    public int getNbPilotes() {
        return nbPilotes;
    }

    /**
     * @param nbPilotes the nbPilotes to set
     */
    public void setNbPilotes(int nbPilotes) {
        this.nbPilotes = nbPilotes;
    }

    /**
     * @return the rayonAction
     */
    public int getRayonAction() {
        return rayonAction;
    }

    /**
     * @param rayonAction the rayonAction to set
     */
    public void setRayonAction(int rayonAction) {
        this.rayonAction = rayonAction;
    }

    @Override
    public void showTable() {
        String query = "Select * from Modele";
        TableImpl.showTable(query);
    }

    @Override
    public ResultSet getResultSetFromId(String nomModele) {
        String query = "Select * from Modele where nomModele='"+nomModele+"'";
        return TableImpl.getResultSet(query);
    }

    @Override
    public void setFromId(String nomModele) {
        ResultSet result = getResultSetFromId(nomModele);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'un Modele");
                result.beforeFirst();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec le nomModele "+nomModele);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        try {
            this.nomModele = result.getString("nomModele");
            this.nbPilotes = result.getInt("nbPilotes");
            this.rayonAction = result.getInt("rayonAction");
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
