/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import java.sql.Date;
import java.sql.ResultSet;

/**
 *
 * @author Pault
 */
public class InstanceVol implements TableInterface{

    
    // <editor-fold defaultstate="collapsed" desc=" GETTERS/SETTERS ">
    /**
     * @return the numVol
     */
    public Vol getNumVol() {
        return numVol;
    }

    /**
     * @param numVol the numVol to set
     */
    public void setNumVol(Vol numVol) {
        this.numVol = numVol;
    }

    /**
     * @return the idAvion
     */
    public Avion getIdAvion() {
        return idAvion;
    }

    /**
     * @param idAvion the idAvion to set
     */
    public void setIdAvion(Avion idAvion) {
        this.idAvion = idAvion;
    }

    
 
    /**
     * @return the numInstance
     */
    public int getNumInstance() {
        return numInstance;
    }

 
    /**
     * @param numInstance the numInstance to set
     */
    public void setNumInstance(int numInstance) {
        this.numInstance = numInstance;
    }


    /**
     * @return the placesRestEco
     */
    public int getPlacesRestEco() {
        return placesRestEco;
    }

    /**
     * @param placesRestEco the placesRestEco to set
     */
    public void setPlacesRestEco(int placesRestEco) {
        this.placesRestEco = placesRestEco;
    }

    /**
     * @return the placesRestPrem
     */
    public int getPlacesRestPrem() {
        return placesRestPrem;
    }

    /**
     * @param placesRestPrem the placesRestPrem to set
     */
    public void setPlacesRestPrem(int placesRestPrem) {
        this.placesRestPrem = placesRestPrem;
    }

    /**
     * @return the placesRestAff
     */
    public int getPlacesRestAff() {
        return placesRestAff;
    }

    /**
     * @param placesRestAff the placesRestAff to set
     */
    public void setPlacesRestAff(int placesRestAff) {
        this.placesRestAff = placesRestAff;
    }

    /**
     * @return the poidsRest
     */
    public int getPoidsRest() {
        return poidsRest;
    }

    /**
     * @param poidsRest the poidsRest to set
     */
    public void setPoidsRest(int poidsRest) {
        this.poidsRest = poidsRest;
    }

    /**
     * @return the dateArrive
     */
    public java.sql.Date getDateArrive() {
        return dateArrive;
    }

    /**
     * @param dateArrive the dateArrive to set
     */
    public void setDateArrive(java.sql.Date dateArrive) {
        this.dateArrive = dateArrive;
    }

    /**
     * @return the dateDepart
     */
    public java.sql.Date getDateDepart() {
        return dateDepart;
    }

    /**
     * @param dateDepart the dateDepart to set
     */
    public void setDateDepart(java.sql.Date dateDepart) {
        this.dateDepart = dateDepart;
    }

    /**
     * @return the etat
     */
    public EtatInstance getEtat() {
        return etat;
    }

    /**
     * @param etat the etat to set
     */
    public void setEtat(EtatInstance etat) {
        this.etat = etat;
    }

  

// </editor-fold>
    
    // <editor-fold defaultstate="collapsed" desc=" CONSTRUCTOR INSTANCEVOL ">
    public InstanceVol() {
        this.numInstance = 0;
        this.numVol = new Vol();
        this.idAvion = null;        
        this.placesRestEco = 0;
        this.placesRestAff = 0;
        this.placesRestPrem = 0;
        this.poidsRest = 0;
        this.dateArrive = null;
        this.dateDepart = new Date(new java.util.Date().getTime());
        this.etat = EtatInstance.CREE;
    }
    
    public InstanceVol(int numInstance, int numVol, int idAvion, int placesRestEco, int placesRestAff, int placesRestPrem, int poidsrest, java.sql.Date dateArrivee, java.sql.Date dateDepart, EtatInstance etat) {
        this.numInstance = numInstance;
        this.numVol = new Vol();
        this.numVol.importFromId("" + numVol);
        this.idAvion = (this.numVol.getType() == 1) ? new AvionPassager() : new AvionFret();
        this.idAvion.importFromId("" + idAvion);
        this.placesRestEco = placesRestEco;
        this.placesRestAff = placesRestAff;
        this.placesRestPrem = placesRestPrem;
        this.dateArrive = null;
        this.dateDepart = new Date(new java.util.Date().getTime());
        this.etat = etat;
    }

// </editor-fold>

    public enum EtatInstance{
        CREE, EN_COURS, ARRIVE, ANNULE
    }
    // https://stackoverflow.com/questions/530012/how-to-convert-java-util-date-to-java-sql-date
    private int numInstance;
    private Vol numVol;
    private Avion idAvion;
    private int placesRestEco;
    private int placesRestPrem;
    private int placesRestAff;
    private int poidsRest;
    private java.sql.Date dateArrive;
    private java.sql.Date dateDepart;
    private EtatInstance etat;


    
      @Override
    public void showTable() {
        String query = "Select * from InstanceVol";
        TableImpl.showTable(query);
    }

    @Override
    public ResultSet getResultSetFromId(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void importFromId(String id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
