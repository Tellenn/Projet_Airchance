/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

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
    
    
    
}
