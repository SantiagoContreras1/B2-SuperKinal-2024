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
import org.santiagocontreras.model.Promocion;
import org.santiagocontreras.system.Main;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class MenuPromocionesController implements Initializable {

    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet =null;
    
    @FXML
    TableView tblPromociones;
    @FXML
    TableColumn colPromocionId,colPrecioPromocion,colDescripcion,colFechaInicio,colFechaFin,colProducto;
    @FXML
    TextField tfPromoId;
    @FXML
    Button btnVolver,btnAgregar,btnEditar,btnEliminar,btnBuscar;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarDatos();
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnVolver){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnEliminar){
            int promo = ((Promocion)tblPromociones.getSelectionModel().getSelectedItem()).getPromocionId();
            eliminarPromo(promo);
            cargarDatos();
        }else if(event.getSource() == btnBuscar){
            
        }else if(event.getSource() == btnAgregar){
            
        }else if(event.getSource() == btnEditar){
            tblPromociones.getItems().clear();
            
            if(tfPromoId.getText().equals("")){
                cargarDatos();
            }else{
                Promocion promo = buscarPromo();
                
                if(promo != null){
                    ObservableList<Promocion> promoList = FXCollections.observableArrayList();
                    tblPromociones.setItems(listarPromociones());
                    colPromocionId.setCellValueFactory(new PropertyValueFactory<Promocion,Integer>("promocionId")); 
                    colPrecioPromocion.setCellValueFactory(new PropertyValueFactory<Promocion,Double>("precioPromocion"));
                    colDescripcion.setCellValueFactory(new PropertyValueFactory<Promocion,String>("precioPromocion"));
                    colFechaInicio.setCellValueFactory(new PropertyValueFactory<Promocion,Date>("precioPromocion"));
                    colFechaFin.setCellValueFactory(new PropertyValueFactory<Promocion,Date>("fechaFinalizacion"));
                    colProducto.setCellValueFactory(new PropertyValueFactory<Promocion,String>("Producto"));
                    tblPromociones.getSortOrder().add(colPromocionId);
                }
            }
        }
    }
    
    public void cargarDatos(){
        tblPromociones.setItems(listarPromociones());                               /*Nombre Columna*/
        colPromocionId.setCellValueFactory(new PropertyValueFactory<Promocion,Integer>("promocionId")); 
        colPrecioPromocion.setCellValueFactory(new PropertyValueFactory<Promocion,Double>("precioPromocion"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<Promocion,String>("precioPromocion"));
        colFechaInicio.setCellValueFactory(new PropertyValueFactory<Promocion,Date>("precioPromocion"));
        colFechaFin.setCellValueFactory(new PropertyValueFactory<Promocion,Date>("fechaFinalizacion"));
        colProducto.setCellValueFactory(new PropertyValueFactory<Promocion,String>("Producto"));
        tblPromociones.getSortOrder().add(colPromocionId);
    }
    
    
    
    public ObservableList<Promocion> listarPromociones(){
        ArrayList<Promocion> promo = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarPromociones()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int promocionId = resultSet.getInt("promocionId");
                Double precioPromocion = resultSet.getDouble("precioPromocion");
                String descripcionPromocion = resultSet.getString("descripcionPromocion");
                Date fechaInicio = resultSet.getDate("fechaInicio");
                Date fechaFinalizacion = resultSet.getDate("fechaFinalizacion");
                String producto = resultSet.getString("Producto");
                
                promo.add(new Promocion(promocionId,precioPromocion,descripcionPromocion,fechaInicio,fechaFinalizacion,producto));
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
        
        
        return FXCollections.observableList(promo);
    }
    
    public void eliminarPromo(int promoId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarPromociones(?)";
            statement= conexion.prepareStatement(sql);
            
            statement.setInt(1,promoId);
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
    
    public Promocion buscarPromo(){
        Promocion promo = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarPromociones()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int promocionId = resultSet.getInt("promocionId");
                Double precioPromocion = resultSet.getDouble("precioPromocion");
                String descripcionPromocion = resultSet.getString("descripcionPromocion");
                Date fechaInicio = resultSet.getDate("fechaInicio");
                Date fechaFinalizacion = resultSet.getDate("fechaFinalizacion");
                String producto = resultSet.getString("Producto");
                
                promo = (new Promocion(promocionId,precioPromocion,descripcionPromocion,fechaInicio,fechaFinalizacion,producto));
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
        
        
        return promo;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
