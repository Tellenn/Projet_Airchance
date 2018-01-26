/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Tables;

import java.sql.ResultSet;

/**
 *
 * @author Andréas
 */
public interface TableInterface {
    
    public void showTable();
    public ResultSet getResultSetFromId(String id);
    public void importFromId(String id);
}
