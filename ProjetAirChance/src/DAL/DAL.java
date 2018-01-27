/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BD.DBManager;
import Tables.AvionFret;
import Tables.Modele;
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
    
    
}
