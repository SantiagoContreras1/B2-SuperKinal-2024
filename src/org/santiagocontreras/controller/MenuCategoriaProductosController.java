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
import org.santiagocontreras.model.CategoriaProducto;
import org.santiagocontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class MenuCategoriaProductosController implements Initializable {

    private Main stage;
    
    private static Connection conexion = null;  // Abre la conexion
    private static PreparedStatement statement = null;  // Almacena la consulta, osea el call _____
    private static ResultSet resultSet = null; // Resultado de la consulta
    
    @FXML
    Button btnAgregar,btnEditar,btnEliminar,btnReporte,btnVolver,btnBuscar;
    @FXML
    TableView tblCategoriaProductos;
    @FXML
    TableColumn colCategoriaId,colNombreCategoria,colDescripcionCategoria;
    @FXML
    TextField tfCategoriaProductoId;
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        cargarDatos();
    } 
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnVolver){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnEliminar){
            int categoriaProductoId = ((CategoriaProducto)tblCategoriaProductos.getSelectionModel().getSelectedItem()).getCategoriaId();
             eliminarCategoriaProducto(categoriaProductoId);
             cargarDatos();
        }else if(event.getSource() == btnBuscar){
            tblCategoriaProductos.getItems().clear();
            
            if(tfCategoriaProductoId.getText().equals("")){
                cargarDatos();
            }else{
                tblCategoriaProductos.getItems().add(buscarCategoriaProducto());
                tblCategoriaProductos.setItems(listarCategoriaProductos());
                colCategoriaId.setCellValueFactory(new PropertyValueFactory<CategoriaProducto,Integer>("categoriaProductosId"));
                colNombreCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProducto,String>("nombreCategoria"));
                colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProducto,String>("descripcionCategoria"));

            }
        }else if(event.getSource() == btnAgregar){
            stage.fromCategoriaProductos(1);
        }else if(event.getSource() == btnEditar){
            stage.fromCategoriaProductos(2);
        }
    }
    
    // Listar y Cargar Datos
    public void cargarDatos(){
        tblCategoriaProductos.setItems(listarCategoriaProductos());
        colCategoriaId.setCellValueFactory(new PropertyValueFactory<CategoriaProducto,Integer>("categoriaProductosId"));
        colNombreCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProducto,String>("nombreCategoria"));
        colDescripcionCategoria.setCellValueFactory(new PropertyValueFactory<CategoriaProducto,String>("descripcionCategoria"));
    }
    
    
    
    public ObservableList<CategoriaProducto> listarCategoriaProductos(){
        ArrayList<CategoriaProducto> categoriaProductos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarCategoriaProductos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int categoriaProductosId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                categoriaProductos.add(new CategoriaProducto(categoriaProductosId,nombreCategoria,descripcionCategoria));
                
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
        
        
        return FXCollections.observableList(categoriaProductos);
    }
    
    public void eliminarCategoriaProducto(int categoriaProductosId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCategoriaProducto(?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1,categoriaProductosId);
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
            }
        }
    }
    
    public CategoriaProducto buscarCategoriaProducto(){
        CategoriaProducto categoriaProducto = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCategoriaProducto(?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1,Integer.parseInt(tfCategoriaProductoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int categoriaProductosId = resultSet.getInt("categoriaProductosId");
                String nombreCategoria = resultSet.getString("nombreCategoria");
                String descripcionCategoria = resultSet.getString("descripcionCategoria");
                
                categoriaProducto = (new CategoriaProducto(categoriaProductosId,nombreCategoria,descripcionCategoria));
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
        
        return categoriaProducto;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
