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
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.santiagocontreras.dao.Conexion;
import org.santiagocontreras.model.Distribuidor;
import org.santiagocontreras.system.Main;
import org.santiagocontreras.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class MenuDistribuidoresController implements Initializable {
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet =null;
    
    @FXML
    Button btnAgregar,btnEditar,btnEliminar,btnBuscar,btnVolver;
    @FXML
    TextField tfDistribuidorId;
    @FXML
    TableView tblDistribuidores;
    @FXML
    TableColumn colDistribuidorId,colNombreDistribuidor,colDireccion,colNit,colTelefono,colWeb;
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarLista();
    }    
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnVolver){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formularioDistribuidoresView(1);
        }else if(event.getSource() == btnEditar){
            stage.formularioDistribuidoresView(2);
        }else if(event.getSource() == btnEliminar){
            if(SuperKinalAlert.getInstance().mostrarAlertaConfirmacion(403).get() == ButtonType.OK){
                int distribuidorId = ((Distribuidor)tblDistribuidores.getSelectionModel().getSelectedItem()).getDistribuidorId();
                eliminarCargo(distribuidorId);
                cargarLista();
            }
        }else if(event.getSource() == btnBuscar){
            tblDistribuidores.getItems().clear();
            
            if(tfDistribuidorId.getText().equals("")){
                cargarLista();
            }else{
                Distribuidor distribuidor = buscarDistribuidor();
                
                if(distribuidor != null){
                    ObservableList<Distribuidor> distribuidorList = FXCollections.observableArrayList();
                    distribuidorList.add(distribuidor);
                    tblDistribuidores.setItems(distribuidorList);
                    colDistribuidorId.setCellValueFactory(new PropertyValueFactory<Distribuidor,Integer>("distribuidorId"));
                    colNombreDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidor,String>("nombreDistribuidor"));
                    colDireccion.setCellValueFactory(new PropertyValueFactory<Distribuidor,String>("direccionDistribuidor"));
                    colNit.setCellValueFactory(new PropertyValueFactory<Distribuidor,String>("nitDistribuidor"));
                    colTelefono.setCellValueFactory(new PropertyValueFactory<Distribuidor,String>("telefonoDistribuidor"));
                    colWeb.setCellValueFactory(new PropertyValueFactory<Distribuidor,String>("web"));
                }
            }
        }
    }
    
    // Listar y Cargad Datos
    public void cargarLista(){
        tblDistribuidores.setItems(listarDistribuidores());
        colDistribuidorId.setCellValueFactory(new PropertyValueFactory<Distribuidor,Integer>("distribuidorId"));
        colNombreDistribuidor.setCellValueFactory(new PropertyValueFactory<Distribuidor,String>("nombreDistribuidor"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Distribuidor,String>("direccionDistribuidor"));
        colNit.setCellValueFactory(new PropertyValueFactory<Distribuidor,String>("nitDistribuidor"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Distribuidor,String>("telefonoDistribuidor"));
        colWeb.setCellValueFactory(new PropertyValueFactory<Distribuidor,String>("web"));
        
    }
    
    public ObservableList<Distribuidor> listarDistribuidores(){
        ArrayList<Distribuidor> distribuidores = new ArrayList();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarDistribuidores()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("distribuidorId");
                String nombre = resultSet.getString("nombreDistribuidor");
                String direccion = resultSet.getString("direccionDistribuidor");
                String nit = resultSet.getString("nitDistribuidor");
                String telefono = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                
                distribuidores.add(new Distribuidor(cargoId,nombre,direccion,nit,telefono,web));
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
        
        return FXCollections.observableList(distribuidores);
    }

    // Agregar
        // En FormularioDistribuidoresController
    
    
    // Eliminar
    public void eliminarCargo(int distribuidorId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarDistribuidores(?)";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, distribuidorId);
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
    
    
    // Buscar
    public Distribuidor buscarDistribuidor(){
        Distribuidor distribuidor = null;
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarDistribuidores(?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1,Integer.parseInt(tfDistribuidorId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int cargoId = resultSet.getInt("distribuidorId");
                String nombre = resultSet.getString("nombreDistribuidor");
                String direccion = resultSet.getString("direccionDistribuidor");
                String nit = resultSet.getString("nitDistribuidor");
                String telefono = resultSet.getString("telefonoDistribuidor");
                String web = resultSet.getString("web");
                
                distribuidor = new Distribuidor(cargoId,nombre,direccion,nit,telefono,web);
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
            }
        }
        return distribuidor;
    }
    
    
    // Editar
        // En FormularioDistribuidoresController

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
    
}
