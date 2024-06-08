/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.santiagocontreras.dao.Conexion;
import org.santiagocontreras.model.Usuario;
import org.santiagocontreras.system.Main;
import org.santiagocontreras.utils.PasswordUtils;
import org.santiagocontreras.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author informatica
 * 
 * 
 * 
 * ARREGLAR ALERTA
 */
public class MenuLoginController implements Initializable {

    private Main stage;
    private int op = 0;
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TextField tfUsuario;
    @FXML
    PasswordField tfPass;
    @FXML
    Button btnIngresar,btnRegistrar;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnIngresar){
            
            
            if(op == 0){
                // Validar el Usuario
            Usuario usuario = buscarUsuario();
            
            if(usuario != null){
                
                if(PasswordUtils.getInstance().checkPassword(tfPass.getText(), usuario.getPass())){
                    
                    if(usuario.getNivelAcceso() == 1){
                        btnRegistrar.setDisable(false);
                        btnIngresar.setText("Ir al Menu");
                        op = 1;
                        
                    }else if(usuario.getNivelAcceso() == 2){
                        stage.menuPrincipalView();
                    }
                    
                    
                    
                }else{
                    // Mostrar alerta
                }
                                
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(600);
                }
            }else{
                stage.menuPrincipalView();
            }
            
            
          
          
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TODO
    }  
    
    public Usuario buscarUsuario(){
        Usuario usuario = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarUsuario(?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setString(1,tfUsuario.getText());
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int usuarioId = resultSet.getInt("usuarioId"); // Nombre columna de sql
                String user = resultSet.getString("usuario");
                String pass = resultSet.getString("pass");
                int nivelAccesoId = resultSet.getInt("nivelAccesoId");
                int empleadoId = resultSet.getInt("empleadoId");
                
                usuario = new Usuario(usuarioId,user,pass,nivelAccesoId,empleadoId);
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
             try{
                if(resultSet != null){
                    resultSet.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return usuario;
    }
    
    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
    
}
