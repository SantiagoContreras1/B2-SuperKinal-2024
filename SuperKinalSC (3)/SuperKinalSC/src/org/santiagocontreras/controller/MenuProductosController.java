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
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.santiagocontreras.dao.Conexion;
import org.santiagocontreras.model.Empleado;
import org.santiagocontreras.model.Producto;
import org.santiagocontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author informatica
 */
public class MenuProductosController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet = null;
    
    
    @FXML
    TableView tblProductos;
    @FXML
    Button btnVolver,btnAgregar,btnEditar,btnEliminar,btnBuscar;
    @FXML
    TableColumn colId,colNombre,colDescripcion,colStock,colPrecioUnitario,colPrecioMayor,colPrecioCompra,colImagen,colDistribuidor,colCategoria;
    @FXML
    TextField tfProductoId;
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if (event.getSource() == btnVolver){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formularioProductosView(1);
        }else if(event.getSource() == btnBuscar){
            tblProductos.getItems().clear();
            
            if(tfProductoId.getText().equals("")){
                cargarDatos();
                
            }else{
                Producto producto = buscarProducto();
                
                if(producto != null){
                    ObservableList<Producto> productosList = FXCollections.observableArrayList();;
                    productosList.add(producto);
                    tblProductos.setItems(productosList);
                    colId.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("productoId"));
                    colNombre.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombreProducto"));
                    colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto,String>("descripcionProducto"));
                    colStock.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("cantidadStock"));
                    colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioVentaUnitario"));
                    colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioVentaMayor"));
                    colPrecioCompra.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioCompra"));
                    colImagen.setCellValueFactory(new PropertyValueFactory<Producto,Byte>("imagenProducto"));
                    colDistribuidor.setCellValueFactory(new PropertyValueFactory<Producto,String>("distribuidor"));
                    colCategoria.setCellValueFactory(new PropertyValueFactory<Producto,String>("categoriaProductos"));
                    tblProductos.getSortOrder().add(colId);
                }
            }
        }else if(event.getSource() == btnEliminar){
            int productoId = ((Producto)tblProductos.getSelectionModel().getSelectedItem()).getProductoId();
            eliminarProducto(productoId);
            cargarDatos();
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    }    
    
    
    
    
    // Listar y Cargar datos
        //Cargar Datos
    public void cargarDatos(){
        tblProductos.setItems(listarProductos());
        colId.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("productoId"));
        colNombre.setCellValueFactory(new PropertyValueFactory<Producto,String>("nombreProducto"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Producto,String>("descripcionProducto"));
        colStock.setCellValueFactory(new PropertyValueFactory<Producto,Integer>("cantidadStock"));
        colPrecioUnitario.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioVentaUnitario"));
        colPrecioMayor.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioVentaMayor"));
        colPrecioCompra.setCellValueFactory(new PropertyValueFactory<Producto,Double>("precioCompra"));
        colImagen.setCellValueFactory(new PropertyValueFactory<Producto,Byte>("imagenProducto"));
        colDistribuidor.setCellValueFactory(new PropertyValueFactory<Producto,String>("distribuidor"));
        colCategoria.setCellValueFactory(new PropertyValueFactory<Producto,String>("categoriaProductos"));
        tblProductos.getSortOrder().add(colId);
    }
    
    
    
    
    
    
        //Listar
    public ObservableList<Producto>listarProductos(){
        ArrayList<Producto> productos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarProducto()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                byte[] imagenProducto = resultSet.getBytes("imagenProducto");
                String distribuidor = resultSet.getString("Distribuidores");
                String categoriaProductos = resultSet.getString("Categoria");
                
                productos.add(new Producto(productoId,nombreProducto,descripcionProducto,cantidadStock,precioVentaUnitario,precioVentaMayor,precioCompra,imagenProducto,distribuidor,categoriaProductos));
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
        
        
        
        return FXCollections.observableList(productos);
    }
    
    public Producto buscarProducto(){
        Producto producto = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_BuscarProducto(?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1, Integer.parseInt(tfProductoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int productoId = resultSet.getInt("productoId");
                String nombreProducto = resultSet.getString("nombreProducto");
                String descripcionProducto = resultSet.getString("descripcionProducto");
                int cantidadStock = resultSet.getInt("cantidadStock");
                double precioVentaUnitario = resultSet.getDouble("precioVentaUnitario");
                double precioVentaMayor = resultSet.getDouble("precioVentaMayor");
                double precioCompra = resultSet.getDouble("precioCompra");
                byte[] imagenProducto = resultSet.getBytes("imagenProducto");
                String distribuidor = resultSet.getString("distribuidorId");
                String categoriaProductos = resultSet.getString("categoriaProductosId");
                
                producto = new Producto(productoId,nombreProducto,descripcionProducto,cantidadStock,precioVentaUnitario,precioVentaMayor,precioCompra,imagenProducto,distribuidor,categoriaProductos);
                
            }
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
        
        return producto;
    }
    
    
    public void eliminarProducto(int productoId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_EliminarProducto(?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1, productoId);
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
    
    

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
