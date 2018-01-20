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
public class LanguePNC {
    
    private Langue nomLangue;
    private PersonnelNavigant idEmploye;
    
    
    public LanguePNC(){
        this.nomLangue = new Langue();
        this.idEmploye = new PNC();
    }
    
    public LanguePNC(Langue nomLangue, PNC idEmploye){
        this.nomLangue = nomLangue;
        this.idEmploye = idEmploye;
    }
    
    public void showTable(){
        String query = "Select * from LanguePNC";
        TableImpl.showTable(query);
    }
    
    public ResultSet getResultSetFromId(Langue nomLangue, PNC idEmploye){
        String query = "Select * from LanguePNC where nomLangue='"+nomLangue.getNomLangue()+"'"
                + " and idEmploye="+idEmploye.getIdEmploye();
        return TableImpl.getResultSet(query);
    }
    
    public void setFromId(Langue nomLangue, PNC idEmploye){
        ResultSet result = getResultSetFromId(nomLangue, idEmploye);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'un LanguePNC");
                result.beforeFirst();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec le nomLangue "+nomLangue+" et l'idEmploye "+idEmploye);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        try {
            this.nomLangue.setFromId(result.getString("nomLangue"));
            ((PNC)this.idEmploye).setFromId(""+result.getInt("idEmploye"));

        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    
    }
    
    /**
     * @return the nomLangue
     */
    public Langue getNomLangue() {
        return nomLangue;
    }

    /**
     * @param nomLangue the nomLangue to set
     */
    public void setNomLangue(Langue nomLangue) {
        this.nomLangue = nomLangue;
    }

    /**
     * @return the idEmploye
     */
    public PersonnelNavigant getIdEmploye() {
        return idEmploye;
    }

    /**
     * @param idEmploye the idEmploye to set
     */
    public void setIdEmploye(PersonnelNavigant idEmploye) {
        this.idEmploye = idEmploye;
    }
    
}
