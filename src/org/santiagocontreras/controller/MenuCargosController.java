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
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.santiagocontreras.dao.Conexion;
import org.santiagocontreras.dto.CargoDTO;
import org.santiagocontreras.model.TicketSoporte;
import org.santiagocontreras.system.Main;
import org.santiagocontreras.model.Cargo;
import org.santiagocontreras.model.Cliente;
import org.santiagocontreras.utils.SuperKinalAlert;

/**
 * FXML Controller class
 *
 * @author senor
 */
public class MenuCargosController implements Initializable {
    private Main stage;
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultSet =null;
    
    @FXML
    Button btnAgregar, btnEditar, btnEliminar,btnVolver,btnBuscar;
    @FXML
    TableView tblCargos;
    @FXML
    TableColumn colCargosId,colNombreCargo,colDescripcionCargo;
    @FXML
    TextField tfCargoId;
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        cargarLista();
    }
    
    @FXML
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnVolver){
            stage.menuPrincipalView();
        }else if(event.getSource() == btnAgregar){
            stage.formularioCargosView(1);
        }else if(event.getSource() == btnEditar){
            CargoDTO.getCargoDTO().setCargo((Cargo)tblCargos.getSelectionModel().getSelectedItem());
            stage.formularioCargosView(2);
        }else if(event.getSource() == btnBuscar){
            tblCargos.getItems().clear();
            
            if(tfCargoId.getText().equals("")){
                cargarLista();
            }else{
                Cargo cargo = buscarCargo();
                if (cargo != null) {
                    ObservableList<Cargo> cargosList = FXCollections.observableArrayList();
                    cargosList.add(cargo);
                    tblCargos.setItems(cargosList);
                    colCargosId.setCellValueFactory(new PropertyValueFactory<Cargo,Integer>("cargoId"));
                    colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargo, String>("nombreCargo"));
                    colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Cargo,String>("descripcionCargo"));
                }
            }
        }
    }
    
    
    
    // Listar
        // cargar Lista
    public void cargarLista(){
        tblCargos.setItems(listarCargos());
        colCargosId.setCellValueFactory(new PropertyValueFactory<Cargo,Integer>("cargoId"));
        colNombreCargo.setCellValueFactory(new PropertyValueFactory<Cargo, String>("nombreCargo"));
        colDescripcionCargo.setCellValueFactory(new PropertyValueFactory<Cargo,String>("descripcionCargo"));
        
    }
    
    
    public ObservableList<Cargo> listarCargos(){
        ArrayList<Cargo> cargos = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_ListarCargos()";
            statement = conexion.prepareStatement(sql);
            resultSet = statement.executeQuery();
            
            while(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombre = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargos.add(new Cargo(cargoId,nombre,descripcionCargo));
                
                
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
        
        return FXCollections.observableList(cargos);
    }
    
    
    /*public void eliminarCargo(int cargoId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarCargo(?)";
            statement = conexion.prepareStatement(sql);
    
            statement.setInt(1,cargoId);
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
    }*/
    
    public Cargo buscarCargo(){
        Cargo cargo = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarCargo(?)";
            statement = conexion.prepareStatement(sql);
            
            statement.setInt(1, Integer.parseInt(tfCargoId.getText()));
            resultSet = statement.executeQuery();
            
            if(resultSet.next()){
                int cargoId = resultSet.getInt("cargoId");
                String nombreCargo = resultSet.getString("nombreCargo");
                String descripcionCargo = resultSet.getString("descripcionCargo");
                
                cargo = (new Cargo(cargoId,nombreCargo,descripcionCargo));
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
        
        
        return cargo;
    }

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    
    
}
