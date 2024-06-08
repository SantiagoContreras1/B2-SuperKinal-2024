
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.model;

/**
 *
 * @author informatica
 */
public class Usuario {
    private int usuarioId;
    private String usuario;
    private String pass;
    private int nivelAcceso;
    private int empleadoId;

    public Usuario() {
    }

    public Usuario(int usuarioId, String usuario, String pass, int nivelAcceso, int empleadoId) {
        this.usuarioId = usuarioId;
        this.usuario = usuario;
        this.pass = pass;
        this.nivelAcceso = nivelAcceso;
        this.empleadoId = empleadoId;
    }

    public int getUsuarioId() {
        return usuarioId;
    }

    public void setUsuarioId(int usuarioId) {
        this.usuarioId = usuarioId;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
    }

    public int getNivelAcceso() {
        return nivelAcceso;
    }

    public void setNivelAcceso(int nivelAcceso) {
        this.nivelAcceso = nivelAcceso;
    }

    public int getEmpleadoId() {
        return empleadoId;
    }

    public void setEmpleadoId(int empleadoId) {
        this.empleadoId = empleadoId;
    }

    @Override
    public String toString() {
        return "Usuario{" + "usuarioId=" + usuarioId + ", usuario=" + usuario + ", pass=" + pass + ", nivelAcceso=" + nivelAcceso + ", empleadoId=" + empleadoId + '}';
    }
    
    
}
