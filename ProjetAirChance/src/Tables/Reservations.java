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
public interface Reservations
{
    public int getNumReservation();
    public void setNumReservation(int numReservation);
    
    public String getDateReservation();
    public void setDateReservation(String date);
    
    public void importFromId(String id);
}
