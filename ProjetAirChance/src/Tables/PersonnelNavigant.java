/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

/**
 *
 * @author Andréas
 */
public interface PersonnelNavigant {
    
    public int getIdEmploye();
    public void setIdEmploye(int idEmploye);
    
    public String getNomEmploye();
    public void setNomEmploye(String nomEmploye);
    
    public String getPrenomEmploye();
    public void setPrenomEmploye(String prenomEmploye);
    
    public String getNumRueEmploye();
    public void setNumRueEmploye(String numRueEmploye);
    
    public String getRueEmploye();
    public void setRueEmploye(String rueEmploye);
    
    public String getCpEmploye();
    public void setCpEmploye(String cpEmploye);
    
    public String getVilleEmploye();
    public void setVilleEmploye(String villeEmploye);
    
    public int getHeuresVol();
    public void setHeuresVol(int heuresVol);
    
    public Ville getIdDerniereVille();
    public void setIdDerniereVille(Ville idDerniereVille);
    
    public void importFromId(String id);
    
}
