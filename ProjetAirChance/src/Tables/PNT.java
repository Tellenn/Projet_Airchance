/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andréas
 */
public class PNT implements PersonnelNavigant, TableInterface{



    private int idEmploye;
    private String nomEmploye;
    private String prenomEmploye;
    private String numRueEmploye;
    private String rueEmploye;
    private String cpEmploye;
    private String villeEmploye;
    private int heuresVol;
    private Ville idDerniereVille;
    
    public PNT(){
        this.idEmploye = 0;
        this.nomEmploye = "";
        this.prenomEmploye = "";
        this.numRueEmploye = "";
        this.rueEmploye = "";
        this.cpEmploye = "";
        this.villeEmploye = "";
        this.heuresVol = 0;
        this.idDerniereVille = new Ville();
    }
    
    public PNT(int idEmploye, String nomEmploye, String prenomEmploye, String numRueEmploye, String rueEmploye, String cpEmploye, String villeEmploye, int heuresVol, int idDerniereVille){
        this.idEmploye = idEmploye;
        this.nomEmploye = nomEmploye;
        this.prenomEmploye = prenomEmploye;
        this.numRueEmploye = numRueEmploye;
        this.rueEmploye = rueEmploye;
        this.cpEmploye = cpEmploye;
        this.villeEmploye = villeEmploye;
        this.heuresVol = heuresVol;
        this.idDerniereVille = new Ville();
        this.idDerniereVille.importFromId(idDerniereVille+"");
    }

    
    /**
     * @return the idEmploye
     */
    @Override
    public int getIdEmploye() {
        return idEmploye;
    }

    /**
     * @param idEmploye the idEmploye to set
     */
    @Override
    public void setIdEmploye(int idEmploye) {
        this.idEmploye = idEmploye;
    }

    /**
     * @return the nomEmploye
     */
    @Override
    public String getNomEmploye() {
        return nomEmploye;
    }

    /**
     * @param nomEmploye the nomEmploye to set
     */
    @Override
    public void setNomEmploye(String nomEmploye) {
        this.nomEmploye = nomEmploye;
    }

    /**
     * @return the prenomEmploye
     */
    @Override
    public String getPrenomEmploye() {
        return prenomEmploye;
    }

    /**
     * @param prenomEmploye the prenomEmploye to set
     */
    @Override
    public void setPrenomEmploye(String prenomEmploye) {
        this.prenomEmploye = prenomEmploye;
    }

    /**
     * @return the numRueEmploye
     */
    @Override
    public String getNumRueEmploye() {
        return numRueEmploye;
    }

    /**
     * @param numRueEmploye the numRueEmploye to set
     */
    @Override
    public void setNumRueEmploye(String numRueEmploye) {
        this.numRueEmploye = numRueEmploye;
    }

    /**
     * @return the rueEmploye
     */
    @Override
    public String getRueEmploye() {
        return rueEmploye;
    }

    /**
     * @param rueEmploye the rueEmploye to set
     */
    @Override
    public void setRueEmploye(String rueEmploye) {
        this.rueEmploye = rueEmploye;
    }

    /**
     * @return the cpEmploye
     */
    @Override
    public String getCpEmploye() {
        return cpEmploye;
    }

    /**
     * @param cpEmploye the cpEmploye to set
     */
    @Override
    public void setCpEmploye(String cpEmploye) {
        this.cpEmploye = cpEmploye;
    }

    /**
     * @return the villeEmploye
     */
    @Override
    public String getVilleEmploye() {
        return villeEmploye;
    }

    /**
     * @param villeEmploye the villeEmploye to set
     */
    @Override
    public void setVilleEmploye(String villeEmploye) {
        this.villeEmploye = villeEmploye;
    }

    /**
     * @return the heuresVol
     */
    @Override
    public int getHeuresVol() {
        return heuresVol;
    }

    /**
     * @param heuresVol the heuresVol to set
     */
    @Override
    public void setHeuresVol(int heuresVol) {
        this.heuresVol = heuresVol;
    }
    
        /**
     * @return the idDerniereVille
     */
    public Ville getIdDerniereVille() {
        return idDerniereVille;
    }

    /**
     * @param idDerniereVille the idDerniereVille to set
     */
    public void setIdDerniereVille(Ville idDerniereVille) {
        this.idDerniereVille = idDerniereVille;
    }
    
    
    @Override
    public void showTable() {
        String query = "Select * from PersonnelNaviguant where typePN='PNT'";
        TableImpl.showTable(query);
    }

    @Override
    public ResultSet getResultSetFromId(String id) {
        String query = "Select * from PersonnelNaviguant where typePN='PNT'"
                + "and idEmploye="+id;
        return TableImpl.getResultSet(query);
    }

    @Override
    public void importFromId(String id) {
        ResultSet result = getResultSetFromId(id);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'un PNT");
            }
            result.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec l'idEmploye "+id);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        try {
            this.idEmploye = result.getInt("idEmploye");
            this.nomEmploye = result.getString("nomEmploye");
            this.prenomEmploye = result.getString("prenomEmploye");
            this.numRueEmploye = result.getString("numRueEmploye");
            this.rueEmploye = result.getString("rueEmploye");
            this.cpEmploye = result.getString("cpEmploye");
            this.villeEmploye = result.getString("villeEmploye");
            this.heuresVol = result.getInt("heuresVol");
            this.idDerniereVille.importFromId(result.getInt("idDerniereVille")+"");

        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
    
    }
    
}
