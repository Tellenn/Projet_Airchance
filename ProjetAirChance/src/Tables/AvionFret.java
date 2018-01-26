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
public class AvionFret implements Avion, TableInterface{

  

    private int idAvion;
    private Modele nomModele;
    private int poidsDispo;
    private int volumeDispo;
    private Ville idDerniereVille;
    
    public AvionFret(){
        this.idAvion = 0;
        this.nomModele = new Modele();
        this.poidsDispo = 0;
        this.volumeDispo = 0;
        this.idDerniereVille = new Ville();
    }
    
    public AvionFret(int idAvion, String nomModele, int poidsDispo, int volumeDispo, int idDerniereVille){
        this.idAvion = idAvion;
        this.poidsDispo = poidsDispo;
        this.volumeDispo = volumeDispo;
        this.nomModele = new Modele();
        this.nomModele.importFromId(nomModele);
        this.idDerniereVille = new Ville();
        this.idDerniereVille.importFromId(""+idDerniereVille);
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
     * @return the poidsDispo
     */
    public int getPoidsDispo() {
        return poidsDispo;
    }

    /**
     * @param poidsDispo the poidsDispo to set
     */
    public void setPoidsDispo(int poidsDispo) {
        this.poidsDispo = poidsDispo;
    }

    /**
     * @return the volumeDispo
     */
    public int getVolumeDispo() {
        return volumeDispo;
    }

    /**
     * @param volumeDispo the volumeDispo to set
     */
    public void setVolumeDispo(int volumeDispo) {
        this.volumeDispo = volumeDispo;
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
        //String query = "Select * from Avion where typeAvion='fret'";
        String query = "Select * from Avion where typeAvion='fret'";
        TableImpl.showTable(query);
    }

    @Override
    public ResultSet getResultSetFromId(String id) {
        String query = "Select * from Avion where typeAvion='Fret'"
                + "and idAvion="+id;
           
        return TableImpl.getResultSet(query);
    }

    @Override
    public void importFromId(String id){
        ResultSet result = getResultSetFromId(id);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'un avionFret");
                
            }
            result.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec l'id "+id);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        try {
            this.idAvion = result.getInt("idAvion");
            this.nomModele.importFromId(result.getString("nomModele"));
            this.poidsDispo = result.getInt("poidsDispo");
            this.volumeDispo = result.getInt("volumeDispo");
            this.nomModele.importFromId(result.getString("nomModele"));
            this.idDerniereVille.importFromId(""+result.getInt(("idDerniereVille")));
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
              

    }
    
    /**
     * importTable -> ArrayList<AvionFret>
     * récupère dans l'ArrayList les AvionFret obtenus grâce à queryTable
     * si queryTable est null ou "", sélectionne tous les AvionFret de la table
     * @param queryTable
     * @return 
     */
    public static ArrayList<AvionFret> importTable(String queryTable){
        String query;
        if (queryTable != null && !queryTable.equals("") ){
            query = queryTable;
        }else{
            query = "Select * from Avion where typeAvion='fret'";
        }
        
        ResultSet result;
        ArrayList<AvionFret> avionF = new ArrayList<>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            while(result.next()){
                int idAvion = result.getInt("idAvion");
                String nomModele = result.getString("nomModele");
                int poids = result.getInt("poidsDispo");
                int volume = result.getInt("volumeDispo");
                int idVille = result.getInt("idDerniereVille");
                AvionFret tmp = new AvionFret(idAvion, nomModele, poids, volume, idVille);
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
    public static ArrayList<AvionFret> importTableWithParameter(int idAvion, Modele nomModele, int poidsDispo, int volumeDispo, Ville idDerniereVille){
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
        
        return importTable(query);
        
    }
        
    
    
}
