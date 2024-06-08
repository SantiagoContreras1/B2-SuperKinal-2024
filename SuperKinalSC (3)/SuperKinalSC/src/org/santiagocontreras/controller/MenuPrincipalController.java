/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import org.santiagocontreras.system.Main;

/**
 *
 * @author senor
 */
public class MenuPrincipalController implements Initializable{
    private Main stage;
    
    @FXML
    MenuItem btnMenuClientesItem,btnTicketSoporteItem,btnCargos,btnDistribuidores,btnEmpleados,btnProductos,btnCategoriaProductos,btnFacturas,btnCompras,btnPromociones;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
        
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnMenuClientesItem){
            stage.menuClientesView();
        }else if(event.getSource() == btnTicketSoporteItem){
            stage.menuTicketSoporteView();     
        }else if(event.getSource() == btnCargos){
            stage.menuCargosView();
        }else if(event.getSource() == btnDistribuidores){
            stage.menuDistribuidores();
        }else if(event.getSource() == btnEmpleados){
            stage.menuEmpleadosView();
        }else if(event.getSource() == btnProductos){
            stage.menuProductosView();
        }else if(event.getSource() == btnCategoriaProductos){
            stage.menuCategoriaProductosView();
        }else if(event.getSource() == btnFacturas){
            stage.menuFacturasView();
        }
    }
    
}
