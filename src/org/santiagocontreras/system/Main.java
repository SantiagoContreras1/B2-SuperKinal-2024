/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.system;

import java.io.InputStream;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.santiagocontreras.controller.FormularioCargosController;
import org.santiagocontreras.controller.FormularioClienteController;
import org.santiagocontreras.controller.FormularioDistribuidoresController;
import org.santiagocontreras.controller.FormularioEmpleadoController;
import org.santiagocontreras.controller.FormularioProductosController;
import org.santiagocontreras.controller.MenuCargosController;
import org.santiagocontreras.controller.MenuClientesController;
import org.santiagocontreras.controller.MenuDistribuidoresController;
import org.santiagocontreras.controller.MenuEmpleadosController;
import org.santiagocontreras.controller.MenuPrincipalController;
import org.santiagocontreras.controller.MenuProductosController;
import org.santiagocontreras.controller.MenuTicketSoporteController;

/**
 *
 * @author senor
 */
public class Main extends Application {
    private Stage stage;
    private Scene scene;
    private final String URLVIEW = "/org/santiagocontreras/view/";
    
    @Override
    public void start(Stage stage){
        this.stage = stage;
        Image icon = new Image("org/santiagocontreras/image/icono.png");
        
        stage.setTitle("SuperKinal S.A.");
        stage.getIcons().add(icon);
        stage.show();
        menuPrincipalView();
        
        
    }
    
    
    public Initializable switchScene(String fxmlName, int width, int height) throws Exception{
        Initializable resultado;
        FXMLLoader loader = new FXMLLoader();
        
        
        InputStream file = Main.class.getResourceAsStream(URLVIEW + fxmlName); // fxmlName es un parametro para que el usuario le diga al programa cual de todas las vistas quiere mostrar
        loader.setBuilderFactory(new JavaFXBuilderFactory()); // Construye la escena
        loader.setLocation(Main.class.getResource(URLVIEW + fxmlName));
        
        // crear la escena
        scene = new Scene((AnchorPane)loader.load(file));
        stage.setScene(scene);
        stage.sizeToScene();
        
        resultado = (Initializable)loader.getController();
        return resultado;
        // Esto funciona pero no muestra nada
    }
    
    
    
    public void menuPrincipalView(){ // Este metodo va a traer la escena
        try{
            MenuPrincipalController menuPrincipalView = (MenuPrincipalController)switchScene("MenuPrincipalView.fxml",1300,700);
            // Asignar escena al Controllador
            menuPrincipalView.setStage(this);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    public void menuClientesView(){
        try{
            MenuClientesController menuClientesView = (MenuClientesController)switchScene("MenuClientesWiew.fxml",1875,815);
            menuClientesView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
     public void formularioClientesView(int op){
        try{
          FormularioClienteController menuFormularioClienteView = (FormularioClienteController)switchScene("MenuFormularioClienteView.fxml",500,580);
          menuFormularioClienteView.setOp(op);
          menuFormularioClienteView.setStage(this);
            
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
     
     
     public void menuTicketSoporteView(){
         try{
             MenuTicketSoporteController menuTicketSoporteView = (MenuTicketSoporteController)switchScene("MenuTicketSoporteView.fxml",1200,700);
             menuTicketSoporteView.setStage(this);
         }catch(Exception e){
             System.out.println(e.getMessage());
             e.printStackTrace();
         }
     }
     
     
     public void menuCargosView(){
         try{
             MenuCargosController menuCargosView = (MenuCargosController)switchScene("MenuCargosView.fxml",1875,815);
             menuCargosView.setStage(this);
         }catch(Exception e){
             System.out.println(e.getMessage());
             e.printStackTrace();
         }
         
         
     }
     
     public void formularioCargosView(int op){
         try{
             FormularioCargosController formularioCargosView = (FormularioCargosController)switchScene("FormularioCargosView.fxml",1875,815);
             formularioCargosView.setOp(op);
             formularioCargosView.setStage(this);
         }catch(Exception e){
             System.out.println(e.getMessage());
             e.printStackTrace();
         }
     }

    public void menuDistribuidores(){
        try{
            MenuDistribuidoresController menuDistribuidoresView = (MenuDistribuidoresController)switchScene("MenuDistribuidoresView.fxml",1875,815);
            menuDistribuidoresView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
     
    public void formularioDistribuidoresView(int op){
        try{
            FormularioDistribuidoresController formularioDistribuidoresView = (FormularioDistribuidoresController)switchScene("FormularioDistribuidoresView.fxml",1875,815);
            formularioDistribuidoresView.setOp(op);
            formularioDistribuidoresView.setStage(this);    
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void menuEmpleadosView(){
        try{
            MenuEmpleadosController menuEmpleadosView = (MenuEmpleadosController)switchScene("MenuEmpleadosView.fxml",1875,815);
            menuEmpleadosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void formularioEmpleadosView(int op){
        try{
            FormularioEmpleadoController formularioEmpleadoView = (FormularioEmpleadoController)switchScene("FormularioEmpleadoView.fxml",500,534);
            formularioEmpleadoView.setOp(op);
            formularioEmpleadoView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        
        
    }
    
    public void menuProductosView(){
        try{
            MenuProductosController menuProductosView = (MenuProductosController)switchScene("MenuProductosView.fxml",1600,815);
            menuProductosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void formularioProductosView(int op){
        try{
            FormularioProductosController formularioProductosView = (FormularioProductosController)switchScene("FormularioProductosView.fxml",1600,800);
            formularioProductosView.setOp(op);
            formularioProductosView.setStage(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    
    
     

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    
    
}
