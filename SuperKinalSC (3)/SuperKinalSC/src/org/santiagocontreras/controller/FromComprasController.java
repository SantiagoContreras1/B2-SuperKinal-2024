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
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import org.santiagocontreras.dao.Conexion;
import org.santiagocontreras.model.DetalleCompra;
import org.santiagocontreras.model.Producto;
import org.santiagocontreras.system.Main;
import org.santiagocontreras.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class FromComprasController implements Initializable {

    private Main stage;
    private int op;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet =null;
    
    @FXML
    TextField tfID,tfTotal,tfFecha,tfCantidad;
    @FXML
    ComboBox cmbProducto;
    @FXML
    Button btnGuardar,btnCancelar;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cmbProducto.setItems(listarProductos());
        
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws ParseException{
        if(event.getSource() == btnCancelar){
            stage.fromComprasView(op);
        }else if(event.getSource() == btnGuardar){
            
            if(op == 1){
                if(!(tfTotal.getText().equals("") && tfFecha.getText().equals("") && tfCantidad.getText().equals(""))){
                    agregarCompra();
                    agregarDetalleCompra();
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(401);
                    stage.menuComprasView();
                }else{
                    SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                    tfTotal.requestFocus();
                    return;
                }
            }else if(op == 2){
                if(!(tfTotal.getText().equals("") && tfFecha.getText().equals("") && tfCantidad.getText().equals(""))){
                    
                    if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(404).get() == ButtonType.OK){
                        editarCompra();
                        editarDetalleCompra();
                        stage.menuComprasView();
                    }else{
                        SuperKinalAlert.getInstance().mostrarAlertaInfo(400);
                        tfTotal.requestFocus();
                        return;
                    }
                    
                }
            }
        }
    }
    
    
    public void agregarCompra() throws ParseException{
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarCompra(?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setDate(1,new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(tfFecha.getText()).getTime()));
            statement.setDouble(2, Double.parseDouble(tfTotal.getText()));
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
    
    public void agregarDetalleCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_AgregarDetalleCompra(?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1,Integer.parseInt(tfCantidad.getText()));
            statement.setInt(2, ((DetalleCompra)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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
    
    public void editarCompra() throws ParseException{
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCompra(?,?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1, Integer.parseInt(tfID.getText()));
            statement.setDate(2,new java.sql.Date(new SimpleDateFormat("yyyy-MM-dd").parse(tfFecha.getText()).getTime()));
            statement.setDouble(3, Double.parseDouble(tfTotal.getText()));
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
    
    public void editarDetalleCompra(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarDetalleCompra(?,?,?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1, Integer.parseInt(tfID.getText()));
            statement.setInt(2,Integer.parseInt(tfCantidad.getText()));
            statement.setInt(3, ((DetalleCompra)cmbProducto.getSelectionModel().getSelectedItem()).getProductoId());
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
