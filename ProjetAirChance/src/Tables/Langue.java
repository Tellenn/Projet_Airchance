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
 * @author Andréas
 */
public class Langue implements TableInterface{

   

    private String nomLangue;
    
    public Langue(){
        this.nomLangue = "";
    }
    
    public Langue(String nomLangue){
        this.nomLangue = nomLangue;
    }
    
    
     /**
     * @return the nomLangue
     */
    public String getNomLangue() {
        return nomLangue;
    }

    /**
     * @param nomLangue the nomLangue to set
     */
    public void setNomLangue(String nomLangue) {
        this.nomLangue = nomLangue;
    }
    
    @Override
    public void showTable() {
        String query = "Select * from Langue";
        TableImpl.showTable(query);
    }

    @Override
    public ResultSet getResultSetFromId(String nomLangue) {
        String query = "Select * from Langue where nomLangue='"+nomLangue+"'";
        return TableImpl.getResultSet(query);
    }

    @Override
    public void setFromId(String nomLangue) {
        ResultSet result = getResultSetFromId(nomLangue);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'une Langue");
                result.beforeFirst();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec le nomLangue "+nomLangue);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        try {
            this.nomLangue = result.getString("nomLangue");

        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
