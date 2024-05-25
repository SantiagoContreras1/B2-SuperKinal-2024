/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.santiagocontreras.dto;

import org.santiagocontreras.model.CategoriaProducto;

/**
 *
 * @author senor
 */
public class CategoriaProductoDTO {
    private static CategoriaProductoDTO instance;
    private CategoriaProducto categoriaProducto;

    public CategoriaProductoDTO() {
    }

    public static CategoriaProductoDTO getInstance() {
        if(instance == null){
            instance = new CategoriaProductoDTO();
        }
        return instance;
    }

    public CategoriaProducto getCategoriaProducto() {
        return categoriaProducto;
    }

    public void setCategoriaProducto(CategoriaProducto categoriaProducto) {
        this.categoriaProducto = categoriaProducto;
    }
    
    
    
}
