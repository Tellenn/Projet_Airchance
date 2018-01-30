/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import BD.DBManager;
import Tables.Avion;
import Tables.AvionFret;
import Tables.AvionPassager;
import Tables.InstanceVol;
import Tables.Langue;
import Tables.Modele;
import Tables.PNC;
import Tables.PNT;
import Tables.Place;
import Tables.Ville;
import Tables.Vol;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andréas
 */
public class ExportDAL
{


    // <editor-fold defaultstate="collapsed" desc=" READERS MAX ">
    public int readMaxIdPN() {
        try {
            String query = "Select max(idEmploye) from PersonnelNaviguant";
            ResultSet res;
            res = DBManager.dbExecuteQuery(query);
            res.next();
            return res.getInt(1);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public int readMaxIdVille() {
        try {
            String query = "Select max(idVille) from Ville";
            ResultSet res;
            res = DBManager.dbExecuteQuery(query);
            res.next();
            return res.getInt(1);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public boolean isNomModeleExists(Modele mod) {
        String query = "Select * from Modele where nomModele='" + mod.getNomModele() + "'";
        ResultSet result;
        boolean itsHere = false;
        
        try {
            result = DBManager.dbExecuteQuery(query);
            itsHere = result.last();
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itsHere;
    }
    
    private int readMaxNumVol() {
        try {
            String query = "Select max(numVol) from Vol";
            ResultSet res;
            res = DBManager.dbExecuteQuery(query);
            res.next();
            return res.getInt(1);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    private int readMaxNumInstance() {
        try {
            String query = "Select max(NumInstance) from InstanceVol";
            ResultSet res;
            res = DBManager.dbExecuteQuery(query);
            res.next();
            return res.getInt(1);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public int readMaxIdAvion() {
        try {
            String query = "Select max(idAvion) from Avion";
            ResultSet res;
            res = DBManager.dbExecuteQuery(query);
            res.next();
            return res.getInt(1);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

// </editor-fold>

    public void exportPNT(ArrayList<PNT> pntArray)
    {
        int maxId;
        String query, queryPiloteModele;

        for (PNT pnt : pntArray)
        {
            if (pnt.getIdEmploye() == 0)
            {
                maxId = readMaxIdPN() + 1;
                query = "Insert into PersonnelNaviguant values (" + maxId + ", '" + pnt.getNomEmploye() + "', '" + pnt.getPrenomEmploye() + "', '"
                        + pnt.getNumRueEmploye() + "', '" + pnt.getRueEmploye() + "', '" + pnt.getCpEmploye() + "', '" + pnt.getVilleEmploye() + "', " + pnt.getHeuresVol() + ", 'PNT', " + pnt.getIdDerniereVille().getIdVille() + ")";
            } else
            {
                maxId = pnt.getIdEmploye();
                query = "Update PersonnelNaviguant set nomEmploye='" + pnt.getNomEmploye() + "', prenomEmploye='" + pnt.getPrenomEmploye() + "', numRueEmploye='" + pnt.getNumRueEmploye() + "', "
                        + "rueEmploye='" + pnt.getRueEmploye() + "', cpEmploye='" + pnt.getCpEmploye() + "', villeEmploye='" + pnt.getVilleEmploye() + "', heuresVol=" + pnt.getHeuresVol() + ", idDerniereVille=" + pnt.getIdDerniereVille().getIdVille() + ""
                        + " Where idEmploye=" + maxId;
            }

            try
            {
                DBManager.dbExecuteUpdate(query);
                DBManager.dbExecuteUpdate("Delete from PiloteModele where idEmploye=" + maxId);
                for (Modele mod : pnt.getPiloteModele().keySet())
                {
                    queryPiloteModele = "Insert into PiloteModele values ('" + mod.getNomModele() + "', " + maxId + ", " + pnt.getPiloteModele().get(mod) + ")";
                    try
                    {
                        DBManager.dbExecuteUpdate(queryPiloteModele);
                    } catch (SQLException | ClassNotFoundException ex)
                    {
                        Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (SQLException | ClassNotFoundException ex)
            {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void exportPNT(PNT pnt)
    {
        ArrayList<PNT> newArrayPNT = new ArrayList<>();
        newArrayPNT.add(pnt);
        exportPNT(newArrayPNT);
    }

    /**
     * exportPNC -> void Exporte les PNC présents en paramètre. On différencie
     * si c'est un nouveau PNC à insérer ou une mise à jour avec l'idEmployr :
     * si c'est 0, c'est un nouveau PNC. Met aussi à jour la table LanguePNC
     * Exemple : ArrayList<String> lang = new ArrayList<>();
     * lang.add("Anglais"); lang.add("Francais"); lang.add("Allemand");
     * ExportDAL dalExp = new ExportDAL(); PNC test = new PNC(0, "Dubooooosc",
     * "Frank", "39", "rue de la Chimie", "38100", "St martin truc", 0, 1,
     * lang); dalExp.exportPNC(test);
     *
     * @param pncArray
     */
    public void exportPNC(ArrayList<PNC> pncArray)
    {
        int maxId;
        String query, queryLangue;

        for (PNC pnc : pncArray)
        {
            if (pnc.getIdEmploye() == 0)
            {
                maxId = readMaxIdPN() + 1;
                query = "Insert into PersonnelNaviguant values (" + maxId + ", '" + pnc.getNomEmploye() + "', '" + pnc.getPrenomEmploye() + "', '"
                        + pnc.getNumRueEmploye() + "', '" + pnc.getRueEmploye() + "', '" + pnc.getCpEmploye() + "', '" + pnc.getVilleEmploye() + "', " + pnc.getHeuresVol() + ", 'PNC', " + pnc.getIdDerniereVille().getIdVille() + ")";

            } else
            {
                maxId = pnc.getIdEmploye();
                query = "Update PersonnelNaviguant set nomEmploye='" + pnc.getNomEmploye() + "', prenomEmploye='" + pnc.getPrenomEmploye() + "', numRueEmploye='" + pnc.getNumRueEmploye() + "', "
                        + "rueEmploye='" + pnc.getRueEmploye() + "', cpEmploye='" + pnc.getCpEmploye() + "', villeEmploye='" + pnc.getVilleEmploye() + "', heuresVol=" + pnc.getHeuresVol() + ", idDerniereVille=" + pnc.getIdDerniereVille().getIdVille() + ""
                        + " Where idEmploye=" + maxId;
            }

            try
            {

                DBManager.dbExecuteUpdate(query);

                String queryDeleteLangue = "Delete from LanguePNC where idEmploye=" + maxId;
                DBManager.dbExecuteUpdate(queryDeleteLangue);

                for (Langue jack : pnc.getLangues())
                {
                    queryLangue = "Insert into LanguePNC values ('" + jack.getNomLangue() + "', " + maxId + ")";
                    try
                    {
                        DBManager.dbExecuteUpdate(queryLangue);
                    } catch (SQLException | ClassNotFoundException ex)
                    {
                        Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (SQLException | ClassNotFoundException ex)
            {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void exportPNC(PNC pnc)
    {
        ArrayList<PNC> newArrayPnc = new ArrayList<>();
        newArrayPnc.add(pnc);
        exportPNC(newArrayPnc);
    }

    /**
     * Modele mod = new Modele(); mod.importFromId("A330"); mod.setNbPilotes(2);
     *
     * dalExp.exportModele(mod);
     *
     * @param modeles
     */
    public void exportModele(ArrayList<Modele> modeles)
    {
        String query;

        for (Modele mod : modeles)
        {
            if (!isNomModeleExists(mod))
            { // new modele, insertion
                query = "Insert into Modele values ('" + mod.getNomModele() + "', " + mod.getNbPilotes() + ", " + mod.getRayonAction() + ")";
            } else
            { // update un modele
                query = "Update Modele set nbPilotes=" + mod.getNbPilotes() + ", rayonAction=" + mod.getRayonAction() + " where nomModele='" + mod.getNomModele() + "'";
            }

            try
            {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex)
            {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void exportModele(Modele mod)
    {
        ArrayList<Modele> modeles = new ArrayList<>();
        modeles.add(mod);
        exportModele(modeles);
    }

    /**
     * ArrayList<Ville> villes = dal.importTableVille(); Ville v = new Ville(0,
     * "Grenoble", "FRANCE"); villes.add(v); dalExp.exportVille(villes);
     *
     * @param villes
     */
    public void exportVille(ArrayList<Ville> villes)
    {
        int maxId;
        String query;

        for (Ville ville : villes)
        {
            if ((maxId = ville.getIdVille()) != 0)
            {
                query = "Update Ville set nomVille='" + ville.getNomVille() + "', paysVille='" + ville.getPaysVille() + "' Where idVille=" + maxId;
            } else
            {
                maxId = readMaxIdVille() + 1;
                query = "Insert into Ville values (" + maxId + ", '" + ville.getNomVille() + "', '" + ville.getPaysVille() + "')";
            }

            try
            {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex)
            {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void exportVille(Ville v)
    {
        ArrayList<Ville> villes = new ArrayList<>();
        villes.add(v);
        exportVille(villes);
    }
    
    public void exportVol(ArrayList<Vol> vols){
        int maxId;
        String query;

        for (Vol vol : vols) {
            if ((maxId = vol.getNumVol()) != 0) {
                query = "Update Vol set type=" + (vol.getType()-1) + ", duree=" + vol.getDuree() + ", distance="+vol.getDistance()+", placesMinEco="+vol.getPlacesMinEco()+""
                        + ", placesMinAff="+vol.getPlacesMinAff()+", placesMinPrem="+vol.getPlacesMinPrem()+", poidsMin="+vol.getPoidsMin()+", idVilleOrigine="+vol.getIdVilleOrigine().getIdVille()+""
                        + ", idVilleDestination="+vol.getIdVilleDestination().getIdVille()
                        + " Where numVol=" + maxId;
            } else {
                maxId = readMaxNumVol()+1;
                query = "Insert into Vol values (" + maxId + ", " + (vol.getType()-1)+ ", " + vol.getDuree()+ ", "+vol.getDistance()+", "+vol.getPlacesMinEco()+""
                        + ", "+vol.getPlacesMinAff()+", "+vol.getPlacesMinPrem()+", "+vol.getPoidsMin()+", "+vol.getIdVilleOrigine().getIdVille()+", "+vol.getIdVilleDestination().getIdVille()+")";     
            }

            try {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void exportVol(Vol v){
        ArrayList<Vol> vols = new ArrayList<>();
        vols.add(v);
        exportVol(vols);
    }

    public void exportAvionPassager(AvionPassager a) throws Exception
    {
        ArrayList<AvionPassager> avions = new ArrayList<>();
        avions.add(a);
        exportAvionsPassager(avions);
    }

    public void exportAvionsPassager(ArrayList<AvionPassager> avions) throws Exception
    {
        int maxId;
        String query;
        for (AvionPassager avion : avions)
        {
            //insertion de l'avion
            if ((maxId = avion.getIdAvion()) != 0)
            {
                query = "Update Avion set nomModele='" + avion.getModele().getNomModele() + "', poidsDispo=0 , volumeDispo = 0, placesEco =" + avion.getPlacesEco()
                        + ",placesAffaire =" + avion.getPlacesAffaire() + ",placesPrem =" + avion.getPlacesPrem() + " Where idAvion=" + maxId;
            } else
            {
                maxId = readMaxIdAvion() + 1;
                query = "Insert into Avion Values (" + maxId + ",'" + avion.getModele().getNomModele() + "',0 ,0,"
                        + avion.getPlacesEco() + "," + avion.getPlacesAffaire() + "," + avion.getPlacesPrem()+",'passager',"+avion.getIdDerniereVille().getIdVille() + ")";
            }
            try
            {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex)
            {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("erreur lors de l'export d'avionPassager");
            }

            //suppression des places déjà existantes (dans le cas où on modifie un avion existant)
            query = "Delete from Place where idAvion =" + avion.getIdAvion();

            try
            {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex)
            {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("erreur lors de l'export d'avionPassager");
            }

            //insertion des places de l'avion
            for (Place p : avion.getPlaces())
            {
                query = "Insert into Place Values("+p.getNumPlace()+","+maxId+",'"+p.getPosition()+"','"+p.getClasse()+"')";
                try
                {
                    DBManager.dbExecuteUpdate(query);
                } catch (SQLException | ClassNotFoundException ex)
                {
                    Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                    throw new Exception("erreur lors de l'export d'avionPassager");
                }
            }
        }
    }

    public void exportAvionFret(AvionFret a) throws Exception
    {
        ArrayList<AvionFret> avions = new ArrayList<>();
        avions.add(a);
        exportAvionsFret(avions);
    }

    public void exportAvionsFret(ArrayList<AvionFret> avions) throws Exception
    {
        int maxId;
        String query;
        for (AvionFret avion : avions)
        {
            //insertion de l'avion
            if ((maxId = avion.getIdAvion()) != 0)
            {
                query = "Update Avion set nomModele='" + avion.getModele().getNomModele() + "', poidsDispo="+ avion.getPoidsDispo()+
                       ",volumeDispo ="+avion.getVolumeDispo()+", placesEco =0,placesAffaire =0,placesPrem =0 Where idAvion=" + maxId;
            } else
            {
                maxId = readMaxIdAvion() + 1;
                query = "Insert into Avion Values (" + maxId + ",'" + avion.getModele().getNomModele() + "',"+avion.getPoidsDispo()+","
                        +avion.getVolumeDispo()+",0,0,0,'fret',"+avion.getIdDerniereVille().getIdVille()+")";
            }
            try
            {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex)
            {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("erreur lors de l'export d'avionPassager");
            }
        }
    }
    
    public void exportInstanceVol(ArrayList<InstanceVol> instances){
        int maxId;
        String query;

        for (InstanceVol iv : instances)
        {
            if (iv.getNumInstance() == 0)
            {
                maxId = readMaxNumInstance()+ 1;
                String arrivée = (iv.getDateArrive() != "") ? "TO_DATE('" + iv.getDateArrive() + "', 'yyyy/mm/dd hh24:mi:ss')" : "null";
                query = "Insert into InstanceVol values (" + maxId + ", " + iv.getNumVol().getNumVol() + ", " + iv.getIdAvion().getIdAvion() + ", "
                        + iv.getPlacesRestEco() + ", " + iv.getPlacesRestAff() + ", " + iv.getPlacesRestPrem() + ", " + iv.getPoidsRest() + ", "
                        + "TO_DATE('"+iv.getDateDepart()+"', 'yyyy/mm/dd hh24:mi:ss') , "+arrivée+", '"+iv.getEtat()+"')";

            } else
            {
                maxId = iv.getNumInstance();

                String arrivée = (iv.getDateArrive() != "") ? "TO_DATE('" + iv.getDateArrive() + "', 'yyyy/mm/dd hh24:mi:ss')" : "null";

                
                query = "Update InstanceVol set numVol=" + iv.getNumVol().getNumVol() + ", idAvion=" + iv.getIdAvion().getIdAvion() + ", placesRestEco=" + iv.getPlacesRestEco() + ", "
                        + "placesRestAff=" + iv.getPlacesRestAff() + ", placesRestPrem=" + iv.getPlacesRestPrem() + ", poidsRest=" + iv.getPoidsRest() + ", dateDepart=TO_DATE('" + iv.getDateDepart() + "', 'yyyy/mm/dd hh24:mi:ss'), "
                        + "dateArrivee="+arrivée+", etat='"+iv.getEtat()+"'"
                        + " Where numInstance="+maxId;
            }

            try
            {

                DBManager.dbExecuteUpdate(query);


            } catch (SQLException | ClassNotFoundException ex)
            {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void exportInstanceVol(InstanceVol iv){
        ArrayList<InstanceVol> ivs = new ArrayList<>();
        ivs.add(iv);
        exportInstanceVol(ivs);
    }
   

}
