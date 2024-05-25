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
import org.santiagocontreras.dto.CategoriaProductoDTO;
import org.santiagocontreras.model.CategoriaProducto;
import org.santiagocontreras.system.Main;
import org.santiagocontreras.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class FromCategoriaProductosController implements Initializable {

    private Main stage;
    private int op;
    private static Connection conexion;
    private static PreparedStatement statement;
    
    @FXML
    TextField tfID,tfNombreCategoria,tfDescripcion;
    @FXML
    Button btnGuardar,btnCancelar;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        if(CategoriaProductoDTO.getInstance().getCategoriaProducto() != null){
            cargarDatos(CategoriaProductoDTO.getInstance().getCategoriaProducto());
        }
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuCategoriaProductosView();
        }else if(event.getSource() == btnGuardar){
            
            if(op == 1){
                if(!(tfNombreCategoria.getText().equals("")&&tfDescripcion.getText().equals(""))){
                agregarCategoriaProducto();
                SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                stage.menuCategoriaProductosView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfNombreCategoria.requestFocus();
                    return;
                }
            }else if(op == 2){
                if(!(tfNombreCategoria.getText().equals("")&&tfDescripcion.getText().equals(""))){
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                        editarCategoriaProducto();
                        CategoriaProductoDTO.getInstance().setCategoriaProducto(null);
                        stage.menuCategoriaProductosView();
                    }else{
                        SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                        tfNombreCategoria.requestFocus();
                        return;
                    }
                }
            }
            
            
        }
    }
    
    public void cargarDatos(CategoriaProducto categoria){
        tfID.setText(Integer.toString(categoria.getCategoriaId()));
        tfNombreCategoria.setText(categoria.getNombreCategoria());
        tfDescripcion.setText(categoria.getDescripcionCategoria());
    }
    
    public void agregarCategoriaProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCategoriaProducto(?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setString(1,tfNombreCategoria.getText());
            statement.setString(2,tfDescripcion.getText());
            
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
    
    public void editarCategoriaProducto(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCategoriaProductos(?,?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1, Integer.parseInt(tfID.getText()));
            statement.setString(2,tfNombreCategoria.getText());
            statement.setString(3,tfDescripcion.getText());
            
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

    public int getOp() {
        return op;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
    
}
