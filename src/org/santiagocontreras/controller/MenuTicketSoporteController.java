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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.santiagocontreras.dao.Conexion;
import org.santiagocontreras.model.Cliente;
import org.santiagocontreras.model.TicketSoporte;

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
    Button btnRegresar,btnGuardar,btnVaciar;
    @FXML
    TableView tblTickets;
    @FXML
    TableColumn colId,colDescripcion,colEstatus,colCliente,colFactura;
    @FXML
    TextArea taDescripcion;
    @FXML
    TextField tfTicketId;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnGuardar){
            // Este boton va a tener doble funcion
            if(tfTicketId.getText().equals("")){
                agregarTicket();
                cargarDatos();
            }else{
                editarTicketSoporte();
                cargarDatos();
            }
        }else if(event.getSource() == btnVaciar){
            vaciarCampos();
        }
    }
    
    // Vacía todos los campos para crear un ticket nuevo
    public void vaciarCampos(){
        tfTicketId.clear();
        taDescripcion.clear();
        cbmEstatus.getSelectionModel().clearSelection();
        cmbClientes.getSelectionModel().clearSelection();
        
    }
    
    // Carga datos al TableView
    public void cargarDatos(){
        tblTickets.setItems(listarTickets());
        colId.setCellValueFactory(new PropertyValueFactory<TicketSoporte,Integer>("ticketSoporteId"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TicketSoporte,String>("descripcionTicket"));
        colEstatus.setCellValueFactory(new PropertyValueFactory<TicketSoporte,String>("estatus"));
        colCliente.setCellValueFactory(new PropertyValueFactory<TicketSoporte,String>("cliente"));
        colFactura.setCellValueFactory(new PropertyValueFactory<TicketSoporte,String>("facturaId"));
        tblTickets.getSortOrder().add(colId);
    }
    
    // Carga datos en los campor a Editar
    public void cargarDatosEditar(){
        TicketSoporte ts = (TicketSoporte)tblTickets.getSelectionModel().getSelectedItem();
        if(ts != null){
            tfTicketId.setText(Integer.toString(ts.getTicketSoporteId()));
            taDescripcion.setText(ts.getDescripcionTicket()); // Continuar
            cbmEstatus.getSelectionModel().select(0);
            cmbClientes.getSelectionModel().select(obtenerIndexCliente());
        }
    }
    
    
    // Carga el comboBox de Clientes
    public int obtenerIndexCliente(){
        int index = 0;
        for(int i = 0; i <= cmbClientes.getItems().size() ; i++){
            String clienteCmb = cmbClientes.getItems().get(1).toString();  // Ir a traer los textos del ComboBox
            String clienteTbl = ((TicketSoporte)tblTickets.getSelectionModel().getSelectedItem()).getCliente(); // Va a traer el texto específicamente de la comlumna Cliente
            if(clienteCmb.equals(clienteTbl)){
                index = i;
                break;
            }
        }
        
        return index;
    }
    
    
    // Cargar comboBox estatus
    public void cargarCmbEstatus(){        // Se usa para llenar los combobox, importar el item
       cbmEstatus.getItems().add("En Proceso");   // Añade las opciones
       cbmEstatus.getItems().add("Finalizado");
    }
    
    
    // Consulta al procedimiento de Listar
    public ObservableList<TicketSoporte> listarTickets(){
        ArrayList<TicketSoporte> tickets = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarTicketSoporte()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int ticketSoporteId = resultSet.getInt("ticketSoporteId");
                String descripcion = resultSet.getString("descripcionTicket");
                String estatus = resultSet.getString("estatus");
                String cliente = resultSet.getString("cliente");
                int facturaId = resultSet.getInt("facturaId");
                
                tickets.add(new TicketSoporte(ticketSoporteId,descripcion,estatus,cliente,facturaId));
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
        
        
        
        return FXCollections.observableList(tickets);
    }
    
    
    // Consulta al procedimiento almacenado de ListarClientes en la DB que se utiliza para llenar el comboBox clientes
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
    
    public void agregarTicket(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarTicketSoporte(?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setString(1,taDescripcion.getText());
            statement.setInt(2,((Cliente)cmbClientes.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(3,1);
            statement.execute(); // Envia la consulta a la base de datos
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
    }
    
    public void editarTicketSoporte(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EditarTicketSoporte(?,?,?,?,?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfTicketId.getText()));
            statement.setString(2,taDescripcion.getText());
            statement.setString(3,cbmEstatus.getSelectionModel().getSelectedItem().toString());
            statement.setInt(4,((Cliente)cmbClientes.getSelectionModel().getSelectedItem()).getClienteId());
            statement.setInt(5, 1);
            statement.executeQuery();
            
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
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarCmbEstatus();
        cmbClientes.setItems(listarClientes());
        cargarDatos();
    }    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
    
    
   
    
}
