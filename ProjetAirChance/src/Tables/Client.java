/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import BD.DBManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Pault
 */
public class Client {
    
    // idClient, nomClient, prenomClient, numRueClient, rueClient, cpClient, villeClient, heuresCumulees, numPasseport
    
    private int idClient;
    private String nomClient;
    private String prenomClient;
    private String numRueClient;
    private String rueClient;
    private int cpClient;
    private String villeClient;
    private int heuresCumulees;
    private String numPasseport;
    private boolean aReduction;
    private Reservation_Correspondances reservations;

    public Client(int idClientRes, String nomClientRes, String prenomRes, String numRueRes, String rueClientRes, int cpClientRes, String villeRes, int heuresRes, String numPassres) {
        this.idClient = idClientRes;
        this.nomClient = nomClientRes;
        this.prenomClient = prenomRes;
        this.numRueClient = numRueRes;
        this.rueClient = rueClientRes;
        this.cpClient = cpClientRes;
        this.villeClient = villeRes;
        this.heuresCumulees = heuresRes;
        this.numPasseport = numPassres;
        this.aReduction = false;
        //this.reservations = new Reservation_Correspondances();
    }
    
     public Client ()
    {
        idClient = 0;
        nomClient = "";
        prenomClient = "";
        numRueClient = "";
        rueClient = "";
        cpClient = 0;
        villeClient = "";
        heuresCumulees = 0;
        numPasseport = "";
        aReduction = false;
        //reservations = new Reservation_Correspondances();
    }

// <editor-fold defaultstate="collapsed" desc=" GETTERS/SETTERS ">
    public Reservation_Correspondances getReservations() {
        return reservations;
    }
    
    public void setReservations(Reservation_Correspondances reservations) {
        this.reservations = reservations;
    }
    

    
    public int getIdClient() {
        return idClient;
    }
    
    public void setIdClient(int idClient) {
        this.idClient = idClient;
    }
    
    public String getNomClient() {
        return nomClient;
    }
    
    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }
    
    public String getPrenomClient() {
        return prenomClient;
    }
    
    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }
    
    public String getNumRueClient() {
        return numRueClient;
    }
    
    public void setNumRueClient(String numRueClient) {
        this.numRueClient = numRueClient;
    }
    
    public String getRueClient() {
        return rueClient;
    }
    
    public void setRueClient(String rueClient) {
        this.rueClient = rueClient;
    }
    
    public int getCpClient() {
        return cpClient;
    }
    
    public void setCpClient(int cpClient) {
        this.cpClient = cpClient;
    }
    
    public String getVilleClient() {
        return villeClient;
    }
    
    public void setVilleClient(String villeClient) {
        this.villeClient = villeClient;
    }
    
    public int getHeuresCumulees() {
        return heuresCumulees;
    }
    
    public void setHeuresCumulees(int heuresCumulees) {
        this.heuresCumulees = heuresCumulees;
    }
    
    public String getNumPasseport() {
        return numPasseport;
    }
    
    public void setNumPasseport(String numPasseport) {
        this.numPasseport = numPasseport;
    }
    
       public boolean isaReduction()
    {
        return aReduction;
    }

    public void setaReduction(boolean aReduction)
    {
        this.aReduction = aReduction;
    }

// </editor-fold>
    
   
    
    public void fillReservations(){
        Reservation_Correspondances rc = new Reservation_Correspondances();
        rc.importFromIdClient(""+idClient);
        this.reservations = rc;
    }

 
    
    public void setFromNom(String nom) throws Exception
    {
        String query = "SELECT * from Client Where nomClient ='"+nom+"'";
        try
        {
            ResultSet rs = DBManager.dbExecuteQuery(query);
            rs.last();
            if(rs.getRow()!=1)
            {
                throw new Exception("La requête a renvoyée plus d'un résultat !!");
            }
            else
            {
                this.idClient = rs.getInt("idClient");
                this.nomClient = rs.getString("nomClient");
                this.prenomClient = rs.getString("prenomClient");
                this.numRueClient = rs.getString("numRueClient");
                this.rueClient = rs.getString("rueClient");
                this.cpClient = rs.getInt("cpClient");
                this.villeClient = rs.getString("villeClient");
                this.heuresCumulees = rs.getInt("heuresCumulees");
                this.numPasseport = rs.getString("numPasseport");

            }
        } catch (ClassNotFoundException | SQLException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void importFromId(String idClient) {
        ResultSet rs = getResultSetFromId(idClient);
        try {
            if(rs.last()){
                int rows = rs.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'un Client");
            }
            rs.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!rs.next()) throw new Exception("La requête n'a pas abouti avec l'idClient "+idClient);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        try {
            this.idClient = rs.getInt("idClient");
            this.nomClient = rs.getString("nomClient");
            this.prenomClient = rs.getString("prenomClient");
            this.numRueClient = rs.getString("numRueClient");
            this.rueClient = rs.getString("rueClient");
            this.cpClient = rs.getInt("cpClient");
            this.villeClient = rs.getString("villeClient");
            this.heuresCumulees = rs.getInt("heuresCumulees");
            this.numPasseport = rs.getString("numPasseport");

        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private ResultSet getResultSetFromId(String idClient) {
        String query = "Select * from Client where idClient="+idClient;
        return TableImpl.getResultSet(query);
    }
}
