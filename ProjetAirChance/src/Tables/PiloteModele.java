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
public class PiloteModele{

    

    private Modele nomModele;
    private PersonnelNavigant idEmploye;
    private int heuresModele;
    
    public PiloteModele(){
        this.nomModele = new Modele();
        this.idEmploye = new PNT();
        this.heuresModele = 0;
    }
    
    public PiloteModele(Modele nomModele, PNT idEmploye, int heuresModele){
        this.nomModele = nomModele;
        this.idEmploye = idEmploye;
        this.heuresModele = heuresModele;
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

    /**
     * @return the heuresModele
     */
    public int getHeuresModele() {
        return heuresModele;
    }

    /**
     * @param heuresModele the heuresModele to set
     */
    public void setHeuresModele(int heuresModele) {
        this.heuresModele = heuresModele;
    }
    
    public void showTable() {
        String query = "Select * from PiloteModele";
        TableImpl.showTable(query);
    }

    public ResultSet getResultSetFromId(Modele nomModele, PersonnelNavigant idEmploye) {
        String query = "Select * from PiloteModele where nomModele='"+nomModele.getNomModele()+"'"
                + " and idEmploye="+idEmploye.getIdEmploye();
        return TableImpl.getResultSet(query);
    }

    public void setFromId(Modele nomModele, PNT idEmploye) {
        ResultSet result = getResultSetFromId(nomModele, idEmploye);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'un PiloteModele");
            }
            result.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec le nomModele "+nomModele+" et l'idEmploye "+idEmploye);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        try {
            ((PNT)this.idEmploye).setFromId(""+result.getInt("idEmploye"));
            this.nomModele.setFromId(result.getString("nomModele"));
            this.heuresModele = result.getInt("heuresModele");
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    
    }
    
}
