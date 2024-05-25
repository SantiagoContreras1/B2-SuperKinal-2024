
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
public class Cliente {
    private int clienteId;
    private String nombre;
    private String apellido;
    private String nit;
    private String telefono;
    private String direccion;

    public Cliente() {
    }

    public Cliente(int clienteId, String nombre, String apellido,String nit, String telefono, String direccion) {
        this.clienteId = clienteId;
        this.nombre = nombre;
        this.apellido = apellido;
        this.nit = nit;
        this.telefono = telefono;
        this.direccion = direccion;
    }

    public int getClienteId() {
        return clienteId;
    }

    public void setClienteId(int clienteId) {
        this.clienteId = clienteId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    @Override
    public String toString() {
        return "ID" + clienteId + " | "+nombre+" | " + apellido;
    }

    
    
    
    
    
    
}
