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
import java.sql.Time;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.santiagocontreras.dao.Conexion;
import org.santiagocontreras.model.Empleado;
import org.santiagocontreras.model.NivelAcceso;
import org.santiagocontreras.system.Main;
import org.santiagocontreras.utils.PasswordUtils;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class FromUsuarioController implements Initializable {
    
    private Main stage;
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TextField tfUser,tfPassword;
    @FXML
    ComboBox cmbEmpleado,cmbNivelAcceso;
    @FXML
    Button btnRegistrar,btnEmpleados;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegistrar){
            agregarUsuario();
            stage.menuLoginView();
        }else if(event.getSource() == btnEmpleados){
            stage.formularioEmpleadosView(0);
            // stage.fromUsuarioView();
        }
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbEmpleado.setItems(listarEmpleados());
        cmbNivelAcceso.setItems(listarNivelesAcceso());
    }  
    
    // Agregar
    public void agregarUsuario(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarUsuario(?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setString(1, tfUser.getText());
            statement.setString(2,PasswordUtils.getInstance().encryptedPassword(tfPassword.getText()));  // Se envia la password encriptada
            statement.setInt(3, ((NivelAcceso)cmbNivelAcceso.getSelectionModel().getSelectedItem()).getNivelAccesoId());
            statement.setInt(4, ((Empleado)cmbEmpleado.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
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
    }
    
           
    public ObservableList<Empleado>listarEmpleados(){
        ArrayList<Empleado> empleados = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarEmpleados()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId")/*Nombre Columna*/;
                String nombreEmpleado = resultSet.getString("nombreEmpleado")/*Nombre Columna*/;
                String apellidoEmpleado = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo")/*Nombre Columna*/;
                Time horaEntrada = resultSet.getTime("horaEntrada")/*Nombre Columna*/;
                Time horaSalida = resultSet.getTime("horaSalida")/*Nombre Columna*/;
                int encargadoId = resultSet.getInt("encargadoId");
                String cargo = resultSet.getString("Cargo");
                
                empleados.add(new Empleado(empleadoId, nombreEmpleado, apellidoEmpleado, sueldo, horaEntrada, horaSalida, cargo,encargadoId));
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
            }
        }
        
        
        
        
        return FXCollections.observableList(empleados);
    }
    
    public ObservableList<NivelAcceso> listarNivelesAcceso(){
        ArrayList<NivelAcceso> nivelesAcceso = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarNivelAcceso()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int nivelAccesoId = resultSet.getInt("nivelAccesoId");
                String nivelAcceso = resultSet.getString("nivelAcceso");
                
                nivelesAcceso.add(new NivelAcceso(nivelAccesoId,nivelAcceso));
                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
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
        
        
        return FXCollections.observableArrayList(nivelesAcceso);
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
