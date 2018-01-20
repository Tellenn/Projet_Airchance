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
    private Modele nomModele;
    private int poidsDispo;
    private int volumeDispo;
    
    public AvionFret(){
        this.idAvion = 0;
        this.nomModele = new Modele();
        this.poidsDispo = 0;
        this.volumeDispo = 0;
    }
    
    public AvionFret(int idAvion, String nomModele, int poidsDispo, int volumeDispo){
        this.idAvion = idAvion;
        this.poidsDispo = poidsDispo;
        this.volumeDispo = volumeDispo;
        this.nomModele = new Modele();
        this.nomModele.setFromId(nomModele);
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
    
       /**
     * @return the nomModele
     */
    public Modele getNomModele() {
        return nomModele;
    }

    /**
     * @param nomModele the nomModele to set
     */
    public void setNomModele(Modele nomModele) {
        this.nomModele = nomModele;
    }

    @Override
    public void showTable() {
        String query = "Select * from Avion where typeAvion='Fret'";
        TableImpl.showTable(query);
    }

    @Override
    public ResultSet getResultSetFromId(String id) {
        String query = "Select * from Avion where typeAvion='Fret'"
                + "and idAvion='"+id+"'";
           
        return TableImpl.getResultSet(query);
    }

    @Override
    public void setFromId(String id){
        ResultSet result = getResultSetFromId(id);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'un avionFret");
                result.beforeFirst();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec l'id "+id);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        try {
            this.idAvion = result.getInt("idAvion");
            this.nomModele.setFromId(result.getString("nomModele"));
            this.poidsDispo = result.getInt("poidsDispo");
            this.volumeDispo = result.getInt("volumeDispo");
            this.nomModele.setFromId(result.getString("nomModele"));
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
            

    }
    
}
