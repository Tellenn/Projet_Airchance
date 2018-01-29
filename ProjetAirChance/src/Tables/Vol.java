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
public class Vol implements TableInterface{

    
    private int numVol;
    private boolean type; // false : Passager true : Fret
    private int duree;
    private int distance;
    private int placesMinEco;
    private int placesMinAff;
    private int placesMinPrem;
    private int poidsMin;
    private Ville idVilleOrigine;
    private Ville idVilleDestination;
    
    public Vol(){
        this.numVol = 0;
        this.type = false;
        this.duree = 0;
        this.placesMinEco = 0;
        this.placesMinAff = 0;
        this.placesMinPrem = 0;
        this.distance = 0;
        this.poidsMin = 0;
        this.idVilleOrigine = new Ville();
        this.idVilleDestination = new Ville();
    }
    
    public Vol(int numVol, boolean type, int duree, int distance, int placesMinEco, int placesMinAff, int placesMinPrem, int poidsMin, int idVilleOrigine, int idVilleDestination){
        this.numVol = numVol;
        this.type = type;
        this.duree = duree;
        this.distance = distance;
        this.placesMinEco = placesMinEco;
        this.placesMinAff = placesMinAff;
        this.placesMinPrem = placesMinPrem;
        this.poidsMin = poidsMin;
        this.idVilleOrigine = new Ville();
        this.idVilleOrigine.importFromId(""+idVilleOrigine);
        this.idVilleDestination = new Ville();
        this.idVilleDestination.importFromId(""+idVilleDestination);
    }
    
    
    @Override
    public void showTable() {
        String query = "Select * from Vol";
        TableImpl.showTable(query);
    }

    @Override
    public ResultSet getResultSetFromId(String id) {
        String query = "Select * from Vol where numVol="+id;
        return TableImpl.getResultSet(query);
    }

    @Override
    public void importFromId(String id) {
        ResultSet result = getResultSetFromId(id);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'un Vol");
            }
            result.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec le numVol "+id);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        try {
            this.numVol = result.getInt("numVol");
            this.type = result.getInt("type") == 1;
            this.duree = result.getInt("duree");
            this.distance = result.getInt("distance");
            this.placesMinEco = result.getInt("placesMinEco");
            this.placesMinAff = result.getInt("placesMinAff");
            this.placesMinPrem = result.getInt("placesMinPrem");
            this.poidsMin = result.getInt("poidsMin");
            this.idVilleOrigine.importFromId("" + result.getInt("idVilleOrigine"));
            this.idVilleDestination.importFromId("" + result.getInt("idVilleDestination"));


        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
     // <editor-fold defaultstate="collapsed" desc=" GETTERS/SETTERS ">
    /**
     * @return the numVol
     */
    public int getNumVol() {
        return numVol;
    }

    /**
     * @param numVol the numVol to set
     */
    public void setNumVol(int numVol) {
        this.numVol = numVol;
    }

    /**
     * @return the type
     */
    public boolean isType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(boolean type) {
        this.type = type;
    }

    /**
     * @return the duree
     */
    public int getDuree() {
        return duree;
    }

    /**
     * @param duree the duree to set
     */
    public void setDuree(int duree) {
        this.duree = duree;
    }

    /**
     * @return the distance
     */
    public int getDistance() {
        return distance;
    }

    /**
     * @param distance the distance to set
     */
    public void setDistance(int distance) {
        this.distance = distance;
    }

    /**
     * @return the placesMinEco
     */
    public int getPlacesMinEco() {
        return placesMinEco;
    }

    /**
     * @param placesMinEco the placesMinEco to set
     */
    public void setPlacesMinEco(int placesMinEco) {
        this.placesMinEco = placesMinEco;
    }

    /**
     * @return the placesMinAff
     */
    public int getPlacesMinAff() {
        return placesMinAff;
    }

    /**
     * @param placesMinAff the placesMinAff to set
     */
    public void setPlacesMinAff(int placesMinAff) {
        this.placesMinAff = placesMinAff;
    }

    /**
     * @return the placesMinPrem
     */
    public int getPlacesMinPrem() {
        return placesMinPrem;
    }

    /**
     * @param placesMinPrem the placesMinPrem to set
     */
    public void setPlacesMinPrem(int placesMinPrem) {
        this.placesMinPrem = placesMinPrem;
    }

    /**
     * @return the poidsMin
     */
    public int getPoidsMin() {
        return poidsMin;
    }

    /**
     * @param poidsMin the poidsMin to set
     */
    public void setPoidsMin(int poidsMin) {
        this.poidsMin = poidsMin;
    }

    /**
     * @return the idVilleOrigine
     */
    public Ville getIdVilleOrigine() {
        return idVilleOrigine;
    }

    /**
     * @param idVilleOrigine the idVilleOrigine to set
     */
    public void setIdVilleOrigine(Ville idVilleOrigine) {
        this.idVilleOrigine = idVilleOrigine;
    }

    /**
     * @return the idVilleDestination
     */
    public Ville getIdVilleDestination() {
        return idVilleDestination;
    }

    /**
     * @param idVilleDestination the idVilleDestination to set
     */
    public void setIdVilleDestination(Ville idVilleDestination) {
        this.idVilleDestination = idVilleDestination;
    }

// </editor-fold>


    
}
