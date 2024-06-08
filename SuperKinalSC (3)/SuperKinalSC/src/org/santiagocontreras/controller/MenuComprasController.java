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
import org.santiagocontreras.model.Compras;
import org.santiagocontreras.model.DetalleCompra;
import org.santiagocontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class MenuComprasController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet =null;
    
    
    @FXML
    TableView tblCompras;
    @FXML
    TableColumn colCompraId,colFecha,colTotal,colCantidad,colProducto;
    @FXML
    TextField tfCompraId;
    @FXML
    Button btnVolver,btnAgregar,btnEditar,btnEliminar,btnBuscar;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatosCompras();
        cargarDatosDetalle();
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnVolver){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnEliminar){
            int compra = ((Compras)tblCompras.getSelectionModel().getSelectedItem()).getCompraId();
            int detalleCompra = ((DetalleCompra)tblCompras.getSelectionModel().getSelectedItem()).getDetalleCompraId();
            eliminarCompra(compra,detalleCompra);
            cargarDatosCompras();
            cargarDatosDetalle();
        }else if(event.getSource() == btnBuscar){
            tblCompras.getItems().clear();
            
            if(tfCompraId.getText().equals("")){
                cargarDatosCompras();
                cargarDatosDetalle();
            }else{
                Compras compra = buscarCompra();
                DetalleCompra detalleCompra = buscarDetalleCompra();
                
                if (compra!=null && detalleCompra != null){
                    ObservableList<Compras> comprasList = FXCollections.observableArrayList();
                    tblCompras.setItems(listarCompras());                               /*Nombre del Modelo*/
                    colCompraId.setCellValueFactory(new PropertyValueFactory<Compras,Integer>("compraId")); 
                    colFecha.setCellValueFactory(new PropertyValueFactory<Compras,Date>("fechaCompra"));
                    colTotal.setCellValueFactory(new PropertyValueFactory<Compras,Double>("total"));
                    tblCompras.getSortOrder().add(colCompraId);
                    tblCompras.setItems(listarDetalleCompra());                             /*Nombre del Modelo*/
                    colCantidad.setCellValueFactory(new PropertyValueFactory<Compras,Integer>("cantidadCompra"));
                    colProducto.setCellValueFactory(new PropertyValueFactory<Compras,String>("producto"));
                }
            }
        }else if(event.getSource() == btnAgregar){
            stage.fromComprasView(1);
        }else if(event.getSource() == btnEditar){
            stage.fromComprasView(2);
        }
    }
    
    public void cargarDatosCompras(){
        tblCompras.setItems(listarCompras());                               /*Nombre del Modelo*/
        colCompraId.setCellValueFactory(new PropertyValueFactory<Compras,Integer>("compraId")); 
        colFecha.setCellValueFactory(new PropertyValueFactory<Compras,Date>("fechaCompra"));
        colTotal.setCellValueFactory(new PropertyValueFactory<Compras,Double>("total"));
        tblCompras.getSortOrder().add(colCompraId);
    }
    
    public void cargarDatosDetalle(){
        tblCompras.setItems(listarDetalleCompra());                             /*Nombre del Modelo*/
        colCantidad.setCellValueFactory(new PropertyValueFactory<Compras,Integer>("cantidadCompra"));
        colProducto.setCellValueFactory(new PropertyValueFactory<Compras,String>("producto"));
    }
    
    
    
    
    
    
    public ObservableList<Compras> listarCompras(){
        ArrayList<Compras> compras = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarCompra()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int compraId = resultSet.getInt("compraId");  /*Nombre Columna*/
                Date fechaCompra = resultSet.getDate("fechaCompra");
                double totalCompra = resultSet.getDouble("totalCompra");
                
                compras.add(new Compras(compraId,fechaCompra,totalCompra));
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
        
        
        
        return FXCollections.observableList(compras);
    }
    
    public ObservableList<DetalleCompra> listarDetalleCompra(){
        ArrayList<DetalleCompra> detalleCompra = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarDetalleCompra()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){                 /*Nombre Columna*/
                int cantidadCompra = resultSet.getInt("cantidadCompra");
                String producto = resultSet.getString("ProductoDetalle");
                
                detalleCompra.add(new DetalleCompra(cantidadCompra,producto));
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
        
        
        return FXCollections.observableList(detalleCompra);
    }
    
    public void eliminarCompra(int compraId,int detalleCompraId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EliminarCompra(?)";
            String sql2 = "call sp_EliminarDetalleCompra(?)";
            statement = conexion.prepareStatement(sql);
            statement = conexion.prepareStatement(sql2);
            
            statement.setInt(1,compraId);
            statement.setInt(1, detalleCompraId);
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
    
    public Compras buscarCompra(){
        Compras compra = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCompras(?)";
            String sql2 = "call sp_buscarDetalleCompra(?)";
            statement = conexion.prepareStatement(sql);
            statement = conexion.prepareStatement(sql2);
            
            statement.setInt(1,Integer.parseInt(tfCompraId.getText()));
            
            statement.setInt(1,Integer.parseInt(tfCompraId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int compraId = resultSet.getInt("compraId");  /*Nombre Columna*/
                Date fechaCompra = resultSet.getDate("fechaCompra");
                double totalCompra = resultSet.getDouble("totalCompra");
                
                compra = (new Compras(compraId,fechaCompra,totalCompra));
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
        
        
        
        return compra;
    }
    
    public DetalleCompra buscarDetalleCompra(){
        DetalleCompra detalleCompra = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarDetalleCompra(?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1,Integer.parseInt(tfCompraId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int cantidadCompra = resultSet.getInt("cantidadCompra");  /*Nombre Columna*/
                String productoDetalle = resultSet.getString("ProductoDetalle");
                
                detalleCompra = (new DetalleCompra(cantidadCompra,productoDetalle));
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
        
        
        
        return detalleCompra;
    }
    
    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
}
