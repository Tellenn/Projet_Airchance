/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import BD.DBManager;
import DAL.ExportDAL;
import DAL.ImportDAL;
import Tables.Avion;
import Tables.AvionFret;
import Tables.AvionPassager;
import Tables.InstanceVol;
import Tables.Modele;
import Tables.PNC;
import Tables.PNT;
import Tables.Ville;
import Tables.Vol;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Pault
 */
public class ProjetAirChance extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        Button btnClient = new Button();
        btnClient.setText("Partie Client");
        btnClient.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Partie client");
            }
        });
        
        btnClient.setTranslateX(50);
        
        Button btnManager = new Button();
        btnManager.setText("Partie manager");
        btnManager.setOnAction(new EventHandler<ActionEvent>() {
            
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Partie manager");
            }
        });
        btnManager.setTranslateX(-50);
        
        StackPane root = new StackPane();
        root.getChildren().add(btnClient);
        root.getChildren().add(btnManager);
        
        Scene scene = new Scene(root, 300, 250);
        
        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //launch(args);
        
        /*insert into avion values (1, 'A300', 50000, 1500, null, null, null, 'fret');
        insert into avion values (2, 'A320', 30000, 1000, null, null, null, 'fret');
        insert into Modele Values ('A300', 2, 7500);
        insert into Modele values('A320', 2, 6000);*/
        
        //Application.launch(ProjetAirChance.class, args);
        /*Avion a = new AvionFret();
        ((AvionFret)a).showTable();
        
        Avion b = new AvionPassager();
        ((AvionPassager)b).showTable();*/
       
        
        ImportDAL dal = new ImportDAL();
        ExportDAL dalExp = new ExportDAL();
        DBManager.dbConnect();
        
        /*ArrayList<AvionFret> avionsF = dal.importTableAvionFret();
        
        Modele mod = new Modele();
        mod.importFromId("A330");
        
        ArrayList<AvionFret> avionsF2 = dal.importTableAvionFret(0, mod, 0, 0, null);*/
        try {

            //dal.importTablePNT(0, "", "", "", "", "", "", 0, 1);
            //ArrayList<PNT> test = dal.importTablePNT(0, "", "", "", "", "", "", 0, null);
            
            /*
            PNT test = new PNT(0, "Garcia", "Jose", "38", "rue de la Chance", "38000", "Grenoble", 0, 1);
            PNT test2 = new PNT(4, "Tardif", "Hugues", "38", "petite fusterie", "62200", "BOULOGNE-SUR-MER", 0, 1);
            ArrayList<PNT> exported = new ArrayList<>();
            //exported.add(test);
            exported.add(test2);
            dalExp.exportPNT(exported);*/
            
            
            /*
            ArrayList<String> lang = new ArrayList<>();
            lang.add("Anglais");
            lang.add("Francais");
            lang.add("Allemand");
            PNC test = new PNC(23, "Dubooooosc", "Frank", "39", "rue de la Chimie", "38100", "St martin truc", 0, 1, lang);
            dalExp.exportPNC(test);
            */

            /*
            dal.importTableVol();
            Vol v = new Vol(0, 1, 1000, 1000, 500, 100, 5, 0, 15, 1);
            dalExp.exportVol(v);
            */

           //dal.importTableInstanceVol(0, 0, 0, 0, 0, 0, 0, "2018/02/15 10:30:00", "", "");
           
           InstanceVol i = new InstanceVol();
           i.importFromId("4");
           i.setEtat("Arrive");
           dalExp.exportInstanceVol(i);
           
           i.setNumInstance(0);
           i.setPlacesRestAff(50);
           dalExp.exportInstanceVol(i);
           
            
            DBManager.commit();
            DBManager.dbDisconnect();
        } catch (SQLException ex) {
            Logger.getLogger(ProjetAirChance.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        System.exit(0);
    }
    
}
