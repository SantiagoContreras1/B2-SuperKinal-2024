
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.model;

import java.sql.Date;

/**
 *
 * @author senor
 */
public class Compras {
    private int compraId;
    private Date fechaCompra;
    private double totalCompra;

    public Compras() {
    }

    public Compras(int compraId, Date fechaCompra, double totalCompra) {
        this.compraId = compraId;
        this.fechaCompra = fechaCompra;
        this.totalCompra = totalCompra;
    }

    public int getCompraId() {
        return compraId;
    }

    public void setCompraId(int compraId) {
        this.compraId = compraId;
    }

    public Date getFechaCompra() {
        return fechaCompra;
    }

    public void setFechaCompra(Date fechaCompra) {
        this.fechaCompra = fechaCompra;
    }

    public double getTotalCompra() {
        return totalCompra;
    }

    public void setTotalCompra(double totalCompra) {
        this.totalCompra = totalCompra;
    }

    @Override
    public String toString() {
        return "ID: " + compraId + " | " + "Fecha de Compra: " + fechaCompra + " | " + "Total de Compra: " + totalCompra + " | ";
    }
    
    
}
