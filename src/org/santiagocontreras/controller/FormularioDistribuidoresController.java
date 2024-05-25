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
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.santiagocontreras.dao.Conexion;
import org.santiagocontreras.dto.DistribuidorDTO;
import org.santiagocontreras.model.Distribuidor;
import org.santiagocontreras.system.Main;
import org.santiagocontreras.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class FormularioDistribuidoresController implements Initializable {

    private Main stage;
    private int op;
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextArea taDireccion;
    @FXML
    TextField tfDistribuidorId,tfNombre,tfNit,tfTelefono,tfWeb;
    @FXML
    Button btnGuardar,btnCancelar;
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        if(DistribuidorDTO.getDistribuidorDTO().getDistribuidor() != null){
            cargarDatos(DistribuidorDTO.getDistribuidorDTO().getDistribuidor());
        }
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnGuardar){
            if(op == 1){
                if(!tfNombre.getText().equals("")&&!tfNit.getText().equals("")&&!tfTelefono.getText().equals("")){
                    agregarDistribuidor();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    stage.menuDistribuidores();
                }
            }else if(op==2){
                if(!tfNombre.getText().equals("")&&!tfNit.getText().equals("")&&!tfTelefono.getText().equals("")){
                    editarDistribuidor();
                    DistribuidorDTO.getDistribuidorDTO().setDistribuidor(null);
                    stage.menuDistribuidores();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNombre.requestFocus();
                    return;
                }
            }
        }else if(event.getSource() == btnCancelar){
            stage.menuDistribuidores();
        }
    }
    
    public void cargarDatos(Distribuidor distribuidor){
        tfDistribuidorId.setText(Integer.toString(distribuidor.getDistribuidorId()));
        tfNombre.setText(distribuidor.getNombreDistribuidor());
        tfNit.setText(distribuidor.getNitDistribuidor());
        tfTelefono.setText(distribuidor.getTelefonoDistribuidor());
        tfWeb.setText(distribuidor.getWeb());
    }
    
    
    
    // Agregar
    public void agregarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarDistribuidores(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setString(1,tfNombre.getText());
            statement.setString(2,taDireccion.getText());
            statement.setString(3,tfNit.getText());
            statement.setString(4,tfTelefono.getText());
            statement.setString(5,tfWeb.getText());
            statement.execute();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement!=null){
                    statement.close();
                }
                if(conexion!=null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }   
        }
    }
    
    // Editar
    public void editarDistribuidor(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarDistribuidores(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setString(1,tfNombre.getText());
            statement.setString(2,taDireccion.getText());
            statement.setString(3,tfNit.getText());
            statement.setString(4,tfTelefono.getText());
            statement.setString(5,tfWeb.getText());
            statement.execute();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement!=null){
                    statement.close();
                }
                if(conexion!=null){
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

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }

    
    
}
