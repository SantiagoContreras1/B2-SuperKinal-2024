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
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.Scene;
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
public class MenuClientesController implements Initializable {
    private Main stage;
    private Scene scene;
    
    private static Connection conexion = null;  // Abre la conexion
    private static PreparedStatement statement = null;  // Almacena la consulta, osea el call _____
    private static ResultSet resultSet = null; // Resultado de la consulta
    
    @FXML
    TableView tblClientes;
    @FXML
    TableColumn colClienteID, colNombre, colApellido,colNit, colTelefono, colDireccion;
    @FXML
    Button btnAgregar,btnEditar,btnEliminar,btnReporte,btnVolver,btnBuscar;
    @FXML
    TextField tfClienteID;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarLista();
    }    
    
    public void cargarLista(){
        tblClientes.setItems(listarClientes());
        colClienteID.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("clienteId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
        colNit.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nit"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
        
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
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnVolver){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formularioClientesView(1);
        }else if(event.getSource() == btnEditar){
                                                  // Componente    // Metodo  ------> Metodo dentro es un objeto sin formato
            
            ClienteDTO.getClienteDTO().setCliente((Cliente)tblClientes.getSelectionModel().getSelectedItem());
            stage.formularioClientesView(2);      // Cliente le da el formato, o sea lo que vamos a convertir
        }else if(event.getSource() == btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(403).get() == ButtonType.OK){
               int cliId = ((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getClienteId();
               eliminarCliente(cliId);
               cargarLista();
            }
                    
        }else if(event.getSource() == btnBuscar){
            tblClientes.getItems().clear();
            if(tfClienteID.getText().equals("")){
                cargarLista();
            }else{
                tblClientes.getItems().add(buscarCliente());
                colClienteID.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("clienteId"));
                colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
                colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
                colNit.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nit"));
                colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
                colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));

            }
            
        }
    }
    
    public void eliminarCliente(int clienteID){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EliminarCliente(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1,clienteID);
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
    
    public Cliente buscarCliente(){
        Cliente cliente = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarCliente(?)";
            statement = conexion.prepareStatement(sql);
            
            
            statement.setInt(1, Integer.parseInt(tfClienteID.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int clienteID = resultSet.getInt("clienteId");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String nit = resultSet.getString("nit");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
            
                cliente = (new Cliente(clienteID, nombre, apellido, nit, telefono,direccion));
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
        return cliente;
    }
   
    
    
    
    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
