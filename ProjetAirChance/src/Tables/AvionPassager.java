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
public class AvionPassager implements Avion, TableInterface {

    private int idAvion;
    private Modele nomModele;
    private int placesEco;
    private int placesAffaire;
    private int placesPrem;

    public AvionPassager() {
        this.idAvion = 0;
        this.placesEco = 0;
        this.placesAffaire = 0;
        this.placesPrem = 0;
        this.nomModele = new Modele();
    }

    public AvionPassager(int idAvion, String nomModele, int placesEco, int placesAffaire, int placesPrem) {
        this.idAvion = idAvion;
        this.placesAffaire = placesAffaire;
        this.placesEco = placesEco;
        this.placesPrem = placesPrem;
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
     * @return the placesEco
     */
    public int getPlacesEco() {
        return placesEco;
    }

    /**
     * @param placesEco the placesEco to set
     */
    public void setPlacesEco(int placesEco) {
        this.placesEco = placesEco;
    }

    /**
     * @return the placesAffaire
     */
    public int getPlacesAffaire() {
        return placesAffaire;
    }

    /**
     * @param placesAffaire the placesAffaire to set
     */
    public void setPlacesAffaire(int placesAffaire) {
        this.placesAffaire = placesAffaire;
    }

    /**
     * @return the placesPrem
     */
    public int getPlacesPrem() {
        return placesPrem;
    }

    /**
     * @param placesPrem the placesPrem to set
     */
    public void setPlacesPrem(int placesPrem) {
        this.placesPrem = placesPrem;
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

        String query = "Select * from Avion where typeAvion='Passager'";
        TableImpl.showTable(query);

    }

    @Override
    public ResultSet getResultSetFromId(String id) {
        String query = "Select * from Avion where typeAvion='Passager'"
                + "and idAvion=" + id;

        return TableImpl.getResultSet(query);
    }

    @Override
    public void setFromId(String id) {
        ResultSet result = getResultSetFromId(id);
        try {
            if (result.last()) {
                int rows = result.getRow();
                if (rows > 1) {
                    throw new Exception("La requête a renvoyé plus d'un avionPassager");
                }
                result.beforeFirst();
            }
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (!result.next()) {
                throw new Exception("La requête n'a pas abouti avec l'id " + id);
            }
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            this.idAvion = result.getInt("idAvion");
            this.placesAffaire = result.getInt("placesAffaire");
            this.placesEco = result.getInt("placesEco");
            this.placesPrem = result.getInt("placesPrem");
            this.nomModele.setFromId(result.getString("nomModele"));
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
