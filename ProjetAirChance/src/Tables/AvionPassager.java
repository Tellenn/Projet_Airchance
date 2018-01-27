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
import static Tables.AvionFret.importTable;
import java.util.ArrayList;

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
    private Ville ville;
    private ArrayList<Place> avionPlaces;

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
        this.ville.importFromId(""+idVille);
        this.avionPlaces = new ArrayList<Place>();
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

    public void setPlaces(DBManager manager, String numInstance)
    {
        ResultSet placeTot;
        ResultSet placeReservee;
        ArrayList<String> tabPlaceRes = new ArrayList<String>();
        try {
          
            //on récupère les places déjà reservées dans un arrayList
            String Query = "SELECT numPlace from ResaVolPlace where idAvion ='"+idAvion+"' and numInstance ='"+ numInstance +"'"; 
            placeReservee = manager.dbExecuteQuery(Query);
            while(placeReservee.next())
            {
                tabPlaceRes.add(placeReservee.getNString("numPlace"));
            }
            
            //On récupère toutes les places de l'avion
            Query = "SELECT numPlace,position,classe from Place where idAvion ='"+idAvion+"'"; 
            placeTot = manager.dbExecuteQuery(Query);
            
            //On créer les places
            while(placeTot.next())
            {
                int numPlace = placeTot.getInt("numPlace");
                if (tabPlaceRes.indexOf(numPlace)== -1)
                {
                    //Si elle est reservée
                    this.avionPlaces.add(new Place(numPlace,placeTot.getNString("position"),placeTot.getNString("classe"),false));
                }else
                {
                    //sinon
                    this.avionPlaces.add(new Place(numPlace,placeTot.getNString("position"),placeTot.getNString("classe"),true));
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }   
    
    //création des places (aucune place reservée)
    public void setPlaces(DBManager manager)
    {
        ResultSet placeTot;
        ResultSet placeReservee;
        try {
          
          
            //On récupère toutes les places de l'avion
            String Query = "SELECT numPlace,position,classe from Place where idAvion ='"+idAvion+"'"; 
            placeTot = manager.dbExecuteQuery(Query);
            
            //On créer les places
            while(placeTot.next())
            {
                int numPlace = placeTot.getInt("numPlace");
                this.avionPlaces.add(new Place(numPlace,placeTot.getNString("position"),placeTot.getNString("classe"),false));
            }
        } catch (SQLException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void importFromId(String id) {
        ResultSet result = getResultSetFromId(id);
        try {
            if (result.last()) {
                int rows = result.getRow();
                if (rows > 1) {
                    throw new Exception("La requête a renvoyé plus d'un avionPassager");
                }
                
            }
            result.beforeFirst();
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
            this.nomModele.importFromId(result.getString("nomModele"));
        } catch (SQLException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
     /**
     * importTable -> ArrayList<AvionPassager>
     * récupère dans l'ArrayList les AvionPassagers obtenus grâce à queryTable
     * si queryTable est null ou "", sélectionne tous les AvionPassagers de la table
     * @param queryTable la requête qui va permettre de récupérer les avions de la table
     * @return ArrayList<AvionPassager>
     */
    public static ArrayList<AvionPassager> importTable(String queryTable){
        String query;
        if (queryTable != null && !queryTable.equals("") ){
            query = queryTable;
        }else{
            query = "Select * from Avion where typeAvion='passager'";
        }
        
        ResultSet result;
        ArrayList<AvionPassager> avionP = new ArrayList<>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            while(result.next()){
                int idAvion = result.getInt("idAvion");
                String nomModele = result.getString("nomModele");
                int placePrem = result.getInt("placePrem");
                int placeAff = result.getInt("placeAff");
                int placeEco = result.getInt("placeEco");
                int idVille = result.getInt("idDerniereVille");
                AvionPassager tmp = new AvionPassager(idAvion, nomModele, placePrem, placeAff,placeEco, idVille);
                avionP.add(tmp);
            }
        
            
        } catch (SQLException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return avionP;
    }
    
    
    /**
     * Il faut passer 0 si on ne veut pas utiliser un parametre int, "" pour une string et null pour un autre objet
     * @param idAvion 
     * @param nomModele
     * @param placePrem
     * @param placeAff
     * @param placeEco
     * @param idDerniereVille
     * @return ArrayList<AvionPassager> correspondant aux parametres passés
     */
    public static ArrayList<AvionPassager> importTableWithParameter(int idAvion, Modele nomModele, int placePrem, int placeAff, int placeEco, Ville idDerniereVille){
        String query = "Select * from Avion where typeAvion='passager'";
        if (idAvion != 0){
            query += " and idAvion="+idAvion;
        }
        if (nomModele != null){
            query += " and nomModele='"+nomModele.getNomModele()+"'";
        }
        if (placePrem != 0){
            query += " and placePrem<="+placePrem;
        }
        
        if (placeAff != 0){
            query += " and placeAff<="+placeAff;
        }
        
        if (placeEco != 0){
            query += " and placeEco<="+placeEco;
        }
        
        if (idDerniereVille != null){
            query += "and idDerniereVille="+idDerniereVille.getIdVille();
        }
        
        return importTable(query);
        
    }

}
