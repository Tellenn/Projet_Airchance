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
    
    private String idClient;
    private String nomClient;
    private String prenomClient;
    private int numRueClient;
    private String rueClient;
    private int cpClient;
    private String villeClient;
    private int heuresCumulees;
    private String numPasseport;
    private boolean aReduction;
    private ArrayList<Reservation> reservations;

    public ArrayList<Reservation> getReservations()
    {
        return reservations;
    }

    public void setReservations(ArrayList<Reservation> reservations)
    {
        this.reservations = reservations;
    }
    
    public void addReservation(Reservation r)
    {
        this.reservations.add(r);
    }
    
    public void removeReservation(Reservation r)
    {
        this.reservations.remove(r);
    }
    

    public String getIdClient() {
        return idClient;
    }

    public void setIdClient(String idClient) {
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

    public int getNumRueClient() {
        return numRueClient;
    }

    public void setNumRueClient(int numRueClient) {
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
    
    public Client ()
    {
        idClient = "";
        nomClient = "";
        prenomClient = "";
        numRueClient = 0;
        rueClient = "";
        cpClient = 0;
        villeClient = "";
        heuresCumulees = 0;
        numPasseport = "";
    }

    public boolean isaReduction()
    {
        return aReduction;
    }

    public void setaReduction(boolean aReduction)
    {
        this.aReduction = aReduction;
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
                this.idClient = rs.getString("idClient");
                this.nomClient = rs.getString("nomClient");
                this.prenomClient = rs.getString("prenomClient");
                this.numRueClient = rs.getInt("numRueClient");
                this.rueClient = rs.getString("rueClient");
                this.cpClient = rs.getInt("cpClient");
                this.villeClient = rs.getString("villeClient");
                this.heuresCumulees = rs.getInt("heuresCumulees");
                this.numPasseport = rs.getString("numPasseport");

            }
        } catch (ClassNotFoundException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex)
        {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
