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
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import org.santiagocontreras.model.Cliente;
import org.santiagocontreras.model.Empleado;
import org.santiagocontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class FromFacturasController implements Initializable {

    private Main stage;
    private MenuFacturasController actualizar;
    private int op;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet =null;
    
    
    @FXML
    TextField tfFacturaId,tfTotal,tfFecha,tfHora;
    @FXML
    ComboBox cmbCliente,cmbEmpleado;
    @FXML
    Button btnGuardar,btnCancelar;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbCliente.setItems(listarClientes());
        cmbEmpleado.setItems(listarEmpleados());
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws ParseException{
        if(event.getSource() == btnCancelar){
            stage.menuFacturasView();
        }else if(event.getSource() == btnGuardar){
            if(tfFacturaId.getText().equals("")){
                agregarFactura();
                
                if(actualizar != null){
                    actualizar.cargarDatos();
                }
                
                stage.menuFacturasView();
            }
        }
    }
    
    public void agregarFactura() throws ParseException{
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarFactura(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setDate(1, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(tfFecha.getText()).getTime()));
            statement.setTime(2, Time.valueOf(tfHora.getText()));
            statement.setInt(3,((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(4, ((Empleado)cmbEmpleado.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.setDouble(5,Double.parseDouble(tfTotal.getText()));
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
    
    public void editarFactura() throws ParseException{
         try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarFactura(?,?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1, Integer.parseInt(tfFacturaId.getText()));
            statement.setDate(2, new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(tfFecha.getText()).getTime()));
            statement.setTime(3, Time.valueOf(tfHora.getText()));
            statement.setInt(4,((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(5, ((Empleado)cmbEmpleado.getSelectionModel().getSelectedItem()).getEmpleadoId());
            statement.setDouble(6,Double.parseDouble(tfTotal.getText()));
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
    
    public ObservableList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();   // Esto abre la conexión
            String sql = "call sp_ListarClientes()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int clienteId = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String nit = resultSet.getString("nit");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                
                clientes.add(new Cliente(clienteId, nombre, apellido,nit, telefono,direccion));
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
        
        return FXCollections.observableList(clientes);
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
