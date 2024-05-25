
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.model;

/**
 *
 * @author senor
 */
public class DetalleFactua {
    private int detalleFacturaId;
    private int facturaId;
    private int productoId;

    public DetalleFactua() {
    }

    public DetalleFactua(int detalleFacturaId, int facturaId, int productoId) {
        this.detalleFacturaId = detalleFacturaId;
        this.facturaId = facturaId;
        this.productoId = productoId;
    }

    public int getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public void setDetalleFacturaId(int detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    public int getProductoId() {
        return productoId;
    }

    public void setProductoId(int productoId) {
        this.productoId = productoId;
    }

    @Override
    public String toString() {
        return "ID: " + detalleFacturaId + " | " +  "ID Factura: " + facturaId + "ID Producto: " + productoId  + " | ";
    }
    
    
}
