package com.ebernet.bazar.service;

import com.ebernet.bazar.model.Producto;
import com.ebernet.bazar.repository.IProductoRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductoService implements IProductoService{
    @Autowired
    private IProductoRepository produrep;

    @Override
    public void crearProducto(Producto prod) {
        produrep.save(prod);
    }

    @Override
    public List<Producto> listaProductos() {
       return produrep.findAll();
    }

    @Override
    public Producto traerProducto(Long idProd) {
        return produrep.findById(idProd).orElse(null);
    }

    @Override
    public void eliminarProducto(Long idProd) {
        produrep.deleteById(idProd);
    }

    @Override
    public Producto editarProducto(Long idProd, Long nuevoIdProd, String nuevoNombre, String nuevaMarca, Double nuevoCosto, Double nuevaCantidad) {
        Producto p = produrep.findById(idProd).orElse(null);
        if(nuevoIdProd != null){
            p.setId_prod(nuevoIdProd);
        }
        if(nuevoNombre != null){
            p.setNombre(nuevoNombre);
        }
        if(nuevaMarca != null){
            p.setMarca(nuevaMarca);
        }
        if(nuevoCosto != null){
            p.setCosto(nuevoCosto);
        }
        if(nuevaCantidad != null){
            p.setCantidad(nuevaCantidad);
        }
        produrep.save(p);
        return p;
    }

    @Override
    public List<Producto> productosConStockBajo() {
        return produrep.traerProductosConCantidadMenorA5();
    }

    
}
