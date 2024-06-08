/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author senor
 */
public class SuperKinalAlert {
    
    private static SuperKinalAlert instance;
    
    private SuperKinalAlert(){
        
    }
    
    public static SuperKinalAlert getInstance(){
        if(instance == null){
            instance = new SuperKinalAlert();
        }
        return instance;
    }
    
    public void mostrarAlertaInfo(int code){
        if(code == 400){                // Alewrta Campos pendientes
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Vacios");
            alert.setHeaderText("Faltan campos");
            alert.setContentText("Falta rellenar algunos campos para el registro");
            alert.showAndWait();
        }else if(code == 401){ // 401 Alerta confirmacion de creacion de registrp
             Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmacion de Registros");
            alert.setHeaderText("Confirmacion de Registro");
            alert.setContentText("Se ha creado el registro con exito");
            alert.showAndWait();
        }else if(code == 600){
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Usuario Incorrecto");
            alert.setHeaderText("Usuario Incorrecto");
            alert.setContentText("Falta rellenar algunos campos para el registro");
            alert.showAndWait();
        }
    }
    
    public Optional<ButtonType> mostrarAlertaConfirmacion(int code){  // Metodo para dar click en Aceptar o Cancelar
        Optional<ButtonType> action = null;
        if(code == 403){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Registro");
            alert.setHeaderText("Eliminar Registro");
            alert.setContentText("Desea eliminar este registro?");
            action = alert.showAndWait();
        }else if(code == 404){   // Alerta confirmacion para edicion de registros
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Edirat Registro");
            alert.setHeaderText("Eeditar Registro");
            alert.setContentText("Desea realizar la edicion del registro");
            action = alert.showAndWait();
        }
        
        
        return action;
    }
    
    public void alertaSaludo(String usuario){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bienvenido");
        alert.setHeaderText("Bienvenido "+usuario);
        alert.showAndWait();
    }
    

}
