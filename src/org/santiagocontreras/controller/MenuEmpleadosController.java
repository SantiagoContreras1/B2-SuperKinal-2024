/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.controller;

import static com.sun.org.apache.xalan.internal.lib.ExsltDatetime.time;
import java.awt.event.MouseEvent;
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
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.santiagocontreras.dao.Conexion;
import org.santiagocontreras.model.Cargo;
import org.santiagocontreras.model.Empleado;
import org.santiagocontreras.system.Main;

// NO FUNCIONA EL BUSCAR Y EL EDITAR



public class MenuEmpleadosController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet =null;
    
    @FXML
    TextField tfEmpleadoId,tfBuscar;
    @FXML
    ComboBox cmbCargo,cmbEncargado;
    @FXML
    Button btnVolver,btnAgregar,btnEditar,btnEliminar,btnBuscar;
    @FXML
    TableView tblEmpleados;
    @FXML
    TableColumn colId,colNombre,colApellido,colSueldo,colHoraEntrada,colHoraSalida,colCargo,colEncargado;

    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
       cmbCargo.setItems(listarCargos());
       cargarDatos();
    }   
    
    
    @FXML
    public void handleButtonAction(ActionEvent event) {
        if(event.getSource() == btnVolver){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formularioEmpleadosView(1);
        }else if(event.getSource() == btnBuscar){
            tblEmpleados.getItems().clear();
            
            if(tfBuscar.getText().equals("")){
                cargarDatos();
            }else{
                Empleado empleado = buscarEmpleado();
                if(empleado != null){
                    ObservableList<Empleado>empleadosList = FXCollections.observableArrayList();
                    empleadosList.add(empleado);
                    tblEmpleados.setItems(empleadosList);
                    colId.setCellValueFactory(new PropertyValueFactory<Empleado,Integer>("empleadoId"));
                    colNombre.setCellValueFactory(new PropertyValueFactory<Empleado,String>("nombreEmpleado"));
                    colApellido.setCellValueFactory(new PropertyValueFactory<Empleado,String>("apellidoEmpleado"));
                    colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, Double>("sueldo"));
                    colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Empleado,Time>("horaEntrada"));
                    colHoraSalida.setCellValueFactory(new PropertyValueFactory<Empleado,Time>("horaSalida"));
                    colCargo.setCellValueFactory(new PropertyValueFactory<Empleado,String>("Cargo"));
                    colEncargado.setCellValueFactory(new PropertyValueFactory<Empleado,String>("encargadoId"));
                    tblEmpleados.getSortOrder().add(colId);//Ordena los ID's de menor a mayor

                }
            }
        }else if(event.getSource() == btnEliminar){
            int empleadoId = ((Empleado)tblEmpleados.getSelectionModel().getSelectedItem()).getEmpleadoId();
            eliminarEmpleado(empleadoId);
            cargarDatos();
            
        }
    }
    
    
    // Listar
    
        //Cargar Datos al TableView
        
    public void cargarDatos(){
        tblEmpleados.setItems(listarEmpleados());
        colId.setCellValueFactory(new PropertyValueFactory<Empleado,Integer>("empleadoId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Empleado,String>("nombreEmpleado"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Empleado,String>("apellidoEmpleado"));
        colSueldo.setCellValueFactory(new PropertyValueFactory<Empleado, Double>("sueldo"));
        colHoraEntrada.setCellValueFactory(new PropertyValueFactory<Empleado,Time>("horaEntrada"));
        colHoraSalida.setCellValueFactory(new PropertyValueFactory<Empleado,Time>("horaSalida"));
        colCargo.setCellValueFactory(new PropertyValueFactory<Empleado,String>("cargoId"));
        colEncargado.setCellValueFactory(new PropertyValueFactory<Empleado,String>("encargadoId"));
        tblEmpleados.getSortOrder().add(colId);//Ordena los ID's de menor a mayor
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
    
    
    
    
    //Eliminar
    public void eliminarEmpleado(int empleadoId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EliminarEmpleado(?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1, empleadoId);
            statement.execute();
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }
    
    
    //Buscar
    public Empleado buscarEmpleado(){
        Empleado empleado = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarEmpleado(?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1,Integer.parseInt(tfBuscar.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int empleadoId = resultSet.getInt("empleadoId"/*Nombre de la Columna*/);
                String nombre = resultSet.getString("nombreEmpleado");
                String apellido = resultSet.getString("apellidoEmpleado");
                double sueldo = resultSet.getDouble("sueldo");
                Time horaEntrada = resultSet.getTime("horaEntrada");
                Time horaSalida = resultSet.getTime("horaSalida");
                String cargo = resultSet.getString("Cargo");
                String encargado = resultSet.getString("encargadoId");
                
                
                empleado = (new Empleado(empleadoId,nombre,apellido,sueldo,horaEntrada,horaSalida,encargado,cargo));
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
            }
        }
        
        
        
        return empleado;
    }
    
    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
