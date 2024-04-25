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
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;
import org.santiagocontreras.dao.Conexion;
import org.santiagocontreras.model.Cliente;

import org.santiagocontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class MenuTicketSoporteController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet =null;
    
    @FXML
    ComboBox cbmEstatus,cmbClientes;
    @FXML
    Button btnRegresar;
    
    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }
    }
    
    public void cargarCmbEstatus(){        // Se usa para llenar los combobox, importar el item
       cbmEstatus.getItems().add("En Proceso");   // Añade las opciones
       cbmEstatus.getItems().add("Finalizado");
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
    
    
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarCmbEstatus();
        cmbClientes.setItems(listarClientes());
    }    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
    
    
   
    
}
