
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.model;

import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author senor
 */
public class Factura {
    private int facturaId;
    private Date fecha;
    private Time hora;
    private String clienteId;  // CONCAT()
    private String empleadoId;  //CONCAT()
    private double total;

    public Factura() {
    }

    public Factura(int facturaId, Date fecha, Time hora, String clienteId, String empleadoId, double total) {
        this.facturaId = facturaId;
        this.fecha = fecha;
        this.hora = hora;
        this.clienteId = clienteId;
        this.empleadoId = empleadoId;
        this.total = total;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public Time getHora() {
        return hora;
    }

    public void setHora(Time hora) {
        this.hora = hora;
    }

    public String getClienteId() {
        return clienteId;
    }

    public void setClienteId(String clienteId) {
        this.clienteId = clienteId;
    }

    public String getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(String empleadoId) {
        this.empleadoId = empleadoId;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ID: " + facturaId  + " | " + "Fecha: " + fecha  + " | " + "Hora: " + hora  + " | " + "Cliente: " + clienteId  + " | " + "Empleado: " + empleadoId  + " | " + "Total: " + total  + " | ";
    }
    
    
}
