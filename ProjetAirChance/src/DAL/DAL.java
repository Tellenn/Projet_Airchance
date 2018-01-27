/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BD.DBManager;
import Tables.AvionFret;
import Tables.AvionPassager;
import Tables.Modele;
import Tables.Place;
import Tables.Ville;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andréas
 */
public class DAL {
     /**
     * importTable -> ArrayList<AvionFret>
     * récupère dans l'ArrayList les AvionFret obtenus grâce à queryTable
     * @return 
     */
    public ArrayList<AvionFret> importTableAvionFret(){
        return importTableAvionFret(0, null, 0, 0, null);
    }
    
    /**
     * importTableWithParameter(int idAvion, Modele nomModele, int poidsDispo, int volumeDispo, Ville idDerniereVille) -> ArrayList<AvionFret>
     * récupère les AvionFret de la base avec les paramètre de sélection entrés
     * si on ne veut pas inclure un paramètre de type int, il faut lui mettre 0
     * si on ne veut pas inclure un paramètre de type Objet, il faut lui mettre null
     * @param idAvion
     * @param nomModele
     * @param poidsDispo
     * @param volumeDispo
     * @param idDerniereVille
     * @return 
     */
    public ArrayList<AvionFret> importTableAvionFret(int idAvion, Modele nomModele, int poidsDispo, int volumeDispo, Ville idDerniereVille){
        String query = "Select * from Avion where typeAvion='fret'";
        if (idAvion != 0){
            query += " and idAvion="+idAvion;
        }
        if (nomModele != null){
            query += " and nomModele='"+nomModele.getNomModele()+"'";
        }
        if (poidsDispo != 0){
            query += " and poidsDispo="+poidsDispo;
        }
        
        if (volumeDispo != 0){
            query += " and volumeDispo="+volumeDispo;
        }
        
        if (idDerniereVille != null){
            query += "and idDerniereVille="+idDerniereVille.getIdVille();
        }
        
        
        ResultSet result;
        ArrayList<AvionFret> avionF = new ArrayList<>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            while(result.next()){
                int idAvionRes = result.getInt("idAvion");
                String nomModeleRes = result.getString("nomModele");
                int poidsRes = result.getInt("poidsDispo");
                int volumeRes = result.getInt("volumeDispo");
                int idVilleRes = result.getInt("idDerniereVille");
                AvionFret tmp = new AvionFret(idAvionRes, nomModeleRes, poidsRes, volumeRes, idVilleRes);
                avionF.add(tmp);
            }
        
            
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return avionF;
        
    }
    
    /**
     * importTable -> ArrayList<AvionPassager>
     * récupère dans l'ArrayList les AvionPassagers obtenus grâce à queryTable
     * si queryTable est null ou "", sélectionne tous les AvionPassagers de la table
     * @param queryTable la requête qui va permettre de récupérer les avions de la table
     * @return ArrayList<AvionPassager>
     */
    public static ArrayList<AvionPassager> importTableAvionPassager(String queryTable){
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
    public static ArrayList<AvionPassager> importAvionPassagerWithParameter(int idAvion, Modele nomModele, int placePrem, int placeAff, int placeEco, Ville idDerniereVille){
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
        
        return importTableAvionPassager(query);
    }
    
    
    
    public static ArrayList<Place> importPlaceWithParameter(int numPlace, int idAvion, String position, String classe, String numInstance){
        String query = "Select * from Place where idAvion="+idAvion;
        if (numPlace != 0){
            query += " and numPlace="+numPlace;
        }
        if (!position.equals("")){
            query += " and position='"+position+"'";
        }
        
        if (!classe.equals("")){
            query += " and classe<="+classe;
        }
        ArrayList<Place> placeTot = importTablePlace(query);
        
        if (!numInstance.equals(""))
        {
            query = "Select * from ResaVolPlace natural join Place where idAvion="+idAvion;
            if (numPlace != 0){
                query += " and numPlace="+numPlace;
            }
            if (!position.equals("")){
                query += " and position='"+position+"'";
            }
            if (!classe.equals("")){
                query += " and classe='"+classe+"'";
            }
            if (!numInstance.equals("")){
                query += " and numInstance='"+numInstance+"'";
            }
            ArrayList<String> placeRes = getPlaceRes(query);

            for (int i=0;i<placeTot.size();i++)
            {
                if(placeRes.indexOf(""+placeTot.get(i).getNumPlace())!=-1)
                {
                    placeTot.get(i).setRes(true);
                }
            }
        }
        
        return placeTot;        
    }
    
    private static ArrayList<String> getPlaceRes(String queryTable) {
        String query;
        if (queryTable != null && !queryTable.equals("") ){
            query = queryTable;
        }else{
            query = "Select numPlace from ResaVolPlace";
        }
         ResultSet result;
        ArrayList<String> placesAvion = new ArrayList<String>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            while(result.next()){
                int numPlace = result.getInt("numPlace");
                placesAvion.add(""+numPlace);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return placesAvion;
    }
    
    private static ArrayList<Place> importTablePlace(String queryTable) {
        String query;
        boolean estRes = false;
        if (queryTable != null && !queryTable.equals("") ){
            query = queryTable;
        }else{
            query = "Select * from Place";
        }
        
        ResultSet result;
        ArrayList<Place> placesAvion = new ArrayList<>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            while(result.next()){
                int numPlace = result.getInt("numPlace");
                int idAvion = result.getInt("idAvion");
                String classe = result.getString("classe");
                String position = result.getString("position");
                
                
                
                Place tmp = new Place(numPlace, idAvion, classe, position,estRes);
                placesAvion.add(tmp);
            }
        
            
        } catch (SQLException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return placesAvion;
    }
    
}
