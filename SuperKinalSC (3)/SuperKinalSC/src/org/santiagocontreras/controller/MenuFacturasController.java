/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.controller;

import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.santiagocontreras.dao.Conexion;
import org.santiagocontreras.model.Factura;
import org.santiagocontreras.report.GenerarReporte;
import org.santiagocontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class MenuFacturasController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet =null;
    
    @FXML
    TableView tblFacturas;
    @FXML
    TableColumn colFacturaId,colFecha,colHora,colCliente,colEmpleado,colTotal,colProductoId;
    @FXML
    Button btnVolver,btnAgregar,btnEditar,btnEliminar,btnBuscar,btnReportes;
    @FXML
    TextField tfFacturaId;

    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnVolver){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.fromFacturaView(1);
        }else if(event.getSource() == btnEditar){
            stage.fromFacturaView(0);
        }else if(event.getSource() == btnEliminar){
            int facturaId = ((Factura)tblFacturas.getSelectionModel().getSelectedItem()).getFacturaId();
            eliminarFactura(facturaId);
            cargarDatos();
        }else if(event.getSource() == btnBuscar){
            tblFacturas.getItems().clear();
            
            if(btnBuscar.getText().equals("")){
                cargarDatos();
            }else{
                Factura factura = buscarFactura();
                if(factura != null){
                    ObservableList<Factura> facturaList = FXCollections.observableArrayList();
                    tblFacturas.setItems(listarFacturas());
                    colFacturaId.setCellValueFactory(new PropertyValueFactory<Factura,Integer>("empleadoId"));
                    colFecha.setCellValueFactory(new PropertyValueFactory<Factura,Date>("fecha"));
                    colHora.setCellValueFactory(new PropertyValueFactory<Factura,Time>("hora"));
                    colCliente.setCellValueFactory(new PropertyValueFactory<Factura,String>("Cliente"));
                    colEmpleado.setCellValueFactory(new PropertyValueFactory<Factura,String>("Empleado"));
                    colTotal.setCellValueFactory(new PropertyValueFactory<Factura,Double>("total"));
                    tblFacturas.getSortOrder().add(colFacturaId);
                }
            }
        }else if(event.getSource() == btnReportes){
            GenerarReporte.getInstance().generarFactura(Integer.parseInt(colFacturaId.getText()));
        }
    }
    
    public void cargarDatos(){
        tblFacturas.setItems(listarFacturas());
        colFacturaId.setCellValueFactory(new PropertyValueFactory<Factura,Integer>("empleadoId"));
        colFecha.setCellValueFactory(new PropertyValueFactory<Factura,Date>("fecha"));
        colHora.setCellValueFactory(new PropertyValueFactory<Factura,Time>("hora"));
        colCliente.setCellValueFactory(new PropertyValueFactory<Factura,String>("Cliente"));
        colEmpleado.setCellValueFactory(new PropertyValueFactory<Factura,String>("Empleado"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Factura,Double>("total"));
        tblFacturas.getSortOrder().add(colFacturaId);
    }
    
    
    
    
    public ObservableList<Factura> listarFacturas(){
        ArrayList<Factura> factura = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarFacturas()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");/*Nombre Columna*/
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                String cliente = resultSet.getString("Cliente");
                String empleado = resultSet.getString("Empleado");
                Double total = resultSet.getDouble("total");
                
                factura.add(new Factura(facturaId,fecha,hora,cliente,empleado,total));
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
        return FXCollections.observableList(factura);
    }
    
    public void eliminarFactura(int facturaId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EliminarFactura(?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1, facturaId);
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
                e.printStackTrace();
            }
        }
    }
    
    public Factura buscarFactura(){
        Factura factura = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarFactura(?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1, Integer.parseInt(tfFacturaId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int facturaId = resultSet.getInt("facturaId");/*Nombre Columna*/
                Date fecha = resultSet.getDate("fecha");
                Time hora = resultSet.getTime("hora");
                String cliente = resultSet.getString("Cliente");
                String empleado = resultSet.getString("Empleado");
                Double total = resultSet.getDouble("total");
                
                factura = (new Factura(facturaId,fecha,hora,cliente,empleado,total));
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
        
        
        return factura;
    }
    
    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
        
    
}
