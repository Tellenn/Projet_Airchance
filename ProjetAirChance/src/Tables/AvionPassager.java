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
import BD.DBManager;
import java.util.ArrayList;

/**
 *
 * @author Andréas
 */
public class AvionPassager implements Avion, TableInterface
{

    private int idAvion;
    private Modele nomModele;
    private int placesEco;
    private int placesAffaire;
    private int placesPrem;
    private Ville ville;
    private ArrayList<Place> avionPlaces;

// <editor-fold defaultstate="collapsed" desc=" CONSTRUCTOR AVIONPASSAGER ">
    public AvionPassager() {
        this.idAvion = 0;
        this.placesEco = 0;
        this.placesAffaire = 0;
        this.placesPrem = 0;
        this.nomModele = new Modele();
        this.avionPlaces = new ArrayList<Place>();
    }
    
    public AvionPassager(int idAvion, String nomModele, int placesEco, int placesAffaire, int placesPrem, int idVille) {
        this.idAvion = idAvion;
        this.placesAffaire = placesAffaire;
        this.placesEco = placesEco;
        this.placesPrem = placesPrem;
        this.nomModele = new Modele();
        this.nomModele.importFromId(nomModele);
        this.ville = new Ville();
        this.ville.importFromId("" + idVille);
        this.avionPlaces = new ArrayList<Place>();
    }

// </editor-fold>

// <editor-fold defaultstate="collapsed" desc=" GETTERS/SETTERS ">
    @Override
    public int getIdAvion() {
        return this.idAvion;
    }
    
    public ArrayList<Place> getPlaces() {
        return this.avionPlaces;
    }
    
    public Ville getIdDerniereVille() {
        return ville;
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
    public Modele getModele() {
        return nomModele;
    }

    /**
     * @param nomModele the nomModele to set
     */
    public void setNomModele(Modele nomModele) {
        this.nomModele = nomModele;
    }

// </editor-fold>

    @Override
    public void showTable()
    {

        String query = "Select * from Avion where typeAvion='passagers'";
        TableImpl.showTable(query);

    }

    @Override
    public ResultSet getResultSetFromId(String id)
    {
        String query = "Select * from Avion where typeAvion='passagers'"
                + "and idAvion=" + id;

        return TableImpl.getResultSet(query);
    }

    public void setPlaces(DBManager manager, String numInstance)
    {
        ResultSet placeTot;
        ResultSet placeReservee;
        ArrayList<String> tabPlaceRes = new ArrayList<String>();
        try
        {

            //on récupère les places déjà reservées dans un arrayList
            String Query = "SELECT numPlace from ResaVolPlace where idAvion ='" + idAvion + "' and numInstance ='" + numInstance + "'";
            placeReservee = manager.dbExecuteQuery(Query);
            while (placeReservee.next())
            {
                tabPlaceRes.add(placeReservee.getNString("numPlace"));
            }

            //On récupère toutes les places de l'avion
            Query = "SELECT numPlace,position,classe from Place where idAvion ='" + idAvion + "'";
            placeTot = manager.dbExecuteQuery(Query);

            //On créer les places
            while (placeTot.next())
            {
                int numPlace = placeTot.getInt("numPlace");
                if (tabPlaceRes.indexOf(numPlace) == -1)
                {
                    //Si elle est reservée
                    this.avionPlaces.add(new Place(numPlace, placeTot.getNString("position"), placeTot.getNString("classe"), false));
                } else
                {
                    //sinon
                    this.avionPlaces.add(new Place(numPlace, placeTot.getNString("position"), placeTot.getNString("classe"), true));
                }
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //création des places (aucune place reservée)
    public void setPlaces(DBManager manager)
    {
        ResultSet placeTot;
        ResultSet placeReservee;
        try
        {

            //On récupère toutes les places de l'avion
            String Query = "SELECT numPlace,position,classe from Place where idAvion ='" + idAvion + "'";
            placeTot = manager.dbExecuteQuery(Query);

            //On créer les places
            while (placeTot.next())
            {
                int numPlace = placeTot.getInt("numPlace");
                this.avionPlaces.add(new Place(numPlace, placeTot.getNString("position"), placeTot.getNString("classe"), false));
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void setPlaces(ArrayList<Place> places)
    {
       this.avionPlaces = places;
    }

    @Override
    public void importFromId(String id)
    {
        ResultSet result = getResultSetFromId(id);
        try
        {
            if (result.last())
            {
                int rows = result.getRow();
                if (rows > 1)
                {
                    throw new Exception("La requête a renvoyé plus d'un avionPassager");
                }

            }
            result.beforeFirst();
        } catch (SQLException ex)
        {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex)
        {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }

        try
        {
            if (!result.next())
            {
                throw new Exception("La requête n'a pas abouti avec l'id " + id);
            }
        } catch (Exception ex)
        {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }

        try
        {
            this.idAvion = result.getInt("idAvion");
            this.placesAffaire = result.getInt("placesAffaire");
            this.placesEco = result.getInt("placesEco");
            this.placesPrem = result.getInt("placesPrem");
            this.nomModele.importFromId(result.getString("nomModele"));
        } catch (SQLException ex)
        {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
