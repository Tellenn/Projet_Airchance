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
import Tables.Langue;
import Tables.Modele;
import Tables.PNC;
import Tables.PNT;
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
public class ExportDAL
{

    public int readMaxIdPN()
    {
        try
        {
            String query = "Select max(idEmploye) from PersonnelNaviguant";
            ResultSet res;
            res = DBManager.dbExecuteQuery(query);
            res.next();
            return res.getInt(1);
        } catch (SQLException | ClassNotFoundException ex)
        {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public int readMaxIdVille()
    {
        try
        {
            String query = "Select max(idVille) from Ville";
            ResultSet res;
            res = DBManager.dbExecuteQuery(query);
            res.next();
            return res.getInt(1);
        } catch (SQLException | ClassNotFoundException ex)
        {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

    public boolean isNomModeleExists(Modele mod)
    {
        String query = "Select * from Modele where nomModele='" + mod.getNomModele() + "'";
        ResultSet result;
        boolean itsHere = false;

        try
        {
            result = DBManager.dbExecuteQuery(query);
            itsHere = result.last();
        } catch (SQLException | ClassNotFoundException ex)
        {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return itsHere;
    }

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

    public void exportAvionPassager(AvionPassager a)
    {
        ArrayList<AvionPassager> avions = new ArrayList<>();
        avions.add(a);
        exportAvionsPassager(avions);
    }

    private void exportAvionsPassager(ArrayList<AvionPassager> avions)
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
                query = "Insert into Avion Values ('" + maxId + "," + avion.getModele().getNomModele() + "',0 ,0,"
                        + avion.getPlacesEco() + "," + avion.getPlacesAffaire() + "," + avion.getPlacesPrem() + ")";
            }
            try
            {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex)
            {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }

            //suppression des places déjà existantes (dans le cas où on modifie un avion existant)
            query = "Delete from Place where idAvion =" + avion.getIdAvion();

            try
            {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex)
            {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }

            //insertion des places de l'avion
            for (Place p : avion.getPlaces())
            {
                query = "Insert into Place Values("+p.getNumPlace()+","+maxId+","+p.getPosition()+","+p.getClasse()+")";
                try
                {
                    DBManager.dbExecuteUpdate(query);
                } catch (SQLException | ClassNotFoundException ex)
                {
                    Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void exportAvionFret(AvionFret a)
    {
        ArrayList<AvionFret> avions = new ArrayList<>();
        avions.add(a);
        exportAvionsFret(avions);
    }

    private void exportAvionsFret(ArrayList<AvionFret> avions)
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
                query = "Insert into Avion Values ('" + maxId + "," + avion.getModele().getNomModele() + "',"+avion.getPoidsDispo()+","
                        +avion.getVolumeDispo()+",0,0,0"+")";
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
    
    public int readMaxIdAvion()
    {
        try
        {
            String query = "Select max(idAvion) from Avion";
            ResultSet res;
            res = DBManager.dbExecuteQuery(query);
            res.next();
            return res.getInt(1);
        } catch (SQLException | ClassNotFoundException ex)
        {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }

}
