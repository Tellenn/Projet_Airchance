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
public class Ville implements TableInterface{

   
    
    private int idVille;
    private String nomVille;
    private String paysVille;
    
    
    public Ville(){
        this.idVille = 0;
        this.nomVille = "";
        this.paysVille = "";
    }
    
    public Ville(int idVille, String nomVille, String paysVille){
        this.idVille = idVille;
        this.nomVille = nomVille;
        this.paysVille = paysVille;
    }
    
     /**
     * @return the idVille
     */
    public int getIdVille() {
        return idVille;
    }

    /**
     * @param idVille the idVille to set
     */
    public void setIdVille(int idVille) {
        this.idVille = idVille;
    }

    /**
     * @return the nomVille
     */
    public String getNomVille() {
        return nomVille;
    }

    /**
     * @param nomVille the nomVille to set
     */
    public void setNomVille(String nomVille) {
        this.nomVille = nomVille;
    }

    /**
     * @return the paysVille
     */
    public String getPaysVille() {
        return paysVille;
    }

    /**
     * @param paysVille the paysVille to set
     */
    public void setPaysVille(String paysVille) {
        this.paysVille = paysVille;
    }

    @Override
    public void showTable() {
        String query = "Select * from Ville";
        TableImpl.showTable(query);
    }

    @Override
    public ResultSet getResultSetFromId(String id) {
       String query = "Select * from Ville where idVille="+id;
       return TableImpl.getResultSet(query);
    }

    @Override
    public void importFromId(String id) {
        ResultSet result = getResultSetFromId(id);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'une Ville");
            }
            result.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec l'idVille "+id);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        try {
            this.idVille = result.getInt("idVille");
            this.nomVille = result.getString("nomVille");
            this.paysVille = result.getString("paysVille");

        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
