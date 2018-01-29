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
import Tables.PNC;
import Tables.PNT;
import Tables.Place;
import Tables.Ville;
import Tables.Vol;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andréas
 */
public class ImportDAL {
     
    
    
    /**
     * importTable -> ArrayList<AvionFret>
     * récupère dans l'ArrayList les AvionFret obtenus grâce à queryTable
     * @return 
     */
    public ArrayList<AvionFret> importTableAvionFret(){
        return importTableAvionFret(0, null, 0, 0, 0);
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
    public ArrayList<AvionFret> importTableAvionFret(int idAvion, Modele nomModele, int poidsDispo, int volumeDispo, int idDerniereVille){
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
        
        if (idDerniereVille != 0){
            query += "and idDerniereVille="+idDerniereVille;
        }
        
        
        ResultSet result;
        ArrayList<AvionFret> avionF = new ArrayList<>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            while(result.next()){
                int idAvionRes = result.getInt("idAvion");
                AvionFret tmp = new AvionFret();
                tmp.importFromId(""+idAvionRes);
                //AvionFret tmp = new AvionFret(idAvionRes, nomModeleRes, poidsRes, volumeRes, idVilleRes);
                avionF.add(tmp);
            }
        
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return avionF;
        
    }
    
    public ArrayList<PNC> importTablePNC(){
        return importTablePNC(0, "", "", "", "", "", "", 0, 0);
    }
    
    public ArrayList<PNC> importTablePNC(int idEmploye, String nomEmploye, String prenomEmploye, String numRueEmploye, String rueEmploye, String cpEmploye, String villeEmploye, int heuresVol, int idDerniereVille){
        String query = "Select * from PersonnelNaviguant where typePN='PNC'";
        if(idEmploye != 0){
            query += " and idEmploye="+idEmploye;
        }
        if (!"".equals(nomEmploye)){
            query += " and nomEmploye='"+nomEmploye+"'";
        }
        if (!"".equals(prenomEmploye)){
            query += " and prenomEmploye='"+prenomEmploye+"'";
        }
        if (!"".equals(numRueEmploye)){
            query += " and numRueEmploye='"+numRueEmploye+"'";
        }
        if (!"".equals(rueEmploye)){
            query += " and rueEmploye='"+rueEmploye+"'";
        }
        if (!"".equals(cpEmploye)){
            query += " and cpEmploye='"+cpEmploye+"'";
        }
        if (!"".equals(villeEmploye)){
            query += " and villeEmploye='"+villeEmploye+"'";
        }
        if (heuresVol != 0){
            query += " and heuresVol="+heuresVol;
        }
        if (idDerniereVille != 0){
            query += " and idDerniereVille="+idDerniereVille;
        }
        
        ResultSet result;
        ArrayList<PNC> pnc = new ArrayList<>();
        try {
            result = DBManager.dbExecuteQuery(query);

            
            
            while(result.next()){
                int idEmployeRes = result.getInt("idEmploye");
                String nomEmployeRes = result.getString("nomEmploye");
                String prenomEmployeRes = result.getString("prenomEmploye");
                String numRueEmployeRes = result.getString("numRueEmploye");
                String rueEmployeRes = result.getString("rueEmploye");
                String cpEmployeRes = result.getString("cpEmploye");
                String villeEmployeRes = result.getString("villeEmploye");
                int heuresVolRes = result.getInt("heuresVol");
                int idDerniereVilleRes = result.getInt("idDerniereVille");
                PNC tmp = new PNC(idEmployeRes, nomEmployeRes, prenomEmployeRes, numRueEmployeRes, rueEmployeRes, cpEmployeRes, villeEmployeRes, heuresVolRes, idDerniereVilleRes);
                tmp.fillLanguePNC();

                //PNC tmp = new PNC(idEmployeRes, nomEmployeRes, prenomEmployeRes, numRueRes, rueEmployeRes, cpEmployeRes, villeEmployeRes, heuresVolRes, idDerRes, languePNC);
                pnc.add(tmp);
            }
        
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pnc;
    }
    
    public ArrayList<PNT> importTablePNT(){
        return importTablePNT(0, "", "", "", "", "", "", 0, 0);
    }
    
    public ArrayList<PNT> importTablePNT(int idEmploye, String nomEmploye, String prenomEmploye, String numRueEmploye, String rueEmploye, String cpEmploye, String villeEmploye, int heuresVol, int idDerniereVille){
        String query = "Select * from PersonnelNaviguant where typePN='PNT'";
        if(idEmploye != 0){
            query += " and idEmploye="+idEmploye;
        }
        if (!"".equals(nomEmploye)){
            query += " and nomEmploye='"+nomEmploye+"'";
        }
        if (!"".equals(prenomEmploye)){
            query += " and prenomEmploye='"+prenomEmploye+"'";
        }
        if (!"".equals(numRueEmploye)){
            query += " and numRueEmploye='"+numRueEmploye+"'";
        }
        if (!"".equals(rueEmploye)){
            query += " and rueEmploye='"+rueEmploye+"'";
        }
        if (!"".equals(cpEmploye)){
            query += " and cpEmploye='"+cpEmploye+"'";
        }
        if (!"".equals(villeEmploye)){
            query += " and villeEmploye='"+villeEmploye+"'";
        }
        if (heuresVol != 0){
            query += " and heuresVol="+heuresVol;
        }
        if (idDerniereVille != 0){
            query += " and idDerniereVille="+idDerniereVille;
        }
        
        ResultSet result;
        ArrayList<PNT> pnt = new ArrayList<>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            
            while(result.next()){
                int idEmployeRes = result.getInt("idEmploye");
                String nomEmployeRes = result.getString("nomEmploye");
                String prenomEmployeRes = result.getString("prenomEmploye");
                String numRueEmployeRes = result.getString("numRueEmploye");
                String rueEmployeRes = result.getString("rueEmploye");
                String cpEmployeRes = result.getString("cpEmploye");
                String villeEmployeRes = result.getString("villeEmploye");
                int heuresVolRes = result.getInt("heuresVol");
                int idDerniereVilleRes = result.getInt("idDerniereVille");
                PNT tmp = new PNT(idEmployeRes, nomEmployeRes, prenomEmployeRes, numRueEmployeRes, rueEmployeRes, cpEmployeRes, villeEmployeRes, heuresVolRes, idDerniereVilleRes);
                //PNC tmp = new PNC(idEmployeRes, nomEmployeRes, prenomEmployeRes, numRueRes, rueEmployeRes, cpEmployeRes, villeEmployeRes, heuresVolRes, idDerRes, languePNC);
                tmp.fillPiloteModele();
                pnt.add(tmp);
            }
        
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return pnt;
    }
    
    
    public ArrayList<Modele> importTableModele(String nomModele, int nbPilotes, int rayonAction){
        String query = "Select * from Modele";
        boolean isTheFirst = true;
        
        if(!"".equals(nomModele)){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " nomModele='"+nomModele+"'";
        }
        if (nbPilotes != 0){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " nbPilotes="+nbPilotes;
        }
        if (rayonAction != 0){
            query += isTheFirst ? " where" : " and";
            query += " rayonAction="+rayonAction;
        }
        
        
        ResultSet result;
        ArrayList<Modele> modeles = new ArrayList<>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            
            while(result.next()){
                String nomModeleRes = result.getString("nomModele");
                int nbPilotesRes = result.getInt("nbPilotes");
                int rayonActionRes = result.getInt("rayonAction");
                Modele tmp = new Modele(nomModeleRes, nbPilotesRes, rayonActionRes);
                modeles.add(tmp);
            }
        
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return modeles;
    }
    
    public ArrayList<Ville> importTableVille(int idVille, String nomVille, String paysVille){
        String query = "Select * from Ville";
        boolean isTheFirst = true;
        
        if(idVille != 0){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " nomModele='"+idVille+"'";
        }
        if (!nomVille.equals("")){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " nbPilotes="+nomVille;
        }
        if (!paysVille.equals("")){
            query += isTheFirst ? " where" : " and";
            query += " rayonAction="+paysVille;
        }
        
        
        ResultSet result;
        ArrayList<Ville> villes = new ArrayList<>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            
            while(result.next()){
                int idVilleRes = result.getInt("idVille");
                String nomVilleRes = result.getString("nomVille");
                String paysVilleRes = result.getString("paysVille");
                Ville tmp = new Ville(idVilleRes, nomVilleRes, paysVilleRes);
                villes.add(tmp);
            }
        
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return villes;
    }
    
    public ArrayList<Ville> importTableVille(){
        return importTableVille(0, "", "");
    }
    
    public ArrayList<Vol> importTableVol(int numVol, int type, int duree, int distance, int placesMinEco, int placesMinAff, int placesMinPrem, int poidsMin, int idVilleOrigine, int idVilleDestination){
        String query = "Select * from Vol";
        boolean isTheFirst = true;
        if(numVol != 0){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " numVol="+numVol;
        }
        if (type != 0){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " type="+type;
        }
        if (duree != 0){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " duree="+duree;
        }
        if (distance != 0){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " distance="+distance;
        }
        if (placesMinEco != 0){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " placesMinEco="+placesMinEco;
        }
        if (placesMinAff != 0){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " placesMinAff="+placesMinAff;
        }
        if (placesMinPrem != 0){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " placesMinPrem="+placesMinPrem;
        }
        if (poidsMin != 0){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " poidsMin="+poidsMin;
        }
        if (idVilleOrigine != 0){
            query += isTheFirst ? " where" : " and";
            isTheFirst = false;
            query += " idVilleOrigine="+idVilleOrigine;
        }
        if (idVilleDestination != 0){
            query += " and idVilleDestination="+idVilleDestination;
        }
        
        ResultSet result;
        ArrayList<Vol> vol = new ArrayList<>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            
            while(result.next()){
                int numVolRes = result.getInt("numVol");
                int typeres = result.getInt("type") +1 ;
                int dureeRes = result.getInt("duree");
                int distanceRes = result.getInt("distance");
                int placesMinEcoRes = result.getInt("placesMinEco");
                int placesMinAffRes = result.getInt("placesMinAff");
                int placesMinPremRes = result.getInt("placesMinPrem");
                int poidsMinRes = result.getInt("poidsMin");
                int idVillOrigineRes = result.getInt("idVilleOrigine");
                int idVilleDestinationRes = result.getInt("idVilleDestination");
                Vol tmp = new Vol(numVolRes, typeres, dureeRes, distanceRes, placesMinEcoRes, placesMinAffRes, placesMinPremRes, poidsMinRes, idVillOrigineRes, idVilleDestinationRes);
                //PNC tmp = new PNC(idEmployeRes, nomEmployeRes, prenomEmployeRes, numRueRes, rueEmployeRes, cpEmployeRes, villeEmployeRes, heuresVolRes, idDerRes, languePNC);
                vol.add(tmp);
            }
        
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return vol;
    }
    
    public ArrayList<Vol> importTableVol(){
        return importTableVol(0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
    }
    
    /**
     * importTable -> ArrayList<AvionPassager>
     * récupère dans l'ArrayList les AvionPassagers
     * @return ArrayList<AvionPassager>
     */
    public static ArrayList<AvionPassager> importTableAvionPassager(){
        
        return importTableAvionPassager(0, null, 0, 0, 0, null);
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
    public static ArrayList<AvionPassager> importTableAvionPassager(int idAvion, Modele nomModele, int placePrem, int placeAff, int placeEco, Ville idDerniereVille){
        String query = "Select * from Avion where typeAvion='passager'";
        if (idAvion != 0){
            query += " and idAvion="+idAvion;
        }
        if (nomModele != null){
            query += " and nomModele='"+nomModele.getNomModele()+"'";
        }
        if (placePrem != 0){
            query += " and placePrem=>"+placePrem;
        }
        
        if (placeAff != 0){
            query += " and placeAff=>"+placeAff;
        }
        
        if (placeEco != 0){
            query += " and placeEco=>"+placeEco;
        }
        
        if (idDerniereVille != null){
            query += "and idDerniereVille="+idDerniereVille.getIdVille();
        }
        
        ResultSet result;
        ArrayList<AvionPassager> avionP = new ArrayList<>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            while(result.next()){
                int idAvionRes = result.getInt("idAvion");
                String nomModeleRes = result.getString("nomModele");
                int placePremRes = result.getInt("placePrem");
                int placeAffRes = result.getInt("placeAff");
                int placeEcoRes = result.getInt("placeEco");
                int idVilleRes = result.getInt("idDerniereVille");
                AvionPassager tmp = new AvionPassager(idAvionRes, nomModeleRes, placePremRes, placeAffRes,placeEcoRes, idVilleRes);
                avionP.add(tmp);
            }
        
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return avionP;
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
