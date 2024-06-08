/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import org.santiagocontreras.dao.Conexion;
import org.santiagocontreras.dto.ClienteDTO;
import org.santiagocontreras.model.Cliente;

import org.santiagocontreras.system.Main;
import org.santiagocontreras.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class FormularioClienteController implements Initializable {
    private Main stage;
    private int op;
    private static Connection conexion;
    private static PreparedStatement statement;
    
    
    
    
    @FXML
    Button btnGuardar,btnCancelar;
    @FXML
    TextField tfID, tfNombre,tfApellido,tfNit,tfTelefono,tfDireccion;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(ClienteDTO.getClienteDTO().getCliente() != null){
            cargarDatos(ClienteDTO.getClienteDTO().getCliente());
        }
       
    }    

    public void cargarDatos(Cliente cliente){
        tfID.setText(Integer.toString(cliente.getClienteId()));
        tfNombre.setText(cliente.getNombre());
        tfApellido.setText(cliente.getApellido());
        tfNit.setText(cliente.getNit());
        tfTelefono.setText(cliente.getTelefono());
        tfDireccion.setText(cliente.getDireccion());
        
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(op == 1){
                
                if(!tfNombre.getText().equals("") && !tfApellido.getText().equals("") && !tfDireccion.getText().equals("")){
                    agregarCliente();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    stage.menuClientesView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNombre.requestFocus();
                    return;
                }
                
                
            }else if(op == 2){
                
                
                
               if(!tfNombre.getText().equals("") && !tfApellido.getText().equals("") && !tfDireccion.getText().equals("")){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                        editarCliente();
                        ClienteDTO.getClienteDTO().setCliente(null);
                        stage.menuClientesView();
                    }else{
                        SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                        tfNombre.requestFocus();
                        return;
                    }
                }
                
            }
            
            
            
            
        }else if(event.getSource() == btnCancelar){
            ClienteDTO.getClienteDTO().setCliente(null);
            stage.menuClientesView();
        }
    }
    
    
   // Hacer el boton regresar
    
    public void editarCliente(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();   // Paso 1: Abrir la conexion
            String sql = "call sp_EditarCliente(?,?,?,?,?,?)";    // Preparar el String para recibir los parametros
            statement = conexion.prepareStatement(sql);           // Preparar Statement
            
            statement.setInt(1, Integer.parseInt(tfID.getText()));
            statement.setString(2,tfNombre.getText());
            statement.setString(3,tfApellido.getText());
            statement.setString(4,tfNit.getText());
            statement.setString(5,tfTelefono.getText());
            statement.setString(6,tfDireccion.getText());
            statement.execute();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                 if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                 System.out.println(e.getMessage());
            }
        }
    }
    
    public void agregarCliente(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarCliente(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setString(1,tfNombre.getText());
            statement.setString(2,tfApellido.getText());
            statement.setString(3,tfNit.getText());
            statement.setString(4,tfTelefono.getText());
            statement.setString(5,tfDireccion.getText());
            statement.execute();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }
                
                
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
    
}
