/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Archives;

import Tables.AvionFret;
import Tables.InstanceVol;
import Tables.PNC;
import Tables.PNT;
import Tables.PersonnelNavigant;
import Tables.TableImpl;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Andréas
 */
public class EmployeInstanceVol {

    
    private InstanceVol numInstance;
    private PersonnelNavigant idEmploye;
    
    public EmployeInstanceVol(){
        this.numInstance = new InstanceVol();
        this.idEmploye = null;
    }
    
    public void showTable(){
        String query = "Select * from EmployeInstanceVol";
        TableImpl.showTable(query);
    }
    
     public ResultSet getResultSetFromId(InstanceVol numInstance, PersonnelNavigant idEmploye){
         String query = "Select * from EmployeInstanceVol where numInstance="+numInstance.getNumInstance()+""
                 + " and idEmploye="+idEmploye.getIdEmploye();
         return TableImpl.getResultSet(query);
     }
     
     public void setFromId(InstanceVol numInstance, PersonnelNavigant idEmploye){
         ResultSet result = getResultSetFromId(numInstance, idEmploye);
        try {
            if(result.last()){
                int rows = result.getRow();
                if (rows > 1) throw new Exception("La requête a renvoyé plus d'un EmployeInstanceVol");
            }
            result.beforeFirst();
        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        try {
            if(!result.next()) throw new Exception("La requête n'a pas abouti avec le numInstance "+numInstance+" et l'idEmploye "+idEmploye);
        } catch (Exception ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
            
        try {
            // this.numInstance.importFromId(result.getString("numInstance"));
            switch (this.idEmploye.getClass().getSimpleName()){
                case "PNT":
                    ((PNT)this.idEmploye).importFromId(""+result.getInt("idEmploye"));
                    break;
                case "PNC":
                    ((PNC)this.idEmploye).importFromId(""+result.getInt("idEmploye"));
                    break;
            }

        } catch (SQLException ex) {
            Logger.getLogger(AvionFret.class.getName()).log(Level.SEVERE, null, ex);
        }
     }
             
             
    /**
     * @return the numInstance
     */
    public InstanceVol getNumInstance() {
        return numInstance;
    }

    /**
     * @param numInstance the numInstance to set
     */
    public void setNumInstance(InstanceVol numInstance) {
        this.numInstance = numInstance;
    }

    /**
     * @return the idEmploye
     */
    public PersonnelNavigant getIdEmploye() {
        return idEmploye;
    }

    /**
     * @param idEmploye the idEmploye to set
     */
    public void setIdEmploye(PersonnelNavigant idEmploye) {
        this.idEmploye = idEmploye;
    }
    

    
    
    
}
