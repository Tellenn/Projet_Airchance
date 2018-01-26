/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package GUI;

import Tables.Avion;
import Tables.AvionFret;
import Tables.AvionPassager;
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
        Avion a = new AvionFret();
        ((AvionFret)a).showTable();
        
        /*Avion b = new AvionPassager();
        ((AvionPassager)b).showTable();*/
       
       AvionFret.importAllTable();

        System.exit(0);
    }
    
}
