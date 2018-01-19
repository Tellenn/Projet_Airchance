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
public class Place {
    //Place(numPlace, #idAvionP, position, classe)

    
    private String numPlace;
    private String idAvionP;
    private String position;
    private String classe;

    public String getNumPlace() {
        return numPlace;
    }

    public void setNumPlace(String numPlace) {
        this.numPlace = numPlace;
    }

    public String getIdAvionP() {
        return idAvionP;
    }

    public void setIdAvionP(String idAvionP) {
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
   
}
