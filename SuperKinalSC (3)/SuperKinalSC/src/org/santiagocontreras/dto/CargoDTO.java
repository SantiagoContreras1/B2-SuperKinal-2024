/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.dto;

import org.santiagocontreras.model.Cargo;

/**
 *
 * @author senor
 */
public class CargoDTO {
    private static CargoDTO instance;
    private Cargo cargo;
    
    private CargoDTO(){
    }
    
    public static CargoDTO getCargoDTO(){
        if(instance == null){
            instance = new CargoDTO();
        }
        return instance;
    }

    public Cargo getCargo() {
        return cargo;
    }

    public void setCargo(Cargo cargo) {
        this.cargo = cargo;
    }
    
    
    
}
