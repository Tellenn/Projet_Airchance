/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BD.DBManager;
import Tables.Langue;
import Tables.PNC;
import Tables.PNT;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andréas
 */
public class ExportDAL {
    
    
    public int readMaxIdPN(){
        try {
            String query = "Select max(idEmploye) from PersonnelNaviguant";
            ResultSet res;
            res = DBManager.dbExecuteQuery(query);
            res.next();
            return res.getInt(1);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
    
    public void exportPNT(ArrayList<PNT> pntArray){
        int maxId;
        String query;
        
        for (PNT pnt : pntArray){
            if (pnt.getIdEmploye() == 0){
                maxId = readMaxIdPN() +1;
                query = "Insert into PersonnelNaviguant values ("+maxId+", '"+pnt.getNomEmploye()+"', '"+pnt.getPrenomEmploye()+"', '"
            + pnt.getNumRueEmploye()+"', '"+pnt.getRueEmploye()+"', '"+pnt.getCpEmploye()+"', '"+pnt.getVilleEmploye()+"', "+pnt.getHeuresVol()+", 'PNT', "+pnt.getIdDerniereVille().getIdVille()+")";
            }else{
                maxId = pnt.getIdEmploye();
                query = "Update PersonnelNaviguant set nomEmploye='"+pnt.getNomEmploye()+"', prenomEmploye='"+pnt.getPrenomEmploye()+"', numRueEmploye='"+pnt.getNumRueEmploye()+"', "
                        + "rueEmploye='"+pnt.getRueEmploye()+"', cpEmploye='"+pnt.getCpEmploye()+"', villeEmploye='"+pnt.getVilleEmploye()+"', heuresVol="+pnt.getHeuresVol()+", idDerniereVille="+pnt.getIdDerniereVille().getIdVille()+""
                        + " Where idEmploye="+maxId;
            }
            
            
            try {
                DBManager.dbExecuteUpdate(query);
                DBManager.commit();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void exportPNT(PNT pnt){
        ArrayList<PNT> newArrayPNT = new ArrayList<>();
        newArrayPNT.add(pnt);
        exportPNT(newArrayPNT);
    }
    
    /**
     * exportPNC -> void
     * Exporte les PNC présents en paramètre. 
     * On différencie si c'est un nouveau PNC à insérer ou une mise à jour avec l'idEmployr : si c'est 0, c'est un nouveau PNC.
     * Met aussi à jour la table LanguePNC
     * Exemple :
     * ArrayList<String> lang = new ArrayList<>();
        lang.add("Anglais");
        lang.add("Francais");
        lang.add("Allemand");
        ExportDAL dalExp = new ExportDAL();
        PNC test = new PNC(0, "Dubooooosc", "Frank", "39", "rue de la Chimie", "38100", "St martin truc", 0, 1, lang);
        dalExp.exportPNC(test);

     * @param pncArray 
     */
    public void exportPNC(ArrayList<PNC> pncArray){
        int maxId;
        String query, queryLangue;
        
        for (PNC pnc : pncArray){
            if (pnc.getIdEmploye() == 0){
                maxId = readMaxIdPN() +1;
                query = "Insert into PersonnelNaviguant values ("+maxId+", '"+pnc.getNomEmploye()+"', '"+pnc.getPrenomEmploye()+"', '"
            + pnc.getNumRueEmploye()+"', '"+pnc.getRueEmploye()+"', '"+pnc.getCpEmploye()+"', '"+pnc.getVilleEmploye()+"', "+pnc.getHeuresVol()+", 'PNC', "+pnc.getIdDerniereVille().getIdVille()+")";
                

            }else{
                maxId = pnc.getIdEmploye();
                query = "Update PersonnelNaviguant set nomEmploye='"+pnc.getNomEmploye()+"', prenomEmploye='"+pnc.getPrenomEmploye()+"', numRueEmploye='"+pnc.getNumRueEmploye()+"', "
                        + "rueEmploye='"+pnc.getRueEmploye()+"', cpEmploye='"+pnc.getCpEmploye()+"', villeEmploye='"+pnc.getVilleEmploye()+"', heuresVol="+pnc.getHeuresVol()+", idDerniereVille="+pnc.getIdDerniereVille().getIdVille()+""
                        + " Where idEmploye="+maxId;
            }
            
            
                try{
                    
                DBManager.dbExecuteUpdate(query);
                         
                String queryDeleteLangue = "Delete from LanguePNC where idEmploye="+maxId;
                DBManager.dbExecuteUpdate(queryDeleteLangue);

                
                for(Langue jack : pnc.getLangues()){
                    queryLangue = "Insert into LanguePNC values ('"+jack.getNomLangue()+"', "+maxId+")";
                    try {
                        DBManager.dbExecuteUpdate(queryLangue);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                

                DBManager.commit();
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void exportPNC(PNC pnc){
        ArrayList<PNC> newArrayPnc = new ArrayList<>();
        newArrayPnc.add(pnc);
        exportPNC(newArrayPnc);
    }

}
