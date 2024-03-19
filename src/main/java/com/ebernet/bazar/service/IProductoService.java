package com.ebernet.bazar.service;

import com.ebernet.bazar.model.Producto;
import java.util.List;


public interface IProductoService {
    //crear producto
    public void crearProducto(Producto prod);
    //lista de productos
    public List<Producto> listaProductos();
    //traer producto en particular
    public Producto traerProducto(Long idProd);
    //eliminar producto
    public void eliminarProducto(Long idProd);
    //editar producto
    public Producto editarProducto (Long idProd, Long nuevoIdProd, String nuevoNombre, String nuevaMarca, Double nuevoCosto, Double nuevaCantidad);
    //trear productos con bajo stock
    public List<Producto> productosConStockBajo();
    
}
