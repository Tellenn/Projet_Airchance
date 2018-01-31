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
import Tables.Client;
import Tables.InstanceVol;
import Tables.Langue;
import Tables.Modele;
import Tables.PNC;
import Tables.PNT;
import Tables.PersonnelNavigant;
import Tables.Place;
import Tables.ReservationFret;
import Tables.ReservationPassager;
import Tables.Reservation_Correspondances;
import Tables.Reservations;
import Tables.Ville;
import Tables.Vol;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import DAL.ConstraintDAL;

/**
 *
 * @author Andréas
 */
public class ExportDAL {

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

    public int readMaxNumReservationP() {
        try {
            String query = "Select max(NumreservationP) from ReservationPassager";
            ResultSet res;
            res = DBManager.dbExecuteQuery(query);
            res.next();
            return res.getInt(1);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private int readMaxNumReservationF() {
        try {
            String query = "Select max(NumreservationF) from ReservationFret";
            ResultSet res;
            res = DBManager.dbExecuteQuery(query);
            res.next();
            return res.getInt(1);
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }

    private int readMaxIdClient() {
        try {
            String query = "Select max(idClient) from Client";
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
    public void exportPNT(ArrayList<PNT> pntArray) {
        int maxId;
        String query, queryPiloteModele;

        for (PNT pnt : pntArray) {
            if (pnt.getIdEmploye() == 0) {
                maxId = readMaxIdPN() + 1;
                //IF not contrainte
                query = "Insert into PersonnelNaviguant values (" + maxId + ", '" + pnt.getNomEmploye() + "', '" + pnt.getPrenomEmploye() + "', '"
                        + pnt.getNumRueEmploye() + "', '" + pnt.getRueEmploye() + "', '" + pnt.getCpEmploye() + "', '" + pnt.getVilleEmploye() + "', " + pnt.getHeuresVol() + ", 'PNT', " + pnt.getIdDerniereVille().getIdVille() + ")";
            } else {
                maxId = pnt.getIdEmploye();
                query = "Update PersonnelNaviguant set nomEmploye='" + pnt.getNomEmploye() + "', prenomEmploye='" + pnt.getPrenomEmploye() + "', numRueEmploye='" + pnt.getNumRueEmploye() + "', "
                        + "rueEmploye='" + pnt.getRueEmploye() + "', cpEmploye='" + pnt.getCpEmploye() + "', villeEmploye='" + pnt.getVilleEmploye() + "', heuresVol=" + pnt.getHeuresVol() + ", idDerniereVille=" + pnt.getIdDerniereVille().getIdVille() + ""
                        + " Where idEmploye=" + maxId;
            }

            try {
                DBManager.dbExecuteUpdate(query);
                DBManager.dbExecuteUpdate("Delete from PiloteModele where idEmploye=" + maxId);
                for (Modele mod : pnt.getPiloteModele().keySet()) {
                    queryPiloteModele = "Insert into PiloteModele values ('" + mod.getNomModele() + "', " + maxId + ", " + pnt.getPiloteModele().get(mod) + ")";
                    try {
                        DBManager.dbExecuteUpdate(queryPiloteModele);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    public void exportPNT(PNT pnt) {
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
    public void exportPNC(ArrayList<PNC> pncArray) {
        int maxId;
        String query, queryLangue;

        for (PNC pnc : pncArray) {
            if (pnc.getIdEmploye() == 0) {
                maxId = readMaxIdPN() + 1;
                query = "Insert into PersonnelNaviguant values (" + maxId + ", '" + pnc.getNomEmploye() + "', '" + pnc.getPrenomEmploye() + "', '"
                        + pnc.getNumRueEmploye() + "', '" + pnc.getRueEmploye() + "', '" + pnc.getCpEmploye() + "', '" + pnc.getVilleEmploye() + "', " + pnc.getHeuresVol() + ", 'PNC', " + pnc.getIdDerniereVille().getIdVille() + ")";

            } else {
                maxId = pnc.getIdEmploye();
                query = "Update PersonnelNaviguant set nomEmploye='" + pnc.getNomEmploye() + "', prenomEmploye='" + pnc.getPrenomEmploye() + "', numRueEmploye='" + pnc.getNumRueEmploye() + "', "
                        + "rueEmploye='" + pnc.getRueEmploye() + "', cpEmploye='" + pnc.getCpEmploye() + "', villeEmploye='" + pnc.getVilleEmploye() + "', heuresVol=" + pnc.getHeuresVol() + ", idDerniereVille=" + pnc.getIdDerniereVille().getIdVille() + ""
                        + " Where idEmploye=" + maxId;
            }

            try {

                DBManager.dbExecuteUpdate(query);

                if (pnc.getLangues().size() < 3){
                    throw new Exception("Contrainte métier : un PNC doit connaitre au moins 3 Langues");
                }
                String queryDeleteLangue = "Delete from LanguePNC where idEmploye=" + maxId;
                DBManager.dbExecuteUpdate(queryDeleteLangue);

                
                
                for (Langue jack : pnc.getLangues()) {
                    queryLangue = "Insert into LanguePNC values ('" + jack.getNomLangue() + "', " + maxId + ")";
                    try {
                        DBManager.dbExecuteUpdate(queryLangue);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (Exception ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void exportPNC(PNC pnc) {
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
    public void exportModele(ArrayList<Modele> modeles) {
        String query;

        for (Modele mod : modeles) {
            if (!isNomModeleExists(mod)) { // new modele, insertion
                query = "Insert into Modele values ('" + mod.getNomModele() + "', " + mod.getNbPilotes() + ", " + mod.getRayonAction() + ")";
            } else { // update un modele
                query = "Update Modele set nbPilotes=" + mod.getNbPilotes() + ", rayonAction=" + mod.getRayonAction() + " where nomModele='" + mod.getNomModele() + "'";
            }

            try {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void exportModele(Modele mod) {
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
    public void exportVille(ArrayList<Ville> villes) {
        int maxId;
        String query;
        
        for (Ville ville : villes) {
            if ((maxId = ville.getIdVille()) != 0) {
                query = "Update Ville set nomVille='" + ville.getNomVille() + "', paysVille='" + ville.getPaysVille() + "' Where idVille=" + maxId;
            } else {
                maxId = readMaxIdVille() + 1;
                query = "Insert into Ville values (" + maxId + ", '" + ville.getNomVille() + "', '" + ville.getPaysVille() + "')";
            }

            try {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void exportVille(Ville v) {
        ArrayList<Ville> villes = new ArrayList<>();
        villes.add(v);
        exportVille(villes);
    }

    public void exportVol(ArrayList<Vol> vols) {
        int maxId;
        String query;

        for (Vol vol : vols) {
            if ((maxId = vol.getNumVol()) != 0) {
                query = "Update Vol set type=" + (vol.getType() - 1) + ", duree=" + vol.getDuree() + ", distance=" + vol.getDistance() + ", placesMinEco=" + vol.getPlacesMinEco() + ""
                        + ", placesMinAff=" + vol.getPlacesMinAff() + ", placesMinPrem=" + vol.getPlacesMinPrem() + ", poidsMin=" + vol.getPoidsMin() + ", idVilleOrigine=" + vol.getIdVilleOrigine().getIdVille() + ""
                        + ", idVilleDestination=" + vol.getIdVilleDestination().getIdVille()
                        + " Where numVol=" + maxId;
            } else {
                maxId = readMaxNumVol() + 1;
                query = "Insert into Vol values (" + maxId + ", " + (vol.getType() - 1) + ", " + vol.getDuree() + ", " + vol.getDistance() + ", " + vol.getPlacesMinEco() + ""
                        + ", " + vol.getPlacesMinAff() + ", " + vol.getPlacesMinPrem() + ", " + vol.getPoidsMin() + ", " + vol.getIdVilleOrigine().getIdVille() + ", " + vol.getIdVilleDestination().getIdVille() + ")";
            }

            try {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void exportVol(Vol v) {
        ArrayList<Vol> vols = new ArrayList<>();
        vols.add(v);
        exportVol(vols);
    }

    public void exportAvionPassager(AvionPassager a) throws Exception {
        ArrayList<AvionPassager> avions = new ArrayList<>();
        avions.add(a);
        exportAvionsPassager(avions);
    }

    public void exportAvionsPassager(ArrayList<AvionPassager> avions) throws Exception {
        int maxId;
        String query;
        for (AvionPassager avion : avions) {
            //insertion de l'avion
            if ((maxId = avion.getIdAvion()) != 0) {
                query = "Update Avion set nomModele='" + avion.getModele().getNomModele() + "', poidsDispo=0 , volumeDispo = 0, placesEco =" + avion.getPlacesEco()
                        + ",placesAffaire =" + avion.getPlacesAffaire() + ",placesPrem =" + avion.getPlacesPrem() + " Where idAvion=" + maxId;
            } else {
                maxId = readMaxIdAvion() + 1;
                query = "Insert into Avion Values (" + maxId + ",'" + avion.getModele().getNomModele() + "',0 ,0,"
                        + avion.getPlacesEco() + "," + avion.getPlacesAffaire() + "," + avion.getPlacesPrem() + ",'passagers'," + avion.getIdDerniereVille().getIdVille() + ")";
            }
            try {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("erreur lors de l'export d'avionPassager");
            }

            //suppression des places déjà existantes (dans le cas où on modifie un avion existant)
            query = "Delete from Place where idAvion =" + avion.getIdAvion();

            try {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("erreur lors de l'export d'avionPassager");
            }

            //insertion des places de l'avion
            for (Place p : avion.getPlaces()) {
                query = "Insert into Place Values(" + p.getNumPlace() + "," + maxId + ",'" + p.getPosition() + "','" + p.getClasse() + "')";
                try {
                    DBManager.dbExecuteUpdate(query);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                    throw new Exception("erreur lors de l'export d'avionPassager");
                }
            }
        }
    }

    public void exportAvionFret(AvionFret a) throws Exception {
        ArrayList<AvionFret> avions = new ArrayList<>();
        avions.add(a);
        exportAvionsFret(avions);
    }

    public void exportAvionsFret(ArrayList<AvionFret> avions) throws Exception {
        int maxId;
        String query;
        for (AvionFret avion : avions) {
            //insertion de l'avion
            if ((maxId = avion.getIdAvion()) != 0) {
                query = "Update Avion set nomModele='" + avion.getModele().getNomModele() + "', poidsDispo=" + avion.getPoidsDispo()
                        + ",volumeDispo =" + avion.getVolumeDispo() + ", placesEco =0,placesAffaire =0,placesPrem =0 Where idAvion=" + maxId;
            } else {
                maxId = readMaxIdAvion() + 1;
                query = "Insert into Avion Values (" + maxId + ",'" + avion.getModele().getNomModele() + "'," + avion.getPoidsDispo() + ","
                        + avion.getVolumeDispo() + ",0,0,0,'fret'," + avion.getIdDerniereVille().getIdVille() + ")";
            }
            try {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                throw new Exception("erreur lors de l'export d'avionPassager");
            }
        }
    }

    public void exportInstanceVol(ArrayList<InstanceVol> instances) {
        int maxId;
        String query;

        for (InstanceVol iv : instances) {
            if (iv.getNumInstance() == 0) {
                maxId = readMaxNumInstance() + 1;
                String arrivée = (iv.getDateArrive() != "") ? "TO_DATE('" + iv.getDateArrive() + "', 'yyyy/mm/dd hh24:mi:ss')" : "null";
                query = "Insert into InstanceVol values (" + maxId + ", " + iv.getNumVol().getNumVol() + ", " + iv.getIdAvion().getIdAvion() + ", "
                        + iv.getPlacesRestEco() + ", " + iv.getPlacesRestAff() + ", " + iv.getPlacesRestPrem() + ", " + iv.getPoidsRest() + ", "
                        + "TO_DATE('" + iv.getDateDepart() + "', 'yyyy/mm/dd hh24:mi:ss') , " + arrivée + ", '" + iv.getEtat() + "')";

            } else {
                maxId = iv.getNumInstance();

                String arrivée = (iv.getDateArrive() != "") ? "TO_DATE('" + iv.getDateArrive() + "', 'yyyy/mm/dd hh24:mi:ss')" : "null";

                query = "Update InstanceVol set numVol=" + iv.getNumVol().getNumVol() + ", idAvion=" + iv.getIdAvion().getIdAvion() + ", placesRestEco=" + iv.getPlacesRestEco() + ", "
                        + "placesRestAff=" + iv.getPlacesRestAff() + ", placesRestPrem=" + iv.getPlacesRestPrem() + ", poidsRest=" + iv.getPoidsRest() + ", dateDepart=TO_DATE('" + iv.getDateDepart() + "', 'yyyy/mm/dd hh24:mi:ss'), "
                        + "dateArrivee=" + arrivée + ", etat='" + iv.getEtat() + "'"
                        + " Where numInstance=" + maxId;
            }

            try {

                DBManager.dbExecuteUpdate(query);
                String queryEiv;
                String queryDeleteEmployeInstanceVol = "Delete from EmployeInstanceVol where numInstance=" + maxId;
                DBManager.dbExecuteUpdate(queryDeleteEmployeInstanceVol);

                for (PersonnelNavigant pn : iv.getPersonnel()) {
                    queryEiv = "Insert into EmployeInstanceVol values (" + maxId + ", " + pn.getIdEmploye() + ")";
                    try {
                        DBManager.dbExecuteUpdate(queryEiv);
                    } catch (SQLException | ClassNotFoundException ex) {
                        Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void deleteInstanceVol(InstanceVol iv) throws Exception {
        ImportDAL impDAL = new ImportDAL();
        if (iv.getEtat().equals("Arrive") || iv.getEtat().equals("En cours de vol") || iv.getEtat().equals("Annule")) {
            throw new Exception("Le vol est déjà terminé");
        } else {
            String query;
            ResultSet res;
            query = "Select * from ResaVolPlace where numInstance=" + iv.getNumInstance();

            try {
                res = DBManager.dbExecuteQuery(query);
                while (res.next()) {
                    String queryIdClient = "Select * from ReservationPassager where numReservationP=" + res.getInt("numReservationP");
                    ResultSet resClient = DBManager.dbExecuteQuery(queryIdClient);
                    resClient.next();
                    Reservation_Correspondances rc = new Reservation_Correspondances();
                    rc.importFromIdClient("" + resClient.getInt("idClient"));
                    
                    
                }
            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }

            // suppression de EmployeInstanceVol
            query = "DELETE FROM EmployeInstanceVol WHERE numInstance =" + iv.getNumInstance();
            try {
                DBManager.dbExecuteUpdate(query);
            } catch (SQLException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }

            // on récupère le prochain vol sur la même ligne
            DateFormat format = new SimpleDateFormat("yyyy/MM/dd' 'hh:mm:ss", Locale.ENGLISH);
            ArrayList<InstanceVol> autresVols = impDAL.importTableInstanceVol(0, iv.getNumVol().getNumVol(), 0, 0, 0, 0, 0, "", "", "");
            int newNumInstance = 0;
            for (InstanceVol autreVol : autresVols) {
                if (autreVol.getNumInstance() != iv.getNumInstance()) {
                    if (format.parse(autreVol.getDateDepart()).after(format.parse(iv.getDateDepart()))) {
                        newNumInstance = autreVol.getNumInstance();
                    }
                }
            }

            // on regarde si c'est du fret(2) ou du passager (1)
            if (iv.getNumVol().getType() == 2) {
                if (newNumInstance == 0) {
                    query = "Delete FROM ReservationFret where numInstance = " + iv.getNumInstance();
                } else {
                    query = "UPDATE ReservationFret SET numInstance=" + newNumInstance + " WHERE numInstance=" + iv.getNumInstance();
                }

                try {
                    DBManager.dbExecuteUpdate(query);
                } catch (SQLException ex) {
                    Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            query = "UPDATE InstanceVol SET etat='" + "Annule" + "' WHERE numInstance=" + iv.getNumInstance();
        }

    }

    public void exportInstanceVol(InstanceVol iv) {
        ArrayList<InstanceVol> ivs = new ArrayList<>();
        ivs.add(iv);
        exportInstanceVol(ivs);
    }

    public void exportReservationCorrespondance(Reservation_Correspondances rc) {
        int maxId;
        String query;

        for (Reservations r : rc.getReservations()) {
            if (r instanceof ReservationPassager) {
                if (r.getNumReservation() == 0) {
                    maxId = readMaxNumReservationP() + 1;
                } else {
                    maxId = r.getNumReservation();
                }
                String queryDelete;
                try {
                    queryDelete = "Delete from ResaVolPlace where numReservationP=" + r.getNumReservation() + " and numInstance=" + ((ReservationPassager) r).getNumInstance().getNumInstance() + " and numPlace=" + ((ReservationPassager) r).getNumPlace().getNumPlace() + " and idAvion=" + ((ReservationPassager) r).getIdAvion().getIdAvion();
                    DBManager.dbExecuteUpdate(queryDelete);

                    exportReservationPassager(rc, (ReservationPassager) r, maxId);

                    
                    ConstraintDAL c = new ConstraintDAL();
                    float prix = r.getPrix();
                    float coeff = 0.95f;
                    if(c.reduction(rc.getIdClient().getIdClient())==true){
                        prix = prix * coeff;
                    }
                    
                    query = "Insert into ResaVolPlace values ("+r.getNumReservation()+", "+((ReservationPassager) r).getNumInstance().getNumInstance()+", "+((ReservationPassager) r).getNumPlace().getNumPlace()+", "+((ReservationPassager) r).getIdAvion().getIdAvion()+", "+prix+")";



                    DBManager.dbExecuteUpdate(query);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (r instanceof ReservationFret) {
                if (r.getNumReservation() == 0) {
                    maxId = readMaxNumReservationF() + 1;
                } else {
                    maxId = r.getNumReservation();
                }
                String queryDelete = "Delete from ReservationFret where numReservationF=" + maxId;
                try {
                    DBManager.dbExecuteUpdate(queryDelete);
                    String arrivée = (r.getDateReservation() != "") ? "TO_DATE('" + r.getDateReservation() + "', 'yyyy/mm/dd hh24:mi:ss')" : "null";
                    query = "Insert into ReservationFret values (" + maxId + ", " + rc.getIdClient().getIdClient() + ", " + ((ReservationFret) r).getNumInstance().getNumInstance() + ", " + ((ReservationFret) r).getVolume() + ", " + ((ReservationFret) r).getPoids() + ", " + arrivée + ")";

                    DBManager.dbExecuteUpdate(query);
                } catch (SQLException | ClassNotFoundException ex) {
                    Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

    }

    private void exportReservationPassager(Reservation_Correspondances rc, ReservationPassager r, int maxId) {
        String query;
        ResultSet result;

        query = "Select * from ReservationPassager where numReservationP=" + maxId;
        try {
            result = DBManager.dbExecuteQuery(query);
            if (!result.next()) {

                query = "Insert into ReservationPassager values (" + maxId + ", " + rc.getIdClient().getIdClient() + ", TO_DATE('" + r.getDateReservation() + "', 'yyyy/mm/dd hh24:mi:ss'))";
                DBManager.dbExecuteUpdate(query);
            } else {
                query = "Update ReservationPassager set idClient=" + rc.getIdClient().getIdClient() + ", dateReservation=TO_DATE('" + r.getDateReservation() + "', 'yyyy/mm/dd hh24:mi:ss') where numreservationP=" + maxId;
                DBManager.dbExecuteUpdate(query);
            }
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void exportTableClient(ArrayList<Client> clients) {
        int maxId;
        String query;

        for (Client c : clients) {
            if (c.getIdClient() == 0) {
                maxId = readMaxIdClient() + 1;
                query = "Insert into Client values (" + maxId + ", '" + c.getNomClient() + "', '" + c.getPrenomClient() + "', '"
                        + c.getNumRueClient() + "', '" + c.getRueClient() + "', " + c.getCpClient() + ", '" + c.getVilleClient() + "', "
                        + c.getHeuresCumulees() + ", '" + c.getNumPasseport() + "')";

            } else {
                maxId = c.getIdClient();

                query = "Update Client set nomClient='" + c.getNomClient() + "', prenomClient='" + c.getPrenomClient() + "', numRueClient='" + c.getNumRueClient() + "', "
                        + "rueClient='" + c.getRueClient() + "', cpClient=" + c.getCpClient() + ", villeClient='" + c.getVilleClient() + "', heuresCumulees=" + c.getHeuresCumulees() + ", numPasseport='" + c.getNumPasseport() + "'"
                        + "where idclient=" + maxId;

            }

            try {

                DBManager.dbExecuteUpdate(query);
                c.fillReservations();
                exportReservationCorrespondance(c.getReservations());

            } catch (SQLException | ClassNotFoundException ex) {
                Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }

    public void exportTableClient(Client c) {
        ArrayList<Client> tmp = new ArrayList<>();
        tmp.add(c);
        exportTableClient(tmp);
    }

    /*
    public void deleteInstanceVol(int numInstance){
        ImportDAL importDal = new ImportDAL();
        String query = "Select * from ResaVolPlace where numInstance="+numInstance;
        InstanceVol instanceToDelete = importDal.importTableInstanceVol(numInstance, 0, 0, 0, 0, 0, 0, "", "", "").get(0);
        ArrayList<InstanceVol> instanceCouldReplace = importDal.importTableInstanceVol(0, instanceToDelete.getNumVol().getNumVol(), 0, 1, 1, 1, 0, instanceToDelete.getDateDepart(), "", "Cree");
        ResultSet res;
        
        try{
            res = DBManager.dbExecuteQuery(query);
            while(res.next()){
                for()
            }
            
        } catch (SQLException | ClassNotFoundException ex) {
            Logger.getLogger(ExportDAL.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     */
}
