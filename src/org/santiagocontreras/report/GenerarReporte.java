/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.report;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.santiagocontreras.dao.Conexion;
import win.zqxu.jrviewer.JRViewerFX;

/**
 *
 * @author senor
 */
public class GenerarReporte {
    private static GenerarReporte instance;
    
    private static Connection conexion = null;
    
    
    private GenerarReporte(){
    }
    
    public static GenerarReporte getInstance(){
        if(instance == null){
            instance = new GenerarReporte();
        }
        return instance;
    }
    
    public void generarFactura(int factId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            
            // Si el reporte solicita parametros se hace esto:
            Map<String, Object> parametros = new HashMap<>();
            parametros.put("facId", factId);
            
            //Paso 3: Crear el reporte, linea importante
            InputStream jasperPath = GenerarReporte.class.getResourceAsStream("org.santiagocontreras.report.Factura.jasper");
            JasperPrint reporte = JasperFillManager.fillReport(jasperPath,parametros,conexion);
            
            //Paso 4: crear la ventana de javafx para mostrar el reporte
            Stage reportStage = new Stage();
            
            // Llenar el stage con el reporte
            JRViewerFX reportViewer = new JRViewerFX(reporte);
            
            Pane root = new Pane();
            
            root.getChildren().add(reportViewer);
            
            reportViewer.setPrefSize(1000, 800);
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("Reporte");
            reportStage.show();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
