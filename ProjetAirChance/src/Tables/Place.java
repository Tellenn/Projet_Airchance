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
public class Place {

    
    //Place(numPlace, #idAvionP, position, classe)

    
    private int numPlace;
    private int idAvionP;
    private String position;
    private String classe;
    private boolean estReservee;
    
     public Place() 
    {
    }

    public Place(int numPlace, String position, String classe, boolean estReservee) {
        this.numPlace = numPlace;
        this.position = position;
        this.classe = classe;
        this.estReservee = estReservee;
    }
    
    public Place(int numPlace,int idAvionP, String position, String classe, boolean estReservee) {
        this.numPlace = numPlace;
        this.position = position;
        this.idAvionP = idAvionP;
        this.classe = classe;
        this.estReservee = estReservee;
    }
    
    

    public int getNumPlace() {
        return numPlace;
    }

    public void setNumPlace(int numPlace) {
        this.numPlace = numPlace;
    }

    public int getIdAvionP() {
        return idAvionP;
    }

    public void setIdAvionP(int idAvionP) {
        this.idAvionP = idAvionP;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getClasse() {
        return classe;
    }

    public void setClasse(String classe) {
        this.classe = classe;
    }
    
    public ResultSet getResultSetFromId(String numPlace,String avionId,String numInstance) {
        String query = "";
        if (numInstance.equals(""))
        {
            query = "Select * from Place where numPlace=" + numPlace + "and idAvion=" + avionId;
        }else
        {
            query = "Select * from Place natural join ResaVolPlace where numPlace=" + numPlace + "and idAvion=" + avionId + "and numInstance=" + numInstance;
        }
       
        return TableImpl.getResultSet(query);
    }
    
    
    public void importFromId(String numPlace,String avionId,String numInstance) {
        ResultSet result = getResultSetFromId(numPlace,avionId,"");
         ResultSet resultPlaceRes = null;
        if(!numInstance.equals(""))
        {
            resultPlaceRes = getResultSetFromId(numPlace,avionId,numInstance);
        }
        boolean estRes = false;
        try {
            if (result.last()) {
                int rows = result.getRow();
                if (rows > 1) {
                    throw new Exception("La requête a renvoyé plus d'une Place");
                }
            }
            result.beforeFirst();
            if(!numInstance.equals(""))
            {
                if (resultPlaceRes.last()) {
                    int rows = resultPlaceRes.getRow();
                    if (rows > 1) {
                        throw new Exception("La requête a renvoyé plus d'une Place");
                    }
                    else if (rows == 1)
                    {
                        estRes = true;
                    }
                }
                resultPlaceRes.beforeFirst();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            if (!result.next()) {
                throw new Exception("La requête n'a pas abouti avec les id " + numPlace + "," + avionId + "," + numInstance );
            }
        } catch (Exception ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            this.numPlace = result.getInt("numPlace");
            this.idAvionP = result.getInt("idAvion");
            this.position = result.getNString("position");
            this.classe = result.getNString("classe");
            this.estReservee = estRes;
        } catch (SQLException ex) {
            Logger.getLogger(Place.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static ArrayList<Place> importTableWithParameter(int numPlace, int idAvion, String position, String classe, String numInstance){
        String query = "Select * from Place where idAvion="+idAvion;
        if (numPlace != 0){
            query += " and numPlace="+numPlace;
        }
        if (!position.equals("")){
            query += " and position='"+position+"'";
        }
        
        if (!classe.equals("")){
            query += " and classe<="+classe;
        }
        ArrayList<Place> placeTot = importTable(query);
        
        if (!numInstance.equals(""))
        {
            query = "Select * from ResaVolPlace natural join Place where idAvion="+idAvion;
            if (numPlace != 0){
                query += " and numPlace="+numPlace;
            }
            if (!position.equals("")){
                query += " and position='"+position+"'";
            }
            if (!classe.equals("")){
                query += " and classe='"+classe+"'";
            }
            if (!numInstance.equals("")){
                query += " and numInstance='"+numInstance+"'";
            }
            ArrayList<String> placeRes = getPlaceRes(query);

            for (int i=0;i<placeTot.size();i++)
            {
                if(placeRes.indexOf(""+placeTot.get(i).numPlace)!=-1)
                {
                    placeTot.get(i).estReservee = true;
                }
            }
        }
        
        return placeTot;        
    }
    
    private static ArrayList<String> getPlaceRes(String queryTable) {
        String query;
        if (queryTable != null && !queryTable.equals("") ){
            query = queryTable;
        }else{
            query = "Select numPlace from ResaVolPlace";
        }
         ResultSet result;
        ArrayList<String> placesAvion = new ArrayList<String>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            while(result.next()){
                int numPlace = result.getInt("numPlace");
                placesAvion.add(""+numPlace);
            }
        } catch (SQLException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return placesAvion;
    }
    
    private static ArrayList<Place> importTable(String queryTable) {
        String query;
        boolean estRes = false;
        if (queryTable != null && !queryTable.equals("") ){
            query = queryTable;
        }else{
            query = "Select * from Place";
        }
        
        ResultSet result;
        ArrayList<Place> placesAvion = new ArrayList<>();
        try {
            result = DBManager.dbExecuteQuery(query);
            
            while(result.next()){
                int numPlace = result.getInt("numPlace");
                int idAvion = result.getInt("idAvion");
                String classe = result.getString("classe");
                String position = result.getString("position");
                
                
                
                Place tmp = new Place(numPlace, idAvion, classe, position,estRes);
                placesAvion.add(tmp);
            }
        
            
        } catch (SQLException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(AvionPassager.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return placesAvion;
    }
    
    
    public boolean equals(Place p)
    {
        boolean res = false;
        if(p.idAvionP == this.idAvionP && p.numPlace == this.numPlace)
        {
            res = true;
        }
            
        return res;
    }
   
}
