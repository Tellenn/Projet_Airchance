/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pault
 */
public class InstanceVol implements TableInterface{

    /**
     * @return the personnel
     */
    public ArrayList<PersonnelNavigant> getPersonnel() {
        return personnel;
    }

    /**
     * @param personnel the personnel to set
     */
    public void setPersonnel(ArrayList<PersonnelNavigant> personnel) {
        this.personnel = personnel;
    }

    
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
    public String getDateArrive() {
        return dateArrive;
    }

    /**
     * @param dateArrive the dateArrive to set
     */
    public void setDateArrive(String dateArrive) {
        this.dateArrive = dateArrive;
    }

    /**
     * @return the dateDepart
     */
    public String getDateDepart() {
        return dateDepart;
    }

    /**
     * @param dateDepart the dateDepart to set
     */
    public void setDateDepart(String dateDepart) {
        this.dateDepart = dateDepart;
    }

    /**
     * @return the etat
     */
    public String getEtat() {
        return etat;
    }

    /**
     * @param etat the etat to set
     */
    public void setEtat(String etat) {
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
        this.dateDepart = "";
        this.etat = "Cree";
    }
    
    public InstanceVol(int numInstance, int numVol, int idAvion, int placesRestEco, int placesRestAff, int placesRestPrem, int poidsrest, String dateDepart,String dateArrivee, String etat) {
        this.numInstance = numInstance;
        this.numVol = new Vol();
        this.numVol.importFromId("" + numVol);
        try {
            this.idAvion = ("passagers".equals(readTypeAvion(idAvion))) ? new AvionPassager() : new AvionFret();
        } catch (Exception ex) {
            Logger.getLogger(InstanceVol.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.idAvion.importFromId("" + idAvion);
        this.placesRestEco = placesRestEco;
        this.placesRestAff = placesRestAff;
        this.placesRestPrem = placesRestPrem;
        this.dateArrive = dateArrivee;
        this.dateDepart = dateDepart;
        this.etat = etat;
    }

// </editor-fold>


    // https://stackoverflow.com/questions/530012/how-to-convert-java-util-date-to-java-sql-date
    // https://stackoverflow.com/questions/907170/java-getminutes-and-gethours
    private int numInstance;
    private Vol numVol;
    private Avion idAvion;
    private int placesRestEco;
    private int placesRestPrem;
    private int placesRestAff;
    private int poidsRest;
    private String dateArrive;
    private String dateDepart;
    private String etat;
    private ArrayList<PersonnelNavigant> personnel;


    
    private String readTypeAvion(int idAvion) throws Exception{
        try {
            String query = "Select * from Avion where idAvion="+idAvion;
            ResultSet result;
            
            result = TableImpl.getResultSet(query);
            result.next();
            return result.getString("typeAvion");
        } catch (SQLException ex) {
            Logger.getLogger(InstanceVol.class.getName()).log(Level.SEVERE, null, ex);
        }
        throw new Exception("Le type d'avion n'a pas pu etre lu avec l'idAvion="+idAvion);

    }
    
      @Override
    public void showTable() {
        String query = "Select * from InstanceVol";
        TableImpl.showTable(query);
    }

    @Override
    public ResultSet getResultSetFromId(String id) {
        String query = "Select * from InstanceVol where numInstance="+id;
        return TableImpl.getResultSet(query);
    }

    @Override
    public void importFromId(String id) {
        ResultSet result = getResultSetFromId(id);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'un InstanceVol");
            }
            result.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec le numInstance "+id);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        

            
        try {
            this.numInstance = result.getInt("numInstance");
            this.numVol = new Vol();
            this.numVol.importFromId(""+result.getInt("numVol"));
            this.idAvion = ("passagers".equals(readTypeAvion(result.getInt("idAvion")))) ? new AvionPassager() : new AvionFret();
            this.idAvion.importFromId(""+result.getInt("idAvion"));
            this.placesRestEco = result.getInt("placesRestEco");
            this.placesRestAff = result.getInt("placesRestAff");
            this.placesRestPrem = result.getInt("placesRestPrem");
            this.poidsRest = result.getInt("poidsRest");
            
            SimpleDateFormat simple = new SimpleDateFormat("yyyy/MM/dd' 'hh:mm:ss");
            java.util.Date dateDepartRes = result.getDate("dateDepart");
            this.dateDepart = simple.format(dateDepartRes);
            
            java.util.Date dateArriveeTmp = result.getDate("dateArrivee");
            String dateArriveeRes = (dateArriveeTmp == null) ? "" : simple.format(result.getDate("dateArrivee"));   
            this.dateArrive = dateArriveeRes;
  
            this.etat = result.getString("etat");


        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(InstanceVol.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    
}
