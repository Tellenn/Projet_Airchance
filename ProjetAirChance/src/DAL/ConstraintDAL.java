/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAL;

import Tables.Client;

/**
 *
 * @author perrink
 */
public class ConstraintDAL {
    
    public boolean reduction(int idClient){
        Client c = new Client();
        c.importFromId(""+idClient);
        if(c.getHeuresCumulees() > 500){
            c.setHeuresCumulees(c.getHeuresCumulees()-500);
            return true;
        } else {
            return false;
        }
    }
    
}
