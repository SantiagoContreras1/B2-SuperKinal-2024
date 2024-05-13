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
import org.santiagocontreras.model.Cargo;
import org.santiagocontreras.system.Main;
import org.santiagocontreras.controller.MenuEmpleadosController;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class FormularioEmpleadoController implements Initializable {
    private Main stage;
    private MenuEmpleadosController actualizar;
    private int op;
    private static Connection conexion;
    private static PreparedStatement statement;
    private static ResultSet resultSet;
    
    @FXML
    TextField tfEmpleadoId,tfHoraEntrada,tfHoraSalida,tfNombre,tfApellido,tfSueldo;
    @FXML
    ComboBox cmbCargo,cmbEncargado;
    @FXML
    Button btnCancelar,btnGuardar;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cmbCargo.setItems(listarCargos());
    }  
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnCancelar){
            stage.menuEmpleadosView();
        }else if(event.getSource() == btnGuardar){
            if(tfEmpleadoId.getText().equals("")){
                agregarEmpleado();
                
                if(actualizar != null){
                    actualizar.cargarDatos();
                }
                
                stage.menuEmpleadosView();
            }
        }
    }
    
    // Agregar
    public void agregarEmpleado(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarEmpleado(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setString(1,tfNombre.getText());
            statement.setString(2, tfApellido.getText());
            statement.setDouble(3,Double.parseDouble(tfSueldo.getText()));
            statement.setTime(4,Time.valueOf(tfHoraEntrada.getText()));
            statement.setTime(5, Time.valueOf(tfHoraSalida.getText()));
            statement.setInt(6, ((Cargo)cmbCargo.getSelectionModel().getSelectedItem()).getCargoId());
            statement.execute();
            
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
    }
    
    // Cargos para el ComboBox
        public ObservableList<Cargo> listarCargos(){
        ArrayList<Cargo> cargos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarCargos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombre = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargos.add(new Cargo(cargoId,nombre,descripcionCargo));
                
                
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
        
        return FXCollections.observableList(cargos);
    }
    
    
       public void cargarDatosEditar(){
           //Empleado empleado = (Empleado)tblEmpleados
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
